package teste;

public enum TipoQuarto {	//todo: mudar o nome para TipoQuarto quando terminar
	DOUBLE(2, 200, "Um quarto duplo."),
	STANDART(1, 130, "Um quarto normal."),
	PRESIDENCIAL(2, 350, "Uma quarto de luxo.");
	
	private int capacidade;
	private double diaria;
	private String descricao;
	
	private TipoQuarto(int capacidade, double diaria, String descricao) {
		this.capacidade = capacidade;
		this.diaria = diaria;
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}
