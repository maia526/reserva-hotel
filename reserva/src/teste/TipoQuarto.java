package teste;

public enum TipoQuarto {
	DOUBLE(2, 200, "Um quarto duplo."),
	STANDARD(1, 130, "Um quarto normal."),
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


	public double getDiaria() {
		return diaria;
	}


	public String getDescricao() {
		return descricao;
	}
	
}
