package dao;

import model.Uzytkownik;

import java.sql.SQLException;
import java.util.List;

public interface UzytkownikDao {

    void zapisz(Uzytkownik uzytkownik) throws SQLException;
    Uzytkownik znajdz(int id) throws SQLException;
    List<Uzytkownik> znajdzWszystkich() throws SQLException;
    void usun(int id) throws SQLException;

}
