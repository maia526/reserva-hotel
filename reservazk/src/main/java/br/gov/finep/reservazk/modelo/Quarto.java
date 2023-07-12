package br.gov.finep.reservazk.modelo;

import java.util.*;

public class Quarto {
	private TipoQuarto tipo;
	private String id;
	private List<PeriodoReserva> datasReservas;	//uma lista de vetores de tamanho 2 que guardem a data de inicio e fim de uma reserva
	
	public Quarto(TipoQuarto tipo, String id) {
		this.tipo = tipo;
		this.id = id; 
		this.datasReservas = new ArrayList<>();
	}
	
	public Quarto (Map<String, Object> detalhes) {
		this.tipo =  TipoQuarto.valueOf((String) detalhes.get("tipo")) ;
		this.id = (String) detalhes.get("id");
		this.datasReservas = populaDatasReservas((List<Object>) detalhes.get("datasReservas"));
	}
	

	public List<PeriodoReserva> populaDatasReservas(List<Object> datas){
		List<PeriodoReserva> listaPeriodoReserva = new ArrayList<>();
		for (Object data : datas) {
			PeriodoReserva periodoReserva = new PeriodoReserva((Map<String,Object>) data);
			listaPeriodoReserva.add(periodoReserva);
		}
		
		return listaPeriodoReserva;
	}
	
	public void adcionaPeriodoReserva(PeriodoReserva datas) {
		datasReservas.add(datas);
	}
	
	
	public double retornarValorDiaria(){
		return tipo.getDiaria();
	}
	
	public void setTipoQuarto(TipoQuarto tipo) {
		this.tipo = tipo;
	}

	public List<PeriodoReserva> getDatasReservas() {
		return datasReservas;
	}

	public void setDatasReservas(List<PeriodoReserva> datasReservas) {
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
		return "\nQuarto " + id + "\nTipo: " + tipo.getNome() + "\nCapacidade: " + tipo.getCapacidade();
	}
	
}
