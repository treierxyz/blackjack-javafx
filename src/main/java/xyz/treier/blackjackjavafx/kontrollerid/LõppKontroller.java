package xyz.treier.blackjackjavafx.kontrollerid;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import xyz.treier.blackjackjavafx.*;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

import java.util.List;

public class LõppKontroller {

    @FXML
    private VBox võitjadVBox;
    @FXML
    private VBox kaotajadVBox;
    @FXML
    private VBox viikVBox;
    @FXML
    private HBox diileriKaardidHBox;
    @FXML
    private Button jätkaNupp;

    private List<Mängija> mängijadList;
    private Diiler diiler;

    /**
     * Kuvab mängu lõpus võitjad, kaotajad ja viiki jäänud mängijad ja nende kaardid.
     */
    public void lõpuTabel() {
        // Näita diileri kaardid
        for (Label kaart : diiler.getKäsi().getKaardidLabelid()) {
            kaart.setText(kaart.getText() + " ");
            diileriKaardidHBox.getChildren().add(kaart);
        }

        int diileriTulemus = diiler.getKäsi().summa();
        boolean diilerilBlackjack = false;

        // Diileril bust kui üle 21
        if (diileriTulemus > 21) {
            Label bust = new Label("Bust!");
            bust.setTextFill(Color.RED);
            diileriKaardidHBox.getChildren().add(bust);
        }

        // Diileril blackjack kui on 2 kaarti ja kokku 21
        else if (diileriTulemus == 21 && diiler.getKäsi().getKaardid().size() == 2) {
            diilerilBlackjack = true;

            Label blackjack = new Label("Blackjack!");
            blackjack.setTextFill(Color.GREEN);
            diileriKaardidHBox.getChildren().add(blackjack);
        }

        // Mängijate tulemused
        for (Mängija m : mängijadList) {
            // Väljas mängijaid ei kontrolli
            if (m.getSeis() == MängijaSeis.VÄLJAS)
                continue;

            // Kõik mängija käed läbi
            for (Käsi käsi : m.getKäed()) {
                // nimi + kaardid
                HBox mängijaHBox = new HBox();

                // lisa mängija nimi
                Label nimi = new Label(m.getNimi() + ": ");
                mängijaHBox.getChildren().add(nimi);

                // lisa käe kaardid
                for (Label kaart : käsi.getKaardidLabelid()) {
                    kaart.setText(kaart.getText() + " ");
                    mängijaHBox.getChildren().add(kaart);
                }

                int käsiTulemus = käsi.summa();

                // Kui käsi on bust
                if (käsi.getSeis() == KäsiSeis.BUST) {
                    kaotajadVBox.getChildren().add(mängijaHBox);
                    m.lisaKrediit(-käsi.getPanus()); // 1:1 kaotus
                }

                // Kui käsi on blackjack
                else if (käsiTulemus == 21 && käsi.getKaardid().size() == 2) {
                    // Blackjacki saab olla siis kui ei ole splititud
                    if (m.getKäed().size() == 1) {
                        // Viik, kui diileril on ka blackjack
                        if (diilerilBlackjack)
                            viikVBox.getChildren().add(mängijaHBox);

                        // Muidu võit
                        else {
                            võitjadVBox.getChildren().add(mängijaHBox);
                            m.lisaKrediit(käsi.getPanus() * 3 / 2);
                        }
                    }

                    // Kui mängija on splittinud siis tavaline võit
                    else {
                        võitjadVBox.getChildren().add(mängijaHBox);
                        m.lisaKrediit(käsi.getPanus()); // 1:1 võit
                    }
                }

                // Kui diiler bust siis kõik standijad võidavad
                else if (diileriTulemus > 21) {
                    võitjadVBox.getChildren().add(mängijaHBox);
                    m.lisaKrediit(käsi.getPanus()); // 1:1 võit
                }

                else {
                    // Võrdle käe tulemust diileri omaga
                    switch (Integer.compare(käsiTulemus, diileriTulemus)) {
                        // Mängijal on väiksem summa
                        case -1 -> {
                            kaotajadVBox.getChildren().add(mängijaHBox);
                            m.lisaKrediit(-käsi.getPanus()); // 1:1 kaotus
                        }

                        // Mängijal on diileriga sama summa
                        case 0 -> {
                            // Kui diileril blackjack ja mängijal ei ole, siis mängija kaotab
                            if (diilerilBlackjack) {
                                kaotajadVBox.getChildren().add(mängijaHBox);
                                m.lisaKrediit(-käsi.getPanus()); // 1:1 kaotus
                            }
                            viikVBox.getChildren().add(mängijaHBox);
                        }

                        // Mängijal on suurem summa
                        case 1 -> {
                            võitjadVBox.getChildren().add(mängijaHBox);
                            m.lisaKrediit(käsi.getPanus()); // 1:1 võit
                        }
                    }
                }
                käsi.setPanus(0); // Peale käe võidu/kaotuse arvutamist ja tasustamist eemaldame sellelt panuse
            }
//            m.setPanus(0); // Mängija panus nulliks
        }
    }

    /**
     * Uus mäng nupp. Viib tagasi peamenüüsse.
     */
    public void uusMäng() {
        VaateVahetaja.vaheta(Vaade.PEAMENÜÜ);
    }

    /**
     * Jätka nupp. Jätkab mängu samade mängijate ja tulemustega.
     */
    public void jätka() {
        MängKontroller mängKontroller = VaateVahetaja.vaheta(Vaade.MÄNG);

        assert mängKontroller != null;
        mängKontroller.setMängijad(mängijadList);
        mängKontroller.mängijadInit();
        mängKontroller.mängijadMustaks();
        mängKontroller.küsiPanuseid();
        mängKontroller.edetabel();
    }

    /**
     * Välju nupp. Sulgeb programmi.
     */
    public void välju() {
        Main.sulge();
    }

    public void setMängijadList(List<Mängija> mängijadList) {
        this.mängijadList = mängijadList;
    }

    public void setDiiler(Diiler diiler) {
        this.diiler = diiler;
    }

    public Button getJätkaNupp() {
        return jätkaNupp;
    }
}
