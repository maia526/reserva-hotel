package teste;
import java.util.*;

public class Hotel {
	
	private String nome;
	private List<Quarto> quartosStandart;
	private List<Quarto> quartosDouble;
	private List<Quarto> quartosPresidencial;
	private List<Reserva> reservas;
	
	public Hotel(String nome, int qtdStandart, int qtdDouble, int qtdPresidencial) {
		this.nome = nome;
		this.quartosStandart = inicializarListaQuartos(TipoQuarto.STANDART, qtdStandart);
		this.quartosDouble = inicializarListaQuartos(TipoQuarto.DOUBLE, qtdDouble);
		this.quartosPresidencial = inicializarListaQuartos(TipoQuarto.PRESIDENCIAL, qtdPresidencial);
		this.reservas = new ArrayList<Reserva>();
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
	
	public void receberQuarto(Quarto q) {
		String tipoQuarto = q.getTipoQuarto();
		switch(tipoQuarto) {
			case "STANDART":
				quartosStandart.add(q);
				break;
			case "DOUBLE":
				quartosDouble.add(q);
				break;
			case "PRESIDENCIAL":
				quartosPresidencial.add(q);
				break;
		}
	}
	
	public Quarto retornarQuarto(TipoQuarto tipo) {
		Quarto q = null;
		switch (tipo) {
			case STANDART:
				q = quartosStandart.get(0);
				quartosStandart.remove(0);
				break;
			case DOUBLE:
				q = quartosDouble.get(0);
				quartosDouble.remove(0);
				break;
			case PRESIDENCIAL:
				q = quartosPresidencial.get(0);
				quartosPresidencial.remove(0);
				break;
		}
		return q;
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

	public List<Quarto> getQuartosStandart() {
		return quartosStandart;
	}

	public void setQuartosStandart(List<Quarto> quartosStandart) {
		this.quartosStandart = quartosStandart;
	}

	public List<Quarto> getQuartosDouble() {
		return quartosDouble;
	}

	public void setQuartosDouble(List<Quarto> quartosDouble) {
		this.quartosDouble = quartosDouble;
	}

	public List<Quarto> getQuartosPresidencial() {
		return quartosPresidencial;
	}

	public void setQuartosPresidencial(List<Quarto> quartosPresidencial) {
		this.quartosPresidencial = quartosPresidencial;
	}
	
	
}
