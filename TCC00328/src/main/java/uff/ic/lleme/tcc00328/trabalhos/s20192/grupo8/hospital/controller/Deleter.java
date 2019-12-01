package uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Fernando_Rodrigo.hospital.controller;

import java.util.List;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Fernando_Rodrigo.hospital.model.facilities.Room;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Fernando_Rodrigo.hospital.model.people.Doctor;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Fernando_Rodrigo.hospital.model.people.Nurse;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Fernando_Rodrigo.hospital.model.people.Patient;

public class Deleter extends Controller {

    public void deleteRoom(Room room) {
        dataBase.roomList.remove(room);
    }

    public void deletePatient(Patient patient) {
        dataBase.patientList.remove(patient);
    }

    public void deletePatientFromRoom(Room room, Patient patient) {
        room.getPatientList().remove(patient);
    }

    public void deleteDoctor(Doctor doctor) {
        dataBase.doctorList.remove(doctor);
    }

    public void deleteNurse(Nurse nurse) {
        dataBase.nurseList.remove(nurse);
    }

    public void deletePatientFromDoctor(List<Doctor> doctorList, Patient patient) {
        for (Doctor d : doctorList)
            d.getPatientsAssigned().remove(patient);
    }

    public void deletePatientFromNurse(List<Nurse> nurseList, Patient patient) {
        for (Nurse n : nurseList)
            n.getPatientsAssigned().remove(patient);
    }

}
