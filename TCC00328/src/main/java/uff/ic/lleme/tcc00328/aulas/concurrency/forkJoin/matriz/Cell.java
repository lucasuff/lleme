/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tcc00328.aulas.concurrency.forkJoin.matriz;

/**
 *
 * @author lapaesleme
 */
public class Cell {

    private Double value = null;

    public synchronized Double getValue() {
        if (value == null)
            this.value = 0.0;
        return value;
    }

    public synchronized void setValue(double value) {
        this.value = value;
    }
}
