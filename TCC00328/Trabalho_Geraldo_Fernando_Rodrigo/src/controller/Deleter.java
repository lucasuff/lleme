package controller;

import model.facilities.Room;
import model.people.Doctor;
import model.people.Nurse;
import model.people.Patient;

import java.util.List;

public class Deleter extends Controller{
    public void deleteRoom(Room room) {
        dataBase.roomList.remove(room);
    }

    public void deletePatient(Patient patient) {
        dataBase.patientList.remove(patient);
    }

    public void deletePatientFromRoom(Room room, Patient patient){
        room.getPatientList().remove(patient);
    }
    public void deleteDoctor(Doctor doctor) {
        dataBase.doctorList.remove(doctor);
    }

    public void deleteNurse(Nurse nurse) {
        dataBase.nurseList.remove(nurse);
    }

    public void deletePatientFromDoctor(List<Doctor> doctorList, Patient patient){
        for (Doctor d : doctorList) {
            d.getPatientsAssigned().remove(patient);
        }
    }

    public void deletePatientFromNurse(List<Nurse> nurseList, Patient patient){
        for (Nurse n : nurseList) {
            n.getPatientsAssigned().remove(patient);
        }
    }

}
