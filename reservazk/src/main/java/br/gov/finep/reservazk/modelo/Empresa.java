package br.gov.finep.reservazk.modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import br.com.caelum.stella.type.Estado;

public class Empresa implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private String cidade;
    private Estado uf;
    private BigDecimal faturamento;
    private LocalDate dataCadastro;
    
    Empresa() {
        // Construtor padrão
    }
    
    public Empresa(Long id, String nome, String cidade, Estado uf, BigDecimal faturamento, LocalDate dataCadastro) {
        super();
        this.id = id;
        this.nome = nome;
        this.cidade = cidade;
        this.uf = uf;
        this.faturamento = faturamento;
        this.dataCadastro = dataCadastro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public Estado getUf() {
        return uf;
    }

    public void setUf(Estado uf) {
        this.uf = uf;
    }

    public BigDecimal getFaturamento() {
        return faturamento;
    }

    public void setFaturamento(BigDecimal faturamento) {
        this.faturamento = faturamento;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}
