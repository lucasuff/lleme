/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_CarlosMuniz;

/**
 *
 * @author Luiz Antonio
 */
public class TipoAssunto {

    private String tipo;
    private String titulo;
    private int urgencia;

    public TipoAssunto(String tipo, String titulo, int urgencia) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.urgencia = urgencia;
    }

    public String getTipo() {
        return this.tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getUrgencia() {
        return this.urgencia;
    }

    public void setUrgencia(int urgencia) {
        this.urgencia = urgencia;
    }

    @Override
    public String toString() {
        return "[Tipo do Assunto: " + this.tipo + ", Título do Assunto: " + this.titulo + ", Urgência do Assunto: " + this.urgencia + "]";
    }
}
