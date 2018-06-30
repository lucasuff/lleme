/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.PabloMoreira.trabalhoed;

/**
 *
 * @author pablomoreira
 */
public class TipoAssunto {
    private int tipo;
    private String título;
    private int urgencia;

    public TipoAssunto(int tipo, String título, int urgencia) {
        this.tipo = tipo;
        this.título = título;
        this.urgencia = urgencia;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getTítulo() {
        return título;
    }

    public void setTítulo(String título) {
        this.título = título;
    }

    public int getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(int urgencia) {
        this.urgencia = urgencia;
    }
    
    
}
