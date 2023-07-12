package br.gov.finep.reservazk.web;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Executions;
import br.gov.finep.reservazk.modelo.Hotel;
import br.gov.finep.reservazk.modelo.PeriodoReserva;
import br.gov.finep.reservazk.modelo.Quarto;
import br.gov.finep.reservazk.modelo.Reserva;
import br.gov.finep.safeparams.SafeParams;

public class TelaFormularioReservaVM {
	
	private Hotel hotel;//passado como parâmetro
    private List<Quarto> quartosSelecionados;//passado como parâmetro
    private String nomeCliente;
    private String emailCliente;
    private String telefoneCliente;
    private LocalDate checkin;
    private LocalDate checkout;
   
	@Init
    public void init(){
		hotel = Hotel.getHotel();
    	String parametros = Executions.getCurrent().getParameter(SafeParams.DEFAULT_DATA_PARAM);
    	if (parametros != null) {
    		this.quartosSelecionados = popularQuartosSelecionados(new SafeParams(parametros).get("selecionados"));
        }
    }
	
    public List<Quarto> popularQuartosSelecionados(List<Object> listaQuartos){
    	List<Quarto> listaPopulada = new ArrayList<>();
    	for (Object detalhesQuarto : listaQuartos) {
    		Quarto quarto = new Quarto((Map<String, Object>) detalhesQuarto);
    		listaPopulada.add(quarto);
    	}
    	
    	return listaPopulada;
    }
    
    public static String converterStringEmTitleCase(String string){
        String[] arr = string.split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < arr.length; i++) {
            sb.append(Character.toUpperCase(arr[i].charAt(0)))
                .append(arr[i].substring(1)).append(" ");
        }          
        return sb.toString().trim();
    }
    
    @Command
    @NotifyChange({"hotel", "checkin", "checkout", "nomeCliente", "emailCliente", "telefoneCliente"})
	public void concluirReserva() {
    	//TODO: não está apagando nos campos
    	if (verificarData() && verificarEmail() && verificarTelefone()) {
    		this.nomeCliente = converterStringEmTitleCase(nomeCliente.toLowerCase());
        	hotel.adicionarReserva(nomeCliente, telefoneCliente, emailCliente, quartosSelecionados, checkin, checkout);	
    		Executions.getCurrent().sendRedirect(MenuVM.urlTelaHome());
    	}
    	
	}
    @NotifyChange({"checkin", "checkout"})
    public boolean verificarData() {
    	if (checkin.isAfter(checkout)) {
    		this.checkin = null;
    		this.checkout = null;
    		return false;
    	}
    	if (!verificarDatasReserva()) {
    		this.checkin = null;
    		this.checkout = null;
    		return false;
    	}
    	return true;
    }
    
    @NotifyChange("telefoneCliente")
    public boolean verificarTelefone() {
    	if (testar(this.telefoneCliente, "\\d{9,11}") == 0) {
    		this.telefoneCliente = "";
    		return false;
    	}
    	return true;
    }
    
    @NotifyChange("emailCliente")
    public boolean verificarEmail() {
    	if (testar(this.emailCliente, "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") == 0) {
    		this.emailCliente = "";
    		return false;
    	}
    	return true;
    		
    }
    
    public int testar(String nome, String regex) {
    	Pattern pattern = Pattern.compile(regex);
    	Matcher matcher = pattern.matcher(nome);
    	int matches = 0;
        while (matcher.find()) {
            matches++;
        }
        return matches;
    }
   
    public boolean verificarDatasReserva() {
    	boolean disponivel = true;
    	for (Quarto quarto: this.quartosSelecionados) {
    		List<PeriodoReserva> listaDatas = quarto.getDatasReservas();
    		for (PeriodoReserva datas : listaDatas) {
    			disponivel = hotel.verificarDisponibilidade(datas, checkin, checkout);
    		}
    	}
    	return disponivel;
    }
   
    
	public List<Quarto> getQuartosSelecionados() {
		return quartosSelecionados;
	}
	public void setQuartosSelecionados(List<Quarto> quartosSelecionados) {
		this.quartosSelecionados = quartosSelecionados;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getEmailCliente() {
		return emailCliente;
	}
	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}
	public String getTelefoneCliente() {
		return telefoneCliente;
	}
	public void setTelefoneCliente(String telefoneCliente) {
		this.telefoneCliente = telefoneCliente;
	}
	public LocalDate getCheckin() {
		return checkin;
	}
	public void setCheckin(LocalDate checkin) {
		this.checkin = checkin;
	}
	public LocalDate getCheckout() {
		return checkout;
	}
	public void setCheckout(LocalDate checkout) {
		this.checkout = checkout;
	}
  
}
