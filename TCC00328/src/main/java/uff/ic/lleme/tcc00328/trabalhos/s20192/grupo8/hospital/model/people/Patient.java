package uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Fernando_Rodrigo.hospital.model.people;

import java.util.List;
import uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Rodrigo_Fernando.model.misc.Cpf;

public class Patient extends Person {

    private String healthInsurance;
    private List<String> diseases;

    public Patient(String name,
            Cpf cpf,
            String birthDate,
            String bloodType,
            String gender,
            String healthInsurance,
            List<String> diseases
    ) {
        super(name, cpf, birthDate, bloodType, gender);
        this.healthInsurance = healthInsurance;
        this.diseases = diseases;
    }

    public String getHealthInsurance() {
        return healthInsurance;
    }

    public void setHealthInsurance(String healthInsurance) {
        this.healthInsurance = healthInsurance;
    }

    public List<String> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<String> diseases) {
        this.diseases = diseases;
    }
}
