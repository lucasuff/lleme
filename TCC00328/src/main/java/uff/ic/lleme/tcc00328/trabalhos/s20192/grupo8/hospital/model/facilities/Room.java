package uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Fernando_Rodrigo.hospital.model.facilities;

import java.util.ArrayList;
import java.util.List;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Fernando_Rodrigo.hospital.model.people.Patient;

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
        for (Patient value : patientList)
            if (value.getCpf().equals(patient.getCpf()))
                return;
        patientList.add(patient);
    }

    public List<Patient> getPatientList() {
        return patientList;
    }
}
