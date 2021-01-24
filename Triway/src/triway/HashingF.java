/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package triway;

/**
 *
 * @author DELL
 */
import java.awt.Toolkit;
import java.sql.Statement;
import java.io.*;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.*;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.io.FileUtils;
import static triway.Home.historyTable;
import static triway.Home.jLabel10;
import static triway.Home.jLabel11;
import static triway.Home.jLabel9;
import static triway.Home.jTable1;
import static triway.Login.userName;
import static triway.ProgressMonitorF.fileCopiedDb;
import static triway.ProgressMonitorF.fileCopiedSizeDb;
import static triway.ProgressMonitorF.timeToCopy;

public class HashingF{
    public static String sourceDirectory;
    public static String destinationDirectory;
    
    public static String collectFiles(File directory,boolean includeHiddenFiles) throws IOException{
        File[] files = directory.listFiles();
        StringBuffer sb = new StringBuffer();

        if (files != null){
            for (File file : files){
                if (includeHiddenFiles || !Files.isHidden(file.toPath())){
                    if (file.isDirectory()){
                        collectFiles(file, includeHiddenFiles);
                    } else{
                        System.out.println(getChecksum(file));
                        sb.append(getChecksum(file));
                    }
                }
            }
        }
        return sb.toString();
    }

    public static long getChecksum(final File path) throws FileNotFoundException, IOException {
        try (final CheckedInputStream in = new CheckedInputStream(new BufferedInputStream(new FileInputStream(path)), new CRC32())) {
            return tryGetChecksum(in);
        } catch (final Exception e) {
            System.err.format("%s: error: Unable to calculate checksum: %s%n", path, e.getMessage());
            return 0L;
        }
    }

    public static long tryGetChecksum(final CheckedInputStream in) throws IOException {
        int bytesRead;
        long csum = 0;
        final byte[] buf = new byte[1000000];
        if ((bytesRead = in.read(buf)) != -1){
            csum = in.getChecksum().getValue();
        }
            return csum;
    }

    public static void main(String[] args) throws Exception {
        
        int row = jTable1.getSelectedRow();
        sourceDirectory = jTable1.getModel().getValueAt(row, jTable1.getColumn("S").getModelIndex()).toString();
        destinationDirectory = jTable1.getModel().getValueAt(row, jTable1.getColumn("D").getModelIndex()).toString();
        
        File theDir = new File(destinationDirectory+"/"+sourceDirectory.substring(0,1)+"_"+jLabel10.getText()+"_"+jLabel9.getText()+"_"+jLabel11.getText());
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        
        String sourceDir = collectFiles(new File(sourceDirectory),true);
        
        String destinationDir = collectFiles(new File(theDir.toString()),true);
        
        if(sourceDir.equals(destinationDir)){
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Succesfully Copied and Verified!");
            jTable1.setValueAt("Copied & Verified", jTable1.getSelectedRow(), jTable1.getColumn("Status").getModelIndex());
            
        //  database  
        try{
            String insertQuery = "INSERT INTO test VALUES(?,?,?,?,?,?,?,?,?,?)";
            //step1 load the driver class  
            Class.forName("oracle.jdbc.driver.OracleDriver"); 
            Connection con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","abc");
            System.out.println("Connection Established");
            PreparedStatement ps = con.prepareStatement(insertQuery);
            ps.setString(1, jLabel9.getText());
            ps.setString(2, jTable1.getModel().getValueAt(jTable1.getSelectedRow(), 0).toString());
            ps.setString(3, sourceDirectory);
            ps.setString(4, theDir.toString());
            ps.setString(5, Integer.toString(fileCopiedDb));
            double fsize = Math.round(fileCopiedSizeDb/1000000000.00*100.00)/100.00;
            ps.setString(6, Double.toString(fsize)+" GB");
            ps.setString(7, timeToCopy);
            ps.setString(8, java.time.LocalDate.now().toString());
            ps.setString(9, java.time.LocalTime.now().toString());
            ps.setString(10, userName);
            ps.execute();
            
            String retrieveQuery = "SELECT * FROM test ORDER BY Copy_Date DESC";
            DefaultTableModel historymodel = (DefaultTableModel) historyTable.getModel();
            // Clear the existing table
            int historyrows = historymodel.getRowCount();
            if (historyrows > 0) {
                for (int i = 0; i < historyrows; i++) {
                    historymodel.removeRow(0);
                }
            }
            Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
            ResultSet rs=stmt.executeQuery(retrieveQuery);  
            
            while (rs.next()) {
                historymodel.addRow(new Object[]{rs.getString(10),rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getString(9)});
            }
            con.close();
            
            int result = JOptionPane.showConfirmDialog(null,"Sure? You want to delete? "+sourceDirectory, "Delete Confirmation",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
            if(result == JOptionPane.YES_OPTION){
                try {
                    FileUtils.deleteDirectory(new File(sourceDirectory));
                    System.out.println("Directory deleted successfully.");
                    JOptionPane.showMessageDialog(null, sourceDirectory+" has been deleted after copying successfully");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            

        }catch(Exception e){
            System.out.println(e);
        }
        }else{
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Copied Files are not same, Copy again");
        }
    }
}

