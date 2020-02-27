//import model.Kategorie;
import service.UslugiService;

import java.sql.ResultSet;
import java.util.List;

public class TworzenieBazy {

    public static void main(String[] args) {
        //torzymy obiekt klasy UslugiService dzięki temu mamy dostęp do metod które występują w tej klasie
        UslugiService datab = new UslugiService();

        //uruchamiamy metode insertCategory która dodaje kategorie do bazy danych
        datab.insertCategory(1,"Internet");
        datab.insertCategory(2,"Telewizja");
        datab.insertCategory(3,"Usługi dodatkowe");

        //uruchamiamy metode insertProduct która dodaje usługę do bazy danych
        datab.insertProduct(1,"Abonament za Internet", 40.00, 1);
        datab.insertProduct(2,"Bezpieczny Internet", 9.90, 3);
        datab.insertProduct(3,"Telewizja Pakiet pełny", 80.00, 2);
        datab.insertProduct(4,"GigaNagrywarka", 15.00, 2);
        datab.insertProduct(5,"Rabat za Internet", -20.00, 1);

        //zamykamy połączenie z bazą danych
        datab.closeConnection();
    }

}