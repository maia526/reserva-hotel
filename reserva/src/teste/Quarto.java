package teste;

public class Quarto {
	private TipoQuarto tipo;
	private String id;
	
	public Quarto(TipoQuarto tipo, String id) {
		this.tipo = tipo;
		this.id = id;
	}
	
	public double retornarValorDiaria(){
		return tipo.getDiaria();
	}

	@Override
	public String toString() {
		return "\nQuarto tipo: " + tipo;
	}

}
