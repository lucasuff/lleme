package uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Rodrigo_Fernando.controller

import model.facilities.Room;
import model.people.Doctor;
import model.people.Nurse;
import model.people.Patient;

import java.util.ArrayList;
import java.util.List;

public class Tracker extends Controller {

    //    search
    public Room searchRoom(String room) {
        for (Room value : dataBase.roomList) {
            if (value.getRoom().equals(room)) {
                return value;
            }
        }
        return null;
    }

    public Room searchRoomCpf(String cpf) {
        for (Room room : dataBase.roomList) {
            for (Patient patient: room.getPatientList()) {
                if (patient.getCpf().equals(cpf)){
                    return room;
                }
            }
        }
        return null;
    }

    public Patient searchPatient(String cpf) {
        for (Patient value : dataBase.patientList) {
            if (value.getCpf().equals(cpf)) {
                return value;
            }
        }
        return null;
    }

    public Doctor searchDoctor(String cpf) {
        for (Doctor value : dataBase.doctorList) {
            if (value.getCpf().equals(cpf)) {
                return value;
            }
        }
        return null;
    }

    public Nurse searchNurse(String cpf) {
        for (Nurse value : dataBase.nurseList) {
            if (value.getCpf().equals(cpf)) {
                return value;
            }
        }
        return null;
    }

    public List<Doctor> searchDoctorsWithPatient(String cpfPat) {
        List<Doctor> aux = new ArrayList<>();
        for (Doctor d : dataBase.doctorList){
            for (Patient p : d.getPatientsAssigned()){
                if (p.getCpf().equals(cpfPat)){
                    aux.add(d);
                }
            }
        }
        return aux;
    }

    public List<Nurse> searchNursesWithPatient(String cpfPat) {
        List<Nurse> aux = new ArrayList<>();
        for (Nurse n : dataBase.nurseList){
            for (Patient p : n.getPatientsAssigned()){
                if (p.getCpf().equals(cpfPat)){
                    aux.add(n);
                }
            }
        }
        return aux;
    }

}
