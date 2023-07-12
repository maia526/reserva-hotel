package br.gov.finep.reservazk.web;

import java.util.Map;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.util.Initiator;
import org.zkoss.zk.ui.util.InitiatorExt;

import br.gov.finep.seguranca.SegurancaUtils;

public class RedirectPrivado implements Initiator, InitiatorExt {

    public void doInit(Page page, Map<String, Object> args) throws Exception {
        if (SegurancaUtils.isAutenticado()) {
            MenuVM.redirectIndexIntranet();
        }
    }

    public boolean doCatch(Throwable ex) throws Exception {
        return false;
    }

    public void doFinally() throws Exception {
    }

    public void doAfterCompose(Page page, Component[] comps) throws Exception {
    }
}