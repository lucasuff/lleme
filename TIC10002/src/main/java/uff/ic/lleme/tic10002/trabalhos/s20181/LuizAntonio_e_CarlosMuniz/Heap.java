/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uff.ic.lleme.tic10002.trabalhos.s20181.LuizAntonio_e_CarlosMuniz;

import java.util.Calendar;

/**
 *
 * @author Luiz Antonio
 */
public class Heap {

    private class Node {

        public Atendimento element;
        public double priority = 0d;

        public Node(Atendimento element, double priority) {
            this.element = element;
            this.priority = priority;
        }

        public Atendimento getElement() {
            return this.element;
        }

        public void setElement(Atendimento element) {
            this.element = element;
        }

        public double getPriority() {
            return this.priority;
        }

        public void setPriority(double priority) {
            this.priority = priority;
        }
    }

    private int length = 50;
    private Node[] heap = new Node[length];
    private int last = -1;

    public Atendimento search() {
        if (this.last == -1) {
            System.out.println("The Heap is empty!");
            return null;
        } else
            return this.heap[0].element;
    }

    private void up(int elementIndex) {
        int parentIndex = (elementIndex > 0) ? (elementIndex + 1) / 2 : 0;
        parentIndex--;

        if (parentIndex >= 0)
            if (heap[elementIndex].priority > heap[parentIndex].priority) {
                trade(elementIndex, parentIndex);
                up(parentIndex);
            }
    }

    private void down(int elementIndex) {
        int childIndex = (elementIndex * 2) + 1;

        if (childIndex <= this.last) {
            if (childIndex < this.last)
                if (this.heap[childIndex + 1].priority > this.heap[childIndex].priority)
                    childIndex += 1;
            if (this.heap[elementIndex].priority < this.heap[childIndex].priority) {
                trade(childIndex, elementIndex);
                down(childIndex);
            }
        }
    }

    private void trade(int childIndex, int parentIndex) {
        Node parent = this.heap[parentIndex];
        this.heap[parentIndex] = this.heap[childIndex];
        this.heap[childIndex] = parent;
    }

    private void increase() {
        int length = this.length + 50;
        Node[] heap = new Node[length];

        for (int index = 0; index < this.length; index++)
            heap[index] = this.heap[index];

        this.heap = heap;
        this.length = length;
    }

    private void change(int elementIndex, int newPriority) {
        double currentPriority = this.heap[elementIndex].priority;
        this.heap[elementIndex].priority = newPriority;

        if (currentPriority > newPriority)
            down(elementIndex);
        if (currentPriority < newPriority)
            up(elementIndex);
    }

    private void insert(Node element) {
        this.last++;
        this.heap[this.last] = element;
        up(this.last);
    }

    public void insert(Atendimento element) {
        if (this.last == this.length - 1)
            increase();

        Node node = new Node(element, 0d);
        insert(node);
    }

    public Atendimento remove(Calendar horaAtendimento) {
        if (this.last < 0) {
            System.out.println("A lista está vazia!");
            return null;
        }

        sort(horaAtendimento);

        Atendimento removed = heap[0].element;

        this.heap[0] = heap[this.last];
        this.heap[this.last] = null;
        this.last--;
        down(0);

        return removed;
    }

    public void print() {
        System.out.print("[");
        for (int index = 0; index <= this.last; index++) {
            System.out.print(this.heap[index].priority);
            if (index != last)
                System.out.print(",");
        }
        System.out.println("]");
    }

    private void sort(Calendar horaAtendimento) {
        updatePriority(horaAtendimento);

        Node[] oldHeap = this.heap;
        int oldLast = this.last;

        // reset the values
        this.heap = new Node[this.length];
        this.last = -1;

        for (int index = 0; index <= oldLast; index++) {
            Node element = oldHeap[index];
            insert(element);
        }
    }

    private void updatePriority(Calendar horaAtendimento) {
        for (int index = 0; index <= this.last; index++) {
            Node element = this.heap[index];
            calculatePriority(element, horaAtendimento);
        }
    }

    private void calculatePriority(Node element, Calendar horaAtendimento) {
        Atendimento atendimento = element.getElement();
        int idadeCliente = atendimento.getCliente().getIdade();

        Calendar horaChegada = atendimento.getHoraChegada();
        int esperaEmMinutos = calculaTempoDeEspera(horaChegada, horaAtendimento);

        double totalUrgencia = 0d;
        double quantidade = 0d;
        Assunto[] assuntos = atendimento.getAssuntos();
        for (Assunto assunto : assuntos) {
            totalUrgencia += assunto.getTipo().getUrgencia();
            quantidade++;
        }
        double mediaUrgencias = (quantidade > 0) ? totalUrgencia / quantidade : 0d;

        double prioridadeIdade = idadeCliente / 65d;
        double prioridadeEspera = esperaEmMinutos / 15d;
        double prioridadeUrgencias = mediaUrgencias / 10d;

        double prioridadeTotal = (prioridadeIdade + prioridadeEspera + prioridadeUrgencias) / 3d;

        element.setPriority(prioridadeTotal);
    }

    private int calculaTempoDeEspera(Calendar inicio, Calendar fim) {
        int startHour = inicio.get(Calendar.HOUR_OF_DAY);
        int startMinutes = inicio.get(Calendar.MINUTE);

        int endHour = fim.get(Calendar.HOUR_OF_DAY);
        int endMinutes = fim.get(Calendar.MINUTE);

        int startDay = inicio.get(Calendar.DAY_OF_MONTH);
        int startMonth = inicio.get(Calendar.MONTH);

        int endDay = fim.get(Calendar.DAY_OF_MONTH);
        int endMonth = fim.get(Calendar.MONTH);

        // situação onde o cliente foi atendido após o dia de chegada
        if ((endDay > startDay) || (endMonth > startMonth))
            /**
             * Para simplificação da lógica, caso um cliente tenha sido atendido
             * X dias após o dia da sua chegada, assume-se sempre o valor 1 para
             * X.
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
        int tempoDeEsperaEmMinutos = hoursIntoMinutes + diffMinutes;
        return tempoDeEsperaEmMinutos;
    }
}
