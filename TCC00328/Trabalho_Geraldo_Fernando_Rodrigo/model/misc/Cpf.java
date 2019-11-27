package uff.ic.lleme.tcc00328.trabalhos.s20192.Geraldo_Rodrigo_Fernando.model.misc

public class Cpf {
    private String cpf;

    public Cpf(String cpf) {
        this.cpf = cpf;
    }

    public boolean checkCpf(){
        if (this.cpf.length() != 11 || this.cpf.equals("00000000000") || this.cpf.equals("11111111111") || this.cpf.equals("22222222222") || this.cpf.equals("33333333333") || this.cpf.equals("44444444444") || this.cpf.equals("55555555555") || this.cpf.equals("66666666666") || this.cpf.equals("77777777777") || this.cpf.equals("88888888888") || this.cpf.equals("99999999999")) return false;
        int add1 = 0;
        for (int i = 0; i < 9; i++) {
            if (this.cpf.charAt(i) == '0' || this.cpf.charAt(i) == '1' || this.cpf.charAt(i) == '2' || this.cpf.charAt(i) == '3' || this.cpf.charAt(i) == '4' || this.cpf.charAt(i) == '5' || this.cpf.charAt(i) == '6' || this.cpf.charAt(i) == '7' || this.cpf.charAt(i) == '8' || this.cpf.charAt(i) == '9') {
                add1 += Character.getNumericValue(this.cpf.charAt(i)) * (10 - i);
            } else return false;
        }
        int d1 = (add1*10)%11;
        if (d1 == 10) d1 = 0;
        if (d1 == Character.getNumericValue(this.cpf.charAt(9))) {
            int add2 = 0;
            for (int i = 0; i < 10; i++) {
                add2 += Character.getNumericValue(this.cpf.charAt(i)) * (11 - i);
            }
            int d2 = (add2*10)%11;
            if (d2 == 10) d2 = 0;
            return d2 == Character.getNumericValue(this.cpf.charAt(10));
        } else return false;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCpf() {
        return cpf;
    }
}
