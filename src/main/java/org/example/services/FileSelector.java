package org.example.services;

import javax.swing.*;
import java.io.File;

public class FileSelector {

    public static File selectFile() {
        JFileChooser fileChooser = new JFileChooser();

        String userHome = System.getProperty("user.home");
        File downloadsDir = new File(userHome, "Downloads");

        if (downloadsDir.exists() && downloadsDir.isDirectory()){
            fileChooser.setCurrentDirectory(downloadsDir);
        } else {
            fileChooser.setCurrentDirectory(new File(userHome));
        }

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            return null;
        }
    }
}
