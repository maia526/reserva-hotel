package teste;

import java.text.ParseException;

public class MyFirstClass {

	public static void main(String[] args) throws ParseException {
		Hotel hotel = new Hotel("123", "Rua x, n 122", "Brasil", "RJ", "Rio de Janeiro", "Hotel Legal");
		
		TipoQuarto q1 = new TipoQuarto("descricao 1", 3, 300, "Quarto1", 1);
		TipoQuarto q2 = new TipoQuarto("descricao 2", 1, 250, "Quarto2", 2);
		TipoQuarto q3 = new TipoQuarto("descricao 3", 2, 230.50, "Quarto3", 3);
		hotel.adicionarQuarto(q1);
		hotel.adicionarQuarto(q2);
		hotel.adicionarQuarto(q3);
		
		Menu menu = new Menu(hotel);
		menu.mostrarMenu();
	}

}
