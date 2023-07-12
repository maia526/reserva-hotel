package br.gov.finep.reservazk.repositorio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import br.com.caelum.stella.type.Estado;
import br.gov.finep.reservazk.modelo.Empresa;

@Repository
public class RepositorioEmpresa extends RepositorioBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositorioEmpresa.class);

    private List<Empresa> bd = new ArrayList<Empresa>();

    private void criarListaEmpresas() {
        bd = new ArrayList<Empresa>();
        LocalDate dataCadastro = LocalDate.now();
        bd.add(new Empresa(1L, "Empresa X", "Rio de Janeiro", Estado.RJ, new BigDecimal("981000"), dataCadastro));
        bd.add(new Empresa(2L, "Empresa Y", "São Paulo", Estado.SP, new BigDecimal("10000"), dataCadastro));
        bd.add(new Empresa(3L, "Empresa Z", "Curitiba", Estado.PA, new BigDecimal("2000"), dataCadastro));
        bd.add(new Empresa(4L, "Empresa A", "Maceió", Estado.AL, new BigDecimal("500"), dataCadastro));
        bd.add(new Empresa(5L, "Empresa B", "Rio de Janeiro", Estado.RJ, new BigDecimal("1000236"), dataCadastro));
        bd.add(new Empresa(6L, "Empresa C", "São Paulo", Estado.SP, new BigDecimal("1000123"), dataCadastro));
        bd.add(new Empresa(7L, "Empresa D", "Rio de Janeiro", Estado.RJ, new BigDecimal("500000"), dataCadastro));
        bd.add(new Empresa(8L, "Empresa E", "Porto Alegre", Estado.RS, new BigDecimal("12300"), dataCadastro));
        bd.add(new Empresa(9L, "Empresa F", "Aracaju", Estado.SE, new BigDecimal("125634"), dataCadastro));
        bd.add(new Empresa(10L, "Empresa G", "Rio de Janeiro", Estado.RJ, new BigDecimal("800"), dataCadastro));
    }

    public List<Empresa> list() {
        this.criarListaEmpresas();
        return this.bd;
    }

    public Empresa buscar(Long id) {
        try {
            for (Empresa e : this.bd) {
                if (e.getId() == id) {
                    return e;
                }
            }
            return null;

        } catch (Exception e) {
            if (e instanceof NoResultException == false) {
                LOGGER.error("Erro ao buscar pessoa física: " + e.getMessage());
            }
            return null;
        }
    }

}
