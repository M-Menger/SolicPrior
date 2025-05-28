package org.example.services;

import javax.swing.*;
import java.io.File;

public class FileSelector {

    public static File selectFile() {
        JFileChooser fileChooser = new JFileChooser();

        int returnValue = fileChooser.showOpenDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        } else {
            return null;
        }
    }
}
