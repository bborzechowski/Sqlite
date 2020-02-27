package service;


import java.sql.*;


public class UslugiService {

    //tworzymy zmienną ze sterownikami(załądowanie sterownika) oraz ze ścieżką do tworzonej bazy danych
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:bazaUslugi.db";

    private Connection conn;
    private Statement stat;

    public UslugiService() {

        try {
            Class.forName(UslugiService.DRIVER);  //W konstruktorze najpierw wywołujemy tę linię, odpowiedzialną za załadowanie sterownika do systemu:
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(DB_URL);  //Później tworzymy połączenie z bazą danych:
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        }

        createTables();
    }
    //tworzymy tabele ze strukturą i kolumnami
    public boolean createTables()  {

        String createCategories = "CREATE TABLE IF NOT EXISTS kategorie (" +
                "id INTEGER PRIMARY KEY UNIQUE," +
                "nazwa_kategorii varchar(255) NOT NULL UNIQUE)";

        String createProducts = "CREATE TABLE IF NOT EXISTS produkty (" +
                "id INTEGER PRIMARY KEY UNIQUE," +
                "nazwa_uslugi varchar(255) NOT NULL," +
                "cena double NOT NULL," +
                "id_kategorii INTEGER NOT NULL," +
               "FOREIGN KEY(id_kategorii) REFERENCES kategorie(id))";


        try {
            stat.execute(createCategories);
            stat.execute(createProducts);

        } catch (SQLException e) {
            System.err.println("Error creating table");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //metoda która dodaje usługę(produkt) do bazy danych
    public boolean insertProduct(int id,String nazwaUslugi,double cena, int idKategorii) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "REPLACE into produkty (id,nazwa_uslugi,cena,id_kategorii) values (?, ?, ?, ?);");
            prepStmt.setInt(1, id);
            prepStmt.setString(2, nazwaUslugi);
            prepStmt.setDouble(3, cena);
            prepStmt.setInt(4, idKategorii);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu czytelnika");
            e.printStackTrace();
            return false;
        }
        System.out.println("Dodano produkt: "+nazwaUslugi);
        return true;
    }

    //metoda która dodaje kategorię do bazy danych
    public boolean insertCategory(int id, String nazwaKategorii) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "REPLACE into kategorie (id,nazwa_kategorii) values (?, ?);");
            prepStmt.setInt(1, id);
            prepStmt.setString(2, nazwaKategorii);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy kategori");
            return false;
        }
        System.out.println("Dodano kategorie: " + nazwaKategorii);
        return true;
    }



    //metoda która zamyka połączenie z bazą danych
    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem z zamknieciem polaczenia");
            e.printStackTrace();
        }
    }

    //metoda która wyświetla podsumowanie(kategoria, suma cen w danej kategorii)
    public void podsumowanie(){
        try {
            ResultSet result = stat.executeQuery("SELECT k.nazwa_kategorii,sum(p.cena) suma \n" +
                    "from kategorie k\n" +
                    "JOIN produkty p on k.id=p.id_kategorii\n" +
                    "GROUP by k.nazwa_kategorii\n" +
                    "order by k.id=2,k.id=3,k.id=1");
            String nazwaKategorii;
            double suma;
            System.out.println("Kategoria podsumowania"+"               "+"kwota");
            while(result.next()){
                nazwaKategorii = result.getString("nazwa_kategorii");
                suma = result.getDouble("suma");
                System.out.println(nazwaKategorii + "                           "+suma);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
