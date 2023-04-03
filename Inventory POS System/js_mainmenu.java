/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jirah.sstoreinventorymanagementsystem;

import java.awt.print.PrinterException;
import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.io.*;
import javax.swing.table.TableModel;
/**
 *
 * @author kram
 */
public class js_mainmenu extends javax.swing.JFrame {
    Connection con;
    PreparedStatement pst;
    Statement st;
    ResultSet rs;
    public static int salesprint = 0;
    public static int stockprint = 0;
    DefaultTableModel dm = new DefaultTableModel();
    
    public js_mainmenu() {
        initComponents();
        setLocationRelativeTo(null);
       
    }
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
  private void table1refresh_table(){
              con = Connect();
    String sql = "Select * from accounts";
        try{
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            model.setRowCount(0);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
                String idz = rs.getString("ID");
                String Username = rs.getString("Username");
                String Password = rs.getString("Password");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String AccountType = rs.getString("AccountType");
                String position = rs.getString("Position");
                String mname = rs.getString("mname");
                String age = rs.getString("Age");
                String address = rs.getString("Address");

                Object[] row = {idz,Username, Password, fname,mname, lname,AccountType,age,address,position};
                model.addRow(row);
            }
            
        }catch(Exception ex){
            System.out.println(ex);
            
        }    
  }
   private void refresh_table(){
       con = Connect();
 String sql = "Select * from items";
        try{
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
                String id = rs.getString("barcode");
                String ProductName = rs.getString("ItemName");
                String Description = rs.getString("Description");
                String Category = rs.getString("Category");
                String Price = rs.getString("Price");
                String RPrice = rs.getString("RetailPrice");
                String Available_Stocks = rs.getString("Available_Stocks");
                String profit = rs.getString("profits");
                String dam = rs.getString("damage");
              
                Object[] row = {id,ProductName, Description, Category, Price,RPrice,Available_Stocks,profit,dam};
                model.addRow(row);
            }
            
        }catch(Exception ex){
            System.out.println(ex);
            
        }
   }
   
   private void category_box_refresh(){
       category.removeAllItems();
                            try {
          String ssql = "SELECT categoryname FROM `category` ORDER BY categoryname asc";
            pst = con.prepareStatement(ssql);
            rs = pst.executeQuery();
                
                while (rs.next()){
                    String name = rs.getString("categoryname");
                    category.addItem(name);
                }
          

        }                 catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
   }
      private void salestable(){
con = Connect();
    String sql = "SELECT ItemCode, ItemName, sum(Quantity), Price, sum(Total),sum(Profit) FROM `sales` GROUP BY ItemName";
        try{
            DefaultTableModel model = (DefaultTableModel) salestable.getModel();
            model.setRowCount(0);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
                String ItemSold = rs.getString("sum(Quantity)");
                String Total = rs.getString("sum(Total)");
                String ItemCode = rs.getString("ItemCode");
                String ItemName = rs.getString("ItemName");
                String Price = rs.getString("Price");     
                String Profit = rs.getString("sum(Profit)");    
                Object[] row = {ItemCode, ItemName, ItemSold,Price,Total,Profit};
                model.addRow(row);
            }
            
        }catch(Exception ex){
            System.out.println(ex);
            
        }    
      }
      
            private void logstable(){
con = Connect();
    String sql = "SELECT * FROM logs where Action like ' Logged%' ORDER BY ID desc";
        try{
            DefaultTableModel model = (DefaultTableModel) logstable.getModel();
            model.setRowCount(0);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
                String Date = rs.getString("Date");
                String Action = rs.getString("Action");
                String AccountType = rs.getString("AccountType");
                String Ufname = rs.getString("Username");
                Object[] row = {Date, Action, AccountType, Ufname};
                model.addRow(row);
            }
            
        }catch(Exception ex){
            System.out.println(ex);
            
        }    
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
private void totalsaless(){
                 try {
          String sql = "SELECT SUM(Total),sum(Profit),count(Total) FROM sales";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery(); 
                while (rs.next()){
                    String name = rs.getString("SUM(Total)");
                    String name1 = rs.getString("sum(Profit)");
                    totalsales.setText(name);
                    totalprofit.setText(name1);
                }
        }                 catch (Exception e) {

                            }
}
    private void inv_stock(){
        con = Connect();
        String sql = "SELECT * FROM stocks ORDER BY Date DESC";
        
        try{
            DefaultTableModel model = (DefaultTableModel) stockintable.getModel();
            model.setRowCount(0);
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String IC = rs.getString("Item Code");
                String IN = rs.getString("Item Name");
                String SI = rs.getString("Stock In");
                String Date = rs.getString("Date");
                String Name = rs.getString("Name");
                Object[] row = {IC, IN, SI, Date, Name};
                model.addRow(row);
                
            }
        }catch(Exception e){
            
        }
    }
    private void inv_stockout(){
        con = Connect();
        String sql = "SELECT * FROM stockout ORDER BY Date DESC";
        
        try{
            DefaultTableModel model = (DefaultTableModel) stockouttable.getModel();
            model.setRowCount(0);
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String IC = rs.getString("ItemCode");
                String IN = rs.getString("ItenName");
                String SI = rs.getString("Stockout");
                String Date = rs.getString("Date");
                String Name = rs.getString("Name");
                Object[] row = {IC, IN, SI, Date, Name};
                model.addRow(row);
                
            }
        }catch(Exception e){
            
        }
    }

    private void insertToLogsAdmin(){
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
                    + "VALUES ('" + today+" "+time + "',' Logged Out ','Administrator','"+fnames.getText()+" "+lsname.getText()+"')";                  
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
     private void insertToLogsStockIn(){
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
    SimpleDateFormat d = new SimpleDateFormat("hh:mm a");
    Date dateobj = new Date();
    String time = d.format(dateobj.getTime());
    String today = sdf.format(dateobj.getTime());
    String ddate = today+" "+time;
                String sql = "Select * from stocks";
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            String sign = "INSERT INTO `stocks`(`Item Code`, `Item Name`, `Stock In`, `Date`, `Name`)"
                    + "VALUES ('" + itemcode_id.getText() + "','"+item_name2.getText() +"','"+ stocksadd.getText()+"','"+ddate+"','"+fnames.getText()+" "+lsname.getText()+"')";                  
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
     
     private void annual(){
    con = Connect();
    String sql = "SELECT ItemName, sum(Quantity), Price, sum(Profit),sum(Total) FROM sales  WHERE Date LIKE '"+yyearr.getText()+"%' GROUP BY ItemName ORDER BY ItemName ASC ";
        try{
            DefaultTableModel model = (DefaultTableModel) salestable3.getModel();
            model.setRowCount(0);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
         
                String ItemName = rs.getString("ItemName");
                String Quantity = rs.getString("sum(Quantity)");
                String Price = rs.getString("Price");
                String Profit = rs.getString("sum(Profit)");  
                String total = rs.getString("sum(Total)"); 
                Object[] row = {ItemName, Quantity, Price,total, Profit};
                model.addRow(row);
            }
            
        }catch(Exception ex){
            System.out.println(ex);
        }
                     try {
          String sqal = "SELECT SUM(Total),SUM(Profit) FROM sales WHERE Date LIKE '"+yyearr.getText()+"%'";
            pst = con.prepareStatement(sqal);
            rs = pst.executeQuery(); 
                while (rs.next()){
                    String name = rs.getString("SUM(Total)");
                    String name1 = rs.getString("SUM(Profit)");
                    totalsales.setText(name);
                    totalprofit.setText(name1);
                }
        }                 catch (Exception e) {

                            }  
     }
     public void exportTable(JTable table, File file) throws IOException{
         TableModel model = table.getModel();
         FileWriter out = new FileWriter(file);
         for(int i = 0; i < model.getColumnCount(); i++){
             out.write(model.getColumnName(i) + "\t");
         }
         out.write("\n");
         for(int i = 0; i < model.getRowCount(); i++){
             for (int j = 0; j < model.getColumnCount();j++){
                 out.write(model.getValueAt(i,j).toString()+"\t");
             }
             out.write("\n");
         }
         out.close();
         System.out.println("write out to:"+file);
     }
     
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btn_items = new javax.swing.JLabel();
        btn_categ = new javax.swing.JLabel();
        btn_acc = new javax.swing.JLabel();
        btn_logout = new javax.swing.JLabel();
        btn_sales = new javax.swing.JLabel();
        btn_logs = new javax.swing.JLabel();
        a1 = new javax.swing.JLabel();
        a2 = new javax.swing.JLabel();
        a3 = new javax.swing.JLabel();
        a4 = new javax.swing.JLabel();
        a5 = new javax.swing.JLabel();
        a6 = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        fnames = new javax.swing.JLabel();
        type = new javax.swing.JLabel();
        lsname = new javax.swing.JLabel();
        mainpanel = new javax.swing.JPanel();
        items_choice = new javax.swing.JPanel();
        btn_items1 = new javax.swing.JLabel();
        btn_items2 = new javax.swing.JLabel();
        jPanel26 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        items = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        stocks6 = new javax.swing.JLabel();
        stocks4 = new javax.swing.JLabel();
        stocks3 = new javax.swing.JLabel();
        stocks1 = new javax.swing.JLabel();
        stocks5 = new javax.swing.JLabel();
        categoryy = new javax.swing.JTextField();
        item_name = new javax.swing.JTextField();
        item_code = new javax.swing.JTextField();
        prays = new javax.swing.JTextField();
        status = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        desc = new javax.swing.JTextArea();
        btn_stockin = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        stocks11 = new javax.swing.JLabel();
        avlstocks1 = new javax.swing.JTextField();
        stocks19 = new javax.swing.JLabel();
        stocks26 = new javax.swing.JLabel();
        excel = new javax.swing.JLabel();
        print = new javax.swing.JLabel();
        Rprice = new javax.swing.JTextField();
        stocks41 = new javax.swing.JLabel();
        btn_damage = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        totalitems = new javax.swing.JLabel();
        stocks20 = new javax.swing.JLabel();
        search = new javax.swing.JTextField();
        sort = new javax.swing.JComboBox<>();
        btn_sort = new javax.swing.JButton();
        jPanel36 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        item_reg = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        stocks7 = new javax.swing.JLabel();
        stocks8 = new javax.swing.JLabel();
        stocks9 = new javax.swing.JLabel();
        stocks10 = new javax.swing.JLabel();
        item_name1 = new javax.swing.JTextField();
        prays1 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        desc1 = new javax.swing.JTextArea();
        category = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        stocks30 = new javax.swing.JLabel();
        rprice = new javax.swing.JTextField();
        stocks55 = new javax.swing.JLabel();
        item_barcode = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        stockin = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        stocks12 = new javax.swing.JLabel();
        stocksadd = new javax.swing.JTextField();
        stockin_btn = new javax.swing.JButton();
        stocks13 = new javax.swing.JLabel();
        item_name2 = new javax.swing.JTextField();
        stocks14 = new javax.swing.JLabel();
        categoryy1 = new javax.swing.JTextField();
        stocks15 = new javax.swing.JLabel();
        avlstocks2 = new javax.swing.JTextField();
        stocks16 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        desc2 = new javax.swing.JTextArea();
        stocks17 = new javax.swing.JLabel();
        prays2 = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        stocks18 = new javax.swing.JLabel();
        itemcode_id = new javax.swing.JLabel();
        stockin_btn6 = new javax.swing.JButton();
        jPanel34 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        cat_reg = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        catname = new javax.swing.JLabel();
        addcateg = new javax.swing.JTextField();
        stockin_btn1 = new javax.swing.JButton();
        stocks24 = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        jScrollPane5 = new javax.swing.JScrollPane();
        category_table = new javax.swing.JTable();
        stockin_btn2 = new javax.swing.JButton();
        Edit = new javax.swing.JButton();
        addcategory2 = new javax.swing.JLabel();
        addcategory3 = new javax.swing.JLabel();
        stockin_btn7 = new javax.swing.JButton();
        jPanel30 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        cat_edit = new javax.swing.JPanel();
        cat_edits = new javax.swing.JPanel();
        addcategory1 = new javax.swing.JLabel();
        addcateg1 = new javax.swing.JTextField();
        stockin_btn4 = new javax.swing.JButton();
        stockin_btn5 = new javax.swing.JButton();
        id = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        edit_item = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        stocks21 = new javax.swing.JLabel();
        stocks22 = new javax.swing.JLabel();
        stocks23 = new javax.swing.JLabel();
        stocks25 = new javax.swing.JLabel();
        item_name3 = new javax.swing.JTextField();
        prays3 = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        desc3 = new javax.swing.JTextArea();
        category1 = new javax.swing.JComboBox<>();
        edititem = new javax.swing.JButton();
        stockin_btn8 = new javax.swing.JButton();
        item_codeid = new javax.swing.JLabel();
        rrprice = new javax.swing.JTextField();
        stocks40 = new javax.swing.JLabel();
        jPanel33 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        accounts_panel = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        stocks31 = new javax.swing.JLabel();
        stocks32 = new javax.swing.JLabel();
        stocks34 = new javax.swing.JLabel();
        stocks35 = new javax.swing.JLabel();
        acc_fname = new javax.swing.JTextField();
        acc_username = new javax.swing.JTextField();
        acc_password = new javax.swing.JTextField();
        acc_lname = new javax.swing.JTextField();
        btn_edit1 = new javax.swing.JButton();
        stocks37 = new javax.swing.JLabel();
        btn_delete2 = new javax.swing.JButton();
        acc_id = new javax.swing.JTextField();
        stocks38 = new javax.swing.JLabel();
        acc_create_type1 = new javax.swing.JComboBox<>();
        acc_mname = new javax.swing.JTextField();
        stocks49 = new javax.swing.JLabel();
        acc_age = new javax.swing.JTextField();
        stocks50 = new javax.swing.JLabel();
        stocks51 = new javax.swing.JLabel();
        acc_add = new javax.swing.JTextField();
        acc_pos = new javax.swing.JComboBox<>();
        stocks52 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        table1 = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        search1 = new javax.swing.JTextField();
        totalaccounts = new javax.swing.JLabel();
        stocks39 = new javax.swing.JLabel();
        create_account = new javax.swing.JButton();
        jPanel35 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        accounts_create = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        stocks27 = new javax.swing.JLabel();
        stocks28 = new javax.swing.JLabel();
        stocks29 = new javax.swing.JLabel();
        acc_create_user = new javax.swing.JTextField();
        acc_create_fname = new javax.swing.JTextField();
        acc_create_type = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        stocks33 = new javax.swing.JLabel();
        acc_create_pass = new javax.swing.JTextField();
        acc_create_lname = new javax.swing.JTextField();
        stocks36 = new javax.swing.JLabel();
        stockin_btn10 = new javax.swing.JButton();
        stocks53 = new javax.swing.JLabel();
        acc_create_mname = new javax.swing.JTextField();
        acc_create_age = new javax.swing.JTextField();
        stocks56 = new javax.swing.JLabel();
        acc_create_pos = new javax.swing.JComboBox<>();
        stocks54 = new javax.swing.JLabel();
        acc_create_add = new javax.swing.JTextField();
        stocks57 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        sales = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        btn_salesannual = new javax.swing.JLabel();
        btn_salessummarry = new javax.swing.JLabel();
        btn_salesdaily = new javax.swing.JLabel();
        btn_salesmonthlys = new javax.swing.JLabel();
        uid1 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        searchday = new javax.swing.JButton();
        searchyear = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        searchmonth = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        totalsales = new javax.swing.JLabel();
        totalprofit = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        sales_table = new javax.swing.JPanel();
        sales_mainpanel = new javax.swing.JPanel();
        sales_summary = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        salestable = new javax.swing.JTable();
        sales_daily = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        salestable1 = new javax.swing.JTable();
        sales_monthly = new javax.swing.JPanel();
        jScrollPane12 = new javax.swing.JScrollPane();
        salestable2 = new javax.swing.JTable();
        sales_yearly = new javax.swing.JPanel();
        jScrollPane13 = new javax.swing.JScrollPane();
        salestable3 = new javax.swing.JTable();
        sales_titlepanel = new javax.swing.JPanel();
        dailysales = new javax.swing.JPanel();
        dailydate = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        summary = new javax.swing.JPanel();
        summarytitle = new javax.swing.JLabel();
        monthly = new javax.swing.JPanel();
        dailydate1 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        months = new javax.swing.JLabel();
        months1 = new javax.swing.JLabel();
        yearly = new javax.swing.JPanel();
        yyearr = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jPanel29 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        Logs = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        uid2 = new javax.swing.JLabel();
        sales_mainpanel1 = new javax.swing.JPanel();
        sales_table1 = new javax.swing.JPanel();
        jPanel25 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        logstable = new javax.swing.JTable();
        jPanel38 = new javax.swing.JPanel();
        ddate = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel24 = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        logsprint = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        damage = new javax.swing.JPanel();
        jPanel27 = new javax.swing.JPanel();
        jPanel28 = new javax.swing.JPanel();
        stocks42 = new javax.swing.JLabel();
        damagedStocks = new javax.swing.JTextField();
        stockin_btn3 = new javax.swing.JButton();
        stocks43 = new javax.swing.JLabel();
        item_name4 = new javax.swing.JTextField();
        stocks44 = new javax.swing.JLabel();
        categoryy2 = new javax.swing.JTextField();
        stocks45 = new javax.swing.JLabel();
        avlstocks3 = new javax.swing.JTextField();
        stocks46 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        desc4 = new javax.swing.JTextArea();
        stocks47 = new javax.swing.JLabel();
        prays4 = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        stocks48 = new javax.swing.JLabel();
        itemcode_id1 = new javax.swing.JLabel();
        stockin_btn9 = new javax.swing.JButton();
        jPanel32 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        reports = new javax.swing.JPanel();
        btn_items3 = new javax.swing.JLabel();
        btn_items4 = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        inventory = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        btn_stockn = new javax.swing.JLabel();
        btn_stockt = new javax.swing.JLabel();
        uid3 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        searchday1 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        sales_mainpanel2 = new javax.swing.JPanel();
        stockIN = new javax.swing.JPanel();
        jScrollPane14 = new javax.swing.JScrollPane();
        stockintable = new javax.swing.JTable();
        stockOUT = new javax.swing.JPanel();
        jScrollPane15 = new javax.swing.JScrollPane();
        stockouttable = new javax.swing.JTable();
        sales_titlepanel1 = new javax.swing.JPanel();
        summary1 = new javax.swing.JPanel();
        summarytitle1 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jPanel40 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        uid = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RAPID JS  System");
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 102, 0));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_items.setBackground(new java.awt.Color(0, 153, 0));
        btn_items.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_items.setForeground(new java.awt.Color(255, 255, 255));
        btn_items.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_items.setText("Items");
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
        jPanel2.add(btn_items, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 206, 58));

        btn_categ.setBackground(new java.awt.Color(0, 153, 0));
        btn_categ.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_categ.setForeground(new java.awt.Color(255, 255, 255));
        btn_categ.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_categ.setText("Categories");
        btn_categ.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_categ.setOpaque(true);
        btn_categ.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_categMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_categMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_categMouseExited(evt);
            }
        });
        jPanel2.add(btn_categ, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 206, 58));

        btn_acc.setBackground(new java.awt.Color(0, 153, 0));
        btn_acc.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_acc.setForeground(new java.awt.Color(255, 255, 255));
        btn_acc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_acc.setText("Accounts");
        btn_acc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_acc.setOpaque(true);
        btn_acc.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_accMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_accMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_accMouseExited(evt);
            }
        });
        jPanel2.add(btn_acc, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 300, 206, 58));

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
        jPanel2.add(btn_logout, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 440, 206, 58));

        btn_sales.setBackground(new java.awt.Color(0, 153, 0));
        btn_sales.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_sales.setForeground(new java.awt.Color(255, 255, 255));
        btn_sales.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_sales.setText("Reports");
        btn_sales.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_sales.setOpaque(true);
        btn_sales.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_salesMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_salesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_salesMouseExited(evt);
            }
        });
        jPanel2.add(btn_sales, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 206, 58));

        btn_logs.setBackground(new java.awt.Color(0, 153, 0));
        btn_logs.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_logs.setForeground(new java.awt.Color(255, 255, 255));
        btn_logs.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_logs.setText("Logs");
        btn_logs.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_logs.setOpaque(true);
        btn_logs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_logsMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_logsMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_logsMouseExited(evt);
            }
        });
        jPanel2.add(btn_logs, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 370, 206, 58));

        a1.setBackground(new java.awt.Color(255, 255, 255));
        a1.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        a1.setForeground(new java.awt.Color(255, 255, 255));
        a1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        a1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        a1.setOpaque(true);
        a1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                a1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                a1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                a1MouseExited(evt);
            }
        });
        jPanel2.add(a1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, 10, 58));

        a2.setBackground(new java.awt.Color(255, 255, 255));
        a2.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        a2.setForeground(new java.awt.Color(255, 255, 255));
        a2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        a2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        a2.setOpaque(true);
        a2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                a2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                a2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                a2MouseExited(evt);
            }
        });
        jPanel2.add(a2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 160, 10, 58));

        a3.setBackground(new java.awt.Color(255, 255, 255));
        a3.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        a3.setForeground(new java.awt.Color(255, 255, 255));
        a3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        a3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        a3.setOpaque(true);
        a3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                a3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                a3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                a3MouseExited(evt);
            }
        });
        jPanel2.add(a3, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 230, 10, 58));

        a4.setBackground(new java.awt.Color(255, 255, 255));
        a4.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        a4.setForeground(new java.awt.Color(255, 255, 255));
        a4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        a4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        a4.setOpaque(true);
        a4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                a4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                a4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                a4MouseExited(evt);
            }
        });
        jPanel2.add(a4, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 300, 10, 58));

        a5.setBackground(new java.awt.Color(255, 255, 255));
        a5.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        a5.setForeground(new java.awt.Color(255, 255, 255));
        a5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        a5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        a5.setOpaque(true);
        a5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                a5MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                a5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                a5MouseExited(evt);
            }
        });
        jPanel2.add(a5, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 370, 10, 58));

        a6.setBackground(new java.awt.Color(255, 255, 255));
        a6.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        a6.setForeground(new java.awt.Color(255, 255, 255));
        a6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        a6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        a6.setOpaque(true);
        a6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                a6MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                a6MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                a6MouseExited(evt);
            }
        });
        jPanel2.add(a6, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 440, 10, 58));

        date.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        date.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        date.setText("yyyy-mm-dd");
        jPanel2.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 0, 230, -1));

        fnames.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        fnames.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fnames.setText("Username");
        jPanel2.add(fnames, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 230, -1));

        type.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        type.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        type.setText("type");
        jPanel2.add(type, new org.netbeans.lib.awtextra.AbsoluteConstraints(2, 20, 230, -1));

        lsname.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        lsname.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lsname.setText("Lname");
        jPanel2.add(lsname, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 230, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1, 111, 230, 529));

        mainpanel.setBackground(new java.awt.Color(0, 204, 102));
        mainpanel.setLayout(new java.awt.CardLayout());

        items_choice.setBackground(new java.awt.Color(0, 102, 0));
        items_choice.setPreferredSize(new java.awt.Dimension(865, 643));
        items_choice.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                items_choiceMouseClicked(evt);
            }
        });
        items_choice.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_items1.setBackground(new java.awt.Color(0, 153, 0));
        btn_items1.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_items1.setForeground(new java.awt.Color(255, 255, 255));
        btn_items1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_items1.setText("View Items");
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
        items_choice.add(btn_items1, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 175, 361, 58));

        btn_items2.setBackground(new java.awt.Color(0, 153, 0));
        btn_items2.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_items2.setForeground(new java.awt.Color(255, 255, 255));
        btn_items2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_items2.setText("Register Items");
        btn_items2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_items2.setOpaque(true);
        btn_items2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_items2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_items2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_items2MouseExited(evt);
            }
        });
        items_choice.add(btn_items2, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 244, 361, 58));

        jPanel26.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel26Layout = new javax.swing.GroupLayout(jPanel26);
        jPanel26.setLayout(jPanel26Layout);
        jPanel26Layout.setHorizontalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 865, Short.MAX_VALUE)
        );
        jPanel26Layout.setVerticalGroup(
            jPanel26Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );

        items_choice.add(jPanel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 865, 530));

        jLabel21.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/backgrounds/3 - Copy.jpg"))); // NOI18N
        items_choice.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(-160, 0, 1025, 530));

        mainpanel.add(items_choice, "card2");

        items.setBackground(new java.awt.Color(255, 255, 255));
        items.setPreferredSize(new java.awt.Dimension(866, 643));
        items.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                itemsMouseMoved(evt);
            }
        });
        items.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(0, 153, 0));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Product Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        stocks6.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        stocks6.setForeground(new java.awt.Color(255, 255, 255));
        stocks6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks6.setText("Available Stocks:");
        stocks6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(stocks6, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 60, -1, 30));

        stocks4.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        stocks4.setForeground(new java.awt.Color(255, 255, 255));
        stocks4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks4.setText("Category:");
        stocks4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(stocks4, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 20, 90, 30));

        stocks3.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        stocks3.setForeground(new java.awt.Color(255, 255, 255));
        stocks3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks3.setText("Export:");
        stocks3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(stocks3, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 140, 100, 30));

        stocks1.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        stocks1.setForeground(new java.awt.Color(255, 255, 255));
        stocks1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks1.setText("Item Code:");
        stocks1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(stocks1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 20, 100, 30));

        stocks5.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        stocks5.setForeground(new java.awt.Color(255, 255, 255));
        stocks5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks5.setText("Based Price:");
        stocks5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(stocks5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 100, 30));

        categoryy.setEditable(false);
        categoryy.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel5.add(categoryy, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 20, 120, 30));

        item_name.setEditable(false);
        item_name.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel5.add(item_name, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 20, 170, 30));

        item_code.setEditable(false);
        item_code.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel5.add(item_code, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 20, 120, 30));

        prays.setEditable(false);
        prays.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel5.add(prays, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 60, 170, 30));

        status.setEditable(false);
        status.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel5.add(status, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 100, 120, 30));

        desc.setEditable(false);
        desc.setColumns(20);
        desc.setRows(5);
        jScrollPane2.setViewportView(desc);

        jPanel5.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 410, 30));

        btn_stockin.setBackground(new java.awt.Color(255, 255, 255));
        btn_stockin.setFont(new java.awt.Font("Tw Cen MT", 0, 15)); // NOI18N
        btn_stockin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/icons/Feed In_52px.png"))); // NOI18N
        btn_stockin.setText("STOCK IN");
        btn_stockin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_stockin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_stockinMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_stockinMouseExited(evt);
            }
        });
        btn_stockin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_stockinActionPerformed(evt);
            }
        });
        jPanel5.add(btn_stockin, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 140, 140, 30));

        btn_delete.setBackground(new java.awt.Color(255, 255, 255));
        btn_delete.setFont(new java.awt.Font("Tw Cen MT", 0, 15)); // NOI18N
        btn_delete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/icons/Trash_52px.png"))); // NOI18N
        btn_delete.setText("DELETE");
        btn_delete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_delete.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                btn_deleteFocusLost(evt);
            }
        });
        btn_delete.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_deleteMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_deleteMouseExited(evt);
            }
        });
        btn_delete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_deleteActionPerformed(evt);
            }
        });
        jPanel5.add(btn_delete, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 140, 30));

        btn_edit.setBackground(new java.awt.Color(255, 255, 255));
        btn_edit.setFont(new java.awt.Font("Tw Cen MT", 0, 15)); // NOI18N
        btn_edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/icons/Edit_52px.png"))); // NOI18N
        btn_edit.setText("EDIT");
        btn_edit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_edit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_editMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_editMouseExited(evt);
            }
        });
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });
        jPanel5.add(btn_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 140, 140, 30));

        stocks11.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        stocks11.setForeground(new java.awt.Color(255, 255, 255));
        stocks11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks11.setText("Description:");
        stocks11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(stocks11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, -1, 30));

        avlstocks1.setEditable(false);
        avlstocks1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel5.add(avlstocks1, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 60, 120, 30));

        stocks19.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        stocks19.setForeground(new java.awt.Color(255, 255, 255));
        stocks19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks19.setText("Item Name:");
        stocks19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(stocks19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 100, 30));

        stocks26.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        stocks26.setForeground(new java.awt.Color(255, 255, 255));
        stocks26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks26.setText("Status:");
        stocks26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(stocks26, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 100, 70, 30));

        excel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        excel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/icons/Microsoft Excel_48px_1.png"))); // NOI18N
        excel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        excel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                excelMouseClicked(evt);
            }
        });
        jPanel5.add(excel, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 140, 40, 30));

        print.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        print.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/icons/Print_48px.png"))); // NOI18N
        print.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        print.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                printMouseClicked(evt);
            }
        });
        jPanel5.add(print, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 140, 40, 30));

        Rprice.setEditable(false);
        Rprice.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel5.add(Rprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 60, 120, 30));

        stocks41.setFont(new java.awt.Font("Tw Cen MT", 0, 14)); // NOI18N
        stocks41.setForeground(new java.awt.Color(255, 255, 255));
        stocks41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks41.setText("Retail Price:");
        stocks41.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel5.add(stocks41, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 60, 100, 30));

        btn_damage.setBackground(new java.awt.Color(255, 255, 255));
        btn_damage.setFont(new java.awt.Font("Tw Cen MT", 0, 15)); // NOI18N
        btn_damage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/backgrounds/039-insert.png"))); // NOI18N
        btn_damage.setText("  DAMAGE");
        btn_damage.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_damage.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                btn_damageFocusLost(evt);
            }
        });
        btn_damage.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_damageMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_damageMouseExited(evt);
            }
        });
        btn_damage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_damageActionPerformed(evt);
            }
        });
        jPanel5.add(btn_damage, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 140, 30));

        items.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 330, 850, 190));

        jScrollPane1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseMoved(evt);
            }
        });

        table.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Name", "Description", "Category", "Based Price", "Retail Price", "Available Stocks", "Profit", "Damage"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                tableMouseMoved(evt);
            }
        });
        table.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tableFocusLost(evt);
            }
        });
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        table.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tablePropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(table);
        if (table.getColumnModel().getColumnCount() > 0) {
            table.getColumnModel().getColumn(0).setPreferredWidth(50);
            table.getColumnModel().getColumn(4).setPreferredWidth(70);
            table.getColumnModel().getColumn(6).setPreferredWidth(100);
        }

        items.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 850, 270));

        totalitems.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        totalitems.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalitems.setText("0");
        totalitems.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        items.add(totalitems, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 0, 130, 50));

        stocks20.setBackground(new java.awt.Color(255, 255, 255));
        stocks20.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks20.setText("Total Items:");
        stocks20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        items.add(stocks20, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 0, 110, 50));

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
        items.add(search, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 160, 30));

        sort.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        sort.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sort by:", "Name", "No.", "Category" }));
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
        items.add(sort, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 10, 130, 30));

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
        items.add(btn_sort, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 10, 80, 30));

        jPanel36.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel36Layout = new javax.swing.GroupLayout(jPanel36);
        jPanel36.setLayout(jPanel36Layout);
        jPanel36Layout.setHorizontalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 870, Short.MAX_VALUE)
        );
        jPanel36Layout.setVerticalGroup(
            jPanel36Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );

        items.add(jPanel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 530));

        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/backgrounds/3 - Copy.jpg"))); // NOI18N
        items.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(-160, 0, 1030, 530));

        mainpanel.add(items, "card4");

        item_reg.setBackground(new java.awt.Color(0, 204, 102));
        item_reg.setPreferredSize(new java.awt.Dimension(866, 643));
        item_reg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(0, 153, 0));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Register Item", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("MS Reference Sans Serif", 1, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel6.setForeground(new java.awt.Color(255, 255, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        stocks7.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks7.setForeground(new java.awt.Color(255, 255, 255));
        stocks7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks7.setText("Item Code:");
        stocks7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel6.add(stocks7, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 100, 30));

        stocks8.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks8.setForeground(new java.awt.Color(255, 255, 255));
        stocks8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks8.setText("Category:");
        stocks8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel6.add(stocks8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, 90, 30));

        stocks9.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks9.setForeground(new java.awt.Color(255, 255, 255));
        stocks9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks9.setText("Retail Price:");
        stocks9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel6.add(stocks9, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 160, 90, 30));

        stocks10.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks10.setForeground(new java.awt.Color(255, 255, 255));
        stocks10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks10.setText("Description:");
        stocks10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel6.add(stocks10, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 200, -1, 30));
        jPanel6.add(item_name1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 290, 30));

        prays1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prays1ActionPerformed(evt);
            }
        });
        prays1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                prays1KeyTyped(evt);
            }
        });
        jPanel6.add(prays1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, 100, 30));

        desc1.setColumns(20);
        desc1.setRows(5);
        jScrollPane3.setViewportView(desc1);

        jPanel6.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 200, 290, 90));

        category.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Category" }));
        category.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                categoryMouseClicked(evt);
            }
        });
        jPanel6.add(category, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 290, 30));

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/icons/Add_50px_1.png"))); // NOI18N
        jButton1.setText("REGISTER");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 300, 290, 50));

        stocks30.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks30.setForeground(new java.awt.Color(255, 255, 255));
        stocks30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks30.setText("Based Price:");
        stocks30.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel6.add(stocks30, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, 90, 30));

        rprice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rpriceActionPerformed(evt);
            }
        });
        rprice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                rpriceKeyTyped(evt);
            }
        });
        jPanel6.add(rprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 160, 100, 30));

        stocks55.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks55.setForeground(new java.awt.Color(255, 255, 255));
        stocks55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks55.setText("Item Name:");
        stocks55.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel6.add(stocks55, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 100, 30));
        jPanel6.add(item_barcode, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 40, 290, 30));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(173, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 527, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(170, 170, 170))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(137, Short.MAX_VALUE))
        );

        item_reg.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 590));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/backgrounds/3 - Copy.jpg"))); // NOI18N
        item_reg.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(-160, 0, 1030, 530));

        mainpanel.add(item_reg, "card5");

        stockin.setBackground(new java.awt.Color(0, 204, 102));
        stockin.setPreferredSize(new java.awt.Dimension(866, 643));

        jPanel7.setBackground(new java.awt.Color(51, 255, 51));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(0, 153, 0));
        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add Stocks", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("MS Reference Sans Serif", 1, 24))); // NOI18N
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        stocks12.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        stocks12.setForeground(new java.awt.Color(255, 255, 255));
        stocks12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks12.setText("Stocks:");
        stocks12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel8.add(stocks12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 140, 100, 30));

        stocksadd.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                stocksaddKeyTyped(evt);
            }
        });
        jPanel8.add(stocksadd, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 140, 250, 30));

        stockin_btn.setBackground(new java.awt.Color(255, 255, 255));
        stockin_btn.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        stockin_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/icons/Feed In_52px.png"))); // NOI18N
        stockin_btn.setText("Stock In");
        stockin_btn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        stockin_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockin_btnActionPerformed(evt);
            }
        });
        jPanel8.add(stockin_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 150, 40));

        stocks13.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 24)); // NOI18N
        stocks13.setForeground(new java.awt.Color(255, 255, 255));
        stocks13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks13.setText("Item Information");
        stocks13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel8.add(stocks13, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 300, 30));

        item_name2.setEditable(false);
        jPanel8.add(item_name2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 80, 190, 30));

        stocks14.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks14.setForeground(new java.awt.Color(255, 255, 255));
        stocks14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks14.setText("Category:");
        stocks14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel8.add(stocks14, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 120, 90, 30));

        categoryy1.setEditable(false);
        jPanel8.add(categoryy1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 120, 190, 30));

        stocks15.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks15.setForeground(new java.awt.Color(255, 255, 255));
        stocks15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks15.setText("Available Stocks:");
        stocks15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel8.add(stocks15, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 200, -1, 30));

        avlstocks2.setEditable(false);
        jPanel8.add(avlstocks2, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 200, 160, 30));

        stocks16.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks16.setForeground(new java.awt.Color(255, 255, 255));
        stocks16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks16.setText("Description:");
        stocks16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel8.add(stocks16, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 240, -1, 30));

        desc2.setEditable(false);
        desc2.setColumns(20);
        desc2.setRows(5);
        jScrollPane4.setViewportView(desc2);

        jPanel8.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 240, 190, 90));

        stocks17.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks17.setForeground(new java.awt.Color(255, 255, 255));
        stocks17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks17.setText("Price:");
        stocks17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel8.add(stocks17, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 160, 100, 30));

        prays2.setEditable(false);
        jPanel8.add(prays2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 160, 190, 30));

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel8.add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, 10, 310));

        stocks18.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks18.setForeground(new java.awt.Color(255, 255, 255));
        stocks18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks18.setText("Item Name:");
        stocks18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel8.add(stocks18, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 80, 100, 30));

        itemcode_id.setForeground(new java.awt.Color(0, 153, 0));
        jPanel8.add(itemcode_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, -1, -1));

        stockin_btn6.setBackground(new java.awt.Color(255, 255, 255));
        stockin_btn6.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        stockin_btn6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/icons/Back_48px.png"))); // NOI18N
        stockin_btn6.setText("Cancel");
        stockin_btn6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        stockin_btn6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockin_btn6ActionPerformed(evt);
            }
        });
        jPanel8.add(stockin_btn6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 150, 40));

        jPanel7.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 746, 362));

        jPanel34.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel34Layout = new javax.swing.GroupLayout(jPanel34);
        jPanel34.setLayout(jPanel34Layout);
        jPanel34Layout.setHorizontalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 870, Short.MAX_VALUE)
        );
        jPanel34Layout.setVerticalGroup(
            jPanel34Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );

        jPanel7.add(jPanel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 530));

        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/backgrounds/3 - Copy.jpg"))); // NOI18N
        jPanel7.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(-160, 0, 1030, 530));

        javax.swing.GroupLayout stockinLayout = new javax.swing.GroupLayout(stockin);
        stockin.setLayout(stockinLayout);
        stockinLayout.setHorizontalGroup(
            stockinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        stockinLayout.setVerticalGroup(
            stockinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        mainpanel.add(stockin, "card6");

        cat_reg.setBackground(new java.awt.Color(51, 255, 51));
        cat_reg.setPreferredSize(new java.awt.Dimension(866, 643));
        cat_reg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel10.setBackground(new java.awt.Color(0, 153, 0));
        jPanel10.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "New Category", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("MS Reference Sans Serif", 1, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel10.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel10MouseMoved(evt);
            }
        });
        jPanel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel10MouseEntered(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jPanel10MouseReleased(evt);
            }
        });
        jPanel10.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        catname.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        catname.setForeground(new java.awt.Color(255, 255, 255));
        catname.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        catname.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel10.add(catname, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 260, 140, 30));

        addcateg.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addcategMouseClicked(evt);
            }
        });
        jPanel10.add(addcateg, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 340, 30));

        stockin_btn1.setBackground(new java.awt.Color(255, 255, 255));
        stockin_btn1.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        stockin_btn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/icons/Trash_52px.png"))); // NOI18N
        stockin_btn1.setText("Delete");
        stockin_btn1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        stockin_btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockin_btn1ActionPerformed(evt);
            }
        });
        jPanel10.add(stockin_btn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 300, 150, 40));

        stocks24.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 24)); // NOI18N
        stocks24.setForeground(new java.awt.Color(255, 255, 255));
        stocks24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks24.setText("Category List");
        stocks24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel10.add(stocks24, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 300, 30));

        jSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel10.add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, 10, 310));

        category_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Category"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        category_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                category_tableMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(category_table);
        if (category_table.getColumnModel().getColumnCount() > 0) {
            category_table.getColumnModel().getColumn(0).setResizable(false);
        }

        jPanel10.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 80, 310, 180));

        stockin_btn2.setBackground(new java.awt.Color(255, 255, 255));
        stockin_btn2.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        stockin_btn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/icons/Enter_52px.png"))); // NOI18N
        stockin_btn2.setText("Submit");
        stockin_btn2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        stockin_btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockin_btn2ActionPerformed(evt);
            }
        });
        jPanel10.add(stockin_btn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 150, 40));

        Edit.setBackground(new java.awt.Color(255, 255, 255));
        Edit.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        Edit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/icons/Edit_52px.png"))); // NOI18N
        Edit.setText("Edit");
        Edit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditActionPerformed(evt);
            }
        });
        jPanel10.add(Edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 300, 150, 40));

        addcategory2.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        addcategory2.setForeground(new java.awt.Color(255, 255, 255));
        addcategory2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addcategory2.setText("Add Category:");
        addcategory2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel10.add(addcategory2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 140, 30));

        addcategory3.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        addcategory3.setForeground(new java.awt.Color(255, 255, 255));
        addcategory3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addcategory3.setText("Category:");
        addcategory3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel10.add(addcategory3, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 260, 140, 30));

        stockin_btn7.setBackground(new java.awt.Color(255, 255, 255));
        stockin_btn7.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        stockin_btn7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/icons/Back_48px.png"))); // NOI18N
        stockin_btn7.setText("Cancel");
        stockin_btn7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        stockin_btn7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockin_btn7ActionPerformed(evt);
            }
        });
        jPanel10.add(stockin_btn7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 150, 40));

        cat_reg.add(jPanel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 746, 362));

        jPanel30.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel30Layout = new javax.swing.GroupLayout(jPanel30);
        jPanel30.setLayout(jPanel30Layout);
        jPanel30Layout.setHorizontalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 870, Short.MAX_VALUE)
        );
        jPanel30Layout.setVerticalGroup(
            jPanel30Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );

        cat_reg.add(jPanel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 530));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/backgrounds/3 - Copy.jpg"))); // NOI18N
        cat_reg.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(-160, 0, 1030, 530));

        mainpanel.add(cat_reg, "card7");

        cat_edit.setBackground(new java.awt.Color(51, 255, 51));
        cat_edit.setPreferredSize(new java.awt.Dimension(866, 643));
        cat_edit.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cat_edits.setBackground(new java.awt.Color(0, 153, 0));
        cat_edits.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Edit Category", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("MS Reference Sans Serif", 1, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        cat_edits.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                cat_editsMouseMoved(evt);
            }
        });
        cat_edits.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        addcategory1.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        addcategory1.setForeground(new java.awt.Color(255, 255, 255));
        addcategory1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        addcategory1.setText("Edit Category:");
        addcategory1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cat_edits.add(addcategory1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 100, 140, 30));
        cat_edits.add(addcateg1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 140, 340, 30));

        stockin_btn4.setBackground(new java.awt.Color(255, 255, 255));
        stockin_btn4.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        stockin_btn4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/icons/Back_48px.png"))); // NOI18N
        stockin_btn4.setText("Cancel");
        stockin_btn4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        stockin_btn4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockin_btn4ActionPerformed(evt);
            }
        });
        cat_edits.add(stockin_btn4, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, 150, 40));

        stockin_btn5.setBackground(new java.awt.Color(255, 255, 255));
        stockin_btn5.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        stockin_btn5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/icons/Enter_52px.png"))); // NOI18N
        stockin_btn5.setText("Submit");
        stockin_btn5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        stockin_btn5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockin_btn5ActionPerformed(evt);
            }
        });
        cat_edits.add(stockin_btn5, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 190, 150, 40));

        id.setForeground(new java.awt.Color(0, 153, 0));
        cat_edits.add(id, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, -1, -1));

        cat_edit.add(cat_edits, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 80, 746, 362));

        jPanel31.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel31Layout = new javax.swing.GroupLayout(jPanel31);
        jPanel31.setLayout(jPanel31Layout);
        jPanel31Layout.setHorizontalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 870, Short.MAX_VALUE)
        );
        jPanel31Layout.setVerticalGroup(
            jPanel31Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );

        cat_edit.add(jPanel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 530));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/backgrounds/3 - Copy.jpg"))); // NOI18N
        cat_edit.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(-160, 0, 1030, 530));

        mainpanel.add(cat_edit, "card7");

        edit_item.setBackground(new java.awt.Color(51, 255, 51));
        edit_item.setPreferredSize(new java.awt.Dimension(866, 643));
        edit_item.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel11.setBackground(new java.awt.Color(51, 255, 51));
        jPanel11.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel12.setBackground(new java.awt.Color(0, 153, 0));
        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Edit Item", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("MS Reference Sans Serif", 1, 24))); // NOI18N
        jPanel12.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        stocks21.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks21.setForeground(new java.awt.Color(255, 255, 255));
        stocks21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks21.setText("Item Name:");
        stocks21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel12.add(stocks21, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, 100, 30));

        stocks22.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks22.setForeground(new java.awt.Color(255, 255, 255));
        stocks22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks22.setText("Category:");
        stocks22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel12.add(stocks22, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 100, 90, 30));

        stocks23.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks23.setForeground(new java.awt.Color(255, 255, 255));
        stocks23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks23.setText("Retail Price:");
        stocks23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel12.add(stocks23, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 140, 90, 30));

        stocks25.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks25.setForeground(new java.awt.Color(255, 255, 255));
        stocks25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks25.setText("Description:");
        stocks25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel12.add(stocks25, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, 30));
        jPanel12.add(item_name3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 320, 30));

        prays3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                prays3KeyTyped(evt);
            }
        });
        jPanel12.add(prays3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 110, 30));

        desc3.setColumns(20);
        desc3.setRows(5);
        jScrollPane6.setViewportView(desc3);

        jPanel12.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 320, 90));

        category1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Category" }));
        category1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                category1MouseClicked(evt);
            }
        });
        jPanel12.add(category1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 320, 30));

        edititem.setBackground(new java.awt.Color(255, 255, 255));
        edititem.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        edititem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/icons/Edit_52px.png"))); // NOI18N
        edititem.setText("EDIT");
        edititem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        edititem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edititemActionPerformed(evt);
            }
        });
        jPanel12.add(edititem, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 290, 320, 50));

        stockin_btn8.setBackground(new java.awt.Color(255, 255, 255));
        stockin_btn8.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        stockin_btn8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/icons/Undo_48px.png"))); // NOI18N
        stockin_btn8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        stockin_btn8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockin_btn8ActionPerformed(evt);
            }
        });
        jPanel12.add(stockin_btn8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 40, 40));

        item_codeid.setForeground(new java.awt.Color(0, 153, 0));
        jPanel12.add(item_codeid, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 320, -1, -1));

        rrprice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                rrpriceKeyTyped(evt);
            }
        });
        jPanel12.add(rrprice, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, 120, 30));

        stocks40.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks40.setForeground(new java.awt.Color(255, 255, 255));
        stocks40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks40.setText("Based Price:");
        stocks40.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel12.add(stocks40, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 140, 90, 30));

        jPanel11.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 60, 610, 390));

        jPanel33.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel33Layout = new javax.swing.GroupLayout(jPanel33);
        jPanel33.setLayout(jPanel33Layout);
        jPanel33Layout.setHorizontalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 870, Short.MAX_VALUE)
        );
        jPanel33Layout.setVerticalGroup(
            jPanel33Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );

        jPanel11.add(jPanel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 530));

        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/backgrounds/3 - Copy.jpg"))); // NOI18N
        jPanel11.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(-160, 0, 1030, 530));

        edit_item.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 530));

        mainpanel.add(edit_item, "card5");

        accounts_panel.setBackground(new java.awt.Color(255, 255, 255));
        accounts_panel.setPreferredSize(new java.awt.Dimension(866, 643));
        accounts_panel.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                accounts_panelMouseMoved(evt);
            }
        });
        accounts_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel13.setBackground(new java.awt.Color(0, 153, 0));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Account Information", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel13.setForeground(new java.awt.Color(255, 255, 255));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        stocks31.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        stocks31.setForeground(new java.awt.Color(255, 255, 255));
        stocks31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks31.setText("Account Type:");
        stocks31.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel13.add(stocks31, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 100, 100, 30));

        stocks32.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        stocks32.setForeground(new java.awt.Color(255, 255, 255));
        stocks32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks32.setText("First Name:");
        stocks32.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel13.add(stocks32, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 90, 30));

        stocks34.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        stocks34.setForeground(new java.awt.Color(255, 255, 255));
        stocks34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks34.setText("Password:");
        stocks34.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel13.add(stocks34, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 20, 80, 29));

        stocks35.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        stocks35.setForeground(new java.awt.Color(255, 255, 255));
        stocks35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks35.setText("Last Name:");
        stocks35.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel13.add(stocks35, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 60, 80, 30));

        acc_fname.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jPanel13.add(acc_fname, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 60, 140, 30));

        acc_username.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jPanel13.add(acc_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 180, 30));

        acc_password.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jPanel13.add(acc_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 20, 180, 30));

        acc_lname.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jPanel13.add(acc_lname, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 60, 180, 30));

        btn_edit1.setBackground(new java.awt.Color(255, 255, 255));
        btn_edit1.setFont(new java.awt.Font("Tw Cen MT", 0, 12)); // NOI18N
        btn_edit1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/icons/Edit_52px.png"))); // NOI18N
        btn_edit1.setText("UPDATE ACCOUNT");
        btn_edit1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_edit1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_edit1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_edit1MouseExited(evt);
            }
        });
        btn_edit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_edit1ActionPerformed(evt);
            }
        });
        jPanel13.add(btn_edit1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 140, 200, 30));

        stocks37.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        stocks37.setForeground(new java.awt.Color(255, 255, 255));
        stocks37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks37.setText("Username:");
        stocks37.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel13.add(stocks37, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 20, 80, 30));

        btn_delete2.setBackground(new java.awt.Color(255, 255, 255));
        btn_delete2.setFont(new java.awt.Font("Tw Cen MT", 0, 12)); // NOI18N
        btn_delete2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/icons/Trash_52px.png"))); // NOI18N
        btn_delete2.setText("DELETE ACCOUNT");
        btn_delete2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_delete2.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                btn_delete2FocusLost(evt);
            }
        });
        btn_delete2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_delete2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_delete2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_delete2MouseExited(evt);
            }
        });
        btn_delete2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_delete2ActionPerformed(evt);
            }
        });
        jPanel13.add(btn_delete2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 200, 30));

        acc_id.setEditable(false);
        acc_id.setBackground(new java.awt.Color(255, 255, 255));
        acc_id.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jPanel13.add(acc_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 140, 30));

        stocks38.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        stocks38.setForeground(new java.awt.Color(255, 255, 255));
        stocks38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks38.setText("ID:");
        stocks38.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel13.add(stocks38, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, 40, 30));

        acc_create_type1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Account Type:", "Administrator", "User" }));
        acc_create_type1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                acc_create_type1MouseClicked(evt);
            }
        });
        jPanel13.add(acc_create_type1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 100, 180, 30));

        acc_mname.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jPanel13.add(acc_mname, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, 180, 30));

        stocks49.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        stocks49.setForeground(new java.awt.Color(255, 255, 255));
        stocks49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks49.setText("Middle Name:");
        stocks49.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel13.add(stocks49, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 60, 110, 30));

        acc_age.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jPanel13.add(acc_age, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 100, 140, 30));

        stocks50.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        stocks50.setForeground(new java.awt.Color(255, 255, 255));
        stocks50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks50.setText("Age:");
        stocks50.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel13.add(stocks50, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 90, 30));

        stocks51.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        stocks51.setForeground(new java.awt.Color(255, 255, 255));
        stocks51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks51.setText("Address: ");
        stocks51.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel13.add(stocks51, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, 80, 30));

        acc_add.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jPanel13.add(acc_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 100, 180, 30));

        acc_pos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Cashier", "Manager" }));
        acc_pos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                acc_posMouseClicked(evt);
            }
        });
        jPanel13.add(acc_pos, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 140, 180, 30));

        stocks52.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        stocks52.setForeground(new java.awt.Color(255, 255, 255));
        stocks52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks52.setText("Position:");
        stocks52.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel13.add(stocks52, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 140, 100, 30));

        accounts_panel.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, 830, 190));

        jScrollPane8.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jScrollPane8MouseMoved(evt);
            }
        });

        table1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Username", "Password", "First Name", "Middle Name", "Last Name", "Account Type", "Age", "Address", "Position"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                table1MouseMoved(evt);
            }
        });
        table1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                table1FocusLost(evt);
            }
        });
        table1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                table1MouseClicked(evt);
            }
        });
        table1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                table1PropertyChange(evt);
            }
        });
        jScrollPane8.setViewportView(table1);
        if (table1.getColumnModel().getColumnCount() > 0) {
            table1.getColumnModel().getColumn(0).setPreferredWidth(30);
            table1.getColumnModel().getColumn(5).setPreferredWidth(70);
            table1.getColumnModel().getColumn(6).setPreferredWidth(100);
            table1.getColumnModel().getColumn(7).setPreferredWidth(30);
        }

        accounts_panel.add(jScrollPane8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 830, 240));

        jPanel14.setBackground(new java.awt.Color(255, 255, 255));
        jPanel14.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        jPanel14.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        search1.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        search1.setText("Search Account");
        search1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                search1FocusLost(evt);
            }
        });
        search1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                search1MouseClicked(evt);
            }
        });
        search1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                search1KeyReleased(evt);
            }
        });
        jPanel14.add(search1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 230, 30));

        totalaccounts.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        totalaccounts.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        totalaccounts.setText("0");
        totalaccounts.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel14.add(totalaccounts, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 10, 80, 30));

        stocks39.setFont(new java.awt.Font("Tw Cen MT", 1, 14)); // NOI18N
        stocks39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks39.setText("Total Account/s:");
        stocks39.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel14.add(stocks39, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 10, 130, 30));

        accounts_panel.add(jPanel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 520, 50));

        create_account.setBackground(new java.awt.Color(255, 255, 255));
        create_account.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        create_account.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/icons/Add User Male_40px.png"))); // NOI18N
        create_account.setText("CREATE NEW ACCOUNT");
        create_account.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 153, 0)));
        create_account.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        create_account.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                create_accountFocusLost(evt);
            }
        });
        create_account.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                create_accountMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                create_accountMouseExited(evt);
            }
        });
        create_account.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                create_accountActionPerformed(evt);
            }
        });
        accounts_panel.add(create_account, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 10, 300, 50));

        jPanel35.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel35Layout = new javax.swing.GroupLayout(jPanel35);
        jPanel35.setLayout(jPanel35Layout);
        jPanel35Layout.setHorizontalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 870, Short.MAX_VALUE)
        );
        jPanel35Layout.setVerticalGroup(
            jPanel35Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );

        accounts_panel.add(jPanel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 530));

        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/backgrounds/3 - Copy.jpg"))); // NOI18N
        accounts_panel.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(-160, 0, 1030, 530));

        mainpanel.add(accounts_panel, "card4");

        accounts_create.setBackground(new java.awt.Color(0, 204, 102));
        accounts_create.setPreferredSize(new java.awt.Dimension(866, 643));
        accounts_create.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel15.setBackground(new java.awt.Color(51, 255, 51));
        jPanel15.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel37.setBackground(new java.awt.Color(255, 255, 255));

        jPanel16.setBackground(new java.awt.Color(0, 153, 0));
        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Account Creation", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("MS Reference Sans Serif", 1, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        stocks27.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks27.setForeground(new java.awt.Color(255, 255, 255));
        stocks27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks27.setText("Username:");
        stocks27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel16.add(stocks27, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 100, 30));

        stocks28.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks28.setForeground(new java.awt.Color(255, 255, 255));
        stocks28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks28.setText("Position:");
        stocks28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel16.add(stocks28, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 160, 130, 30));

        stocks29.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks29.setForeground(new java.awt.Color(255, 255, 255));
        stocks29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks29.setText("First Name:");
        stocks29.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel16.add(stocks29, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, 100, 30));
        jPanel16.add(acc_create_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 200, 30));
        jPanel16.add(acc_create_fname, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 200, 30));

        acc_create_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Account Type:", "Administrator", "User" }));
        acc_create_type.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                acc_create_typeMouseClicked(evt);
            }
        });
        jPanel16.add(acc_create_type, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 120, 200, 30));

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/icons/Add User Male_50px.png"))); // NOI18N
        jButton2.setText("Create Account");
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel16.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 250, 250, 60));

        stocks33.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks33.setForeground(new java.awt.Color(255, 255, 255));
        stocks33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks33.setText("Password:");
        stocks33.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel16.add(stocks33, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 100, 30));
        jPanel16.add(acc_create_pass, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 200, 30));
        jPanel16.add(acc_create_lname, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 200, 200, 30));

        stocks36.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks36.setForeground(new java.awt.Color(255, 255, 255));
        stocks36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks36.setText("Last Name:");
        stocks36.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel16.add(stocks36, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 100, 30));

        stockin_btn10.setBackground(new java.awt.Color(255, 255, 255));
        stockin_btn10.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        stockin_btn10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/icons/Undo_48px.png"))); // NOI18N
        stockin_btn10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        stockin_btn10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockin_btn10ActionPerformed(evt);
            }
        });
        jPanel16.add(stockin_btn10, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 270, 40, 40));

        stocks53.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks53.setForeground(new java.awt.Color(255, 255, 255));
        stocks53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks53.setText("Middle Name:");
        stocks53.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel16.add(stocks53, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 120, 30));
        jPanel16.add(acc_create_mname, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 160, 200, 30));
        jPanel16.add(acc_create_age, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, 200, 30));

        stocks56.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks56.setForeground(new java.awt.Color(255, 255, 255));
        stocks56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks56.setText("Age:");
        stocks56.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel16.add(stocks56, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 80, 120, 30));

        acc_create_pos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select", "Cashier", "Manager", "Owner" }));
        acc_create_pos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                acc_create_posMouseClicked(evt);
            }
        });
        jPanel16.add(acc_create_pos, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 160, 200, 30));

        stocks54.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks54.setForeground(new java.awt.Color(255, 255, 255));
        stocks54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks54.setText("Account Type:");
        stocks54.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel16.add(stocks54, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 120, 130, 30));
        jPanel16.add(acc_create_add, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 40, 200, 30));

        stocks57.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks57.setForeground(new java.awt.Color(255, 255, 255));
        stocks57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks57.setText("Address:");
        stocks57.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel16.add(stocks57, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 40, 120, 30));

        javax.swing.GroupLayout jPanel37Layout = new javax.swing.GroupLayout(jPanel37);
        jPanel37.setLayout(jPanel37Layout);
        jPanel37Layout.setHorizontalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 690, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );
        jPanel37Layout.setVerticalGroup(
            jPanel37Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel37Layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        jPanel15.add(jPanel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 530));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/backgrounds/3 - Copy.jpg"))); // NOI18N
        jPanel15.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(-160, 0, 1030, 530));

        accounts_create.add(jPanel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 530));

        mainpanel.add(accounts_create, "card5");

        sales.setBackground(new java.awt.Color(0, 204, 102));
        sales.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_salesannual.setBackground(new java.awt.Color(0, 153, 0));
        btn_salesannual.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_salesannual.setForeground(new java.awt.Color(255, 255, 255));
        btn_salesannual.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_salesannual.setText("Annual Sales");
        btn_salesannual.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btn_salesannual.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_salesannual.setOpaque(true);
        btn_salesannual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_salesannualMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_salesannualMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_salesannualMouseExited(evt);
            }
        });
        jPanel17.add(btn_salesannual, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 10, 160, 50));

        btn_salessummarry.setBackground(new java.awt.Color(0, 153, 0));
        btn_salessummarry.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_salessummarry.setForeground(new java.awt.Color(255, 255, 255));
        btn_salessummarry.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_salessummarry.setText("Sales Summary");
        btn_salessummarry.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btn_salessummarry.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_salessummarry.setOpaque(true);
        btn_salessummarry.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_salessummarryMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_salessummarryMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_salessummarryMouseExited(evt);
            }
        });
        jPanel17.add(btn_salessummarry, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 160, 50));

        btn_salesdaily.setBackground(new java.awt.Color(0, 153, 0));
        btn_salesdaily.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_salesdaily.setForeground(new java.awt.Color(255, 255, 255));
        btn_salesdaily.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_salesdaily.setText("Daily Sales");
        btn_salesdaily.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btn_salesdaily.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_salesdaily.setOpaque(true);
        btn_salesdaily.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_salesdailyMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_salesdailyMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_salesdailyMouseExited(evt);
            }
        });
        jPanel17.add(btn_salesdaily, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 160, 50));

        btn_salesmonthlys.setBackground(new java.awt.Color(0, 153, 0));
        btn_salesmonthlys.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_salesmonthlys.setForeground(new java.awt.Color(255, 255, 255));
        btn_salesmonthlys.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_salesmonthlys.setText("Monthly Sales");
        btn_salesmonthlys.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btn_salesmonthlys.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_salesmonthlys.setOpaque(true);
        btn_salesmonthlys.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_salesmonthlysMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_salesmonthlysMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_salesmonthlysMouseExited(evt);
            }
        });
        jPanel17.add(btn_salesmonthlys, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 160, 50));

        uid1.setBackground(new java.awt.Color(51, 255, 51));
        uid1.setForeground(new java.awt.Color(51, 255, 51));
        jPanel17.add(uid1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel20.setBackground(new java.awt.Color(51, 255, 51));
        jPanel20.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        searchday.setBackground(new java.awt.Color(255, 255, 255));
        searchday.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        searchday.setText("Select Date");
        searchday.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchdayActionPerformed(evt);
            }
        });
        jPanel20.add(searchday, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 130, 30));

        searchyear.setBackground(new java.awt.Color(255, 255, 255));
        searchyear.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        searchyear.setText("Select Year");
        searchyear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchyearActionPerformed(evt);
            }
        });
        jPanel20.add(searchyear, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 130, 30));

        jButton5.setText("Search");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel20.add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, -1, 30));

        jLabel7.setFont(new java.awt.Font("Tw Cen MT", 1, 20)); // NOI18N
        jLabel7.setText("Php.");
        jPanel20.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 0, 40, 50));

        searchmonth.setBackground(new java.awt.Color(255, 255, 255));
        searchmonth.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        searchmonth.setText("Select Month");
        searchmonth.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchmonthActionPerformed(evt);
            }
        });
        jPanel20.add(searchmonth, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 130, 30));

        jLabel12.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("Total Sales:");
        jPanel20.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 100, 50));

        jLabel11.setFont(new java.awt.Font("Tw Cen MT", 0, 20)); // NOI18N
        jLabel11.setText("Total Profit:");
        jPanel20.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 0, -1, 50));

        totalsales.setFont(new java.awt.Font("Tw Cen MT", 1, 20)); // NOI18N
        totalsales.setText("00.00");
        jPanel20.add(totalsales, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 0, 100, 50));

        totalprofit.setFont(new java.awt.Font("Tw Cen MT", 1, 20)); // NOI18N
        totalprofit.setText("00.00");
        jPanel20.add(totalprofit, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 0, 100, 50));

        jLabel8.setFont(new java.awt.Font("Tw Cen MT", 1, 20)); // NOI18N
        jLabel8.setText("Php.");
        jPanel20.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 0, 40, 50));

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/backgrounds/Print_48px.png"))); // NOI18N
        jButton3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 51), 1, true));
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton3.setIconTextGap(20);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel20.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 10, 30, 30));

        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/backgrounds/Microsoft Excel_48px_1.png"))); // NOI18N
        jButton9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel20.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 10, 30, 30));

        jPanel17.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 865, 50));

        sales_table.setLayout(new java.awt.CardLayout());
        jPanel17.add(sales_table, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        sales_mainpanel.setLayout(new java.awt.CardLayout());

        sales_summary.setBackground(new java.awt.Color(255, 255, 255));
        sales_summary.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        salestable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Code", "Item Name", "Item Sold", "Price", "Total", "Profit"
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
        jScrollPane7.setViewportView(salestable);

        sales_summary.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 865, 330));

        sales_mainpanel.add(sales_summary, "card4");

        sales_daily.setBackground(new java.awt.Color(255, 255, 255));
        sales_daily.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        salestable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Transaction No.", "Date", "Item Code", "Item Name", "Item Sold", "Price", "Total", "Profit"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane11.setViewportView(salestable1);

        sales_daily.add(jScrollPane11, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 865, 330));

        sales_mainpanel.add(sales_daily, "card4");

        sales_monthly.setBackground(new java.awt.Color(255, 255, 255));
        sales_monthly.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        salestable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Code", "Item Name", "Total Item Sold", "Price", "Total", "Total Profit"
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
        jScrollPane12.setViewportView(salestable2);
        if (salestable2.getColumnModel().getColumnCount() > 0) {
            salestable2.getColumnModel().getColumn(3).setHeaderValue("Price");
            salestable2.getColumnModel().getColumn(4).setHeaderValue("Total");
            salestable2.getColumnModel().getColumn(5).setHeaderValue("Total Profit");
        }

        sales_monthly.add(jScrollPane12, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 865, 330));

        sales_mainpanel.add(sales_monthly, "card4");

        sales_yearly.setBackground(new java.awt.Color(255, 255, 255));
        sales_yearly.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        salestable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Name", "Total Item Sold", "Price", "Total", "Total Profit"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane13.setViewportView(salestable3);

        sales_yearly.add(jScrollPane13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 865, 330));

        sales_mainpanel.add(sales_yearly, "card4");

        jPanel17.add(sales_mainpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 865, 330));

        sales_titlepanel.setLayout(new java.awt.CardLayout());

        dailysales.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dailydate.setBackground(new java.awt.Color(51, 255, 51));
        dailydate.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        dailysales.add(dailydate, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 0, 220, 40));

        jLabel10.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel10.setText("List of Sales as of:");
        dailysales.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 150, 40));

        sales_titlepanel.add(dailysales, "card2");

        summary.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        summarytitle.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        summarytitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        summarytitle.setText("SUMMARY OF SALES AS OF");
        summary.add(summarytitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 40));

        sales_titlepanel.add(summary, "card2");

        monthly.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dailydate1.setBackground(new java.awt.Color(51, 255, 51));
        dailydate1.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        monthly.add(dailydate1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 220, 40));

        jLabel13.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel13.setText("Monthly Sales of :");
        monthly.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 40));

        months.setForeground(new java.awt.Color(240, 240, 240));
        monthly.add(months, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 70, 20));

        months1.setForeground(new java.awt.Color(240, 240, 240));
        monthly.add(months1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, 70, 20));

        sales_titlepanel.add(monthly, "card2");

        yearly.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        yyearr.setBackground(new java.awt.Color(51, 255, 51));
        yyearr.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        yearly.add(yyearr, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 0, 220, 40));

        jLabel14.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jLabel14.setText("Yearly Sales of: ");
        jLabel14.setToolTipText("");
        yearly.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 40));

        sales_titlepanel.add(yearly, "card2");

        jPanel17.add(sales_titlepanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 810, 40));
        jPanel17.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 810, 10));

        jPanel29.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel29Layout = new javax.swing.GroupLayout(jPanel29);
        jPanel29.setLayout(jPanel29Layout);
        jPanel29Layout.setHorizontalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 870, Short.MAX_VALUE)
        );
        jPanel29Layout.setVerticalGroup(
            jPanel29Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );

        jPanel17.add(jPanel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 530));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/backgrounds/3 - Copy.jpg"))); // NOI18N
        jPanel17.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(-160, 0, 1030, 530));

        sales.add(jPanel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 865, 530));

        mainpanel.add(sales, "card11");

        Logs.setBackground(new java.awt.Color(0, 102, 0));

        jPanel22.setBackground(new java.awt.Color(255, 255, 255));
        jPanel22.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uid2.setBackground(new java.awt.Color(51, 255, 51));
        uid2.setForeground(new java.awt.Color(51, 255, 51));
        jPanel22.add(uid2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        sales_mainpanel1.setLayout(new java.awt.CardLayout());

        sales_table1.setLayout(new java.awt.CardLayout());

        jPanel25.setBackground(new java.awt.Color(255, 255, 255));
        jPanel25.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        logstable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Action", "Account Type", "Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane9.setViewportView(logstable);

        jPanel25.add(jScrollPane9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 865, 380));

        sales_table1.add(jPanel25, "card4");

        sales_mainpanel1.add(sales_table1, "card3");

        jPanel22.add(sales_mainpanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, 865, 380));

        jPanel38.setBackground(new java.awt.Color(255, 255, 255));
        jPanel38.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        ddate.setBackground(new java.awt.Color(51, 255, 51));
        ddate.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        ddate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jPanel38.add(ddate, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 780, 40));
        jPanel38.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 780, 10));
        jPanel38.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 10, 780, 10));

        jPanel24.setBackground(new java.awt.Color(51, 255, 51));
        jPanel24.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jButton4.setText("Print");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel24.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 10, 120, -1));

        logsprint.setBackground(new java.awt.Color(255, 255, 255));
        logsprint.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        logsprint.setText("Clear");
        logsprint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logsprintActionPerformed(evt);
            }
        });
        jPanel24.add(logsprint, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, 120, 30));

        jPanel38.add(jPanel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 870, 50));

        jPanel22.add(jPanel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 865, 530));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/backgrounds/3 - Copy.jpg"))); // NOI18N
        jPanel22.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(-160, 0, 1030, 530));

        javax.swing.GroupLayout LogsLayout = new javax.swing.GroupLayout(Logs);
        Logs.setLayout(LogsLayout);
        LogsLayout.setHorizontalGroup(
            LogsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(LogsLayout.createSequentialGroup()
                .addComponent(jPanel22, javax.swing.GroupLayout.PREFERRED_SIZE, 865, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 278, Short.MAX_VALUE))
        );
        LogsLayout.setVerticalGroup(
            LogsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        mainpanel.add(Logs, "card11");

        damage.setBackground(new java.awt.Color(0, 204, 102));
        damage.setPreferredSize(new java.awt.Dimension(866, 643));

        jPanel27.setBackground(new java.awt.Color(51, 255, 51));
        jPanel27.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel28.setBackground(new java.awt.Color(0, 153, 0));
        jPanel28.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Damaged Item", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("MS Reference Sans Serif", 1, 24), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel28.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        stocks42.setFont(new java.awt.Font("Tw Cen MT", 1, 18)); // NOI18N
        stocks42.setForeground(new java.awt.Color(255, 255, 255));
        stocks42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks42.setText("Enter Damage Item:");
        stocks42.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel28.add(stocks42, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 160, 30));

        damagedStocks.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                damagedStocksKeyTyped(evt);
            }
        });
        jPanel28.add(damagedStocks, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 330, 30));

        stockin_btn3.setBackground(new java.awt.Color(255, 255, 255));
        stockin_btn3.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        stockin_btn3.setText("Enter");
        stockin_btn3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        stockin_btn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockin_btn3ActionPerformed(evt);
            }
        });
        jPanel28.add(stockin_btn3, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 180, 150, 40));

        stocks43.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 24)); // NOI18N
        stocks43.setForeground(new java.awt.Color(255, 255, 255));
        stocks43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks43.setText("Item Information");
        stocks43.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel28.add(stocks43, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 300, 30));

        item_name4.setEditable(false);
        jPanel28.add(item_name4, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 80, 190, 30));

        stocks44.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks44.setForeground(new java.awt.Color(255, 255, 255));
        stocks44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks44.setText("Category:");
        stocks44.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel28.add(stocks44, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 120, 90, 30));

        categoryy2.setEditable(false);
        jPanel28.add(categoryy2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 120, 190, 30));

        stocks45.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks45.setForeground(new java.awt.Color(255, 255, 255));
        stocks45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks45.setText("Available Stocks:");
        stocks45.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel28.add(stocks45, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 200, -1, 30));

        avlstocks3.setEditable(false);
        jPanel28.add(avlstocks3, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 200, 160, 30));

        stocks46.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks46.setForeground(new java.awt.Color(255, 255, 255));
        stocks46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks46.setText("Description:");
        stocks46.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel28.add(stocks46, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 240, -1, 30));

        desc4.setEditable(false);
        desc4.setColumns(20);
        desc4.setRows(5);
        jScrollPane10.setViewportView(desc4);

        jPanel28.add(jScrollPane10, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 240, 190, 90));

        stocks47.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks47.setForeground(new java.awt.Color(255, 255, 255));
        stocks47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks47.setText("Price:");
        stocks47.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel28.add(stocks47, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 160, 100, 30));

        prays4.setEditable(false);
        jPanel28.add(prays4, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 160, 190, 30));

        jSeparator8.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel28.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, 10, 310));

        stocks48.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        stocks48.setForeground(new java.awt.Color(255, 255, 255));
        stocks48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        stocks48.setText("Item Name:");
        stocks48.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel28.add(stocks48, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 80, 100, 30));

        itemcode_id1.setForeground(new java.awt.Color(0, 153, 0));
        jPanel28.add(itemcode_id1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, -1, -1));

        stockin_btn9.setBackground(new java.awt.Color(255, 255, 255));
        stockin_btn9.setFont(new java.awt.Font("MS Reference Sans Serif", 1, 18)); // NOI18N
        stockin_btn9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/icons/Back_48px.png"))); // NOI18N
        stockin_btn9.setText("Cancel");
        stockin_btn9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        stockin_btn9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stockin_btn9ActionPerformed(evt);
            }
        });
        jPanel28.add(stockin_btn9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 180, 150, 40));

        jPanel27.add(jPanel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 90, 746, 362));

        jPanel32.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel32Layout = new javax.swing.GroupLayout(jPanel32);
        jPanel32.setLayout(jPanel32Layout);
        jPanel32Layout.setHorizontalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 870, Short.MAX_VALUE)
        );
        jPanel32Layout.setVerticalGroup(
            jPanel32Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );

        jPanel27.add(jPanel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 530));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/backgrounds/3 - Copy.jpg"))); // NOI18N
        jPanel27.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(-160, 0, 1030, 530));

        javax.swing.GroupLayout damageLayout = new javax.swing.GroupLayout(damage);
        damage.setLayout(damageLayout);
        damageLayout.setHorizontalGroup(
            damageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, 867, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        damageLayout.setVerticalGroup(
            damageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel27, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        mainpanel.add(damage, "card6");

        reports.setBackground(new java.awt.Color(0, 102, 0));
        reports.setPreferredSize(new java.awt.Dimension(865, 643));
        reports.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reportsMouseClicked(evt);
            }
        });
        reports.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_items3.setBackground(new java.awt.Color(0, 153, 0));
        btn_items3.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_items3.setForeground(new java.awt.Color(255, 255, 255));
        btn_items3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_items3.setText("Inventory Report");
        btn_items3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_items3.setOpaque(true);
        btn_items3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_items3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_items3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_items3MouseExited(evt);
            }
        });
        reports.add(btn_items3, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 175, 361, 58));

        btn_items4.setBackground(new java.awt.Color(0, 153, 0));
        btn_items4.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_items4.setForeground(new java.awt.Color(255, 255, 255));
        btn_items4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_items4.setText("Sales Report");
        btn_items4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_items4.setOpaque(true);
        btn_items4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_items4MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_items4MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_items4MouseExited(evt);
            }
        });
        reports.add(btn_items4, new org.netbeans.lib.awtextra.AbsoluteConstraints(252, 244, 361, 58));

        jPanel39.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel39Layout = new javax.swing.GroupLayout(jPanel39);
        jPanel39.setLayout(jPanel39Layout);
        jPanel39Layout.setHorizontalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 865, Short.MAX_VALUE)
        );
        jPanel39Layout.setVerticalGroup(
            jPanel39Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 530, Short.MAX_VALUE)
        );

        reports.add(jPanel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 865, 530));

        jLabel23.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/backgrounds/3 - Copy.jpg"))); // NOI18N
        reports.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(-160, 0, 1025, 530));

        mainpanel.add(reports, "card2");

        inventory.setBackground(new java.awt.Color(0, 204, 102));
        inventory.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_stockn.setBackground(new java.awt.Color(0, 153, 0));
        btn_stockn.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_stockn.setForeground(new java.awt.Color(255, 255, 255));
        btn_stockn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_stockn.setText("Stock In");
        btn_stockn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btn_stockn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_stockn.setOpaque(true);
        btn_stockn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_stocknMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_stocknMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_stocknMouseExited(evt);
            }
        });
        jPanel18.add(btn_stockn, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 10, 160, 50));

        btn_stockt.setBackground(new java.awt.Color(0, 153, 0));
        btn_stockt.setFont(new java.awt.Font("Tw Cen MT", 0, 24)); // NOI18N
        btn_stockt.setForeground(new java.awt.Color(255, 255, 255));
        btn_stockt.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btn_stockt.setText("Stock Out");
        btn_stockt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        btn_stockt.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_stockt.setOpaque(true);
        btn_stockt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_stocktMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_stocktMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_stocktMouseExited(evt);
            }
        });
        jPanel18.add(btn_stockt, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 160, 50));

        uid3.setBackground(new java.awt.Color(51, 255, 51));
        uid3.setForeground(new java.awt.Color(51, 255, 51));
        jPanel18.add(uid3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel21.setBackground(new java.awt.Color(51, 255, 51));
        jPanel21.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        searchday1.setBackground(new java.awt.Color(255, 255, 255));
        searchday1.setFont(new java.awt.Font("Tw Cen MT", 0, 16)); // NOI18N
        searchday1.setText("Select Date");
        searchday1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchday1ActionPerformed(evt);
            }
        });
        jPanel21.add(searchday1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 130, 30));

        jButton6.setText("Search");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel21.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 10, -1, 30));

        jButton7.setBackground(new java.awt.Color(255, 255, 255));
        jButton7.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jButton7.setText("Export To Excel");
        jButton7.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 51), 1, true));
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.setIconTextGap(20);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel21.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, 130, 30));

        jButton8.setBackground(new java.awt.Color(255, 255, 255));
        jButton8.setFont(new java.awt.Font("Tw Cen MT", 0, 18)); // NOI18N
        jButton8.setText("Print");
        jButton8.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(51, 255, 51), 1, true));
        jButton8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton8.setIconTextGap(20);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel21.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 10, 90, 30));

        jPanel18.add(jPanel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 865, 50));

        sales_mainpanel2.setLayout(new java.awt.CardLayout());

        stockIN.setBackground(new java.awt.Color(255, 255, 255));
        stockIN.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        stockintable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Code", "Item Name", "Stocked In", "Date", "Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane14.setViewportView(stockintable);

        stockIN.add(jScrollPane14, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 865, 330));

        sales_mainpanel2.add(stockIN, "card4");

        stockOUT.setBackground(new java.awt.Color(255, 255, 255));
        stockOUT.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        stockouttable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Code", "Item Name", "Stock Out", "Date", "Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane15.setViewportView(stockouttable);

        stockOUT.add(jScrollPane15, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 865, 330));

        sales_mainpanel2.add(stockOUT, "card4");

        jPanel18.add(sales_mainpanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 865, 330));

        sales_titlepanel1.setLayout(new java.awt.CardLayout());

        summary1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        summarytitle1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        summarytitle1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        summarytitle1.setText("date");
        summary1.add(summarytitle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 810, 40));

        sales_titlepanel1.add(summary1, "card2");

        jPanel18.add(sales_titlepanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, 810, 40));
        jPanel18.add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 810, 10));

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

        jPanel18.add(jPanel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 870, 530));

        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/jirah/backgrounds/3 - Copy.jpg"))); // NOI18N
        jPanel18.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(-160, 0, 1030, 530));

        inventory.add(jPanel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 865, 530));

        mainpanel.add(inventory, "card11");

        jPanel1.add(mainpanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(233, 111, -1, 529));

        jPanel3.setBackground(new java.awt.Color(51, 255, 51));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 0), 2));

        jLabel1.setFont(new java.awt.Font("Tw Cen MT", 1, 60)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("POS System ");

        jLabel2.setFont(new java.awt.Font("Tw Cen MT", 0, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 0));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Sales & Stock Control Management System");

        uid.setBackground(new java.awt.Color(51, 255, 51));
        uid.setForeground(new java.awt.Color(51, 255, 51));
        uid.setText("jLabel5");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(uid)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(uid))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 105));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1102, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
    refresh_table();
    account();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
    SimpleDateFormat d = new SimpleDateFormat("hh:mm a");
    Date dateobj = new Date();
    String today = sdf.format(dateobj.getTime());
    date.setText(today);
        jPanel26.setBackground(new java.awt.Color(255,255,255,150));
        a1.setBackground(new java.awt.Color(51,255,51));
    }//GEN-LAST:event_formWindowOpened

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked
        try{
            int row = table.getSelectedRow();
            String itemcode = (table.getModel().getValueAt(row,0).toString());
            String itemname = (table.getModel().getValueAt(row,1).toString());
            String description = (table.getModel().getValueAt(row,2).toString());
            String categaory = (table.getModel().getValueAt(row,3).toString());
            String price = (table.getModel().getValueAt(row,4).toString());
            String Rpprice = (table.getModel().getValueAt(row,5).toString());
            String avl_stocks = (table.getModel().getValueAt(row,6).toString());
            item_code.setText(itemcode);
            item_name.setText(itemname);
            categoryy.setText(categaory);
            desc.setText(description);
            avlstocks1.setText(avl_stocks);
            prays.setText(price);
            Rprice.setText(Rpprice);
            if(avlstocks1.getText().equals("0")){
                status.setText("Not Available");
                status.setForeground(new java.awt.Color(255, 0, 0)); 
            }
            else{
                status.setText("Available");
                status.setForeground(new java.awt.Color(0, 0, 0)); 
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_tableMouseClicked

    private void btn_stockinMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_stockinMouseEntered
        btn_stockin.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_stockinMouseEntered
    private void btn_stockinMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_stockinMouseExited
        btn_stockin.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_stockinMouseExited

    private void btn_deleteMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_deleteMouseEntered
        btn_delete.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_deleteMouseEntered

    private void btn_deleteMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_deleteMouseExited
        btn_delete.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_deleteMouseExited

    private void btn_editMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_editMouseEntered
        btn_edit.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_editMouseEntered

    private void btn_editMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_editMouseExited
        btn_edit.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_editMouseExited

    private void btn_stockinActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_stockinActionPerformed
    if(status.getText().equals("")){
        JOptionPane.showMessageDialog(null, "Select item to add stock");
    }  
    else{    
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(stockin);  
    mainpanel.repaint();
    mainpanel.revalidate();
    ///
    item_name2.setText(item_name.getText());
    categoryy1.setText(categoryy.getText());
    prays2.setText(prays.getText());
    avlstocks2.setText(avlstocks1.getText());
    desc2.setText(desc.getText());
    itemcode_id.setText(item_code.getText());
    //
    item_name.setText("");
   categoryy.setText("");
   prays.setText("");
   avlstocks1.setText("");
   desc.setText("");
   item_code.setText("");
   status.setText("");
   Rprice.setText("");
       jPanel34.setBackground(new java.awt.Color(255,255,255,150));
    }
    }//GEN-LAST:event_btn_stockinActionPerformed

    private void stockin_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockin_btnActionPerformed
con = Connect();
        if (stocksadd.getText().equals("")){
        JOptionPane.showMessageDialog(null, "Type valid quantity");
    }  
    else{
      String add =avlstocks2.getText(); 
      int a = Integer.parseInt(add);
      String b = stocksadd.getText();
      int c = Integer.parseInt(b);
      int sum = a + c;
      String addd = String.valueOf(sum);
      String sql = "SELECT * FROM items";
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            String sign = "UPDATE `items` SET `Available_Stocks`= '"+ addd +"' WHERE barcode = "+ itemcode_id.getText();
            int x = JOptionPane.showConfirmDialog(null, "STOCK IN ", "CONFIRM", JOptionPane.YES_NO_OPTION);
            if (x == JOptionPane.YES_OPTION) {                      
                        try {
                    pst = con.prepareStatement(sign);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "ADDED Successfully");
                    insertToLogsStockIn();
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(items);  
    mainpanel.repaint();
    mainpanel.revalidate();
    stocksadd.setText("");
    refresh_table();
                } 
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }    
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        }    
        refresh_table();
    }//GEN-LAST:event_stockin_btnActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
 
        if(desc1.getText().equals("") || item_name1.getText().equals("") || item_barcode.getText().equals("")|| prays1.getText().equals("") || rprice.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Enter valid data");
        }   
        else if( category.getSelectedItem().equals("Select Category")){
            JOptionPane.showMessageDialog(null, "Select Category");
        }
        else{         
        con = Connect();
                try {
                    String sqal = "SELECT * FROM items WHERE ItemName = '"+item_name1.getText()+"' OR barcode = "+item_barcode.getText();
                    pst = con.prepareStatement(sqal);
                    rs = pst.executeQuery(); 
                        if(rs.next())
                        {
                            JOptionPane.showMessageDialog(null, "Item Name or Item Code already exist");
                            item_name1.setText("");
                            prays1.setText("");
                            rprice.setText("");
                            desc1.setText("");
                          }
                        else{
                            String itemname = item_name1.getText();
                            String description = desc1.getText();
                            String caategory = (String) category.getSelectedItem();
                            String price = prays1.getText();
                            String rpsrice = rprice.getText();
                            float bp = Float.parseFloat(prays1.getText());
                            float rp = Float.parseFloat(rprice.getText());

                            float profits = rp - bp;
                            String a = String.valueOf(profits);
                            String sql = "Select * from items";
                            try {
                                st = con.createStatement();
                                rs = st.executeQuery(sql);
                                String sign = "Insert into items(`ItemName`,`Description`,`Category`,`Price`,`Available_Stocks`,`RetailPrice`,`profits`,`damage`,`barcode`)"
                                + "VALUES ('" + itemname + "','" + description + "','" + caategory + "','" + price + "','0','"+ rpsrice + "','"+ a + "','0','"+item_barcode.getText()+"')";
                                int x = JOptionPane.showConfirmDialog(null, "REGISTER ITEMS", "REGISTER", JOptionPane.YES_NO_OPTION);
                                    if (x == JOptionPane.YES_OPTION) {                      
                                        try {
                                            pst = con.prepareStatement(sign);
                                            pst.execute();
                                            JOptionPane.showMessageDialog(null, "Registered Successfully");
                                            item_name1.setText("");
                                            item_barcode.setText("");
                                            prays1.setText("");
                                            desc1.setText("");
                                            rprice.setText("");
                                        } 
                                        catch (Exception ex) {
                                            JOptionPane.showMessageDialog(null, ex);
                                        }       
                                        }
                                } 
                            catch (Exception ex) {
                                JOptionPane.showMessageDialog(null, ex);
                            }
                            }

                            refresh_table();
                            
                            }
                            catch (Exception e) {
                            }
        }  
    }//GEN-LAST:event_jButton1ActionPerformed

    private void categoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_categoryMouseClicked

    }//GEN-LAST:event_categoryMouseClicked

    private void stockin_btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockin_btn1ActionPerformed
 con = Connect();
        if (catname.getText().equals("")){
        JOptionPane.showMessageDialog(null, "Please select a category to delete");
    }  
    else{
       String sql = "SELECT * FROM category";
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            String sign = "DELETE FROM `category` WHERE ID = " + id.getText() +"";
            int x = JOptionPane.showConfirmDialog(null, "DELETE CATEGORY?  If you delete this item it will not be recovered", "DELETE "+ catname.getText(), JOptionPane.YES_NO_OPTION);
            if (x == JOptionPane.YES_OPTION) {                      
                        try {
                    pst = con.prepareStatement(sign);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "DELETED SUCCESSFULLY");      
    catname.setText("");    
                } 
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }       
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        }       
    }//GEN-LAST:event_stockin_btn1ActionPerformed

    private void category_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_category_tableMouseClicked
try{
            int row = category_table.getSelectedRow();
            String itemcode = (category_table.getModel().getValueAt(row,0).toString());
            String itemname = (category_table.getModel().getValueAt(row,1).toString());
            id.setText(itemcode);
            catname.setText(itemname);
            String a = catname.getText();
            addcateg1.setText(a);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_category_tableMouseClicked

    private void stockin_btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockin_btn2ActionPerformed
    //addcateg
    con = Connect();
    String caategory =addcateg.getText();
        if (addcateg.getText().equals("")){
        JOptionPane.showMessageDialog(null, "Type valid category name");
    }  
    else{
        try {
          String sqal = "SELECT * FROM category WHERE categoryname = '"+caategory+"'";
            pst = con.prepareStatement(sqal);
            rs = pst.executeQuery(); 
                if(rs.next())
                {
                    JOptionPane.showMessageDialog(null, "Category is already added");
                    addcateg.setText("");
                }
                else{
       String sql = "Select * from category";
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            String sign = "Insert into category(`categoryname`)"
                    + "VALUES ('" + caategory + "')";
            int x = JOptionPane.showConfirmDialog(null, "REGISTER CATEGORY", "REGISTER", JOptionPane.YES_NO_OPTION);
            if (x == JOptionPane.YES_OPTION) {                      
                        try {
                    pst = con.prepareStatement(sign);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Registered Successfully");
                    addcateg.setText("");
                } 
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }       
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
                }
        }                 catch (Exception e) {

                            }  
        }
    }//GEN-LAST:event_stockin_btn2ActionPerformed

    private void EditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditActionPerformed
        if (catname.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please select a category to edit");;
        }
        else{
            jPanel31.setBackground(new java.awt.Color(255,255,255,150));
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(cat_edit);  
    mainpanel.repaint();
    mainpanel.revalidate();
    catname.setText("");
        }
    }//GEN-LAST:event_EditActionPerformed

    private void jPanel10MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseMoved
                 String sql1 = "Select * from category";
        try{
            DefaultTableModel model = (DefaultTableModel) category_table.getModel();
            model.setRowCount(0);
            st = con.createStatement();
            rs = st.executeQuery(sql1);
            while (rs.next()){
                String id = rs.getString("ID");
                String Category = rs.getString("categoryname");
                Object[] row = {id,Category };
                model.addRow(row);
            }
        }catch(Exception ex){
            System.out.println(ex); 
        }
    }//GEN-LAST:event_jPanel10MouseMoved

    private void stockin_btn4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockin_btn4ActionPerformed
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(cat_reg);  
    mainpanel.repaint();
    mainpanel.revalidate();
    }//GEN-LAST:event_stockin_btn4ActionPerformed

    private void cat_editsMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cat_editsMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_cat_editsMouseMoved

    private void stockin_btn5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockin_btn5ActionPerformed
 con = Connect();
        if (addcateg1.getText().equals("")){
        JOptionPane.showMessageDialog(null, "Type valid category name");
    }  
    else{
      String caategory =addcateg1.getText();
       String sql = "SELECT * FROM category";
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            String sign = "UPDATE `category` SET `categoryname`= '"+ caategory +"' WHERE ID = "+ id.getText();
            int x = JOptionPane.showConfirmDialog(null, "EDIT CATEGORY", "EDIT", JOptionPane.YES_NO_OPTION);
            if (x == JOptionPane.YES_OPTION) {                      
                        try {
                    pst = con.prepareStatement(sign);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "EDITED Successfully");
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(cat_reg);  
    mainpanel.repaint();
    mainpanel.revalidate();
                } 
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }      
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        }    
    }//GEN-LAST:event_stockin_btn5ActionPerformed

    private void jPanel10MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseEntered
  
   
    }//GEN-LAST:event_jPanel10MouseEntered

    private void jPanel10MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel10MouseReleased

    }//GEN-LAST:event_jPanel10MouseReleased

    private void addcategMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_addcategMouseClicked
catname.setText("");        // TODO add your handling code here:
    }//GEN-LAST:event_addcategMouseClicked

    private void itemsMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemsMouseMoved
                                                    try {
          String sql = "SELECT COUNT(ID) FROM items";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
                while (rs.next()){
                    String name = rs.getString("COUNT(ID)");
                    totalitems.setText(name);
                }
        }                 catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
    }//GEN-LAST:event_itemsMouseMoved

    private void searchMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchMouseClicked
    search.setText("");
    }//GEN-LAST:event_searchMouseClicked

    private void sortMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sortMouseReleased
   
    }//GEN-LAST:event_sortMouseReleased

    private void sortMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sortMousePressed
        // TODO add your handling code here:
    }//GEN-LAST:event_sortMousePressed

    private void sortMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sortMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_sortMouseExited

    private void sortMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sortMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_sortMouseMoved

    private void tableMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseMoved
   
        ///
                                                      try {
          String sql = "SELECT COUNT(ID) FROM items";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
                
                while (rs.next()){
                    String name = rs.getString("COUNT(ID)");
                    totalitems.setText(name);
                }
          

        }                 catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
    }//GEN-LAST:event_tableMouseMoved

    private void sortFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sortFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_sortFocusLost

    private void searchFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_searchFocusLost
    search.setText("Search");
    }//GEN-LAST:event_searchFocusLost

    private void stockin_btn6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockin_btn6ActionPerformed
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(items);  
    mainpanel.repaint();
    mainpanel.revalidate();
    }//GEN-LAST:event_stockin_btn6ActionPerformed

    private void stockin_btn7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockin_btn7ActionPerformed
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(items_choice);  
    mainpanel.repaint();
    mainpanel.revalidate();
    }//GEN-LAST:event_stockin_btn7ActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
 con = Connect();
        if (item_code.getText().equals("")){
        JOptionPane.showMessageDialog(null, "Please select an item to delete");
    }  
    else{
      String id =item_code.getText();
       String sql = "SELECT * FROM items";
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            String sign = "DELETE FROM `items` WHERE barcode = " + id;
                   

            int x = JOptionPane.showConfirmDialog(null, "DELETE ITEM?  If you delete this item it will not be recovered", "DELETE "+ item_name.getText(), JOptionPane.YES_NO_OPTION);
            if (x == JOptionPane.YES_OPTION) {                      
                        try {
                    pst = con.prepareStatement(sign);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "DELETED SUCCESSFULLY");
                        
    item_name.setText("");
   categoryy.setText("");
   prays.setText("");
   avlstocks1.setText("");
   desc.setText("");
   item_code.setText("");
   status.setText("");
    refresh_table();
                } 
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
                        
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
      
        }    
    }//GEN-LAST:event_btn_deleteActionPerformed

    private void btn_deleteFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btn_deleteFocusLost

    }//GEN-LAST:event_btn_deleteFocusLost

    private void tableFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tableFocusLost

    }//GEN-LAST:event_tableFocusLost

    private void tablePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tablePropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_tablePropertyChange

    private void jScrollPane1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseMoved
                                                      try {
          String sql = "SELECT COUNT(ID) FROM items";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
                
                while (rs.next()){
                    String name = rs.getString("COUNT(ID)");
                    totalitems.setText(name);
                }
          

        }                 catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
                                                      
                                                      

    }//GEN-LAST:event_jScrollPane1MouseMoved

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
if(status.getText().equals("")){
    JOptionPane.showMessageDialog(null, "SELECT ITEM TO EDIT"); 
}
else{
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(edit_item);  
    mainpanel.repaint();
    mainpanel.revalidate();
    jPanel33.setBackground(new java.awt.Color(255,255,255,150));
    
    item_name3.setText(item_name.getText());       
    prays3.setText(prays.getText());        
    rrprice.setText(Rprice.getText());       
    desc3.setText(desc.getText());  
        item_codeid.setText(item_code.getText());  
             item_code.setText("");
            item_name.setText("");
            categoryy.setText("");
            desc.setText("");
            avlstocks1.setText("");
            prays.setText("");
            status.setText("");
            Rprice.setText("");
}       
      try {
          String ssql = "SELECT categoryname FROM `category` ORDER BY categoryname asc";
            pst = con.prepareStatement(ssql);
            rs = pst.executeQuery();
                category1.removeAllItems();
                while (rs.next()){
                    String name = rs.getString("categoryname");
                    category1.addItem(name);
                }
          

        }                 catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
    }//GEN-LAST:event_btn_editActionPerformed

    private void category1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_category1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_category1MouseClicked

    private void edititemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edititemActionPerformed
con = Connect();
        if (category1.getSelectedItem().equals("Select Category")){
        JOptionPane.showMessageDialog(null, "Select category name");
    }  
    else{
    String upname =item_name3.getText();
    String upcat = category1.getSelectedItem().toString();        
    String upprice = prays3.getText();        
    String decs = desc3.getText(); 
     String iid = item_codeid.getText();  
     String rrprise  = rrprice.getText();
         float bp = Float.parseFloat(prays3.getText());
         float rp = Float.parseFloat(rrprice.getText());
         
         float profits = rp - bp;
         String a = String.valueOf(profits);
       String sql = "SELECT * FROM items";
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            String sign = "UPDATE `items` SET ItemName= '"+ upname +"' , Description = '"+decs+"' , Category = '"+upcat+"' , Price = " + upprice + " , RetailPrice = '"+rrprise+"', profits = '"+a+"'WHERE barcode = " + iid;
                   

            int x = JOptionPane.showConfirmDialog(null, "EDIT ITEM", "EDIT", JOptionPane.YES_NO_OPTION);
            if (x == JOptionPane.YES_OPTION) {                      
                        try {
                    pst = con.prepareStatement(sign);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "EDITED Successfully");
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(items);  
    mainpanel.repaint();
    mainpanel.revalidate();
refresh_table();
                } 
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
                        
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
      
        }   
    }//GEN-LAST:event_edititemActionPerformed

    private void stockin_btn8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockin_btn8ActionPerformed
       mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(items);  
    mainpanel.repaint();
    mainpanel.revalidate();
    }//GEN-LAST:event_stockin_btn8ActionPerformed

    private void table1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table1MouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_table1MouseMoved

    private void table1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_table1FocusLost

    }//GEN-LAST:event_table1FocusLost

    private void table1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_table1MouseClicked
   try{
            int row = table1.getSelectedRow();
            String idd = (table1.getModel().getValueAt(row,0).toString());
            String username = (table1.getModel().getValueAt(row,1).toString());
            String password = (table1.getModel().getValueAt(row,2).toString());
            String fname = (table1.getModel().getValueAt(row,3).toString());
            String mname = (table1.getModel().getValueAt(row,4).toString());
            String lname = (table1.getModel().getValueAt(row,5).toString());
            String age = (table1.getModel().getValueAt(row,7).toString());
            String address = (table1.getModel().getValueAt(row,8).toString());
            String position = (table1.getModel().getValueAt(row,9).toString());
            String acctype = (table1.getModel().getValueAt(row,6).toString());

            acc_id.setText(idd);
            acc_username.setText(username);
            acc_password.setText(password);
            acc_fname.setText(fname);
            acc_lname.setText(lname);
            acc_mname.setText(mname);
            acc_create_type1.setSelectedItem(acctype);
            acc_pos.setSelectedItem(position);
            acc_age.setText(age);
            acc_add.setText(address);   
            
        }
        catch(Exception e){
            System.out.println(e);
        }
    }//GEN-LAST:event_table1MouseClicked

    private void table1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_table1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_table1PropertyChange

    private void jScrollPane8MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane8MouseMoved
                                                       try {
          String sql = "SELECT COUNT(ID) FROM accounts";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
                
                while (rs.next()){
                    String name = rs.getString("COUNT(ID)");
                    totalaccounts.setText(name);
                }
          

        }                 catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }  
    }//GEN-LAST:event_jScrollPane8MouseMoved

    private void create_accountFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_create_accountFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_create_accountFocusLost

    private void create_accountMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_create_accountMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_create_accountMouseEntered

    private void create_accountMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_create_accountMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_create_accountMouseExited

    private void create_accountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_create_accountActionPerformed
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(accounts_create);  
    mainpanel.repaint();
    mainpanel.revalidate();
    jPanel37.setBackground(new java.awt.Color(255,255,255,150));

    }//GEN-LAST:event_create_accountActionPerformed

    private void btn_edit1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_edit1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_edit1MouseEntered

    private void btn_edit1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_edit1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_edit1MouseExited

    private void btn_edit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_edit1ActionPerformed
 con = Connect();  
         if (acc_id.getText().equals("")){
        JOptionPane.showMessageDialog(null, "Please select an account to update");
    }  
         else{
       String sql = "SELECT * FROM accounts";
        try {
            if( acc_create_type1.getSelectedItem().equals("Account Type:")){
            JOptionPane.showMessageDialog(null, "Select Account Type");
        }
        else if( acc_pos.getSelectedItem().equals("Select")){
            JOptionPane.showMessageDialog(null, "Select Position");
        } 
        else{
            st = con.createStatement();
            rs = st.executeQuery(sql);

            String sign = "UPDATE `accounts` SET Username= '"+ acc_username.getText() +"' , Password = '"+acc_password.getText()+"' , mname = '"+acc_mname.getText()+"' , fname = '"+acc_fname.getText()+"' , lname = '" + acc_lname.getText() +"' , Age = '" + acc_age.getText() +  "', AccountType = '" + acc_create_type1.getSelectedItem() + "', Position = '" + acc_pos.getSelectedItem() + "'   WHERE ID = " + acc_id.getText();
                   

            int x = JOptionPane.showConfirmDialog(null, "EDIT ACCOUNT", "EDIT", JOptionPane.YES_NO_OPTION);
            if (x == JOptionPane.YES_OPTION) {                      
                        try {
                    pst = con.prepareStatement(sign);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "EDITED Successfully");
                    acc_username.setText("");
                    acc_password.setText("");
                    acc_id.setText("");
                    acc_fname.setText("");
                    acc_lname.setText("");
                    acc_mname.setText("");
                    acc_create_type1.setSelectedItem("Account Type:");
                    acc_pos.setSelectedItem("Select");
                    acc_age.setText("");        
                    acc_add.setText("");    
                     table1refresh_table();
       try {
          String ssql = "SELECT COUNT(ID) FROM accounts";
            pst = con.prepareStatement(ssql);
            rs = pst.executeQuery();
                
                while (rs.next()){
                    String name = rs.getString("COUNT(ID)");
                    totalaccounts.setText(name);
                }
          

        }                 catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }         
                 
                } 
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
                        
            }
        }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
         }
      
        
    }//GEN-LAST:event_btn_edit1ActionPerformed

    private void search1FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_search1FocusLost
    search1.setText("Search");
    }//GEN-LAST:event_search1FocusLost

    private void search1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_search1MouseClicked
    search1.setText("");
    }//GEN-LAST:event_search1MouseClicked

    private void accounts_panelMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_accounts_panelMouseMoved
                                                              try {
          String sql = "SELECT COUNT(ID) FROM accounts";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
                
                while (rs.next()){
                    String name = rs.getString("COUNT(ID)");
                    totalaccounts.setText(name);
                }
          

        }                 catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }         
                     
    }//GEN-LAST:event_accounts_panelMouseMoved

    private void btn_delete2FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btn_delete2FocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_delete2FocusLost

    private void btn_delete2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_delete2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_delete2MouseEntered

    private void btn_delete2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_delete2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_delete2MouseExited

    private void btn_delete2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_delete2ActionPerformed
con = Connect();
        if (acc_id.getText().equals("")){
        JOptionPane.showMessageDialog(null, "Please select an account to delete");
    }  
    else{
      String id =acc_id.getText();
       String sql = "SELECT * FROM accounts";
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);

            String sign = "DELETE FROM `accounts` WHERE ID = " + id;
                   

            int x = JOptionPane.showConfirmDialog(null, "DELETE ACCOUNT?  If you delete this account it will not be recovered", "DELETE "+ acc_username.getText(), JOptionPane.YES_NO_OPTION);
            if (x == JOptionPane.YES_OPTION) {                      
                        try {
                    pst = con.prepareStatement(sign);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "DELETED SUCCESSFULLY");
                    acc_username.setText("");
                    acc_password.setText("");
                    acc_id.setText("");
                    acc_fname.setText("");
                    acc_lname.setText("");
                    acc_mname.setText("");
                    acc_create_type1.setSelectedItem("Account Type:");
                    acc_pos.setSelectedItem("Select");
                    acc_age.setText("");        
                    acc_add.setText("");    
                     table1refresh_table();
                } 
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
                        
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
      
        }    
    }//GEN-LAST:event_btn_delete2ActionPerformed

    private void acc_create_typeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_acc_create_typeMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_acc_create_typeMouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
            if(   acc_create_user.getText().equals("") || acc_create_pass.getText().equals("") || acc_create_fname.getText().equals("") || acc_create_lname.getText().equals("")){
             JOptionPane.showMessageDialog(null, "Enter valid data");
        }
        
        else if( acc_create_type.getSelectedItem().equals("Account Type:")){
            JOptionPane.showMessageDialog(null, "Select Account Type");
        }
        else if( acc_create_pos.getSelectedItem().equals("Select")){
            JOptionPane.showMessageDialog(null, "Select Position");
        } 
        else{         
        con = Connect();
        
        String user = acc_create_user.getText();
        String pass = acc_create_pass.getText();
        String acctype = (String) acc_create_type.getSelectedItem();
        String pos = (String) acc_create_pos.getSelectedItem();
        String fname = acc_create_fname.getText();
        String lname = acc_create_lname.getText();        
        String age = acc_create_age.getText();        
        String address = acc_create_add.getText();        
        String mname = acc_create_mname.getText();        

        String sql = "Select * from accounts WHERE username = '"+ user+"'";
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            if (rs.next()){
                    JOptionPane.showMessageDialog(null, "Username exist");
            }
            else{
            String sign = "Insert into accounts(`Username`,`Password`,`fname`,`lname`,`AccountType`,`mname`,`Age`,`Address`,`Position`)"
                    + "VALUES ('" + user + "','" + pass + "','" + fname + "','" + lname + "','" + acctype + "','" + mname + "',"+age+",'"+address+"','"+pos+"')";

            int x = JOptionPane.showConfirmDialog(null, "CREATE ACCOUNT", "CREATE", JOptionPane.YES_NO_OPTION);
            if (x == JOptionPane.YES_OPTION) {                      
                        try {
                    pst = con.prepareStatement(sign);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "CREATED Successfully");
    acc_create_user.setText("");
    acc_create_pass.setText("");
    acc_create_fname.setText("");
    acc_create_lname.setText("");
    acc_create_type.setSelectedItem("Account Type:");
    acc_create_pos.setSelectedItem("Select");
    acc_create_age.setText("");        
    acc_create_add.setText("");        
    acc_create_mname.setText("");    
    //////
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(accounts_panel);  
    mainpanel.repaint();
    mainpanel.revalidate();
    
    
    //refresh
    table1refresh_table();
                                                     try {
          String sqdl = "SELECT COUNT(ID) FROM accounts";
            pst = con.prepareStatement(sqdl);
            rs = pst.executeQuery();
                
                while (rs.next()){
                    String name = rs.getString("COUNT(ID)");
                    totalaccounts.setText(name);
                }
          

        }                 catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }  
             //refreshtable
             
             
                } 
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
                        
            }

        }
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        }
    }//GEN-LAST:event_jButton2ActionPerformed

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
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
                String id = rs.getString("barcode");
                String ProductName = rs.getString("ItemName");
                String Description = rs.getString("Description");
                String Category = rs.getString("Category");
                String Price = rs.getString("Price");
                String RPrice = rs.getString("RetailPrice");
                String Available_Stocks = rs.getString("Available_Stocks");
                String s = rs.getString("profits");
                String dam = rs.getString("damage");
                Object[] row = {id,ProductName, Description, Category, Price,RPrice,Available_Stocks,s,dam};
                model.addRow(row);
            }
            
        }catch(Exception ex){
            System.out.println(ex);
            
        } 
    
    }
    else if(sort.getSelectedItem().equals("No.")){
        refresh_table();
    }
        else if(sort.getSelectedItem().equals("Sort by:")){
            refresh_table();
    }
       else if(sort.getSelectedItem().equals("Category")){
             con = Connect();
    String sql = "SELECT * FROM items ORDER BY Category ASC";
        try{
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
                String id = rs.getString("barcode");
                String ProductName = rs.getString("ItemName");
                String Description = rs.getString("Description");
                String Category = rs.getString("Category");
                String Price = rs.getString("Price");
                String RPrice = rs.getString("RetailPrice");
                String Available_Stocks = rs.getString("Available_Stocks");
                String s = rs.getString("profits");
                String dam = rs.getString("damage");
                Object[] row = {id,ProductName, Description, Category, Price,RPrice,Available_Stocks,s,dam};
                model.addRow(row);
            }
            
        }catch(Exception ex){
            System.out.println(ex);
            
        }     
    }
    }//GEN-LAST:event_btn_sortActionPerformed

    private void sortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sortActionPerformed

    private void searchMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_searchMouseReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_searchMouseReleased

    private void searchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchKeyReleased
 con = Connect();
     String searchQuery = "SELECT * FROM items WHERE CONCAT(`barcode`, `ItemName`, `Description`,`Category`, `Price`,`Available_Stocks`) LIKE '%" + search.getText() + "%'";
        try{
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            st = con.createStatement();
            rs = st.executeQuery(searchQuery);
            while (rs.next()){
                String id = rs.getString("barcode");
                String ProductName = rs.getString("ItemName");
                String Description = rs.getString("Description");
                String Category = rs.getString("Category");
                String Price = rs.getString("Price");
                String RPrice = rs.getString("RetailPrice");
                String Available_Stocks = rs.getString("Available_Stocks");
                String a = rs.getString("profits");
                String dam = rs.getString("damage");
                Object[] row = {id,ProductName, Description, Category, Price,RPrice,Available_Stocks,a,dam};
                model.addRow(row);
            }
            
        }catch(Exception ex){
            System.out.println(ex);
            
        }     
    }//GEN-LAST:event_searchKeyReleased

    private void btn_itemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_itemsMouseClicked
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(items_choice);  
    mainpanel.repaint();
    mainpanel.revalidate();
    jPanel26.setBackground(new java.awt.Color(255,255,255,150));
  a1.setBackground(new java.awt.Color(51,255,51));
a5.setBackground(new java.awt.Color(255,255,255));
a4.setBackground(new java.awt.Color(255,255,255));
a3.setBackground(new java.awt.Color(255,255,255));
a2.setBackground(new java.awt.Color(255,255,255));
a6.setBackground(new java.awt.Color(255,255,255));
    
    }//GEN-LAST:event_btn_itemsMouseClicked

    private void btn_itemsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_itemsMouseEntered
        btn_items.setBackground(new java.awt.Color(51,255,51));
        btn_items.setForeground(new java.awt.Color(0,153,0));
        btn_items.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,153,0), 2));
    }//GEN-LAST:event_btn_itemsMouseEntered

    private void btn_categMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_categMouseClicked
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(cat_reg);  
    mainpanel.repaint();
    mainpanel.revalidate();
        jPanel30.setBackground(new java.awt.Color(255,255,255,150));
a3.setBackground(new java.awt.Color(51,255,51));
a5.setBackground(new java.awt.Color(255,255,255));
a4.setBackground(new java.awt.Color(255,255,255));
a6.setBackground(new java.awt.Color(255,255,255));
a2.setBackground(new java.awt.Color(255,255,255));
a1.setBackground(new java.awt.Color(255,255,255));
    String sql1 = "Select * from category";
        try{
            DefaultTableModel model = (DefaultTableModel) category_table.getModel();
            model.setRowCount(0);
            st = con.createStatement();
            rs = st.executeQuery(sql1);
            while (rs.next()){
                String id = rs.getString("ID");
                String Category = rs.getString("categoryname");
              
                Object[] row = {id,Category };
                model.addRow(row);
            }
        }catch(Exception ex){
            System.out.println(ex);
        }
    }//GEN-LAST:event_btn_categMouseClicked

    private void btn_categMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_categMouseEntered
        btn_categ.setBackground(new java.awt.Color(51,255,51));
        btn_categ.setForeground(new java.awt.Color(0,153,0));
        btn_categ.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,153,0), 2));
    }//GEN-LAST:event_btn_categMouseEntered

    private void btn_accMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_accMouseClicked
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(accounts_panel);  
    mainpanel.repaint();
    mainpanel.revalidate();
        jPanel35.setBackground(new java.awt.Color(255,255,255,150));

a4.setBackground(new java.awt.Color(51,255,51));
a5.setBackground(new java.awt.Color(255,255,255));
a6.setBackground(new java.awt.Color(255,255,255));
a3.setBackground(new java.awt.Color(255,255,255));
a2.setBackground(new java.awt.Color(255,255,255));
a1.setBackground(new java.awt.Color(255,255,255));
    table1refresh_table();
       try {
          String ssql = "SELECT COUNT(ID) FROM accounts";
            pst = con.prepareStatement(ssql);
            rs = pst.executeQuery();
                
                while (rs.next()){
                    String name = rs.getString("COUNT(ID)");
                    totalaccounts.setText(name);
                }
          

        }                 catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }         
                     
    }//GEN-LAST:event_btn_accMouseClicked

    private void btn_accMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_accMouseEntered
        btn_acc.setBackground(new java.awt.Color(51,255,51));
        btn_acc.setForeground(new java.awt.Color(0,153,0));
        btn_acc.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,153,0), 2));
    }//GEN-LAST:event_btn_accMouseEntered

    private void btn_logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_logoutMouseClicked
    this.dispose();
    login log = new login();
    log.setVisible(true);
    insertToLogsAdmin();
a6.setBackground(new java.awt.Color(51,255,51));
a5.setBackground(new java.awt.Color(255,255,255));
a4.setBackground(new java.awt.Color(255,255,255));
a3.setBackground(new java.awt.Color(255,255,255));
a2.setBackground(new java.awt.Color(255,255,255));
a1.setBackground(new java.awt.Color(255,255,255));

    }//GEN-LAST:event_btn_logoutMouseClicked

    private void btn_logoutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_logoutMouseEntered
        btn_logout.setBackground(new java.awt.Color(51,255,51));
        btn_logout.setForeground(new java.awt.Color(0,153,0));
        btn_logout.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,153,0), 2));
    }//GEN-LAST:event_btn_logoutMouseEntered

    private void btn_items1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_items1MouseClicked
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(items);  
    mainpanel.repaint();
    mainpanel.revalidate();   
    jPanel36.setBackground(new java.awt.Color(255,255,255,150));
    
    try {
          String sql = "SELECT COUNT(ID) FROM items";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery(); 
                while (rs.next()){
                    String name = rs.getString("COUNT(ID)");
                    totalitems.setText(name);
                }
        }                 catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }  
        btn_items1.setBackground(new java.awt.Color(0,153,0));
        btn_items1.setForeground(new java.awt.Color(255,255,255));
            refresh_table();
    }//GEN-LAST:event_btn_items1MouseClicked

    private void btn_items1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_items1MouseEntered
         btn_items1.setBackground(new java.awt.Color(255,255,255));
         btn_items1.setForeground(new java.awt.Color(0,153,0));
         btn_items1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,153,0), 2));
    }//GEN-LAST:event_btn_items1MouseEntered

    private void btn_items1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_items1MouseExited
        btn_items1.setBackground(new java.awt.Color(0,153,0));
        btn_items1.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_items1MouseExited

    private void btn_items2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_items2MouseClicked
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(item_reg);  
    mainpanel.repaint();
    mainpanel.revalidate();
    category_box_refresh();
        btn_items2.setBackground(new java.awt.Color(0,153,0));
        btn_items2.setForeground(new java.awt.Color(255,255,255));
        jPanel4.setBackground(new java.awt.Color(255,255,255,150));
 
    }//GEN-LAST:event_btn_items2MouseClicked

    private void btn_items2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_items2MouseEntered
         btn_items2.setBackground(new java.awt.Color(255,255,255));
         btn_items2.setForeground(new java.awt.Color(0,153,0));
         btn_items2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,153,0), 2));
    }//GEN-LAST:event_btn_items2MouseEntered

    private void btn_items2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_items2MouseExited
        btn_items2.setBackground(new java.awt.Color(0,153,0));
        btn_items2.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_items2MouseExited

    private void acc_create_type1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_acc_create_type1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_acc_create_type1MouseClicked

    private void btn_delete2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_delete2MouseClicked
 table1refresh_table();
       try {
          String ssql = "SELECT COUNT(ID) FROM accounts";
            pst = con.prepareStatement(ssql);
            rs = pst.executeQuery();
                
                while (rs.next()){
                    String name = rs.getString("COUNT(ID)");
                    totalaccounts.setText(name);
                }
          

        }                 catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }         
                 
    }//GEN-LAST:event_btn_delete2MouseClicked

    private void search1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_search1KeyReleased
 con = Connect();
     String searchQuery = "SELECT * FROM accounts WHERE CONCAT(`ID`, `Username`, `Password`,`fname`, `lname`,`AccountType`) LIKE '%" + search1.getText() + "%'";
        try{
            DefaultTableModel model = (DefaultTableModel) table1.getModel();
            model.setRowCount(0);
            st = con.createStatement();
            rs = st.executeQuery(searchQuery);
            while (rs.next()){
                String id = rs.getString("ID");
                String Username = rs.getString("Username");
                String Password = rs.getString("Password");
                String fname = rs.getString("fname");
                String lname = rs.getString("lname");
                String AccountType = rs.getString("AccountType");
                String position = rs.getString("Position");
                String mname = rs.getString("mname");
                String age = rs.getString("Age");
                String address = rs.getString("Address");

                Object[] row = {id,Username, Password, fname,mname, lname,AccountType,age,address,position};
                model.addRow(row);
            }
            
        }catch(Exception ex){
            System.out.println(ex);
            
        }  
    }//GEN-LAST:event_search1KeyReleased

    private void btn_salesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salesMouseClicked
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(reports);  
    mainpanel.repaint();
    mainpanel.revalidate();
    jPanel39.setBackground(new java.awt.Color(255,255,255,150));
        a2.setBackground(new java.awt.Color(51,255,51));
        a5.setBackground(new java.awt.Color(255,255,255));
        a4.setBackground(new java.awt.Color(255,255,255));
        a3.setBackground(new java.awt.Color(255,255,255));
        a6.setBackground(new java.awt.Color(255,255,255));
        a1.setBackground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_salesMouseClicked

    private void btn_salesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salesMouseEntered
        btn_sales.setBackground(new java.awt.Color(51,255,51));
        btn_sales.setForeground(new java.awt.Color(0,153,0));
        btn_sales.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,153,0), 2));                                    

    }//GEN-LAST:event_btn_salesMouseEntered

    private void btn_salesannualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salesannualMouseClicked
        searchday.hide();
        searchmonth.hide();
        searchyear.show();
        jButton5.show();  
                salesprint = 3;
        btn_salesannual.setBackground(new java.awt.Color(0,204,102));
        btn_salessummarry.setBackground(new java.awt.Color(0,153,0));
        btn_salesdaily.setBackground(new java.awt.Color(0,153,0));
        btn_salesmonthlys.setBackground(new java.awt.Color(0,153,0));
        sales_titlepanel.removeAll();
        sales_titlepanel.repaint();
        sales_titlepanel.revalidate();
        sales_titlepanel.add(yearly);  
        sales_titlepanel.repaint();
        sales_titlepanel.revalidate();
        sales_mainpanel.removeAll();
        sales_mainpanel.repaint();
        sales_mainpanel.revalidate();
        sales_mainpanel.add(sales_yearly);  
        sales_mainpanel.repaint();
        sales_mainpanel.revalidate();
        
    SimpleDateFormat sdsf = new SimpleDateFormat("yyyy");
    Date dateobj = new Date();
    String year = sdsf.format(dateobj.getTime());
    yyearr.setText(year);
    
    //fetch from db
    annual();
    }//GEN-LAST:event_btn_salesannualMouseClicked

    private void btn_salesannualMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salesannualMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_salesannualMouseEntered

    private void btn_salesannualMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salesannualMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_salesannualMouseExited

    private void btn_salessummarryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salessummarryMouseClicked
        searchday.hide();
        searchmonth.hide();
        searchyear.hide();
        jButton5.hide();
        salesprint = 0;
        btn_salessummarry.setBackground(new java.awt.Color(0,204,102));
     btn_salesdaily.setBackground(new java.awt.Color(0,153,0));
     btn_salesmonthlys.setBackground(new java.awt.Color(0,153,0));
     btn_salesannual.setBackground(new java.awt.Color(0,153,0));
     salestable();
     totalsaless();
    sales_titlepanel.removeAll();
    sales_titlepanel.repaint();
    sales_titlepanel.revalidate();
    sales_titlepanel.add(summary);  
    sales_titlepanel.repaint();
    sales_titlepanel.revalidate();
    sales_mainpanel.removeAll();
    sales_mainpanel.repaint();
    sales_mainpanel.revalidate();
    sales_mainpanel.add(sales_summary);  
    sales_mainpanel.repaint();
    sales_mainpanel.revalidate();
    
   
    }//GEN-LAST:event_btn_salessummarryMouseClicked

    private void btn_salessummarryMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salessummarryMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_salessummarryMouseEntered

    private void btn_salessummarryMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salessummarryMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_salessummarryMouseExited

    private void btn_salesdailyMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salesdailyMouseClicked

    totalsaless();
        searchday.show();
        searchmonth.hide();
        searchyear.hide();
        jButton5.show();
                salesprint = 1;
        btn_salesdaily.setBackground(new java.awt.Color(0,204,102));
     btn_salessummarry.setBackground(new java.awt.Color(0,153,0));
     btn_salesmonthlys.setBackground(new java.awt.Color(0,153,0));
     btn_salesannual.setBackground(new java.awt.Color(0,153,0));
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
    Date dateobj = new Date();
    String today = sdf.format(dateobj.getTime());
    dailydate.setText(today);
    
con = Connect();
    String sql = "Select * from sales where Date LIKE '"+today+"%' ORDER BY TransactionNo DESC";
        try{
            DefaultTableModel model = (DefaultTableModel) salestable1.getModel();
            model.setRowCount(0);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
                String TransactionNo = rs.getString("TransactionNo");
                String Date = rs.getString("Date");
                String Quantity = rs.getString("Quantity");
                String Total = rs.getString("Total");
                String ItemCode = rs.getString("ItemCode");
                String ItemName = rs.getString("ItemName");
                String Price = rs.getString("Price"); 
                String Profit = rs.getString("Profit");  
                Object[] row = {TransactionNo,Date, ItemCode, ItemName, Quantity,Price,Total,Profit};
                model.addRow(row);
            }
            
        }catch(Exception ex){
            System.out.println(ex);
        }
    sales_titlepanel.removeAll();
    sales_titlepanel.repaint();
    sales_titlepanel.revalidate();
    sales_titlepanel.add(dailysales);  
    sales_titlepanel.repaint();
    sales_titlepanel.revalidate();
    sales_mainpanel.removeAll();
    sales_mainpanel.repaint();
    sales_mainpanel.revalidate();
    sales_mainpanel.add(sales_daily);  
    sales_mainpanel.repaint();
    sales_mainpanel.revalidate();
    
    //dailysales
                     try {
          String sqal = "SELECT SUM(Total),SUM(Profit) FROM sales WHERE Date LIKE '"+dailydate.getText()+"%'";
            pst = con.prepareStatement(sqal);
            rs = pst.executeQuery(); 
                while (rs.next()){
                    String name = rs.getString("SUM(Total)");
                    String name1 = rs.getString("SUM(Profit)");
                    totalsales.setText(name);
                    totalprofit.setText(name1);
                }
        }                 catch (Exception e) {

                            }
                     
    }//GEN-LAST:event_btn_salesdailyMouseClicked

    private void btn_salesdailyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salesdailyMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_salesdailyMouseEntered

    private void btn_salesdailyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salesdailyMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_salesdailyMouseExited

    private void btn_salesmonthlysMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salesmonthlysMouseClicked
        searchday.hide();
        searchmonth.show();
        searchyear.hide();
        jButton5.show();  
                salesprint = 2;
     btn_salesmonthlys.setBackground(new java.awt.Color(0,204,102));
     btn_salessummarry.setBackground(new java.awt.Color(0,153,0));
     btn_salesdaily.setBackground(new java.awt.Color(0,153,0));
     btn_salesannual.setBackground(new java.awt.Color(0,153,0));
    SimpleDateFormat sdf = new SimpleDateFormat("MM");
    SimpleDateFormat sdsf = new SimpleDateFormat("yyyy");
    Date dateobj = new Date();
    String today = sdf.format(dateobj.getTime());
    String year = sdsf.format(dateobj.getTime());
    switch(today){
        case"01":
            dailydate1.setText("January "+year);
            break;
        case"02":
            dailydate1.setText("February "+year);
            break;
        case"03":
            dailydate1.setText("March "+year);
            break;
        case"04":
            dailydate1.setText("April "+year);
            break;
        case"05":
            dailydate1.setText("May "+year);
            break;
        case"06":
            dailydate1.setText("June "+year);
            break;
        case"07":
            dailydate1.setText("July "+year);
            break;
        case"08":
            dailydate1.setText("August "+year);
            break;
        case"09":
            dailydate1.setText("September "+year);
            break;
        case"10":
            dailydate1.setText("October "+year);
            break;
        case"11":
            dailydate1.setText("November "+year);
            break;
        case"12":
            dailydate1.setText("December "+year);
            break;
    }
  con = Connect();
    String sql = "SELECT ItemCode, ItemName, SUM(Quantity), Price, sum(Total), SUM(Profit) FROM sales WHERE Month = "+today+" AND Date LIKE '"+year+"%'GROUP BY ItemName";
        try{
            DefaultTableModel model = (DefaultTableModel) salestable2.getModel();
            model.setRowCount(0);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
                String ItemCode = rs.getString("ItemCode");
                String ItemName = rs.getString("ItemName");
                String Quantity = rs.getString("SUM(Quantity)");
                String Price = rs.getString("Price");
                String Total = rs.getString("sum(Total)");
                String pro = rs.getString("SUM(Profit)");

                Object[] row = {ItemCode,ItemName, Quantity, Price, Total,pro};
                model.addRow(row);
            }
            
        }catch(Exception ex){
            System.out.println(ex);
        }
        
    //monthlyales
                  try {
          String sqal = "SELECT SUM(Total),SUM(Profit) FROM sales WHERE Date LIKE '"+year+"%' AND Month = "+today;
            pst = con.prepareStatement(sqal);
            rs = pst.executeQuery(); 
                while (rs.next()){
                    String name = rs.getString("SUM(Total)");
                    String name1 = rs.getString("SUM(Profit)");
                    totalsales.setText(name);
                    totalprofit.setText(name1);
                }
        }                 catch (Exception e) {

                            }
    sales_titlepanel.removeAll();
    sales_titlepanel.repaint();
    sales_titlepanel.revalidate();
    sales_titlepanel.add(monthly);  
    sales_titlepanel.repaint();
    sales_titlepanel.revalidate();
    sales_mainpanel.removeAll();
    sales_mainpanel.repaint();
    sales_mainpanel.revalidate();
    sales_mainpanel.add(sales_monthly);  
    sales_mainpanel.repaint();
    sales_mainpanel.revalidate();
    }//GEN-LAST:event_btn_salesmonthlysMouseClicked

    private void btn_salesmonthlysMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salesmonthlysMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_salesmonthlysMouseEntered

    private void btn_salesmonthlysMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salesmonthlysMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_salesmonthlysMouseExited

    private void prays1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_prays1KeyTyped

    }//GEN-LAST:event_prays1KeyTyped

    private void stocksaddKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stocksaddKeyTyped

    }//GEN-LAST:event_stocksaddKeyTyped

    private void prays3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_prays3KeyTyped

    }//GEN-LAST:event_prays3KeyTyped

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if (salesprint == 0){
        try {
            salestable.print();
        } catch (PrinterException ex) {
            Logger.getLogger(js_mainmenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        else if(salesprint == 1){
        try {
            salestable1.print();
        } catch (PrinterException ex) {
            Logger.getLogger(js_mainmenu.class.getName()).log(Level.SEVERE, null, ex);
        }  
        }
        else if(salesprint == 2){
        try {
            salestable2.print();
        } catch (PrinterException ex) {
            Logger.getLogger(js_mainmenu.class.getName()).log(Level.SEVERE, null, ex);
        }  
        }
        else{
        try {
            salestable3.print();
        } catch (PrinterException ex) {
            Logger.getLogger(js_mainmenu.class.getName()).log(Level.SEVERE, null, ex);
        }  
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btn_logsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_logsMouseClicked
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(Logs);  
    mainpanel.repaint();
    mainpanel.revalidate();
    logstable();
        jPanel38.setBackground(new java.awt.Color(255,255,255,150));
a5.setBackground(new java.awt.Color(51,255,51));
a6.setBackground(new java.awt.Color(255,255,255));
a4.setBackground(new java.awt.Color(255,255,255));
a3.setBackground(new java.awt.Color(255,255,255));
a2.setBackground(new java.awt.Color(255,255,255));
a1.setBackground(new java.awt.Color(255,255,255));
    //
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
    SimpleDateFormat d = new SimpleDateFormat("hh:mm a");
    Date dateobj = new Date();
    String today = sdf.format(dateobj.getTime());
    ddate.setText(today);
    
    //
    }//GEN-LAST:event_btn_logsMouseClicked

    private void btn_logsMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_logsMouseEntered
        btn_logs.setBackground(new java.awt.Color(51,255,51));
        btn_logs.setForeground(new java.awt.Color(0,153,0));
        btn_logs.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,153,0), 2));
    }//GEN-LAST:event_btn_logsMouseEntered

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
        try {
            logstable.print();
        } catch (PrinterException ex) {
            Logger.getLogger(js_mainmenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void printMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_printMouseClicked
        try {
           table.print();
        } catch (PrinterException ex) {
            Logger.getLogger(js_mainmenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_printMouseClicked

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
    if(searchmonth.isShowing()){
  con = Connect();
    String sql = "SELECT ItemCode, ItemName, SUM(Quantity), Price, sum(Total), SUM(Profit) FROM sales WHERE Month = "+months.getText()+" AND Date LIKE '"+months1.getText()+"%'GROUP BY ItemName";
        try{
            DefaultTableModel model = (DefaultTableModel) salestable2.getModel();
            model.setRowCount(0);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
                String ItemCode = rs.getString("ItemCode");
                String ItemName = rs.getString("ItemName");
                String Quantity = rs.getString("SUM(Quantity)");
                String Price = rs.getString("Price");
                String Total = rs.getString("sum(Total)");
                String pro = rs.getString("SUM(Profit)");

                Object[] row = {ItemCode,ItemName, Quantity, Price, Total,pro};
                model.addRow(row);
            }
            
        }catch(Exception ex){
            System.out.println(ex);
        }
        
    //monthlyales
                  try {
          String sqal = "SELECT SUM(Total),SUM(Profit) FROM sales WHERE Date LIKE '"+months1.getText()+"%' AND Month = "+months.getText();
            pst = con.prepareStatement(sqal);
            rs = pst.executeQuery(); 
                while (rs.next()){
                    String name = rs.getString("SUM(Total)");
                    String name1 = rs.getString("SUM(Profit)");
                    totalsales.setText(name);
                    totalprofit.setText(name1);
                }
        }                 catch (Exception e) {

                            }

    }
    else if(searchday.isShowing()){
    
        con = Connect();
    String sql = "Select * from sales where Date LIKE '"+dailydate.getText()+"%' ORDER BY TransactionNo DESC";
        try{
            DefaultTableModel model = (DefaultTableModel) salestable1.getModel();
            model.setRowCount(0);
            st = con.createStatement();
            rs = st.executeQuery(sql);
            while (rs.next()){
                String TransactionNo = rs.getString("TransactionNo");
                String Date = rs.getString("Date");
                String Quantity = rs.getString("Quantity");
                String Total = rs.getString("Total");
                String ItemCode = rs.getString("ItemCode");
                String ItemName = rs.getString("ItemName");
                String Price = rs.getString("Price"); 
                String Profit = rs.getString("Profit"); 
                Object[] row = {TransactionNo,Date, ItemCode, ItemName, Quantity,Price,Total,Profit};
                model.addRow(row);
            }
            
        }catch(Exception ex){
            System.out.println(ex);
        }
        
    //dailysales
                  try {
          String sqal = "SELECT SUM(Total),SUM(Profit) FROM sales WHERE Date LIKE '"+dailydate.getText()+"%'";
            pst = con.prepareStatement(sqal);
            rs = pst.executeQuery(); 
                while (rs.next()){
                    String name = rs.getString("SUM(Total)");
                    String name1 = rs.getString("SUM(Profit)");
                    totalsales.setText(name);
                    totalprofit.setText(name1);
                }
        }                 catch (Exception e) {

                            }
    }
    else{
      annual(); 
    }

    }//GEN-LAST:event_jButton5ActionPerformed

    private void searchdayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchdayActionPerformed
    datechooser dat = new datechooser();
    dat.setVisible(true);
    }//GEN-LAST:event_searchdayActionPerformed

    private void searchyearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchyearActionPerformed
    yearchooser dat = new yearchooser();
    dat.setVisible(true);
    }//GEN-LAST:event_searchyearActionPerformed

    private void searchmonthActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchmonthActionPerformed
    monthchooser m = new monthchooser();
    m.setVisible(true);
    }//GEN-LAST:event_searchmonthActionPerformed

    private void prays1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prays1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_prays1ActionPerformed

    private void rpriceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rpriceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rpriceActionPerformed

    private void rpriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rpriceKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_rpriceKeyTyped

    private void rrpriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_rrpriceKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_rrpriceKeyTyped

    private void logsprintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logsprintActionPerformed
            con = Connect();
            String q = "DELETE FROM `logs`";
        int x = JOptionPane.showConfirmDialog(null, "Confirm?  If you clear this log, it will be also cleared from the DATABASE", "CLEAR", JOptionPane.YES_NO_OPTION);
        
        if (x == JOptionPane.YES_OPTION) {    
        try{
                st = con.createStatement();
                if(st.executeUpdate(q) == 1){
                          JOptionPane.showMessageDialog(null,"Cleared Successfully","CLEAR",JOptionPane.INFORMATION_MESSAGE);
                          logstable();
                }
            }catch(Exception e){
                System.out.print(e);
            }
        }
    }//GEN-LAST:event_logsprintActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    login.login_.setBackground(new java.awt.Color(255, 255, 255, 190));
    login.banner.setBackground(new java.awt.Color(255, 255, 255, 190));
    }//GEN-LAST:event_formWindowClosed

    private void btn_damageFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_btn_damageFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_damageFocusLost

    private void btn_damageMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_damageMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_damageMouseEntered

    private void btn_damageMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_damageMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_damageMouseExited

    private void btn_damageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_damageActionPerformed
    if(status.getText().equals("")){
        JOptionPane.showMessageDialog(null, "Select item");
    }  
    else{    
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(damage);  
    mainpanel.repaint();
    mainpanel.revalidate();
    ///
    item_name4.setText(item_name.getText());
    categoryy2.setText(categoryy.getText());
    prays4.setText(prays.getText());
    avlstocks3.setText(avlstocks1.getText());
    desc4.setText(desc.getText());
    itemcode_id1.setText(item_code.getText());
    //
    item_name.setText("");
   categoryy.setText("");
   prays.setText("");
   avlstocks1.setText("");
   desc.setText("");
   item_code.setText("");
   status.setText("");
   Rprice.setText("");
       jPanel32.setBackground(new java.awt.Color(255,255,255,150));
    } 
    }//GEN-LAST:event_btn_damageActionPerformed

    private void excelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_excelMouseClicked
        try{
            exportTable(table, new File("D:\\Items Table.xls"));
            JOptionPane.showMessageDialog(null,"EXPORTED SUCCESSFULLY \n The File is saved in D: ","EXPORT",JOptionPane.INFORMATION_MESSAGE);

        }
        catch(IOException e){
            System.out.println(e);
        }
    }//GEN-LAST:event_excelMouseClicked

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
    jPanel26.setBackground(new java.awt.Color(255,255,255,150));
    }//GEN-LAST:event_formWindowActivated

    private void damagedStocksKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_damagedStocksKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_damagedStocksKeyTyped

    private void stockin_btn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockin_btn3ActionPerformed
con = Connect();
        if (damagedStocks.getText().equals("")){
        JOptionPane.showMessageDialog(null, "Type valid damaged quantity");
    }  
        else if(Integer.parseInt(damagedStocks.getText()) > Integer.parseInt(avlstocks3.getText())){
        JOptionPane.showMessageDialog(null, "Quantity is greater than Stocks \n Enter valid data");
        }
    else{
      String add =avlstocks3.getText(); 
      int a = Integer.parseInt(add);
      String b = damagedStocks.getText();
      int c = Integer.parseInt(b);
      int sum = a - c;
      String damagedd = String.valueOf(sum);
      String sql = "SELECT * FROM items";
        try {
            st = con.createStatement();
            rs = st.executeQuery(sql);
            String sign = "UPDATE `items` SET `Available_Stocks`="+damagedd+", `damage`= damage + "+b+" WHERE barcode = "+ itemcode_id1.getText();
            int x = JOptionPane.showConfirmDialog(null, "Enter Damaged Item? ", "CONFIRM", JOptionPane.YES_NO_OPTION);
            if (x == JOptionPane.YES_OPTION) {                      
                        try {
                    pst = con.prepareStatement(sign);
                    pst.execute();
                    JOptionPane.showMessageDialog(null, "Entered Successfully");
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(items);  
    mainpanel.repaint();
    mainpanel.revalidate();
    damagedStocks.setText("");
    refresh_table();
                } 
                catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }    
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
        }    
        refresh_table();
    }//GEN-LAST:event_stockin_btn3ActionPerformed

    private void stockin_btn9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockin_btn9ActionPerformed
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(items);  
    mainpanel.repaint();
    mainpanel.revalidate();
    }//GEN-LAST:event_stockin_btn9ActionPerformed

    private void btn_itemsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_itemsMouseExited
        btn_items.setBackground(new java.awt.Color(0,153,0));
        btn_items.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_itemsMouseExited

    private void btn_salesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salesMouseExited
        btn_sales.setBackground(new java.awt.Color(0,153,0));
        btn_sales.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_salesMouseExited

    private void btn_categMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_categMouseExited
        btn_categ.setBackground(new java.awt.Color(0,153,0));
        btn_categ.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_categMouseExited

    private void btn_accMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_accMouseExited
        btn_acc.setBackground(new java.awt.Color(0,153,0));
        btn_acc.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_accMouseExited

    private void btn_logsMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_logsMouseExited
        btn_logs.setBackground(new java.awt.Color(0,153,0));
        btn_logs.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_logsMouseExited

    private void btn_logoutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_logoutMouseExited
        btn_logout.setBackground(new java.awt.Color(0,153,0));
        btn_logout.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_logoutMouseExited

    private void a1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_a1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_a1MouseClicked

    private void a1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_a1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_a1MouseEntered

    private void a1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_a1MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_a1MouseExited

    private void a2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_a2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_a2MouseClicked

    private void a2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_a2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_a2MouseEntered

    private void a2MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_a2MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_a2MouseExited

    private void a3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_a3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_a3MouseClicked

    private void a3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_a3MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_a3MouseEntered

    private void a3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_a3MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_a3MouseExited

    private void a4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_a4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_a4MouseClicked

    private void a4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_a4MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_a4MouseEntered

    private void a4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_a4MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_a4MouseExited

    private void a5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_a5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_a5MouseClicked

    private void a5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_a5MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_a5MouseEntered

    private void a5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_a5MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_a5MouseExited

    private void a6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_a6MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_a6MouseClicked

    private void a6MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_a6MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_a6MouseEntered

    private void a6MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_a6MouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_a6MouseExited

    private void items_choiceMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_items_choiceMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_items_choiceMouseClicked

    private void acc_posMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_acc_posMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_acc_posMouseClicked

    private void stockin_btn10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stockin_btn10ActionPerformed
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(accounts_panel);  
    mainpanel.repaint();
    mainpanel.revalidate();
    acc_create_user.setText("");
    acc_create_pass.setText("");
    acc_create_fname.setText("");
    acc_create_lname.setText("");
    acc_create_type.setSelectedItem("Account Type:");
    acc_create_pos.setSelectedItem("Select");
    acc_create_age.setText("");        
    acc_create_add.setText("");        
    acc_create_mname.setText("");    
    }//GEN-LAST:event_stockin_btn10ActionPerformed

    private void acc_create_posMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_acc_create_posMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_acc_create_posMouseClicked

    private void btn_items3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_items3MouseClicked
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(inventory);  
    mainpanel.repaint();
    mainpanel.revalidate();
    sales_mainpanel2.removeAll();
    sales_mainpanel2.repaint();
    sales_mainpanel2.revalidate();
    sales_mainpanel2.add(stockIN);  
    sales_mainpanel2.repaint();
    sales_mainpanel2.revalidate();
    stockprint = 0;
    jPanel40.setBackground(new java.awt.Color(255,255,255,150));
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
    Date dateobj = new Date();
    String today = sdf.format(dateobj.getTime());
    inv_stock();
     summarytitle1.setText(today);
     btn_stockn.setBackground(new java.awt.Color(0,204,102));
     btn_stockt.setBackground(new java.awt.Color(0,153,0));
        btn_items3.setBackground(new java.awt.Color(0,153,0));
        btn_items3.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_items3MouseClicked

    private void btn_items3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_items3MouseEntered
         btn_items3.setBackground(new java.awt.Color(255,255,255));
         btn_items3.setForeground(new java.awt.Color(0,153,0));
         btn_items3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,153,0), 2));
    }//GEN-LAST:event_btn_items3MouseEntered

    private void btn_items3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_items3MouseExited
        btn_items3.setBackground(new java.awt.Color(0,153,0));
        btn_items3.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_items3MouseExited

    private void btn_items4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_items4MouseClicked
    mainpanel.removeAll();
    mainpanel.repaint();
    mainpanel.revalidate();
    mainpanel.add(sales);  
    mainpanel.repaint();
    mainpanel.revalidate();
    salesprint = 0;
    jPanel29.setBackground(new java.awt.Color(255,255,255,150));
    totalsaless();
    salestable();
    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
    Date dateobj = new Date();
    String today = sdf.format(dateobj.getTime());
     summarytitle.setText("SUMMARY OF SALES AS OF "+today);
     btn_salessummarry.setBackground(new java.awt.Color(0,204,102));
     btn_salesannual.setBackground(new java.awt.Color(0,153,0));
     btn_salesdaily.setBackground(new java.awt.Color(0,153,0));
     btn_salesmonthlys.setBackground(new java.awt.Color(0,153,0));
        sales_titlepanel.removeAll();
        sales_titlepanel.repaint();
        sales_titlepanel.revalidate();
        sales_titlepanel.add(summary);  
        sales_titlepanel.repaint();
        sales_titlepanel.revalidate();
        sales_mainpanel.removeAll();
        sales_mainpanel.repaint();
        sales_mainpanel.revalidate();
        sales_mainpanel.add(sales_summary);  
        sales_mainpanel.repaint();
        sales_mainpanel.revalidate();
        searchday.hide();
        searchmonth.hide();
        searchyear.hide();
        jButton5.hide();  
        btn_items4.setBackground(new java.awt.Color(0,153,0));
        btn_items4.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_items4MouseClicked

    private void btn_items4MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_items4MouseEntered
         btn_items4.setBackground(new java.awt.Color(255,255,255));
         btn_items4.setForeground(new java.awt.Color(0,153,0));
         btn_items4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,153,0), 2));
    }//GEN-LAST:event_btn_items4MouseEntered

    private void btn_items4MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_items4MouseExited
        btn_items4.setBackground(new java.awt.Color(0,153,0));
        btn_items4.setForeground(new java.awt.Color(255,255,255));
    }//GEN-LAST:event_btn_items4MouseExited

    private void reportsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportsMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_reportsMouseClicked

    private void btn_stocknMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_stocknMouseClicked
    sales_mainpanel2.removeAll();
    sales_mainpanel2.repaint();
    sales_mainpanel2.revalidate();
    sales_mainpanel2.add(stockIN);  
    sales_mainpanel2.repaint();
    sales_mainpanel2.revalidate();
    stockprint = 0;
    btn_stockn.setBackground(new java.awt.Color(0,204,102));
    btn_stockt.setBackground(new java.awt.Color(0,153,0));
    inv_stock();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
    Date dateobj = new Date();
    String today = sdf.format(dateobj.getTime());
    inv_stock();
     summarytitle1.setText(today);
    }//GEN-LAST:event_btn_stocknMouseClicked

    private void btn_stocknMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_stocknMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_stocknMouseEntered

    private void btn_stocknMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_stocknMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_stocknMouseExited

    private void btn_stocktMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_stocktMouseClicked
    sales_mainpanel2.removeAll();
    sales_mainpanel2.repaint();
    sales_mainpanel2.revalidate();
    sales_mainpanel2.add(stockOUT);  
    sales_mainpanel2.repaint();
    sales_mainpanel2.revalidate();
    stockprint = 1;
    btn_stockt.setBackground(new java.awt.Color(0,204,102));
    btn_stockn.setBackground(new java.awt.Color(0,153,0));
    inv_stockout();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-MM");
    Date dateobj = new Date();
    String today = sdf.format(dateobj.getTime());
    inv_stock();
     summarytitle1.setText(today);
    }//GEN-LAST:event_btn_stocktMouseClicked

    private void btn_stocktMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_stocktMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_stocktMouseEntered

    private void btn_stocktMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_stocktMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_stocktMouseExited

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
     
        if(stockprint == 0){
                try{
            exportTable(stockintable, new File("D:\\Stock In Table.xls"));
            JOptionPane.showMessageDialog(null,"EXPORTED SUCCESSFULLY \n The File is saved in D: ","EXPORT",JOptionPane.INFORMATION_MESSAGE);

        }
        catch(IOException e){
            System.out.println(e);
        }
        }
        else{
                try{
            exportTable(stockouttable, new File("D:\\Stock Out Table.xls"));
            JOptionPane.showMessageDialog(null,"EXPORTED SUCCESSFULLY \n The File is saved in D: ","EXPORT",JOptionPane.INFORMATION_MESSAGE);

        }
        catch(IOException e){
            System.out.println(e);
        }
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        con = Connect();
        if(stockprint == 0){
        String sql = "SELECT * FROM stocks WHERE Date LIKE '"+summarytitle1.getText()+"%' ORDER BY Date DESC";
        try{
            DefaultTableModel model = (DefaultTableModel) stockintable.getModel();
            model.setRowCount(0);
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String IC = rs.getString("Item Code");
                String IN = rs.getString("Item Name");
                String SI = rs.getString("Stock In");
                String Date = rs.getString("Date");
                String Name = rs.getString("Name");
                Object[] row = {IC, IN, SI, Date, Name};
                model.addRow(row);
                
            }
        }catch(Exception e){
            
        }
        }
        else{

        String sql = "SELECT * FROM stockout WHERE Date LIKE '"+summarytitle1.getText()+"%' ORDER BY Date DESC";
        try{
            DefaultTableModel model = (DefaultTableModel) stockouttable.getModel();
            model.setRowCount(0);
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            while(rs.next()){
                String IC = rs.getString("ItemCode");
                String IN = rs.getString("ItenName");
                String SI = rs.getString("Stockout");
                String Date = rs.getString("Date");
                String Name = rs.getString("Name");
                Object[] row = {IC, IN, SI, Date, Name};
                model.addRow(row);
                
            }
        }catch(Exception e){
            
        }
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void searchday1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchday1ActionPerformed
    new inv_date().setVisible(true);
    }//GEN-LAST:event_searchday1ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        if(stockprint == 0){
        try {
            stockintable.print();
        } catch (PrinterException ex) {
            Logger.getLogger(js_mainmenu.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
        else{
        try {
            stockouttable.print();
        } catch (PrinterException ex) {
            Logger.getLogger(js_mainmenu.class.getName()).log(Level.SEVERE, null, ex);
        }
                }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        if (salesprint == 0){
        try{
            exportTable(salestable, new File("D:\\Sales Summarry Table.xls"));
            JOptionPane.showMessageDialog(null,"EXPORTED SUCCESSFULLY \n The File is saved in D: ","EXPORT",JOptionPane.INFORMATION_MESSAGE);

        }
        catch(IOException e){
            System.out.println(e);
        }
        }
        else if(salesprint == 1){
        try{
            exportTable(salestable1, new File("D:\\Daily Sales Table.xls"));
            JOptionPane.showMessageDialog(null,"EXPORTED SUCCESSFULLY \n The File is saved in D: ","EXPORT",JOptionPane.INFORMATION_MESSAGE);

        }
        catch(IOException e){
            System.out.println(e);
        }
        }
        else if(salesprint == 2){
        try{
            exportTable(salestable2, new File("D:\\Monthly Sales Table.xls"));
            JOptionPane.showMessageDialog(null,"EXPORTED SUCCESSFULLY \n The File is saved in D: ","EXPORT",JOptionPane.INFORMATION_MESSAGE);

        }
        catch(IOException e){
            System.out.println(e);
        }
        }
        else{
        try{
            exportTable(salestable3, new File("D:\\Annual Sales Table.xls"));
            JOptionPane.showMessageDialog(null,"EXPORTED SUCCESSFULLY \n The File is saved in D: ","EXPORT",JOptionPane.INFORMATION_MESSAGE);

        }
        catch(IOException e){
            System.out.println(e);
        }
        }
    }//GEN-LAST:event_jButton9ActionPerformed

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
            java.util.logging.Logger.getLogger(js_mainmenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(js_mainmenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(js_mainmenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(js_mainmenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new js_mainmenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Edit;
    private javax.swing.JPanel Logs;
    private javax.swing.JTextField Rprice;
    private javax.swing.JLabel a1;
    private javax.swing.JLabel a2;
    private javax.swing.JLabel a3;
    private javax.swing.JLabel a4;
    private javax.swing.JLabel a5;
    private javax.swing.JLabel a6;
    private javax.swing.JTextField acc_add;
    private javax.swing.JTextField acc_age;
    private javax.swing.JTextField acc_create_add;
    private javax.swing.JTextField acc_create_age;
    private javax.swing.JTextField acc_create_fname;
    private javax.swing.JTextField acc_create_lname;
    private javax.swing.JTextField acc_create_mname;
    private javax.swing.JTextField acc_create_pass;
    private javax.swing.JComboBox<String> acc_create_pos;
    private javax.swing.JComboBox<String> acc_create_type;
    private javax.swing.JComboBox<String> acc_create_type1;
    private javax.swing.JTextField acc_create_user;
    private javax.swing.JTextField acc_fname;
    private javax.swing.JTextField acc_id;
    private javax.swing.JTextField acc_lname;
    private javax.swing.JTextField acc_mname;
    private javax.swing.JTextField acc_password;
    private javax.swing.JComboBox<String> acc_pos;
    private javax.swing.JTextField acc_username;
    private javax.swing.JPanel accounts_create;
    private javax.swing.JPanel accounts_panel;
    private javax.swing.JTextField addcateg;
    private javax.swing.JTextField addcateg1;
    private javax.swing.JLabel addcategory1;
    private javax.swing.JLabel addcategory2;
    private javax.swing.JLabel addcategory3;
    private javax.swing.JTextField avlstocks1;
    private javax.swing.JTextField avlstocks2;
    private javax.swing.JTextField avlstocks3;
    private javax.swing.JLabel btn_acc;
    private javax.swing.JLabel btn_categ;
    private javax.swing.JButton btn_damage;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_delete2;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_edit1;
    private javax.swing.JLabel btn_items;
    private javax.swing.JLabel btn_items1;
    private javax.swing.JLabel btn_items2;
    private javax.swing.JLabel btn_items3;
    private javax.swing.JLabel btn_items4;
    private javax.swing.JLabel btn_logout;
    private javax.swing.JLabel btn_logs;
    private javax.swing.JLabel btn_sales;
    private javax.swing.JLabel btn_salesannual;
    private javax.swing.JLabel btn_salesdaily;
    private javax.swing.JLabel btn_salesmonthlys;
    private javax.swing.JLabel btn_salessummarry;
    private javax.swing.JButton btn_sort;
    private javax.swing.JButton btn_stockin;
    private javax.swing.JLabel btn_stockn;
    private javax.swing.JLabel btn_stockt;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel cat_edit;
    private javax.swing.JPanel cat_edits;
    private javax.swing.JPanel cat_reg;
    private javax.swing.JComboBox<String> category;
    private javax.swing.JComboBox<String> category1;
    private javax.swing.JTable category_table;
    private javax.swing.JTextField categoryy;
    private javax.swing.JTextField categoryy1;
    private javax.swing.JTextField categoryy2;
    private javax.swing.JLabel catname;
    private javax.swing.JButton create_account;
    public static javax.swing.JLabel dailydate;
    public static javax.swing.JLabel dailydate1;
    private javax.swing.JPanel dailysales;
    private javax.swing.JPanel damage;
    private javax.swing.JTextField damagedStocks;
    private javax.swing.JLabel date;
    public static javax.swing.JLabel ddate;
    private javax.swing.JTextArea desc;
    private javax.swing.JTextArea desc1;
    private javax.swing.JTextArea desc2;
    private javax.swing.JTextArea desc3;
    private javax.swing.JTextArea desc4;
    private javax.swing.JPanel edit_item;
    private javax.swing.JButton edititem;
    private javax.swing.JLabel excel;
    private javax.swing.JLabel fnames;
    private javax.swing.JLabel id;
    private javax.swing.JPanel inventory;
    private javax.swing.JTextField item_barcode;
    private javax.swing.JTextField item_code;
    private javax.swing.JLabel item_codeid;
    private javax.swing.JTextField item_name;
    private javax.swing.JTextField item_name1;
    private javax.swing.JTextField item_name2;
    private javax.swing.JTextField item_name3;
    private javax.swing.JTextField item_name4;
    private javax.swing.JPanel item_reg;
    private javax.swing.JLabel itemcode_id;
    private javax.swing.JLabel itemcode_id1;
    private javax.swing.JPanel items;
    private javax.swing.JPanel items_choice;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    public static javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    public static javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    public static javax.swing.JPanel jPanel30;
    public static javax.swing.JPanel jPanel31;
    public static javax.swing.JPanel jPanel32;
    public static javax.swing.JPanel jPanel33;
    public static javax.swing.JPanel jPanel34;
    public static javax.swing.JPanel jPanel35;
    public static javax.swing.JPanel jPanel36;
    public static javax.swing.JPanel jPanel37;
    public static javax.swing.JPanel jPanel38;
    public static javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel4;
    public static javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JButton logsprint;
    private javax.swing.JTable logstable;
    private javax.swing.JLabel lsname;
    private javax.swing.JPanel mainpanel;
    private javax.swing.JPanel monthly;
    public static javax.swing.JLabel months;
    public static javax.swing.JLabel months1;
    private javax.swing.JTextField prays;
    private javax.swing.JTextField prays1;
    private javax.swing.JTextField prays2;
    private javax.swing.JTextField prays3;
    private javax.swing.JTextField prays4;
    private javax.swing.JLabel print;
    private javax.swing.JPanel reports;
    private javax.swing.JTextField rprice;
    private javax.swing.JTextField rrprice;
    private javax.swing.JPanel sales;
    private javax.swing.JPanel sales_daily;
    private javax.swing.JPanel sales_mainpanel;
    private javax.swing.JPanel sales_mainpanel1;
    private javax.swing.JPanel sales_mainpanel2;
    private javax.swing.JPanel sales_monthly;
    private javax.swing.JPanel sales_summary;
    private javax.swing.JPanel sales_table;
    private javax.swing.JPanel sales_table1;
    private javax.swing.JPanel sales_titlepanel;
    private javax.swing.JPanel sales_titlepanel1;
    private javax.swing.JPanel sales_yearly;
    private javax.swing.JTable salestable;
    private javax.swing.JTable salestable1;
    private javax.swing.JTable salestable2;
    private javax.swing.JTable salestable3;
    private javax.swing.JTextField search;
    private javax.swing.JTextField search1;
    private javax.swing.JButton searchday;
    private javax.swing.JButton searchday1;
    private javax.swing.JButton searchmonth;
    private javax.swing.JButton searchyear;
    private javax.swing.JComboBox<String> sort;
    private javax.swing.JTextField status;
    private javax.swing.JPanel stockIN;
    private javax.swing.JPanel stockOUT;
    private javax.swing.JPanel stockin;
    private javax.swing.JButton stockin_btn;
    private javax.swing.JButton stockin_btn1;
    private javax.swing.JButton stockin_btn10;
    private javax.swing.JButton stockin_btn2;
    private javax.swing.JButton stockin_btn3;
    private javax.swing.JButton stockin_btn4;
    private javax.swing.JButton stockin_btn5;
    private javax.swing.JButton stockin_btn6;
    private javax.swing.JButton stockin_btn7;
    private javax.swing.JButton stockin_btn8;
    private javax.swing.JButton stockin_btn9;
    private javax.swing.JTable stockintable;
    private javax.swing.JTable stockouttable;
    private javax.swing.JLabel stocks1;
    private javax.swing.JLabel stocks10;
    private javax.swing.JLabel stocks11;
    private javax.swing.JLabel stocks12;
    private javax.swing.JLabel stocks13;
    private javax.swing.JLabel stocks14;
    private javax.swing.JLabel stocks15;
    private javax.swing.JLabel stocks16;
    private javax.swing.JLabel stocks17;
    private javax.swing.JLabel stocks18;
    private javax.swing.JLabel stocks19;
    private javax.swing.JLabel stocks20;
    private javax.swing.JLabel stocks21;
    private javax.swing.JLabel stocks22;
    private javax.swing.JLabel stocks23;
    private javax.swing.JLabel stocks24;
    private javax.swing.JLabel stocks25;
    private javax.swing.JLabel stocks26;
    private javax.swing.JLabel stocks27;
    private javax.swing.JLabel stocks28;
    private javax.swing.JLabel stocks29;
    private javax.swing.JLabel stocks3;
    private javax.swing.JLabel stocks30;
    private javax.swing.JLabel stocks31;
    private javax.swing.JLabel stocks32;
    private javax.swing.JLabel stocks33;
    private javax.swing.JLabel stocks34;
    private javax.swing.JLabel stocks35;
    private javax.swing.JLabel stocks36;
    private javax.swing.JLabel stocks37;
    private javax.swing.JLabel stocks38;
    private javax.swing.JLabel stocks39;
    private javax.swing.JLabel stocks4;
    private javax.swing.JLabel stocks40;
    private javax.swing.JLabel stocks41;
    private javax.swing.JLabel stocks42;
    private javax.swing.JLabel stocks43;
    private javax.swing.JLabel stocks44;
    private javax.swing.JLabel stocks45;
    private javax.swing.JLabel stocks46;
    private javax.swing.JLabel stocks47;
    private javax.swing.JLabel stocks48;
    private javax.swing.JLabel stocks49;
    private javax.swing.JLabel stocks5;
    private javax.swing.JLabel stocks50;
    private javax.swing.JLabel stocks51;
    private javax.swing.JLabel stocks52;
    private javax.swing.JLabel stocks53;
    private javax.swing.JLabel stocks54;
    private javax.swing.JLabel stocks55;
    private javax.swing.JLabel stocks56;
    private javax.swing.JLabel stocks57;
    private javax.swing.JLabel stocks6;
    private javax.swing.JLabel stocks7;
    private javax.swing.JLabel stocks8;
    private javax.swing.JLabel stocks9;
    private javax.swing.JTextField stocksadd;
    private javax.swing.JPanel summary;
    private javax.swing.JPanel summary1;
    private javax.swing.JLabel summarytitle;
    public static javax.swing.JLabel summarytitle1;
    public javax.swing.JTable table;
    private javax.swing.JTable table1;
    private javax.swing.JLabel totalaccounts;
    private javax.swing.JLabel totalitems;
    private javax.swing.JLabel totalprofit;
    private javax.swing.JLabel totalsales;
    private javax.swing.JLabel type;
    public static javax.swing.JLabel uid;
    public static javax.swing.JLabel uid1;
    public static javax.swing.JLabel uid2;
    public static javax.swing.JLabel uid3;
    private javax.swing.JPanel yearly;
    public static javax.swing.JLabel yyearr;
    // End of variables declaration//GEN-END:variables
}
