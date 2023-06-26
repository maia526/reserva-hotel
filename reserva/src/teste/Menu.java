package teste;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Menu {
	private Hotel hotel;
	final DateTimeFormatter formatter;
	
	public Menu(Hotel hotel) {
		this.hotel = hotel;
		this.formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	}
	
	public void mostrarMenu() {
		//como um quarto sempre estará disponível em alguma data (possibilidade de reservar um mesmo quarto para múltiplas datas)
		//não faz mais sentido ter uma opção para mostrar os quartos disponíveis, já que o mostrar quartos cumpre a função desejada
		int opcao = -1;
		while(opcao != 0) {
			System.out.print("\n------- Sistema de Reserva de Hotel " + hotel.getNome() + 
					" -------\n\t(1)Mostrar quartos\n\t(2)Fazer reserva\n\t(3)Reservar quarto específico\n\t(4)Cancelar reserva\n\t(5)Mostrar reservas feitas\n\t(0)Sair\n");
			Scanner input = new Scanner(System.in);
			opcao = input.nextInt();
			relizarOpcao(opcao);
		}
	}
	
	public void relizarOpcao(int opc) {
		boolean fez;
		switch(opc) {
		case 1:
			mostrarQuartos();
			break;
		case 2: 
			//fazer reserva
			fez = fazerReserva();
			if (fez)
				System.out.println("Reserva feita com sucesso!");
			else
				System.out.println("Houve problema na reserva.");
			break;
		case 3:
			//fazer reserva de um quarto específico
			fez = fazerReservaQuartoEspecifico();
			if (fez)
				System.out.println("Reserva feita com sucesso!");
			else
				System.out.println("Houve problema na reserva.");
			break;
		case 4:
			//cancelar reserva
			cancelarReserva();
			break;
		case 5:
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
		System.out.println("--------------------------------");
		List<Reserva> reservas = hotel.getReservas();
		if (reservas.isEmpty())
			System.out.println("Não há reservas atualmente.");
		for (Reserva r : reservas) {
			System.out.println(r);
		}
	}
	
	public String lerString() {
		Scanner input = new Scanner(System.in);
		return input.nextLine().toUpperCase();
	}
	
	public boolean cancelarReserva() {
		//ler o id e encontrar a reserva a ser deletada
		Reserva r = encontrarReserva();
		
		//apagar a data da reserva do quarto
		List<Quarto> quartosReserva = r.getQuartos();
		for (Quarto quarto : quartosReserva) {
			List<LocalDate[]> datasQuarto = quarto.getDatasReservas();
			for (LocalDate[] datas : datasQuarto) {
				if (datas[0].equals(r.getDataIni()) && datas[1].equals(r.getDataFim()))
					datasQuarto.remove(datas);
			}
		}
		
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
	
	
	public boolean fazerReservaQuartoEspecifico() {
		String nomeCliente = pegarNome();
		
		//pega data checkin
		LocalDate dataIni = pegarDataInicio();
		
		//pega data checkout
		LocalDate dataFim = pegarDataFinal(dataIni);
		
		List<Quarto> quartosReserva = pegarQuartosReservaEspecifica(dataIni, dataFim);
		
		if (quartosReserva.isEmpty())
			return false;
		
		//cria a reserva
		String id = Integer.toString(gerarIdReserva());
		
		Reserva reserva = new Reserva(nomeCliente, quartosReserva, dataIni, dataFim, id);
		return hotel.adicionarReserva(reserva);	
		
	}
	
	public List<Quarto> pegarQuartosReservaEspecifica(LocalDate ini, LocalDate fim){
		List<Quarto> quartosReserva = new ArrayList<>();
		String id = "";
		boolean ativo = true;
		String tipoQuarto = "";
		while (ativo) {
			System.out.print("\nTipo do quarto (para fim digite 0): ");
			tipoQuarto = lerString().toUpperCase();
			if (tipoQuarto.equals("0"))
				ativo = false;
			if (ativo) {
				id = pegarIdQuarto(tipoQuarto);
				Quarto q = retornarQuarto(tipoQuarto, id, ini, fim);
				
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
	
	
	public boolean fazerReserva(){
		//pega o nome do cliente
		String nomeCliente = pegarNome();
		
		//pega data de checkin
		LocalDate dataIni = pegarDataInicio();
		
		//pega data checkout
		LocalDate dataFim = pegarDataFinal(dataIni);
		
		//pega os tipos de quarto escolhidos
		List<Quarto> quartosReserva = retornarListaQuartosCliente(dataIni, dataFim);
		
		if (quartosReserva.isEmpty())
			return false;
		
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
	
	public LocalDate pegarDataInicio() {
		
		boolean ativo = true;
		LocalDate dataIni = null;
		while (ativo) {
			ativo = false;
			System.out.print("\nDigite a data de check-in no formato dd/mm/yyyy: ");
			String ini = lerString();
			dataIni = LocalDate.parse(ini, formatter);
		}
		return dataIni;
	}
	
	public LocalDate pegarDataFinal(LocalDate inicio) {
		
		boolean ativo = true;
		LocalDate dataFim = null;
		while(ativo) {
			ativo = false;
			System.out.print("\nDigite a data de check-out no formato dd/mm/yyyy: ");
			String fim = lerString();
			
			dataFim = LocalDate.parse(fim, formatter);
		
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
	
	public List<Quarto> retornarListaQuartosCliente(LocalDate dataIni, LocalDate dataFim){
		List<Quarto> quartosReserva = new ArrayList<>();
		boolean ativo = true;
		String tipoQuarto = "";
		while (ativo) {
			System.out.print("\nTipo do quarto (para fim digite 0): ");
			tipoQuarto = lerString().toUpperCase();
			
			if (tipoQuarto.equals("0"))
				ativo = false;
			
			if (ativo) {
				if (hotel.verificarSePossuiTipo(tipoQuarto)) {
					Quarto quarto = retornarQuarto(tipoQuarto, dataIni, dataFim);	//fazer o retornarQuarto retornar uma cópia do quarto, ou o id e tipo dele ou invés do objeto em si
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
	
	public Quarto retornarQuarto(String tipo, String id, LocalDate ini, LocalDate fim) {
		return hotel.devolverQuartoEspecificoParaReserva(tipo, id, ini, fim);
	}	
	
	public Quarto retornarQuarto(String tipoQuarto, LocalDate dataIni, LocalDate dataFim) {
		return hotel.darQuartoParaReserva(tipoQuarto, dataIni, dataFim);
	}
}
