package xyz.treier.blackjackjavafx;

import javafx.beans.Observable;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import xyz.treier.blackjackjavafx.kontrollerid.LõppKontroller;
import xyz.treier.blackjackjavafx.kontrollerid.MängKontroller;

import java.util.*;


public class Mäng {
    private MängKontroller mängKontroller;
    private Queue<Mängija> mängijad;
    private ObservableList<IntegerProperty> krediitList = FXCollections.observableArrayList();
    private IntegerProperty krediitSumma = new SimpleIntegerProperty();


    public Mäng(MängKontroller mängKontroller) {
        this.mängKontroller = mängKontroller;
        mängijad = new LinkedList<>();
        lisaOsalejad(this.mängKontroller.getMängijad(), mängKontroller.getLõpetanudList());
        for (Mängija mängija : mängKontroller.getMängijad()) {
            krediitSumma.add(mängija.krediitProperty());
        }
        krediitSumma.bind(Bindings.createIntegerBinding(() -> krediitList.stream().mapToInt(IntegerProperty::get).sum(), krediitList));
    }

    public void init() {
        järgmineMängija();
    }

    /**
     * Seab järjekorrast järgmise mängija mängukontrolleri aktiivseks mängijaks.
     * Ringi lõpus lisab sooritab ka diileri käigu.
     */
    public void järgmineMängija() {
        Mängija järgmine;

        // Kui järjekord on tühi ehk mängijate ring tehtud
        if ((järgmine = mängijad.poll()) == null) {
            // Diileri käik
            Mängija diiler = mängKontroller.getDiiler();

            // Kui diileril on alla 17-ne, siis lisa kaart
            if (diiler.getKäsi().summa() < 17) {
                HBox diileriKaardid = mängKontroller.getDiileriKaardid();
                Kaardipakk pakk = mängKontroller.getMänguPakk();
                diiler.getKäsi().lisaKaart(pakk.suvaline());

                // Lisa küsimärk ekraanile
                Text küsimärk = new Text(" ? ");
                küsimärk.setFont(new Font(16));
                diileriKaardid.getChildren().add(küsimärk);
            }

            // Kui kõik mängijad on lõpetanud, kuva lõpustseen
            if (mängKontroller.getLõpetanudList().size() == mängKontroller.getMängijad().size()) {
                lõpuStseen(); //
                return;
            }

            // Lisa allesjäänud mängijad uuesti järjekorda
            lisaOsalejad(mängKontroller.getMängijad(), mängKontroller.getLõpetanudList());
            järgmine = mängijad.poll(); // võta järjekorrast järgmine mängija
        }

        järgmine.setSeis(MängijaSeis.MÄNGIB);
        mängKontroller.setKelleKäik(järgmine); // sea järgmine mängija kontrolleris aktiivseks mängijaks
    }

    /**
     * Lisab kõik mängijad, kes ei ole lõpetanud, käigu järjekorda
     * @param mängijad Mängu mängijad
     * @param lõpetanud Mängu lõpetanud mängijad
     */
    public void lisaOsalejad(List<Mängija> mängijad, List<Mängija> lõpetanud) {
        for (Mängija m : mängijad) {
            if (lõpetanud.contains(m)) continue;
            this.mängijad.add(m);
        }
    }

    public void lõpuStseen() {
        LõppKontroller lõppKontroller = mängKontroller.getLõppEkraanController();
        System.out.println(krediitSumma.toString());
        lõppKontroller.getJätkaNupp().disableProperty().bind(krediitSumma.greaterThan(0));
        mängKontroller.näitaLõppEkraan(true);
        lõppKontroller.setMängijadList(mängKontroller.getMängijad());
        lõppKontroller.setDiiler(mängKontroller.getDiiler());
        lõppKontroller.lõpuTabel();
        mängKontroller.edetabel();

        System.out.println("Mängijad otsas");
    }

    public int getKrediitSumma() {
        return krediitSumma.get();
    }

    public IntegerProperty krediitSummaProperty() {
        return krediitSumma;
    }
}
