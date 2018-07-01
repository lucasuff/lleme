package uff.ic.lleme.tic10002.trabalhos.s20181.Marcos_Raylan.model;

import uff.ic.lleme.tic10002.trabalhos.s20181.Marcos_Raylan.HashTable;
import uff.ic.lleme.tic10002.trabalhos.s20181.Marcos_Raylan.LinkedListStrings;
import uff.ic.lleme.tic10002.trabalhos.s20181.Marcos_Raylan.model.ClientInfo;

public class Statistic {

    Day[] listDay = null;
    int currentDay;
    int tam;

    public Statistic(int days) {
        currentDay = 0;
        tam = days;

        // Monta um histórico com uma quantidade fixa de dias
        // a vantagem desta abordagem é que podemos rapidamente acessar o histórico de um dia
        //for(int i = 0; i < days; i++)
        listDay = new Day[days];

        for (int i = 0; i < days; i++)
            listDay[i] = new Day();

        //ClientInfo[] newArray = new ClientInfo[indexNumArray];
    }

    //getter
    // Obtem o dia CORRENTE
    public int getCurrentDay() {
        return currentDay + 1;
    }

    // Obtem o histórico do dia CORRENTE
    public void getHistoricCorrentDay(int type) {
        getHistoricOfDay(type, currentDay);
    }

    // Obtem o histórico de um dia específico
    public void getHistoricOfDay(int type, int day) {
        //day = day - 1;
        if (day <= currentDay)
            if (listDay[day].historicOfDay.getSize() > 0)
                listDay[day].historicOfDay.getLastClients(type, day);
        //else System.out.println("\n-------------------- Não houve atendimento no dia "+ day+1 +" --------------------\n\n");
    }

    // Obtem o histórico dos PRIMEIROS "k" dias
    // 10
    public void getHistoricOfFirstDays(int type, int k) {
        if (k < tam)
            for (int i = 0; i < k; i++) {
                //if( listDay[i].historicOfDay.getSize() > 0 )
                listDay[i].historicOfDay.getLastClients(type, i);
                //else System.out.println("\n-------------------- Não houve atendimento no dia "+ i+1 +" --------------------\n\n");
                if (i == currentDay)
                    break;
            }
        //else System.out.println("\n-------------------- Quantidade de dias excedente --------------------\n\n");
    }

    // Obtem o histórico dos ÚLTIMOS "k" dias
    public void getHistoricOfLastDays(int type, int k) {
        if (k < tam) {
            int valueCont = 0;
            for (int i = currentDay; i >= 0; i--) {
                valueCont++;
                //if( listDay[i].historicOfDay.getSize() > 0 )
                listDay[i].historicOfDay.getLastClients(type, i);
                //else System.out.println("\n-------------------- Não houve atendimento no dia "+ i+1 +" --------------------\n\n");
                if (valueCont == k)
                    break;

            }
        }
        //else System.out.println("\n-------------------- Quantidade de dias excedente --------------------\n\n");
    }

    //setter
    public void addClient(ClientInfo c) {
        listDay[currentDay].historicOfDay.push(c);
    }

    public void newDay() {
        currentDay++;
        if (currentDay > tam) {
            // Realoca mais dias para o histórico ou informa OverFlow
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Classe que armazena as informações de um dia
    public class Day {

        LinkedList historicOfDay = null;
        //ClientInfo client = null;

        //Construtor
        public Day() {
            historicOfDay = new LinkedList();
        }

        // Classe para Lista Duplamente Encadeada
        // Armazena todos os clientes removidos da heap
        // A Classe também armazena uma Tabela Hash com os tipos de assuntos
        // Cada chave da "hash" armazena um "tipo de assunto"
        // Para cada indice da "hash" temos armazenado:
        //
        // Chave, Descrição do Assunto e , O PRINCIPAL, a soma dos tempos por tipo de assunto
        public class LinkedList {

            Node head = null; 	// Lista de Clientes do dia
            HashTable hash;		// Tabela Hash com os tipos de assunto
            int size;			// Tamanho da lista de Clientes

            public LinkedList() {
                hash = new HashTable();
                size = 0;
            }

            class Node {

                ClientInfo client;
                Node prev;
                Node next;

                Node(ClientInfo c) {
                    client = c;
                }

                public ClientInfo getClient() {
                    return client;
                }
            }

            public Node getHead() {
                return head;
            }

            public int getSize() {
                return size;
            }

            public HashTable getHashTable() {
                return hash;
            }

            public ClientInfo getNodeI(int index) {
                Node temp = getHead();
                int i = 0;

                while (temp != null && i != index) {
                    i++;
                    temp = temp.next;
                }
                return temp.getClient();
            }

            public void push(ClientInfo c) {
                Node new_Node = new Node(c);
                String key;
                String description;
                double duration;

                new_Node.next = head;
                new_Node.prev = null;

                // Caso exista um nó inicial o novo nó será o inicio da lista
                if (head != null)
                    head.prev = new_Node;

                // Neste sentido, teremos uma lista encadeada em que o primeiro elemento da lista será o último
                // cliente removido da heap
                head = new_Node;
                size++;

                // Adicionando AS CHAVES com a LISTA DE ASSUNTOS do cliente "c"
                // Percorre a lista de assuntos do cliente e adiciona key, description, duration na HashTable
                ///*
                for (int j = 0; j < head.client.getSubjects().size(); j++) {
                    key = String.valueOf(head.client.getSubjects().getType(j));
                    description = head.client.getSubjects().getDescription(j);
                    duration = head.client.getSubjects().getTime(j);

                    hash.put(key, description, duration);
                }
                //*/
            }

            // Calcula a média de tempo para o assunto do tipo "type"
            public void getLastClients(int type, int day) {
                int count = 0;
                int qtd = 0;
                double value = 0;
                double average = 0;
                Node nodeAux = head;

                // Calculando a média de tempo para o Assunto com o tipo (type)
                System.out.printf("\n-------------------------------------------------- Clientes Removidos do HEAP no dia " + (day + 1) + " -------------------------------------------------\n\n");

                if (head != null) {

                    // Temos "tam" clientes para avaliar
                    //for(int i = 0; i < listDay.getSize(); i++) {
                    for (int i = 0; i < getSize(); i++) {
                        ClientInfo client = nodeAux.client;

                        //Tempo total de serviço do cliente, ou seja, inclui TODOS os serviços
                        value += client.getServiceTime();

                        // Cada cliente possui uma quantidade de "x" de assuntos
                        // x = client.getSubjects().size()
                        for (int j = 0; j < client.getSubjects().size(); j++)
                            if (client.getSubjects().getType(j) == type) {
                                // Caso o serviço seja do tipo "type" , então soma em "average" o tempo do serviço "j"
                                average += client.getSubjects().getTime(j);
                                qtd++;
                            }

                        // Imprime as informações do cliente corrente
                        client.print();

                        //System.out.printf("Cliente " + client.getName() + " atendido p["  + client.getPriority() + "] " + "Tempo: " +client.getServiceTime() + "\n");
                        nodeAux = nodeAux.next;
                        if (nodeAux == head)
                            i = getSize();
                        //i = listDay.getSize();
                    }

                    // Lista a frequencia em todas as chaves UTILIZADAS/ATIVAS na hash
                    findType();
                    average = (qtd > 0) ? (average / qtd) : 0.0;

                    // Média geral de todos os Assuntos
                    System.out.printf("\nMedia Geral: " + value / getSize() + "\nMedia de tempo do Serviço Tipo [" + type + "]: " + average + "\n\n");
                } else
                    System.out.printf("\n-------------------------------------------------- Não Houve Atendimentos no Dia " + (day + 1) + " -------------------------------------------------\n\n\n"); // Temos "tam" clientes para avaliar
            }

            public void findType() {
                // Obtem as chaves usadas na tabela hash
                // Ou poderia simplesmente uscar uma chave com o tipo de serviço e fazer esse processo para todos os tipos de serviços
                // Entretanto, uma alternativa seria armazenar em uma lista as chaves usadas e depois mostra-las da forma que for mais conveniente
                //
                LinkedListStrings keys = hash.getUsedKeys();
                String key = keys.getNext();
                int qtd = 0;
                double value = 0;
                double average = 0;

                while (key != null) {
                    System.out.printf("Media de tempo do Serviço Tipo " + key + ": " + hash.getTime(String.valueOf(key)) + "\n");
                    key = keys.getNext();
                }
                hash.resetIT();
            }
        }
    }
}
