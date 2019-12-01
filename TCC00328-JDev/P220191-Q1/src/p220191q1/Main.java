package p220191q1;

import p220191q1.model.Dicionario;
import p220191q1.model.Palavra;
import p220191q1.model.Sinonimos;
import p220191q1.model.Substantivo;


public abstract class Main {

    public static void main(String[] args) {
        Dicionario dicionario = new Dicionario();

        {
            String grafia = "livro";
            String fonetica = "li·vru";
            Palavra palavra = new Palavra(grafia, fonetica);
            dicionario.palavras.add(palavra);

            String significado1 =
                "coleção de folhas de papel, impressas ou não, reunidas em cadernos cujos dorsos são " +
                "unidos por meio de cola, costura etc., formando um volume que se recobre com capa resistente.";
            Sinonimos sinonimos = new Substantivo(significado1);
            palavra.sinonimos.add(sinonimos);
            sinonimos.palavras.add(palavra);

            String significado2 =
                "obra de cunho literário, artístico, científico etc. que constitui um volume [Para fins de " +
                "documentação, é uma publicação não periódica com mais de 48 páginas, além da capa.].";
            sinonimos = new Substantivo(significado2);
            palavra.sinonimos.add(sinonimos);
            sinonimos.palavras.add(palavra);
        }
        //
        {
            String grafia = "obra";
            String fonetica = "oo·bruh";
            Palavra palavra = new Palavra(grafia, fonetica);
            dicionario.palavras.add(palavra);

            String significado1 = "aquilo que resulta de um trabalho, de uma ação.";
            Sinonimos sinonimos = new Substantivo(significado1);
            palavra.sinonimos.add(sinonimos);
            sinonimos.palavras.add(palavra);

            String significado2 =
                "obra de cunho literário, artístico, científico etc. que constitui um volume [Para fins de " +
                "documentação, é uma publicação não periódica com mais de 48 páginas, além da capa.].";
            sinonimos = dicionario.palavras
                                  .get(0)
                                  .sinonimos
                                  .get(1);
            palavra.sinonimos.add(sinonimos);
            sinonimos.palavras.add(palavra);
        }

        for (Palavra p : dicionario.palavras) {
            for (Sinonimos s : p.sinonimos) {
                System.out.println(p.grafia + ": " + s.significado);
            }
        }

    }
}
