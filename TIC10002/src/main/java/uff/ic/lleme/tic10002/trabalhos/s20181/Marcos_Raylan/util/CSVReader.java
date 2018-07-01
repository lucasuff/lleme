package uff.ic.lleme.tic10002.trabalhos.s20181.Marcos_Raylan.util;

import uff.ic.lleme.tic10002.trabalhos.s20181.Marcos_Raylan.model.ClientInfo;
import uff.ic.lleme.tic10002.trabalhos.s20181.Marcos_Raylan.model.TypeSubject;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import uff.ic.lleme.tic10002.trabalhos.s20181.Marcos_Raylan.SubjectList;
//import clientWaitingRoom.*;

public class CSVReader {

    public List<List<String>> myData;
    public String fileName;
    public List<ClientInfo> info = new ArrayList<ClientInfo>();
    public List<String> myTitles = new ArrayList<String>();

    ;

	public void addClient(ClientInfo newInfo) {
        this.info.add(newInfo);
        this.info.get(this.info.size() - 1).calcPriority();
        //this.info.get( this.info.size()-1).print();
    }

    public ClientInfo getClient(int i) {
        return this.info.get(i);
    }

    public int getSizeList() {
        return this.info.size();
    }

    public void buildClientsInfo(String name, String cpf, double age, int arrivalTime, int departureTime, SubjectList subjects) {

        ClientInfo newInfo = new ClientInfo();

        newInfo.setName(name);
        newInfo.setNumber(cpf);
        newInfo.setAge(age);
        newInfo.setArrivalTime(arrivalTime);
        newInfo.setDepartureTime(departureTime);
        newInfo.setSubjects(subjects);

        addClient(newInfo);

    }//end buildClitensInfo

    public CSVReader(String path) {
        String fileName = path;
        File file = new File(fileName);

        info = new ArrayList<ClientInfo>();

        initialize();

        // this gives you a 2-dimensional array of strings
        List<List<String>> lines = new ArrayList<>();
        Scanner inputStream;

        try {
            inputStream = new Scanner(file);

            while (inputStream.hasNext()) {
                String line = inputStream.next();
                String[] values = line.split(",");
                // this adds the currently parsed line to the 2-dimensional string array
                lines.add(Arrays.asList(values));
            }

            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // the following code lets you iterate through the 2-dimensional array
        int lineNo = 1;
        for (List<String> line : lines) {
            int columnNo = 1;
            List<String> tuple = new ArrayList<String>();
            SubjectList subjects = new SubjectList();

            for (String value : line) {
                //System.out.println("Line " + lineNo + " Column " + columnNo + ": " + value);
                tuple.add(value);
                columnNo++;
            }

            if (lineNo > 1) {

                //System.out.println(tuple.get(0) + " " + tuple.get(1) + " " + tuple.get(2) + " " + tuple.get(3) + " " + tuple.get(4));
                //0		1	 2		3			  4               5              6            7           8
                //Name, CPF, Age,	Arrival Time, Departure Time, *Subject Type, Description, Providence, Duration
                for (int i = 5; i < tuple.size(); i += 4) {
                    //System.out.println(tuple.get(i) + " " + tuple.get(i+1) + " " + tuple.get(i+2) + " " + tuple.get(i+3) + " " );

                    TypeSubject item = new TypeSubject(tuple.get(i + 1), tuple.get(i + 2), Double.parseDouble(tuple.get(i + 3)));

                    // Implementar ... Double.parseDouble(tuple.get(i+2))
                    item.setType(Integer.parseInt(tuple.get(i)));
                    item.setTitle(getDefaultTitles(tuple.get(i)));
                    item.setUrgency(1.0);

                    //System.out.println("ERRO 1");
                    subjects.addSubject(item);
                }
                //System.out.println("ERRO 2");
                ///*
                //				  Name          CPF           Age							  Arrival Time                    Departure Time
                buildClientsInfo(tuple.get(0), tuple.get(1), Double.parseDouble(tuple.get(2)), Integer.parseInt(tuple.get(3)), Integer.parseInt(tuple.get(4)), subjects);
                //info.get(lineNo-1).print();
                //System.out.println("ERRO 3");
                //tuple.clear();
                //*/

                //tuple.clear();
            }
            lineNo++;
            System.out.println("\n\n");
        }
        this.myData = lines;
    }

    public int getIntValue(int line, int index) {
        return Integer.parseInt(this.myData.get(line).get(index));
    }

    public double getDoubleValue(int line, int index) {
        return Double.parseDouble(this.myData.get(line).get(index));
    }

    public void printData() {
        for (int i = 0; i < info.size(); i++)
            this.info.get(i).print();
    }

    public String getDefaultTitles(String index) {
        int i = Integer.parseInt(index);
        //return "+++++";
        return myTitles.get(i % myTitles.size());
    }

    public void initialize() {

        ///*
        myTitles.add("Alertas de gastos");
        myTitles.add("Aplicações automáticas");
        myTitles.add("Pagamentos de Ccontas");
        myTitles.add("Bloqueio de Transações ");
        myTitles.add("Históricos de Tarifas");
        myTitles.add("Fatura Online");
        myTitles.add("Transferências");
        //*/

        /*
    	1 Alertas de gastos

    	Saldo em conta
    	Acompanhamento da fatura do cartão por SMS e e-mail

    	2 Aplicações automáticas

    	Aplicações financeiras
    	Aplicações em poupança
    	Fundos de renda fixa.

    	3 Pagamentos de contas pelo celular

    	Pagar boletos pelo celular
    	Leitor de código de barras

    	4 Bloqueio de transações e avisos de viagem

    	Bloquear compras online
    	Desbloqueio de compras online
    	Habilitar função viagem

    	5 Históricos de tarifas cobradas pelo banco e conta

    	Acompanhar o histórico
    	Extrato dos últimos seis meses

    	6 Fatura online

    	Formulário de solicitação
    	Documento em formato de PDF


    	3 Transferências pela agenda telefônica

    	Para realizar a transação, basta inserir o número de telefone, valor e motivo da transferência. A operação leva alguns segundos.
         */
    }
}
