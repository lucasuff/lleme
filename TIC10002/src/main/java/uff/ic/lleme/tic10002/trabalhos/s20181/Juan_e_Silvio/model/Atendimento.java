package uff.ic.lleme.tic10002.trabalhos.s20181.Juan_e_Silvio.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/*
As informações registradas para o atendimento são o CPF do cliente, seu nome e os assuntos
que desejam tratar (Atendimento {cliente, assuntos, horaChegada,
horaAtendimento}, Cliente {id, nome})
 */
public class Atendimento {

    private Cliente cliente;
    private Assunto[] assuntos;
    private String horaChegada;
    private String inicioAtendimento;

// Imprimir mensagnes de debug?
    private boolean debug = false;

    public Atendimento(Cliente cliente, Assunto[] assuntos) {
        this.cliente = cliente;
        this.assuntos = assuntos;
        this.horaChegada = getTimeStamp();
        this.inicioAtendimento = null;
    }

    public void inicioAtendimento() {
        this.inicioAtendimento = getTimeStamp();
    }

    private String getTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }

    private long getTempoDeEspera() {
        try {
            long tempoEspera = 0;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date chegada = sdf.parse(horaChegada);
            long epochChegada = chegada.getTime();
            if (inicioAtendimento != null) {
                Date atendimento = sdf.parse(inicioAtendimento);
                long epochAtendimento = atendimento.getTime();
                tempoEspera = epochAtendimento - epochChegada;
            } else
                tempoEspera = System.currentTimeMillis() - epochChegada;
            if (debug)
                System.out.println("DEBUG GET TEMPO ESPERA:" + tempoEspera);
            return tempoEspera;
        } catch (ParseException e) {
            System.err.println("Erro na conversão de datas!");
        }
        return -1;
    }

    private String printTempoDeEspera() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String tempo = sdf.format(new Date(getTempoDeEspera()));
        System.out.println(tempo);
        return tempo;
    }

    public Assunto[] getAssuntos() {
        return assuntos;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public float getPrioridade() {
        int idade = cliente.getIdade();
        int somaUrgencias = 0;
        for (int i = 0; i < assuntos.length; i++)
            somaUrgencias += assuntos[i].getUrgencia();
        float mediaUrgencias = somaUrgencias / assuntos.length;
        long minutosDeEspera = getTempoDeEspera() / 60000; //Converte de millis para minutos
        float prioridade = ((idade / 65f) + (minutosDeEspera / 15f) + (mediaUrgencias / 10f)) / 3;
        if (debug)
            System.out.println(String.format("DEBUG Prioridade: %s [IDADE:%f,ESPERA:%f,URGENCIAS:%f] ", cliente.getNome(), (idade / 65f), (minutosDeEspera / 15f), (mediaUrgencias / 10f)));
        return prioridade;
    }

    public String getHoraAtendimento() {
        return inicioAtendimento;
    }

    public String toShortString() {
        StringBuilder atendSTR = new StringBuilder();
        atendSTR.append("[C:").append(cliente.getNome()).append("|A:[");
        for (int i = 0; i < assuntos.length; i++)
            atendSTR.append("{").append(assuntos[i].toShortString()).append("}");
        atendSTR.append("]");
        return atendSTR.toString();
    }
}
