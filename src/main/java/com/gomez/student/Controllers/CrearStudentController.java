package com.gomez.student.Controllers;

import com.gomez.student.models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CrearStudentController {
    @FXML
    private Button agregarBtn;

    @FXML
    private TextField apellidoTxt;

    @FXML
    private TextField edadTxt;

    @FXML
    private TextField idTxt;

    @FXML
    private TextField nomTxt;

    private Student student;
    private ArrayList<IDataStudent> databases;

    public void initialize() {
        databases = new ArrayList<>();
        databases.add(new Database01());
        databases.add(new Database02());
        databases.add(new Database03());
    }

    public void initAttributes(Student student) {
        this.student = student;

        if (student != null) {
            nomTxt.setText(student.getNombre());
            apellidoTxt.setText(student.getApellido());
            edadTxt.setText(String.valueOf(student.getEdad()));
            idTxt.setText(String.valueOf(student.getId()));
        }
    }

    @FXML
    void OnActionAgregarBtn(ActionEvent event) {
        try {
            String nombre = nomTxt.getText();
            String apellidos = apellidoTxt.getText();
            int edad = Integer.parseInt(edadTxt.getText());
            int id = Integer.parseInt(idTxt.getText());

            Student newStudent = new Student(nombre, apellidos, id,edad);

            boolean exists = false;

            for (IDataStudent database : databases) {
                if (database.getStudents().contains(newStudent)) {
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                if (student != null) {
                    student.setNombre(nombre);
                    student.setApellido(apellidos);
                    student.setEdad(edad);
                    student.setId(id);
                    for (IDataStudent database : databases) {
                        database.updateStudent(student);
                    }
                } else {
                    student = newStudent;
                    for (IDataStudent database : databases) {
                        database.saveStudent(student);
                    }
                }
                closeWindow();
            } else {
                showErrorAlert("La tarea ya existe en una de las bases de datos.");
            }
        } catch (NumberFormatException e) {
            showErrorAlert("El ID debe ser un número entero.");
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) agregarBtn.getScene().getWindow();
        stage.close();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Student getStudent() {
        return student;
    }
}

