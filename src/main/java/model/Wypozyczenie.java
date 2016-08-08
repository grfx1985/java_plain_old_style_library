package model;

import java.util.Date;

public class Wypozyczenie {
    private int id;
    private Date dataWypozyczenia;
    private Date dataZwrotu;
    private Boolean zwrocone;
    private Uzytkownik uzytkownik;
    private Ksiazka ksiazka;

    public Wypozyczenie(Uzytkownik uzytkownik, Ksiazka ksiazka) {
        this.dataWypozyczenia = new Date();
        this.dataZwrotu = new Date();
        this.zwrocone = false;
        this.uzytkownik = uzytkownik;
        this.ksiazka = ksiazka;
    }

    public Wypozyczenie(int id, Date dataWypozyczenia, Date dataZwrotu, Boolean zwrocone, Uzytkownik uzytkownik, Ksiazka ksiazka) {
        this.id = id;
        this.dataWypozyczenia = dataWypozyczenia;
        this.dataZwrotu = dataZwrotu;
        this.zwrocone = zwrocone;
        this.uzytkownik = uzytkownik;
        this.ksiazka = ksiazka;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataWypozyczenia() {
        return dataWypozyczenia;
    }

    public void setDataWypozyczenia(Date dataWypozyczenia) {
        this.dataWypozyczenia = dataWypozyczenia;
    }

    public Date getDataZwrotu() {
        return dataZwrotu;
    }

    public void setDataZwrotu(Date dataZwrotu) {
        this.dataZwrotu = dataZwrotu;
    }

    public Boolean getZwrocone() {
        return zwrocone;
    }

    public void setZwrocone(Boolean zwrocone) {
        this.zwrocone = zwrocone;
    }

    public Uzytkownik getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(Uzytkownik uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public Ksiazka getKsiazka() {
        return ksiazka;
    }

    public void setKsiazka(Ksiazka ksiazka) {
        this.ksiazka = ksiazka;
    }
}
