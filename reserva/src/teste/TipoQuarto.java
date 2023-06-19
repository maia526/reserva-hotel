package teste;

public class Quarto {
	private String id;
	private String nome;	//todo: trocar para tipo
	private String descricao;
	private int capacidade;
	private double diaria;
	private boolean ocupado;
	
	public Quarto(String descricao, int capacidade, double diaria, String nome) {
		this.descricao = descricao;
		this.capacidade = capacidade;
		this.ocupado = false;
		this.diaria = diaria;
		this.nome = nome;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public boolean isOcupado() {
		return ocupado;
	}

	public void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "\n" + nome + "\nDescricao: " + descricao + "\nCapacidade: " + capacidade + "\nDiaria=" + diaria;
	}

	

	
	
}
