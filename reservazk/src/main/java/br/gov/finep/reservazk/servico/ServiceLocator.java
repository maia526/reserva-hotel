package br.gov.finep.reservazk.servico;

public class ServiceLocator {

    // Declare os serviços conforme o modelo abaixo
    private static ServicoEmpresa servicoEmpresa;

    public static ServicoEmpresa getServicoEmpresa() {
        return servicoEmpresa;
    }

    public static void setServicoEmpresa(ServicoEmpresa servicoEmpresa) {
        ServiceLocator.servicoEmpresa = servicoEmpresa;
    }
}
