package br.gov.finep.reservazk.modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class PeriodoReserva {
	LocalDate inicio;
	LocalDate fim;
	
	public PeriodoReserva(LocalDate inicio, LocalDate fim) {
		super();
		this.inicio = inicio;
		this.fim = fim;
	}

	public PeriodoReserva(Map<String, Object> data) {
		this.inicio = populaLocalDate((Map<String, Object>)data.get("inicio"));
		this.fim = populaLocalDate((Map<String, Object>)data.get("fim"));
	}
	
	public LocalDate populaLocalDate(Map<String, Object> partesData) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		double dia = (double) partesData.get("day");
		double mes = (double)partesData.get("month");
		double ano = (double)partesData.get("year");
		String dataString = String.format("%02d", (int)dia) + "/" + String.format("%02d", (int)mes) + "/" + (int)ano;
		LocalDate data = LocalDate.parse(dataString, formatter);
		
		return data;
	}

	public LocalDate getInicio() {
		return inicio;
	}

	public void setInicio(LocalDate inicio) {
		this.inicio = inicio;
	}

	public LocalDate getFim() {
		return fim;
	}

	public void setFim(LocalDate fim) {
		this.fim = fim;
	}

	@Override
	public String toString() {
		return "Check-in: " + inicio + " Check-out: " + fim;
	}
	
	
}
