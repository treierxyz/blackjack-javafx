package xyz.treier.blackjackjavafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {

    // TODO: README lisamine
    // TODO: äkki liiguta mõned klassid omaette paketti?

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage pealava) {
        Scene stseen = new Scene(new Pane(),640,480);
        VaateVahetaja.setStseen(stseen);
        VaateVahetaja.vaheta(Vaade.PEAMENÜÜ);
        pealava.setScene(stseen);
        pealava.setTitle("Blackjack");
        pealava.show();
    }
}
