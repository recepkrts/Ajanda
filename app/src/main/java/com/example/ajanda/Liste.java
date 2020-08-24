package com.example.ajanda;


public class Liste {
    private int id,durum;
    private String listeNotu;

    public Liste(int id, int durum, String listeNotu) {
        this.id = id;
        this.durum = durum;
        this.listeNotu = listeNotu;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getDurum() {
        return durum;
    }
    public void setDurum(int durum) {
        this.durum = durum;
    }
    public String getListeNotu() {
        return listeNotu;
    }
    public void setListeNotu(String listeNotu) {
        this.listeNotu = listeNotu;
    }
}
