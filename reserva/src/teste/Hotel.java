package teste;
import java.util.*;

public class Hotel {
	
	private String nome;
	private List<Reserva> reservas;
	private Map<TipoQuarto, List<Quarto>> quartosPorTipo;
	
	public Hotel(String nome, int qtdStandard, int qtdDouble, int qtdPresidencial) {
		this.nome = nome;
		this.reservas = new ArrayList<Reserva>();
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
	
	public boolean verificarSePossuiTipo(String tipoQuarto) {
		List<String> listaNomesTiposQuarto = new ArrayList<String>();
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
	
	public void devolverQuartoParaHotel(Quarto q) {
		List<Quarto> quartos = quartosPorTipo.get(q.getTipoQuarto());
		quartos.add(q);
	}
	
	public Quarto darQuartoParaReserva(String tipoQuarto) {
		List<Quarto> quartos = quartosPorTipo.get(TipoQuarto.valueOf(tipoQuarto)); 
		Quarto quarto = quartos.get(0);
		quartos.remove(0);
		return quarto;
	}
	
	public Quarto devolverQuartoEspecificoParaReserva(String tipoQuarto, String id) {
		List<Quarto> quartos = quartosPorTipo.get(TipoQuarto.valueOf(tipoQuarto));
		Quarto q = null;
		for (Quarto quarto : quartos) {
			if (quarto.getId().equals(id)) {
				q = quarto;
				break;
			}
		}
		quartos.remove(q);
		return q;
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
