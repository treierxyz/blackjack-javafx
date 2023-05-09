package xyz.treier.blackjackjavafx.kontrollerid;

import xyz.treier.blackjackjavafx.Vaade;
import xyz.treier.blackjackjavafx.VaateVahetaja;
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
