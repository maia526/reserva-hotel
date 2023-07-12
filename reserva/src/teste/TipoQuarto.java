package teste;

public enum TipoQuarto {
	DOUBLE("Double", 2, 200, "Um quarto duplo."),
	STANDARD("Standars", 1, 130, "Um quarto normal."),
	PRESIDENCIAL("Presidencial", 2, 350, "Uma quarto de luxo.");
	
	private String nome;
	private int capacidade;
	private double diaria; 
	private String descricao;
	
	private TipoQuarto(String nome, int capacidade, double diaria, String descricao) {
		this.capacidade = capacidade;
		this.diaria = diaria;
		this.descricao = descricao;
		this.nome = nome;
	}

	public int getCapacidade() {
		
		return capacidade;
		
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
