package br.gov.finep.reservazk.modelo;
public enum TipoQuarto {
	DOUBLE("Double", 2, 200, "Um quarto duplo.", true),
	STANDARD("Standard", 1, 130, "Um quarto normal.", true),
	PRESIDENCIAL("Presidencial", 2, 350, "Uma quarto de luxo.", false);
	
	private boolean economico;
	private String nome;
	private int capacidade;
	private double diaria; 
	private String descricao;
	
	private TipoQuarto(String nome, int capacidade, double diaria, String descricao, boolean economico) {
		this.capacidade = capacidade;
		this.diaria = diaria;
		this.descricao = descricao;
		this.nome = nome;
		this.economico = economico;
	}

	public int getCapacidade() {
		
		return capacidade;
		
	}

	
	public boolean isEconomico() {
		return economico;
	}

	public void setEconomico(boolean economico) {
		this.economico = economico;
	}

	public void setCapacidade(int capacidade) {
		this.capacidade = capacidade;
	}

	public void setDiaria(double diaria) {
		this.diaria = diaria;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public double getDiaria() {
		return diaria;
	}


	public String getDescricao() {
		return descricao;
	}
	
}
