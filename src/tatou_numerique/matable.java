/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tatou_numerique;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author pc
 */
public class matable extends javax.swing.JFrame {

    /**
     * Creates new form ma_table
     */
    int lenth=0;
    WatermarkEmb watermarkEmb;
    @SuppressWarnings("empty-statement")
    public matable(connexion con) throws SQLException {
        initComponents();
                this.jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        Integer id;
        Integer note;
        Integer taille;
        Integer poid;
        Statement st = con.cCon.createStatement();
        ResultSet rs=st.executeQuery("SELECT * FROM Etudiant");
        ResultSetMetaData rstMT = rs.getMetaData();
        int nbrCo=rstMT.getColumnCount();
        DefaultTableModel model =  new DefaultTableModel();
        this.jTable1.setModel(model);
        while (rs.next()) {
            
                id = rs.getInt("id");
                note = rs.getInt("note");
                taille = rs.getInt("taille");
                poid = rs.getInt("poid");
                System.out.println(id+"  "+note+" "+taille+" "+poid);
                String [] tuple={id.toString(),note.toString(),taille.toString(),poid.toString()};
                model.addRow(tuple);               
        }
        this.jTable1.setModel(model);
        jTable1.setVisible(true);
        jScrollPane1.setViewportView(jTable1);
        
        
    }
    
    
    public matable(String val) {
    }

    public String [] getTuple(int idx){
        String [] tuple = new String[4];
        
        tuple[0]= jTable1.getValueAt(idx, 0).toString();
        tuple[1]= jTable1.getValueAt(idx, 1).toString();
        tuple[2]= jTable1.getValueAt(idx, 2).toString();
        tuple[3]= jTable1.getValueAt(idx, 3).toString();
        return tuple; 
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ma_table");
        setLocation(new java.awt.Point(200, 200));
        setMinimumSize(new java.awt.Dimension(691, 438));
        setResizable(false);

        jButton3.setText("retour");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, etudiantList, jTable1);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${id}"));
        columnBinding.setColumnName("Id");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${note}"));
        columnBinding.setColumnName("Note");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${taille}"));
        columnBinding.setColumnName("Taille");
        columnBinding.setColumnClass(String.class);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${poid}"));
        columnBinding.setColumnName("Poid");
        columnBinding.setColumnClass(String.class);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("coder");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 256, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addGap(66, 66, 66))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 132, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(90, 90, 90)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>                        

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {                                         
new mon_appli().setVisible(true);
this.setVisible(false);        // TODO add your handling code here:
    }                                        

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
        lenth = jTable1.getRowCount();
        String[][] tuples = new String [lenth][4];
        for(int i=0; i<lenth;i++){
            String[] tuple = this.getTuple(i);
            for(int j=0;j<4;j++){
                tuples[i][j] = tuple[j];
            }
        }
         watermarkEmb=new WatermarkEmb(tuples);
         this.setTuples(watermarkEmb.getResutas());
         String[][] tuplesResult = watermarkEmb.getResutas();
      //  System.out.println(tuplesResult );
        
    }                                        
    
    private void setTuples(String[][] tuplesResult){
        for(int i=0; i<lenth;i++){
            
            for(int j=0;j<4;j++){
                jTable2.setValueAt(tuplesResult[i][j], i, j);
            }
        }
        
    }
    /**
     * @param args the command line arguments
     */
    

    // Variables declaration - do not modify                     
    private javax.persistence.EntityManager entityManager;
    private java.util.List<tatou_numerique.Etudiant> etudiantList;
    private java.util.List<tatou_numerique.Etudiant> etudiantList1;
    private java.util.List<tatou_numerique.Etudiant> etudiantList2;
    private javax.persistence.Query etudiantQuery;
    private javax.persistence.Query etudiantQuery1;
    private javax.persistence.Query etudiantQuery2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration                   


}
