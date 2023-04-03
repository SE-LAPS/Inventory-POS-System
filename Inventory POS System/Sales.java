/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jirah.sstoreinventorymanagementsystem;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.PrintJob;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lhen
 */
public class Sales extends javax.swing.JFrame {

    /**
     * Creates new form Sales
     */
    public Sales() {
        initComponents();
        setLocationRelativeTo(null);
    }
    private String a = null;
    public static int line = 0;
    public int valid = 0;
    Connection con;
    PreparedStatement pst;
    Statement st;
    ResultSet rs;
      public java.sql.Connection Connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jirahs_store", "root", "");
            st = con.createStatement();
            return con;

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Not Connected");
            return null;
        }
      }
      private void addcart(){
    String stoccks = stocks.getText();
        String qtyy = qty_input.getText();
        int a = Integer.parseInt(stoccks);
        int b = Integer.parseInt(qtyy);
        if(stocks.getText().equals("0")){
            JOptionPane.showMessageDialog(null, "Out of Stock!");
            clear();
        }
        else if(qty_input.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Enter a valid quantity!");
        }
        else if(b > a)
        {
            JOptionPane.showMessageDialog(null, "Quantity exceeds! The stocks available is only "+a);
        }
        else{
            DefaultTableModel model = (DefaultTableModel) table.getModel();
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
    SimpleDateFormat d = new SimpleDateFormat("hh:mm a");
    Date dateobj = new Date();
    String today = sdf.format(dateobj.getTime());
    String time = d.format(dateobj.getTime());            
            String Date = today+" "+time;
            String ccode = code.getText();
            String nname = name.getText();
            String qty = qty_input.getText();
            String ppprice = price.getText();
            String ttotal = total.getText();
            String totalamt = totalamount.getText();
            float totalamont = Float.parseFloat(totalamt);
            float preamount = Float.parseFloat(ttotal);

            float sum = totalamont + preamount;
            itemupdateadd();
            String all = String.valueOf(sum);
            totalamount.setText(all);
            
            Object[] row = {Date,ccode, nname, qty, ppprice,ttotal};
            model.addRow(row);
            clear();
        }
      }
      private void clear(){
                name.setText("");
                code.setText("");
                price.setText("");
                stocks.setText("");
                qty_input.setText("");
                total.setText(""); 
                item_input.setText("");
                desc.setText("");
      }
      private void clearsales(){
           DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            totalamount1.setText("0");
            totalamount.setText("0");
            amount.setText("");
            change.setText("");
        process_panel.removeAll();
        process_panel.repaint();
        process_panel.revalidate();
        process_panel.add(compute);  
        process_panel.repaint();
        process_panel.revalidate();
    
      }

private void account(){
con = Connect();
    String sql = "Select * from accounts where ID = "+ uid.getText();
        try{
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String account = rs.getString("AccountType");
                type.setText(account);
                lsname.setText(lname);
                fnames.setText(fname);
            }
            
        }catch(Exception ex){
            System.out.println(ex);
            
        }   
}
      public void availableStocks(){
con = Connect();
    String sql = "Select * from items ORDER BY ItemName ASC";
        try{
            DefaultTableModel model = (DefaultTableModel) stocks_avl.getModel();
            model.setRowCount(0);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
                String Iname = rs.getString("ItemName");
                String Desc = rs.getString("Description");
                String Cat = rs.getString("Category");
                String Pri = rs.getString("RetailPrice");
                String Stocks = rs.getString("Available_Stocks");
                Object[] row = {Iname, Desc, Cat, Pri,Stocks};
                model.addRow(row);
            }
            
        }catch(Exception ex){
            System.out.println(ex);
            
        }    
      }
private void itemupdateremoved(){
                       String sql = "SELECT * FROM items";
        try {
            int row = table.getSelectedRow();
            String qty = table.getModel().getValueAt(row,3).toString();
            int q = Integer.parseInt(qty);
            String iddd = table.getModel().getValueAt(row,1).toString();
            int i = Integer.parseInt(iddd);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            String sign = "UPDATE `items` SET `Available_Stocks`= Available_Stocks + "+ q +" WHERE barcode = "+ i;                   
                        try {
                    pst = con.prepareStatement(sign);
                    pst.execute();
                } 
                catch (Exception ex) {
                }      
            
        } catch (Exception ex) {
        }
        }   
private void itemupdateadd(){
        con = Connect();
                       String sql = "SELECT * FROM items";
        try {
            String q = qty_input.getText();
            String i = code.getText();
            st = con.createStatement();
            rs = st.executeQuery(sql);
            String sign = "UPDATE `items` SET `Available_Stocks`= Available_Stocks - "+ q +" WHERE barcode = "+ i;                   
                        try {
                    pst = con.prepareStatement(sign);
                    pst.execute();
                } 
                catch (Exception ex) {
                }      
            
        } catch (Exception ex) {
        }
        }   
    private void insertToLogsUser(){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
    SimpleDateFormat d = new SimpleDateFormat("hh:mm a");
    Date dateobj = new Date();
    String time = d.format(dateobj.getTime());
    String today = sdf.format(dateobj.getTime());

                String sql = "Select * from logs";
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            String sign = "INSERT INTO `logs`(`Date`, `Action`, `AccountType`, `Username`)"
                    + "VALUES ('" + today+" "+time + "',' Logged Out ','Guest','"+fnames.getText()+" "+lsname.getText()+"')";                  
                        try {
                    pst = con.prepareStatement(sign);
                    pst.execute();
                        }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }       
            }      
        catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
    }
 public void insertToLogsStockOut(){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
    SimpleDateFormat d = new SimpleDateFormat("hh:mm a");
    Date dateobj = new Date();
    String time = d.format(dateobj.getTime());
    String today = sdf.format(dateobj.getTime());
    String datwe = today+" "+time;
    int cont = table.getRowCount();
           con = Connect();
                   for ( int row = 0; row <= cont; row++){
                String sql = "Select * from stockout";
                
                try{
               String ItemName = table.getModel().getValueAt(row,2).toString();
               String ItemCode = table.getModel().getValueAt(row,1).toString();
               String Quantity = table.getModel().getValueAt(row,3).toString();
            st = con.createStatement();
            rs = st.executeQuery(sql);
            String sign = "INSERT INTO `stockout`(`ItemCode`, `ItenName`, `Stockout`, `Date`, `Name`)"
                    + "VALUES ('" + ItemCode + "','"+ItemName +"','"+ Quantity+"','"+datwe+"','"+fnames.getText()+" "+lsname.getText()+"')";                  
                        try {
                    pst = con.prepareStatement(sign);
                    pst.execute();
                        }
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }     
                }     
                catch (Exception ex) {
                }
                   }
            
                   
 }
 

private void insertTosales(){
    SimpleDateFormat sdf = new SimpleDateFormat("MM");
    Date dateobj = new Date();
    String today = sdf.format(dateobj.getTime());
       int cont = table.getRowCount();
           con = Connect();
           System.out.println(cont);
                   for ( int row = 0; row <= cont; row++){
                       System.out.println(row);
               String Profit = "";
               String ap ;
               String Date = table.getModel().getValueAt(row,0).toString();
               String ItemCode = table.getModel().getValueAt(row,1).toString();
               String ItemName = table.getModel().getValueAt(row,2).toString();
               String Quantity = table.getModel().getValueAt(row,3).toString();
               String Price = table.getModel().getValueAt(row,4).toString(); 
               String Total = table.getModel().getValueAt(row,5).toString();   
            try{
               String sqql = "SELECT * FROM items WHERE barcode ="+table.getModel().getValueAt(row,1).toString();
                st = con.createStatement();
                rs = st.executeQuery(sqql);
                    while(rs.next()){
                        Profit = rs.getString("profits");
                                    }
                        float pro = Float.parseFloat(Profit);
                        float q = Float.parseFloat(Quantity);
                        float allpro = pro * q;
                        ap = String.valueOf(allpro);
                        
                String sales = "Insert into sales(Date,ItemCode,ItemName,Quantity,Price,Total,Profit,Month)"
                    + "VALUES ('" + Date + "'," + ItemCode + ",'" + ItemName + "'," + Quantity + "," + Price + "," + Total + ","+ap+","+today+")";
                                        pst = con.prepareStatement(sales);
                                        pst.execute(); 
                    }
                    catch(Exception e){
                    }
        }    
}
    public void getAllDataTo_Print() {
        printTextArea.setText("\n\n\n\n\t\tPOS SYSTEM Store\n"
                + "\t Davao City\n\t       Davao Del Sur\n"
                + "\t\t   \n\n"
                + "  =========================================\n"
                + "   " + date.getText() + " Cashier: "+fnames.getText()+" "+lsname.getText()+"\t\n"
                + "  =========================================\n"
                + "  Item Code:          Item Name: \n");
       int cont = table.getRowCount();
                   for ( int row = 0; row <= cont; row++){
               String ItemCode = table.getModel().getValueAt(row,1).toString();
               String ItemName = table.getModel().getValueAt(row,2).toString();
               String Quantity = table.getModel().getValueAt(row,3).toString();
               String Price = table.getModel().getValueAt(row,4).toString(); 
               String Total = table.getModel().getValueAt(row,5).toString();  
                printTextArea.append("  " + ItemCode + "\n                  " +ItemName + "\n\t" + Quantity + "\n\t" + Price + "\n\t"+Total+"\n");
                   }
    }
    public void printComponenet(Component component) {
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setJobName(" Print Component ");

        pj.setPrintable(new Printable() {
            public int print(Graphics pg, PageFormat pf, int pageNum) {
                if (pageNum > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2 = (Graphics2D) pg;
                g2.translate(pf.getImageableX(), pf.getImageableY());
                component.paint(g2);
                return Printable.PAGE_EXISTS;
            }
        });
        if (pj.printDialog() == false) {
            return;
        }

        try {
            pj.print();
        } catch (PrinterException ex) {
            // handle exception
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btn_items = new javax.swing.JLabel();
        btn_logout = new javax.swing.JLabel();
        btn_avl_stocks = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        type = new javax.swing.JLabel();
        lsname = new javax.swing.JLabel();
        fnames = new javax.swing.JLabel();
        btn_logout1 = new javax.swing.JLabel();
        btn_avl_stocks1 = new javax.swing.JLabel();
        btn_items1 = new javax.swing.JLabel();
        mainpanel = new javax.swing.JPanel();
        cashier_panel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        process_panel = new javax.swing.JPanel();
        compute = new javax.swing.JPanel();
        btn_process = new javax.swing.JButton();
        stocks23 = new javax.swing.JLabel();
        total = new javax.swing.JTextField();
        stocks19 = new javax.swing.JLabel();
        item_input = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        stocks26 = new javax.swing.JLabel();
        btn_addcart = new javax.swing.JLabel();
        qty_input = new javax.swing.JTextField();
        stocks27 = new javax.swing.JLabel();
        totalamount = new javax.swing.JTextField();
        btn_removecart = new javax.swing.JLabel();
        finish = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        stocks28 = new javax.swing.JLabel();
        amount = new javax.swing.JTextField();
        change = new javax.swing.JTextField();
        process = new javax.swing.JButton();
        stockin_btn8 = new javax.swing.JButton();
        stocks29 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        totalamount1 = new javax.swing.JTextField();
        stocks25 = new javax.swing.JLabel();
        jPanel32 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        stocks21 = new javax.swing.JLabel();
        stocks22 = new javax.swing.JLabel();
        stocks20 = new javax.swing.JLabel();
        stocks = new javax.swing.JTextField();
        name = new javax.swing.JTextField();
        price = new javax.swing.JTextField();
        code = new javax.swing.JTextField();
        poats = new javax.swing.JLabel();
        stocks31 = new javax.swing.JLabel();
        desc = new javax.swing.JTextField();
        stocks32 = new javax.swing.JLabel();
        avl_stocks = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        stocks_avl = new javax.swing.JTable();
        search = new javax.swing.JTextField();
        sort = new javax.swing.JComboBox<>();
        btn_sort = new javax.swing.JButton();
        stocks30 = new javax.swing.JLabel();
        totalitems = new javax.swing.JLabel();
        jPanel40 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        receipt = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        printTextArea = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        confirm = new javax.swing.JPanel();
        jPanel42 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jLabel33 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        uid = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("POS System");
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 102, 0));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_items.setBackground(new java.awt.Color(0, 153, 0));
        btn_items.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_items.setForeground(new java.awt.Color(255, 255, 255));
        btn_items.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_items.setText("Cashier");
        btn_items.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_items.setOpaque(true);
        btn_items.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_itemsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_itemsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_itemsMouseExited(evt);
            }
        });
        jPanel2.add(btn_items, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 96, 206, 58));

        btn_logout.setBackground(new java.awt.Color(0, 153, 0));
        btn_logout.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_logout.setForeground(new java.awt.Color(255, 255, 255));
        btn_logout.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_logout.setText("Logout");
        btn_logout.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_logout.setOpaque(true);
        btn_logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_logoutMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_logoutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_logoutMouseExited(evt);
            }
        });
        jPanel2.add(btn_logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 234, 206, 58));

        btn_avl_stocks.setBackground(new java.awt.Color(0, 153, 0));
        btn_avl_stocks.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_avl_stocks.setForeground(new java.awt.Color(255, 255, 255));
        btn_avl_stocks.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_avl_stocks.setText("Available Stocks");
        btn_avl_stocks.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_avl_stocks.setOpaque(true);
        btn_avl_stocks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_avl_stocksMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_avl_stocksMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_avl_stocksMouseExited(evt);
            }
        });
        jPanel2.add(btn_avl_stocks, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 165, 206, 58));

        date.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        date.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        date.setText("yyyy-mm-dd");
        jPanel2.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 0, 230, -1));

        type.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        type.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        type.setText("type");
        jPanel2.add(type, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 20, 230, -1));

        lsname.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        lsname.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lsname.setText("Lname");
        jPanel2.add(lsname, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 230, -1));

        fnames.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        fnames.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fnames.setText("Username");
        jPanel2.add(fnames, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 230, -1));

        btn_logout1.setBackground(new java.awt.Color(255, 255, 255));
        btn_logout1.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_logout1.setForeground(new java.awt.Color(255, 255, 255));
        btn_logout1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_logout1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_logout1.setOpaque(true);
        btn_logout1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_logout1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_logout1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_logout1MouseExited(evt);
            }
        });
        jPanel2.add(btn_logout1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 234, 10, 58));

        btn_avl_stocks1.setBackground(new java.awt.Color(255, 255, 255));
        btn_avl_stocks1.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_avl_stocks1.setForeground(new java.awt.Color(255, 255, 255));
        btn_avl_stocks1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_avl_stocks1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_avl_stocks1.setOpaque(true);
        btn_avl_stocks1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_avl_stocks1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_avl_stocks1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_avl_stocks1MouseExited(evt);
            }
        });
        jPanel2.add(btn_avl_stocks1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 165, 10, 58));

        btn_items1.setBackground(new java.awt.Color(255, 255, 255));
        btn_items1.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_items1.setForeground(new java.awt.Color(255, 255, 255));
        btn_items1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_items1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_items1.setOpaque(true);
        btn_items1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_items1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_items1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_items1MouseExited(evt);
            }
        });
        jPanel2.add(btn_items1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 96, 10, 58));

        mainpanel.setBackground(new java.awt.Color(0, 204, 102));
        mainpanel.setLayout(new java.awt.CardLayout());

        cashier_panel.setBackground(new java.awt.Color(0, 204, 102));
        cashier_panel.setPreferredSize(new java.awt.Dimension(866, 643));
        cashier_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Item Code", "Item", "Quantity", "Price", "Total"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        cashier_panel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(12, 13, 840, 150));

        process_panel.setLayout(new java.awt.CardLayout());

        compute.setBackground(new java.awt.Color(0, 204, 102));

        btn_process.setBackground(new java.awt.Color(255, 255, 255));
        btn_process.setFont(new java.awt.Font("Tw Cen MT", 0, 36)); // NOI18N
        btn_process.setText("PROCESS");
        btn_process.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_process.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                btn_processFocusLost(evt);
            }
        });
        btn_process.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_processMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_processMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_processMouseExited(evt);
            }
        });
        btn_process.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_processActionPerformed(evt);
            }
        });

        stocks23.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        stocks23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks23.setText("Total Amount:");
        stocks23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        total.setEditable(false);
        total.setBackground(new java.awt.Color(255, 255, 255));
        total.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        stocks19.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks19.setText("Item:");
        stocks19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        item_input.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        item_input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                item_inputKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                item_inputKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                item_inputKeyTyped(evt);
            }
        });

        stocks26.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks26.setText("Total:");
        stocks26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btn_addcart.setBackground(new java.awt.Color(0, 153, 0));
        btn_addcart.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_addcart.setForeground(new java.awt.Color(255, 255, 255));
        btn_addcart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_addcart.setText("Add to cart");
        btn_addcart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_addcart.setOpaque(true);
        btn_addcart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_addcartMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_addcartMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_addcartMouseExited(evt);
            }
        });

        qty_input.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        qty_input.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                qty_inputKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                qty_inputKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                qty_inputKeyTyped(evt);
            }
        });

        stocks27.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks27.setText("Quantity:");
        stocks27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        totalamount.setEditable(false);
        totalamount.setBackground(new java.awt.Color(255, 255, 255));
        totalamount.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        totalamount.setText("0");

        btn_removecart.setBackground(new java.awt.Color(0, 153, 0));
        btn_removecart.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_removecart.setForeground(new java.awt.Color(255, 255, 255));
        btn_removecart.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_removecart.setText("Remove from cart");
        btn_removecart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_removecart.setOpaque(true);
        btn_removecart.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_removecartMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_removecartMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_removecartMouseExited(evt);
            }
        });

        javax.swing.GroupLayout computeLayout = new javax.swing.GroupLayout(compute);
        compute.setLayout(computeLayout);
        computeLayout.setHorizontalGroup(
            computeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(computeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(computeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(computeLayout.createSequentialGroup()
                        .addComponent(stocks19, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(item_input, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(computeLayout.createSequentialGroup()
                        .addComponent(stocks27, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(qty_input, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(computeLayout.createSequentialGroup()
                        .addComponent(stocks26, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(computeLayout.createSequentialGroup()
                        .addGroup(computeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_addcart, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btn_removecart, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addComponent(btn_process, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(computeLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(computeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(computeLayout.createSequentialGroup()
                                .addComponent(stocks23, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(totalamount, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        computeLayout.setVerticalGroup(
            computeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(computeLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(computeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(computeLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(stocks23, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(totalamount, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(8, 8, 8)
                .addGroup(computeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(stocks19, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(item_input))
                .addGap(10, 10, 10)
                .addGroup(computeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(stocks27, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(qty_input, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(computeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(stocks26, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(total, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(computeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(computeLayout.createSequentialGroup()
                        .addComponent(btn_addcart, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8)
                        .addComponent(btn_removecart, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btn_process, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        process_panel.add(compute, "card2");

        finish.setBackground(new java.awt.Color(0, 204, 102));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        stocks28.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks28.setText("Change:");
        stocks28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel7.add(stocks28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 90, 40));

        amount.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        amount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amountActionPerformed(evt);
            }
        });
        amount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                amountKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                amountKeyTyped(evt);
            }
        });
        jPanel7.add(amount, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 280, 40));

        change.setEditable(false);
        change.setBackground(new java.awt.Color(255, 255, 255));
        change.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        change.setText("0");
        change.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeActionPerformed(evt);
            }
        });
        jPanel7.add(change, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 180, 280, 40));

        process.setBackground(new java.awt.Color(255, 255, 255));
        process.setText("PROCESS");
        process.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        process.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                processMouseClicked(evt);
            }
        });
        process.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                processActionPerformed(evt);
            }
        });
        jPanel7.add(process, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 240, 280, 40));

        stockin_btn8.setBackground(new java.awt.Color(255, 255, 255));
        stockin_btn8.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        stockin_btn8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/icons/Undo_48px.png"))); // NOI18N
        stockin_btn8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        stockin_btn8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockin_btn8ActionPerformed(evt);
            }
        });
        jPanel7.add(stockin_btn8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 40, 40));

        stocks29.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks29.setText("Amount:");
        stocks29.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel7.add(stocks29, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 90, 40));
        jPanel7.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 280, 10));

        totalamount1.setEditable(false);
        totalamount1.setBackground(new java.awt.Color(255, 255, 255));
        totalamount1.setFont(new java.awt.Font("Arial", 1, 36)); // NOI18N
        totalamount1.setText("0");
        jPanel7.add(totalamount1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 20, 280, 60));

        stocks25.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        stocks25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks25.setText("Total:");
        stocks25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel7.add(stocks25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 110, 40));

        javax.swing.GroupLayout finishLayout = new javax.swing.GroupLayout(finish);
        finish.setLayout(finishLayout);
        finishLayout.setHorizontalGroup(
            finishLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(finishLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
                .addContainerGap())
        );
        finishLayout.setVerticalGroup(
            finishLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, finishLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
                .addContainerGap())
        );

        process_panel.add(finish, "card3");

        cashier_panel.add(process_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, -1, -1));

        jPanel32.setBackground(new java.awt.Color(255, 255, 255));
        jPanel32.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(0, 204, 102));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Information", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 16))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        stocks21.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks21.setText("Item Name:");
        stocks21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel4.add(stocks21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 100, 40));

        stocks22.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks22.setText("Price:");
        stocks22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel4.add(stocks22, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 100, 40));

        stocks20.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks20.setText("Stocks:");
        stocks20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel4.add(stocks20, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 100, 40));

        stocks.setEditable(false);
        stocks.setBackground(new java.awt.Color(255, 255, 255));
        stocks.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel4.add(stocks, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 220, 250, 40));

        name.setEditable(false);
        name.setBackground(new java.awt.Color(255, 255, 255));
        name.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel4.add(name, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 120, 250, 40));

        price.setEditable(false);
        price.setBackground(new java.awt.Color(255, 255, 255));
        price.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel4.add(price, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 170, 250, 40));

        code.setEditable(false);
        code.setBackground(new java.awt.Color(255, 255, 255));
        code.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel4.add(code, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 70, 250, 40));

        poats.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        poats.setForeground(new java.awt.Color(0, 204, 102));
        poats.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        poats.setText("Item Code:");
        poats.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel4.add(poats, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 100, 40));

        stocks31.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks31.setText("Description:");
        stocks31.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel4.add(stocks31, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, 100, 40));

        desc.setEditable(false);
        desc.setBackground(new java.awt.Color(255, 255, 255));
        desc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel4.add(desc, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, 250, 40));

        stocks32.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks32.setText("Item Code:");
        stocks32.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel4.add(stocks32, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 100, 40));

        jPanel32.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 170, 420, 354));

        cashier_panel.add(jPanel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 530));

        mainpanel.add(cashier_panel, "card2");

        avl_stocks.setBackground(new java.awt.Color(0, 204, 102));
        avl_stocks.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        stocks_avl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Name", "Description", "Category", "Price", "Stocks"
            }
        ));
        jScrollPane2.setViewportView(stocks_avl);

        avl_stocks.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 845, 407));

        search.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        search.setText("Click to Search");
        search.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                searchFocusLost(evt);
            }
        });
        search.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                searchMouseReleased(evt);
            }
        });
        search.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchKeyReleased(evt);
            }
        });
        avl_stocks.add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 160, 30));

        sort.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        sort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sort by:", "Name", "Category" }));
        sort.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                sortMouseMoved(evt);
            }
        });
        sort.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                sortFocusLost(evt);
            }
        });
        sort.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                sortMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                sortMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                sortMouseReleased(evt);
            }
        });
        sort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortActionPerformed(evt);
            }
        });
        avl_stocks.add(sort, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 40, 130, 30));

        btn_sort.setBackground(new java.awt.Color(255, 255, 255));
        btn_sort.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        btn_sort.setText("SORT");
        btn_sort.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_sort.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                btn_sortFocusLost(evt);
            }
        });
        btn_sort.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_sortMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_sortMouseExited(evt);
            }
        });
        btn_sort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_sortActionPerformed(evt);
            }
        });
        avl_stocks.add(btn_sort, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 40, 80, 30));

        stocks30.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks30.setText("Total Items:");
        stocks30.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        avl_stocks.add(stocks30, new org.netbeans.lib.awtextra.AbsoluteConstraints(695, 40, 110, 40));

        totalitems.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        totalitems.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalitems.setText("0");
        totalitems.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        avl_stocks.add(totalitems, new org.netbeans.lib.awtextra.AbsoluteConstraints(805, 40, 50, 40));

        jPanel40.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel40Layout = new javax.swing.GroupLayout(jPanel40);
        jPanel40.setLayout(jPanel40Layout);
        jPanel40Layout.setHorizontalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 870, Short.MAX_VALUE)
        );
        jPanel40Layout.setVerticalGroup(
            jPanel40Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );

        avl_stocks.add(jPanel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 530));

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/backgrounds/3 - Copy.jpg"))); // NOI18N
        avl_stocks.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(-160, 0, 1030, 530));

        mainpanel.add(avl_stocks, "card3");

        receipt.setBackground(new java.awt.Color(0, 204, 102));
        receipt.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                receiptPropertyChange(evt);
            }
        });
        receipt.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel41.setBackground(new java.awt.Color(255, 255, 255));
        jPanel41.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jPanel41FocusGained(evt);
            }
        });
        jPanel41.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                jPanel41ComponentShown(evt);
            }
        });
        jPanel41.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jPanel41PropertyChange(evt);
            }
        });

        jScrollPane4.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N

        printTextArea.setEditable(false);
        printTextArea.setColumns(20);
        printTextArea.setFont(new java.awt.Font("Monospaced", 0, 10)); // NOI18N
        printTextArea.setRows(5);
        printTextArea.setText("\t");
        printTextArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                printTextAreaMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(printTextArea);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton1.setText("FINISH");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel41Layout = new javax.swing.GroupLayout(jPanel41);
        jPanel41.setLayout(jPanel41Layout);
        jPanel41Layout.setHorizontalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addGap(289, 289, 289)
                .addGroup(jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(301, Short.MAX_VALUE))
        );
        jPanel41Layout.setVerticalGroup(
            jPanel41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel41Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 444, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        receipt.add(jPanel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 530));

        jLabel32.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/backgrounds/3 - Copy.jpg"))); // NOI18N
        receipt.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(-160, 0, 1030, 530));

        mainpanel.add(receipt, "card3");

        confirm.setBackground(new java.awt.Color(0, 204, 102));
        confirm.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel42.setBackground(new java.awt.Color(255, 255, 255));

        jButton2.setBackground(new java.awt.Color(0, 0, 0));
        jButton2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Click here to Print");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jButton2MouseClicked(evt);
            }
        });
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel42Layout = new javax.swing.GroupLayout(jPanel42);
        jPanel42.setLayout(jPanel42Layout);
        jPanel42Layout.setHorizontalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGap(284, 284, 284)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(306, Short.MAX_VALUE))
        );
        jPanel42Layout.setVerticalGroup(
            jPanel42Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel42Layout.createSequentialGroup()
                .addGap(228, 228, 228)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(260, Short.MAX_VALUE))
        );

        confirm.add(jPanel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 530));

        jLabel33.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/backgrounds/3 - Copy.jpg"))); // NOI18N
        confirm.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(-160, 0, 1030, 530));

        mainpanel.add(confirm, "card3");

        jPanel3.setBackground(new java.awt.Color(51, 255, 51));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 0), 2));

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 1, 60)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("	POS SYSTEM ");

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 0, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Sales & Stock Control Management System");

        uid.setBackground(new java.awt.Color(51, 255, 51));
        uid.setForeground(new java.awt.Color(51, 255, 51));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1080, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(uid, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(1033, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(47, 47, 47)
                    .addComponent(uid)
                    .addContainerGap(48, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mainpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 865, Short.MAX_VALUE)
                        .addGap(3, 3, 3))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mainpanel, javax.swing.GroupLayout.PREFERRED_SIZE, 529, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(4, 4, 4))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_itemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_itemsMouseClicked
qty_input.transferFocusBackward();
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();
        mainpanel.add(cashier_panel);
        mainpanel.repaint();
        mainpanel.revalidate();
        btn_items1.setBackground(new java.awt.Color(51,255,51));
        btn_avl_stocks1.setBackground(new java.awt.Color(255,255,255));

    }//GEN-LAST:event_btn_itemsMouseClicked

    private void btn_itemsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_itemsMouseEntered
        btn_items.setBackground(new java.awt.Color(51,255,51));
        btn_items.setForeground(new java.awt.Color(0,153,0));
        btn_items.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,153,0), 2));
    }//GEN-LAST:event_btn_itemsMouseEntered

    private void btn_itemsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_itemsMouseExited
        btn_items.setBackground(new java.awt.Color(0,153,0));
        btn_items.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_itemsMouseExited

    private void btn_logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_logoutMouseClicked
        this.dispose();
        login log = new login();
        log.setVisible(true);
        insertToLogsUser();
                btn_avl_stocks.setBackground(new java.awt.Color(51,255,51));

    }//GEN-LAST:event_btn_logoutMouseClicked

    private void btn_logoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_logoutMouseEntered
        btn_logout.setBackground(new java.awt.Color(51,255,51));
        btn_logout.setForeground(new java.awt.Color(0,153,0));
        btn_logout.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,153,0), 2));
    }//GEN-LAST:event_btn_logoutMouseEntered

    private void btn_logoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_logoutMouseExited
        btn_logout.setBackground(new java.awt.Color(0,153,0));
        btn_logout.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_logoutMouseExited

    private void btn_avl_stocksMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_avl_stocksMouseClicked
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();
        mainpanel.add(avl_stocks);
        mainpanel.repaint();
        mainpanel.revalidate();
        availableStocks();
        btn_avl_stocks1.setBackground(new java.awt.Color(51,255,51));
                btn_items1.setBackground(new java.awt.Color(255,255,255));
    jPanel40.setBackground(new java.awt.Color(255,255,255,150));
            try {
          String sql = "SELECT COUNT(ID) FROM items";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery(); 
                while (rs.next()){
                    String name = rs.getString("COUNT(ID)");
                    totalitems.setText(name);
                }
        }                 catch (Exception e) {

                            }
            
    }//GEN-LAST:event_btn_avl_stocksMouseClicked

    private void btn_avl_stocksMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_avl_stocksMouseEntered
        btn_avl_stocks.setBackground(new java.awt.Color(51,255,51));
        btn_avl_stocks.setForeground(new java.awt.Color(0,153,0));
        btn_avl_stocks.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,153,0), 2));
    }//GEN-LAST:event_btn_avl_stocksMouseEntered

    private void btn_avl_stocksMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_avl_stocksMouseExited
        btn_avl_stocks.setBackground(new java.awt.Color(0,153,0));
        btn_avl_stocks.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_avl_stocksMouseExited

    private void btn_processFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btn_processFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_processFocusLost

    private void btn_processMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_processMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_processMouseEntered

    private void btn_processMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_processMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_processMouseExited

    private void btn_processActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_processActionPerformed
        if(totalamount.getText().equals("0") || totalamount.getText().equals("0.0")){
            JOptionPane.showMessageDialog(null, "Please add items to cart");
        }
        else{
            
    process_panel.removeAll();
    process_panel.repaint();
    process_panel.revalidate();
    process_panel.add(finish);  
    process_panel.repaint();
    process_panel.revalidate();
    totalamount1.setText(totalamount.getText());
    total.requestFocus();
        }
    }//GEN-LAST:event_btn_processActionPerformed

    private void item_inputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_item_inputKeyReleased
        if(item_input.getText().equals("")){
            name.setText("");
            code.setText("");
            price.setText("");
            stocks.setText("");
            qty_input.setText("");
            total.setText("");
        }
        else{
            con = Connect();
            String searchQuery = "SELECT * FROM items WHERE CONCAT(`ItemName`,`barcode`) LIKE '%" + item_input.getText() + "%'";
            try{
                st = con.createStatement();
                rs = st.executeQuery(searchQuery);
                while (rs.next()){
                    String id = rs.getString("barcode");
                    String Description = rs.getString("Description");
                    String ProductName = rs.getString("ItemName");
                    String Price = rs.getString("RetailPrice");
                    String Available_Stocks = rs.getString("Available_Stocks");
                    desc.setText(Description);
                    name.setText(ProductName);
                    code.setText(id);
                    price.setText(Price);
                    stocks.setText(Available_Stocks);
                }

            }catch(Exception ex){
                System.out.println(ex);

            }
        }
    }//GEN-LAST:event_item_inputKeyReleased

    private void item_inputKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_item_inputKeyTyped

    }//GEN-LAST:event_item_inputKeyTyped

    private void btn_addcartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_addcartMouseClicked
addcart();
    }//GEN-LAST:event_btn_addcartMouseClicked

    private void btn_addcartMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_addcartMouseEntered
        btn_addcart.setBackground(new java.awt.Color(51,255,51));
        btn_addcart.setForeground(new java.awt.Color(0,153,0));
        btn_addcart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,153,0), 2));
    }//GEN-LAST:event_btn_addcartMouseEntered

    private void btn_addcartMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_addcartMouseExited
        btn_addcart.setBackground(new java.awt.Color(0,153,0));
        btn_addcart.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_addcartMouseExited

    private void qty_inputKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qty_inputKeyReleased
        if(qty_input.getText().equals("")){
            total.setText("");
        }
        else{
            String priys = price.getText();
            String qnty = qty_input.getText();

            float p = Float.parseFloat(priys);
            float q = Float.parseFloat(qnty);

            float ttotal = p * q;
            total.setText(String.valueOf(ttotal));
        }
    }//GEN-LAST:event_qty_inputKeyReleased

    private void btn_removecartMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_removecartMouseClicked
        try{
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            itemupdateremoved();
            int row = table.getSelectedRow();
            String priccce = table.getModel().getValueAt(row,5).toString();
            float q = Float.parseFloat(priccce);
            float newsum = Float.parseFloat(totalamount.getText()) - q;
            totalamount.setText(String.valueOf(newsum));
            clear();
            model.removeRow(row);

           
        }
        catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_btn_removecartMouseClicked

    private void btn_removecartMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_removecartMouseEntered
        btn_removecart.setBackground(new java.awt.Color(51,255,51));
        btn_removecart.setForeground(new java.awt.Color(0,153,0));
        btn_removecart.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,153,0), 2));
    }//GEN-LAST:event_btn_removecartMouseEntered

    private void btn_removecartMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_removecartMouseExited
        btn_removecart.setBackground(new java.awt.Color(0,153,0));
        btn_removecart.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_removecartMouseExited

    private void amountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amountActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_amountActionPerformed

    private void changeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_changeActionPerformed

    private void processActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_processActionPerformed

    }//GEN-LAST:event_processActionPerformed

    private void stockin_btn8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockin_btn8ActionPerformed
        process_panel.removeAll();
        process_panel.repaint();
        process_panel.revalidate();
        process_panel.add(compute);
        process_panel.repaint();
        process_panel.revalidate();
    }//GEN-LAST:event_stockin_btn8ActionPerformed

    private void amountKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_amountKeyReleased

        if(amount.getText().equals("")){
        change.setText("0");
        }
    else{
    float tota = Float.parseFloat(totalamount.getText());
    float amnt = Float.parseFloat(amount.getText());
    float finished = amnt - tota;
    change.setText(String.valueOf(finished));
    
    }

    }//GEN-LAST:event_amountKeyReleased

    private void searchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchFocusLost
        search.setText("Search");
    }//GEN-LAST:event_searchFocusLost

    private void searchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchMouseClicked
        search.setText("");
    }//GEN-LAST:event_searchMouseClicked

    private void searchMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_searchMouseReleased

    private void searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyReleased
        con = Connect();
        String searchQuery = "SELECT * FROM items WHERE CONCAT(`ID`, `ItemName`, `Description`,`Category`, `Price`,`Available_Stocks`) LIKE '%" + search.getText() + "%'";
        try{
            DefaultTableModel model = (DefaultTableModel) stocks_avl.getModel();
            model.setRowCount(0);
            st = con.createStatement();
            rs = st.executeQuery(searchQuery);
            while (rs.next()){

                String ProductName = rs.getString("ItemName");
                String Description = rs.getString("Description");
                String Category = rs.getString("Category");
                String Price = rs.getString("RetailPrice");
                String Available_Stocks = rs.getString("Available_Stocks");

                Object[] row = {ProductName, Description, Category, Price,Available_Stocks};
                model.addRow(row);
            }

        }catch(Exception ex){
            System.out.println(ex);

        }
    }//GEN-LAST:event_searchKeyReleased

    private void sortMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sortMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_sortMouseMoved

    private void sortFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sortFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_sortFocusLost

    private void sortMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sortMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_sortMouseExited

    private void sortMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sortMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_sortMousePressed

    private void sortMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sortMouseReleased

    }//GEN-LAST:event_sortMouseReleased

    private void sortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sortActionPerformed

    private void btn_sortFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btn_sortFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_sortFocusLost

    private void btn_sortMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_sortMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_sortMouseEntered

    private void btn_sortMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_sortMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_sortMouseExited

    private void btn_sortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_sortActionPerformed
        if( sort.getSelectedItem().equals("Name")){
            con = Connect();
            String sql = "SELECT * FROM items ORDER BY ItemName ASC";
            try{
                DefaultTableModel model = (DefaultTableModel) stocks_avl.getModel();
                model.setRowCount(0);
                st = con.createStatement();
                rs = st.executeQuery(sql);
                while (rs.next()){
                    String ProductName = rs.getString("ItemName");
                    String Description = rs.getString("Description");
                    String Category = rs.getString("Category");
                    String Price = rs.getString("RetailPrice");
                    String Available_Stocks = rs.getString("Available_Stocks");

                    Object[] row = {ProductName, Description, Category, Price,Available_Stocks};
                    model.addRow(row);
                }

            }catch(Exception ex){
                System.out.println(ex);

            }

        }
        else if(sort.getSelectedItem().equals("Sort by:")){
            availableStocks();
        }
        else if(sort.getSelectedItem().equals("Category")){
            con = Connect();
            String sql = "SELECT * FROM items ORDER BY Category ASC";
            try{
                DefaultTableModel model = (DefaultTableModel) stocks_avl.getModel();
                model.setRowCount(0);
                st = con.createStatement();
                rs = st.executeQuery(sql);
                while (rs.next()){
                    String ProductName = rs.getString("ItemName");
                    String Description = rs.getString("Description");
                    String Category = rs.getString("Category");
                    String Price = rs.getString("RetailPrice");
                    String Available_Stocks = rs.getString("Available_Stocks");

                    Object[] row = {ProductName, Description, Category, Price,Available_Stocks};
                    model.addRow(row);
                }

            }catch(Exception ex){
                System.out.println(ex);

            }
        }
    }//GEN-LAST:event_btn_sortActionPerformed

    private void qty_inputKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qty_inputKeyTyped
     char c = evt.getKeyChar();
     if(!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE ))
     {
         evt.consume();
     }
     
    }//GEN-LAST:event_qty_inputKeyTyped

    private void amountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_amountKeyTyped
     char c = evt.getKeyChar();
     if(!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE ))
     {
         evt.consume();
     }
    }//GEN-LAST:event_amountKeyTyped

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    account();
    qty_input.transferFocusBackward();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
    SimpleDateFormat d = new SimpleDateFormat("hh:mm a");
    Date dateobj = new Date();
    String today = sdf.format(dateobj.getTime());
    date.setText(today);
            btn_items1.setBackground(new java.awt.Color(51,255,51));
        btn_avl_stocks1.setBackground(new java.awt.Color(255,255,255));
        jPanel41.hide();
    }//GEN-LAST:event_formWindowOpened

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked

    }//GEN-LAST:event_tableMouseClicked

    private void qty_inputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qty_inputKeyPressed
    if( evt.getKeyCode() == KeyEvent.VK_ENTER) {
        addcart();
        qty_input.transferFocusBackward();
    }
    }//GEN-LAST:event_qty_inputKeyPressed

    private void item_inputKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_item_inputKeyPressed
    if( evt.getKeyCode() == KeyEvent.VK_ENTER) {
        total.transferFocusBackward();
    }
    }//GEN-LAST:event_item_inputKeyPressed

    private void btn_processMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_processMouseClicked
      amount.requestFocus();
    }//GEN-LAST:event_btn_processMouseClicked

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    login.login_.setBackground(new java.awt.Color(255, 255, 255, 190));
    login.banner.setBackground(new java.awt.Color(255, 255, 255, 190));
    }//GEN-LAST:event_formWindowClosed

    private void btn_logout1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_logout1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_logout1MouseClicked

    private void btn_logout1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_logout1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_logout1MouseEntered

    private void btn_logout1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_logout1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_logout1MouseExited

    private void btn_avl_stocks1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_avl_stocks1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_avl_stocks1MouseClicked

    private void btn_avl_stocks1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_avl_stocks1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_avl_stocks1MouseEntered

    private void btn_avl_stocks1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_avl_stocks1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_avl_stocks1MouseExited

    private void btn_items1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_items1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_items1MouseClicked

    private void btn_items1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_items1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_items1MouseEntered

    private void btn_items1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_items1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_items1MouseExited

    private void processMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_processMouseClicked

        String ge  = amount.getText();
        String tal = totalamount1.getText();
        float cha = Float.parseFloat(ge);
        float to = Float.parseFloat(tal);       
            jPanel41.setBackground(new java.awt.Color(255,255,255,150));
        if (cha < to){
            JOptionPane.showMessageDialog(null, "Please enter a valid amount");
        }
        else{
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();
        mainpanel.add(confirm);
        mainpanel.repaint();
        mainpanel.revalidate();
        insertTosales();
        
        }
    }//GEN-LAST:event_processMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        insertToLogsStockOut();
        JOptionPane.showMessageDialog(null, "PROCESSED Successfully");
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
        totalamount1.setText("0");
        totalamount.setText("0");
        amount.setText("");
        change.setText("");
        process_panel.removeAll();
        process_panel.repaint();
        process_panel.revalidate();
        process_panel.add(compute);
        process_panel.repaint();
        process_panel.revalidate();
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();
        mainpanel.add(cashier_panel);
        mainpanel.repaint();
        mainpanel.revalidate();
        Toolkit tkp = printTextArea.getToolkit();
        PrintJob pjp = tkp.getPrintJob(this, null, null);
        Graphics g = pjp.getGraphics();
        printTextArea.print(g);
        g.dispose();
        pjp.end();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        jPanel41.show();
        mainpanel.removeAll();
        mainpanel.repaint();
        mainpanel.revalidate();
        mainpanel.add(receipt);
        mainpanel.repaint();
        mainpanel.revalidate();
        getAllDataTo_Print();

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jPanel41ComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jPanel41ComponentShown
               printTextArea.append("\n  _________________________________________\n");
        printTextArea.append("\n\t\tCash:  " + amount.getText());
        printTextArea.append("\n\t\tChange:  " + change.getText());
        printTextArea.append("\n\t\tTotal Ammount: " + totalamount1.getText());
        printTextArea.append("\n  _________________________________________\n");
        printTextArea.append("\n  _________Thank You ! God Bless___________\n"); 
    }//GEN-LAST:event_jPanel41ComponentShown

    private void receiptPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_receiptPropertyChange

    }//GEN-LAST:event_receiptPropertyChange

    private void jPanel41PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jPanel41PropertyChange

    }//GEN-LAST:event_jPanel41PropertyChange

    private void jButton2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MouseClicked

    }//GEN-LAST:event_jButton2MouseClicked

    private void jPanel41FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jPanel41FocusGained

    }//GEN-LAST:event_jPanel41FocusGained

    private void printTextAreaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_printTextAreaMouseClicked

    }//GEN-LAST:event_printTextAreaMouseClicked

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
            java.util.logging.Logger.getLogger(Sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Sales.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Sales().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JTextField amount;
    private javax.swing.JPanel avl_stocks;
    private javax.swing.JLabel btn_addcart;
    private javax.swing.JLabel btn_avl_stocks;
    private javax.swing.JLabel btn_avl_stocks1;
    private javax.swing.JLabel btn_items;
    private javax.swing.JLabel btn_items1;
    private javax.swing.JLabel btn_logout;
    private javax.swing.JLabel btn_logout1;
    private javax.swing.JButton btn_process;
    private javax.swing.JLabel btn_removecart;
    private javax.swing.JButton btn_sort;
    private javax.swing.JPanel cashier_panel;
    public javax.swing.JTextField change;
    private javax.swing.JTextField code;
    public javax.swing.JPanel compute;
    private javax.swing.JPanel confirm;
    private javax.swing.JLabel date;
    private javax.swing.JTextField desc;
    private javax.swing.JPanel finish;
    private javax.swing.JLabel fnames;
    private javax.swing.JTextField item_input;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    public static javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel4;
    public static javax.swing.JPanel jPanel40;
    public static javax.swing.JPanel jPanel41;
    public static javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lsname;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JTextField name;
    private javax.swing.JLabel poats;
    private javax.swing.JTextField price;
    private javax.swing.JTextArea printTextArea;
    private javax.swing.JButton process;
    public javax.swing.JPanel process_panel;
    private javax.swing.JTextField qty_input;
    private javax.swing.JPanel receipt;
    private javax.swing.JTextField search;
    private javax.swing.JComboBox<String> sort;
    private javax.swing.JButton stockin_btn8;
    private javax.swing.JTextField stocks;
    private javax.swing.JLabel stocks19;
    private javax.swing.JLabel stocks20;
    private javax.swing.JLabel stocks21;
    private javax.swing.JLabel stocks22;
    private javax.swing.JLabel stocks23;
    private javax.swing.JLabel stocks25;
    private javax.swing.JLabel stocks26;
    private javax.swing.JLabel stocks27;
    private javax.swing.JLabel stocks28;
    private javax.swing.JLabel stocks29;
    private javax.swing.JLabel stocks30;
    private javax.swing.JLabel stocks31;
    private javax.swing.JLabel stocks32;
    private javax.swing.JTable stocks_avl;
    public javax.swing.JTable table;
    private javax.swing.JTextField total;
    public javax.swing.JTextField totalamount;
    public javax.swing.JTextField totalamount1;
    private javax.swing.JLabel totalitems;
    private javax.swing.JLabel type;
    public static javax.swing.JLabel uid;
    // End of variables declaration//GEN-END:variables
}
