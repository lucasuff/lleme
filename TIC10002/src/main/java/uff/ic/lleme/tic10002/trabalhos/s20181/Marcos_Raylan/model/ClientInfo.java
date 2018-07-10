package uff.ic.lleme.tic10002.trabalhos.s20181.Marcos_Raylan.model;

import uff.ic.lleme.tic10002.trabalhos.s20181.Marcos_Raylan.HashTable;
import uff.ic.lleme.tic10002.trabalhos.s20181.Marcos_Raylan.model.TypeSubject;
import uff.ic.lleme.tic10002.trabalhos.s20181.Marcos_Raylan.model.Subject;

/*Name: Marcos Raylan Sousa Matos
 Class: PatientInfo
 Due Date: 10/05/2018
 Date Submitted: 14/06/2018
 */
//import java.util.ArrayList;
//import java.util.List;
public class ClientInfo {

    private String name;										// nome
    private String cpf;											// cpf
    private double age;											// idade

    private int arrivalTime;									// Hora de chegada
    private int departureTime;									// Hora de saída
    private int serviceTime;									// Tempo total de serviços
    private SubjectList subjects;								// Lista de assuntos
    HashTable hashTable;										// Lista de Prioridades
    private double priority;									// Prioridade do Cliente
    //private boolean byOrder;									// Parâmetro para definir prioridade apenas pela ordem de chegada

    //constructor
    public ClientInfo() {
        this.name = "-";
        this.cpf = "-";
        this.arrivalTime = 0;
        this.departureTime = 0;
        this.subjects = new SubjectList();
    }

    //getter
    public String getName() {
        return name;
    }

    public String getCPF() {
        return cpf;
    }

    public double getAge() {
        return this.age;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getDepartureTime() {
        return departureTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public SubjectList getSubjects() {
        return subjects;
    }

    public double getPriority() {
        return priority;
    }

    //setter
    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String cpf) {
        this.cpf = cpf;
    }

    public void setAge(double age) {
        // Normaliza o padrão de idade
        this.age = age / 65;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setDepartureTime(int departureTime) {
        this.departureTime = this.arrivalTime + getServiceTime() + departureTime;
    }

    public void setSubjects(SubjectList subjects) {
        this.subjects = subjects;
    }

    public void setPriority(double priority) {
        this.priority = priority;
    }

    //methods
    //Adiciona um assunto na lista de assuntos do cliente
    public void addSubject(String description, String providence, int duration, Subject sub) {
        TypeSubject item = new TypeSubject(description, providence, duration, sub);
        item.setDescription(description);
        item.setProvidence(providence);
        item.setDuration(duration);
        item.setSubject(sub);
        this.subjects.addSubject(item);
    }

    //Calcula a prioridade do cliente
    public void calcPriority() {
        double value = 0;
        this.serviceTime = 0;

        for (int i = 0; i < subjects.size(); i++) {
            // Soma o tempo total de serviço do Cliente
            this.serviceTime += subjects.getTime(i);
            // Soma das urgências dos assuntos
            value += subjects.getPriority(i);
            //this.departureTime += this.arrivalTime + subjects.getTime(i);
        }
        //Recalcula o tempo de saída do cliente
        setDepartureTime(this.serviceTime);

        // Média das urgências dos assuntos
        value = value / subjects.size();

        // Retorna a prioridade do Cliente
        this.priority = (getAge() + this.serviceTime + value) / 3;
    }

    public void log(String msg) {
        System.out.println(msg);
    }

    //Informação sobre o cliente
    public void print() {

        // Print the list objects in tabular format.
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%30s %30s %30s %20s %21s", "Nome", "CPF", "Idade", "Tempo de Chegada", "Tempo de Saída");
        System.out.println();
        System.out.format("%30s %30s %30s %20s %21s",
                getName(), getCPF(), getAge() * 65, getArrivalTime(), getDepartureTime());
        System.out.println();

        System.out.println("PRIORITY: " + getPriority());

        //System.out.println("---------------------------------------------------------------------------------------------------");
        System.out.println();

        System.out.printf("%1s %30s %30s %30s %15s %16s", "Tipo de Assunto", "Titulo", "Descrição", "Providencia", "Duração", "Urgência\n");
        for (int i = 0; i < this.subjects.size(); i++)
            subjects.getItem(i);
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println();
    }

}//end PatientInfo class
