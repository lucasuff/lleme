package uff.ic.lleme.tic10002.provas.s20171.P220171Q3;

public class P220171Q3 {

    private No raiz = null;
    private int quantidadeNos = 0;
    private boolean avl = false;

    public class No {

        public Integer chave = null;
        public No direita = null;
        public No esquerda = null;
        public int saldo = 0;

        public No(int chave) {
            this.chave = chave;
        }

        public void printTree() {
            if (esquerda != null)
                esquerda.printTree(false, "");
            printNodeValue();
            if (direita != null)
                direita.printTree(true, "");
        }

        private void printNodeValue() {
            System.out.print("" + chave);
            System.out.print('\n');
        }

        private void printTree(boolean isRight, String indent) {
            if (esquerda != null)
                esquerda.printTree(false, indent + (isRight ? " |      " : "        "));
            System.out.print(indent);
            if (isRight)
                System.out.print(" \\");
            else
                System.out.print(" /");
            System.out.print("----- ");
            printNodeValue();
            if (direita != null)
                direita.printTree(true, indent + (isRight ? "        " : " |      "));
        }

    }

    public boolean ehAVL() {
        avl = true;
        ehAVL(raiz, 1);
        return avl;
    }

    private int ehAVL(No no, int nivel) {
        if (no == null)
            return nivel;
        int alturaD = ehAVL(no.direita, nivel + 1);
        int alturaE = ehAVL(no.esquerda, nivel + 1);
        no.saldo = Math.abs(alturaD - alturaE);
        if (no.saldo > 1)
            avl = false;
        return Math.max(alturaD, alturaE);
    }

    public void incluir(int chave) {
        if (raiz == null) {
            raiz = new No(chave);
            quantidadeNos = 1;
        } else
            incluir(raiz, new No(chave));
    }

    private void incluir(No no, No novoNo) {
        if (novoNo.chave > no.chave)
            if (no.esquerda == null) {
                no.esquerda = novoNo;
                quantidadeNos++;
            } else
                incluir(no.esquerda, novoNo);
        else if (novoNo.chave < no.chave)
            if (no.direita == null) {
                no.direita = novoNo;
                quantidadeNos++;
            } else
                incluir(no.direita, novoNo);
    }

    public void print() {
        raiz.printTree();
    }

    public static void main(String[] args) {
        P220171Q3 a = new P220171Q3();
        int[] nums = {34, 1, 78, 12, 14};
        for (int num : nums)
            a.incluir(num);
        System.out.println("" + a.ehAVL());
        a.print();
    }
}
