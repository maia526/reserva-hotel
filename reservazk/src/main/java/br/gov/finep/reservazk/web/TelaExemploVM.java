/*
 * utf-8
 */
package br.gov.finep.reservazk.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import br.com.caelum.stella.type.Estado;
import br.gov.finep.reservazk.modelo.Empresa;
import br.gov.finep.reservazk.servico.ServicoEmpresa;
import br.gov.finep.safeparams.SafeParams;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TelaExemploVM implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<Empresa> empresas = new ArrayList<Empresa>();

    private Estado ufSelecionada;

    @WireVariable
    private ServicoEmpresa servicoEmpresa;

    @Init
    public void init() {
        this.empresas.clear();
    }

    public List<Empresa> getEmpresas() {
        return empresas;
    }

    public void setEmpresas(List<Empresa> empresas) {
        this.empresas = empresas;
    }

    public Estado getUfSelecionada() {
        return ufSelecionada;
    }

    public void setUfSelecionada(Estado ufSelecionada) {
        this.ufSelecionada = ufSelecionada;
    }

    public List<Estado> listarUFs() {
        return Arrays.asList(Estado.values());
    }

    @Command
    @NotifyChange("empresas")
    public void buscar() {
        this.empresas.clear();
        this.empresas.addAll(this.servicoEmpresa.buscar(this.ufSelecionada));
    }

    @Command
    public void visualizar(@BindingParam("idEmpresa") Long idEmpresa) {
        Executions.getCurrent().sendRedirect(MenuVM.urlDetalheEmpresaMVVM() + "?" + new SafeParams("idEmpresa", idEmpresa));
    }
}
