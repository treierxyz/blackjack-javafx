package com.example.blackjackjavafx.kontrollerid;

import com.example.blackjackjavafx.Vaade;
import com.example.blackjackjavafx.VaateVahetaja;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.awt.*;
import java.awt.datatransfer.StringSelection;

public class AbiKontroller {
    @FXML
    private TextFlow abiTextFlow;

    public void tagasi() {
        VaateVahetaja.vaheta(Vaade.PEAMENÜÜ);
    }

    public void avaLink(ActionEvent event) {
        String link = ((Hyperlink) event.getTarget()).getText();
        System.out.println("Ava link: "+link);
        //TODO: Lingi avamine brauseris
    }

    public void kopeeriLink(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            String link = ((Hyperlink) event.getSource()).getText();
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(link),null);
        }
    }

    public void initialize() {
        for (Node child : abiTextFlow.getChildren()) {
            if (child.getClass() == Text.class) {
                ((Text) child).setText(((Text) child).getText()+"\n\n");
            }
        }
    }

}
