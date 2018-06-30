package uff.ic.lleme.tic10002.trabalhos.s20181.Bruna_Cleomar_Patrick.src.trabalho_ed.model;

import uff.ic.lleme.tic10002.trabalhos.s20181.Bruna_Cleomar_Patrick.src.trabalho_ed.model.TipoAssunto;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import uff.ic.lleme.tic10002.trabalhos.s20181.Bruna_Cleomar_Patrick.src.trabalho_ed.HashTable;

public class TiposAssuntos extends HashTable {

    public TiposAssuntos(int tamanho) {
        super(tamanho);
        // TODO Auto-generated constructor stub
    }

    public TiposAssuntos(String filePath) {
        super(100);
        try {
            CarregarDoArquivo(filePath);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void CarregarDoArquivo(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        try {
            String line = br.readLine();

            while (line != null) {
                String[] data = line.split(";");
                TipoAssunto tpAssunto = new TipoAssunto(data[0].trim(), data[1].trim(), Integer.parseInt(data[2].trim()));
                this.Inserir(tpAssunto.getChave(), tpAssunto);
                line = br.readLine();
            }
        } finally {
            br.close();
        }
    }
}
