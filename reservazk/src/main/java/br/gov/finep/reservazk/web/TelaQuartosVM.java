package br.gov.finep.reservazk.web;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import br.com.caelum.stella.type.Estado;
import br.gov.finep.reservazk.modelo.Empresa;
import br.gov.finep.reservazk.modelo.Hotel;
import br.gov.finep.reservazk.modelo.PeriodoReserva;
import br.gov.finep.reservazk.modelo.Quarto;
import br.gov.finep.reservazk.modelo.TipoQuarto;
import br.gov.finep.reservazk.servico.ServicoEmpresa;
import br.gov.finep.safeparams.SafeParams;

public class TelaQuartosVM {
	private static final long serialVersionUID = 1L;
	
	private Hotel hotel;
	private Map<TipoQuarto, List<Quarto>> quartosHotel;
	private List<Quarto> quartosFiltrados = new ArrayList<>();
	private List<Quarto> quartosSelecionados = new ArrayList<>();
	private List<TipoQuarto> tipos = new ArrayList<>();
	private LocalDate checkin;
	private LocalDate checkout;
	private boolean economico;
	private boolean conforto;
	private int numeroQuarto;
	private int qtdHospedes;
	private TipoQuarto tipoSelecionado;


	@Init
    public void init() {
		hotel = Hotel.getHotel();
		inicializarAtributosDoFiltro();
		inicializarListaTipos();
        this.quartosHotel = hotel.getQuartosPorTipo();
        quartosFiltrados.clear();
        quartosSelecionados.clear();
    }
	
	@NotifyChange({"quartosFiltrado", "checkin", "checkout", "economico", "conforto", "numeroQuarto", "qtdHospedes", "tipoSelecionado"})
	public void inicializarAtributosDoFiltro() {
		this.checkin = null;
		this.checkout = null;
		this.economico = false;
		this.conforto = false;
		this.numeroQuarto = 0;
		this.qtdHospedes = 0;
		this.tipoSelecionado = null;
	}
	
	public void inicializarListaTipos() {
		this.tipos.clear();
		tipos.add(null);
        for (TipoQuarto tipo : TipoQuarto.values()){
        	this.tipos.add(tipo);
        }
	}
	
	@Command
	@NotifyChange({"quartosFiltrado", "checkin", "checkout", "economico", "conforto", "numeroQuarto", "qtdHospedes", "tipoSelecionado"})
	public void filtrar (){
		this.quartosFiltrados = hotel.retornarTodosOsQuartos();
		
		if (economico ^ conforto)
			filtrarPorEconomicoConforto();
		if (tipoSelecionado != null)
			filtrarPorTipo();
		if (numeroQuarto > 0)
			filtrarPorNumero();
		if (qtdHospedes > 0)
			filtrarPorQtdHospedes();
		if (checkin != null && checkout!= null)
			filtrarPorData();
	}
	
	@Command
	public void reservar() {
		if (!quartosSelecionados.isEmpty())
			Executions.getCurrent().sendRedirect(MenuVM.urlTelaFormularioReserva() + "?" + new SafeParams("selecionados", quartosSelecionados));
	}

	public void filtrarPorEconomicoConforto() {
		List<Quarto> quartosARemover = new ArrayList<>();
		if (economico) {
			for (Quarto quarto: quartosFiltrados) {
				if (!quarto.getTipoQuarto().isEconomico())
					quartosARemover.add(quarto);
			}
		}
		if (conforto) {
			for (Quarto quarto: quartosFiltrados) {
				if (quarto.getTipoQuarto().isEconomico())
					quartosARemover.add(quarto);
			}
		}
		quartosFiltrados.removeAll(quartosARemover);
	}
	
	public void filtrarPorTipo() {
		List<Quarto> quartosARemover = new ArrayList<>();
		for (Quarto quarto: quartosFiltrados) {
			if (!quarto.getTipoQuarto().equals(tipoSelecionado))
				quartosARemover.add(quarto);
		}
		quartosFiltrados.removeAll(quartosARemover);
	}
	
	public void filtrarPorNumero() {
		List<Quarto> quartosARemover = new ArrayList<>();
		for (Quarto quarto: quartosFiltrados) {
			if (!quarto.getId().equals(Integer.toString(numeroQuarto)))
				quartosARemover.add(quarto);
		}
		quartosFiltrados.removeAll(quartosARemover);
	}
	
	public void filtrarPorQtdHospedes() {
		List<Quarto> quartosARemover = new ArrayList<>();
		for (Quarto quarto: quartosFiltrados) {
			if (quarto.getTipoQuarto().getCapacidade() < qtdHospedes)
				quartosARemover.add(quarto);
		}
		quartosFiltrados.removeAll(quartosARemover);
	}
	
	public void filtrarPorData () {
		List<Quarto> quartosARemover = new ArrayList<>();
		boolean disponivel = true;
		for (Quarto quarto : quartosFiltrados) {
			List<PeriodoReserva> listaDatas = quarto.getDatasReservas();
			for (PeriodoReserva datas : listaDatas) {
				disponivel = hotel.verificarDisponibilidade(datas, checkin, checkout);
			}
			if (!disponivel)
				quartosARemover.add(quarto);
		}
		quartosFiltrados.removeAll(quartosARemover);
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
	

	public List<Quarto> getQuartosSelecionados() {
		return quartosSelecionados;
	}

	public void setQuartosSelecionados(List<Quarto> quartosSelecionados) {
		this.quartosSelecionados = quartosSelecionados;
	}

	public Hotel getHotel() {
		return hotel;
	}

	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}

	public Map<TipoQuarto, List<Quarto>> getQuartosHotel() {
		return quartosHotel;
	}

	public void setQuartosHotel(Map<TipoQuarto, List<Quarto>> quartosHotel) {
		this.quartosHotel = quartosHotel;
	}

	public List<Quarto> getQuartosFiltrado() {
		return quartosFiltrados;
	}

	public void setQuartosFiltrado(List<Quarto> quartosFiltrado) {
		this.quartosFiltrados = quartosFiltrado;
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


	public void setQuartosFiltrados(List<Quarto> quartosFiltrados) {
		this.quartosFiltrados = quartosFiltrados;
	}
	
	public boolean isEconomico() {
		return economico;
	}

	public void setEconomico(@BindingParam("val") boolean economico) {
		this.economico = economico;
	}

	public boolean isConforto() {
		return conforto;
	}

	public void setConforto(@BindingParam("val") boolean conforto) {
		this.conforto = conforto;
	}

	public int getNumeroQuarto() {
		return numeroQuarto;
	}

	public void setNumeroQuarto(int numeroQuarto) {
		this.numeroQuarto = numeroQuarto;
	}

	public int getQtdHospedes() {
		return qtdHospedes;
	}

	public void setQtdHospedes(int qtdHospedes) {
		this.qtdHospedes = qtdHospedes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}	
}
