package uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Fernando_Rodrigo.hospital.controller;

import java.util.ArrayList;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Fernando_Rodrigo.hospital.model.facilities.Room;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Fernando_Rodrigo.hospital.model.people.Doctor;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Fernando_Rodrigo.hospital.model.people.Nurse;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Fernando_Rodrigo.hospital.model.people.Patient;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Rodrigo_Fernando.model.misc.Cpf;

public class Creator extends Controller {

    public void createPatient(String name, Cpf cpf, String birthDate, String bloodType, String gender, String healthInsurance, String room, ArrayList<String> diseases) {

        Patient patient = new Patient(name, cpf, birthDate, bloodType, gender, healthInsurance, diseases);

        boolean found = false;
        for (Room value : dataBase.roomList)
            if (value.getRoom().equals(room)) {
                value.addPatient(patient);
                found = true;
            }
        if (!found) {
            Room newRoom = new Room(room);
            newRoom.addPatient(patient);
            dataBase.roomList.add(newRoom);
        }

        dataBase.patientList.add(patient);
    }

    public void createDoctor(String name, Cpf cpf, String birthDate, String bloodType, String gender, ArrayList<String> specialties) {
        Doctor doctor = new Doctor(name, cpf, birthDate, bloodType, gender, specialties);
        dataBase.doctorList.add(doctor);
    }

    public void createNurse(String name, Cpf cpf, String birthDate, String bloodType, String gender) {
        Nurse nurse = new Nurse(name, cpf, birthDate, bloodType, gender);
        dataBase.nurseList.add(nurse);
    }
}
