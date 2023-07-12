/*
 * utf-8
 */
package br.gov.finep.reservazk.web;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Window;

import br.gov.finep.Ambiente;
import br.gov.finep.authfinep.security.LoginUtil;
import br.gov.finep.authfinep.security.UsuarioUtil;
import br.gov.finep.authfinep.Usuario;
import br.gov.finep.seguranca.SegurancaUtils;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class MenuVM implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String NOME_APLICACAO = "reservazk";
	private static final String URL_INTRANET = "/intranet";
	private static final String URL_EXTERNO = "/externo";

	private String loginPersonificado;

	@Wire
	private Window windowPersonificar;

	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
	}

	private static String getUrlAplicacao() {
		return Ambiente.uri(NOME_APLICACAO).toTemplate();
	}

	public static String urlAutenticar() {
		return LoginUtil.VIEW_LOGIN_INTERNO + "?from=" + getUrlAplicacao() + URL_INTRANET;
	}

	@Command
	public void logout() {
		SegurancaUtils.logout();
	}

	public static void redirectIndexIntranet() {
		Executions.sendRedirect(URL_INTRANET);
	}

	public static String urlIndexIntranet() {
		return URL_INTRANET;
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

	public String getInfoUsuario() {
		Usuario usuario = LoginUtil.getObjetoUsuarioCorrente();
		return "Usuário: " + usuario.getNome() + " - Departamento: " + usuario.getSiglaUO();
	}

	public Boolean isURLExterna() {
		String requestPath = Executions.getCurrent().getDesktop().getRequestPath();
		return requestPath.contains("externo");
	}

	public static String urlTelaHome() {
		return getUrlAplicacao() + URL_INTRANET + "/privado/telaHome.zul";
	}
	
	public static String urlTelaReserva() {
		return getUrlAplicacao() + URL_INTRANET + "/privado/telaReserva.zul";
	}
	
	public static String urlTelaFormularioReserva() {
		return getUrlAplicacao() + URL_INTRANET + "/privado/telaFormularioReserva.zul";
	}
	
	public static String urlTelaQuartos() {
		return getUrlAplicacao() + URL_INTRANET + "/privado/telaQuartos.zul";
	}
	
	public static String urlTelaDetalhesReserva() {
		return getUrlAplicacao() + URL_INTRANET + "/privado/telaDetalhesReserva.zul";
	}
	
	public static String urlTelaMVVM() {
		return getUrlAplicacao() + URL_INTRANET + "/privado/telaExemploMVVM.zul";
	}

	public static String urlDetalheEmpresaMVVM() {
		return getUrlAplicacao() + URL_INTRANET + "/privado/detalheEmpresaMVVM.zul";
	}

	public static String urlTelaMVC() {
		return getUrlAplicacao() + URL_INTRANET + "/privado/telaExemploMVC.zul";
	}

	@Command
	public void personificar() {
		HttpServletRequest request = (HttpServletRequest) Executions.getCurrent().getNativeRequest();
		HttpServletResponse response = (HttpServletResponse) Executions.getCurrent().getNativeResponse();
		try {
			if (!LoginUtil.isLoginDeUsuarioExterno(loginPersonificado)) {
				Executions.sendRedirect(UsuarioUtil.personificar(request, response, loginPersonificado));
			} else {
				Executions.sendRedirect(UsuarioUtil.personificar(request, response, loginPersonificado,
						getUrlAplicacao() + URL_EXTERNO));
			}
		} catch (Exception e) {
			Clients.showNotification(e.getMessage(), "error", null, "middle_center", 4000);
		}
		this.windowPersonificar.setVisible(false);
	}

	@Command
	public void abrirPersonificar() {
		loginPersonificado = "";
		windowPersonificar.doModal();
	}

	public boolean isPersonificacaoPermitida() {
		return LoginUtil.isAutenticado() && Ambiente.PRODUCAO != Ambiente.atual();
	}

	public String getLoginPersonificado() {
		return loginPersonificado;
	}

	public void setLoginPersonificado(String loginPersonificado) {
		this.loginPersonificado = loginPersonificado;
	}
}