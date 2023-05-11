package xyz.treier.blackjackjavafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    // TODO: README lisamine
    // TODO: äkki liigutaks mõned klassid omaette paketti?

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage pealava) throws IOException {
        Scene stseen = new Scene(new Pane(),640,480);
        VaateVahetaja.setStseen(stseen);
        VaateVahetaja.vaheta(Vaade.PEAMENÜÜ);
        pealava.setScene(stseen);
        pealava.setTitle("Blackjack");
        pealava.getIcons().add(new Image(getClass().getResource("icon.png").openStream()));
        pealava.show();
    }
}
