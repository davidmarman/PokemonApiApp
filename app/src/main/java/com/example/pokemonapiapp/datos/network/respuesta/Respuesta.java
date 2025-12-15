package com.example.pokemonapiapp.datos.network.respuesta;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Respuesta {
    @SerializedName("name")
    private String name;

    @SerializedName("types")
    private List<TypeSlot> types;

    @SerializedName("sprites")
    private Sprites sprites;

    @SerializedName("id")
    private int id;

    @SerializedName("weight")
    private int weight; // Viene en hectogramos

    @SerializedName("height")
    private int height; // Viene en decímetros

    @SerializedName("stats")
    private List<StatSlot> stats;


    public String getName() { return name; }
    public List<TypeSlot> getTypes() { return types; }
    public Sprites getSprites() { return sprites; }

    public int getId() { return id; }
    public int getWeight() { return weight; }
    public int getHeight() { return height; }
    public List<StatSlot> getStats() { return stats; }


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
        @SerializedName("official-artwork")
        public OfficialArtwork officialArtwork;
    }

    public static class OfficialArtwork {
        @SerializedName("front_default")
        public String frontDefault;
    }

    public static class StatSlot {
        @SerializedName("base_stat")
        public int baseStat;

        @SerializedName("stat")
        public StatObj stat;
    }

    public static class StatObj {
        @SerializedName("name")
        public String name;
    }
}