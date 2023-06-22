package teste;

import java.text.SimpleDateFormat;
import java.util.*;

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
	
	public String formatarData(Date data) {
		String pattern = "dd/MM/yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(data);
		
		return date;
	}
	
	public String retornarStringComTiposDosQuartos() {
		String s = "";
		for (Quarto q : quartos) {
			s += "\n\t" + q.getTipoQuarto();
		}
		return s;
	}
}
