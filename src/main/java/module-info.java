module com.gomez.student {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gomez.student to javafx.fxml;
    exports com.gomez.student;
    exports com.gomez.student.Controllers;
    opens com.gomez.student.Controllers to javafx.fxml;
}