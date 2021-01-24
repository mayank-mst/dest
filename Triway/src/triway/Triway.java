/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package triway;

import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author DELL
 */
public class Triway {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        try {
              UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (InstantiationException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(splash.class.getName()).log(Level.SEVERE, null, ex);
        }
        new Triway();
        
        splash Splash = new splash();
        Splash.setVisible(true);
        Login login = new Login();
        try{
            Thread.sleep(5000);
            //Splash.loadingnum.setText(Integer.toString(i)+"%");
            //Splash.loadingbar.setValue(i);

            login.setVisible(true);
            Splash.setVisible(false);
              
        }catch(Exception e){}
    }
}
