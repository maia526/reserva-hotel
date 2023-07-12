package br.gov.finep.reservazk.seguranca;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.A;
import org.zkoss.zul.Window;

import br.gov.finep.authfinep.security.URLsUtil;
 
public class AvisoSessaoExpiradaController extends SelectorComposer<Window>  {
    
    private static final long serialVersionUID = 1L;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(AvisoSessaoExpiradaController.class);

    @Wire
    private A redirecionarLink;
    
    private HttpServletRequest request;
    private HttpServletResponse response;

    private String requisicaoOriginal;
    
    private void redirecionar(String url) {
        
        try {
            response.sendRedirect(response.encodeRedirectURL(url));
        } catch (IOException e) {
            LOGGER.error("Erro ao redirecionar para a URL " + url + ".", e);
            e.printStackTrace();
        }
    }
    
    @Override
    public void doAfterCompose(Window comp) throws Exception {
        super.doAfterCompose(comp);
        Execution exec = Executions.getCurrent();
        
        response = (HttpServletResponse)exec.getNativeResponse();
        request = (HttpServletRequest)exec.getNativeRequest();
        
        String referer = request.getHeader("referer");
        
        String urlAplicacao = getUrlAplicacao();
        
        requisicaoOriginal = !StringUtils.isEmpty(referer) ? referer : urlAplicacao;
        
        if (requisicaoOriginal.contains("timeout/timeout")) {
            requisicaoOriginal = urlAplicacao;
        }
        
        if (requisicaoOriginal.compareTo(getUrlAplicacao()) != 0) {
            String caminho = requisicaoOriginal.replace(urlAplicacao, "");
            // se a sessão expirou em tela pública, não é necessário exibir notificação para recarregar a página
            if (URLsUtil.getInstance().isAutorizado(caminho)) {
                redirecionar(requisicaoOriginal);
                return;
            }
        }

        redirecionarLink.setHref(requisicaoOriginal);
        
    }
    
    public String getHost() {
        String url = request.getRequestURL().toString().replace(request.getRequestURI().substring(0), "");
        return url;
    }
    
    public String getUrlAplicacao() {
        String url = getHost() + request.getContextPath();
        return url;
    }
}