/*
 * utf-8
 */
package br.gov.finep.reservazk.web;

import java.io.Serializable;

import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;

import br.gov.finep.reservazk.modelo.Empresa;
import br.gov.finep.reservazk.servico.ServicoEmpresa;
import br.gov.finep.safeparams.SafeParams;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DetalheEmpresaVM implements Serializable {

    private static final long serialVersionUID = 1L;

    private Empresa empresa;
    
    @WireVariable
    private ServicoEmpresa servicoEmpresa;

    @Init
    // public void init(@ContextParam(ContextType.VIEW) Component view) { // Caso precise acessar a view
    public void init() {
        String parametros = Executions.getCurrent().getParameter(SafeParams.DEFAULT_DATA_PARAM);
        if (parametros != null) {
            Long idEmpresa = new SafeParams(parametros).getLong("idEmpresa");
            this.empresa = servicoEmpresa.buscar(idEmpresa);
        }
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

}
