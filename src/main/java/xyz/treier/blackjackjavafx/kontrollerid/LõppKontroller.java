package xyz.treier.blackjackjavafx.kontrollerid;

import xyz.treier.blackjackjavafx.Mängija;
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

    public void lõpuEdetabel() {

    }

    public void uusMäng() {
        VaateVahetaja.vaheta(Vaade.PEAMENÜÜ);
    }

    public void välju() {
        Platform.exit();
    }

    public void setMängijadList(List<Mängija> mängijadList) {
        this.mängijadList = mängijadList;
    }
}
