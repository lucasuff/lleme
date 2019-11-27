package model.people;

import model.misc.Cpf;

import java.util.ArrayList;

public class Doctor extends Staff{
    private ArrayList<String> specialties;

    public Doctor(String name,
                  Cpf cpf,
                  String birthDate,
                  String bloodType,
                  String gender,
                  ArrayList<String> specialties
    ) {
        super(name, cpf, birthDate, bloodType, gender);
        this.specialties = specialties;
    }

    public ArrayList<String> getSpecialties() {
        return specialties;
    }

    public void setSpecialties(ArrayList<String> specialties) {
        this.specialties = specialties;
    }
}
