package uff.ic.lleme.mavenproject1;

import java.util.Scanner;

public class Conversao {

    public static float cels;/* armazena temperatura em oC */
    public static float fahr;/* armazena temperatura em oF */

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Digite a temperatura em oC: ");
        cels = in.nextFloat();
        fahr = celsius_fahrenheit(cels);
        System.out.println("Temperatura em oF: ");
        System.out.println(fahr);

    }

    public static float celsius_fahrenheit(float tc) {
        float tf;
        /* 6 */
        tf = 1.8f * tc + 32;
        /* 7 */
        return tf;
        /* 8 */
    }

}
