package teste;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Reserva {
	private String id;
	private String nomeCliente;
	private List<Quarto> quartos;
	private double valor;
	private LocalDate dataIni;
	private LocalDate dataFim;
	final DateTimeFormatter formatter;
	
	public Reserva(String nomeCliente, List<Quarto> quartos, LocalDate dataIni, LocalDate dataFim, String id) {
		this.nomeCliente = nomeCliente;
		this.quartos = quartos;
		this.dataIni = dataIni;
		this.dataFim = dataFim;
		this.id = id;
		this.valor = calcularTotal();
		this.formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		}
	
	public double calcularTotal() {
		double total = 0;
		for (int i = 0; i < quartos.size(); i++) {
			total += quartos.get(i).retornarValorDiaria();
		}
		return total;
	}

	
	public LocalDate getDataIni() {
		return dataIni;
	}

	public void setDataIni(LocalDate dataIni) {
		this.dataIni = dataIni;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Quarto> getQuartos() {
		return quartos;
	}
	
	public void setQuartos(List<Quarto> quartos) {
		this.quartos = quartos;
	}

	@Override
	public String toString() {
		return "\nReserva id:" + id + "\nCliente:" + nomeCliente + "\nQuartos:" + retornarStringComTiposDosQuartos() + "\nValor: " + valor
				+ "\nDataIni: " + dataIni.toString() + "\nDataFim: " + dataFim.toString();
	}
	
	public String retornarStringComTiposDosQuartos() {
		String s = "";
		for (Quarto q : quartos) {
			s += "\n\t" + q.getTipoQuarto();
		}
		return s;
	}
}
