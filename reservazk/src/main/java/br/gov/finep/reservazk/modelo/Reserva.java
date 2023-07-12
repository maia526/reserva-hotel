package br.gov.finep.reservazk.modelo;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Reserva {
	private String id;
	private String nomeCliente;
	private String telefoneCliente;
	private String emailCliente;
	private List<Quarto> quartos;
	private double valor;
	private PeriodoReserva periodoReserva;
	final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");;
	private String resumo;
	private String detalhes;
	
	public Reserva(String nomeCliente, String telefoneCliente, String emailCliente, List<Quarto> quartos, PeriodoReserva periodoReserva, String id) {
		this.nomeCliente = nomeCliente;
		this.telefoneCliente = telefoneCliente;
		this.emailCliente = emailCliente;
		this.quartos = quartos;
		this.periodoReserva = periodoReserva;
		this.id = id;
		this.valor = calcularTotal();
		this.resumo = gerarResumo();
		this.detalhes = this.toString();
	}
	
	public Reserva (Map<String, Object> detalhes) {
		this.nomeCliente = (String) detalhes.get("nomeCliente");
		this.telefoneCliente = (String) detalhes.get("telefoneCliente");
		this.emailCliente = (String) detalhes.get("emailCliente");
		this.quartos = popularListaQuartos((List<Object>)detalhes.get("quartos"));
		this.periodoReserva = new PeriodoReserva((Map<String, Object>) detalhes.get("periodoReserva"));
		this.id = (String) detalhes.get("id");
		this.valor = calcularTotal();
		this.resumo = gerarResumo();
		this.detalhes = this.toString();
	}
	
	public List<Quarto> popularListaQuartos(List<Object> quartos){
		List<Quarto> listaQuartos = new ArrayList<>();
		for (Object objeto : quartos) {
			Quarto quarto = new Quarto((Map<String, Object>) objeto);
			listaQuartos.add(quarto);
		}
		
		return listaQuartos;
	}
	
	public String gerarResumo() {
		return "Nome cliente: " + this.nomeCliente +"\nQuartos: " + this.quartos;
	}

	public double calcularTotal() {
		double total = 0;
		for (int i = 0; i < quartos.size(); i++) {
			total += quartos.get(i).retornarValorDiaria();
		}
		return total;
	}

	
	public String getDetalhes() {
		return detalhes;
	}

	public void setDetalhes(String detalhes) {
		this.detalhes = detalhes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getTelefoneCliente() {
		return telefoneCliente;
	}

	public void setTelefoneCliente(String telefoneCliente) {
		this.telefoneCliente = telefoneCliente;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public PeriodoReserva getPeriodoReserva() {
		return periodoReserva;
	}

	public void setPeriodoReserva(PeriodoReserva periodoReserva) {
		this.periodoReserva = periodoReserva;
	}

	public DateTimeFormatter getFormatter() {
		return formatter;
	}

	public List<Quarto> getQuartos() {
		return quartos;
	}
	
	public void setQuartos(List<Quarto> quartos) {
		this.quartos = quartos;
	}
	
	public String getResumo() {
		return resumo;
	}

	public void setResumo(String resumo) {
		this.resumo = resumo;
	}

	@Override
	public String toString() {
		return "Reserva [id=" + id + ", nomeCliente=" + nomeCliente + ", telefoneCliente=" + telefoneCliente
				+ ", emailCliente=" + emailCliente + ", quartos=" + quartos + ", valor=" + valor + ", periodoReserva="
				+ periodoReserva + "]";
	}

	public String retornarStringComTiposDosQuartos() {
		String s = "";
		for (Quarto q : quartos) {
			s += "\n\t" + q.getTipoQuarto();
		}
		return s;
	}
}
