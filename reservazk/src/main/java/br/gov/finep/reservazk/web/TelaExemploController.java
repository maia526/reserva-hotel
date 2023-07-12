/*
 * utf-8
 */
package br.gov.finep.reservazk.web;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Caption;
import org.zkoss.zul.Column;
import org.zkoss.zul.Columns;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Space;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Vlayout;

import br.com.caelum.stella.type.Estado;
import br.gov.finep.reservazk.modelo.Empresa;
import br.gov.finep.reservazk.servico.ServicoEmpresa;
import br.gov.finep.safeparams.SafeParams;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TelaExemploController extends SelectorComposer<Vlayout> implements Serializable {

    private static final long serialVersionUID = 1L;

    private Estado ufSelecionada;

    @WireVariable
    private ServicoEmpresa servicoEmpresa;

    @Wire
    private Vbox conteudo;

    @Wire
    private Button btnVisualizar;

    @Wire
    private Grid gridResultado;

    private ListModelList<Empresa> gridModel = new ListModelList<Empresa>();

    @Override
    public void doBeforeComposeChildren(Vlayout comp) throws Exception {
        // É executado antes do init()
    }

    @Override
    public void doAfterCompose(Vlayout root) throws Exception {
        // Equivalente ao init()
        super.doAfterCompose(root);

        // Filtro
        Groupbox groupbox = criarFiltro();

        // Grid Resultado
        this.gridResultado = criarGridResultado();

        // Conteudo
        this.conteudo.appendChild(groupbox);
        this.conteudo.appendChild(new Space());
        this.conteudo.appendChild(this.gridResultado);
    }

    private Groupbox criarFiltro() {
        Groupbox groupbox = new Groupbox();
        groupbox.setStyle("max-width:500px");
        groupbox.appendChild(new Caption("Filtros"));

        Grid gridFiltro = new Grid();
        gridFiltro.setStyle("border:0; max-width:500px");
        gridFiltro.setOddRowSclass(null);
        Columns columns = new Columns();
        Column column1 = new Column();
        column1.setHflex("1");
        Column column2 = new Column();
        column2.setHflex("2");
        Column column3 = new Column();
        column3.setHflex("1");

        columns.appendChild(column1);
        columns.appendChild(column2);
        columns.appendChild(column3);

        gridFiltro.appendChild(columns);

        Rows rows = new Rows();
        Row row = new Row();
        row.appendChild(new Label("UF:"));

        Combobox comboUF = new Combobox();
        comboUF.setModel(new ListModelList<>(this.listarUFs()));
        comboUF.addEventListener(Events.ON_CHANGE, new EventListener<Event>() {
            @Override
            public void onEvent(Event event) throws Exception {
                Comboitem selectedItem = ((Combobox) event.getTarget()).getSelectedItem();
                ufSelecionada = selectedItem != null ? (Estado) selectedItem.getValue() : null;
            }
        });
        row.appendChild(comboUF);

        this.btnVisualizar = new Button("Buscar");
        this.btnVisualizar.setId("btnBuscar");
        this.btnVisualizar.setIconSclass("z-icon-search");
        this.btnVisualizar.setSclass("btn-primary btn-sm");
        this.btnVisualizar.setMold("bs");
        row.appendChild(this.btnVisualizar);
        rows.appendChild(row);
        gridFiltro.appendChild(rows);
        groupbox.appendChild(gridFiltro);
        return groupbox;
    }

    private Grid criarGridResultado() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Grid gridResultado = new Grid();
        gridResultado.setId("gridResultado");
        gridResultado.setHflex("true");
        gridResultado.setEmptyMessage("Nenhuma empresa cadastrada");
        gridResultado.setModel(this.gridModel);
        Columns columns = new Columns();

        Column columnNome = new Column("Nome");
        columnNome.setAlign("center");
        columnNome.setSort("auto(nome)");
        columns.appendChild(columnNome);

        Column columnCidade = new Column("Cidade");
        columnCidade.setAlign("center");
        columnCidade.setSort("auto(cidade)");
        columns.appendChild(columnCidade);

        Column columnUF = new Column("UF");
        columnUF.setAlign("center");
        columnUF.setSort("auto(uf)");
        columns.appendChild(columnUF);

        Column columnFaturamento = new Column("Faturamento");
        columnFaturamento.setAlign("center");
        columnFaturamento.setSort("auto(faturamento)");
        columns.appendChild(columnFaturamento);

        Column columnDataCadastro = new Column("Data Cadastro");
        columnDataCadastro.setAlign("center");
        columnDataCadastro.setSort("auto(dataCadastro)");
        columns.appendChild(columnDataCadastro);

        Column columnBotao = new Column();
        columnBotao.setAlign("center");
        columns.appendChild(columnBotao);

        gridResultado.appendChild(columns);

        gridResultado.setRowRenderer(new RowRenderer<Empresa>() {
            @Override
            public void render(Row row, final Empresa data, int index) throws Exception {
                row.appendChild(new Label(data.getNome()));
                row.appendChild(new Label(data.getCidade()));
                row.appendChild(new Label(data.getUf().toString()));
                row.appendChild(new Label(data.getFaturamento().toString()));
                row.appendChild(new Label(data.getDataCadastro().toString()));

                btnVisualizar = new Button("Visualizar");
                btnVisualizar.setIconSclass("z-icon-folder-open");
                btnVisualizar.setSclass("btn-primary btn-sm");
                btnVisualizar.setMold("bs");
                btnVisualizar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
                    @Override
                    public void onEvent(Event event) throws Exception {
                        Executions.getCurrent().sendRedirect(MenuVM.urlDetalheEmpresaMVVM() + "?" + new SafeParams("idEmpresa", data.getId()));
                    }
                });

                row.appendChild(btnVisualizar);
            }
        });

        return gridResultado;
    }

    @Listen("onClick = #btnBuscar")
    @NotifyChange("gridResultado")
    public void onBtnBuscarClick() {
        this.gridModel.clear();
        this.gridModel.addAll(this.servicoEmpresa.buscar(this.ufSelecionada));
    }

    public List<Estado> listarUFs() {
        return Arrays.asList(Estado.values());
    }

    public Estado getUfSelecionada() {
        return ufSelecionada;
    }

    public void setUfSelecionada(Estado ufSelecionada) {
        this.ufSelecionada = ufSelecionada;
    }

    public Vbox getConteudo() {
        return conteudo;
    }

    public void setConteudo(Vbox conteudo) {
        this.conteudo = conteudo;
    }

    public Grid getGridResultado() {
        return gridResultado;
    }

    public void setGridResultado(Grid gridResultado) {
        this.gridResultado = gridResultado;
    }

    public ListModelList<Empresa> getGridModel() {
        return gridModel;
    }

    public void setGridModel(ListModelList<Empresa> gridModel) {
        this.gridModel = gridModel;
    }

}
