package com.gomez.student.models;

import java.util.ArrayList;

public class Database03 implements IDataStudent {
    private ArrayList<Student> students3 = new ArrayList<>();

    @Override
    public void saveStudent(Student student) {
        students3.add(student);
    }

    @Override
    public void updateStudent(Student student) {
        for (Student e : students3) {
            if (e.getId() == student.getId()) {
                e.setNombre(student.getNombre());
                e.setApellido(student.getApellido());

                return;
            }
        }
    }

    public void printStudents() {
        System.out.println("Estudiantes en Database03:");
        for (Student s : students3) {
            System.out.println(s);
        }
    }

    @Override
    public ArrayList<Student> getStudents() {
        return new ArrayList<>(students3);
    }
}
