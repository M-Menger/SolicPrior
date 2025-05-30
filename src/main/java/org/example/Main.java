package org.example;

import org.example.services.FileSelector;
import org.example.services.Registro;
import org.example.services.CsvReader;
import org.example.services.Rules;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String userHome = System.getProperty("user.home");
        String CSV_FILE_OUT = userHome + File.separator + "Área de trabalho" + File.separator + "Relatorio.csv"; //No linux
//        String CSV_FILE_OUT = userHome + File.separator + "Desktop" + File.separator + "Relatorio.csv"; //No Windows

        System.out.println("Selecione o arquivo! ");
        File CSV_FILE = FileSelector.selectFile();

        System.out.println("Digite o nome do cliente conforme planilha: ");
        String cliente = scanner.nextLine();

        Map<String, Registro> registros = new HashMap<>();
        System.out.println("Carregando arquivo...");
        CsvReader.lerCSV(CSV_FILE, registros, cliente);

        System.out.println("Verificando regras...");
        Rules.setRules(registros);

        System.out.println("Exportando arquivo...");
        Registro.salvarCSV(CSV_FILE_OUT, registros);
    }
}
