package uff.ic.lleme.tcc00328.aulas.concurrency.simpleThreads;

public class HelloRunnable implements Runnable {

    public void run() {
        System.out.println("Hello from a thread!");
    }

    public static void main(String args[]) {
        (new Thread(new HelloRunnable(), "ThreadHelloRunnable")).start();
        Thread t = new Thread(new HelloRunnable(), "ThreadHelloRunnable");
        t.start();
    }
}
