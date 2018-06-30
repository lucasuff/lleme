package uff.ic.lleme.tic10002.trabalhos.s20181.Juan_e_Silvio.src.model;

/*
Os assuntos informados são registrados
com o seu tipo, a descrição fornecida pelo cliente, as providências tomadas e a duração do
atendimento para aquele assunto (Assunto {tipo, descrição, providências,
duraçãoAtendimento}).
 */
public class Assunto {

    private TipoAssunto tipo;
    private String descricao;
    private String providencias;
    private int duracaoAtendimento;

    //Abordagem com teclado
    public Assunto(TipoAssunto tipo, String descricao) {
        this.tipo = tipo;
        this.descricao = descricao;
    }

    //Abordagem hardcoded
    public Assunto(TipoAssunto tipo, String descricao, String providencias, int duracaoAtendimento) {
        this.tipo = tipo;
        this.descricao = descricao;
        this.providencias = providencias;
        this.duracaoAtendimento = duracaoAtendimento;
    }

    public void setProvidencias(String providencias) {
        this.providencias = providencias;
    }

    public void setDuracaoAtendimento(int duracaoAtendimento) {
        this.duracaoAtendimento = duracaoAtendimento;
    }

    public TipoAssunto getTipoAssunto() {
        return tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getProvidencias() {
        return providencias;
    }

    public int getDuracaoAtendimento() {
        return duracaoAtendimento;
    }

    public int getUrgencia() {
        return tipo.getUrgencia();
    }

    public String toShortString() {
        return "T:" + tipo.getTipo() + "|U:" + tipo.getUrgencia() + "|D:" + duracaoAtendimento;
    }

}
