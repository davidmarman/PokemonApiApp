package com.example.pokemonapiapp.datos;

import com.google.gson.annotations.SerializedName;
import java.util.List;

// Esta clase imita la estructura exacta del JSON que recibimos
public class Respuesta {

    @SerializedName("name")
    private String name;

    @SerializedName("types")
    private List<TypeSlot> types;

    @SerializedName("sprites")
    private Sprites sprites;

    // Getters
    public String getName() { return name; }
    public List<TypeSlot> getTypes() { return types; }
    public Sprites getSprites() { return sprites; }

    // --- Clases internas para navegar por el JSON ---

    public static class TypeSlot {
        @SerializedName("type")
        public TypeObj type;
    }

    public static class TypeObj {
        @SerializedName("name")
        public String name;
    }

    public static class Sprites {
        @SerializedName("other")
        public Other other;
    }

    public static class Other {
        @SerializedName("dream_world")
        public DreamWorld dreamWorld;
    }

    public static class DreamWorld {
        @SerializedName("front_default")
        public String frontDefault;
    }
}