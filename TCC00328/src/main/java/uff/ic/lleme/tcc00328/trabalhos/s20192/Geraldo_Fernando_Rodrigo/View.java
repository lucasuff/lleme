package uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Rodrigo_Fernando

import controller.Creator;
import controller.Deleter;
import controller.Tracker;
import model.facilities.Room;
import model.misc.Cpf;
import model.people.Doctor;
import model.people.Nurse;
import model.people.Patient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class View {

    private static boolean running = true;

    private static Creator creator = new Creator();
    private static Tracker tracker = new Tracker();
    private static Deleter deleter = new Deleter();


    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner sc = new Scanner(System.in);
        String command;

        while (running) {
            show_main_menu();
            command = sc.nextLine();

            switch (command.toUpperCase()) {
                case "CP":
                    System.out.println("Inserir CPF do paciente: ");
                    String cpf = sc.nextLine();

                    if (tracker.searchPatient(cpf) != null) {
                        System.out.println("paciente já existente");
                        break;
                    }
                    Cpf newCpf = new Cpf(cpf);
                    if (!newCpf.checkCpf()) {
                        System.out.println("CPF invalido, favor digitar novamente: ");
                        newCpf.setCpf(sc.nextLine());
                    }

                    System.out.println("Inserir Nome do Paciente:");
                    String name = sc.nextLine();

                    System.out.println("Inserir data de nascimento: ");
                    String birthDate = sc.nextLine();

                    System.out.println("Inserir tipo sanguineo: ");
                    String bloodType = sc.nextLine();

                    System.out.println("Inserir genero: ");
                    String gender = sc.nextLine();

                    System.out.println("Inserir plano de saude: ");
                    String healthInsurance = sc.nextLine();

                    System.out.println("Inserir quarto: ");
                    String room = sc.nextLine();

                    System.out.println("Inserir problemas do paciente (\"final\" para encerrar): ");
                    ArrayList<String> diseases = new ArrayList<>();
                    String disease = sc.nextLine();
                    while (!disease.equals("final")){
                        diseases.add(disease);
                        disease = sc.nextLine();
                    }

                    creator.createPatient(name, newCpf, birthDate, bloodType, gender, healthInsurance, room, diseases);
                    break;
                case "CD":
                    System.out.println("Inserir CPF do medico: ");
                    cpf = sc.nextLine();

                    if (tracker.searchDoctor(cpf) != null) {
                        System.out.println("Medico já existente");
                        break;
                    }
                    newCpf = new Cpf(cpf);
                    if (!newCpf.checkCpf()) {
                        System.out.println("CPF invalido, favor digitar novamente: ");
                        newCpf.setCpf(sc.nextLine());
                    }

                    System.out.println("Inserir Nome do medico:");
                    name = sc.nextLine();

                    System.out.println("Inserir data de nascimento: ");
                    birthDate = sc.nextLine();

                    System.out.println("Inserir tipo sanguineo: ");
                    bloodType = sc.nextLine();

                    System.out.println("Inserir genero: ");
                    gender = sc.nextLine();

                    System.out.println("Inserir especialidades do medico (\"final\" para encerrar): ");
                    ArrayList<String> specialties = new ArrayList<>();
                    String specialty = sc.nextLine();
                    while (!specialty.equals("final")){
                        specialties.add(specialty);
                        specialty = sc.nextLine();
                    }

                    creator.createDoctor(name, newCpf, birthDate, bloodType, gender, specialties);
                    break;
                case "CE":
                    System.out.println("Inserir CPF do enfermeiro: ");
                    cpf = sc.nextLine();

                    if (tracker.searchNurse(cpf) != null) {
                        System.out.println("Enfermeiro já existente");
                        break;
                    }
                    newCpf = new Cpf(cpf);
                    if (!newCpf.checkCpf()) {
                        System.out.println("CPF invalido, favor digitar novamente: ");
                        newCpf.setCpf(sc.nextLine());
                    }

                    System.out.println("Inserir Nome do enfermeiro:");
                    name = sc.nextLine();

                    System.out.println("Inserir data de nascimento: ");
                    birthDate = sc.nextLine();

                    System.out.println("Inserir tipo sanguineo: ");
                    bloodType = sc.nextLine();

                    System.out.println("Inserir genero: ");
                    gender = sc.nextLine();

                    creator.createNurse(name, newCpf, birthDate, bloodType, gender);

                    break;
                case "PP":
                    System.out.println("Insira o cpf do paciente a ser procurado: ");
                    cpf = sc.nextLine();
                    Patient patient = tracker.searchPatient(cpf);
                    if (patient == null) {
                        System.out.println("Paciente nao existe");
                        break;
                    } else {
                        System.out.printf("Nome: %s\n", patient.getName());
                        System.out.printf("Cpf: %s\n", patient.getCpf());
                        System.out.printf("Data de Nascimento: %s\n", patient.getBirthDate());
                        System.out.printf("Tipo sanguineo: %s\n", patient.getBloodType());
                        System.out.printf("Sexo: %s\n", patient.getGender());
                        System.out.printf("Plano de Saude: %s\n", patient.getHealthInsurance());
                        System.out.printf("Quarto: %s\n", tracker.searchRoomCpf(patient.getCpf()).getRoom());
                        System.out.println("Doencas: ");
                        for (String d : patient.getDiseases()) {
                            System.out.printf("- %s\n", d);
                        }
                    }
                    break;
                case "PD":
                    System.out.println("Insira o cpf do medico a ser procurado: ");
                    cpf = sc.nextLine();
                    Doctor doctor = tracker.searchDoctor(cpf);
                    if (doctor == null) {
                        System.out.println("Medico nao existe");
                        break;
                    } else {
                        System.out.printf("Nome: %s\n", doctor.getName());
                        System.out.printf("Cpf: %s\n", doctor.getCpf());
                        System.out.printf("Data de Nascimento: %s\n", doctor.getBirthDate());
                        System.out.printf("Tipo sanguineo: %s\n", doctor.getBloodType());
                        System.out.printf("Sexo: %s\n", doctor.getGender());

                        System.out.println("Especialidades: ");
                        for (String s : doctor.getSpecialties()) {
                            System.out.printf("- %s\n", s);
                        }

                        System.out.println("Pacientes: ");
                        if (doctor.getPatientsAssigned().size() == 0) {
                            System.out.println("Nenhum paciente associado");
                            break;
                        }
                        for (Patient p : doctor.getPatientsAssigned()){
                            System.out.printf("- Nome: %s, Cpf: %s\n", p.getName(), p.getCpf());
                        }
                    }
                    break;
                case "PE":
                    System.out.println("Insira o cpf do enfermeiro a ser procurado: ");
                    cpf = sc.nextLine();
                    Nurse nurse = tracker.searchNurse(cpf);
                    if (nurse == null) {
                        System.out.println("enfermeiro nao existe");
                        break;
                    } else {
                        System.out.printf("Nome: %s\n", nurse.getName());
                        System.out.printf("Cpf: %s\n", nurse.getCpf());
                        System.out.printf("Data de Nascimento: %s\n", nurse.getBirthDate());
                        System.out.printf("Tipo sanguineo: %s\n", nurse.getBloodType());
                        System.out.printf("Sexo: %s\n", nurse.getGender());

                        System.out.println("Pacientes: ");
                        if (nurse.getPatientsAssigned().size() == 0) {
                            System.out.println("Nenhum paciente associado");
                            break;
                        }
                        for (Patient p : nurse.getPatientsAssigned()) {
                            System.out.printf("- Nome: %s, Cpf: %s\n", p.getName(), p.getCpf());
                        }
                    }
                    break;
                case "PQ":
                    System.out.println("Insira o numero do quarto: ");
                    room = sc.nextLine();
                    Room roomFound = tracker.searchRoom(room);
                    if (roomFound == null) {
                        System.out.println("Quarto nao existe");
                        break;
                    }
                    for (Patient p : roomFound.getPatientList()) {
                        System.out.printf("- Nome: %s, Cpf: %s\n", p.getName(), p.getCpf());
                    }
                    break;
                case "DP":
                    System.out.println("Insira o cpf do paciente a ser excluido: ");
                    cpf = sc.nextLine();
                    patient = tracker.searchPatient(cpf);
                    if (patient == null) {
                        System.out.println("Paciente nao existe");
                        break;
                    }
                    Room r = tracker.searchRoomCpf(cpf);
                    deleter.deletePatientFromRoom(r, patient);
                    if (r.getPatientList().size() == 0) {
                        deleter.deleteRoom(r);
                    }
                    deleter.deletePatientFromDoctor(tracker.searchDoctorsWithPatient(cpf), patient);
                    deleter.deletePatientFromNurse(tracker.searchNursesWithPatient(cpf), patient);
                    deleter.deletePatient(patient);
                    break;
                case "DD":
                    System.out.println("Insira o cpf do medico a ser deletado: ");
                    cpf = sc.nextLine();
                    doctor = tracker.searchDoctor(cpf);
                    if (doctor == null) {
                        System.out.println("Medico nao existe");
                        break;
                    }
                    deleter.deleteDoctor(doctor);
                    break;
                case "DE":
                    System.out.println("Insira o cpf do enfermeiro a ser deletado: ");
                    cpf = sc.nextLine();
                    nurse = tracker.searchNurse(cpf);
                    if (nurse == null) {
                        System.out.println("enfermeiro nao existe");
                        break;
                    }
                    deleter.deleteNurse(nurse);
                    break;
                case "ADP":
                    System.out.println("Insira o cpf do medico: ");
                    cpf = sc.nextLine();
                    doctor = tracker.searchDoctor(cpf);
                    if (doctor == null) {
                        System.out.println("Medico nao existe");
                        break;
                    }
                    System.out.println("Insira o cpf do paciente: ");
                    cpf = sc.nextLine();
                    patient = tracker.searchPatient(cpf);
                    if (patient == null) {
                        System.out.println("Paciente nao existe");
                        break;
                    }
                    doctor.addPatient(patient);
                    break;
                case "AEP":
                    System.out.println("Insira o cpf do enfermeiro: ");
                    cpf = sc.nextLine();
                    nurse = tracker.searchNurse(cpf);
                    if (nurse == null) {
                        System.out.println("enfermeiro nao existe");
                        break;
                    }
                    System.out.println("Insira o cpf do paciente: ");
                    cpf = sc.nextLine();
                    patient = tracker.searchPatient(cpf);
                    if (patient == null) {
                        System.out.println("Paciente nao existe");
                        break;
                    }
                    nurse.addPatient(patient);
                    break;
                case "0":
                    running = false;
                    break;
                default:
                    System.out.println("Comando não válido");
            }
            System.out.println();
        }
    }

    private static void show_main_menu() {

        System.out.println("MENU");
        System.out.println("\n");
        System.out.println("Cadastro -> Paciente [CP] - Doutor [CD] - Enfermeiro [CE]");
        System.out.println("Procurar -> Paciente [PP] - Doutor [PD] - Enfermeiro [PE] - Quarto[PQ]");
        System.out.println("Deletar -> Paciente [DP] - Doutor [DD] - Enfermeiro [DE]");
        System.out.println("Associar -> doutor a um paciente [ADP] - enfermeiro a um paciente [AEP]");
        System.out.println("Sair -> [0]");
        System.out.print("> ");
    }
}

