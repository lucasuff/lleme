package uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Rodrigo_Fernando.model.people;


import model.misc.Cpf;

public class Person {
    private String name;
    private Cpf cpf;
    private String birthDate;
    private String bloodType;
    private String gender;

    Person(String name, Cpf cpf, String birthDate, String bloodType, String gender) {
        this.name = name;
        this.cpf = cpf;
        this.birthDate = birthDate;
        this.bloodType = bloodType;
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public String getBloodType() {
        return bloodType;
    }

    public String getCpf() {
        return cpf.getCpf();
    }

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
