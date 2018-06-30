package trabalho_ed;

/**
 *
 * @author raphael
 */
public class TipoAssunto {

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    private int id = 0;
    private String tipo;
    private String titulo;
    private int urgencia = 0;

    //método construtor com inicialização
    public TipoAssunto(int id, String tipo, String titulo, int urgencia) {
        this.id = id;
        this.tipo = tipo;
        this.titulo = titulo;
        this.urgencia = urgencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(int urgencia) {
        this.urgencia = urgencia;
    }
}
