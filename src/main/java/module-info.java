module com.example.blackjackjavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.material2;
    requires java.desktop;
    opens com.example.blackjackjavafx.kontrollerid;

    exports com.example.blackjackjavafx;
    exports com.example.blackjackjavafx.kontrollerid;
}