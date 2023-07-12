package br.gov.finep.reservazk.modelo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Hotel {
	private static Hotel hotel;
	private List<Reserva> reservas;
	private Map<TipoQuarto, List<Quarto>> quartosPorTipo;
	
	public Hotel(int qtdStandard, int qtdDouble, int qtdPresidencial) {
		this.reservas = new ArrayList<>();
		this.quartosPorTipo = new HashMap<TipoQuarto, List<Quarto>>();
		this.quartosPorTipo.put(TipoQuarto.STANDARD, inicializarListaQuartos(TipoQuarto.STANDARD, qtdStandard));
		this.quartosPorTipo.put(TipoQuarto.DOUBLE, inicializarListaQuartos(TipoQuarto.DOUBLE, qtdDouble));
		this.quartosPorTipo.put(TipoQuarto.PRESIDENCIAL, inicializarListaQuartos(TipoQuarto.PRESIDENCIAL, qtdPresidencial));
	}
	
	public Hotel(Map<String, Object> atributosHotel) {
		this.reservas = (List<Reserva>) atributosHotel.get("reservas");
		this.quartosPorTipo = (Map<TipoQuarto, List<Quarto>>) atributosHotel.get("quartosPorTipo");
	}
	
	public static Hotel getHotel() {
		if (hotel == null)
			hotel = new Hotel(2,2,2);
		return hotel;
	}
	
	public List<Quarto> inicializarListaQuartos(TipoQuarto tipoQuarto, int qtd){
		List<Quarto> lista = new ArrayList<>();
		int id = 1;
		for (int i = 0; i < qtd; i++) {
			Quarto q = new Quarto(tipoQuarto, Integer.toString(id));
			lista.add(q);
			id++;
		}
		return lista;
	}
	
	public void cancelarReservas(List<Reserva> listaReservas) {
		reservas.removeAll(listaReservas);
	}
	
	public List<Quarto> retornarTodosOsQuartos(){
		List<Quarto> listaQuartos = new ArrayList<>();
		for (TipoQuarto tipoQuarto : TipoQuarto.values()) {
			List<Quarto> listaTipoQuarto = quartosPorTipo.get(tipoQuarto);
			for (Quarto quarto : listaTipoQuarto) {
				listaQuartos.add(quarto);
			}
		}
		return listaQuartos;
	}
	
	public Map<TipoQuarto, List<Quarto>> getQuartosPorTipo() {
		return quartosPorTipo;
	}

	public void setQuartosPorTipo(Map<TipoQuarto, List<Quarto>> quartosPorTipo) {
		this.quartosPorTipo = quartosPorTipo;
	}

	public Quarto encontrarQuartoDaReserva(TipoQuarto tipoQuarto, LocalDate ini, LocalDate fim) {
		List<Quarto> quartos = quartosPorTipo.get(tipoQuarto);
		for (Quarto quarto : quartos) {
			List<PeriodoReserva> datas = quarto.getDatasReservas();
			for (PeriodoReserva data : datas) {
				if (data.getInicio().equals(ini) && data.getFim().equals(fim))
					return quarto;
			}
		}
		System.out.println("Quarto n�o encontrado.");
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
	
	public Reserva retornarReserva(String id) {
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
			List<PeriodoReserva> datasReserva = quarto.getDatasReservas();
			for (PeriodoReserva datas : datasReserva) {
				disponivel = verificarDisponibilidade(datas, dataIni, dataFim);
			}
			if (disponivel) {
				PeriodoReserva datas = new PeriodoReserva(dataIni, dataFim);
				quarto.adcionaPeriodoReserva(datas);
				return quarto;
			}	
		}
		System.out.println("N�o h� quarto desse tipo dispon�vel no per�odo desejado.");
		return null;
	}
	
	public boolean verificarDisponibilidade(PeriodoReserva datasReservaExistente, LocalDate dataIni, LocalDate dataFim) {
		LocalDate ini = datasReservaExistente.getInicio();
		LocalDate fim = datasReservaExistente.getFim();
		
		//TODO: verificar se condição está certa
		return !((dataIni.isBefore(ini) && ini.isBefore(dataFim) && dataFim.isBefore(fim)) || 
			(ini.isBefore(dataIni) && dataIni.isBefore(fim) && fim.isBefore(dataFim)) || 
			(ini.isBefore(dataIni) && ini.isBefore(dataFim) && dataIni.isBefore(fim) && dataFim.isBefore(fim)) ||
			(ini.isEqual(dataIni) || fim.isEqual(dataFim)));
	}
	
	
	
	public Quarto darQuartoEspecificoParaReserva(String tipoQuarto, String idQuarto, LocalDate dataIni, LocalDate dataFim) {
		List<Quarto> quartos = quartosPorTipo.get(TipoQuarto.valueOf(tipoQuarto));
		boolean disponivel = true;
		for (Quarto quarto : quartos) {
			if (quarto.getId().equals(idQuarto)) {
				List<PeriodoReserva> datasReserva = quarto.getDatasReservas();
				for (PeriodoReserva datas : datasReserva) {
					disponivel = verificarDisponibilidade(datas, dataIni, dataFim);
				}
				if (disponivel) {
					PeriodoReserva periodoReserva = new PeriodoReserva(dataIni, dataFim);
					quarto.adcionaPeriodoReserva(periodoReserva);
					return quarto;
				}
			}
		}
		System.out.println("O quarto n�o est� dispon�vel no per�odo desejado.");
		return null;
	}
	
	public boolean verificarSeQuartoExiste(String idQuarto, String tipoQuarto) {
		List<Quarto> quartos = quartosPorTipo.get(TipoQuarto.valueOf(tipoQuarto));
		for (Quarto q : quartos) {
			if (q.getId().equals(idQuarto))
				return true;
		}
		return false;
	}
	
	public boolean adicionarReserva(Reserva reserva) {
		return reservas.add(reserva);
	}
	
	public boolean adicionarReserva(String nomeCliente, String telefoneCliente, String emailCliente, 
									List<Quarto> quartos, LocalDate checkin, LocalDate checkout) {
		PeriodoReserva periodoReserva = new PeriodoReserva(checkin, checkout);
		quartos = adicionarPeriodoReservaNosQuartos(quartos, periodoReserva);
		Reserva reserva = new Reserva(nomeCliente, telefoneCliente, emailCliente, quartos, periodoReserva, gerarIdReserva());
		
		return adicionarReserva(reserva);
	}
	
	public Quarto buscarQuarto(String id, TipoQuarto tipo) {
		for (Quarto quarto : quartosPorTipo.get(tipo)) {
			if (quarto.getId().equals(id))
				return quarto;
		}
		return null;
	}
	
	public List<Quarto> adicionarPeriodoReservaNosQuartos(List<Quarto> quartos, PeriodoReserva periodoReserva) {
		List<Quarto> quartosComPeriodo = new ArrayList<>();
		for (Quarto quarto : quartos) {
			buscarQuarto(quarto.getId(), quarto.getTipoQuarto()).adcionaPeriodoReserva(periodoReserva);
			quarto.adcionaPeriodoReserva(periodoReserva);
			quartosComPeriodo.add(quarto);
		}
		return quartosComPeriodo;
	}
	
	public String gerarIdReserva() {
    	int id = 1;
    	
    	for (Reserva reservaHotel : this.reservas) {
    		if (Integer.toString(id).equals(reservaHotel.getId()))
    			id++;
    	}
    	
    	return Integer.toString(id);
    }


	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {//private
		this.reservas = reservas;
	}
	
	public List<Quarto> retornarListaDeQuartos(TipoQuarto tipoQuarto){
		return quartosPorTipo.get(tipoQuarto);
	}
}
