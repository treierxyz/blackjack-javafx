package com.example.blackjackjavafx.kontrollerid;

import com.example.blackjackjavafx.Vaade;
import com.example.blackjackjavafx.VaateVahetaja;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class PeamenüüKontroller {

    public void mängijaid(ActionEvent event) {
        int mängijaid = Integer.parseInt(((Button) event.getSource()).getText());

        MängijateNimedKontroller mängijateNimedKontroller = VaateVahetaja.vaheta(Vaade.MÄNGIJATENIMED);
        mängijateNimedKontroller.genereeriTekstiväljad(mängijaid);
    }

    public void abi() {
        VaateVahetaja.vaheta(Vaade.ABI);
    }
}
