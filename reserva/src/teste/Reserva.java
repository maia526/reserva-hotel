package teste;

import java.util.List;
import java.util.Date;

public class Reserva {
	private String id;
	private String nomeCliente;
	private List<Quarto> quartos;	//uma pessoa pode fazer a reserva de dois quartos ao mesmo tempo
	private double valor;
	private Date dataIni;
	private Date dataFim;
	private boolean cancelada;
	
	public Reserva(String nomeCliente, List<Quarto> quartos, Date dataIni, Date dataFim, String id) {
		this.nomeCliente = nomeCliente;
		this.quartos = quartos;
		this.dataIni = dataIni;
		this.dataFim = dataFim;
		this.cancelada = false;
		this.id = id;
		this.valor = calcularTotal();
		}
	
	public double calcularTotal() {
		double valor = 0;
		for (int i = 0; i < quartos.size(); i++) {
			valor += quartos.get(i).getDiaria();
		}
		return valor;
	}

	@Override
	public String toString() {
		return "\nReserva " + id + "\nNomeCliente:" + nomeCliente + "\nQuartos:" + quartos + "\nValor: " + valor
				+ "\nDataIni: " + dataIni + "\nDataFim: " + dataFim + "\nCancelada: " + cancelada;
	}

	
	
}
