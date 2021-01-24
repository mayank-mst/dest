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
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.beans.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.TimeUnit.DAYS;
import static java.util.concurrent.TimeUnit.HOURS;
import static java.util.concurrent.TimeUnit.MICROSECONDS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import static triway.Home.jLabel10;
import static triway.Home.jLabel11;
import static triway.Home.jLabel9;
import static triway.Home.jTable1;
import static triway.Home.jProgressBar4;
 
public class ProgressMonitorH extends JPanel {
    private JProgressBar progressBar;
    private CopyFiles operation;
    String sourceDirectory;
    String destinationDirectory;
    private JLabel size;
    private JLabel time;
    private JLabel from;
    private JLabel to;
    JFrame frame;
    public static int fileCopiedDb;
    public static long fileCopiedSizeDb;
    public static String timeToCopy; 
    
    public static void main(String[] args) {        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ProgressMonitorH().createAndShowGUI();
            }
        });
    }
    
    private void createAndShowGUI() {
        progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        jProgressBar4.setStringPainted(true);
        
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        from = new JLabel("From :");
        to = new JLabel("To :");
        panel.add(progressBar,BorderLayout.PAGE_START);
        panel.add(from,BorderLayout.CENTER);
        panel.add(to,BorderLayout.SOUTH);

        
        JPanel labelpanel = new JPanel(new BorderLayout(8, 8));
        size = new JLabel("Item Remaining:");
        time = new JLabel("Time Remaining:");
        labelpanel.add(size,BorderLayout.PAGE_START);
        labelpanel.add(time,BorderLayout.CENTER);
        
        Action pause = new AbstractAction("Pause") {
            @Override
            public void actionPerformed(ActionEvent e) {
                operation.pause();
                size.setText("Paused");
                time.setText("");
            }
        };

        Action resume = new AbstractAction("Resume") {
            @Override
            public void actionPerformed(ActionEvent e) {
                operation.resume();
            }
        };

        Action stop = new AbstractAction("Cancel") {
            @Override
            public void actionPerformed(ActionEvent e) {
                operation.cancel(true);
                frame.dispose();;
            }
        };

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonsPanel.add(new JButton(pause));
        buttonsPanel.add(new JButton(resume));
        buttonsPanel.add(new JButton(stop));
        
        JPanel content = new JPanel(new BorderLayout(8, 8));
        content.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        content.add(panel, BorderLayout.PAGE_START);
        content.add(labelpanel,BorderLayout.CENTER);
        content.add(buttonsPanel, BorderLayout.SOUTH);
        
        int row = jTable1.getSelectedRow();
        sourceDirectory = jTable1.getModel().getValueAt(row, jTable1.getColumn("S").getModelIndex()).toString();
        
        frame = new JFrame("Copying: "+sourceDirectory);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (!operation.isDone()) {
                    operation.cancel(true);
                }
                e.getWindow().dispose();
            }
        });
        
        destinationDirectory = jTable1.getModel().getValueAt(row, jTable1.getColumn("D").getModelIndex()).toString();
        
        File theDir = new File(destinationDirectory+"/"+sourceDirectory.substring(0,1)+"_"+jLabel10.getText()+"_"+jLabel9.getText()+"_"+jLabel11.getText());
        if (!theDir.exists()){
            theDir.mkdirs();
        }
        
        
        
        File srcDir = new File(sourceDirectory);        
        File destDir = null;
        
        from.setText("From: "+sourceDirectory);
        if (srcDir.exists() && (srcDir.listFiles() != null && srcDir.listFiles().length > 0)) {
            destDir = new File(theDir.toString());
            to.setText("To: "+theDir.toString());
        }
        operation = new CopyFiles(srcDir, destDir);
            //operation.addPropertyChangeListener(this);
        operation.execute();
        
        operation.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("paused".equals(evt.getPropertyName())) {
                    if((Boolean)evt.getNewValue() == true){
                        System.out.println("Paused");
                    }
                    else{
                        if (evt.getPropertyName().equals("progress")) {            
                        // get the % complete from the progress event
                        // and set it on the progress monitor
                        int progress = ((Integer)evt.getNewValue()).intValue();
                        //progressMonitor.setProgress(progress);
                        progressBar.setValue(progress);
                        jProgressBar4.setValue(progress);
                        }
                        System.out.println("Resumed");
                    }
                    String text = (Boolean)evt.getNewValue() ? "Paused..." : "Resumed...";
                }
            }
        });
        frame.add(content);
        frame.setSize(370, 203);
        //frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }
    
    

    class CopyData {

        private long i;
        private String s;
        private long i0;
        private long i1;
        private String t;
        private boolean isPaused;

        public CopyData(long i,String s,long i0,long i1,String t,boolean isPaused) {
            this.i = i;
            this.s = s;
            this.i0 = i0;
            this.i1 = i1;
            this.t = t;
            this.isPaused = isPaused;
        }
        long getTotalKiloBytes(){
            return i0;
        }
        long getProgress(){
            return i;
        }
        long getKiloBytesCopied(){
            return i1;
        }
        String getFileName(){
            return s;
        }
        String getTime(){
            return t;
        }
        boolean getisPaused(){
            return isPaused;
        }
    }
 
    class CopyFiles extends SwingWorker<List<CopyData>, CopyData> {
        private File srcDir;
        private File destDir;

        private CopyFiles(File srcDir, File destDir) {
            this.srcDir = srcDir;
            this.destDir = destDir;
        }
        private volatile boolean isPaused;

        public final void pause() {
            if (!isPaused() && !isDone()) {
                isPaused = true;
                firePropertyChange("paused", false, true);
            }
        }

        public final void resume() {
            if (isPaused() && !isDone()) {
                isPaused = false;
                firePropertyChange("paused", true, false);
            }
        }

        public final boolean isPaused() {
            return isPaused;
        }
        
        public String timeToString(long nanos) {

            Optional<TimeUnit> first = Stream.of(DAYS, HOURS, MINUTES, SECONDS, MILLISECONDS,MICROSECONDS).filter(u -> u.convert(nanos, NANOSECONDS) > 0).findFirst();
            TimeUnit unit = first.isPresent() ? first.get() : NANOSECONDS;

            double value = (double) nanos / NANOSECONDS.convert(1, unit);
            return String.format("%.4g %s", value, unit.name().toLowerCase());
        }
        
        long getTotalKiloBytes(long totalBytes){
            return totalBytes/1000;
        }
        
        long getKiloBytesCopied(long bytesCopied){
            return bytesCopied/1000;
        }
        
        long calcTotalBytes(File directory) {
            long length = 0;
            for (File file : directory.listFiles()) {
                if (file.isFile())
                    length += file.length();
                else
                    length += calcTotalBytes(file);
            }
            return length;
        }        
        public void process(List<CopyData> data) {
            if(isCancelled()) { return; }
            CopyData update  = new CopyData(0, "", 0, 0, "",false);
            for (CopyData d : data) {
                // progress updates may be batched, so get the most recent
                    if (d.getKiloBytesCopied() > update.getKiloBytesCopied()) {
                            update = d;
                    }
            }
            String progressNote = Math.round((double)update.getKiloBytesCopied()/1000000.00*100.00)/100.00+" GB "+" of "+ Math.round((double)update.getTotalKiloBytes()/1000000.00*100.00)/100.00 + " GB";

            if (update.getProgress() < 100 && !update.getisPaused()) {
                progressBar.setValue((int) update.getProgress());
                jProgressBar4.setValue((int) update.getProgress());
                size.setText("Item Copied: "+progressNote);
                time.setText("Time Remaining: About "+update.getTime());
                //progressMonitor.setNote(update.getProgress()+"%"+" "+"["+progressNote+"]"+" "+"\n"+update.getTime());
            } else if(update.getProgress() < 100 && update.getisPaused()){
                System.out.println("In procees: " +update.getisPaused());
            }
            else {
                progressBar.setValue((int) update.getProgress());
                jProgressBar4.setValue((int) update.getProgress());
                size.setText("Item Copied: "+progressNote);
                time.setText("Time Remaining: About "+update.getTime());
                //progressMonitor.setNote(update.getProgress()+"%"+" "+"["+progressNote+"]"+" "+"\n"+update.getTime());
            }         
	}
        long startTime = System.nanoTime();
        @Override
        public List<CopyData> doInBackground() throws Exception{
            if (!isCancelled()) {
                if (!isPaused() ) {
                    int progress = 0;
                    // initialize bound property progress (inherited from SwingWorker)
                    setProgress(0);
                    // get the files to be copied from the source directory
                    File[] files = srcDir.listFiles();
                    fileCopiedDb = files.length;
                    // determine the scope of the task
                    long totalBytes = calcTotalBytes(srcDir);
                    fileCopiedSizeDb = calcTotalBytes(srcDir);
                    long bytesCopied = 0;

                    while (progress < 100 && !isCancelled()) {                 
                            // copy the files to the destination directory
                        for (File f : files) {
                            File destFile = new File(destDir, f.getName());
                            long previousLen = 0;
                            //taskOutput.append("Now Copying "+ f.getName() + "\n");
                            try {
                                InputStream in = new FileInputStream(f);
                                OutputStream out = new FileOutputStream(destFile);                    
                                byte[] buf = new byte[12000000];
                                int counter = 0;
                                int len;
                                while ((len = in.read(buf)) > 0) {
                                    out.write(buf, 0, len);
                                    counter += len;
                                    bytesCopied += (destFile.length() - previousLen);
                                    previousLen = destFile.length();
                                    int PROGRESS_CHECKPOINT=0;
                                    if (counter > PROGRESS_CHECKPOINT || bytesCopied == totalBytes) {
                                            // get % complete for the task
                                        progress = (int)((100 * bytesCopied) / totalBytes);
                                        counter = 0;
                                        CopyData current = new CopyData(progress, f.getName(), getTotalKiloBytes(totalBytes), getKiloBytesCopied(bytesCopied),timeToString((((totalBytes-bytesCopied)*100)*(totalBytes/bytesCopied))),isPaused());

                                        setProgress(progress);
                                        publish(current);
                                    }
                                }
                                in.close();
                                out.close();
                            } catch (IOException e) {
                                    e.printStackTrace();
                            }
                            //taskOutput.append("Copied "+ f.getName() + "\n");
                        }
                    }
                } else {
                    Thread.sleep(2000);
                }
            }
            return null;
        }
        @Override
        public void done() {
            try {
                // call get() to tell us whether the operation completed or 
                // was canceled; we don't do anything with this result
                CopyData result = (CopyData) get();
                //taskOutput.append("Copy operation completed.\n"); 
                //jTable1.setValueAt("Copied Not Verified", jTable1.getSelectedRow(), jTable1.getColumn("Status").getModelIndex());
		} catch (InterruptedException e) {
			System.out.println("Interrupted");
		} catch (CancellationException e) {
		    // get() throws CancellationException if background task was canceled
			//taskOutput.append("Copy operation canceled.\n");
                        //jTable1.setValueAt("Canceled", jTable1.getSelectedRow(), jTable1.getColumn("Status").getModelIndex());
		}catch (ExecutionException e) {
			//taskOutput.append("Exception: " + "Please select Directory not Drive");
                        frame.setVisible(false);
                        progressBar.setValue(0);
                        jProgressBar4.setValue(0);
                        JOptionPane.showMessageDialog(null, "Please select Directory not Drive");
		}    
            Toolkit.getDefaultToolkit().beep();
            //progressMonitor.setProgress(0);
            progressBar.setValue(100);
            jProgressBar4.setValue(100);
            long endTime = System.nanoTime();
            long a=endTime-startTime;
            timeToCopy = timeToString(a);
        }
    }
}

