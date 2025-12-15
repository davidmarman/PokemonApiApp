package com.example.pokemonapiapp.util;

public class Sesion {

    public static int userId = -1;
    public static String username = "";

    // metodo para cerrar sesion
    public static void cerrarSesion() {
        userId = -1;
        username = "";
    }

    // metodo para saber is hay alguien loguead
    public static boolean estaLogueado() {
        return userId != -1;
    }

}
