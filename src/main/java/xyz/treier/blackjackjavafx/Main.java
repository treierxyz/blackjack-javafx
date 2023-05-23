package xyz.treier.blackjackjavafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.util.*;

public class Main extends Application {

    // TODO: äkki liigutaks mõned klassid omaette paketti?

    private static Stage pealava;

    public static void main(String[] args) {
        launch(args);
    }

    public EventHandler<WindowEvent> sulgeProgramm = event -> {
        Alert kinnita = new Alert(Alert.AlertType.CONFIRMATION, "Kas soovid kindlasti väljuda?");
        kinnita.setTitle("Kinnita väljumine");
        ((Button) kinnita.getDialogPane().lookupButton(ButtonType.OK)).setText("Jah");
        ((Button) kinnita.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("Ei");
        Platform.runLater(() -> kinnita.getDialogPane().lookupButton(ButtonType.CANCEL).requestFocus());
        kinnita.initOwner(pealava);

        Random random = new Random();
        // Mõned suvaliselt valitud sõnumid mida näidatakse mängijale väljudes, natuke nalja peab ikka saama ;)

        try (BufferedReader sc = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("quitmsg.txt")))) {
            List<String> väljuSõnumid = new ArrayList<>();
            String rida;
            while ((rida = sc.readLine()) != null) {
                väljuSõnumid.add(rida);
            }
            kinnita.setHeaderText(väljuSõnumid.get(random.nextInt(väljuSõnumid.size())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Optional<ButtonType> kinnitus = kinnita.showAndWait();
        if (kinnitus.isPresent() && !ButtonType.OK.equals(kinnitus.get())) {
            event.consume();
        }
    };

    @Override
    public void start(Stage pealava) throws IOException {
        Main.pealava = pealava;
        Scene stseen = new Scene(new Pane(),1280,720);
        stseen.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        VaateVahetaja.setStseen(stseen);
        VaateVahetaja.vaheta(Vaade.PEAMENÜÜ);
        pealava.setScene(stseen);
        pealava.setTitle("Blackjack");
        pealava.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));

        pealava.setOnCloseRequest(sulgeProgramm);

        pealava.show();
    }

    public static void sulge() {
        pealava.fireEvent(new WindowEvent(pealava, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
