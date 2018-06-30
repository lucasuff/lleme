/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_CarlosMuniz;

import java.util.Calendar;
import java.util.Random;

/**
 *
 * @author Luiz Antonio
 */
public class ServicoDeAtendimento {

    public static Heap heap = new Heap();
    public static HashMap hashMap = new HashMap();

    public static ListaDeAtendimentos atendimentos = new ListaDeAtendimentos();
    public static ListaDeAtendimentosEncerrados encerrados = new ListaDeAtendimentosEncerrados();

    public static void recepcionar(Cliente cliente, Assunto[] assuntos) {
        Calendar horaChegada = Calendar.getInstance();
        Atendimento atendimento = new Atendimento(cliente, assuntos, horaChegada);
        heap.insert(atendimento);
    }

    public static Atendimento atender() {
        Calendar horaAtendimento = Calendar.getInstance();
        Atendimento atendimento = heap.remove(horaAtendimento);
        atendimento.setHoraAtendimento(horaAtendimento);
        atendimentos.incluir(atendimento);
        return atendimento;
    }

    public static void encerrar(int idCliente) {
        Calendar horaEncerramento = Calendar.getInstance();

        Atendimento atendimento = atendimentos.buscar(idCliente);
        if (atendimento != null) {
            Calendar horaAtendimento = atendimento.getHoraAtendimento();

            // duração do atendimento em minutos
            int duracao = calculaDuracaoAtendimento(horaAtendimento, horaEncerramento);

            Assunto[] assuntos = atendimento.getAssuntos();

            Random random = new Random();
            for (Assunto assunto : assuntos) {
                // Configura a duração em minutos de cada assunto e as providências. É apenas uma simplificação
                int duracaoAssunto = random.nextInt(180) + 1;
                assunto.setDuracaoAtendimento(duracaoAssunto);
                assunto.setProvidencias("Voltar daqui a 3 meses para reavaliação");
            }

            encerrados.incluir(atendimento);
            hashMap.incluir(atendimento);
        }
    }

    public static double gerarEstatistica(TipoAssunto tipo) {
        Calendar dataAtendimento = Calendar.getInstance();
        double tempoMedio = encerrados.calculaTempoMedioDeAtendimento(tipo, dataAtendimento);
        return tempoMedio;
    }

    /**
     * Calcula a duração do atendimento em minutos
     *
     * @param inicio
     * @param fim
     * @return
     */
    private static int calculaDuracaoAtendimento(Calendar inicio, Calendar fim) {
        int startHour = inicio.get(Calendar.HOUR_OF_DAY);
        int startMinutes = inicio.get(Calendar.MINUTE);

        int endHour = fim.get(Calendar.HOUR_OF_DAY);
        int endMinutes = fim.get(Calendar.MINUTE);

        int startDay = inicio.get(Calendar.DAY_OF_MONTH);
        int startMonth = inicio.get(Calendar.MONTH);

        int endDay = fim.get(Calendar.DAY_OF_MONTH);
        int endMonth = fim.get(Calendar.MONTH);

        // situação onde o atendimento se encerrou após o dia de início
        if ((endDay > startDay) || (endMonth >= startMonth))
            /**
             * Para simplificação da lógica, caso um atendimento tenha sido
             * encerrado X dias após o dia em que o mesmo foi iniciado,
             * assume-se sempre o valor 1 para X.
             */
            endHour += 24;

        int diffHour = endHour - startHour;
        int diffMinutes = endMinutes - startMinutes;

        if (endMinutes < startMinutes) {
            diffHour--;
            int newEndMinutes = endMinutes + 60;
            diffMinutes = newEndMinutes - startMinutes;
        }

        int hoursIntoMinutes = diffHour * 60;
        int duracaoAtendimento = hoursIntoMinutes + diffMinutes;
        return duracaoAtendimento;
    }

}
