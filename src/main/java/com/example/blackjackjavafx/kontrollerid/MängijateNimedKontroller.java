package com.example.blackjackjavafx.kontrollerid;

import com.example.blackjackjavafx.Mängija;
import com.example.blackjackjavafx.Vaade;
import com.example.blackjackjavafx.VaateVahetaja;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class MängijateNimedKontroller {

    @FXML
    private VBox nimedVbox;

    public void genereeriTekstiväljad(int arv) {
        for (int i = 0; i < arv; i++) {
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            Text text = new Text("Mängija "+(i+1)+" nimi:");
            TextField textField = new TextField();
            textField.setId("mängija"+(i+1)+"nimi");
//            textField.setOnAction(event -> {}); // TODO: nimede väljal Enter võiks viia järgmise välja/edasi nuppu juurde
            HBox.setHgrow(textField, Priority.ALWAYS);
            hbox.getChildren().addAll(text, textField);
            nimedVbox.getChildren().add(hbox);
        }
    }

    public void tagasi() {
        VaateVahetaja.vaheta(Vaade.PEAMENÜÜ);
    }

    public void edasi() {
        List<Mängija> mängijad = new ArrayList<>();
        for (int i = 0; i < nimedVbox.getChildren().size(); i++) {
            String nimi = ((TextField) VaateVahetaja.getStseen().lookup("#mängija"+(i+1)+"nimi")).getText();
            if (nimi.equals("")) {
                nimi = "Mängija "+(i+1);
            }
            mängijad.add(new Mängija(nimi, 300));
        }
        MängKontroller mängKontroller = VaateVahetaja.vaheta(Vaade.MÄNG);
        mängKontroller.setMängijad(mängijad);
        mängKontroller.mängijadInit();
        mängKontroller.küsiPanuseid();
        mängKontroller.edetabelInit();
    }
}
