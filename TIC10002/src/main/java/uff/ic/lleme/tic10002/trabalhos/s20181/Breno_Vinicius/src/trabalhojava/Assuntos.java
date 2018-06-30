/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhojava;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList; // Nao utlizado em estruturas:
import java.util.List;      //         Somente impressao ou leituras de arquivos

/**
 *
 * @author Breno
 */
public class Assuntos {

    int n;
    int pos;
    int[] urgencia;
    int[] tempo;
    String[] assuntos;
    String[] providencias;

    public Assuntos(String aqruivoassuntos, String providencia) throws FileNotFoundException {
        List<String> ListaAssuntos = lerAssuntos(aqruivoassuntos);
        this.n = ListaAssuntos.size();
        //this.urgencia = IntStream.generate(() -> new Random().nextInt(11)).limit(n).toArray();
        String str = new String(new char[55]).replace("\0", "_");
        System.out.println(str);
        System.out.println("Lista de Assuntos - Urgencia:");
        // System.out.println(ListaAssuntos);
        //System.out.println("Ugencias dos Assuntos:");
        for (int i = 0; i < n; i++)
            System.out.println("     " + ListaAssuntos.get(i) + "              " + urgencia[i]);
        System.out.println(str);
        System.out.println(System.lineSeparator());
        String[] temp = new String[ListaAssuntos.size()];
        this.assuntos = ListaAssuntos.toArray(temp);
        this.providencias = lerProvidencia(providencia);
    }

    private String[] lerProvidencia(String providencia) throws FileNotFoundException {
        FileReader provd = new FileReader(providencia);
        List<String> ListaProv = new ArrayList<String>();
        BufferedReader bufferedReader = new BufferedReader(provd);
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null)
                ListaProv.add(line);
        } catch (IOException ex) {
            System.out.println("Arquivo Assuntos nao encontrado");
        }
        return ListaProv.toArray(new String[0]);
    }

    private List<String> lerAssuntos(String arquivoAssunto) throws FileNotFoundException {
        FileReader assuntos_txt = new FileReader(arquivoAssunto);
        List<String> assuntos = new ArrayList<String>();
        List<Integer> urgent = new ArrayList<Integer>();
        BufferedReader bufferedReader = new BufferedReader(assuntos_txt);
        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                //String a = line.split(", ")[0];
                assuntos.add(String.format("%10s", line.split(", ")[0]));
                urgent.add(Integer.valueOf(line.split(", ")[1]));
            }
        } catch (IOException ex) {
            System.out.println("Arquivo Assuntos nao encontrado");
        }
        urgencia = urgent.stream().mapToInt(Integer::intValue).toArray();
        return assuntos;
    }
}
