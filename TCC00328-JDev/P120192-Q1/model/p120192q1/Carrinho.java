package p120192q1;

import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private List<Item> itens = new ArrayList<>();

    public Carrinho() {
        super();
    }

    public List<Item> getItens() {
        return itens;
    }

    public void setItens(List<Item> itens) {
        this.itens = itens;
    }
}
