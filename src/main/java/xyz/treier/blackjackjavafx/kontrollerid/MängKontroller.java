package xyz.treier.blackjackjavafx.kontrollerid;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.kordamp.ikonli.javafx.FontIcon;
import xyz.treier.blackjackjavafx.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MängKontroller {

    @FXML
    private VBox edetabel;
    @FXML
    private FlowPane mängijadFlow;
    @FXML
    private HBox diileriKaardid;
    @FXML
    private ButtonBar actionBar;
    @FXML
    private Parent lõppEkraan;
    @FXML
    private LõppKontroller lõppEkraanController; // siin peab olema formaadis id+"Controller", muidu ei tööta
    @FXML
    private BorderPane mängEkraan;

    private List<Mängija> mängijadList;
    private List<Mängija> lõpetanudList;
    private Mängija kelleKäik;
    private Mängija diiler;
    private Kaardipakk mänguPakk;
    private Mäng mäng;

    /**
     * Kutsutakse iga kord kui on vaja edetabelit värskendada
     * Hetkel luuakse kõik elemendid uuesti, võibolla on võimalik kasutada ka getChildren().sort(), kuid see lahendus hetkel sobib
     */
    public void edetabel() {
        edetabel.getChildren().clear();
        List<Mängija> sorteeritudMängijadList = new ArrayList<>(mängijadList); // kloonitud list et mängijate järjekord mängus ei muutuks
        Collections.sort(sorteeritudMängijadList, Collections.reverseOrder());
        for (Mängija m : sorteeritudMängijadList) {
            Label nimi = new Label(m.getNimi());
            nimi.setTextOverrun(OverrunStyle.CENTER_ELLIPSIS); // TODO: miks overrun ei tööta???

            Label krediit = new Label();
            Label panus = new Label();
            krediit.textProperty().bind(m.krediitProperty().asString());
            panus.textProperty().bind(Bindings.when(m.panusProperty().isEqualTo(0)).then("").otherwise(Bindings.concat("-", m.panusProperty().asString())));
            panus.getStyleClass().add("panus");
            HBox krediitbox = new HBox(krediit, panus);

            BorderPane borderPane = new BorderPane();
            borderPane.setLeft(nimi);
            borderPane.setRight(krediitbox);
            BorderPane.setMargin(nimi, new Insets(0, 40, 0, 0));

            VBox.setMargin(borderPane, new Insets(0, 5, 0, 5));

            edetabel.setSpacing(5);
            edetabel.getChildren().add(borderPane);
        }
    }

    public void küsiPanuseid() {
        for (Mängija mängija : mängijadList) {
            mängija.getMängijaHbox().getChildren().clear();

            // Kui pole krediiti et alustada mängu
            if (mängija.getKrediit() == 0) {
                lõpetanudList.add(mängija);

                mängija.setSeis(MängijaSeis.VÄLJAS);
                mängija.strikeThroughNimi(); // Kriipsuta mängija nimi läbi

                // Kaartide asemel näita "VÄLJAS"
                Text väljas = new Text("VÄLJAS");
                väljas.setFont(new Font(16));
                mängija.getMängijaHbox().getChildren().add(väljas);

                continue;
            }

            TextField panus = new TextField();
            panus.setPromptText("Panus");
            panus.setPrefWidth(70.0);
            Button ok = new Button("OK");
            ok.disableProperty().bind(panus.textProperty().isEmpty());
            ok.disableProperty().bind(panus.disabledProperty());
            panus.setOnAction(event -> ok.fire());

            // OK nupule vajutades
            ok.setOnAction(event -> {
                // kontrolli, kas sisestatud panus sobib
                // Kui panus sobib, siis seab mängija panus, kui ei sobi, siis ei tee midagi
                try {
                    int panusKogus = Integer.parseInt(panus.getText());
                    if (panusKogus > mängija.getKrediit() || panusKogus <= 0) return;
                    mängija.setPanus(panusKogus);
                } catch (NumberFormatException e) {
                    return;
                }

                // Korrektse panuse sisestamisel keelab panuse sisestamise välja
                panus.setDisable(true);
                mängija.setSeis(MängijaSeis.PANUS_VALMIS);

                // Kui kõik on panused teinud, siis alusta mänguga
                if (panusedTehtud()) {
                    actionBar.disableProperty().set(false); // käigu nupud aktiivseks

                    // Uus kaardipakk. Pakkide arv võrdne mängijate arvuga, kuid minimaalselt 2.
                    mänguPakk = new Kaardipakk(Math.max(mängijadList.size(), 2));
                    jagaKaardid(); // jaga mängijatele kaardid
                    mängijadHalliks(); // mängijad halliks

                    // Alusta mängijate ringlusega
                    mäng = new Mäng(this);
                    mäng.järgmineMängija();
                }
            });

            // Lisa iga mängija puhul ekraanile panuse kast ja ok nupp
            mängija.getMängijaHbox().getChildren().addAll(panus, ok);
        }
    }

    /**
     * Kontrollib, kas kõik mängijad on oma panused teinud.
     * @return true, kui kõik panused on tehtud,
     *         false, kui ei ole.
     */
    public boolean panusedTehtud() {
        for (Mängija mängija : mängijadList) {
            // Kui mängija on väljas ehk krediit otsas, siis võib ta vahele jätta
            if (mängija.getSeis() == MängijaSeis.VÄLJAS)
                continue;

            // Kui mingilgi mängijal pole panus tehtud, siis tagasta false
            if (mängija.getSeis() != MängijaSeis.PANUS_VALMIS) {
                return false;
            }
        }
        return true;
    }

    /**
     * Teeb kõik mängijad mänguvaates halliks.
     */
    public void mängijadHalliks() {
        for (Mängija m : mängijadList) {
            m.setSeis(MängijaSeis.OOTAB);
        }
    }
    /**
     * Teeb kõik mängijad mänguvaates mustaks.
     */
    public void mängijadMustaks() {
        for (Mängija m : mängijadList) {
            m.setSeis(MängijaSeis.INIT);
        }
    }

    /**
     * Jagab diilerile ja mängijatele mängu alguses 2 kaarti.
     */
    public void jagaKaardid() {
        // Diiler
        Mängija diiler = new Mängija("Diiler", 1000);
        this.diiler = diiler;

        // Jaga diilerile 2 kaarti
        for (int i = 0; i < 2; i++)
            diiler.getKäsi().lisaKaart(mänguPakk.suvaline());
        // Näita ühte diileri kaarti
        diileriKaardid.getChildren().add(diiler.getKäsi().getKaardidLabelid().get(0));

        // Teised diileri kaardid küsimärgid
        for (int i = 1; i < diiler.getKäsi().getKaardid().size(); i++) {
            Label küsimärk = new Label(" ? ");
            küsimärk.setFont(new Font(16));
            diileriKaardid.getChildren().add(küsimärk);
        }

        // Jaga mängijatele
        for (Mängija mängija : mängijadList) {
            mängija.getKäsi().tühjendaKäsi();

            // 0 krediidiga mängijale kaarte ei jaga
            if (mängija.getKrediit() == 0)
                continue;

            mängija.getMängijaHbox().getChildren().clear();

            // Jaga 2 kaarti
            for (int i = 0; i < 2; i++)
                mängija.getKäsi().lisaKaart(mänguPakk.suvaline());

            // Kuva kaardid ekraanil
            for (Label kaart : mängija.getKäsi().getKaardidLabelid()) {
                mängija.getMängijaHbox().getChildren().add(kaart);
            }
        }
    }

    /**
     * Lisab mängijad ekraanile.
     */
    public void mängijadInit() {
        lõpetanudList = new ArrayList<>();
        mängijadFlow.getChildren().clear();

        for (Mängija m : mängijadList) {
            // Mängija ikoon
            FontIcon icon = new FontIcon("mdmz-person_outline");
            icon.setIconColor(Paint.valueOf("#F8F8F7"));
            icon.setIconSize(42);

            // Mängija nimi
            Label nimi = new Label(m.getNimi());
            nimi.setId(m.getNimi());
            nimi.setFont(new Font(20));

            VBox vBox = new VBox(icon, nimi);
            vBox.setAlignment(Pos.CENTER);

            // Kuva mängija kaardid
            HBox hBox = m.getMängijaHbox();
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(5);
            vBox.getChildren().add(hBox);

            m.getMängijaHbox().getParent().opacityProperty().bind(m.läbipaistvusProperty());
            mängijadFlow.getChildren().add(vBox);
        }
    }

    /**
     * Stand nupp. Seab mängija lõpetanuks ja enam käia ei saa.
     */
    public void standNupp() {
        kelleKäik.setSeis(MängijaSeis.STAND);
        lõpetanudList.add(kelleKäik);

        mäng.järgmineMängija();
    }

    /**
     * Hit nupp. Lisab mängijale kaardi ja kontrollib, kas läks üle 21.
     */
    public void hitNupp() {
        Käsi käsi = kelleKäik.getKäsi();
        Kaart uusKaart = mänguPakk.suvaline(); // Võta pakist uus kaart
        käsi.lisaKaart(uusKaart); // lisa kaart mängijale

        // Lisa kaart ekraanile
        kelleKäik.getMängijaHbox().getChildren().add(uusKaart.kaartLabel());

        // System.out.println(kelleKäik.getNimi() + " kaartide summa: " + käsi.summa());

        // Võrdle uut tulemust 21-ga
        switch (Integer.compare(käsi.summa(), 21)) {
            case -1 -> kelleKäik.setSeis(MängijaSeis.OOTAB);
            case 0 -> {
//                System.out.println("21 käes!");
                kelleKäik.setSeis(MängijaSeis.STAND);
                lõpetanudList.add(kelleKäik);
            }
            case 1 -> {
//                System.out.println("Bust! Oled mängust väljas");
                kelleKäik.setSeis(MängijaSeis.BUST);
                lõpetanudList.add(kelleKäik);
                kelleKäik.strikeThroughNimi();
            }
        }

        // võta järgmine mängija
        mäng.järgmineMängija();
    }

    /**
     * Double nupp. Kahekordistab mängija panuse (kui võimalik) ja võtab mängijale viimase kaardi.
     * Kontrollib saadud tulemust 21-ga.
     */
    public void doubleNupp() {
        // kui mängijal ei ole krediiti et doubleida
        if (kelleKäik.getKrediit() < kelleKäik.getPanus() * 2) return;

        kelleKäik.setPanus(kelleKäik.getPanus() * 2); // kahekordista panust
        Kaart uusKaart = mänguPakk.suvaline(); // uus kaart
        Käsi käsi = kelleKäik.getKäsi();
        käsi.lisaKaart(uusKaart); // lisa uus kaart mängijale

        System.out.println("Panus on nüüd " + kelleKäik.getPanus());
        System.out.println("Tuli kaart " + uusKaart.toString());

        // Lisa kaart ekraanile
        kelleKäik.getMängijaHbox().getChildren().add(uusKaart.kaartLabel());

        // Võrdle uut tulemust 21-ga
        switch (Integer.compare(käsi.summa(), 21)) {
            case -1 -> kelleKäik.setSeis(MängijaSeis.STAND);
            case 0 -> {
//                System.out.println("21 käes!");
                kelleKäik.setSeis(MängijaSeis.STAND);
            }
            case 1 -> {
//                System.out.println("Bust! Oled mängust väljas");
                kelleKäik.setSeis(MängijaSeis.BUST);
                kelleKäik.strikeThroughNimi();
            }
        }

        lõpetanudList.add(kelleKäik);
//        System.out.println("Lõpetasid mängu tulemusega " + käsi.summa());

        mäng.järgmineMängija(); // käik järgmisele mängijale
    }

    /**
     * Näita lõppekraani või mitte.
     * Näitamisel peidab mänguekraani, peitmisel kuvab mänguekraani.
     * @param näita Tõeväärtus, kas näidata lõppekraani.
     */
    public void näitaLõppEkraan(boolean näita) {
        mängEkraan.setVisible(!näita);
        lõppEkraan.setVisible(näita);
    }

    public void initialize() {
        lõppEkraan.setVisible(false);
//        System.out.println(lõppEkraanController);
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

    public Mängija getDiiler() {
        return this.diiler;
    }

    public HBox getDiileriKaardid() {
        return diileriKaardid;
    }

    public Kaardipakk getMänguPakk() {
        return mänguPakk;
    }

    public List<Mängija> getLõpetanudList() {
        return lõpetanudList;
    }

    public LõppKontroller getLõppEkraanController() {
        return lõppEkraanController;
    }
}
