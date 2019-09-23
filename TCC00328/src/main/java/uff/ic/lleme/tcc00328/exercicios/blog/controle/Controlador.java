package uff.ic.lleme.tcc00328.exercicios.blog.controle;

import uff.ic.lleme.tcc00328.exercicios.blog.model.Usuario;

public abstract class Controlador {

    public static Usuario validarUsuario(String userid, String password) {
        return BD.consultarUsuario(userid, password);
    }

}
