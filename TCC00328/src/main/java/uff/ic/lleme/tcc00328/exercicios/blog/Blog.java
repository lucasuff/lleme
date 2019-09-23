package uff.ic.lleme.tcc00328.exercicios.blog;

import uff.ic.lleme.tcc00328.exercicios.blog.controle.Controlador;
import uff.ic.lleme.tcc00328.exercicios.blog.model.Usuario;

public class Blog {

    public static void main(String[] args) {
        String userid = "";
        String password = "";
        Usuario usuarioCorrente = Controlador.validarUsuario(userid, password);

    }
}
