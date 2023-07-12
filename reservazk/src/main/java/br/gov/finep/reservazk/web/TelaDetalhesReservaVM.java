package br.gov.finep.reservazk.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;

import br.gov.finep.reservazk.modelo.Reserva;
import br.gov.finep.safeparams.SafeParams;

public class TelaDetalhesReservaVM {
	private Reserva reserva;
	private List<Reserva> reservaLista = new ArrayList<>();
	@Init
	public void init() {
		String parametros = Executions.getCurrent().getParameter(SafeParams.DEFAULT_DATA_PARAM);
    	if (parametros != null) {
    		this.reserva = new Reserva((Map<String, Object>) new SafeParams(parametros).get("reserva"));
        }
    	this.reservaLista.add(reserva);
	}
	public Reserva getReserva() {
		return reserva;
	}
	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}
	public List<Reserva> getReservaLista() {
		return reservaLista;
	}
	public void setReservaLista(List<Reserva> reservaLista) {
		this.reservaLista = reservaLista;
	}
	
	

}
