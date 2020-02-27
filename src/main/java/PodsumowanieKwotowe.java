import service.UslugiService;

public class PodsumowanieKwotowe {
    public static void main(String[] args) {

        UslugiService datab = new UslugiService();
        //uruchamiamy metode podsumowanie która wyświetli podsumowanie w konsoli na dole
        datab.podsumowanie();
        //metoda zamknie połacenie z baza danych
        datab.closeConnection();
    }
}
