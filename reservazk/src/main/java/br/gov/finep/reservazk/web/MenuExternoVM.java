/*
 * utf-8
 */
package br.gov.finep.reservazk.web;

import java.io.Serializable;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.zk.ui.select.annotation.VariableResolver;

import br.gov.finep.Ambiente;
import br.gov.finep.authfinep.security.LoginUtil;
import br.gov.finep.seguranca.SegurancaUtils;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class MenuExternoVM implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String NOME_APLICACAO = "reservazk", //
            AUTHFINEP = "authfinep", //
            URL_EXTERNO = "/externo", //
            URL_EXTERNO_PRIVADO = URL_EXTERNO + "/privado", //
            URL_EXTERNO_PUBLICO = URL_EXTERNO + "/publico"; //

    @Init
    public void init() {
    }
    
    private static String getUrlAplicacao() {
        return Ambiente.uri(NOME_APLICACAO).toTemplate();
    }

    public static String urlAutenticar() {
        return LoginUtil.VIEW_LOGIN_EXTERNO + "?from=" + getUrlAplicacao() + URL_EXTERNO;
    }

    @Command
    public void logout() {
        SegurancaUtils.logout(URL_EXTERNO);
     }

    public Boolean isUsuarioIntranet() {
        return LoginUtil.isUsuarioInterno() && this.isAutenticado();
    }

    public Boolean isUsuarioExterno() {
        return LoginUtil.isUsuarioExterno() && this.isAutenticado();
    }

    public Boolean isAutenticado() {
        return LoginUtil.isAutenticado();
    }

    public static String urlIndexExterno() {
        return URL_EXTERNO;
    }

    public String getInfoUsuario() {
        return "Usuário: " + LoginUtil.getObjetoUsuarioCorrente().getNome();
    }

}