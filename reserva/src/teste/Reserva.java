package teste;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class Reserva {
	private String id;
	private String nomeCliente;
	private List<Quarto> quartos;
	private double valor;
	private LocalDateTime dataIni;
	private LocalDateTime dataFim;
	
	public Reserva(String nomeCliente, List<Quarto> quartos, LocalDateTime dataIni, LocalDateTime dataFim, String id) {
		this.nomeCliente = nomeCliente;
		this.quartos = quartos;
		this.dataIni = dataIni;
		this.dataFim = dataFim;
		this.id = id;
		this.valor = calcularTotal();
		}
	
	public double calcularTotal() {
		double total = 0;
		for (int i = 0; i < quartos.size(); i++) {
			total += quartos.get(i).retornarValorDiaria();
		}
		return total;
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
				+ "\nDataIni: " + formatarData(dataIni) + "\nDataFim: " + formatarData(dataFim);
	}
	
	public String formatarData(LocalDateTime data) {
		String pattern = "dd/MM/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(data);
	}
	
	public String retornarStringComTiposDosQuartos() {
		String s = "";
		for (Quarto q : quartos) {
			s += "\n\t" + q.getTipoQuarto();
		}
		return s;
	}
}
