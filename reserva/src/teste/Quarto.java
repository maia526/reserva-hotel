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
	
	public void setTipoQuarto(TipoQuarto tipo) {
		this.tipo = tipo;
	}
	
	public String getTipoQuarto() {
		return tipo.toString();
	}

	@Override
	public String toString() {
		return "\nQuarto " + id + "\nTipo: " + tipo.toString() + "\nCapacidade: " + tipo.getCapacidade();
	}
	
}
