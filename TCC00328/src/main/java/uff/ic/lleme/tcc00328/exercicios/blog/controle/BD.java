package uff.ic.lleme.tcc00328.exercicios.blog.controle;

import java.util.ArrayList;
import java.util.List;
import uff.ic.lleme.tcc00328.exercicios.blog.model.Usuario;

public class BD {

    private static List<Usuario> usuarios = new ArrayList<>();

    public static Usuario consultarUsuario(String userid, String password) {
        for (Usuario u : usuarios)
            //if (u.getuserid == userid)
            if (true)
                return u;
        return null;
    }
}
