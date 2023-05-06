package com.example.blackjackjavafx.kontrollerid;

import com.example.blackjackjavafx.VaateVahetaja;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;

import java.io.IOException;

public class MängijaKontroller {

    public void mängijaid(ActionEvent event) throws IOException {
        int mängijaid = Integer.parseInt(((Button) event.getSource()).getText());

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/blackjackjavafx/mängija-nimed.fxml"));
        Parent root = fxmlLoader.load();

        MängijaNimedKontroller mängijaNimedKontroller = fxmlLoader.getController();
        mängijaNimedKontroller.genereeriTekstiväljad(mängijaid);

        VaateVahetaja.getStseen().setRoot(root);
    }
}
