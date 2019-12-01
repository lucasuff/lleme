package p220191q1.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Sinonimos {

    /**
     * @associates <{p220191q1.model.Palavra}>
     */
    public List<Palavra> palavras = new ArrayList<>();

    /**
     * @attribute
     */
    public String significado = null;

    public Sinonimos() {
        super();
    }

    public Sinonimos(String significado) {
        this.significado = significado;
    }
}
