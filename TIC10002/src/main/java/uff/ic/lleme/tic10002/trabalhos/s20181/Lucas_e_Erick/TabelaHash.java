package uff.ic.lleme.tic10002.trabalhos.s20181.Lucas_e_Erick;

import uff.ic.lleme.tic10002.trabalhos.s20181.Lucas_e_Erick.model.Hasheavel;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/*
* essa classe representa uma tabela hash por encadeamento externo. Ela possui um atributo que � um array de objects, cada posi��o desse array pode guardar uma lista que para essa aplica��o � do tipo Lista<String>. Essa lista por sua vez guarda o endere�o em disco para cada arquivo do encadeamento externo que teve colis�o na chave. A primeira chave usada para acessar o array de objects � o ano dobrado (metodo da dobra) e por isso diferentes anos podem colidir (ex: 1994 e 1805) por�m s�o poucos os que d�o colis�o. A segunda chave dado que j� sabemos o arquivo � para acessar a linha corretamente e a mesma � formada multiplicando o m�s por 100, depois somando o dia (ex: para o dia 22 de maio, a chave ficaria 0522), essa chave � ent�o o n�mero exato da linha do arquivo para buscarmos ou adicionarmos o objeto T.
* Essa  classe pode receber qualquer objeto contanto que este seja serializavel, compar�vel e hasheavel. No java cada objeto j� tem um metodo hashcode. No nosso caso criamos nossa propria chave por entendermos que esse tamb�m � um dos desafios do trabalho, logo como garantir que qualquer objeto aplicavel ao nosso caso tenha sua chave? a resposta � simples, basta o objeto do dom�nio implementar a interface hasheavel e ter o m�todo que gera a chave. No nosso caso o objeto T foi o objeto Estatistica, por�m poderia ser qualquer outro. Para facilitar a implementa��o as chaves foram pensadas por data, mas no futuro nada nos impede de com mais tempo gerar uma interface e mecanismos mais genericos para a chave ser qualquer coisa e n�o somente a data.
 */
@SuppressWarnings("rawtypes")
public class TabelaHash<T extends Comparable & Serializable & Hasheavel> implements Serializable {

    private static final long serialVersionUID = -8301764307615435524L;
    private Object[] t;
    private int tamanho;
    private String path;

    public TabelaHash(int tamanho, String nomeArquivo) {
        Path path = Paths.get("hashes", nomeArquivo);
        this.path = path.toString();
        try {
            if (Files.exists(path))
                deserializar(path);
            else {
                Files.createFile(path);
                this.tamanho = tamanho;
                this.t = new Object[tamanho];

            }
        } catch (IOException e) {

        }
    }

    /*
* Esse m�todo acha o local correto onde deve-se salvar o objeto T, caso a primeira chave usada no array de objects indique que n�o � lista, ou seja n�o h� objetos uma lista � instanciada e um endere�o para um arquivo � posto nela, o objeto � serializado nesse arquivo. Caso seja verificado que na posi��o correta do array de objects j� h� uma lista, quer dizer que houve colis�o e o endere�o para o arquivo � colocado nessa lista e o objeto T � serializado nesse endere�o. O custo de achar a posi��o no vetor de objetos � O(1), mesmo que haja colis�o s� criado mais um arquivo e adicionado seu endere�o na lista com custo O(1) e salvar o objeto T no arquivo tem custo O(1) tamb�m.
     */
    @SuppressWarnings("unchecked")
    public void add(T t) {
        int[] chaves = geraChaveExterna(t.hashcode());
        int hash = chaves[0];
        int linha = chaves[1];
        int gerador = chaves[2];
        String filename = new String(Integer.toString(gerador) + ".data");
        Path path = Paths.get("hashes", filename);
        if (hash >= 0 && hash < this.t.length) {
            if (this.t[hash] == null) {
                this.t[hash] = new Lista<>();
                ((Lista<String>) this.t[hash]).add(path.toString(), 0);
            } else {
                Lista<String> lista = ((Lista<String>) this.t[hash]);
                lista.add(path.toString(), lista.getTamanho());
            }
            RandomAccessFile raf;
            ByteArrayOutputStream baos = null;
            try {
                baos = new ByteArrayOutputStream();
                ObjectOutputStream oos;
                oos = new ObjectOutputStream(baos);
                oos.writeObject(t);
                oos.flush();
                int tamObjeto = 874;
                raf = new RandomAccessFile(path.toFile(), "rw");
                if (Files.exists(path))
                    raf.setLength(1231 * tamObjeto);
                raf.seek(linha * tamObjeto);
                raf.write(baos.toByteArray());
                oos.close();
                raf.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /*
* esse metodo busca pelo objeto T, para isso ele recebe a hash, que � quebrada para identificar a posi��o no array de objects e assim poder encontrar o arquivo corretamente e em segundo dentro do arquivo encontrar a linha correta para desserializar o objeto T e finalmente retorna-lo. Percorrer o array de objeto � custo O(1) por�m se houver muitas colis�es a complexidade � de percorrer a lista de endere�os onde o custo seria O(n). Desseralizar o objeto do arquivo exato tamb�m � O(1).
     */
    @SuppressWarnings("unchecked")
    public T get(long hashcode) {
        int[] chaves = geraChaveExterna(hashcode);
        int hash = chaves[0];
        int linha = chaves[1];
        int gerador = chaves[2];
        String[] lista = (String[]) (((Lista<String>) this.t[hash]).toArray());
        String filename;
        try {
            for (int i = 0; i < lista.length; i++)
                if (lista[i].contains(gerador + ".data")) {
                    filename = lista[i];
                    Path path = Paths.get(filename);
                    RandomAccessFile raf;
                    ByteArrayInputStream bais = null;
                    int tamObjeto = 874;
                    raf = new RandomAccessFile(path.toFile(), "r");
                    raf.seek(linha * tamObjeto);
                    // tamanho do objeto byte que ser� lido.
                    byte[] objeto = new byte[tamObjeto];
                    raf.read(objeto);
                    bais = new ByteArrayInputStream(objeto);
                    ObjectInputStream ois = new ObjectInputStream(bais);
                    ois.close();
                    raf.close();

                    return (T) ois.readObject();
                }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /*
* esse metodo separa as componentes da chave recebida, essa chave � construida da seguinte forma: a primeira componente � o ano j� dobrado, ou seja, metodo da dobra (ex: para o ano 1994 essa componente seria 85); a segunda componente � o resultado do metodo da multiplica��o aplicado ao m�s por 100, que � somado ao dia (ex: mes 12, dia 31, chave 1231); terceira componente � o ano original (ex: 1994).
     */
    private int[] geraChaveExterna(long hashcode) {
        String chavestring = Long.toString(hashcode);
        int hash;
        int linha;
        int gerador;
        if (chavestring.length() == 8) {
            hash = 0;
            linha = Integer.parseInt(chavestring.substring(0, 4));
            gerador = Integer.parseInt(chavestring.substring(4, 9));
        } else if (chavestring.length() == 7) {
            hash = 0;
            linha = Integer.parseInt(chavestring.substring(0, 3));
            gerador = Integer.parseInt(chavestring.substring(2, 6));
        } else if (chavestring.length() == 9) {
            hash = Integer.parseInt(chavestring.substring(0, 1));
            linha = Integer.parseInt(chavestring.substring(1, 5));
            gerador = Integer.parseInt(chavestring.substring(5, 9));
        } else {
            hash = Integer.parseInt(chavestring.substring(0, 2));
            linha = Integer.parseInt(chavestring.substring(2, 6));
            gerador = Integer.parseInt(chavestring.substring(6, 10));
        }
        int[] chaves = {hash, linha, gerador};
        return chaves;
    }

    /*
* esse metodo � usado para reconstruir a tabela hash, sua complexidade � O(1) porque so abre o arquivo e restaura a tabela.
     */
    @SuppressWarnings("unchecked")
    private void deserializar(Path path) {
        RandomAccessFile raf;
        ByteArrayInputStream bais = null;
        try {
            File file = new File(path.toString());
            FileInputStream filein = new FileInputStream(file);
            int tamObjeto = (int) filein.getChannel().size();
            raf = new RandomAccessFile(path.toFile(), "r");
            raf.seek(0);
            // tamanho do objeto byte que ser� lido.
            byte[] objeto = new byte[tamObjeto];
            raf.read(objeto);
            bais = new ByteArrayInputStream(objeto);
            ObjectInputStream ois = new ObjectInputStream(bais);
            TabelaHash<T> tabelalida = (TabelaHash<T>) ois.readObject();
            this.t = tabelalida.t;
            this.tamanho = tabelalida.tamanho;
            filein.close();
            raf.close();
            ois.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
* esse metodo � usado para serializar a tabela hash e persisti-la no disco, n�s o chamamos no final do metodo main. Seu custo � O(1), j� que a unica fun��o � abrir um arquivo e salvar o conteudo.
     */
    private void serializar() {
        RandomAccessFile raf;
        ByteArrayOutputStream baos = null;
        try {
            Path path = Paths.get(this.path);
            if (Files.exists(path))
                Files.delete(path);

            baos = new ByteArrayOutputStream();
            ObjectOutputStream oos;
            oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.flush();
            raf = new RandomAccessFile(path.toFile(), "rw");
            raf.seek(0);
            raf.write(baos.toByteArray());
            oos.close();
            raf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void persistir() {
        serializar();
    }
}
