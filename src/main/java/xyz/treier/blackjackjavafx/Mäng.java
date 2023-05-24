package xyz.treier.blackjackjavafx;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import xyz.treier.blackjackjavafx.kontrollerid.LõppKontroller;
import xyz.treier.blackjackjavafx.kontrollerid.MängKontroller;

import java.util.*;


public class Mäng {
    private final MängKontroller mängKontroller;
    private final Queue<Mängija> mängijad;
    private final ObservableList<IntegerProperty> krediitList = FXCollections.observableArrayList();
    private final IntegerProperty krediitSumma = new SimpleIntegerProperty();


    public Mäng(MängKontroller mängKontroller) {
        this.mängKontroller = mängKontroller;
        mängijad = new LinkedList<>();
        lisaJärjekorda(this.mängKontroller.getMängijad(), this.mängKontroller.getLõpetanudList());

        for (Mängija mängija : mängKontroller.getMängijad()) {
            krediitList.add(mängija.krediitProperty());
        }
    }

    /**
     * Seab järjekorrast järgmise mängija mängukontrolleri aktiivseks mängijaks.
     * Ringi lõpus sooritab ka diileri käigu.
     * Kui kõik mängijad on lõpetanud, siis kuvab lõpuvaate.
     */
    public void järgmineMängija() { // holy moly mis siin toimub
        Mängija järgmine;

        // Kui järjekord on tühi ehk mängijate ring tehtud
        if ((järgmine = mängijad.poll()) == null) {
            // Diileri käik
            Diiler diiler = mängKontroller.getDiiler();
            // Kui diileril on alla 17-ne või on soft 17, siis lisa kaart
            if (diiler.getKäsi().summa() < 17 ||
                diiler.getKäsi().summa() == 17 && diiler.getKäsi().getKaardid().contains(new Kaart(null, Väärtus.ÄSS)))
                lisaDiilerileKaart(diiler);

            // Kui kõik mängijad on lõpetanud, kuva lõpustseen
            if (mängKontroller.getLõpetanudList().size() == mängKontroller.getMängijad().size()) {
                // Mängu lõpus lisa diilerile kaarte kuni vähemalt 17 on koos ja ei ole soft 17
                while (diiler.getKäsi().summa() < 17 ||
                       diiler.getKäsi().summa() == 17 && diiler.getKäsi().getKaardid().contains(new Kaart(null, Väärtus.ÄSS)))
                    lisaDiilerileKaart(diiler);

                // Lõpuvaate initsialiseerimine
                LõppKontroller lõppKontroller = mängKontroller.getLõppEkraanController();
                lõppKontroller.setMängijadList(mängKontroller.getMängijad());
                lõppKontroller.setDiiler(mängKontroller.getDiiler());
                lõppKontroller.lõpuTabel();
                mängKontroller.edetabel();

                // kui kõikidel mängijatel on krediit otsas, siis ei saa "jätka" nuppu vajutada
                krediitSumma.bind(Bindings.createIntegerBinding(() -> krediitList.stream().mapToInt(IntegerProperty::get).sum(), krediitList));
                lõppKontroller.getJätkaNupp().disableProperty().bind(krediitSumma.lessThanOrEqualTo(0));

                // Kuva mängu lõpuvaade
                mängKontroller.näitaLõppEkraan(true);
                return;
            }

            // Lisa allesjäänud mängijad uuesti järjekorda
            lisaJärjekorda(mängKontroller.getMängijad(), mängKontroller.getLõpetanudList());
            järgmine = mängijad.poll(); // võta järjekorrast järgmine mängija
        }

        järgmine.setSeis(MängijaSeis.MÄNGIB);
        mängKontroller.setKelleKäik(järgmine); // sea järgmine mängija kontrolleris aktiivseks mängijaks

        int mitmesKäsi = mitmesKäsiMängitav(järgmine.getKäed());
        järgmine.getKäed().get(mitmesKäsi).setSeis(KäsiSeis.MÄNGIB);
        mängKontroller.setMitmesKäsi(mitmesKäsi);
    }

    /**
     * Leiab mängija käe, millega saab veel mängida.
     *
     * @param käed Mängija käed
     * @return Indeks käele, mis ei ole stand ega bust
     */
    public int mitmesKäsiMängitav(List<Käsi> käed) {
        int i;
        for (i = 0; i < käed.size(); i++) {
            Käsi käsi = käed.get(i);
            // Kui käsi ei ole bust või stand, siis saab seda mängida
            if (käsi.getSeis() != KäsiSeis.BUST &&
                    käsi.getSeis() != KäsiSeis.STAND)
                return i;
        }
        return i;
    }

    /**
     * Lisab kõik mängijad, kes ei ole lõpetanud, käigu järjekorda.
     * @param mängijad Mängu mängijad.
     * @param lõpetanud Mängu lõpetanud mängijad.
     */
    public void lisaJärjekorda(List<Mängija> mängijad, List<Mängija> lõpetanud) {
        for (Mängija m : mängijad) {
            if (lõpetanud.contains(m)) continue;
            this.mängijad.add(m);
        }
    }

    /**
     * Lisab diilerile kaardi juurde.
     * @param diiler Mängu diiler
     */
    public void lisaDiilerileKaart(Diiler diiler) {
        HBox diileriKaardid = mängKontroller.getDiileriKaardid();
        Kaardipakk pakk = mängKontroller.getMänguPakk();
        diiler.getKäsi().lisaKaart(pakk.suvaline());

        // Lisa küsimärk ekraanile kaardi asemel
        Label küsimärk = new Label(" ? ");
        küsimärk.setFont(new Font(16));
        diileriKaardid.getChildren().add(küsimärk);
    }
}
