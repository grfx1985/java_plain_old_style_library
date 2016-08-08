package dao.impl;

import config.Database;
import dao.UzytkownikDao;
import model.Uzytkownik;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UzytkownikDaoImpl implements UzytkownikDao {

    private Database database;

    public UzytkownikDaoImpl() {
        this.database = new Database();
    }


    public void zapisz(Uzytkownik uzytkownik) throws SQLException {
        Connection connection = database.openConnection();
        String sql = "INSERT INTO uzytkownik(imie, nazwisko) VALUES (?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, uzytkownik.getImie());
        statement.setString(2, uzytkownik.getNazwisko());

        statement.executeUpdate();
        database.closeConnection();
    }

    public Uzytkownik znajdz(int id) throws SQLException {
        Uzytkownik uzytkownik = null;
        Connection connection = database.openConnection();
        String sql = "SELECT id,imie,nazwisko FROM uzytkownik WHERE id=?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String imie = resultSet.getString("imie");
            String nazwisko = resultSet.getString("nazwisko");
            uzytkownik = new Uzytkownik(id, imie, nazwisko);
        }
        return uzytkownik;
    }

    public List<Uzytkownik> znajdzWszystkich() throws SQLException {
        List<Uzytkownik> uzytkownicy = new ArrayList<>();
        Connection connection = database.openConnection();
        String sql = "SELECT id,imie,nazwisko FROM uzytkownik";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String imie = resultSet.getString("imie");
            String nazwisko = resultSet.getString("nazwisko");
            Uzytkownik uzytkownik = new Uzytkownik(id, imie, nazwisko);
            uzytkownicy.add(uzytkownik);
        }
        return uzytkownicy;
    }

    public void usun(int id) throws SQLException {
        Connection connection = database.openConnection();
        String sql = "DELETE FROM uzytkownik WHERE id=?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}
