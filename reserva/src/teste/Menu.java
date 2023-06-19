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
			System.out.print("\n------- Sistema de Reserva de Hotel -------\n\t(1)Mostrar quartos\n\t(2)Mostrar quartos disponíveis\n\t(3)Fazer reserva\n\t(4)Cancelar reserva\n\t(5)Mostrar reservas feitas\n\t(0)Sair");
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
		List<TipoQuarto> quartos = hotel.getQuartos();
		for (TipoQuarto q : quartos) {
			System.out.println(q);
		}
	}
	
	public void mostrarQuartosDisponiveis() {
		List<TipoQuarto> quartos = hotel.getQuartos();
		for (TipoQuarto t : quartos) {
			if (t.verificarDisponibilidade())
				System.out.println(t);
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
	
	public boolean cancelarReserva() {
		//id reserva
		System.out.println("ID da reserva: ");
		String idReserva = lerString();
		
		//achar reserva na lista de reserva e apagar
		
		
		//retornar quarto para lista de quartos do tipo dele
		return false;
	}
	
	
	public boolean fazerReserva() throws ParseException {
		//pega o nome do cliente
		System.out.print("\nNome: ");
		String nomeCliente = lerString();
		
		//pega os tipos de quarto escolhidos
		List<Quarto> quartosReserva = retornarListaQuartosCliente();
		
		//pega data de checkin
		System.out.print("\nDigite a data de check-in no formato dd/mm/yyyy: ");
		String ini = lerString();
		Date dataIni = new SimpleDateFormat("dd/mm/yyyy").parse(ini);
		
		//pega data checkout
		System.out.print("\nDigite a data de check-out no formato dd/mm/yyyy: ");
		String fim = lerString();
		Date dataFim = new SimpleDateFormat("dd/mm/yyyy").parse(fim);
		
		//cria a reserva
		String id = Integer.toString(gerarIdReserva());
		
		Reserva reserva = new Reserva(nomeCliente, quartosReserva, dataIni, dataFim, id);
		return hotel.adicionarReserva(reserva);		
	}
	
	public int gerarIdReserva() {
		int id = 0;
		for (Reserva r : hotel.getReservas()) {
			if (r.getId().equals(Integer.toString(id)))
				id++;
		}
		return id;
	}
	
	public List<Quarto> retornarListaQuartosCliente(){
		List<Quarto> quartosReserva = new ArrayList<Quarto>();
		boolean ativo = true;
		String tipoQuarto = "";
		while (ativo) {
			System.out.print("\nTipo do quarto (para fim digite 0): ");
			tipoQuarto = lerString();
			if (tipoQuarto.equals("0"))
				ativo = false;
			if (ativo) {
				Quarto quarto = retornarQuarto(tipoQuarto);
				if (quarto != null) {
					quartosReserva.add(quarto);
				}
			}
		}
		return quartosReserva;
	}
	
	public Quarto retornarQuarto(String tipo) {	//todo: ele está retornando null para todos, mesmo que exista
		List<TipoQuarto> quartos= hotel.getQuartos();
		for (TipoQuarto q : quartos) {
			if (q.getTipo().equals(tipo))
				return q.reservarERetornarUmQuarto();
		}
		System.out.println("Quarto não encontrado.");
		return null;
	}
}
