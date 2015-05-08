package com.huidonline.coupon.ui;/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */



import com.huidonline.coupon.data.Order;
import com.huidonline.coupon.excel.ExcelFileReader;
import com.huidonline.coupon.excel.ExcelFileWriter;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Set;

/*
 * com.huidonline.coupon.ui.HuidOnlineExport.java uses these files:
 *   images/Open16.gif
 *   images/Save16.gif
 */
public class HuidOnlineExport extends JPanel
                             implements ActionListener {
    static private final String newline = "\n";
    JButton openButton, saveButton, andereMerk;
    private File selectedFile;
    private  Set<Order>  orders ;
    JTextArea log;
    JFileChooser fc;

    public HuidOnlineExport() {
        super(new BorderLayout());

        //Create the log first, because the action listeners
        //need to refer to it.
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);

        //Create a file chooser
        fc = new JFileChooser();

        File currentDir = new File(System.getProperty("user.home") + System.getProperty("file.separator") + "Excel");
        System.out.println(currentDir.getAbsolutePath());
        fc.setCurrentDirectory
                (currentDir);
        FileFilter allFilter = new FileTypeFilter(".", "Alle bestanden");

        FileFilter xlsFilter = new FileTypeFilter(".xls", "MS Excel");
        FileFilter xlsmFilter = new FileTypeFilter(".xlsm", "MS Excel with macro");
        FileFilter xlsxFilter = new FileTypeFilter(".xlsx", "MS Excel new");


        fc.addChoosableFileFilter(allFilter);
        fc.addChoosableFileFilter(xlsFilter);
        fc.addChoosableFileFilter(xlsmFilter);
        fc.addChoosableFileFilter(xlsxFilter);
        openButton = new JButton("Open Excel...",
                                 createImageIcon("images/Open16.gif"));
        openButton.addActionListener(this);

        saveButton = new JButton("Export for Vichy ....",
                                 createImageIcon("images/Save16.gif"));
        saveButton.addActionListener(this);

        //Create the save button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        andereMerk = new JButton("Export for Andere merk ....",
                createImageIcon("images/Save16.gif"));
        andereMerk.addActionListener(this);
        //For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(andereMerk);

        //Add the buttons and the log to this panel.
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {

        //Handle open button action.
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(HuidOnlineExport.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                selectedFile = fc.getSelectedFile();
                //This is where a real application would open the file.
                log.append("Bestand : " + selectedFile.getName() + " geopend." + newline);


                orders= ExcelFileReader.readExecelFile(selectedFile);
                log.append("Er zijn in totaal "+orders.size() +" gevonden");


            } else {
                log.append("Geannuleerd." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());

        //Handle save button action.
        } else if (e.getSource() == saveButton) {
            int returnVal = fc.showSaveDialog(HuidOnlineExport.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fc.getSelectedFile();
                //This is where a real application would save the file.
                log.append("Exporteren van: " + file.getName() + "." + newline);

                ExcelFileWriter.export(file,orders);
            } else {
                log.append("Exporteren geannuleerd." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());
        }
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = HuidOnlineExport.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Bestand geopend : " + path);
            return null;
        }
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Huidonline Export");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new HuidOnlineExport());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createAndShowGUI();
            }
        });
    }
}
