package org.example.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.Map;

public class Registro {
    private String atendimento;
    private String placa;
    private String cliente;
    private String status;
    private String tipo;
    private String dataEnt;
    private String dataOrc;
    private static final String CSV_SEPARATOR = ";";
    private String SLA = "";
    private boolean export = false;

    public Registro(String atendimento, String placa, String cliente, String status, String tipo,String dataEnt, String dataOrc) {
        this.atendimento = atendimento;
        this.placa = placa;
        this.cliente = cliente;
        this.status = status;
        this.tipo = tipo;
        this.dataEnt = dataEnt;
        this.dataOrc = dataOrc;
    }

    public void setSLA(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        LocalDateTime now = LocalDateTime.now();
        if (status.equalsIgnoreCase("Em Elaboração Orçamento")) {
            if (dataEnt != null && !dataEnt.trim().isEmpty()) {
                try {
                    LocalDateTime receb = LocalDateTime.parse(dataEnt, formatter);
                    Long tempDec = ChronoUnit.HOURS.between(receb, now);
                    this.SLA = tempDec.toString();
                } catch (DateTimeParseException e) {
                    this.SLA = "N/A";
                    System.err.println("Erro ao processar dataOrc: " + dataEnt);
                }
            } else {
                this.SLA = "N/A";
            }
        } else {
            if (dataOrc != null && !dataOrc.trim().isEmpty()) {
                try {
                    LocalDateTime receb = LocalDateTime.parse(dataOrc, formatter);
                    Long tempDec = ChronoUnit.HOURS.between(receb, now);
                    this.SLA = tempDec.toString();
                } catch (DateTimeParseException e) {
                    this.SLA = "N/A";
                    System.err.println("Erro ao processar dataOrc: " + dataOrc);
                }
            } else {
                this.SLA = "N/A";
            }
        }
    }

    public String toCsv() {
        return String.join(CSV_SEPARATOR, atendimento, placa,cliente, status, tipo, dataEnt, dataOrc != null ? dataOrc : "", SLA);
    }

    public  boolean export() {
        return export;
    }

    public void setExport (boolean export) {
        this.export = export;
    }

    public String getAtendimento() {
        return atendimento;
    }

    public String getTipo() {
        return tipo;
    }

    public String getSLA() {
        return SLA;
    }

    public static void salvarCSV(String arquivo, Map<String, Registro> registros) {
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(arquivo), StandardCharsets.ISO_8859_1)) {
            writer.write("Atendimento;Placa;Cliente;Status;Tipo;Data_Entrada;Data_Orcamento; SLA\n");
            for (Registro registro : registros.values()){
                if (registro.export()) {
                    writer.write(registro.toCsv() + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
