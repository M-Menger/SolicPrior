package org.example.services;

import java.io.*;
import java.util.Map;

public class CsvReader {

    private static final String CSV_SEPARATOR = ";";

    public static void lerCSV(File CSV_FILE, Map<String, Registro> registros, String searchCliente) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(CSV_FILE), "ISO-8859-1"))) {
            String linha;
            boolean cabecalho = true;
            while ((linha = br.readLine()) != null) {
                if(cabecalho){
                    cabecalho = false;
                    continue;
                }
                String[] dados = linha.split(CSV_SEPARATOR);
                String atendimento = dados[0];
                String placa = dados[2];
                String cliente = dados[5];
                String status = dados[11];
                String tipo = dados[10];
                String dataEnt = dados[15];
                String dataOrc = dados[16];

                if (cliente.toLowerCase().contains(searchCliente.toLowerCase())) {
                    if(status.equalsIgnoreCase("Em Elaboração Orçamento") || status.equalsIgnoreCase( "Aguardando Aprovação")) {
                            Registro registro = registros.get(atendimento);
                            registro = new Registro(atendimento, placa, cliente, status, tipo, dataEnt, dataOrc);
                            registros.put(atendimento, registro);

                            if(dataEnt != null && !dataEnt.trim().isEmpty()) {
                                registro.setSLA();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
