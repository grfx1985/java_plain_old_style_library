package dao.impl;

import config.Database;
import dao.KsiazkaDao;
import model.Ksiazka;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KsiazkaDaoImpl implements KsiazkaDao {
    private Database database;

    public KsiazkaDaoImpl() {
        this.database = new Database();
    }

    @Override
    public void zapisz(Ksiazka ksiazka) throws SQLException {
        Connection connection = database.openConnection();
        String sql = "INSERT INTO ksiazka(tytul, autor) VALUES (?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, ksiazka.getTytul());
        statement.setString(2, ksiazka.getAutor());

        statement.executeUpdate();
        database.closeConnection();
    }

    @Override
    public Ksiazka znajdz(int id) throws SQLException {
        Ksiazka ksiazka = null;
        Connection connection = database.openConnection();
        String sql = "SELECT id,tytul,autor FROM ksiazka WHERE id=?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            String tytul = resultSet.getString("tytul");
            String autor = resultSet.getString("autor");
            ksiazka = new Ksiazka(id, tytul, autor);
        }
        return ksiazka;
    }

    @Override
    public List<Ksiazka> znajdzWszystkie() throws SQLException {
        List<Ksiazka> ksiazki = new ArrayList<>();
        Connection connection = database.openConnection();
        String sql = "SELECT id,tytul,autor FROM ksiazka";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String tytul = resultSet.getString("tytul");
            String autor = resultSet.getString("autor");
            Ksiazka ksiazka = new Ksiazka(id, tytul, autor);
            ksiazki.add(ksiazka);
        }
        return ksiazki;
    }

    @Override
    public void usun(int id) throws SQLException {
        Connection connection = database.openConnection();
        String sql = "DELETE FROM ksiazka WHERE id=?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}
