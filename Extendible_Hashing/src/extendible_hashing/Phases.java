/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package extendible_hashing;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;

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
        runIt();
    }
    
    public int binary(int num)
    {
        if(num==1)
        {
            return 1;
        }
        int binary = binary(num/2);
        num = num%2;
        return binary*10 + num;
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
//                        JOptionPane.showMessageDialog(this,"Local Depth shortage for block " + j + "\nIncreasing it by one");
                        System.out.println("Local Depth shortage for block " + j + "\nIncreasing it by one");
                        int k = (int)Math.pow(2,localdepth.get(j)) + j;
                        if(blocks.size()<k)
                            addList(k);
                        System.out.println("value of k is " + k);
                        int ldj = localdepth.get(j);
                        ldj++;
                        localdepth.set(j,ldj);
                        if(localdepth.size()<k)
                            addLocalDepth(k);
//                        localdepth[j]++;
                        if(ldj>gd)
                        {
//                            JOptionPane.showMessageDialog(this,"Local Depth exeeded Global Depth\nIncreasing it by one");
                            System.out.println("Local Depth exeeded Global Depth\nIncreasing it by one");
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
        addList((int)Math.pow(2,gd));
        for(int i=0;i<keys;i++)
        {
            System.out.println("Allocating " + arr[i]);
            allocate(i);
            for(int j=0;j<blocks.size();j++)
            {
                if(blocks.get(j).size()!=0)
                {
                     System.out.print("Keys in block " + j + ": ");
//                for (int k=0;k<blocks.get(j).size();k++) {
//                    int get = blocks.get(j).get(k);
                    System.out.println(blocks.get(j));
                }
//                }
            }
        }
//        System.out.println(depth(3,5));
        /*for(int i=0;i<localdepth.length;i++)
        {
            System.out.println(localdepth[i]);
        }*/
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 736, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 492, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
    // End of variables declaration//GEN-END:variables
}
