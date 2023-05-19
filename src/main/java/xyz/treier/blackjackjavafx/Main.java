package xyz.treier.blackjackjavafx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
        List<String> väljuSõnumid = List.of("Annad juba alla?", "Diiler lootis, et jääd kauemaks.", "Tagasi tööle, eks?", "Sa olid ju nii lähedal!", "Kas panus oli liiga suur?", "Värske õhk ei teeks liiga...");
        kinnita.setHeaderText(väljuSõnumid.get(random.nextInt(väljuSõnumid.size())));

        Optional<ButtonType> kinnitus = kinnita.showAndWait();
        if (!ButtonType.OK.equals(kinnitus.get())) {
            event.consume();
        }
    };

    @Override
    public void start(Stage pealava) throws IOException {
        this.pealava = pealava;
        Scene stseen = new Scene(new Pane(),960,540);
        stseen.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        VaateVahetaja.setStseen(stseen);
        VaateVahetaja.vaheta(Vaade.PEAMENÜÜ);
        pealava.setScene(stseen);
        pealava.setTitle("Blackjack");
        pealava.getIcons().add(new Image(getClass().getResource("icon.png").openStream()));

        pealava.setOnCloseRequest(sulgeProgramm);

        pealava.show();
    }

    public static void sulge() {
        pealava.fireEvent(new WindowEvent(pealava, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
