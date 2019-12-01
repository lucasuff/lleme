package p220191q1.model;

import java.util.ArrayList;
import java.util.List;


public class Palavra {

    /**
     * @attribute
     */
    public String grafia = null;

    /**
     * @attribute
     */
    public String fonetica = null;

    /**
     * @associates <{p220191q1.model.Sinonimos}>
     */
    public List<Sinonimos> sinonimos = new ArrayList<>();

    public Palavra() {
        super();
    }

    public Palavra(String grafia, String fonetica) {
        this.grafia = grafia;
        this.fonetica = fonetica;
    }
}
