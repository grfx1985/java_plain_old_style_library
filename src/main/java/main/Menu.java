package main;


import dao.KsiazkaDao;
import dao.UzytkownikDao;
import dao.WypozyczenieDao;
import dao.impl.KsiazkaDaoImpl;
import dao.impl.UzytkownikDaoImpl;
import dao.impl.WypozyczenieDaoImpl;
import de.vandermeer.asciitable.v2.RenderedTable;
import de.vandermeer.asciitable.v2.V2_AsciiTable;
import de.vandermeer.asciitable.v2.render.V2_AsciiTableRenderer;
import de.vandermeer.asciitable.v2.render.WidthAbsoluteEven;
import model.Ksiazka;
import model.Uzytkownik;
import model.Wypozyczenie;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Menu {

    private KsiazkaDao ksiazkaDao;
    private UzytkownikDao uzytkownikDao;
    private WypozyczenieDao wypozyczenieDao;

    public Menu() {
        this.ksiazkaDao = new KsiazkaDaoImpl();
        this.uzytkownikDao = new UzytkownikDaoImpl();
        this.wypozyczenieDao = new WypozyczenieDaoImpl();
    }

    public void wyswietlProgram() {
        Scanner scanner = new Scanner(System.in);
        String wybor;
        System.out.println("1 - dodaj uzytkownika");
        System.out.println("2 - dodaj ksiazke");

        System.out.println("3 - usun uzytkownika");
        System.out.println("4 - usun ksiazke");

        System.out.println("5 - wypozycz");
        System.out.println("6 - zwroc");

        System.out.println("7 - wyswietl biblioteke");

        System.out.println("q - zakoncz program");

        do {
            System.out.print("Twoj wybor: ");
            wybor = scanner.nextLine();
            switch (wybor) {
                case "1": {
                    System.out.print("Podaj imie: ");
                    String imie = scanner.nextLine();
                    System.out.print("Podaj nazwisko: ");
                    String nazwisko = scanner.nextLine();

                    Uzytkownik uzytkownik = new Uzytkownik(imie, nazwisko);
                    try {
                        uzytkownikDao.zapisz(uzytkownik);
                    } catch (SQLException e) {
                        System.err.println("Nie zapisano uzytkownika: " + e.getMessage());
                    }
                }

                break;
                case "2": {
                    System.out.print("Podaj tytul: ");
                    String tytul = scanner.nextLine();
                    System.out.print("Podaj autora: ");
                    String autor = scanner.nextLine();

                    Ksiazka ksiazka = new Ksiazka(tytul, autor);
                    try {
                        ksiazkaDao.zapisz(ksiazka);
                    } catch (SQLException e) {
                        System.err.println("Nie zapisano uzytkownika: " + e.getMessage());
                    }
                }
                break;
                case "3": {
                    try {
                        wypiszUzytkownikow(uzytkownikDao.znajdzWszystkich());
                        System.out.print("\n\n\tPodaj id uzytkownika do usuniecia: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        uzytkownikDao.usun(id);
                        System.out.println("Uzytkownik zostal usuniety");
                    } catch (SQLException e) {
                        System.err.println("Blad podczas pobierania danych: " + e.getMessage());
                    }
                }
                break;
                case "4": {
                    try {
                        wypiszKsiazki(ksiazkaDao.znajdzWszystkie());
                        System.out.print("\n\n\tPodaj id ksiazki do usuniecia: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        ksiazkaDao.usun(id);
                        System.out.println("Ksiazka zostala usunieta");
                    } catch (SQLException e) {
                        System.err.println("Blad podczas pobierania danych: " + e.getMessage());
                    }
                }
                break;
                case "5": {
                    try{
                        wypiszKsiazki(ksiazkaDao.znajdzWszystkie());
                        wypiszUzytkownikow(uzytkownikDao.znajdzWszystkich());

                        System.out.print("Podaj id uzytkownika: ");
                        int idUzytkownika = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Podaj id ksiazki: ");
                        int idKsiazki = scanner.nextInt();
                        scanner.nextLine();

                        Uzytkownik uzytkownik = uzytkownikDao.znajdz(idUzytkownika);
                        Ksiazka ksiazka = ksiazkaDao.znajdz(idKsiazki);

                        Wypozyczenie wypozyczenie = new Wypozyczenie(uzytkownik, ksiazka);
                        wypozyczenieDao.zapisz(wypozyczenie);

                    } catch (SQLException e) {
                        System.err.println("Blad podczas pobierania danych: " + e.getMessage());
                    }

                }
                break;
                case "6": {
                    try {
                        wypiszWypozyczenia(wypozyczenieDao.znajdzWszystkie());
                        System.out.print("Podaj id wypozyczenia ktore chcesz zakonczyc: ");
                        int idWypozyczenia = scanner.nextInt();
                        scanner.nextLine();

                        Wypozyczenie wypozyczenie = wypozyczenieDao.znajdz(idWypozyczenia);
                        wypozyczenie.setZwrocone(true);
                        wypozyczenie.setDataZwrotu(new Date());
                        wypozyczenieDao.zaktualizuj(wypozyczenie);

                    } catch (SQLException e) {
                        System.err.println("Blad podczas pobierania danych: " + e.getMessage());
                    }
                }
                break;
                case "7": {
                    try {
                        List<Uzytkownik> uzytkownicy = uzytkownikDao.znajdzWszystkich();
                        List<Ksiazka> ksiazki = ksiazkaDao.znajdzWszystkie();
                        List<Wypozyczenie> wypozyczenia = wypozyczenieDao.znajdzWszystkie();
                        wypiszUzytkownikow(uzytkownicy);
                        wypiszKsiazki(ksiazki);
                        wypiszWypozyczenia(wypozyczenia);
                    } catch (SQLException e) {
                        System.err.println("Blad podczas wyswietlania: " + e.getMessage());
                    }

                }
                break;
                case "q":
                    System.out.println("Koniec programu");
                    break;
            }
        } while (!wybor.equals("q"));
    }

    private void wypiszUzytkownikow(List<Uzytkownik> uzytkownicy) {
        V2_AsciiTable table = new V2_AsciiTable();
        table.addRule();
        table.addRow("id", "imie", "nazwisko");
        table.addStrongRule();
        for (Uzytkownik uzytkownik : uzytkownicy) {
            table.addRow(uzytkownik.getId(), uzytkownik.getImie(), uzytkownik.getNazwisko());
            table.addRule();
        }
        V2_AsciiTableRenderer renderer = new V2_AsciiTableRenderer();
        renderer.setWidth(new WidthAbsoluteEven(76));

        RenderedTable renderedTable = renderer.render(table);
        System.out.println("\n\n Lista uzytkownikow: ");
        System.out.println(renderedTable);
    }

    private void wypiszKsiazki(List<Ksiazka> ksiazki) {
        V2_AsciiTable table = new V2_AsciiTable();
        table.addRule();
        table.addRow("id", "tytul", "autor");
        table.addStrongRule();
        for (Ksiazka ksiazka : ksiazki) {
            table.addRow(ksiazka.getId(), ksiazka.getTytul(), ksiazka.getAutor());
            table.addRule();
        }
        V2_AsciiTableRenderer renderer = new V2_AsciiTableRenderer();
        renderer.setWidth(new WidthAbsoluteEven(76));

        RenderedTable renderedTable = renderer.render(table);
        System.out.println("\n\n Lista ksiazek: ");
        System.out.println(renderedTable);
    }

    private void wypiszWypozyczenia(List<Wypozyczenie> wypozyczenia) {
        V2_AsciiTable table = new V2_AsciiTable();
        table.addRule();
        table.addRow("id", "ksiazka", "uzytkownik", "data wypozyczania", "data zwrotu", "zwrocono");
        table.addStrongRule();
        for (Wypozyczenie wypozyczenie : wypozyczenia) {
            table.addRow(wypozyczenie.getId(),
                    wypozyczenie.getKsiazka().getTytul(),
                    wypozyczenie.getUzytkownik().getFullName(),
                    wypozyczenie.getDataWypozyczenia(),
                    wypozyczenie.getDataZwrotu(),
                    wypozyczenie.getZwrocone());
            table.addRule();
        }
        V2_AsciiTableRenderer renderer = new V2_AsciiTableRenderer();
        renderer.setWidth(new WidthAbsoluteEven(116));

        RenderedTable renderedTable = renderer.render(table);
        System.out.println("\n\n Lista wypozyczen: ");
        System.out.println(renderedTable);
    }


}
