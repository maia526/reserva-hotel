package br.gov.finep.reservazk.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import br.com.caelum.stella.type.Estado;
import br.gov.finep.reservazk.modelo.Empresa;
import br.gov.finep.reservazk.modelo.TipoQuarto;
import br.gov.finep.reservazk.servico.ServicoEmpresa;
import br.gov.finep.safeparams.SafeParams;

public class TelaHomeVM {
    private List<TipoQuarto> tipos = new ArrayList<TipoQuarto>();
    
	@Init
	public void init() {
		inicializarListaTipos();
    }

	public void inicializarListaTipos() {
		this.tipos.clear();
        for (TipoQuarto tipo : TipoQuarto.values()){
        	this.tipos.add(tipo);
        }
	}

	public List<TipoQuarto> getTipos() {
		return tipos;
	}

	public void setTipos(List<TipoQuarto> tipos) {
		this.tipos = tipos;
	}	
}
