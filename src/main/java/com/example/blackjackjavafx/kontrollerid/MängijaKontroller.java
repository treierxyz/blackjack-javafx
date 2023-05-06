package com.example.blackjackjavafx.kontrollerid;

import com.example.blackjackjavafx.Vaade;
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

        MängijaNimedKontroller mängijaNimedKontroller = VaateVahetaja.vaheta(Vaade.MÄNGIJANIMED);
        mängijaNimedKontroller.genereeriTekstiväljad(mängijaid);
    }
}
