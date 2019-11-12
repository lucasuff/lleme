package uff.ic.lleme.tcc00328.aulas.concurrency.simpleThreads;

public class HelloThread extends Thread {

    private String texto = null;

    public HelloThread(String texto) {
        this.texto = texto;
    }

    public void run() {
        System.out.println("Hello from a thread!" + texto);
    }

    public static void main(String args[]) {
        (new HelloThread("")).start();
    }
}
