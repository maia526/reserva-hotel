package teste;

import java.util.*;

public class TipoQuarto {
	private int id;
	private String tipo;
	private String descricao;
	private int capacidade;
	private double diaria;
	private List<Quarto> quartos;
	
	public TipoQuarto(String descricao, int capacidade, double diaria, String nome, int qtd) {
		this.descricao = descricao;
		this.capacidade = capacidade;
		this.diaria = diaria;
		this.tipo = nome;
		this.quartos = inicializarListaQuartos(qtd);
	}
	
	public List<Quarto> inicializarListaQuartos(int qtd){
		List<Quarto> lista = new ArrayList<Quarto>();
		int id = 0;
		for (int i = 0; i < qtd; i++) {
			Quarto q = new Quarto(this, Integer.toString(id));
			id++;
			lista.add(q);
		}
		return lista;
	}

	public boolean verificarDisponibilidade() {
		return quartos.size() > 0;
	}
	
	public Quarto reservarERetornarUmQuarto() {
		if (verificarDisponibilidade()) {
			Quarto q = quartos.get(0);
			this.quartos.remove(quartos.size()-1);
			return q;	//indexOutOfBound Exception
		}
		System.out.println("Não há disponibilidade.");
		return null;
	}
	
	public void cancelarReservaDeUmQuarto(Quarto q) {
		this.quartos.add(q);
	}

	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getTipo() {
		return tipo;
	}



	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public String getDescricao() {
		return descricao;
	}



	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}



	public int getCapacidade() {
		return capacidade;
	}



	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}



	public double getDiaria() {
		return diaria;
	}



	public void setDiaria(double diaria) {
		this.diaria = diaria;
	}



	@Override
	public String toString() {
		return "\n" + tipo + "\nDescricao: " + descricao + "\nCapacidade: " + capacidade + "\nDiaria=" + diaria + "\nDisponível: " + quartos.size();
	}

	

	
	
}
