package teste;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Menu {
	private Hotel hotel;
	private Scanner input;
	
	public Menu(Hotel hotel) {
		this.hotel = hotel;
	}
	
	//mostrar todas as opções
	//mostras apenas as opções disponíveis
	// fazer uma reserva
	// cancelar uma reserva
	//mostrar as reservas feitas
	
	public void mostrarMenu() throws ParseException {
		int opcao = -1;
		while(opcao != 0) {
			System.out.println("------- Sistema de Reserva de Hotel -------\n\t(1)Mostrar quartos\n\t(2)Mostrar quartos disponíveis\n\t(3)Fazer reserva\n\t(4)Cancelar reserva\n\t(5)Mostrar reservas feitas\n\t(0)Sair");
			Scanner input = new Scanner(System.in);
			opcao = input.nextInt();
			relizarOpcao(opcao);
		}
	}
	
	public void relizarOpcao(int opc) throws ParseException {
		if (opc == 1) {
			//mostrar quartos
			mostrarQuartos();
		}
		if (opc == 2) {
			//mostrar quartos disponíveis
			mostrarQuartosDisponiveis();
		}
		if (opc == 3) {
			//fazer reserva
			boolean fez = fazerReserva();
			if (fez)
				System.out.println("Reserva feita com sucesso!");
			else
				System.out.println("Houve problema na reserva.");
		}
		if (opc == 4) {
			//cancelar reserva
		}
		if (opc == 5) {
			//mostrar reservas
			mostrarReservas();
		}
		if (opc == 0)
			System.out.println("Fim do programa");
	}
	
	public void mostrarQuartos() {
		List<Quarto> quartos = hotel.getQuartos();
		for (Quarto q : quartos) {
			System.out.println(q);
		}
	}
	
	public void mostrarQuartosDisponiveis() {
		List<Quarto> quartos = hotel.getQuartos();
		for (Quarto q : quartos) {
			if (!q.isOcupado())
				System.out.println(q);
		}
	}
	
	public void mostrarReservas() {
		List<Reserva> reservas = hotel.getReservas();
		for (Reserva r : reservas) {
			System.out.println(r);
		}
	}
	
	public String lerString() {
		Scanner input = new Scanner(System.in);
		String s = input.nextLine();
		return s;
	}
	
	
	public boolean fazerReserva() throws ParseException {
		System.out.println("Nome: ");
		String nomeCliente = lerString();
		
		List<Quarto> quartosReserva = new ArrayList<Quarto>();
		String nomeQuarto = "";
		while(nomeQuarto != "0") {
			System.out.println("Nome do quarto (para fim digite 0): ");
			nomeQuarto = lerString();
			if (nomeQuarto != "0") {
				Quarto quarto = retornarQuarto(nomeQuarto);
				if (quarto == null)
					System.out.println("Quarto não encontrado.");
				else if (!quarto.isOcupado()) {
					quartosReserva.add(quarto);
					quarto.setOcupado(true);
				}
			}
		}
		
		System.out.println("Digite a data de check-in no formato dd/mm/yyyy: ");
		String ini = lerString();
		Date dataIni = new SimpleDateFormat("dd/mm/yyyy").parse(ini);
		
		System.out.println("Digite a data de check-out no formato dd/mm/yyyy: ");
		String fim = lerString();
		Date dataFim = new SimpleDateFormat("dd/mm/yyyy").parse(fim);
		
		
		Reserva reserva = new Reserva(nomeCliente, quartosReserva, dataIni, dataFim, "1111");
		return hotel.adicionarReserva(reserva);		
	}
	
	public Quarto retornarQuarto(String nome) {	//todo: ele está retornando null para todos, mesmo que exista
		List<Quarto> quartos= hotel.getQuartos();
		for (Quarto q : quartos) {
			if (q.getNome().equals(nome))
				return q;
		}
		return null;
	}
}
