module pw.chat {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.bootstrapfx.core;

    opens pw.chat to javafx.fxml;
    opens pw.chat.controllers to javafx.fxml;
    exports pw.chat;
}