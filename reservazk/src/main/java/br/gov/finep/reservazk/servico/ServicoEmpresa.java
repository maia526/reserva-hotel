package br.gov.finep.reservazk.servico;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.caelum.stella.type.Estado;
import br.gov.finep.reservazk.modelo.Empresa;
import br.gov.finep.reservazk.repositorio.RepositorioEmpresa;

@Service
public class ServicoEmpresa {

    @Autowired
    private RepositorioEmpresa repositorioEmpresa;

    @SuppressWarnings("unused")
    private static final Logger LOGGER = LoggerFactory.getLogger(ServicoEmpresa.class);

    public List<Empresa> buscar() {
        return repositorioEmpresa.list();
    }

    public Empresa buscar(Long id) {
        return repositorioEmpresa.buscar(id);
    }

    public List<Empresa> buscar(Estado uf) {
        List<Empresa> empresas = new ArrayList<Empresa>();

        if (StringUtils.isEmpty(uf) == false) {
            for (Empresa empresa : this.buscar()) {
                if (empresa.getUf().equals(uf)) {
                    empresas.add(empresa);
                }
            }
        }else{
            empresas = this.buscar();
        }

        return empresas;
    }

}
