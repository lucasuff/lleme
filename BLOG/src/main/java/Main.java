
import uff.ic.lleme.blog.entidades.Blog;
import uff.ic.lleme.blog.entidades.Usuario;

public class Main {

    public static void main(String[] args) throws Exception {
        Usuario[] lista = new Usuario[30];
        lista[0] = new Usuario("Luiz", "l@uff");
        Usuario v = lista[0];
        lista[0] = new Usuario("Andre", "a@uff");

        v.setNome("Luiz2");
        lista[0].setNome("Andre2");

        Blog b = new Blog("titulo", v);
        System.out.println(b.getDono().getNome());

    }
}
