package teste;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Hotel {
	
	private String nome;
	private List<Reserva> reservas;
	private Map<TipoQuarto, List<Quarto>> quartosPorTipo;
	
	public Hotel(String nome, int qtdStandard, int qtdDouble, int qtdPresidencial) {
		this.nome = nome;
		this.reservas = new ArrayList<>();
		this.quartosPorTipo = new HashMap<TipoQuarto, List<Quarto>>();
		this.quartosPorTipo.put(TipoQuarto.STANDARD, inicializarListaQuartos(TipoQuarto.STANDARD, qtdStandard));
		this.quartosPorTipo.put(TipoQuarto.DOUBLE, inicializarListaQuartos(TipoQuarto.DOUBLE, qtdDouble));
		this.quartosPorTipo.put(TipoQuarto.PRESIDENCIAL, inicializarListaQuartos(TipoQuarto.PRESIDENCIAL, qtdPresidencial));
	}
	
	public List<Quarto> inicializarListaQuartos(TipoQuarto tipo, int qtd){
		List<Quarto> lista = new ArrayList<>();
		int id = 0;
		for (int i = 0; i < qtd; i++) {
			Quarto q = new Quarto(tipo, Integer.toString(id));
			lista.add(q);
			id++;
		}
		
		return lista;
	}
	
	public Quarto encontrarQuartoDaReserva(TipoQuarto tipoQuarto, LocalDate ini, LocalDate fim) {
		List<Quarto> quartos = quartosPorTipo.get(tipoQuarto);
		for (Quarto quarto : quartos) {
			List<LocalDate[]> datas = quarto.getDatasReservas();
			for (LocalDate[] data : datas) {
				if (data[0].equals(ini) && data[1].equals(fim))
					return quarto;
			}
		}
		System.out.println("Quarto não encontrado.");
		return null;
	}
	
	public boolean verificarSePossuiTipo(String tipoQuarto) {
		List<String> listaNomesTiposQuarto = new ArrayList<>();
		for (TipoQuarto tipo : TipoQuarto.values()) {
			listaNomesTiposQuarto.add(tipo.toString());
		}
		for (String nomeTipo : listaNomesTiposQuarto) {
			if (nomeTipo.equals(tipoQuarto))
				return true;
		}
		return false;
	}
	
	public Reserva retornaReserva(String id) {
		Reserva res = null;
		for (Reserva r : reservas) {
			if (r.getId().equals(id)) {
				res = r;
				reservas.remove(r);
				return res;
			}
		}
		return res;
	}
	
	public Quarto darQuartoParaReserva(String tipoQuarto, LocalDate dataIni, LocalDate dataFim) {
		List<Quarto> quartos = quartosPorTipo.get(TipoQuarto.valueOf(tipoQuarto)); 
		boolean disponivel = true;
		for (Quarto quarto : quartos) {
			List<LocalDate[]> datasReserva = quarto.getDatasReservas();
			for (LocalDate[] datas : datasReserva) {
				disponivel = verificarDisponibilidade(datas, dataIni, dataFim);
			}
			if (disponivel) {
				LocalDate[] datas = {dataIni, dataFim};
				quarto.adcionarDatasReserva(datas);
				return quarto;
			}	
		}
		System.out.println("Não há quarto desse tipo disponível no período desejado.");
		return null;
	}
	
	public boolean verificarDisponibilidade(LocalDate[] datasReservaExistente, LocalDate dataIni, LocalDate dataFim) {
		LocalDate ini = datasReservaExistente[0];
		LocalDate fim = datasReservaExistente[1];
		
		return !( (dataIni.isBefore(ini) && ini.isBefore(dataFim) && dataFim.isBefore(fim)) || 
			(ini.isBefore(dataIni) && dataIni.isBefore(fim) && fim.isBefore(dataFim)) || 
			(ini.isBefore(dataIni) && ini.isBefore(dataFim) && dataIni.isBefore(fim) && dataFim.isBefore(fim)));
	}
	
	
	
	public Quarto devolverQuartoEspecificoParaReserva(String tipoQuarto, String id, LocalDate dataIni, LocalDate dataFim) {
		List<Quarto> quartos = quartosPorTipo.get(TipoQuarto.valueOf(tipoQuarto));
		boolean disponivel = true;
		for (Quarto quarto : quartos) {
			if (quarto.getId().equals(id)) {
				List<LocalDate[]> datasReserva = quarto.getDatasReservas();
				for (LocalDate[] datas : datasReserva) {
					disponivel = verificarDisponibilidade(datas, dataIni, dataFim);
				}
				if (disponivel) {
					LocalDate[] data = {dataIni, dataFim};
					quarto.adcionarDatasReserva(data);
					return quarto;
				}
			}
		}
		System.out.println("O quarto não está disponível no período desejado.");
		return null;
	}
	
	public boolean verificarSeQuartoExiste(String id, String tipoQuarto) {
		List<Quarto> quartos = quartosPorTipo.get(TipoQuarto.valueOf(tipoQuarto));
		for (Quarto q : quartos) {
			if (q.getId().equals(id))
				return true;
		}
		return false;
	}
	
	public boolean adicionarReserva(Reserva r) {
		return reservas.add(r);
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}
	
	public List<Quarto> retornarListaDeQuartos(TipoQuarto tipoQuarto){
		return quartosPorTipo.get(tipoQuarto);
	}
}
