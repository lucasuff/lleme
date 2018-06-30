/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataStructure;

import Objects.Atendimento;

public class HeapNode {

    public Atendimento atendimento = null;
    public float prioridade = 0;

    public HeapNode(Atendimento atendimento) {
        this.atendimento = atendimento;
        this.prioridade = atendimento.getPrioridade();
    }

    public void recalculaPrioridade() {
        this.prioridade = atendimento.getPrioridade();
    }

    public Atendimento getAtendimento() {
        return atendimento;
    }

}
