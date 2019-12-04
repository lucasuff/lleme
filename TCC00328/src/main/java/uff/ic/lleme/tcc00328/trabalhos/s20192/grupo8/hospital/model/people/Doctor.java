package uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Fernando_Rodrigo.hospital.model.people;

import java.util.ArrayList;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Rodrigo_Fernando.model.misc.Cpf;

public class Doctor extends Staff {

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
