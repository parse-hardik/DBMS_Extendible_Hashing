/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extendible_hashing;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hardik
 */
public class Phases extends javax.swing.JFrame {

    /**
     * Creates new form Phases
     */
    ArrayList<ArrayList<Integer> > blocks = new ArrayList<ArrayList<Integer> >(); 
    int keys = GUI.keys;
    int ld = GUI.ld;
    int gd = GUI.gd;
    int arr[] = GUI.arr;
    int func = GUI.func;
    int bfr = GUI.bfr;
    ArrayList<Integer> localdepth = new ArrayList<Integer>();
//    int localdepth[] = new int[func];
    
    public Phases() {
        initComponents();
        for(int i=0;i<func;i++)
        {
            localdepth.add(1);
        }
//        Arrays.fill(localdepth, 1);
        jLabel3.setText(gd + "");
        jLabel5.setText(bfr + "");
        jTextField1.setEnabled(false);
        jButton2.setEnabled(false);
        jLabel6.setEnabled(false);
    }
    
    public int binary(int num)
    {
        if(num==0)
        {
            return 0;
        }
        if(num==1)
        {
            return 1;
        }
        int bin = binary(num/2);
        num = num%2;
        return bin*10 + num;
    }
    
    public int num(int binary)
    {
        int num=0,i=0;
        while(binary!=0)
        {
            num += (binary%10)*((int)Math.pow(2,i++)); 
            binary /= 10;
        }
        return num;
    }
    
    public int depth(int hash,int localdepth)
    {
        int binary = binary(hash);
        binary %= ((int)Math.pow(10,localdepth));
        int num = num(binary);
        return num;
    }
    
    public void addList(int num)
    {
        while(blocks.size() <= num)
        {
            ArrayList<Integer> a = new ArrayList<Integer>();
            blocks.add(a);
        }
    }
    
    public void addLocalDepth(int num)
    {
        while(localdepth.size() <= num)
        {
            localdepth.add(1);
        }
    }
    
    public void allocate(int i){
        System.out.println("Block size: " + blocks.size());
        for(int j=0;j<func;j++)
            {
                try{
                    if((depth(arr[i]%func,localdepth.get(j)))==j)
                {
                    if(blocks.get(j).size()<bfr){
                        blocks.get(j).add(arr[i]);

                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Local Depth shortage for block " + j + "\nIncreasing it by one");
                        System.out.println("Local Depth shortage for block " + j + "\nIncreasing it by one");
                        int k = (int)Math.pow(2,localdepth.get(j)) + j;
                        if(k>=blocks.size())
                        {
                            System.out.println("Since new key addition exceeds current bfr\nbfr has to be incremented by 1");
                            JOptionPane.showMessageDialog(null,"Since new key addition exceeds current bfr\nbfr has to be incremented by 1");
                            bfr++;
                        }
                        if(blocks.size()<=k)
                            addList(k);
                        System.out.println("value of k is " + k);
                        JOptionPane.showMessageDialog(null,"value of k is " + k);
                        int ldj = localdepth.get(j);
                        ldj++;
                        localdepth.set(j,ldj);
                        if(localdepth.size()<k)
                            addLocalDepth(k);
//                        localdepth[j]++;
                        if(ldj>gd)
                        {
                            JOptionPane.showMessageDialog(null,"Local Depth exeeded Global Depth\nIncreasing it by one");
                            System.out.println("Local Depth exeeded Global Depth\nIncreasing it by one");
                            JOptionPane.showMessageDialog(null,"Local Depth exeeded Global Depth\nIncreasing it by one");
                            gd++;
                        }
                        localdepth.set(k,ldj);
//                        localdepth[k] = localdepth[j];
                        /*
                        if(blocks.size()<=k)
                        {
                            int p = k - blocks.size();
                            for(int m=0;m<1+p;m++)
                            {
                                ArrayList<Integer> a = new ArrayList<Integer>();
                                blocks.add(a);
                                System.out.println("row added");
                            }
                        }*/
                        for(int m=0;m<blocks.get(j).size();m++)
                        {
                            if((depth(blocks.get(j).get(m)%func,localdepth.get(j)))!=j)
                            {
                                int key = blocks.get(j).remove(m);
                                blocks.get(k).add(key);
                                m--;
                            }
                        }
                        
                        if((depth(arr[i]%func,localdepth.get(j)))==j)
                        {
                            blocks.get(j).add(arr[i]);
                        }
                        else
                        {
                            blocks.get(k).add(arr[i]);
                            
                                /*To verify the condition that if all keys from blocks.get(j)
                                have been transfered to blocks.get(k), 
                                Then we'll have to check if blocks.get(k).size()<bfr
                                if(yes)
                                    add arr[i];
                                else
                                    find the partener for k;
                                    add arr[i] to the newly found partener;
                            */
                        }
                    }
                    break;
                }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
    }
    
    public final void runIt()
    {
        /*
        for(int i=0;i<func;i++)
        {
            ArrayList<Integer> a = new ArrayList<Integer>();
            blocks.add(a);
        }
        */
        DefaultTableModel model = (DefaultTableModel)jTable1.getModel();
        addList((int)Math.pow(2,gd));
        for(int i=0;i<keys;i++)
        {
            jLabel3.setText(gd + "");
            jLabel5.setText(bfr + "");
            System.out.println("Allocating " + arr[i]);
            jTextArea1.append("Allocating " + arr[i] + "\n");
            allocate(i);
            model.setRowCount(0);
            if(i==0 || (JOptionPane.showConfirmDialog(null,"Start Allocating " + arr[i])==0))
            {
                for(int j=0;j<blocks.size();j++)
                {
                    if(blocks.get(j).size()!=0)
                    {
                         System.out.print("Keys in block " + j + ": ");
                         jTextArea1.append("Keys in block " + j + ": ");
    //                for (int k=0;k<blocks.get(j).size();k++) {
    //                    int get = blocks.get(j).get(k);
                        System.out.println(blocks.get(j));
                        jTextArea1.append(blocks.get(j) + "\n");
    //                    int binary = binary(j);
    //                    int ldj = (int)Math.pow(10,localdepth.get(j));
    //                    int bin = binary%ldj;
                        int binary = binary(j);
                        binary %= ((int)Math.pow(10,localdepth.get(j)));
                        model.addRow(new Object[]{
                            j,
                            binary,
                            blocks.get(j),
                            localdepth.get(j)
                        });
                    }
    //                }
                }
            }
            else
            {
                break;
            }
        }
        jButton2.setEnabled(true);
        jLabel6.setEnabled(true);
        jTextField1.setEnabled(true);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Block#", "Binary Rep", "Hashed Keys", "Local Depth "
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setFont(new java.awt.Font("Comic Sans MS", 3, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Allocation");

        jLabel2.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Current Global Depth");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Current BFR");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        jButton1.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jButton1.setText("Allocate");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jButton2.setText("Search");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Search an element");

        jTextField1.setFont(new java.awt.Font("Comic Sans MS", 1, 18)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel7.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(53, 53, 53)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 713, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(94, 94, 94)
                        .addComponent(jButton2))
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(43, 43, 43))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(69, 69, 69)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(5, 5, 5)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(1, 1, 1)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(40, 40, 40)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2))
                        .addGap(30, 30, 30))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)))
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTextField1.setEnabled(false);
        runIt();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        int element = Integer.parseInt(jTextField1.getText());
        int flag=0;
        for(int j=0;j<func;j++)
        {
            if((depth(element%func,localdepth.get(j)))==j)
            {
                for(int i=0;i<blocks.get(j).size();i++)
                {
                    if(blocks.get(j).get(i)==element)
                    {
                        jLabel7.setText("Element " + element + " found in bucket " + j + " and block " + i);
                        flag=1;
                    }
                }
            }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Phases.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Phases.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Phases.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Phases.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Phases().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
