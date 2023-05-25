package xyz.treier.blackjackjavafx.kontrollerid;

import xyz.treier.blackjackjavafx.Main;
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

    /**
     * Välju nupp. Sulgeb programmi.
     */
    public void välju() {
        Main.sulge();
    }
}
