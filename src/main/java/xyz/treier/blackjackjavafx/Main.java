package xyz.treier.blackjackjavafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class Main extends Application {

    // TODO: äkki liigutaks mõned klassid omaette paketti?

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage pealava) throws IOException {
        Scene stseen = new Scene(new Pane(),960,540);
        VaateVahetaja.setStseen(stseen);
        VaateVahetaja.vaheta(Vaade.PEAMENÜÜ);
        pealava.setScene(stseen);
        pealava.setTitle("Blackjack");
        pealava.getIcons().add(new Image(getClass().getResource("icon.png").openStream()));
        stseen.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        pealava.show();
    }
}
