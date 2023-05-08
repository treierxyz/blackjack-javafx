package com.example.blackjackjavafx.kontrollerid;

import com.example.blackjackjavafx.Kaardipakk;
import com.example.blackjackjavafx.Kaart;
import com.example.blackjackjavafx.Mängija;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.ArrayList;
import java.util.List;

public class MängKontroller {

    @FXML
    private ListView mängijadVaade;
    @FXML
    private HBox mängijadHBox;
    @FXML
    private HBox diileriKaardid;
    private List<Mängija> mängijadList;

    public void mängijadPaan() {
        for (Mängija m : mängijadList) {
            HBox hBox = new HBox(new Text(m.getNimi()), new Text(Integer.toString(m.getKrediit())));
            hBox.setSpacing(100);
            mängijadVaade.getItems().add(hBox);
        }
    }

    public void lisaMängijad() {
        // Testiks
        Kaardipakk pakk = new Kaardipakk(2);

        mängijadHBox.setSpacing(25);

        for (Mängija m : mängijadList) {
            FontIcon icon = new FontIcon("mdmz-person_outline");
            icon.setIconSize(42);

            Text nimi = new Text(m.getNimi());
            nimi.setFont(new Font(20));

            VBox vBox = new VBox(icon, nimi);
            vBox.setAlignment(Pos.CENTER);

            // Testiks jaga paar kaarti
            for (int i = 0; i < 2; i++)
                m.getKäsi().lisaKaart(pakk.suvaline());

            // Kuva kaardid
            HBox kaardid = new HBox();
            kaardid.setAlignment(Pos.CENTER);
            kaardid.setSpacing(5);
            for (Kaart k : m.getKäsi().getKaardid()) {
                Text kaart = new Text(k.toString());
                kaart.setFont(new Font(16));
                kaardid.getChildren().add(kaart);
            }

            vBox.getChildren().add(kaardid);
            mängijadHBox.getChildren().add(vBox);
        }
    }

    public void initialize() {
    }

    public void setMängijad(List<Mängija> mängijad) {
        mängijadList = mängijad;
    }

}
