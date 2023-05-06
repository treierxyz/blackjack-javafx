package com.example.blackjackjavafx.kontrollerid;

import com.example.blackjackjavafx.Mängija;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class MängKontroller {

    @FXML
    private ListView mängijadVaade;
    private List<Mängija> mängijadList;

    public void mängijadPaan() {
        for (Mängija m : mängijadList) {
            HBox hBox = new HBox(new Text(m.getNimi()), new Text(Integer.toString(m.getKrediit())));
            hBox.setSpacing(100);
            mängijadVaade.getItems().add(hBox);
        }
    }

    public void initialize() {
    }

    public void setMängijad(List<Mängija> mängijad) {
        mängijadList = mängijad;
    }

}
