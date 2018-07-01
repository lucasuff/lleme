package uff.ic.lleme.tic10002.trabalhos.s20181.Marcos_Raylan;

/*Name: Marcos Raylan Sousa Matos
 Class: MenuOption
 Due Date: 10/05/2018
 Date Submitted: 14/06/2018
 */
import uff.ic.lleme.tic10002.trabalhos.s20181.Marcos_Raylan.model.SubjectList;
import uff.ic.lleme.tic10002.trabalhos.s20181.Marcos_Raylan.util.CSVReader;
import uff.ic.lleme.tic10002.trabalhos.s20181.Marcos_Raylan.model.ClientInfo;
import uff.ic.lleme.tic10002.trabalhos.s20181.Marcos_Raylan.model.TypeSubject;
import java.util.Scanner;

public class MenuOption {

    public void Menu() {

        //Controle do fluxo no Menu()
        Scanner input = new Scanner(System.in);
        String myPathFile;
        int choice;
        boolean exit = true;
        boolean addSubject = true;

        //Variáveis para leitura dos dados
        String name = "";
        String cpf = "";
        double age = 0;
        int arrivalTime = 0;
        int departureTime = 0;

        String subjectType = "";
        String description = "";
        String providence = "";
        double duration = 0;
        String title = "";
        double urgency = 1;

        //Arquivo .csv
        CSVReader myCSVFile;

        //Variáveis para armazenamento da fila de prioridades
        int indexNumArray = 0;
        PriorityQueue heap = new PriorityQueue(indexNumArray);
        ClientInfo[] array = null;

        //Variáveis para Estatística
        // Variável para armazenar o histórico durante 30 dias, caso a quantidade seja excedida uma nova alocação é realizada dentro da classe
        Statistic historic = new Statistic(30);
        int days;

        do {
            System.out.printf("-------------------------------------------------------\n");
            System.out.println("Selecione uma das opções                 |dia atual: " + historic.getCurrentDay() + "|");
            System.out.printf("-------------------------------------------------------\n");
            System.out.printf("1. Recepcionar\n");
            System.out.printf("2. Atender\n");
            System.out.printf("3. Imprimir Heap\n");
            System.out.printf("4. Opções\n");
            System.out.printf("5. Gerar Estatística\n");
            System.out.printf("0. Encerrar o Sistema\n");

            System.out.printf("\n-> ");
            choice = input.nextInt();
            System.out.printf("\n");

            while (choice != 0 && choice != 1 && choice != 2 && choice != 3 && choice != 4 && choice != 5) {
                System.out.printf("Entrada Inválida. Digite novamente (apenas de 0 a 4):\n");
                System.out.printf("\n-> ");
                choice = input.nextInt();
                System.out.printf("\n");
            }

            switch (choice) {
                case 1:
                    //Option 1. Adiciona um cliente na fila
                    //System.out.printf("Add Cliente:\n");

                    //Cria um cliente
                    ClientInfo key = new ClientInfo();
                    //Cria uma lista de assuntos
                    SubjectList subjects = new SubjectList();

                    //Armazenando dados de um cliente
                    System.out.printf("Nome do cliente:\n");
                    System.out.printf("\n-> ");

                    name = input.next();

                    //name = input.nextLine();
                    /*
					while(!input.next().isEmpty()) { // only when you enter literal string
						//do your scanner stuff here
						//name = input.next();
					}
					//*/
                    //name = "unknown";
                    System.out.printf("CPF do cliente:\n");
                    System.out.printf("\n-> ");
                    cpf = input.next();
                    //cpf = "0000.000";

                    System.out.printf("Idade do cliente:\n");
                    System.out.printf("\n-> ");
                    age = input.nextInt();
                    //age = 66;

                    System.out.printf("Hora de chegada:\n");
                    System.out.printf("\n-> ");
                    // Verificar a opção de obter a hora do computador
                    arrivalTime = input.nextInt();
                    //arrivalTime = 99;
                    departureTime = 0;

                    //Adiciona dados do cliente na variável key (class ClientInfo)
                    key.setName(name);
                    key.setNumber(cpf);
                    key.setAge(age);
                    key.setArrivalTime(arrivalTime);
                    key.setDepartureTime(departureTime);

                    //Cadastrando assuntos do cliente
                    do {
                        System.out.printf("Tipo de assunto:\n");
                        System.out.printf("\n-> ");
                        subjectType = input.next();
                        addSubject = true;

                        if (subjectType.equals("0"))
                            addSubject = false;
                        else {
                            System.out.printf("Descrição:\n");
                            System.out.printf("\n-> ");
                            description = input.next();
                            //description = "Configuração de Arquivos";

                            System.out.printf("Providência:\n");
                            System.out.printf("\n-> ");
                            providence = input.next();
                            //providence = "FormatarPC";

                            System.out.printf("Duração:\n");
                            System.out.printf("\n-> ");
                            duration = input.nextDouble();
                            //duration = 15;

                            System.out.printf("Titulo do Assunto:\n");
                            System.out.printf("\n-> ");
                            title = input.next();
                            //title = "Arquivo";

                            System.out.printf("Urgência do Assunto:\n");
                            System.out.printf("\n-> ");
                            urgency = input.nextDouble();
                            //urgency = 7; //[0-10]

                            TypeSubject item = new TypeSubject(description, providence, duration);
                            item.setType(Integer.parseInt(subjectType));
                            item.setTitle(title);
                            item.setUrgency(urgency);

                            //Adiciona o Assunto/Tarefa na lista de assuntos do cliente
                            subjects.addSubject(item);

                            // Armazena no histórico o tipo de assunto, urgencia do assunto e a sua duração
                            // Tal histórico possui uma lista de dias (listDay) , cada listDay possui uma hash
                            // Cada hash possui como chave o TIPO do Assunto, a informação armazenada em cada célula
                            // da hash possui um contador que armazena a urgência do assunto e ...
                            // A SOMA DE TEMPOS DO SERVIÇO do RESPECTIVO ASSUNTO
                            //historic.addType( String.valueOf( subjectType ), description , duration , day);
                        }

                    } while (addSubject);

                    // Adiciona lista de assuntos para o cliente (key)
                    key.setSubjects(subjects);
                    // Calcula a prioridade do cliente baseado na lista de serviços, idade, tempo etc.
                    key.calcPriority();
                    indexNumArray++;

                    // Imprimindo Heap antes da inserção
                    if (array != null) {
                        System.out.printf("\nFila Antes \n");
                        System.out.printf("----------------------------------------------------------------------------------------------------------------------------------------\n");
                        heap.printHeap(array);

                        ClientInfo[] newArray = new ClientInfo[indexNumArray];
                        // Insere em um vetor os clintes
                        for (int i = 0; i < array.length; i++)
                            newArray[i] = array[i]; //array[i].calcPriority();
                        //System.out.println("P[" + i + "] = " + array[i].getPriority());

                        newArray[indexNumArray - 1] = key;
                        array = new ClientInfo[indexNumArray];
                        array = newArray;
                    } else {
                        array = new ClientInfo[indexNumArray];
                        array[0] = key;
                        //array[0].calcPriority();
                    }

                    //Criando a Heap com os elementos do vetor
                    heap.BuildMaxHeap(array);

                    System.out.printf("\nFila Antes \n");
                    System.out.printf("----------------------------------------------------------------------------------------------------------------------------------------\n");

                    // Imprimindo a Heap Depois da Inserção
                    heap.printHeap(array);

                    break;
                case 2:
                    //Option 2. Atende o cliente do inicio da fila
                    if (indexNumArray > 0) {
                        System.out.printf("Cliente " + array[0].getName() + " atendido p[" + array[0].getPriority() + "] \n");

                        System.out.printf("\nFila Antes \n");
                        System.out.printf("----------------------------------------------------------------------------------------------------------------------------------------\n");

                        heap.printHeap(array);

                        // Adiciona no histórico do dia o cliente removido da Heap
                        historic.addClient(array[0]);

                        ClientInfo[] newArray = new ClientInfo[indexNumArray - 1];
                        heap.setSize(indexNumArray - 1);

                        //System.out.println("************"  + newArray.length + " " +array.length);
                        for (int i = 1; i < array.length; i++)
                            newArray[i - 1] = array[i];

                        indexNumArray--;
                        array = new ClientInfo[indexNumArray];
                        array = newArray;

                        heap.BuildMaxHeap(array);
                        System.out.printf("\nFila Depois \n");
                        System.out.printf("----------------------------------------------------------------------------------------------------------------------------------------\n");
                        heap.printHeap(array);
                    } else
                        System.out.printf("\nFila Vazia, insira elementos \n");
                    break;
                case 3:
                    if (indexNumArray > 0)
                        heap.printHeap(array);

                    // Aplique heap.HeapSort(array); para ordenar a Heap decescentemente
                    else
                        System.out.printf("\nFila Vazia, insira elementos \n");
                    break;
                case 4:
                    int option,
                     option2;
                    System.out.printf("1. Ler do arquivo\n");
                    System.out.printf("2. Salvar .csv\n");
                    System.out.printf("3. Alterar Prioridade\n");
                    System.out.printf("4. Encerrar o dia\n");
                    System.out.printf("\n\nObs.: Encerrar o dia continua manipulando a fila no próximo dia.\n\n");
                    System.out.printf("\n-> ");
                    option = input.nextInt();

                    switch (option) {
                        case 1:
                            //Option 3. Adiciona uma lista de clientes a partir de um .CSV com serviços e prioridades distintas na fila

                            //System.out.printf("Digite o nome do arquivo:\n");
                            //System.out.printf("\n-> ");
                            //myPathFile = input.next();
                            myPathFile = "src/file/input2.csv";

                            //Leitura do arquivo .csv
                            myCSVFile = new CSVReader(myPathFile);
                            //myCSVFile.printData();
                            indexNumArray = myCSVFile.getSizeList();
                            heap.setSize(indexNumArray);
                            array = new ClientInfo[indexNumArray];

                            // Insere em um vetor os clintes na ordem de chagada
                            for (int i = 0; i < heap.getSize(); i++) {
                                array[i] = myCSVFile.getClient(i);
                                array[i].calcPriority();
                                System.out.println("P[" + i + "] = " + array[i].getPriority());
                            }

                            //heap.setHeap(array);
                            //Criando a Heap com os elementos do vetor
                            heap.BuildMaxHeap(array);

                            // Imprimindo a Heap
                            heap.printHeap(array);
                            break;
                        case 2:
                            // Salva heap no .csv

                            //System.out.printf("Digite o nome do arquivo:\n");
                            //System.out.printf("\n-> ");
                            //myPathFile = input.next();
                            myPathFile = "src/file/input2.csv";

                            //Leitura do arquivo .csv
                            myCSVFile = new CSVReader(myPathFile);
                            //myCSVFile.printData();
                            indexNumArray = myCSVFile.getSizeList();
                            heap.setSize(indexNumArray);
                            array = new ClientInfo[indexNumArray];

                            // Insere em um vetor os clintes na ordem de chagada
                            for (int i = 0; i < heap.getSize(); i++) {
                                array[i] = myCSVFile.getClient(i);
                                array[i].calcPriority();
                                //System.out.println("P[" + i + "] = " + array[i].getPriority());
                            }

                            //heap.setHeap(array);
                            //Criando a Heap com os elementos do vetor
                            heap.BuildMaxHeap(array);

                            // Imprimindo a Heap
                            heap.printHeap(array);
                            System.out.printf("Salvo em file.csv\n");
                            break;
                        case 3:
                            double valor = 0;
                            System.out.printf("Digite ordem do cliente (número)\n");
                            System.out.printf("\n-> ");
                            option2 = input.nextInt();

                            System.out.printf("Digite a nova prioridade para o " + option2 + "-ésimo cliente (número)\n");
                            System.out.printf("\n-> ");
                            valor = input.nextDouble();

                            option2--;// Avalia pelo indice do vetor

                            //HeapIncreaseKey(ClientInfo[] array, int i, ClientInfo key)
                            ClientInfo key2 = array[option2];
                            key2.setPriority(valor);
                            heap.HeapIncreaseKey(array, option2, key2);

                            //Adapatando
                            ClientInfo[] newArray = new ClientInfo[indexNumArray];
                            heap.setSize(indexNumArray);
                            for (int i = 0; i < array.length; i++)
                                newArray[i] = array[i];
                            array = new ClientInfo[indexNumArray];
                            array = newArray;
                            heap.BuildMaxHeap(array);

                            break;
                        case 4:
                            historic.newDay();
                        //indexNumArray = 0;
                        default:
                            break;
                    }
                    break;
                case 5:
                    //Option 4. Gerar Estatística
                    System.out.printf("... Estatística ... \n");
                    System.out.printf("1. Histórico do dia\n");
                    System.out.printf("2. Histórico dos PRIMEIROS k dias\n");
                    System.out.printf("3. Histórico dos ÚLTIMOS k dias\n");
                    System.out.printf("\n-> ");
                    option = input.nextInt();

                    System.out.printf("Tipo de Assunto:\n");
                    System.out.printf("\n-> ");
                    subjectType = input.next();

                    if (indexNumArray > 0)
                        switch (option) {
                            case 1:
                                // Mostra o histórico do dia corrente
                                historic.getHistoricCorrentDay(Integer.parseInt(subjectType));
                                break;
                            case 2:
                                System.out.printf("Quantidade de dias:\n");
                                System.out.printf("\n-> ");
                                days = input.nextInt();

                                // Obtem o histórico dos PRIMEIROS "k" dias
                                historic.getHistoricOfFirstDays(Integer.parseInt(subjectType), days);
                                break;
                            case 3:
                                System.out.printf("Quantidade de dias:\n");
                                System.out.printf("\n-> ");
                                days = input.nextInt();

                                // Obtem o histórico dos ÚLTIMOS "k" dias
                                historic.getHistoricOfLastDays(Integer.parseInt(subjectType), days);
                                break;
                            default:
                                break;
                        }
                    else
                        System.out.printf("\nFila Vazia, insira elementos \n");
                    break;

                default:
                    break;
            }
            if (choice == 0) {
                if (indexNumArray > 0) {
                    // Ordenando a Heap
                    heap.HeapSort(array);

                    // Imprimindo a Heap
                    heap.printHeap(array);
                }
                exit = false;
            }

        } while (exit);
        input.close();
        System.out.printf("finished ... \n");
    }//end Menu

}//end MenuOption
