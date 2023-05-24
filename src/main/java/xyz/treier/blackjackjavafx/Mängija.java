package xyz.treier.blackjackjavafx;

import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Mängija implements Comparable<Mängija> {
    private final String nimi;
    private final IntegerProperty krediit = new SimpleIntegerProperty();
//    private final Käsi käsi;
    private final List<Käsi> käed = new ArrayList<>(); // Mängija käed
    private final ObservableList<IntegerProperty> panusList = FXCollections.observableArrayList();
    private final IntegerProperty panusSumma = new SimpleIntegerProperty();
    private int algnePanus;
    private final MängijaSeisProperty seis = new MängijaSeisProperty();
    private final DoubleProperty läbipaistvus = new SimpleDoubleProperty();
    private final StringProperty värv = new SimpleStringProperty();
    private final VBox mängijaVBox = new VBox();

    private static final List<String> debugNimed = new ArrayList<>(List.of("Artur", "Peeter", "Joonas", "Kaarel", "Johanna", "Liina", "Mia", "Lisete"));

    /**
     * Mängija, kelle nime saab määrata
     *
     * @param nimi    mängija nimi
     * @param krediit mängija krediit
     */
    public Mängija(String nimi, int krediit) {
        this.nimi = nimi;
        this.krediit.set(krediit);

        this.käed.add(new Käsi());

        for (Käsi käsi : käed) {
            panusList.add(käsi.panusProperty());
        }

        //panusSumma.bind(Bindings.createIntegerBinding(() -> panusList.stream().mapToInt(IntegerProperty::get).sum(), panusList));
        panusSumma.bind(Bindings.createIntegerBinding(() -> käed.stream().mapToInt(Käsi::getPanus).sum(),
                käed.stream().map(Käsi::panusProperty).toArray(IntegerProperty[]::new)));

//        System.out.println(panusSumma.intValue());
//        panusSumma.bind(Bindings.createIntegerBinding(() -> {
//            int summa = 0;
//            for (Käsi käsi : this.käed) {
//                summa += käsi.getPanus();
//            }
//            return summa;
//        }, käed.stream().map(Käsi::panusProperty).toArray(IntegerProperty[]::new)));

        this.seis.addListener(((observable, oldValue, newValue) -> {
            läbipaistvus.set(newValue.getLäbipaistvus());
            värv.set("-fx-text-fill: "+newValue.getVärv());
//            System.out.println(newValue.getLäbipaistvus());
        }));
        this.seis.setValue(MängijaSeis.INIT);
    }

    /**
     * Mängija kellele antakse suvaline nimi.
     * @param krediit mängija krediit.
     */
/*    public Mängija(int krediit) {
        this.nimi = debugNimed.get((int) (Math.random() * debugNimed.size()));
        debugNimed.remove(nimi);
        this.krediit.set(krediit);
        this.käsi = new Käsi();
        this.seis.addListener(((observable, oldValue, newValue) -> {
            läbipaistvus.set(newValue.getLäbipaistvus());
            värv.set(newValue.getVärv());
//            System.out.println(newValue.getLäbipaistvus());
        }));
        this.seis.setValue(MängijaSeis.INIT);
    }*/

    /**
     * Tagastab mängija nime.
     * @return mängija nimi.
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Tagastab mängija krediidi.
     * @return mängija krediit.
     */
    public int getKrediit() {
        return krediit.get();
    }

    /**
     * Lisab mängijale krediiti.
     * @param krediit lisatav krediidi kogus.
     */
    public void lisaKrediit(int krediit) {
        this.krediit.set(getKrediit() + krediit);
    }

    /**
     * Võimaldab mängijate krediidi kogusumma siduda lõpuekraani "jätka" nupu keelamisega.
     * @return Mängija krediit.
     */
    public IntegerProperty krediitProperty() {
        return krediit;
    }

    /**
     * Kriipsutab ekraanilt mängija nime läbi.
     */
    public void strikeThroughNimi() {
        for (Node child : mängijaVBox.getChildren()) {
            if (child.getClass() == Text.class) {
                ((Text) child).setStrikethrough(true);
            }
        }
    }

    /**
     * Tagastab mängija käe.
     * @return mängija käsi.
     */
//    public Käsi getKäsi() {
//        return käsi;
//    }

    public List<Käsi> getKäed() {
        return käed;
    }

    public void lisaKäsi(Käsi käsi) {
        käed.add(käsi);
    }

    public void lisaKäsi(Käsi käsi, int index) {
        käed.add(index, käsi);

        panusSumma.bind(Bindings.createIntegerBinding(() -> käed.stream().mapToInt(Käsi::getPanus).sum(),
                käed.stream().map(Käsi::panusProperty).toArray(IntegerProperty[]::new)));
    }

    /**
     * Tagastab mängija seisu.
     * @return mängija seis.
     */
    public MängijaSeis getSeis() {
        return seis.get();
    }

    /**
     * Määrab mängija seisu.
     */
    public void setSeis(MängijaSeis seis) {
        this.seis.set(seis);
    }

    /**
     * Tagastab mängija panuse.
     * @return mängija panus.
     */
//    public int getPanus() {
//        return this.panus.get();
//    }

    public IntegerProperty panusSummaProperty() {
        return panusSumma;
    }

    public ObservableList<IntegerProperty> panusListProperty() {
        return panusList;
    }
    public int getAlgnePanus() {
        return algnePanus;
    }
    public void setAlgnePanus(int panus) {
        this.algnePanus = panus;
    }

    public int käePanused() {
        int sum = 0;
        for (Käsi k: käed)
            sum += k.getPanus();

        return sum;
    }

    public VBox getMängijaVBox() {
        return mängijaVBox;
    }

    public void lisaHBox(HBox hbox) {
        mängijaVBox.getChildren().add(hbox);
    }

    public void lisaHBox(HBox hbox, int index) {
        mängijaVBox.getChildren().add(index, hbox);
    }

    /**
     * Võimaldab siduda mängija läbipaistvuse ekraanil tema seisuga.
     * @return mängija läbipaistvuse väärtus.
     */
    public DoubleProperty läbipaistvusProperty() {
        return läbipaistvus;
    }

    public StringProperty värvProperty() {
        return värv;
    }

    /**
     * Tagastab mängija nime ja krediidi lihtsalt loetaval kujul
     *
     * @return mängija nimi ja krediit sõnena
     */
    @Override
    public String toString() {
        return nimi + ", krediit: " + krediit;
    }

    /**
     * Võrdleb teise mängijaga krediidi kogust ja tagastab,
     * kas mängijal on võrreldavast rohkem, vähem või võrdselt krediiti
     *
     * @param mängija võrreldav mängija.
     * @return -1, kui mängijal on vähem krediiti kui võrreldaval.
     * 0, kui krediiti on võrdselt.
     * 1, kui mängijal on rohkem krediiti kui võrreldaval.
     */
    @Override
    public int compareTo(Mängija mängija) {
        return Integer.compare(krediit.get(), mängija.krediit.get());
    }
}
