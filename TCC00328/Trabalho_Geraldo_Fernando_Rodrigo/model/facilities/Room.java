package uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Rodrigo_Fernando.model.facilities

import model.people.Patient;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private String room;
    private List<Patient> patientList = new ArrayList<>();

    public Room(String room) {
        this.room = room;
    }

    public String getRoom() {
        return room;
    }

    public void addPatient(Patient patient) {
        for (Patient value : patientList) {
            if (value.getCpf().equals(patient.getCpf())) {
                return;
            }
        }
        patientList.add(patient);
    }

    public List<Patient> getPatientList() {
        return patientList;
    }
}
