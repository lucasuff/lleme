/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lobisomem.papeis;

import lobisomem.participantes.Participante;

/**
 *
 * @author MarcosFrederico
 */
public abstract class Papel {
    protected boolean aldeia;
    protected Participante dono;
    protected String id;
    
    public Papel(boolean aldeia){
        this.aldeia = aldeia;
    }
    public boolean isAldeia(){
        return aldeia;
    }
    public final void setDono(Participante dono){
        this.dono = dono;
    }
    public final Participante getDono(){
        return dono;
    }
    public final String getId(){
        return id;
    }
    public abstract void acaoNoturnaPlayer();
    public abstract void acaoNoturnaIA();
}
