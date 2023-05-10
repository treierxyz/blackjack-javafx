package xyz.treier.blackjackjavafx.kontrollerid;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import xyz.treier.blackjackjavafx.Mängija;
import xyz.treier.blackjackjavafx.MängijaSeis;
import xyz.treier.blackjackjavafx.Vaade;
import xyz.treier.blackjackjavafx.VaateVahetaja;
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

    private List<Mängija> mängijadList;
    private Mängija diiler;

    public void lõpuTabel() {
        for (Label kaart : diiler.getKäsi().getKaardidLabelid()) {
            kaart.setText(kaart.getText() + " ");
            diileriKaardidHBox.getChildren().add(kaart);
        }

        switch(Integer.compare(diiler.getKäsi().summa(), 21)) {
            case 0 -> {
                Text blackjack = new Text("(Blackjack!)");
                blackjack.setFont(new Font(16));
                diileriKaardidHBox.getChildren().add(blackjack);
            }
            case 1 -> {
                Text bust = new Text("(Bust!)");
                bust.setFont(new Font(16));
                diileriKaardidHBox.getChildren().add(bust);
            }
        }

        for (Mängija m : mängijadList) {
            MängijaSeis seis = m.getSeis();
            Text nimi = new Text(m.getNimi());
            nimi.setFont(new Font(16));

            switch(seis) {
                // Kaotajad
                case BUST -> {
                    kaotajadVBox.getChildren().add(nimi);
                    m.lisaKrediit(-m.getPanus());
                }

                case STAND -> {
                    // Kui diiler bust siis kõik standijad võidavad
                    if (diiler.getKäsi().summa() > 21) {
                        võitjadVBox.getChildren().add(nimi);
                        m.lisaKrediit(m.getPanus());
                        break;
                    }

                    // Võrdle mängija tulemust diileri omaga
                    int tulemus = m.getKäsi().summa();
                    switch (Integer.compare(tulemus, diiler.getKäsi().summa())) {
                        case -1 -> {
                            kaotajadVBox.getChildren().add(nimi);
                            m.lisaKrediit(-m.getPanus());
                        }
                        case 0 -> viikVBox.getChildren().add(nimi);
                        case 1 -> {
                            võitjadVBox.getChildren().add(nimi);
                            m.lisaKrediit(m.getPanus());
                        }
                    }
                }
            }
            m.setPanus(0); // Mängija panus nulliks
        }
    }

    public void uusMäng() {
        VaateVahetaja.vaheta(Vaade.PEAMENÜÜ);
    }

    public void jätka() {
        MängKontroller mängKontroller = VaateVahetaja.vaheta(Vaade.MÄNG);
        mängKontroller.setMängijad(mängijadList);
        mängKontroller.mängijadInit();
        mängKontroller.mängijadMustaks();
        mängKontroller.küsiPanuseid();
        mängKontroller.edetabel();
    }

    public void välju() {
        Platform.exit();
    }

    public void setMängijadList(List<Mängija> mängijadList) {
        this.mängijadList = mängijadList;
    }

    public void setDiiler(Mängija diiler) {
        this.diiler = diiler;
    }
}
