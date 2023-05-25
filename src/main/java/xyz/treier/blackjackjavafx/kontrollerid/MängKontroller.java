package xyz.treier.blackjackjavafx.kontrollerid;

import javafx.animation.FadeTransition;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;
import xyz.treier.blackjackjavafx.*;

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
    private HBox actionBar;
    @FXML
    private Parent lõppEkraan;
    @FXML
    private LõppKontroller lõppEkraanController; // siin peab olema formaadis id+"Controller", muidu ei tööta
    @FXML
    private BorderPane mängEkraan;
    @FXML
    private Label infoText;

    private List<Mängija> mängijadList;
    private List<Mängija> lõpetanudList;
    private Mängija kelleKäik;
    private int mitmesKasi = 0;
    private Diiler diiler;
    private Kaardipakk mänguPakk;
    private Mäng mäng;
    private FadeTransition ft;

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
            panus.textProperty().bind(Bindings.when(m.panusSummaProperty().isEqualTo(0)).then("").otherwise(Bindings.concat("-", m.panusSummaProperty().asString())));
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

        // TODO: EEMALDA SEE PEALE TESTIMIST
        // AINULT DEBUGIMISEKS
        // See ajutiselt määrab panusteks 100 ja alustab mänguga. See on üpris katkine kuid see on mõeldud
        // ainult debugimiseks. See on kasulik panuste kiireks sisestamiseks et katsetada mängu funktsionaalsust.
        VaateVahetaja.getStseen().setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.F8) {
                for (Mängija mängija : mängijadList) {
                    if (100 > mängija.getKrediit()) {
                        if (lõpetanudList.contains(mängija)) continue;
                        mängija.getKäed().get(0).setPanus(mängija.getKrediit());
                        continue;
                    }
                    mängija.getKäed().get(0).setPanus(100);
                    mängija.setSeis(MängijaSeis.PANUS_VALMIS);
                }
                alustaMängu();
            }
        });
        // AINULT DEBUGIMISEKS

        for (Mängija mängija : mängijadList) {
            mängija.getMängijaVBox().getChildren().clear();

            // Kui pole krediiti et alustada mängu
            if (mängija.getKrediit() == 0) {
                lõpetanudList.add(mängija);

                mängija.setSeis(MängijaSeis.VÄLJAS);
                mängija.strikeThroughNimi(); // Kriipsuta mängija nimi läbi

                // Kaartide asemel näita "VÄLJAS"
                Label väljas = new Label("VÄLJAS");
//                väljas.setFont(new Font(16));
                mängija.getMängijaVBox().getChildren().add(väljas);

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
                    if (panusKogus > mängija.getKrediit() || panusKogus <= 0) {
                        infoText.setText("Panus ei sobi!");
                        ft.stop();
                        ft.play();
                        return;
                    }
                    mängija.getKäed().get(0).setPanus(panusKogus); // määra esimese käe panus
                    mängija.setAlgnePanus(panusKogus);

//                    System.out.println(mängija.panusListProperty().toString());
//                    System.out.println(mängija.panusSummaProperty().get());
                } catch (NumberFormatException e) {
                    infoText.setText("Panus ei sobi!");
                    ft.stop();
                    ft.play();
                    return;
                }

                // Korrektse panuse sisestamisel keelab panuse sisestamise välja
                panus.setDisable(true);
                mängija.setSeis(MängijaSeis.PANUS_VALMIS);

                // Kui kõik on panused teinud, siis alusta mänguga
                if (panusedTehtud()) {
                    alustaMängu();
                }
            });

            // Lisa iga mängija puhul ekraanile panuse kast ja ok nupp
            mängija.getMängijaVBox().getChildren().addAll(panus, ok);
        }
    }

    /**
     * Kontrollib, kas kõik mängijad on oma panused teinud.
     *
     * @return true, kui kõik panused on tehtud,
     * false, kui ei ole.
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

    public void alustaMängu() {
        // käigu nupud aktiivseks
        actionBar.disableProperty().set(false);

        // Uus kaardipakk. Pakkide arv võrdne mängijate arvuga, kuid minimaalselt 2.
        mänguPakk = new Kaardipakk(Math.max(mängijadList.size(), 2));
        jagaKaardid(); // jaga mängijatele kaardid
        mängijadHalliks(); // mängijad halliks

        // Alusta mängijate ringlusega
        mäng = new Mäng(this);
        mäng.järgmineMängija();
    }

    /**
     * Teeb kõik mängijad mänguvaates halliks.
     */
    public void mängijadHalliks() {
        for (Mängija m : mängijadList) {
            if (lõpetanudList.contains(m)) {
                continue;
            }
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
        Diiler diiler = new Diiler();
        this.diiler = diiler;

        // Jaga diilerile 2 kaarti
        for (int i = 0; i < 2; i++)
            diiler.getKäsi().lisaKaart(mänguPakk.suvaline());
        // Näita ühte diileri kaarti
        diileriKaardid.getChildren().clear();
        diileriKaardid.getChildren().add(diiler.getKäsi().getKaardidLabelid().get(0)); // Lisa esimene kaart ekraanile

        // Teised diileri kaardid küsimärgid
        for (int i = 1; i < diiler.getKäsi().getKaardid().size(); i++) {
            Label küsimärk = new Label(" ? ");
            küsimärk.setFont(new Font(16));
            diileriKaardid.getChildren().add(küsimärk);
        }

        // Jaga mängijatele
        for (Mängija mängija : mängijadList) {
            // Kustuta kõik käed peale esimese
            for (int i = 1; i < mängija.getKäed().size(); i++) {
                mängija.getKäed().remove(i);
                i--;
            }

            // Tühjenda käsi
            mängija.getKäed().get(0).tühjendaKäsi();
            mängija.getKäed().get(0).setSeis(KäsiSeis.OOTAB);

            // Esimese käe panus sama mis mängija panus
            // seda pole enam vaja, käe panus määratakse individuaalselt
//            mängija.getKäed().get(0).setPanus(mängija.getPanus());

            // 0 krediidiga mängijale kaarte ei jaga
            if (mängija.getKrediit() == 0)
                continue;

            // Eemalda kaardid ekraanilt
            mängija.getMängijaVBox().getChildren().clear();

            // Jaga esimesse kätte 2 kaarti
            for (int i = 0; i < 2; i++)
                mängija.getKäed().get(0).lisaKaart(mänguPakk.suvaline());

            HBox kasiHBox = new HBox();
            kasiHBox.setAlignment(Pos.CENTER);
            kasiHBox.setSpacing(5);
            kasiHBox.getStyleClass().add("kaardid");
            kasiHBox.opacityProperty().bind(mängija.getKäed().get(0).labipaistvusProperty());

            // Kuva kaardid ekraanil
            for (Label kaart : mängija.getKäed().get(0).getKaardidLabelid())
                kasiHBox.getChildren().add(kaart);

            mängija.lisaHBox(kasiHBox);
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
            icon.setIconSize(36);

            // Mängija nimi
            Label nimi = new Label(m.getNimi());
            nimi.setId(m.getNimi());
            nimi.getStyleClass().add("mangija");

            VBox vBox = new VBox(icon, nimi); // ikoon, nimi, käed
            vBox.setAlignment(Pos.CENTER);

            // Kuva mängija kaardid
            VBox mängijaVBox = m.getMängijaVBox();
            mängijaVBox.setAlignment(Pos.CENTER);
            mängijaVBox.setSpacing(2);
            vBox.getChildren().add(mängijaVBox);

            mängijaVBox.getParent().opacityProperty().bind(m.läbipaistvusProperty());
//            m.getMängijaHbox().getParent().getChildrenUnmodifiable().forEach(() label -> label.styleProperty().bind(m.värvProperty()));
            rekursiivneStyleBind(m.getMängijaVBox().getParent(), m);
            mängijadFlow.getChildren().add(vBox);
        }
    }

    private void rekursiivneStyleBind(Parent vanem, Mängija mängija) {
        vanem.getChildrenUnmodifiable().forEach(node -> {
            if (node instanceof Label) {
                Label label = (Label) node;
                label.styleProperty().bind(mängija.värvProperty());
            } else if (node instanceof Parent) {
                Parent laps = (Parent) node;
                rekursiivneStyleBind(laps, mängija);
            }
        });
    }

    /**
     * Stand nupp. Seab mängija lõpetanuks ja enam käia ei saa.
     */
    public void standNupp() {
        Käsi kasi = kelleKäik.getKäed().get(mitmesKasi);
        kasi.setSeis(KäsiSeis.STAND);

        mitmesKasi = mitmesKäsiMängitav(mitmesKasi, kelleKäik.getKäed());

        // Kui kõik käed on mängitud
        if (mitmesKasi >= kelleKäik.getKäed().size()) {
            // Kui kõik käed ei sa enam mängida
            if (kõikKäedLäbi(kelleKäik.getKäed())) {
                kelleKäik.setSeis(MängijaSeis.STAND);
                lõpetanudList.add(kelleKäik);
            }

            kelleKäik.setSeis(MängijaSeis.OOTAB);
            mäng.järgmineMängija();
        } else {
            // Järgmine käsi mustaks

            kelleKäik.getKäed().get(mitmesKasi).setSeis(KäsiSeis.MÄNGIB);
        }
    }

    /**
     * Hit nupp. Lisab mängijale kaardi ja kontrollib, kas läks üle 21.
     */
    public void hitNupp() {
        Käsi käsi = kelleKäik.getKäed().get(mitmesKasi);

        // Kui käsi ei ole bust või stand, siis lisa kaart
        if (käsi.getSeis() != KäsiSeis.BUST && käsi.getSeis() != KäsiSeis.STAND) {
            Kaart uusKaart = mänguPakk.suvaline(); // Võta pakist uus kaart
            käsi.lisaKaart(uusKaart); // lisa kaart mängijale

            // Lisa kaart ekraanile
            ((HBox) kelleKäik.getMängijaVBox().getChildren().get(mitmesKasi)).getChildren().add(uusKaart.kaartLabel());

            // Võrdle uut tulemust 21-ga
            if (käsi.summa() > 21) {
                käsi.setSeis(KäsiSeis.BUST);
            } else {
                käsi.setSeis(KäsiSeis.OOTAB);
            }
        }

        mitmesKasi = mitmesKäsiMängitav(mitmesKasi + 1, kelleKäik.getKäed());

        // Kui kõik käed on mängitud
        if (mitmesKasi >= kelleKäik.getKäed().size()) {
            käteKontroll();
            mäng.järgmineMängija();
        } else {
            // Järgmine käsi mustaks
            kelleKäik.getKäed().get(mitmesKasi).setSeis(KäsiSeis.MÄNGIB);
        }
    }

    /**
     * Double nupp. Kahekordistab mängija panuse (kui võimalik) ja võtab mängijale viimase kaardi.
     * Kontrollib saadud tulemust 21-ga.
     */
    public void doubleNupp() {
        // kui mängijal ei ole krediiti et doubleida
        if (kelleKäik.getKrediit() < (kelleKäik.käePanused() + kelleKäik.getKäed().get(mitmesKasi).getPanus())) {
            infoText.setText("Pole küllalt krediiti!");
            ft.stop();
            ft.play();
            return;
        }

        Kaart uusKaart = mänguPakk.suvaline(); // uus kaart
        Käsi käsi = kelleKäik.getKäed().get(mitmesKasi);
        käsi.lisaKaart(uusKaart); // lisa kätte uus kaart

        // Määra uus käe panus
        käsi.setPanus(käsi.getPanus() * 2);

        // Lisa kaart ekraanile õigesse kätte
        ((HBox) kelleKäik.getMängijaVBox().getChildren().get(mitmesKasi)).getChildren().add(uusKaart.kaartLabel());

        // Võrdle uut tulemust 21-ga
        if (käsi.summa() > 21)
            käsi.setSeis(KäsiSeis.BUST);
        else
            käsi.setSeis(KäsiSeis.STAND);


        mitmesKasi = mitmesKäsiMängitav(mitmesKasi + 1, kelleKäik.getKäed());

        // Kui kõik käed on mängitud
        if (mitmesKasi >= kelleKäik.getKäed().size()) {
            käteKontroll();
            mäng.järgmineMängija();
        } else {
            // Järgmine käsi mustaks
            kelleKäik.getKäed().get(mitmesKasi).setSeis(KäsiSeis.MÄNGIB);
        }
    }

    public void splitNupp() {
        // Maksimaalselt saab 3 korda splittida
        if (kelleKäik.getKäed().size() > 3) {
            infoText.setText("Rohkem ei saa splittida!");
            ft.stop();
            ft.play();
            return;
        }

        Käsi käsi = kelleKäik.getKäed().get(mitmesKasi);

        //Splittida saab ainult siis kui on kaks samat kaarti
        if (käsi.getKaardid().size() == 2) {
            Kaart esimene = käsi.getKaardid().get(0);
            Kaart teine = käsi.getKaardid().get(1);

            // Kui ei ole samad kaardid siis ei saa splittida
            if (!esimene.equals(teine)) {
                infoText.setText("Pole samad kaardid!");
                ft.stop();
                ft.play();
                return;
            }
        } else {
            infoText.setText("Ei saa "+käsi.getKaardid().size()+" kaardiga splittida!");
            ft.stop();
            ft.play();
            return;
        }

        // Kui mängijal pole krediiti, et splittida
        if (kelleKäik.getKrediit() < (kelleKäik.käePanused() + kelleKäik.getAlgnePanus())) {
            infoText.setText("Pole küllalt krediiti!");
            ft.stop();
            ft.play();
            return;
        }

        // Mängijale uus käsi
        Käsi uusKäsi = new Käsi();
        uusKäsi.setPanus(kelleKäik.getAlgnePanus());
        kelleKäik.lisaKäsi(uusKäsi, mitmesKasi + 1); // Lisa käsi mängijale

        // Esimese käe teine kaart
        Kaart splitKaart = käsi.getKaardid().get(1);
        uusKäsi.setPanus(käsi.getPanus()); // Uue käe panus

        // Lisa teine kaart uude kätte ja eemalda vanast
        uusKäsi.lisaKaart(splitKaart);
        käsi.getKaardid().remove(1);

        // Lisa mõlemasse kätte uus kaart
        uusKäsi.lisaKaart(mänguPakk.suvaline());
        käsi.lisaKaart(mänguPakk.suvaline());

        // Uue käe HBox
        HBox splitHBox = new HBox();
        splitHBox.setAlignment(Pos.CENTER);
        splitHBox.setSpacing(5);
        splitHBox.getStyleClass().add("kaardid");
        splitHBox.opacityProperty().bind(kelleKäik.getKäed().get(mitmesKasi + 1).labipaistvusProperty()); // Käe läbipaistvus binditud selle seisuga

        // Lisa uue käe kaardid ekraanile
        splitHBox.getChildren().add(splitKaart.kaartLabel());
        splitHBox.getChildren().add(uusKäsi.getKaardid().get(1).kaartLabel());
        kelleKäik.lisaHBox(splitHBox, mitmesKasi + 1);

        // Lisa algse käe kaardid ekraanile
        HBox aglseKäeHBox = ((HBox) kelleKäik.getMängijaVBox().getChildren().get(mitmesKasi));
        aglseKäeHBox.getChildren().remove(1); // Kustuta ekraanilt algse käe teine kaart
        aglseKäeHBox.getChildren().add(kelleKäik.getKäed().get(mitmesKasi).getKaardid().get(1).kaartLabel()); // Lisa uus kaart

        käsi.setSeis(KäsiSeis.OOTAB); // split käsi ootab
        mitmesKasi += 2; // Üle uue käe

        // Kui kõik käed on mängitud, siis võta järgmine mängija
        if (mitmesKasi >= kelleKäik.getKäed().size()) {
            kelleKäik.setSeis(MängijaSeis.OOTAB);
            mäng.järgmineMängija();
        } else {
            kelleKäik.getKäed().get(mitmesKasi).setSeis(KäsiSeis.MÄNGIB); // Järgmine käsi aktiivseks
        }
    }

    /**
     * Leiab mängija kätest esimese, millega saab mängida.
     *
     * @param algus Mis indeksist peale käsi vaadata
     * @param käed  Mängija käed
     * @return Indeks esimesele käele, mis on ooteseisus.
     */
    public int mitmesKäsiMängitav(int algus, List<Käsi> käed) {
        int i;
        for (i = algus; i < käed.size(); i++)
            if (käed.get(i).getSeis() == KäsiSeis.OOTAB)
                return i;

        return i;
    }

    public boolean kõikKäedBust(List<Käsi> kaed) {
        for (Käsi k : kaed)
            if (k.getSeis() != KäsiSeis.BUST)
                return false;
        return true;
    }

    public boolean kõikKäedLäbi(List<Käsi> kaed) {
        for (Käsi k : kaed)
            if (k.getSeis() != KäsiSeis.BUST && k.getSeis() != KäsiSeis.STAND)
                return false;
        return true;
    }

    /**
     * Kontrollib, kas mingi käega saab veel mängida.
     * Paneb mängijale vastavad seisud - bust, stand või ootab.
     */
    public void käteKontroll() {
        // Kui kõik käed on bust, siis on mängija bust
        if (kõikKäedBust(kelleKäik.getKäed())) {
            kelleKäik.setSeis(MängijaSeis.BUST);
            kelleKäik.strikeThroughNimi();
            lõpetanudList.add(kelleKäik);
        }

        // Kui ükski käsi ei saa enam mängida, siis on mängija stand
        else if (kõikKäedLäbi(kelleKäik.getKäed())) {
            kelleKäik.setSeis(MängijaSeis.STAND);
            lõpetanudList.add(kelleKäik);
        }

        // Kui saab veel mingi käega mängida
        else {
            kelleKäik.setSeis(MängijaSeis.OOTAB);
        }
    }

    /**
     * Näita lõppekraani või mitte.
     * Näitamisel peidab mänguekraani, peitmisel kuvab mänguekraani.
     *
     * @param näita Tõeväärtus, kas näidata lõppekraani.
     */
    public void näitaLõppEkraan(boolean näita) {
        mängEkraan.setVisible(!näita);
        lõppEkraan.setVisible(näita);
    }

    public void initialize() {
        infoText.setOpacity(0.0);
        infoText.getStyleClass().add("teade");
        ft = new FadeTransition(Duration.seconds(3), infoText);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.setCycleCount(1);
//        ft.play();
        lõppEkraan.setVisible(false);
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

    public void setMitmesKäsi(int i) {
        this.mitmesKasi = i;
    }

    public Diiler getDiiler() {
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
