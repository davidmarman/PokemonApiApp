package com.example.pokemonapiapp.datos;

public class Contenido {

    String nombrePokemon;
    String tipoPokemon;
    String artworkUrl100;
    public Contenido(String nombrePokemon, String tipoPokemon, String
            artworkUrl100) {
        this.nombrePokemon = nombrePokemon;
        this.tipoPokemon = tipoPokemon;
        this.artworkUrl100 = artworkUrl100;
    }
    public String getNombrePokemon() {
        return nombrePokemon;
    }
    public void setNombrePokemon(String nombrePokemon) {
        this.nombrePokemon = nombrePokemon;
    }
    public String getTipoPokemon() {
        return tipoPokemon;
    }
    public void setTipoPokemon(String tipoPokemon) {
        this.tipoPokemon = tipoPokemon;
    }
    public String getArtworkUrl100() {
        return artworkUrl100;
    }
    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }


}
