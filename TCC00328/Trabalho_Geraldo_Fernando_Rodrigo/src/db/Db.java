package db;

import model.facilities.Room;
import model.people.Doctor;
import model.people.Nurse;
import model.people.Patient;

import java.util.ArrayList;
import java.util.List;

public class Db {
    public List<Patient> patientList = new ArrayList<>();
    public List<Room> roomList = new ArrayList<>();
    public List<Doctor> doctorList = new ArrayList<>();
    public List<Nurse> nurseList = new ArrayList<>();

}
