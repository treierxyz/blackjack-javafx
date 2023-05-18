package xyz.treier.blackjackjavafx.kontrollerid;

import javafx.scene.control.Label;
import xyz.treier.blackjackjavafx.Mängija;
import xyz.treier.blackjackjavafx.Vaade;
import xyz.treier.blackjackjavafx.VaateVahetaja;
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

    /**
     * Genereerib mängijate nimede sisestamise kastid.
     * @param arv Genereeritavate kastide arv (mänmgijate arv)
     */
    public void genereeriTekstiväljad(int arv) {
        for (int i = 0; i < arv; i++) {
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            Label label = new Label("Mängija "+(i+1)+":");
            label.setPrefWidth(75); // kohutav hack selleks et nimeväljad ei oleks paari piksli võrra erineva laiusega
            TextField textField = new TextField();
            textField.setId("mängija"+(i+1)+"nimi");
//            textField.setOnAction(event -> {}); // TODO: nimede väljal Enter võiks viia järgmise välja/edasi nuppu juurde
            HBox.setHgrow(textField, Priority.ALWAYS);
            hbox.getChildren().addAll(label, textField);
            nimedVbox.getChildren().add(hbox);
        }
    }

    /**
     * Tagasi nupp. Viib tagasi peamenüüsse.
     */
    public void tagasi() {
        VaateVahetaja.vaheta(Vaade.PEAMENÜÜ);
    }

    /**
     * Edasi nupp. Viib edasi mängu.
     */
    public void edasi() {
        List<Mängija> mängijad = new ArrayList<>();
        // Leiab mängijate nimed. Kui nime ei sisestatud, saab mägnija nimeks "Mängija x", kus x on järjekorranumber.
        for (int i = 0; i < nimedVbox.getChildren().size(); i++) {
            String nimi = ((TextField) VaateVahetaja.getStseen().lookup("#mängija"+(i+1)+"nimi")).getText();
            if (nimi.equals("")) {
                nimi = "Mängija "+(i+1);
            }
            mängijad.add(new Mängija(nimi, 300));
        }
        // Mängu initsialiseerimine
        MängKontroller mängKontroller = VaateVahetaja.vaheta(Vaade.MÄNG);
        mängKontroller.setMängijad(mängijad);
        mängKontroller.mängijadInit();
        mängKontroller.küsiPanuseid();
        mängKontroller.edetabel();
    }
}
