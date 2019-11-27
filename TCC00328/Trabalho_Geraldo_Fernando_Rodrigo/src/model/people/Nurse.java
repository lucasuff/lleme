package model.people;

import model.misc.Cpf;

public class Nurse extends Staff {
    public Nurse(String name, Cpf cpf, String birthDate, String bloodType, String gender) {
        super(name, cpf, birthDate, bloodType, gender);
    }
}
