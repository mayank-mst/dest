/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package triway;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.AbstractBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.filechooser.FileSystemView;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;


/**
 *
 * @author DELL
 */
public class Home extends javax.swing.JFrame {
    boolean currupted = true;
    /**
     * Creates new form Home
     */
    public Home() {
        initComponents();
        
        DefaultTableModel historymodel = (DefaultTableModel) historyTable.getModel();
        // Clear the existing table
        int historyrows = historymodel.getRowCount();
        if (historyrows > 0) {
            for (int i = 0; i < historyrows; i++) {
                historymodel.removeRow(0);
            }
        }
        try{
            String query = "SELECT * FROM test ORDER BY Copy_Date DESC";
            //step1 load the driver class  
            Class.forName("oracle.jdbc.driver.OracleDriver"); 
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","abc");
            System.out.println("Connection Established");
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs=stmt.executeQuery(query);  
            
            while (rs.next()) {
                historymodel.addRow(new Object[]{rs.getString(10),rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)});
            }
            con.close();  

        }catch(Exception e){
            System.out.println(e);
        }
        
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        getContentPane().setBackground(Color.white);      
        jPanel1.setSize(JFrame.WIDTH,JFrame.HEIGHT);
                
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jLabel2.setSize(screenSize.width,screenSize.height/4);
        ImageIcon imageIcon1 = new ImageIcon(new ImageIcon(this.getClass().getResource("/resources/cover.jpg")).getImage().getScaledInstance(screenSize.width,screenSize.height/4, Image.SCALE_SMOOTH));
        jLabel2.setIcon(imageIcon1);
        
        JTabbedPane jTabbedPane1 = new JTabbedPane();
        JTabbedPane jTabbedPane2 = new JTabbedPane();
        jTabbedPane1.setFocusable(true);
        jTabbedPane2.setFocusable(true);
        
        Font f = new Font("Helvetica",Font.PLAIN,15);
        
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.getTableHeader().setDefaultRenderer(new HeaderColor());       
        historyTable.getTableHeader().setReorderingAllowed(false);
        historyTable.getTableHeader().setDefaultRenderer(new HeaderColor());
        
        int[] alignments = new int[] { JLabel.CENTER, JLabel.CENTER, JLabel.CENTER,JLabel.CENTER, JLabel.CENTER, JLabel.CENTER,JLabel.CENTER, JLabel.CENTER, JLabel.CENTER, JLabel.CENTER, JLabel.CENTER,JLabel.CENTER,JLabel.CENTER,JLabel.CENTER, JLabel.CENTER };
        for (int i = 0 ; i < jTable1.getColumnCount(); i++){
            jTable1.getTableHeader().getColumnModel().getColumn(i).setHeaderRenderer(new HeaderRenderer(jTable1, alignments[i]));
        }
        int[] historyAlignments = new int[] {JLabel.CENTER,JLabel.CENTER, JLabel.CENTER, JLabel.CENTER, JLabel.CENTER, JLabel.CENTER,JLabel.CENTER,JLabel.CENTER,JLabel.CENTER, JLabel.CENTER };
        for (int i = 0 ; i < historyTable.getColumnCount(); i++){
            historyTable.getTableHeader().getColumnModel().getColumn(i).setHeaderRenderer(new HeaderRenderer(historyTable, historyAlignments[i]));
        }
        
        jTable1.setFont(f);
        jTable1.setRowHeight(jTable1.getRowHeight() + 25);
        System.out.println(jTable1.getRowHeight() + 25);
        jTable1.getTableHeader().setFont(f);
        
        historyTable.setFont(f);
        historyTable.setRowHeight(historyTable.getRowHeight() + 25);
        historyTable.getTableHeader().setFont(f);
        
        Font fl = new Font("Helvetica",Font.PLAIN,12);
        jLabel3.setFont(fl);
        jLabel4.setFont(fl);
        jLabel5.setFont(fl);
        jLabel6.setFont(fl);
        jLabel7.setFont(fl);
        jLabel13.setFont(fl);
        jLabel15.setFont(fl);
        
        Font fll = new Font("Helvetica",Font.PLAIN,15);
        jLabel8.setFont(fll);
        jLabel9.setFont(fll);
        jLabel10.setFont(fll);
        jLabel11.setFont(fll);
        jLabel12.setFont(fll);
        jLabel14.setFont(fll);
        jLabel16.setFont(fll);
        
        //ImageIcon homeIcon = new ImageIcon(ClassLoader.getSystemResource("home_icon.png"));
        //jTabbedPane1.setIconAt(0, homeIcon);
        
        //ImageIcon historyIcon = new ImageIcon(this.getClass().getResource("history_icon.png"));
        //jTabbedPane1.setIconAt(1, historyIcon);
       
        
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        // Clear the existing table
        int rows = model.getRowCount();
        if (rows > 0) {
            for (int i = 0; i < rows; i++){
                model.removeRow(0);
            }
        }
              
        FileSystemView fsv = FileSystemView.getFileSystemView();
        File[] drives = File.listRoots();
        if (drives != null && drives.length > 0){
            for (File aDrive : drives){
                String s1 = FileSystemView.getFileSystemView().getSystemDisplayName(aDrive); 
                if(!(fsv.getSystemTypeDescription(aDrive)).equalsIgnoreCase("Local Disk") && !(fsv.getSystemTypeDescription(aDrive)).equalsIgnoreCase("CD Drive")){
                    File files[] = new File(aDrive.toString()).listFiles();
                    model.addRow(new Object[]{aDrive,s1,Math.round(aDrive.getTotalSpace()/1000000000.00*100.0)/100.0+" GB",((aDrive.getTotalSpace()-aDrive.getFreeSpace())/1000000>1024?Math.round((aDrive.getTotalSpace()-aDrive.getFreeSpace())/1000000000.00*100.0)/100.0+" GB":Math.round((aDrive.getTotalSpace()-aDrive.getFreeSpace())/1000000.00)+" MB"),Math.round(aDrive.getFreeSpace()/1000000000.00*100.0)/100.0+" GB",fsv.getSystemTypeDescription(aDrive),"External",files.length,"Choose","Select","Choose","Select","Download","Verify",""});
                }/*else{
                    File files[] = new File(aDrive.toString()).listFiles();
                    model.addRow(new Object[]{aDrive,s1,Math.round(aDrive.getTotalSpace()/1000000000.00)+" GB",Math.round((aDrive.getTotalSpace()-aDrive.getFreeSpace())/1000000000.00)+" GB",Math.round(aDrive.getFreeSpace()/1000000000.00)+" GB",fsv.getSystemTypeDescription(aDrive),"Local",files.length,"","","","","","",""});
                }*/
            }
        }
        
        File[] drives1 = File.listRoots();
        if (drives1 != null && drives1.length > 0){
            for (File aDrive : drives1){
                File files = new File(aDrive.toString());
                //System.out.println(Files.isReadable(files.toPath()));
                if(Files.isReadable(files.toPath())){
                    currupted = false;
                    driveCurrupted(jTable1, jTable1.getColumn("Drive").getModelIndex());
                }
                else{
                    currupted = true;
                } 
            }
        }
        changeTableNotCorrupted(jTable1);
        changeTableNotCorrupted(historyTable);
        changeTable(jTable1, jTable1.getColumn("Action").getModelIndex());
        changeTableFileChooserS(jTable1, jTable1.getColumn("Source").getModelIndex());
        changeTableFileChooserD(jTable1, jTable1.getColumn("Destination").getModelIndex());
        changeTableVerify(jTable1, jTable1.getColumn("Check").getModelIndex());
        
        jTable1.getColumn("S").setMinWidth(0); // Must be set before maxWidth!!
        jTable1.getColumn("S").setMaxWidth(0);
        jTable1.getColumn("S").setWidth(0);
        jTable1.getColumn("D").setMinWidth(0); // Must be set before maxWidth!!
        jTable1.getColumn("D").setMaxWidth(0);
        jTable1.getColumn("D").setWidth(0);
        
        jLabel1.setFont(f);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel16 = new javax.swing.JPanel();
        jProgressBar2 = new javax.swing.JProgressBar();
        jPanel17 = new javax.swing.JPanel();
        jProgressBar3 = new javax.swing.JProgressBar();
        jPanel18 = new javax.swing.JPanel();
        jProgressBar4 = new javax.swing.JProgressBar();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        historyTable = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(245, 245, 245));
        jPanel1.setLayout(null);

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTabbedPane1.setOpaque(true);

        jPanel3.setBackground(new java.awt.Color(245, 245, 245));
        jPanel3.setLayout(null);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(239, 239, 239));

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Aircraft Tail Number");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel8.setText("D-ABYT");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBackground(new java.awt.Color(239, 239, 239));
        jPanel8.setPreferredSize(new java.awt.Dimension(141, 67));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Aircaft Type");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel10.setText("Airplane");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.setBackground(new java.awt.Color(239, 239, 239));
        jPanel9.setPreferredSize(new java.awt.Dimension(141, 67));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Date of the Flight");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel11.setText("20 Jan 2021");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(239, 239, 239));
        jPanel10.setPreferredSize(new java.awt.Dimension(204, 0));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Flight Number");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel9.setText("BA2490");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel11.setBackground(new java.awt.Color(239, 239, 239));
        jPanel11.setPreferredSize(new java.awt.Dimension(141, 67));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setText("Departure Time");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel12.setText("18:30");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel12)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(239, 239, 239));
        jPanel12.setPreferredSize(new java.awt.Dimension(141, 67));

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setText("From");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel14.setText("MAA - Chennai");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel13.setBackground(new java.awt.Color(239, 239, 239));
        jPanel13.setPreferredSize(new java.awt.Dimension(141, 67));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("To");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 15)); // NOI18N
        jLabel16.setText("JFK - New York");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(174, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel2);
        jPanel2.setBounds(-1, -2, 1310, 280);

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);

        jTable1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Drive", "Name", "Total Space", "Used Space", "Free Space", "Drive Type", "Storage Type", "Files", "Source", "S", "Destination", "D", "Action", "Check", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setColumnSelectionAllowed(true);
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.setFocusable(false);
        jTable1.setGridColor(new java.awt.Color(204, 204, 204));
        jTable1.setInheritsPopupMenu(true);
        jTable1.setRowSelectionAllowed(false);
        jTable1.setSelectionBackground(new java.awt.Color(0, 0, 255));
        jTable1.setShowHorizontalLines(false);
        jTable1.setShowVerticalLines(false);
        jTable1.setSurrendersFocusOnKeystroke(true);
        jTable1.setUpdateSelectionOnSort(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setPreferredWidth(5);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(60);
            jTable1.getColumnModel().getColumn(6).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(7).setResizable(false);
            jTable1.getColumnModel().getColumn(7).setPreferredWidth(10);
            jTable1.getColumnModel().getColumn(8).setResizable(false);
            jTable1.getColumnModel().getColumn(8).setPreferredWidth(35);
            jTable1.getColumnModel().getColumn(9).setResizable(false);
            jTable1.getColumnModel().getColumn(9).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(10).setResizable(false);
            jTable1.getColumnModel().getColumn(10).setPreferredWidth(35);
            jTable1.getColumnModel().getColumn(11).setResizable(false);
            jTable1.getColumnModel().getColumn(11).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(12).setResizable(false);
            jTable1.getColumnModel().getColumn(12).setPreferredWidth(50);
            jTable1.getColumnModel().getColumn(13).setResizable(false);
            jTable1.getColumnModel().getColumn(13).setPreferredWidth(20);
            jTable1.getColumnModel().getColumn(14).setPreferredWidth(20);
        }

        jPanel14.setBackground(new java.awt.Color(0, 0, 0));
        jPanel14.setPreferredSize(new java.awt.Dimension(120, 45));
        jPanel14.setLayout(null);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Progress");
        jPanel14.add(jLabel1);
        jLabel1.setBounds(20, 4, 90, 40);

        jPanel15.setBackground(new java.awt.Color(236, 236, 236));
        jPanel15.setLayout(null);
        jPanel15.add(jProgressBar1);
        jProgressBar1.setBounds(10, 10, 100, 20);

        jPanel16.setBackground(new java.awt.Color(222, 222, 222));
        jPanel16.setLayout(null);
        jPanel16.add(jProgressBar2);
        jProgressBar2.setBounds(10, 10, 100, 20);

        jPanel17.setPreferredSize(new java.awt.Dimension(120, 41));

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel18.setBackground(new java.awt.Color(222, 222, 222));
        jPanel18.setPreferredSize(new java.awt.Dimension(120, 41));

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar4, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jProgressBar4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, 41, Short.MAX_VALUE)))
                .addGap(70, 70, 70))
        );

        jPanel3.add(jPanel5);
        jPanel5.setBounds(0, 290, 1310, 240);

        jTabbedPane1.addTab("Home", jPanel3);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(null);

        historyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "User Name", "Flight Number", "Drive", "Source", "Destination", "Total Files Copied", "Total Size", "Time Taken", "Copy Date", "Copy Time"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(historyTable);

        jPanel4.add(jScrollPane2);
        jScrollPane2.setBounds(20, 20, 1260, 490);

        jTabbedPane1.addTab("History", jPanel4);

        jPanel1.add(jTabbedPane1);
        jTabbedPane1.setBounds(30, 140, 1310, 560);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(null);
        jPanel1.add(jPanel6);
        jPanel6.setBounds(30, 140, 1310, 470);

        jLabel2.setBackground(new java.awt.Color(0, 255, 153));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/cover.jpg"))); // NOI18N
        jLabel2.setText("jLabel2");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(0, 0, 1370, 180);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1358, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:       
        int col = jTable1.getSelectedColumn();
        int row = jTable1.getSelectedRow();
        
        if(jTable1.getModel().getValueAt(row, col).toString().equals("Choose")){
            JFileChooser chooser = new JFileChooser();
            try {
                File dummy_file = new File(new File(jTable1.getModel().getValueAt(row, 0).toString()).getCanonicalPath());
                chooser.setCurrentDirectory(new File(dummy_file.toString()));
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int result = chooser.showOpenDialog(Home.this);
                if (result != JFileChooser.APPROVE_OPTION) {}
                else if(result == chooser.APPROVE_OPTION){
                    try {
                        File f = chooser.getSelectedFile();
                        jTable1.setValueAt(f.getAbsolutePath(), jTable1.getSelectedRow(), jTable1.getSelectedColumn()+1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println("I am returning false");
                    }
                }
             } catch (IOException ex) {
                Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
            }  
        }
        
        if(jTable1.getModel().getValueAt(row, col).toString().equals("Download")){
            //String s = jTable1.getModel().getValueAt(row, 0).toString();
            //String driveName = s.substring(0 , s.indexOf(":"));
            if(!jTable1.getModel().getValueAt(row, jTable1.getColumn("S").getModelIndex()).toString().equals("Select") && !jTable1.getModel().getValueAt(row, jTable1.getColumn("D").getModelIndex()).toString().equals("Select") /*&& driveName.equals("E")*/){
                try{
                    ProgressMonitorE.main(null);             
                }
                catch(Exception e){
                    System.out.println("Exception");
                }
            }
            
            else{
                JOptionPane.showMessageDialog(rootPane, "Please choose Source and Destination");
            }
        }        
        
        if(jTable1.getModel().getValueAt(row, col).toString().equals("Verify")){
            //String s = jTable1.getModel().getValueAt(row, 0).toString();
            //String driveName = s.substring(0 , s.indexOf(":"));
            if(!jTable1.getModel().getValueAt(row, jTable1.getColumn("S").getModelIndex()).toString().equals("Select") && !jTable1.getModel().getValueAt(row, jTable1.getColumn("D").getModelIndex()).toString().equals("Select") /*&& driveName.equals("E")*/){
                try{
                    HashingE.main(null);             
                }
                catch(Exception e){
                    System.out.println("Exception");
                }
            }

            if(jTable1.getModel().getValueAt(row, jTable1.getColumn("Status").getModelIndex()).toString().equals("Copied & Verified")){
                JOptionPane.showMessageDialog(rootPane, "Already Copied and Verified Successfully!");
            }
            else{
                JOptionPane.showMessageDialog(rootPane, "Please Download then Verify");
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws ClassNotFoundException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
              UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (InstantiationException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Home().setVisible(true);
            }
            
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTable historyTable;
    private javax.swing.JLabel jLabel1;
    public static javax.swing.JLabel jLabel10;
    public static javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    public static javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    public javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    public static javax.swing.JProgressBar jProgressBar1;
    public static javax.swing.JProgressBar jProgressBar2;
    public static javax.swing.JProgressBar jProgressBar3;
    public static javax.swing.JProgressBar jProgressBar4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    public static javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
    static public class HeaderColor extends DefaultTableCellRenderer{
        public HeaderColor(){
            setOpaque(true);
        }
        public Component getTableCellRendererComponent (JTable mytable,Object value,boolean selected,boolean focused,int row, int column){
            super.getTableCellRendererComponent(mytable, value, selected, focused, row, column);
            setBackground(new java.awt.Color(0,0,0));
            setForeground(new java.awt.Color(255,255,255));
            setFont(new Font("Helvetica", Font.PLAIN, 15));
            setPreferredSize(new Dimension(120,45));
            return this;
        }
    }
    
    private static class HeaderRenderer implements TableCellRenderer {
        DefaultTableCellRenderer renderer;
        int horAlignment;
        public HeaderRenderer(JTable table, int horizontalAlignment) {
            horAlignment = horizontalAlignment;
            renderer = (DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer();
        }
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            Component c = renderer.getTableCellRendererComponent(table, value,isSelected, hasFocus, row, col);
            JLabel label = (JLabel)c;
            label.setHorizontalAlignment(horAlignment);
            return label;
        }
    }
    
    
    public void changeTable(JTable table, int column_index) {
        table.getColumnModel().getColumn(column_index).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String st_val = table.getModel().getValueAt(row, (jTable1.getColumn("Action").getModelIndex())).toString();
               
                if (st_val.equalsIgnoreCase("Download") && row%2==0) {
                    c.setBackground(new Color(198,13,47));
                    c.setForeground(Color.white);
                    JLabel label = (JLabel)c;
                    label.setHorizontalAlignment(JLabel.CENTER);
                    //label.setBorder(new RoundedBorder(Color.black, 10));
                    label.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, new Color(236,236,236)),BorderFactory.createEmptyBorder(8, 8, 8, 8)));
                    
                }
                else if(st_val.equalsIgnoreCase("Download") && row%2!=0){
                    c.setBackground(new Color(198,13,47));
                    c.setForeground(Color.white);
                    JLabel label = (JLabel)c;
                    label.setHorizontalAlignment(JLabel.CENTER);
                    //label.setBorder(new RoundedBorder(Color.black, 10));
                    label.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(8, 8, 8, 8,new Color(222,222,222)),BorderFactory.createEmptyBorder(8, 8, 8, 8)));
                    
                }
                else if(st_val.equalsIgnoreCase("") && row%2==0){
                    c.setBackground(new Color(236,236,236));
                    c.setForeground(new Color(236,236,236));
                }
                else if(st_val.equalsIgnoreCase("") && row%2!=0){
                    c.setBackground(new Color(222,222,222));
                    c.setForeground(Color.white);                    
                }
                else{
                    c.setBackground(Color.BLUE);
                }
                return c;                
            }
        });
    }
    
    public void changeTableFileChooserS(JTable table, int column_index) {
        table.getColumnModel().getColumn(column_index).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String st_val = table.getModel().getValueAt(row, column_index).toString();
                if (st_val.equalsIgnoreCase("Choose") && row%2==0) {
                    c.setBackground(new Color(198,13,47));
                    c.setForeground(Color.white);
                    JLabel label = (JLabel)c;
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(8,8 ,8, 8, new Color(236,236,236)),BorderFactory.createEmptyBorder(8,8 ,8, 8)));
                    label.setToolTipText(jTable1.getModel().getValueAt(row, jTable1.getColumn("S").getModelIndex()).toString());
                }
                else if(st_val.equalsIgnoreCase("Choose") && row%2!=0){
                    c.setBackground(new Color(198,13,47));
                    c.setForeground(Color.white);
                    JLabel label = (JLabel)c;
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(8,8 ,8, 8,new Color(222,222,222)),BorderFactory.createEmptyBorder(8,8 ,8, 8)));
                    label.setToolTipText(jTable1.getModel().getValueAt(row, jTable1.getColumn("S").getModelIndex()).toString());
                }
                else if(st_val.equalsIgnoreCase("") && row%2==0 /*&& currupted == false*/){
                    c.setBackground(new Color(236,236,236));
                    c.setForeground(new Color(236,236,236));
                }
                else if(st_val.equalsIgnoreCase("") && row%2!=0 /*&& currupted == false*/){
                    c.setBackground(new Color(222,222,222));
                    c.setForeground(Color.white);                  
                }
                else{
                    c.setBackground(Color.blue);
                }
                return c;                
            }
        });
    }
    
    public void changeTableFileChooserD(JTable table, int column_index) {
        table.getColumnModel().getColumn(column_index).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String st_val = table.getModel().getValueAt(row, column_index).toString();
                if (st_val.equalsIgnoreCase("Choose") && row%2==0) {
                    c.setBackground(new Color(198,13,47));
                    c.setForeground(Color.white);
                    JLabel label = (JLabel)c;
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(8,8 ,8, 8, new Color(236,236,236)),BorderFactory.createEmptyBorder(8,8 ,8, 8)));
                    if(!(jTable1.getModel().getValueAt(row, jTable1.getColumn("D").getModelIndex()).toString().equals("Select"))){
                        label.setToolTipText(jTable1.getModel().getValueAt(row, jTable1.getColumn("D").getModelIndex()).toString()+"\\"+jTable1.getModel().getValueAt(row, jTable1.getColumn("Drive").getModelIndex()).toString().substring(0,1)+"_"+jLabel10.getText()+"_"+jLabel9.getText()+"_"+jLabel11.getText());
                    }
                    else{
                        label.setToolTipText(jTable1.getModel().getValueAt(row, jTable1.getColumn("D").getModelIndex()).toString());
                    }
                    
                }
                else if(st_val.equalsIgnoreCase("Choose") && row%2!=0){
                    c.setBackground(new Color(198,13,47));
                    c.setForeground(Color.white);
                    JLabel label = (JLabel)c;
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(8,8 ,8, 8,new Color(222,222,222)),BorderFactory.createEmptyBorder(8,8 ,8, 8)));
                    label.setToolTipText(jTable1.getModel().getValueAt(row, jTable1.getColumn("D").getModelIndex()).toString());
                    if(!(jTable1.getModel().getValueAt(row, jTable1.getColumn("D").getModelIndex()).toString().equals("Select"))){
                        label.setToolTipText(jTable1.getModel().getValueAt(row, jTable1.getColumn("D").getModelIndex()).toString()+"\\"+jTable1.getModel().getValueAt(row, jTable1.getColumn("Drive").getModelIndex()).toString().substring(0,1)+"_"+jLabel10.getText()+"_"+jLabel9.getText()+"_"+jLabel11.getText());
                    }
                    else{
                        label.setToolTipText(jTable1.getModel().getValueAt(row, jTable1.getColumn("D").getModelIndex()).toString());
                    }
                }
                else if(st_val.equalsIgnoreCase("") && row%2==0 /*&& currupted == false*/){
                    c.setBackground(new Color(236,236,236));
                    c.setForeground(new Color(236,236,236));
                }
                else if(st_val.equalsIgnoreCase("") && row%2!=0 /*&& currupted == false*/){
                    c.setBackground(new Color(222,222,222));
                    c.setForeground(Color.white);                  
                }
                else{
                    c.setBackground(Color.blue);
                }
                return c;                
            }
        });
    }
    
    public void changeTableVerify(JTable table, int column_index) {
        table.getColumnModel().getColumn(column_index).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                String st_val = table.getModel().getValueAt(row, (jTable1.getColumn("Check").getModelIndex())).toString();
               
                if (st_val.equalsIgnoreCase("Verify") && row%2==0) {
                    c.setBackground(new Color(198,13,47));
                    c.setForeground(Color.white);
                    JLabel label = (JLabel)c;
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, new Color(236,236,236)),BorderFactory.createEmptyBorder(8, 8, 8, 8)));
                }
                else if(st_val.equalsIgnoreCase("Verify") && row%2!=0){
                    c.setBackground(new Color(198,13,47));
                    c.setForeground(Color.white);
                    JLabel label = (JLabel)c;
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(8, 8, 8, 8,new Color(222,222,222)),BorderFactory.createEmptyBorder(8, 8, 8, 8)));
                }
                else if(st_val.equalsIgnoreCase("") && row%2==0){
                    c.setBackground(new Color(236,236,236));
                    c.setForeground(new Color(236,236,236));
                }
                else if(st_val.equalsIgnoreCase("") && row%2!=0){
                    c.setBackground(new Color(222,222,222));
                    c.setForeground(Color.white);                    
                }
                else{
                    c.setBackground(Color.BLUE);
                }
                return c;                
            }
        });
    }
    
    public void driveCurrupted(JTable table, int column_index) {
        table.getColumnModel().getColumn(column_index).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                               
                if (currupted==false && row%2==0) {
                    c.setBackground(new Color(0,128,0));
                    c.setForeground(Color.white);
                    JLabel label = (JLabel)c;
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(8, 8, 8, 8, new Color(236,236,236)),BorderFactory.createEmptyBorder(8, 8, 8, 8)));
                }
                else if(currupted==false && row%2!=0){
                    c.setBackground(new Color(0,128,0));
                    c.setForeground(Color.white);
                    JLabel label = (JLabel)c;
                    label.setHorizontalAlignment(JLabel.CENTER);
                    label.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(8, 8, 8, 8,new Color(222,222,222)),BorderFactory.createEmptyBorder(8, 8, 8, 8)));
                }
                else if(currupted==false && row%2==0){
                    c.setBackground(new Color(236,236,236));
                    c.setForeground(new Color(236,236,236));
                }
                else if(currupted==false && row%2!=0){
                    c.setBackground(new Color(222,222,222));
                    c.setForeground(Color.white);                    
                }
                else{
                    c.setBackground(Color.red);
                    c.setForeground(Color.white);
                }
                return c;                
            }
        });
    }
    
    public void changeTableNotCorrupted(JTable table) {
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if(row%2==0){
                    c.setBackground(new Color(236,236,236));
                    c.setForeground(Color.black);
                    JLabel label = (JLabel)c;
                    label.setHorizontalAlignment(JLabel.CENTER);
                }
                else if(row%2!=0){
                    c.setBackground(new Color(222,222,222));
                    c.setForeground(Color.black);
                    JLabel label = (JLabel)c;
                    label.setHorizontalAlignment(JLabel.CENTER);
                }
                else{
                    c.setBackground(Color.BLUE);
                    JLabel label = (JLabel)c;
                    label.setHorizontalAlignment(JLabel.CENTER);
                }
                return c;               
            }
        });
    }

    class MyTableCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Color getBackground() {
            return super.getBackground();
        }
        @Override
        public Color getForeground() {
            return super.getForeground();
        }
    }
}