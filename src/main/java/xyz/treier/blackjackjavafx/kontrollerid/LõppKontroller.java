package xyz.treier.blackjackjavafx.kontrollerid;

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
    VBox võitjadVBox;
    @FXML
    VBox kaotajadVBox;
    @FXML
    VBox viikVBox;

    List<Mängija> mängijadList;
    Mängija diiler;

    public void lõpuEdetabel() {
        for (Mängija m : mängijadList) {
            MängijaSeis seis = m.getSeis();
            Text nimi = new Text(m.getNimi());
            nimi.setFont(new Font(16));

            switch(seis) {
                // Kaotajad
                case BUST -> {
                    kaotajadVBox.getChildren().add(nimi);
                    m.lisaKrediit(-m.getPanus());
                    m.setPanus(0);
                }

                case STAND -> {
                    // Kui diiler bust siis kõik standijad võidavad
                    if (diiler.getKäsi().summa() > 21) {
                        võitjadVBox.getChildren().add(nimi);
                        m.lisaKrediit(m.getPanus());
                        m.setPanus(0);
                        break;
                    }

                    int tulemus = m.getKäsi().summa();
                    System.out.println(m.getNimi() + ": " + tulemus);
                    System.out.println("Diiler: " + diiler.getKäsi().summa());
                    switch (Integer.compare(tulemus, diiler.getKäsi().summa())) {
                        case -1 -> {
                            kaotajadVBox.getChildren().add(nimi);
                            m.lisaKrediit(-m.getPanus());
                            m.setPanus(0);
                        }
                        case 0 -> {
                            viikVBox.getChildren().add(nimi);
                            m.setPanus(0);
                        }
                        case 1 -> {
                            võitjadVBox.getChildren().add(nimi);
                            m.lisaKrediit(m.getPanus());
                            m.setPanus(0);
                        }
                    }
                }
            }
        }

    }

    public void uusMäng() {
        VaateVahetaja.vaheta(Vaade.PEAMENÜÜ);
    }

    public void jätka() {
        MängKontroller mängKontroller = VaateVahetaja.vaheta(Vaade.MÄNG);
        // lisaMängijateVõidusumma();
        mängKontroller.setMängijad(mängijadList);
        mängKontroller.mängijadInit();
        mängKontroller.mängijadMustaks();
        mängKontroller.küsiPanuseid();
        mängKontroller.edetabel();
    }

    public void välju() {
        Platform.exit();
    }

    public void lisaMängijateVõidusumma() {
        for (Mängija m : mängijadList) {
            m.lisaKrediit(m.getKrediit() - m.getPanus());
        }
    }

    public void setMängijadList(List<Mängija> mängijadList) {
        this.mängijadList = mängijadList;
    }

    public void setDiiler(Mängija diiler) {
        this.diiler = diiler;
    }
}
