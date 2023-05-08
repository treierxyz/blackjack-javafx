package com.example.blackjackjavafx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    // TODO: README lisamine

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage pealava) throws IOException {
        Scene stseen = new Scene(new Pane());
        VaateVahetaja.setStseen(stseen);
        VaateVahetaja.vaheta(Vaade.PEAMENÜÜ);
        pealava.setScene(stseen);
        pealava.show();
    }
}
