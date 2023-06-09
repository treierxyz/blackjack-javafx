package xyz.treier.blackjackjavafx.kontrollerid;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import xyz.treier.blackjackjavafx.Mängija;
import xyz.treier.blackjackjavafx.Vaade;
import xyz.treier.blackjackjavafx.VaateVahetaja;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class MängijateNimedKontroller {

    @FXML
    private VBox nimedVbox;

    @FXML
    private Label sisestaPrompt;

    private final int maxNimiPikkus = 20; // Loodetavasti 20 on küllalt pikk nime jaoks

    /**
     * Genereerib mängijate nimede sisestamise kastid.
     * @param arv Genereeritavate kastide arv (mänmgijate arv)
     */
    public void genereeriTekstiväljad(int arv) {
        nimedVbox.getStyleClass().add("mangijanimi");
        if (arv == 1) {
            sisestaPrompt.setText("Sisesta nimi");
        }
        for (int i = 0; i < arv; i++) {
            HBox hbox = new HBox();
            hbox.setSpacing(10);
            hbox.setAlignment(Pos.CENTER);
            Label label = new Label("Mängija "+(i+1)+":");
            label.setPrefWidth(90); // kohutav hack selleks et nimeväljad ei oleks paari piksli võrra erineva laiusega
            TextField textField = new TextField();
            textField.textProperty().addListener(((observable, oldValue, newValue) -> {
                if (newValue.length() > maxNimiPikkus) {
                    textField.setText(oldValue);
                }
            }));
            textField.setId("mängija"+(i+1)+"nimi");
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
        // Leiab mängijate nimed. Kui nime ei sisestatud, saab mägnija nimeks "Mängija n", kus n on järjekorranumber.
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
