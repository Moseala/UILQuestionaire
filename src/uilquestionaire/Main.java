package uilquestionaire;

import java.io.*;

/**
 * This program has been run on two computers:
 * Desktop: Windows 7 Home Premium (c) Microsoft Corporation, AMD Athlon(tm) 64 X2 Dual Core 4600+ 2.41 GHz, 3GB RAM, 32-bit OS. (Tested through NetBeans 6.8 IDE)
 * Laptop: Windows 7 Hope Premium (c) Microsoft Corporation, Intel(R) Atom(TM) CPU N280 1.67 GHz, 1GB RAM, 32-bit OS. (Tested through NetBeans 6.9.1 IDE)
 * @author Erik Clary
 */
public class Main {

    /**
     * C:\\Users\\Erik Clary\\Desktop\\
     * @param args the command line arguments
     */

    public final File FILE = new File(getClass().getClassLoader().getResource("uilquestionaire/Topics.dat").toString());
    public final File RAF = new File(getClass().getClassLoader().getResource("uilquestionaire/RandomAccesFile.dat").toString());

    public static void main(String[] args) throws FileNotFoundException, IOException {
        UI Ui = new UI();
        Ui.displayMenu();

        
    }

}
