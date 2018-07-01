/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.Breno_Vinicius;

import uff.ic.lleme.tic10002.trabalhos.s20181.Breno_Vinicius.model.Assuntos;
import uff.ic.lleme.tic10002.trabalhos.s20181.Breno_Vinicius.model.Cliente;
import java.io.*;
import java.time.*;
import java.util.*;

/**
 *
 * @author Breno
 */
public class AtendimentoApp {

    public static File[] finder(String dirName) {

        File dir = new File(dirName);

        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".txt");
            }
        });
    }
    //}

    public static File[] arquivosOrdenados(String dirName) {
        File[] arquivos = AtendimentoApp.finder(dirName);
        Arrays.sort(arquivos, new Comparator<File>() {
            @Override
            public int compare(File f1, File f2) {
                Integer num1 = Integer.parseInt(f1.toString().replaceAll("\\D+", ""));
                Integer num2 = Integer.parseInt(f2.toString().replaceAll("\\D+", ""));
                return num1.compareTo(num2);
            }
        });
        return arquivos;
    }

    public static void imprimirEstatistica(ClientesAtendidos i) {
        System.out.println("Deseja imprimir a Estatistica? (y/n/i=interrrompe)");
        Scanner sc = new Scanner(System.in);
        String fim = sc.next();
        if ("y".equals(fim))
            //System.out.println("i.dias: " + i.dias);
            i.estatistica[i.dias].geraEstatistica();
        else if ("i".equals(fim))
            System.exit(0);
        System.out.println("Deseja continuar? (y/n)");
        //Scanner sc=new Scanner(System.in);
        String fimdois = sc.next();
        if ("n".equals(fimdois))
            System.exit(0);
    }

    public static void testeColisao(Assuntos ass) {
        System.out.print("##### TESTE de COLISAO ASSUNTOS ####" + System.lineSeparator());
        ClientesAtendidos temp = new ClientesAtendidos(ass);
        int[] array = new int[ass.assuntos.length];
        Arrays.fill(array, 1);
        String linha1 = "08:04:00; 197.375.798-28;  Teste; 15; ";
        for (String i : ass.assuntos) {
            Cliente tempCliente = new Cliente((linha1 + i).split(";"));
            tempCliente.assuntos[0] = i;
            int[] a = {10};
            tempCliente.tempoAssuntos = a;
            temp.Encerrar(tempCliente);
        }
        for (String i : ass.assuntos) {
            Cliente tempCliente = new Cliente((linha1 + i).split(";"));
            tempCliente.assuntos[0] = i;
            int[] a = {10};
            tempCliente.tempoAssuntos = a;
            temp.Encerrar(tempCliente);
        }

        temp.estatistica[0].geraEstatistica();
        Scanner sc = new Scanner(System.in);
        System.out.println("Deseja Parar Execuçao? digite p e enter");
        String fim = sc.next();
        if ("p".equals(fim))
            System.exit(0);
    }

    public static void main(String[] args) throws FileNotFoundException {
        //Inicializar os pessos para cada assunto
        Assuntos ass = new Assuntos("./assuntos.txt", "./providencias.txt");

        //  Obtem os arquivos na pasta Dias --> Ordenados em dias
        File[] days = AtendimentoApp.arquivosOrdenados("./Dias");
        String ident = "      ";
        System.out.print("##### INICIANDO PROCEDIMENTO DE ATENDIMENTO ####" + System.lineSeparator());
        System.out.print(System.lineSeparator());

        ClientesAtendidos atendidos = new ClientesAtendidos(ass);

        for (File arquivo : days) {

            System.out.print("Inicio do Dia " + arquivo.toString().replaceAll("\\D+", "") + System.lineSeparator());
            FileReader larquivo = new FileReader(arquivo);
            BufferedReader bufferedReader = new BufferedReader(larquivo);
            String line = "Pass";

            ClientesEsperando clientesespera = new ClientesEsperando(ass);

            Dia hoje = new Dia();
            clientesespera.horaDIA = hoje.horario;

            try {
                while (hoje.horario.isBefore(LocalTime.parse(hoje.fim))) {
                    line = bufferedReader.readLine();
                    if (clientesespera.n == 0 && line == null)
                        hoje.horario = LocalTime.parse(hoje.fim);
                    else if (line == null)
                        line = "17:02:00;";
                    if (hoje.horario.isAfter(LocalTime.parse(hoje.fim)))
                        hoje.fimexpediente();
                    else {
                        // Procedimento para Receber Cliente(s)
                        LocalTime horariocliente = LocalTime.parse(line.split(";")[0]);
                        boolean aguardarCliente = true;
                        while (aguardarCliente)
                            // Se chegou Clientes - Apenas Recepciona
                            if (hoje.horario.isAfter(horariocliente.minusMinutes(1))) {
                                hoje.ihora();
                                clientesespera.recepcionar(line.split(";"), hoje.horario);
                                aguardarCliente = false;
                            } else if (clientesespera.n == 0) {
                                //hoje.ihora();
                                // Caso ainda nao tenha chegado cliente, Passa + 1 min
                                hoje.horario = hoje.horario.plusMinutes(1);
                                aguardarCliente = true;
                            } else {
                                // Terminando de Clegar novos Clientes
                                // Atende o Prioritario
                                hoje.ihora();
                                Cliente atendido = clientesespera.Atendimento(hoje.horario);
                                hoje.horario = clientesespera.horaDIA;
                                atendidos.Encerrar(atendido);
                                aguardarCliente = false;
                            }
                    }
                    //imprimirEstatistica(atendidos);
                }
            } catch (IOException ex) {
                System.out.println("Arquivo nao encontrado");
            }
            //atendidos.estatistica.geraEstatistica();
            imprimirEstatistica(atendidos);
            System.out.println(System.lineSeparator());
            System.out.println("Clientes nao atendidos no dia corrente: " + clientesespera.n);
            System.out.print("Fim do Dia " + arquivo.toString().replaceAll("\\D+", "") + System.lineSeparator());
            System.out.print(new String(new char[50]).replace("\0", "_") + System.lineSeparator());
            atendidos.fimDia();
        }
    }
}

class Dia {

    String inicio = "08:00:00";
    String fim = "17:00:00";
    LocalTime horario = LocalTime.parse(inicio);

    public void ihora() {
        System.out.println(System.lineSeparator() + "  Horario: " + horario);
    }

    public void somaAtendimento(int minutos) {
        horario = horario.plusMinutes(minutos);
    }

    public void fimexpediente() {
        System.out.println(System.lineSeparator() + "  Horario: " + horario);
    }

}
