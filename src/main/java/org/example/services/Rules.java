package org.example.services;

import java.util.Map;

public class Rules {

    public static Map<String, Long> loadRegras() {
        return Map.of(
                "PREVENTIVA", 3L,
                "CORRETIVA", 5L,
                "SINISTRO", 24L,
                "PRÉ-APROVADO", 3L,
                "", 5L
        );
    }

    public static void setRules(Map<String, Registro> registros) {

        Map<String, Long> regras = loadRegras();

        for (Registro registro : registros.values()) {
            String tipo = registro.getTipo();
            String sla = registro.getSLA();

            if (sla != null && !sla.trim().isEmpty() && !sla.equals("N/A")) {
                try {
                    long slaCalculado = Long.parseLong(sla);
                    long slaRegra = regras.getOrDefault(tipo, 5L);

                    if (slaCalculado >= slaRegra) {
                        registro.setExport(true);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao processar SLA: " + sla + " para o registro " + registro.getAtendimento());
                }
            }
        }
    }
}