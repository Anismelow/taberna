package com.ipartek.taberna.controladores;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ipartek.taberna.servicio.HistoricoServicio;

@Controller
public class HistoricoControlador {

    @Autowired
    private HistoricoServicio historicoServ;

    @GetMapping("/resumenVentas")
    public String resumenVentas(Model model,
            @RequestParam(name = "periodo", required = false, defaultValue = "mes") String periodo) {

        Map<String, BigDecimal> ventas;

        // elegir el cálculo según el periodo
        if (periodo.equals("trimestre")) {
            ventas = historicoServ.ventasPorTrimestre();
        } else if (periodo.equals("semestre")) {
            ventas = historicoServ.ventasPorSemestre();
        } else {
            ventas = historicoServ.ventasPorMes();
        }

        model.addAttribute("ventas", ventas);
        model.addAttribute("periodoActivo", periodo);
        return "resumen";
    }
}
