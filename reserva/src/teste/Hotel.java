package teste;
import java.util.*;

public class Hotel {
	private String id;
	private String endereco;
	private String pais;
	private String estado;
	private String cidade;
	private String nome;
	private List<TipoQuarto> quartosPorTipo;
	private List<Reserva> reservas;
	
	public Hotel(String idHotel, String endereco, String pais, String estado, String cidade, String nome) {
		this.id = idHotel;
		this.endereco = endereco;
		this.pais = pais;
		this.estado = estado;
		this.cidade = cidade;
		this.nome = nome;
		this.quartosPorTipo = new ArrayList<TipoQuarto>();
		this.reservas = new ArrayList<Reserva>();
	}
	
	public boolean adicionarQuarto(TipoQuarto q) {
		return quartosPorTipo.add(q);
	}
	
	public boolean adicionarReserva(Reserva r) {
		return reservas.add(r);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<TipoQuarto> getQuartos() {
		return quartosPorTipo;
	}

	public void setQuartos(List<TipoQuarto> quartos) {
		this.quartosPorTipo = quartos;
	}

	public List<Reserva> getReservas() {
		return reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}
	
	
	
	
}
