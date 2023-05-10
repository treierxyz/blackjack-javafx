package xyz.treier.blackjackjavafx;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import xyz.treier.blackjackjavafx.kontrollerid.LõppKontroller;
import xyz.treier.blackjackjavafx.kontrollerid.MängKontroller;

import java.util.*;


public class Mäng {
    private MängKontroller mängKontroller;
    private Queue<Mängija> mängijad;


    public Mäng(MängKontroller mängKontroller) {
        this.mängKontroller = mängKontroller;
        mängijad = new LinkedList<>();
        lisaOsalejad(this.mängKontroller.getMängijad(), new ArrayList<>());
    }

    public void alusta() {
        List<Mängija> lõpetanudList = new ArrayList<>();
        mängKontroller.setLõpetanudList(lõpetanudList);

        järgmineMängija();
    }

    /**
     * Seab järjekorrast järgmise mängija mängukontrolleri aktiivseks mängijaks.
     * Ringi lõpus lisab ka diilerile kaardi juurde.
     */
    public void järgmineMängija() {
        Mängija järgmine;

        // Kui järjekord on tühi ehk mängijate ring tehtud
        if ((järgmine = mängijad.poll()) == null) {
            // Kui kõik mängijad on lõpetanud, kuva lõpustseen
            if (mängKontroller.getLõpetanudList().size() == mängKontroller.getMängijad().size()) {
                LõppKontroller lõppKontroller = VaateVahetaja.vaheta(Vaade.LÕPP);
                lõppKontroller.setMängijadList(mängKontroller.getMängijad());
                lõppKontroller.setDiiler(mängKontroller.getDiiler());
                lõppKontroller.lõpuEdetabel();

                System.out.println("Mängijad otsas");
                return;
            }

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
}
