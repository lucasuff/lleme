package uff.ic.lleme.tcc00328.exercicios.jogoDeDados;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MesaDeDado {

    private Regra regraDoJogo = null;
    private Map<Partida, Integer> primeiroPonto = null;
    private Map<Partida, Integer> ultimoPonto = null;
    private List<Partida> ganhadores = null;
    private List<Partida> perdedores = null;

    public MesaDeDado(Regra regra) {
        this.regraDoJogo = regra;
        this.ganhadores = new ArrayList<>();
        this.perdedores = new ArrayList<>();
        this.primeiroPonto = new HashMap<>();
        this.ultimoPonto = new HashMap<>();
    }

    public Resultado decidirResultado(Dado[] dados, Partida partida) {
        if (ganhadores.contains(partida))
            return Resultado.GANHOU;
        else if (perdedores.contains(partida))
            return Resultado.PERDEU;
        else {
            Integer pontosObtidos = somarPontos(dados);
            Resultado resultado = regraDoJogo.decidirResultado(obterPontoInicial(partida), pontosObtidos);

            guardarPontoInicial(partida, pontosObtidos);
            guardarUltimoPonto(partida, pontosObtidos);

            if (resultado == Resultado.GANHOU)
                ganhadores.add(partida);
            else if (resultado == Resultado.PERDEU)
                perdedores.add(partida);
            return resultado;
        }
    }

    private void guardarPontoInicial(Partida partida, Integer pontosObtidos) {
        if (obterPontoInicial(partida) == null)
            this.primeiroPonto.put(partida, new Integer(pontosObtidos));
    }

    private void guardarUltimoPonto(Partida partida, Integer pontosObtidos) {
        this.ultimoPonto.put(partida, new Integer(pontosObtidos));
    }

    public Integer obterPontoInicial(Partida partida) {
        return primeiroPonto.get(partida);
    }

    public Integer obterUltimoPonto(Partida partida) {
        return ultimoPonto.get(partida);
    }

    public Resultado obterResultado(Partida partida) {
        if (ganhadores.contains(partida))
            return Resultado.GANHOU;
        else if (perdedores.contains(partida))
            return Resultado.PERDEU;
        else
            return Resultado.INDEFINIDO;
    }

    public Dado[] obterDados() {
        Dado[] dados = new Dado[2];
        dados[0] = new Dado();
        dados[1] = new Dado();
        return dados;
    }

    private Integer somarPontos(Dado[] dados) {
        int pontosObtidos = 0;
        for (Dado dado : dados)
            pontosObtidos += dado.getValor();
        if (pontosObtidos > 0)
            return pontosObtidos;
        else
            return null;
    }
}
