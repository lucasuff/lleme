package uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Fernando_Rodrigo.hospital.db;

import java.util.ArrayList;
import java.util.List;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Fernando_Rodrigo.hospital.model.facilities.Room;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Fernando_Rodrigo.hospital.model.people.Doctor;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Fernando_Rodrigo.hospital.model.people.Nurse;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Fernando_Rodrigo.hospital.model.people.Patient;

public class Db {

    public List<Patient> patientList = new ArrayList<>();
    public List<Room> roomList = new ArrayList<>();
    public List<Doctor> doctorList = new ArrayList<>();
    public List<Nurse> nurseList = new ArrayList<>();

}
