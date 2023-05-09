package com.example.blackjackjavafx.kontrollerid;

import com.example.blackjackjavafx.*;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
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
    private FlowPane mängijadFlow;
    @FXML
    private HBox diileriKaardid;
    @FXML
    private ButtonBar actionBar;

    private List<Mängija> mängijadList;
    private List<Mängija> lõpetanudList;
    private Mängija kelleKäik;
    private Kaardipakk mänguPakk;
    private Mäng mäng;

    public void edetabelInit() {
        for (Mängija m : mängijadList) {
            Label nimi = new Label(m.getNimi());
            nimi.setTextOverrun(OverrunStyle.CENTER_ELLIPSIS); // miks ei tööta???

            Label krediit = new Label();
            Label panus = new Label();
            krediit.textProperty().bind(m.krediitProperty().asString());
            panus.textProperty().bind(Bindings.when(m.panusProperty().isEqualTo(0)).then("").otherwise(Bindings.concat("-",m.panusProperty().asString())));
            panus.setTextFill(Paint.valueOf("a89f23"));
            HBox krediitbox = new HBox(krediit,panus);

            BorderPane borderPane = new BorderPane();
            borderPane.setLeft(nimi);
            borderPane.setRight(krediitbox);
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
            panus.setOnAction(event -> ok.fire());
            ok.setOnAction(event -> {
                try {
                    int panusKogus = Integer.parseInt(panus.getText());
                    if (panusKogus > mängija.getKrediit() || panusKogus <= 0) return;
                    mängija.setPanus(panusKogus);
                } catch (NumberFormatException e) {
                    return;
                }
                panus.setDisable(true);

                mängija.setSeis(MängijaSeis.PANUS_VALMIS);
                if (panusedTehtud()) {
                    actionBar.disableProperty().set(false);
                    mänguPakk = new Kaardipakk(mängijadList.size());
                    jagaKaardid();
                    mängijadHalliks();
                    try {
                        mäng = new Mäng(this);
                    } catch (MängijadOtsasErind e) {
                        throw new RuntimeException(e);
                    }
                    mäng.alusta();
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

    public void mängijadHalliks() {
        for (Mängija m : mängijadList) {
            m.setSeis(MängijaSeis.OOTAB);
        }
    }

    public void jagaKaardid() {
        // Testiks
        // Diiler
        Mängija diiler = new Mängija("Diiler", 1000);
        for (int i = 0; i < 2; i++)
            diiler.getKäsi().lisaKaart(mänguPakk.suvaline());
        // Näita ühte diileri kaarti
        diileriKaardid.getChildren().add(diiler.getKäsi().getKaardidLabelid().get(0));

        // Teised diileri kaardid küsimärgid
        for (int i = 1; i < diiler.getKäsi().getKaardid().size(); i++) {
            Text küsimärk = new Text(" ? ");
            küsimärk.setFont(new Font(16));
            diileriKaardid.getChildren().add(küsimärk);
        }

        for (Mängija mängija : mängijadList) {
            mängija.getMängijaHbox().getChildren().clear();
            // Jaga paar kaarti
            for (int i = 0; i < 2; i++)
                mängija.getKäsi().lisaKaart(mänguPakk.suvaline());

            for (Label kaart : mängija.getKäsi().getKaardidLabelid()) {
                mängija.getMängijaHbox().getChildren().add(kaart);
            }
        }
    }

    public void mängijadInit() {
        mängijadFlow.getChildren().clear();

        for (Mängija m : mängijadList) {
            FontIcon icon = new FontIcon("mdmz-person_outline");
            icon.setIconSize(42);

            Text nimi = new Text(m.getNimi());
            nimi.setId(m.getNimi());
            nimi.setFont(new Font(20));

            VBox vBox = new VBox(icon, nimi);
            vBox.setAlignment(Pos.CENTER);

            // Kuva mängija hbox
            HBox hBox = m.getMängijaHbox();
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(5);

            vBox.getChildren().add(hBox);
            m.getMängijaHbox().getParent().opacityProperty().bind(m.läbipaistvusProperty());
            mängijadFlow.getChildren().add(vBox);
        }
    }

    public void standNupp() {
        kelleKäik.setSeis(MängijaSeis.STAND);
        lõpetanudList.add(kelleKäik);

        mäng.järgmineMängija();
    }

    public void hitNupp() {
        Käsi käsi = kelleKäik.getKäsi();
        Kaart uusKaart = mänguPakk.suvaline();
        käsi.lisaKaart(uusKaart);

        // Lisa kaart ekraanile
        kelleKäik.getMängijaHbox().getChildren().add(uusKaart.kaartLabel());

        System.out.println("Kaartide summa: " + käsi.summa());

        switch (Integer.compare(käsi.summa(), 21)) {
            case -1 -> kelleKäik.setSeis(MängijaSeis.OOTAB);
            case 0 -> {
                System.out.println("21 käes!");
                kelleKäik.setSeis(MängijaSeis.STAND);
                lõpetanudList.add(kelleKäik);
            }
            case 1 -> {
                System.out.println("Bust! Oled mängust väljas");
                kelleKäik.setSeis(MängijaSeis.BUST);
                lõpetanudList.add(kelleKäik);
                kelleKäik.strikeThroughNimi();
            }
        }

        mäng.järgmineMängija();
    }

    public void doubleNupp() {
        if (kelleKäik.getKrediit() < kelleKäik.getPanus()) return; // kui mängijal ei ole krediiti et doubleida

        kelleKäik.setPanus(kelleKäik.getPanus()*2); // kahekordista panust
        System.out.println("Panus on nüüd "+kelleKäik.getPanus());
        Kaart uusKaart = mänguPakk.suvaline();
        System.out.println("Tuli kaart " + uusKaart.toString());
        Käsi käsi = kelleKäik.getKäsi();
        käsi.lisaKaart(uusKaart);

        // Lisa kaart ekraanile
        Text kaartTekst = new Text(uusKaart.toString());
        kaartTekst.setFont(new Font(16));
        kelleKäik.getMängijaHbox().getChildren().add(kaartTekst);

        switch (Integer.compare(käsi.summa(), 21)) {
            case -1 -> kelleKäik.setSeis(MängijaSeis.STAND);
            case 0 -> {
                System.out.println("21 käes!");
                kelleKäik.setSeis(MängijaSeis.STAND);
            }
            case 1 -> {
                System.out.println("Bust! Oled mängust väljas");
                kelleKäik.setSeis(MängijaSeis.BUST);
                kelleKäik.strikeThroughNimi();
            }
        }

        lõpetanudList.add(kelleKäik);
        System.out.println("Lõpetasid mängu tulemusega " + käsi.summa());

        mäng.järgmineMängija();
    }

    public void initialize() {
    }

    public void setMängijad(List<Mängija> mängijad) {
        mängijadList = mängijad;
    }

    public List<Mängija> getMängijad() {
        return this.mängijadList;
    }

    public void setKelleKäik(Mängija kelleKäik) {
        this.kelleKäik = kelleKäik;
    }

    public Mängija getKelleKäik() {
        return kelleKäik;
    }

    public void setLõpetanudList(List<Mängija> lõpetanudList) {
        this.lõpetanudList = lõpetanudList;
    }

    public List<Mängija> getLõpetanudList() {
        return lõpetanudList;
    }
}
