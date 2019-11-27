package model.people;

import model.misc.Cpf;

import java.util.ArrayList;
import java.util.List;

public class Staff extends Person {
    private List<Patient> PatientsAssigned = new ArrayList<>();

    Staff(String name, Cpf cpf, String birthDate, String bloodType, String gender) {
        super(name, cpf, birthDate, bloodType, gender);
    }

    public List<Patient> getPatientsAssigned() {
        return PatientsAssigned;
    }

    public void addPatient(Patient patient) {
        PatientsAssigned.add(patient);
    }
}
