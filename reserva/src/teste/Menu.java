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
	
	public void mostrarMenu() throws ParseException {
		int opcao = -1;
		while(opcao != 0) {
			System.out.print("\n------- Sistema de Reserva de Hotel " + hotel.getNome() + 
					" -------\n\t(1)Mostrar quartos\n\t(2)Mostrar quartos disponíveis\n\t(3)Fazer reserva\n\t(4)Reservar quarto específico\n\t(5)Cancelar reserva\n\t(6)Mostrar reservas feitas\n\t(0)Sair\n");
			Scanner input = new Scanner(System.in);
			opcao = input.nextInt();
			relizarOpcao(opcao);
		}
	}
	
	public void relizarOpcao(int opc) throws ParseException {
		boolean fez;
		switch(opc) {
		case 1:
			mostrarQuartos();
			break;
		case 2:
			mostrarQuartosDisponiveis();
			break;
		case 3: 
			//fazer reserva
			fez = fazerReserva();
			if (fez)
				System.out.println("Reserva feita com sucesso!");
			else
				System.out.println("Houve problema na reserva.");
			break;
		case 4:
			//fazer reserva de um quarto específico
			fez = fazerReservaQuartoEspecifico();
			if (fez)
				System.out.println("Reserva feita com sucesso!");
			else
				System.out.println("Houve problema na reserva.");
			break;
		case 5:
			//cancelar reserva
			cancelarReserva();
			break;
		case 6:
			//mostrar reservas
			mostrarReservas();
			break;
		case 0:
			System.out.println("Fim do programa");
			break;
		default:
			System.out.println("Opção inválida.");		
		}
	}
	
	public void mostrarQuartos() {		
		for (TipoQuarto tipo : TipoQuarto.values()) {
			List<Quarto> quartos= hotel.retornarListaDeQuartos(tipo);
			System.out.println("\nTipo: " + tipo.toString() + "\nDescrição: " + tipo.getDescricao() + 
					"\nCapacidade: " + tipo.getCapacidade() + "\nDiaria: " + tipo.getDiaria() + 
					"\nDisponíveis: " + quartos.size());
		}
	}
	
	public void mostrarQuartosDisponiveis() {
		for (TipoQuarto tipo : TipoQuarto.values()) {
			List<Quarto> quartos= hotel.retornarListaDeQuartos(tipo);
			if (!quartos.isEmpty()) {
				System.out.println("\nTipo: " + tipo.toString() + "\nDescrição: " + tipo.getDescricao() + 
						"\nCapacidade: " + tipo.getCapacidade() + "\nDiaria: " + tipo.getDiaria() + 
						"\nDisponíveis: " + quartos.size());
			}
		}
	}
	
	public void mostrarReservas() {
		List<Reserva> reservas = hotel.getReservas();
		if (reservas.isEmpty())
			System.out.println("Não há reservas atualmente.");
		for (Reserva r : reservas) {
			System.out.println(r);
		}
	}
	
	public String lerString() {
		Scanner input = new Scanner(System.in);
		String s = input.nextLine().toUpperCase();
		return s;
	}
	
	public boolean cancelarReserva() {
		//ler o id e encontrar a reserva a ser deletada
		Reserva r = encontrarReserva();
		
		//retornar quarto para lista de quartos do tipo dele
		devolverQuartosParaHotel(r.getQuartos());
		
		System.out.println("Reserva cancelada com sucesso.");
		return false;
	}
	
	public Reserva encontrarReserva() {
		boolean ativo = true;
		String idReserva = "";
		Reserva r = null;
		while (ativo) {
			ativo = false;
			System.out.println("ID da reserva: ");
			idReserva = lerString();
			
			r = hotel.retornaReserva(idReserva);
			if (r == null) {
				System.out.println("ID não existente, por favor digite um ID válido.");
				ativo = true;
			}
		}
		return r;
	}
	
	public void devolverQuartosParaHotel(List<Quarto> quartos) {
		for (Quarto q : quartos) {
			hotel.devolverQuartoParaHotel(q);
		}
	}
	
	public boolean fazerReservaQuartoEspecifico() {
		String nomeCliente = pegarNome();
		
		List<Quarto> quartosReserva = pegarQuartosReservaEspecifica();
		
		Date dataIni = pegarDataInicio();
		
		//pega data checkout
		Date dataFim = pegarDataFinal(dataIni);
		
		//cria a reserva
		String id = Integer.toString(gerarIdReserva());
		
		Reserva reserva = new Reserva(nomeCliente, quartosReserva, dataIni, dataFim, id);
		return hotel.adicionarReserva(reserva);	
		
	}
	
	public List<Quarto> pegarQuartosReservaEspecifica(){
		List<Quarto> quartosReserva = new ArrayList<Quarto>();
		String id = "";
		boolean ativo = true;
		String tipoQuarto = "";
		while (ativo) {
			System.out.print("\nTipo do quarto (para fim digite 0): ");
			tipoQuarto = lerString().toUpperCase();
			if (tipoQuarto.equals("0"))
				ativo = false;
			if (ativo) {
				ativo = true;
				id = pegarIdQuarto(tipoQuarto);
				Quarto q = retornarQuarto(tipoQuarto, id);
				
				if (q == null)
					ativo = false;
				else
					quartosReserva.add(q);
				}
			}
		return quartosReserva;
	}
	
	public String pegarIdQuarto(String tipo) {
		boolean ativo = true;
		String id = "";
		while(ativo) {
			System.out.print("\nDigite o ID do quarto desejado: ");
			id = lerString();
			
			if (hotel.verificarSeQuartoExiste(id, tipo)) {
				ativo = false;
			}
			else
				System.out.println("ID do quarto não existe, ou quarto já está reservado.");
				
		}
		return id;
	}
	
	
	public boolean fazerReserva() throws ParseException {
		//pega o nome do cliente
		String nomeCliente = pegarNome();
		
		//pega os tipos de quarto escolhidos
		List<Quarto> quartosReserva = retornarListaQuartosCliente();
		
		//pega data de checkin
		Date dataIni = pegarDataInicio();
		
		//pega data checkout
		Date dataFim = pegarDataFinal(dataIni);
		
		//cria a reserva
		String id = Integer.toString(gerarIdReserva());
		
		Reserva reserva = new Reserva(nomeCliente, quartosReserva, dataIni, dataFim, id);
		return hotel.adicionarReserva(reserva);		
	}
	
	public String pegarNome() {
		String nomeCliente = "";
		boolean ativo = true;
		while(ativo) {
			ativo = false;
			System.out.print("\nNome: ");
			nomeCliente = lerString();
			if (nomeCliente.equals(""))
				ativo = true;
		}
		return nomeCliente;
	}
	
	public Date pegarDataInicio() {
		boolean ativo = true;
		Date dataIni = null;
		while (ativo) {
			ativo = false;
			System.out.print("\nDigite a data de check-in no formato dd/mm/yyyy: ");
			String ini = lerString();
			try {
				dataIni = new SimpleDateFormat("dd/MM/yyyy").parse(ini);
			} catch (ParseException e) {
				System.out.println("Data em formato inválido. Por favor, digite novamente.");
				ativo = true;
			}
		}
		return dataIni;
	}
	
	public Date pegarDataFinal(Date inicio) {
		
		boolean ativo = true;
		Date dataFim = null;
		while(ativo) {
			ativo = false;
			System.out.print("\nDigite a data de check-out no formato dd/mm/yyyy: ");
			String fim = lerString();
			try {
				dataFim = new SimpleDateFormat("dd/MM/yyyy").parse(fim);
			} catch (ParseException e) {
				System.out.println("Data em formato inválido. Por favor, digite novamente.");
				ativo = true;
			}
			
			if (dataFim.compareTo(inicio) < 0) {
				System.out.println("Data inválida, por favor digite uma data de checkout posterior à data de checkin.");
				ativo = true;
			}
		}
		return dataFim;
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
			tipoQuarto = lerString().toUpperCase();
			
			if (tipoQuarto.equals("0"))
				ativo = false;
			
			if (ativo) {
				if (hotel.verificarSePossuiTipo(tipoQuarto)) {
					Quarto quarto = retornarQuarto(tipoQuarto);
					if (quarto != null) {
						quartosReserva.add(quarto);
					}
				}
				else
					System.out.println("Tipo de quarto inexistente.");
			}
		}
		return quartosReserva;
	}
	
	public Quarto retornarQuarto(String tipo, String id) {
		Quarto q = hotel.devolverQuartoEspecificoParaReserva(tipo, id);
		
		if (q == null)
			System.out.println("Quarto não está disponível no momento.");
		return q;
	}	
	
	public Quarto retornarQuarto(String tipoQuarto) {
		Quarto q = null;
		if(hotel.retornarListaDeQuartos(TipoQuarto.valueOf(tipoQuarto)).isEmpty())
			System.out.println("Não há quarto desse tipo disponível atualmente.");
		q = hotel.darQuartoParaReserva(tipoQuarto);
		return q;
	}
}
