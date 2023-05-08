package com.example.blackjackjavafx.kontrollerid;

import com.example.blackjackjavafx.Vaade;
import com.example.blackjackjavafx.VaateVahetaja;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

import java.io.IOException;

public class PeamenüüKontroller {

    public void mängijaid(ActionEvent event) throws IOException {
        int mängijaid = Integer.parseInt(((Button) event.getSource()).getText());

        MängijateNimedKontroller mängijateNimedKontroller = VaateVahetaja.vaheta(Vaade.MÄNGIJATENIMED);
        mängijateNimedKontroller.genereeriTekstiväljad(mängijaid);
    }

    public void abi() {
        VaateVahetaja.vaheta(Vaade.ABI);
    }
}
