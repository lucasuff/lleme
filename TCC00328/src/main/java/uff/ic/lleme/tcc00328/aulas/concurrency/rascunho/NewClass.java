package uff.ic.lleme.tcc00328.aulas.concurrency.rascunho;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class NewClass {

    private Teste a = null;

    private class Teste {

        public NewClass1 a = null;
    }

    public NewClass() {
        a = new Teste();
    }

    public static void main(String[] args) throws IOException {
        OutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream s = new ObjectOutputStream(out);

    }

}
