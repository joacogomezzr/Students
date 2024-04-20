package com.gomez.student.Controllers;

import com.gomez.student.models.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HomeController {
    @FXML
    private Button closeButton;

    @FXML
    private TableColumn<Student, String> col1;

    @FXML
    private TableColumn<Student, String> col2;

    @FXML
    private TableColumn<Student, Integer> col3;

    @FXML
    private TableColumn<Student,Integer> col4;

    @FXML
    private Button deleteButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button seeButton;

    @FXML
    private TableView<Student> tableViewStudents;

    private ObservableList<Student> studentObservableList = FXCollections.observableArrayList();
    private ArrayList<IDataStudent> databases = new ArrayList<>();

    private Database01 database01;
    private Database02 database02;
    private Database03 database03;
    @FXML
    public void initialize() {
        database01 = new Database01();
        database02 = new Database02();
        database03 = new Database03();

        databases.add(database01);
        databases.add(database02);
        databases.add(database03);

        col1.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        col2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        col3.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        col4.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEdad()).asObject());



        loadFromDatabases();
    }

    private void loadFromDatabases() {
        for (IDataStudent database : databases) {
            studentObservableList.addAll(database.getStudents());
        }
        tableViewStudents.setItems(studentObservableList);
    }

    @FXML
    void agregarAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gomez/student/crear-view.fxml"));
            Parent root = loader.load();

            CrearStudentController controller = loader.getController();
            controller.initAttributes(null);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            scene.getStylesheets().add(getClass().getResource("/Style.css").toExternalForm());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();

            Student student = controller.getStudent();
            if (student != null) {
                studentObservableList.add(student);
                saveToDatabases(student);
                tableViewStudents.refresh();
            }
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Error al guardar");
            alert.showAndWait();
        }
    }

    @FXML
    void modificarAction(ActionEvent event) {
        Student selectedStudent = tableViewStudents.getSelectionModel().getSelectedItem();

        if( selectedStudent == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Error");
            alert.setContentText("Debe seleccionar un estudiante");
            alert.showAndWait();
        }else{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gomez/student/crear-view.fxml"));
                Parent root = loader.load();

                CrearStudentController controller = loader.getController();
                controller.initAttributes(selectedStudent);

                Scene scene = new Scene(root);
                Stage stage = new Stage();
                scene.getStylesheets().add(getClass().getResource("/Style.css").toExternalForm());
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setScene(scene);
                stage.showAndWait();

                Student modifiedStudent = controller.getStudent();
                if (modifiedStudent != null) {
                    updateInDatabases(selectedStudent, modifiedStudent);
                    tableViewStudents.refresh();
                }
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setTitle("Error");
                alert.showAndWait();
            }
        }
    }


    private void saveToDatabases(Student student) {
        for (IDataStudent database : databases) {
            database.saveStudent(student);
        }
    }
    @FXML
    void eliminarAction(ActionEvent event) {

    }
    @FXML
    void closeWindow(ActionEvent event){
        System.exit(1);
    }
    private void updateInDatabases(Student modifiedStudent, Student student) {
        database01.updateStudent(student);
        database02.updateStudent(student);
        database03.updateStudent(student);

        database01.printStudents();
        database02.printStudents();
        database03.printStudents();
    }
}
