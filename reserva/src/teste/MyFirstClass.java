package teste;

import java.text.ParseException;

public class MyFirstClass {

	public static void main(String[] args) throws ParseException{
		Hotel hotel = new Hotel("Hotel Legal", 4, 2, 1);
		
		Menu menu = new Menu(hotel);
		menu.mostrarMenu();
	}

}
