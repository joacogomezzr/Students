package com.gomez.student.models;

import java.util.ArrayList;

public class Escuela {
    private IDataStudent dataStudent;

    public Escuela(IDataStudent dataStudent){
        this.dataStudent= dataStudent;
    }

    public void agregarEstudiante(int id,String nombre,String apellido,int edad){
        Student student = new Student(nombre, apellido,edad, id);
        dataStudent.saveStudent(student);
    }

    public void actualizarEstudiante(String nombre, String apellido, int id, int edad) {
        Student student = new Student(nombre, apellido, id,edad);
        dataStudent.updateStudent(student);
    }

    public ArrayList<Student> obtenerEstudiantes(){
        return  dataStudent.getStudents();
    }
}
