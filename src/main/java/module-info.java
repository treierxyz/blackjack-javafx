module xyz.treier.blackjackjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.material2;
    requires java.desktop;
    requires org.commonmark;
    requires javafx.web;
    requires org.commonmark.ext.gfm.tables;
    opens xyz.treier.blackjackjavafx.kontrollerid;

    exports xyz.treier.blackjackjavafx;
    exports xyz.treier.blackjackjavafx.kontrollerid;
}