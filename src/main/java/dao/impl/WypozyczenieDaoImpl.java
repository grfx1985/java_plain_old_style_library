package dao.impl;

import config.Database;
import dao.WypozyczenieDao;
import model.Ksiazka;
import model.Uzytkownik;
import model.Wypozyczenie;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WypozyczenieDaoImpl implements WypozyczenieDao {

    private Database database;

    public WypozyczenieDaoImpl() {
        this.database = new Database();
    }

    @Override
    public void zapisz(Wypozyczenie wypozyczenie) throws SQLException {
        Connection connection = database.openConnection();
        String sql = "INSERT INTO wypozyczenie(data_wypozyczenia, data_zwrotu, zwrocone, ksiazka_id, uzytkownik_id) VALUES (?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setDate(1, new Date(wypozyczenie.getDataWypozyczenia().getTime()));
        statement.setDate(2, new Date(wypozyczenie.getDataZwrotu().getTime()));
        statement.setBoolean(3, wypozyczenie.getZwrocone());
        statement.setInt(4, wypozyczenie.getKsiazka().getId());
        statement.setInt(5, wypozyczenie.getUzytkownik().getId());

        statement.executeUpdate();
        database.closeConnection();
    }

    @Override
    public Wypozyczenie znajdz(int id) throws SQLException {
        Wypozyczenie wypozyczenie = null;
        Connection connection = database.openConnection();
        String sql = "SELECT wypozyczenie.id as wypozyczenie_id, data_wypozyczenia, data_zwrotu, zwrocone, uzytkownik_id, ksiazka_id, imie, nazwisko, tytul, autor FROM wypozyczenie " +
                "inner join uzytkownik " +
                "on uzytkownik.id = wypozyczenie.uzytkownik_id " +
                "inner join ksiazka " +
                "on ksiazka.id = wypozyczenie.ksiazka_id WHERE wypozyczenie.id=?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int idUzytkownika = resultSet.getInt("uzytkownik_id");
            String imie = resultSet.getString("imie");
            String nazwisko = resultSet.getString("nazwisko");
            int idKsiazki = resultSet.getInt("ksiazka_id");
            String tytul = resultSet.getString("tytul");
            String autor = resultSet.getString("autor");
            Date dataWypozyczenia = resultSet.getDate("data_wypozyczenia");
            Date dataZwrotu = resultSet.getDate("data_zwrotu");
            boolean zwrocone = resultSet.getBoolean("zwrocone");


            Uzytkownik uzytkownik = new Uzytkownik(idUzytkownika, imie, nazwisko);
            Ksiazka ksiazka = new Ksiazka(idKsiazki, tytul, autor);
            wypozyczenie = new Wypozyczenie(id, new java.util.Date(dataWypozyczenia.getTime()),
                    new java.util.Date(dataZwrotu.getTime()),
                    zwrocone,
                    uzytkownik,
                    ksiazka);
        }
        return wypozyczenie;
    }

    @Override
    public List<Wypozyczenie> znajdzWszystkie() throws SQLException {
        List<Wypozyczenie> wypozyczenia = new ArrayList<>();
        Connection connection = database.openConnection();
        String sql = "SELECT wypozyczenie.id as wypozyczenie_id, data_wypozyczenia, data_zwrotu, zwrocone, uzytkownik_id, ksiazka_id, imie, nazwisko, tytul, autor FROM wypozyczenie " +
                "inner join uzytkownik " +
                "on uzytkownik.id = wypozyczenie.uzytkownik_id " +
                "inner join ksiazka " +
                "on ksiazka.id = wypozyczenie.ksiazka_id";

        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            int idUzytkownika = resultSet.getInt("uzytkownik_id");
            String imie = resultSet.getString("imie");
            String nazwisko = resultSet.getString("nazwisko");
            int idKsiazki = resultSet.getInt("ksiazka_id");
            String tytul = resultSet.getString("tytul");
            String autor = resultSet.getString("autor");
            int idWypozyczenia = resultSet.getInt("wypozyczenie_id");
            Date dataWypozyczenia = resultSet.getDate("data_wypozyczenia");
            Date dataZwrotu = resultSet.getDate("data_zwrotu");
            boolean zwrocone = resultSet.getBoolean("zwrocone");


            Uzytkownik uzytkownik = new Uzytkownik(idUzytkownika, imie, nazwisko);
            Ksiazka ksiazka = new Ksiazka(idKsiazki, tytul, autor);
            Wypozyczenie wypozyczenie = new Wypozyczenie(idWypozyczenia,
                    new java.util.Date(dataWypozyczenia.getTime()),
                    new java.util.Date(dataZwrotu.getTime()),
                    zwrocone,
                    uzytkownik,
                    ksiazka);
            wypozyczenia.add(wypozyczenie);
        }
        return wypozyczenia;
    }

    @Override
    public void usun(int id) throws SQLException {
        Connection connection = database.openConnection();
        String sql = "DELETE FROM wypozyczenie WHERE id=?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }

    @Override
    public void zaktualizuj(Wypozyczenie wypozyczenie) throws SQLException {
        Connection connection = database.openConnection();
        String sql = "UPDATE wypozyczenie SET data_zwrotu=?,zwrocone=? WHERE id=?";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setDate(1, new Date(wypozyczenie.getDataZwrotu().getTime()));
        statement.setBoolean(2, wypozyczenie.getZwrocone());
        statement.setInt(3, wypozyczenie.getId());

        statement.executeUpdate();
        database.closeConnection();
    }
}
