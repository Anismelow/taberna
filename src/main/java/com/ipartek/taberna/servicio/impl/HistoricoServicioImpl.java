package com.ipartek.taberna.servicio.impl;

import java.math.BigDecimal;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.ipartek.taberna.modelo.Ticket;
import com.ipartek.taberna.servicio.HistoricoServicio;
import com.ipartek.taberna.servicio.TicketServicio;

@Service
public class HistoricoServicioImpl implements HistoricoServicio {

	@Autowired
	private TicketServicio ticketServ;

	@Override
	public Map<String, BigDecimal> ventasPorMes() {
		Map<String, BigDecimal> mapa = new TreeMap<>();
		for (Ticket ticket : ticketServ.obtenerTodos()) {
			String mes = ticket.getFecha().getYear() + "-" + ticket.getFecha().getMonthValue();
			BigDecimal acumulado = mapa.getOrDefault(mes, BigDecimal.ZERO);
			mapa.put(mes, acumulado.add(ticket.getTotal()));
		}
		return mapa;
	}

	@Override
	public Map<String, BigDecimal> ventasPorTrimestre() {
		Map<String, BigDecimal> mapa = new TreeMap<>();
		for (Ticket ticket : ticketServ.obtenerTodos()) {
			int mes = ticket.getFecha().getMonthValue();
			int trimestre = (mes - 1) / 3 + 1;
			String clave = ticket.getFecha().getYear() + "-T" + trimestre;

			BigDecimal acumulado = mapa.getOrDefault(clave, BigDecimal.ZERO);
			mapa.put(clave, acumulado.add(ticket.getTotal()));
		}
		return mapa;
	}

	@Override
	public Map<String, BigDecimal> ventasPorSemestre() {
		Map<String, BigDecimal> mapa = new  TreeMap<>();
		for (Ticket ticket : ticketServ.obtenerTodos()){
			int mes = ticket.getFecha().getMonthValue();
			int semestre = (mes - 1) / 6 + 1 ;
			String clave = ticket.getFecha().getYear() + "-S" + semestre;

			BigDecimal acumulado = mapa.getOrDefault(clave, BigDecimal.ZERO );
			mapa.put(clave, acumulado.add(ticket.getTotal()));
		}
		return mapa;
	}

}
