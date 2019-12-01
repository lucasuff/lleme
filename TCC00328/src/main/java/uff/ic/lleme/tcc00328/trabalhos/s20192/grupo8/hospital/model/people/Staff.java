package uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Fernando_Rodrigo.hospital.model.people;

import java.util.ArrayList;
import java.util.List;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Rodrigo_Fernando.model.misc.Cpf;

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
