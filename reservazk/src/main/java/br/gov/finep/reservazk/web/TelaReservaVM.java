package br.gov.finep.reservazk.web;

import java.time.LocalDate;
import java.util.*;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;

import br.gov.finep.reservazk.modelo.Hotel;
import br.gov.finep.reservazk.modelo.Quarto;
import br.gov.finep.reservazk.modelo.Reserva;
import br.gov.finep.reservazk.modelo.TipoQuarto;
import br.gov.finep.safeparams.SafeParams;

public class TelaReservaVM {
	Hotel hotel;
	private TipoQuarto tipoSelecionado;
	private List<TipoQuarto> tipos = new ArrayList<>();
	private String nomeCliente;
	private LocalDate checkin;
	private LocalDate checkout;
	private List<Reserva> reservasFiltradas = new ArrayList<>();
	private List<Reserva> reservasSelecionadas = new ArrayList<>();
	
	@Init
	public void init() {
		this.hotel = Hotel.getHotel();
		inicializarAtributosFiltro();
		inicializarListaTipos();
		reservasFiltradas.clear();
		reservasSelecionadas.clear();
	}
	
	@NotifyChange({"reservasSelecionadas", "reservasFiltradas", "tipoSelecionado", "nomeCliente", "checkin", "checkout"})
	public void inicializarAtributosFiltro() {
		this.tipoSelecionado = null;
		this.nomeCliente = null;
		this.checkin = null;
		this.checkout = null;
	}
	
	@Command
	public void verDetalhes(@BindingParam("idReserva") String idReserva) {
		Executions.getCurrent().sendRedirect(MenuVM.urlTelaDetalhesReserva() + "?" + new SafeParams("reserva", buscarReserva(idReserva)));
	}
	
	public Reserva buscarReserva(String idReserva) {
		for (Reserva reserva : reservasFiltradas) {
			if (reserva.getId().equals(idReserva))
				return reserva;
		}
		return null;
	}
	
	public void removerReservasDeReservasFiltradas() {
		for (Reserva reserva : reservasSelecionadas) {
			reservasFiltradas.remove(reserva);
		}
	}
	
	@Command
	@NotifyChange({"hotel", "reservasFiltradas"})
	public void cancelarReservas() {
		removerReservasDeReservasFiltradas();
		hotel.cancelarReservas(reservasSelecionadas);
		init();
	}
	
	@Command
	@NotifyChange({"reservasSelecionadas", "reservasFiltradas", "tipoSelecionado", "nomeCliente", "checkin", "checkout"})
	public void filtrar() {
		this.reservasFiltradas = popularReservasFitradas();
		
		if (this.tipoSelecionado != null)
			filtrarPorTipo();
		if (this.nomeCliente != null && this.nomeCliente != "")
			filtrarPorNome();
		if (this.checkin != null)
			filtrarPorCheckin();
		if (this.checkout != null)
			filtrarPorCheckout();	
	}
	
	public List<Reserva> popularReservasFitradas(){
		List<Reserva> listaReserva = new ArrayList<>();
		for (Reserva reserva : hotel.getReservas()) {
			listaReserva.add(reserva);
		}
		
		return listaReserva;
	}
	
	public void filtrarPorTipo() {
		List<Reserva> reservasARemover = new ArrayList<>();
		for (Reserva reserva : reservasFiltradas) {
			boolean tem = false;
			List<Quarto> quartosReserva = reserva.getQuartos();
			for (Quarto quarto : quartosReserva) {
				if (quarto.getTipoQuarto().equals(tipoSelecionado))
					tem = true;
			}
			if (!tem)
				reservasARemover.add(reserva);
		}
		this.reservasFiltradas.removeAll(reservasARemover);
	}
	
	public void filtrarPorNome() {
		List<Reserva> reservasARemover = new ArrayList<>();
		for (Reserva reserva : reservasFiltradas) {
			if (!reserva.getNomeCliente().equals(this.nomeCliente))
				reservasARemover.add(reserva);
		}
		this.reservasFiltradas.removeAll(reservasARemover);
	}
	
	public void filtrarPorCheckin() {
		List<Reserva> reservasARemover = new ArrayList<>();
		for (Reserva reserva : reservasFiltradas) {
			if (!reserva.getPeriodoReserva().getInicio().isEqual(checkin))
				reservasARemover.add(reserva);
		}
		this.reservasFiltradas.removeAll(reservasARemover);
	}
	
	public void filtrarPorCheckout() {
		List<Reserva> reservasARemover = new ArrayList<>();
		for (Reserva reserva : reservasFiltradas) {
			if (!reserva.getPeriodoReserva().getFim().isEqual(checkout))
				reservasARemover.add(reserva);
		}
		this.reservasFiltradas.removeAll(reservasARemover);
	}
	
	public void inicializarListaTipos() {
		this.tipos.clear();
		tipos.add(null);
        for (TipoQuarto tipo : TipoQuarto.values()){
        	this.tipos.add(tipo);
        }
	}

	public TipoQuarto getTipoSelecionado() {
		return tipoSelecionado;
	}

	public void setTipoSelecionado(TipoQuarto tipoSelecionado) {
		this.tipoSelecionado = tipoSelecionado;
	}

	public List<TipoQuarto> getTipos() {
		return tipos;
	}

	public void setTipos(List<TipoQuarto> tipos) {
		this.tipos = tipos;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public LocalDate getCheckin() {
		return checkin;
	}

	public void setCheckin(LocalDate checkin) {
		this.checkin = checkin;
	}

	public LocalDate getCheckout() {
		return checkout;
	}

	public void setCheckout(LocalDate checkout) {
		this.checkout = checkout;
	}

	public List<Reserva> getReservasFiltradas() {
		return reservasFiltradas;
	}

	public void setReservasFiltradas(List<Reserva> reservasFiltradas) {
		this.reservasFiltradas = reservasFiltradas;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public List<Reserva> getReservasSelecionadas() {
		return reservasSelecionadas;
	}

	public void setReservasSelecionadas(List<Reserva> reservasSelecionadas) {
		this.reservasSelecionadas = reservasSelecionadas;
	}

	
	
}
