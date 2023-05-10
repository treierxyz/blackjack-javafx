package xyz.treier.blackjackjavafx.kontrollerid;

import xyz.treier.blackjackjavafx.Vaade;
import xyz.treier.blackjackjavafx.VaateVahetaja;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.net.URI;
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

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI(link));
            } catch (Exception e) {
                System.out.println("Lingi avamine ebaõnnestus:");
                e.printStackTrace();
            }
        }
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
