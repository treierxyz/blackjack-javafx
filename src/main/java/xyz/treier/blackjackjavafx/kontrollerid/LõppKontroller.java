package xyz.treier.blackjackjavafx.kontrollerid;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import xyz.treier.blackjackjavafx.*;
import javafx.application.Platform;
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
    private Mängija diiler;

    /**
     * Kuvab mängu lõpus võitjad, kaotajad ja viiki jäänud mängijad ja nende kaardid.
     */
    public void lõpuTabel() {
        // Näita diileri kaardid
        for (Label kaart : diiler.getKäsi().getKaardidLabelid()) {
            kaart.setText(kaart.getText() + " ");
            diileriKaardidHBox.getChildren().add(kaart);
        }

        boolean diilerilBlackjack = false;
        // Lisa diileri tulemusele kas blackjack või bust kiri
        switch (Integer.compare(diiler.getKäsi().summa(), 21)) {
            case 0 -> {
                // Kui on üle kahe kaardi siis blackjack olla ei saa
                if (diiler.getKäsi().getKaardid().size() > 2)
                    break;

                diilerilBlackjack = true;
                Label blackjack = new Label("Blackjack!");
                blackjack.setFont(new Font(16));
                blackjack.setTextFill(Color.GREEN);
                diileriKaardidHBox.getChildren().add(blackjack);
            }
            case 1 -> {
                Label bust = new Label("Bust!");
                bust.setFont(new Font(16));
                bust.setTextFill(Color.RED);
                diileriKaardidHBox.getChildren().add(bust);
            }
        }

        // Mängija tulemus
        for (Mängija m : mängijadList) {
            MängijaSeis seis = m.getSeis();

            HBox mängijaHBox = new HBox(); // nimi + kaardid

            // Mängija nimi
            Label nimi = new Label(m.getNimi() + ": ");
            nimi.setFont(new Font(16));
            mängijaHBox.getChildren().add(nimi);

            // lisa mängija kaardid ekraanile
            for (Label kaart : m.getKäsi().getKaardidLabelid()) {
                kaart.setText(kaart.getText() + " ");
                mängijaHBox.getChildren().add(kaart);
            }

            switch (seis) {
                // Kaotajad
                case BUST -> {
                    kaotajadVBox.getChildren().add(mängijaHBox);
                    m.lisaKrediit(-m.getPanus()); // 1:1 kaotus
                }

                case STAND -> {
                    int mängijaTulemus = m.getKäsi().summa();
                    int diileriTulemus = diiler.getKäsi().summa();

                    // Kui mängijal on blackjack
                    if (mängijaTulemus == 21 && m.getKäsi().getKaardid().size() == 2) {
                        // Viik, kui diileril on ka blackjack
                        if (diilerilBlackjack) {
                            viikVBox.getChildren().add(mängijaHBox);
                            m.setPanus(0);
                            continue;
                        }
                        // Võit
                        võitjadVBox.getChildren().add(mängijaHBox);
                        m.lisaKrediit((int) Math.round(m.getPanus() * 3.0 / 2.0)); // 3:2 võit TODO: kas peaks ikka ümardama?
                        m.setPanus(0);
                        continue;
                    }

                    // Kui diiler bust siis kõik standijad võidavad
                    if (diileriTulemus > 21) {
                        võitjadVBox.getChildren().add(mängijaHBox);
                        m.lisaKrediit(m.getPanus()); // 1:1 võit
                        break;
                    }

                    // Võrdle mängija tulemust diileri omaga
                    switch (Integer.compare(mängijaTulemus, diileriTulemus)) {
                        case -1 -> {
                            kaotajadVBox.getChildren().add(mängijaHBox);
                            m.lisaKrediit(-m.getPanus()); // 1:1 kaotus
                        }
                        case 0 -> {
                            // Kui diileril blackjack ja mängijal ei ole, siis mängija kaotab
                            if (diilerilBlackjack) {
                                kaotajadVBox.getChildren().add(mängijaHBox);
                                continue;
                            }
                            viikVBox.getChildren().add(mängijaHBox);
                        }
                        case 1 -> {
                            võitjadVBox.getChildren().add(mängijaHBox);
                            m.lisaKrediit(m.getPanus()); // 1:1 võit
                        }
                    }
                }
            }
            m.setPanus(0); // Mängija panus nulliks
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
        Platform.exit();
    }

    public void setMängijadList(List<Mängija> mängijadList) {
        this.mängijadList = mängijadList;
    }

    public void setDiiler(Mängija diiler) {
        this.diiler = diiler;
    }

    public Button getJätkaNupp() {
        return jätkaNupp;
    }
}
