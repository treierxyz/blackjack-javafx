package xyz.treier.blackjackjavafx.kontrollerid;

import xyz.treier.blackjackjavafx.Vaade;
import xyz.treier.blackjackjavafx.VaateVahetaja;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class PeamenüüKontroller {

    /**
     * Leiab mängijate arvu peamenüüs vajutatud nupult.
     * @param event mis nupule vajutatud
     */
    public void mängijaid(ActionEvent event) {
        int mängijaid = Integer.parseInt(((Button) event.getSource()).getText());

        // Kuva mängijate nimede sisestuse vaade.
        MängijateNimedKontroller mängijateNimedKontroller = VaateVahetaja.vaheta(Vaade.MÄNGIJATENIMED);
        mängijateNimedKontroller.genereeriTekstiväljad(mängijaid);
    }

    /**
     * Näita abi vaadet.
     */
    public void abi() {
        VaateVahetaja.vaheta(Vaade.ABI);
    }
}
