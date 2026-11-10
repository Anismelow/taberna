package com.ipartek.taberna.servicio;

import java.math.BigDecimal;
import java.util.Map;

public interface HistoricoServicio {

    Map<String, BigDecimal> ventasPorMes();

    Map<String, BigDecimal> ventasPorTrimestre();

    Map<String, BigDecimal> ventasPorSemestre();

}
