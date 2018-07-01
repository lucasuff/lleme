/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.Breno_Vinicius;

import uff.ic.lleme.tic10002.trabalhos.s20181.Breno_Vinicius.model.Assuntos;
import uff.ic.lleme.tic10002.trabalhos.s20181.Breno_Vinicius.model.Cliente;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 *
 * @author Breno
 */
public class ClientesAtendidos {

    public Assuntos ass;

    public String ident = "    ";

    int n = 0;
    int nmax = 1000;
    int dias = 0;

    AVL atendidos;
    ArmazenaEstatistica[] estatistica = new ArmazenaEstatistica[100];

    public ClientesAtendidos(Assuntos ass) {
        estatistica[0] = new ArmazenaEstatistica(ass.assuntos.length);
        this.ass = ass;
        this.atendidos = new AVL();
    }

    public void Encerrar(Cliente clienteatendido) {
        atendidos.inserir(clienteatendido);
        n++;
        inspect(clienteatendido);
        estatistica[dias].incluir(clienteatendido.assuntos, clienteatendido.tempoAssuntos);
    }

    // PARA IMPRIMIR OS DADOS DO CLIENTE JA ATENDIDO NA TELA
    static void inspect(Cliente obj) {
        Class<?> c = obj.getClass();
        Field[] fields = c.getDeclaredFields();
        //System.out.printf("%d fields:%n", fields.length);

        //Map<String, Object> temp = new HashMap<String, Object>();
        for (Field field : fields)
            try {
                if (field.get(obj) instanceof String[]) {
                    String[] strArray = (String[]) field.get(obj);
                    System.out.println("           " + field.getName().toString() + ": " + Arrays.toString(strArray));
                    //System.out.println("           "+field.getName().toString());
                    //for (Object a : field.get(obj))
                    //        System.out.println(a);
                    //String a = field.get(obj).toString();
                    //System.out.println("           "+ "String");
                    //System.out.println("           "+field.getName().toString() + " - " + field.get(obj).getClass());
                } else if (field.get(obj) instanceof int[]) {
                    int[] intArray = (int[]) field.get(obj);
                    System.out.println("           " + field.getName().toString() + ": " + Arrays.toString(intArray));
                } else if (field.getName().toString() == "this$0") {
                    //System.out.println("           "+ "this");
                } else
                    System.out.println("           " + field.getName().toString() + ": " + field.get(obj));
                //temp.put(field.getName().toString(), field.get(obj));
            } catch (IllegalArgumentException | IllegalAccessException e1) {
            }
    }

    void fimDia() {
        dias++;
        estatistica[dias] = new ArmazenaEstatistica(ass.assuntos.length);
    }

    class ArmazenaEstatistica {

        //int n;
        int qassuntos;

        Nos[] hash;

        class Nos {

            No primeiro; // = null;

            public void Nos() {
                this.primeiro = null;
            }

            public No buscar(String ass) {
                No resp = null;
                //System.out.println("Buscar: assunto" + ass);
                //System.out.println("Buscar: primeiro.ass" + primeiro.ass);
                if (primeiro.ass.equals(ass))
                    //System.out.println("Buscar: primeiro" + primeiro);
                    resp = primeiro;
                else if (primeiro.proximo != null)
                    resp = buscar(ass, primeiro.proximo);
                //System.out.println("Buscar2: resp" + resp);
                //System.out.println(System.lineSeparator());
                return resp;
            }

            public No buscar(String ass, No proximo) {
                No resp = null;
                //System.out.println("Buscar2: assunto" + ass);
                //System.out.println("Buscar2: proximo.ass" + proximo.ass);
                if (proximo == null)
                    return resp;
                if (proximo.ass.equals(ass))
                    resp = proximo;
                else if (proximo.proximo != null)
                    resp = buscar(ass, proximo.proximo);
                //System.out.println("Buscar2: resp" + resp);
                //System.out.println(System.lineSeparator());
                return resp;
            }

            public void incluir(No atual, int tempo, String assunto) {
                if (atual.proximo == null)
                    atual.proximo = new No(tempo, assunto);
                else
                    incluir(atual.proximo, tempo, assunto);
            }

            public void incluir(int tempo, String assunto) {
                if (primeiro == null)
                    primeiro = new No(tempo, assunto);
                else if (primeiro.proximo == null)
                    primeiro.proximo = new No(tempo, assunto);
                else
                    incluir(primeiro.proximo, tempo, assunto);
            }

            public void add(String assunto, int tempo) {
                //System.out.println("ADD:");
                //System.out.println("ADD: assunto " + assunto);
                //System.out.println("ADD: tempo " + tempo);
                if (primeiro == null)
                    //System.out.println("ADD: Primeiro==null");
                    incluir(tempo, assunto);
                else {
                    No pos = buscar(assunto);
                    if (pos == null)
                        incluir(tempo, assunto);
                    else
                        pos.incrementar(tempo);
                }
            }
            //System.out.println("add: " + pos);

//                if (pos != null){
//
//                }
//                else{
//
//                }
            public void excluir() {
            }

            public void qtosNos() {
                if (primeiro != null)
                    System.out.println("Primeiro-Assunto: " + primeiro.ass);
                qtosNos(primeiro.proximo, 1);
            }

            public void qtosNos(No i, int i2) {
                i2++;
                if (i != null) {
                    System.out.println(i2 + "-Assunto: " + i.ass);
                    qtosNos(i.proximo, i2);
                }
            }
        }

        class No {

            int qclientes = 1;
            int tassuntos;
            String ass; // <- chave

            public No proximo;

            public No(int tempo, String a) {
                //this.qclientes = q;
                this.tassuntos = tempo;
                this.ass = a;
            }

            void incrementar(int tempo) {
                qclientes += 1;
                tassuntos += tempo;
            }
        }

        public ArmazenaEstatistica(int maximo) {
            //System.out.println("ArmazenaEstatisticaBegin: " + maximo);
            this.qassuntos = maximo;
            this.hash = new Nos[qassuntos];
            //this.qclientes = new int[qassuntos];
            //this.tassuntos = new int[qassuntos];
        }

        int funcao(String s) {
            // Obter a posicao final

            int soma = 0;
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                soma += (int) c;
            }
            return soma % qassuntos;
        }

        void incluir(String[] assuntos, int[] tempos) {
            for (int i = 0; i < assuntos.length; i++) {
                int pos = funcao(assuntos[i]);
                if (hash[pos] == null)
                    hash[pos] = new Nos();
                hash[pos].add(assuntos[i].replaceFirst("^ *", ""), tempos[i]);
            }
        }

        public No buscarNo(String assunto) {
            int pos = funcao(assunto);
            if (hash[pos] == null)
                return null;
            else
                return hash[pos].buscar(assunto);
        }

        public void nosVazios() {
            for (int pos = 0; pos < qassuntos; pos++)
                if (hash[pos] == null)
                    System.out.println("No Vazio: " + pos);
        }

        void geraEstatistica() {
            System.out.print(System.lineSeparator()
                    + new String(new char[20]).replace("\0", " ") + "ESTATISTICA");
            System.out.print(System.lineSeparator());
            System.out.print(new String(new char[50]).replace("\0", "_") + System.lineSeparator());
            //for (int i = 0; i< qassu ; i++){
            for (String i : ass.assuntos) {
                No info = buscarNo(i.replaceFirst("^ *", ""));
                //System.out.println("info: " + info);
                if (info != null)
                    System.out.println(ident + i + ": "
                            + "Clientes: " + info.qclientes + "  "
                            + "Tempo: " + info.tassuntos + "  "
                            + (float) info.tassuntos / info.qclientes);
                else
                    System.out.println(ident + i + ": "
                            + "Clientes: " + 0 + "  "
                            + "Tempo: " + 0 + "  "
                            + (float) 0);

            }
            System.out.print(new String(new char[50]).replace("\0", "_") + System.lineSeparator());
            //nosVazios();
        }
    }
}
