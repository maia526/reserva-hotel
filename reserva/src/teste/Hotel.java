package teste;
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
	
	public void devolverQuartoParaHotel(Quarto q) {
		List<Quarto> quartos = quartosPorTipo.get(q.getTipoQuarto());
		quartos.add(q);
	}
	
	//todo: trocar date pra localdatetime
	public Quarto darQuartoParaReserva(String tipoQuarto, LocalDateTime dataIni, LocalDateTime dataFim) {
		List<Quarto> quartos = quartosPorTipo.get(TipoQuarto.valueOf(tipoQuarto)); 
		//todo: verificar para cada quarto se ele está disponiível na data da reserva e retorna o primeiro que estiver. Caso contrário retorna null
		//cada quarto agora tem uma lista de vetores com as datas de inicio e fim de cada reserva existente
		for (Quarto quarto : quartos) {
			List<LocalDateTime[]> datasReserva = quarto.getDatasReservas();
			for(LocalDateTime[] datas : datasReserva) {
				LocalDateTime ini = datas[0];
				LocalDateTime fim = datas[1];
				
				
			}
		}
		Quarto quarto = quartos.get(0);
		quartos.remove(0);
		return quarto;
	}
	
	public boolean verificarDisponibilidade(LocalDateTime[] datasReservaExistente, LocalDateTime dataIni, LocalDateTime dataFim) {
		
		return false;
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
