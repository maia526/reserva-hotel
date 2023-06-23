package teste;

import java.time.LocalDateTime;
import java.util.*;

public class Quarto {
	private TipoQuarto tipo;
	private String id;
	private List<LocalDateTime[]>datasReservas;	//uma lista de vetores de tamanho 2 que guardem a data de inicio e fim de uma reserva
	
	public Quarto(TipoQuarto tipo, String id) {
		this.tipo = tipo;
		this.id = id; 
		this.datasReservas = new ArrayList<>();
	}
	
	public double retornarValorDiaria(){
		return tipo.getDiaria();
	}
	
	public void setTipoQuarto(TipoQuarto tipo) {
		this.tipo = tipo;
	}

	public List<LocalDateTime[]> getDatasReservas() {
		return datasReservas;
	}

	public void setDatasReservas(List<LocalDateTime[]> datasReservas) {
		this.datasReservas = datasReservas;
	}

	public TipoQuarto getTipoQuarto() {
		return tipo;
	}

	public String getId() {
		return this.id;
	}
	@Override
	public String toString() {
		return "\nQuarto " + id + "\nTipo: " + tipo.toString() + "\nCapacidade: " + tipo.getCapacidade();
	}
	
}
