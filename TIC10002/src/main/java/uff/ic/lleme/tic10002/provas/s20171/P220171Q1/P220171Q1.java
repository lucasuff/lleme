package uff.ic.lleme.tic10002.provas.s20171.P220171Q1;

public class P220171Q1 {

    public static class No {

        public String conteudo = null;
        public No noDireito = null;
        public No noEsquerdo = null;

        public No(String conteudo) {
            this.conteudo = conteudo;
        }

        public double calcular() {
            if (noDireito == null && noEsquerdo == null)
                return Double.parseDouble(conteudo);
            else if (conteudo.equals("+"))
                return noDireito.calcular() + noEsquerdo.calcular();
            else if (conteudo.equals("-"))
                return noDireito.calcular() - noEsquerdo.calcular();
            else if (conteudo.equals("*"))
                return noDireito.calcular() * noEsquerdo.calcular();
            else if (conteudo.equals("/"))
                return noDireito.calcular() / noEsquerdo.calcular();
            else
                return 0;
        }
    }

    public static void main(String[] args) {
        No dif = new No("-");
        No prod1 = new No("*");
        No prod2 = new No("*");
        No soma = new No("+");
        No a = new No("1");
        No b = new No("2");
        No c = new No("4");
        No d = new No("7");
        No e = new No("3");

        dif.noDireito = prod1;
        prod1.noDireito = a;
        prod1.noEsquerdo = b;
        dif.noEsquerdo = prod2;
        prod2.noDireito = soma;
        soma.noDireito = c;
        soma.noEsquerdo = d;
        prod2.noEsquerdo = e;

        System.out.println(dif.calcular());
    }
}
