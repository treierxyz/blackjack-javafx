package com.example.blackjackjavafx.kontrollerid;

import com.example.blackjackjavafx.Vaade;
import com.example.blackjackjavafx.VaateVahetaja;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class AbiKontroller {
    @FXML
    private TextFlow abiTextFlow;

    public void tagasi() {
        VaateVahetaja.vaheta(Vaade.PEAMENÜÜ);
    }

    public void avaLink(ActionEvent event) {
        String link = ((Hyperlink) event.getTarget()).getText();
        //TODO: Lingi avamine brauseris
    }

    public void initialize() {
        for (Node child : abiTextFlow.getChildren()) {
            if (child.getClass() == Text.class) {
                ((Text) child).setText(((Text) child).getText()+"\n\n");
            }
        }
    }

}
