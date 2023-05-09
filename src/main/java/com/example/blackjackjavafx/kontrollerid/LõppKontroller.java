package com.example.blackjackjavafx.kontrollerid;

import com.example.blackjackjavafx.Mängija;
import com.example.blackjackjavafx.MängijaSeis;
import com.example.blackjackjavafx.Vaade;
import com.example.blackjackjavafx.VaateVahetaja;
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
