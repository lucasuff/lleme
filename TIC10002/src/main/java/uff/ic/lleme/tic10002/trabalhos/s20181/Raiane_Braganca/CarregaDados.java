package uff.ic.lleme.tic10002.trabalhos.s20181.Raiane_Braganca;

import uff.ic.lleme.tic10002.trabalhos.s20181.Raiane_Braganca.model.TipoAssunto;
import uff.ic.lleme.tic10002.trabalhos.s20181.Raiane_Braganca.model.Atendimento;
import uff.ic.lleme.tic10002.trabalhos.s20181.Raiane_Braganca.model.Cliente;
import uff.ic.lleme.tic10002.trabalhos.s20181.Raiane_Braganca.model.Assunto;
import java.io.File;
import java.util.Random;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class CarregaDados {

    public static int lerXML(
            String fileName,
            int codigoAtendimento,
            ListaLigada<Atendimento> listaDeEspera,
            TipoAssunto tiposDeAssuntos[]
    ) throws Exception {
        File fXmlFile = new File(fileName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);

        NodeList listaDeClientes = doc.getElementsByTagName("cliente");
        System.out.println("*************************************************************");
        System.out.println("************** CARREGANDO DADOS PARA O SISTEMA **************");
        System.out.println("*************************************************************");

        for (int id = 0; id < listaDeClientes.getLength(); id++) {
            Node nNode = listaDeClientes.item(id);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String nome = eElement.getElementsByTagName("nome").item(0).getTextContent();
                String cpf = eElement.getElementsByTagName("cpf").item(0).getTextContent();
                int idade = Integer.parseInt(eElement.getElementsByTagName("idade").item(0).getTextContent());
                System.out.println("Nome: " + nome);
                System.out.println("CPF: " + cpf);
                System.out.println("Idade: " + idade);

                NodeList listaDeAssuntos = eElement.getElementsByTagName("assunto");
                System.out.println("Assuntos:");

                Cliente cliente = new Cliente(id, nome, cpf, idade);
                ListaLigada<Assunto> assuntos = new ListaLigada();

                for (int j = 0; j < listaDeAssuntos.getLength(); j++) {
                    Node nNodeAssuntos = listaDeAssuntos.item(j);
                    if (nNodeAssuntos.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElementAssunto = (Element) nNodeAssuntos;
                        int assuntoTipoID = Integer.parseInt(eElementAssunto.getElementsByTagName("tipo").item(0).getTextContent());
                        String assuntoDescricao = eElementAssunto.getElementsByTagName("descricao").item(0).getTextContent();
                        System.out.println("  Tipo: " + assuntoTipoID);
                        System.out.println("  Descrição: " + assuntoDescricao);

                        Assunto assunto = new Assunto(tiposDeAssuntos[assuntoTipoID], assuntoDescricao, null, null);
                        assuntos.adicionaComPrioridade(assunto, new AssuntoComparator());
                    }
                }

                Atendimento objAtendimento = new Atendimento(codigoAtendimento);
                objAtendimento.importaCliente(cliente, assuntos);
                listaDeEspera.adiciona(objAtendimento);
                codigoAtendimento++;

                // Atrasa a importação em alguns segundos
                atrasoRandomico();
            }
            System.out.println("*************************************************************");
        }

        return codigoAtendimento;
    }

    public static void atrasoRandomico() throws InterruptedException {
        Random r = new Random();
        int low = 100;
        int high = 2000;
        int result = r.nextInt(high - low) + low;
        Thread.currentThread().sleep(result);
    }
}
