package dao;

import model.Wypozyczenie;

import java.sql.SQLException;
import java.util.List;

public interface WypozyczenieDao {

    void zapisz(Wypozyczenie wypozyczenie) throws SQLException;
    Wypozyczenie znajdz(int id) throws SQLException;
    List<Wypozyczenie> znajdzWszystkie() throws SQLException;
    void usun(int id) throws SQLException;

    void zaktualizuj(Wypozyczenie wypozyczenie) throws SQLException;
}
