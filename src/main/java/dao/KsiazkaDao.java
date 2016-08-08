package dao;

import model.Ksiazka;

import java.sql.SQLException;
import java.util.List;

public interface KsiazkaDao {
    void zapisz(Ksiazka ksiazka) throws SQLException;
    Ksiazka znajdz(int id) throws SQLException;
    List<Ksiazka> znajdzWszystkie() throws SQLException;
    void usun(int id) throws SQLException;
}
