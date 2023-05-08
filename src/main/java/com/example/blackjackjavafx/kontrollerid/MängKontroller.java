package com.example.blackjackjavafx.kontrollerid;

import com.example.blackjackjavafx.Kaardipakk;
import com.example.blackjackjavafx.Kaart;
import com.example.blackjackjavafx.Mängija;
import com.example.blackjackjavafx.MängijaSeis;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.List;

public class MängKontroller {

    @FXML
    private VBox edetabel;
    @FXML
    private HBox mängijadHBox;
    @FXML
    private HBox diileriKaardid;

    @FXML
    private ButtonBar actionBar;

    private List<Mängija> mängijadList;

    public void edetabelInit() {
        for (Mängija m : mängijadList) {
            Label nimi = new Label(m.getNimi());
            nimi.setTextOverrun(OverrunStyle.CENTER_ELLIPSIS); // miks ei tööta???

            Label krediit = new Label();
            krediit.textProperty().bind(m.krediitProperty().asString());

            BorderPane borderPane = new BorderPane();
            borderPane.setLeft(nimi);
            borderPane.setRight(krediit);
            BorderPane.setMargin(nimi, new Insets(0,25,0,0));

            VBox.setMargin(borderPane, new Insets(0,5,0,5));

            edetabel.setSpacing(5);
            edetabel.getChildren().add(borderPane);
        }
    }

    public void küsiPanuseid() {
        for (Mängija mängija : mängijadList) {
            mängija.getMängijaHbox().getChildren().clear();
            TextField panus = new TextField();
            panus.setPromptText("Panus");
            panus.setPrefWidth(70.0);
            Button ok = new Button("OK");
            ok.disableProperty().bind(panus.textProperty().isEmpty());
            ok.disableProperty().bind(panus.disabledProperty());
            ok.setOnMouseClicked(event -> {
                panus.setDisable(true);
                mängija.setSeis(MängijaSeis.PANUS_VALMIS);
                if (panusedTehtud()) {
                    actionBar.disableProperty().set(false);
                    jagaKaardid();
                }
            });
            mängija.getMängijaHbox().getChildren().addAll(panus, ok);
        }
    }

    public boolean panusedTehtud() {
        for (Mängija mängija : mängijadList) {
            if (mängija.getSeis() != MängijaSeis.PANUS_VALMIS) {
                return false;
            }
        }
        return true;
    }

    public void jagaKaardid() {
        // Testiks
        Kaardipakk pakk = new Kaardipakk(2);
        for (Mängija mängija : mängijadList) {
            mängija.getMängijaHbox().getChildren().clear();
            // Jaga paar kaarti
            for (int i = 0; i < 2; i++)
                mängija.getKäsi().lisaKaart(pakk.suvaline());

            for (Kaart kaart : mängija.getKäsi().getKaardid()) {
                Text kaartTekst = new Text(kaart.toString());
                kaartTekst.setFont(new Font(16));
                mängija.getMängijaHbox().getChildren().add(kaartTekst);
            }
        }
    }

    public void mängijadInit() {
        mängijadHBox.getChildren().clear();
        mängijadHBox.setSpacing(25);

        for (Mängija m : mängijadList) {
            FontIcon icon = new FontIcon("mdmz-person_outline");
            icon.setIconSize(42);

            Text nimi = new Text(m.getNimi());
            nimi.setFont(new Font(20));

            VBox vBox = new VBox(icon, nimi);
            vBox.setAlignment(Pos.CENTER);

            // Kuva mängija hbox
            HBox hBox = m.getMängijaHbox();
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(5);

            vBox.getChildren().add(hBox);
            mängijadHBox.getChildren().add(vBox);
        }
    }

    public void standNupp() {

    }
    public void hitNupp() {

    }
    public void doubleNupp() {

    }

    public void initialize() {
    }

    public void setMängijad(List<Mängija> mängijad) {
        mängijadList = mängijad;
    }

}
