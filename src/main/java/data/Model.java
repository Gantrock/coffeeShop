/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import objects.Shop;

/**
 *
 * @author nick
 */
public class Model {
    static final Logger logger = Logger.getLogger(Model.class.getName());
    private static Model instance;
    private Connection conn;
    
    public static Model singleton() throws Exception {
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }
    
    Model() throws Exception
    {
        Class.forName("org.postgresql.Driver");
        String dbUrl = System.getenv("JDBC_DATABASE_URL");
        if ((dbUrl == null) || (dbUrl.length() < 1))
            dbUrl = System.getProperties().getProperty("DBCONN");
        logger.log(Level.INFO, "dbUrl=" + dbUrl);  
        logger.log(Level.INFO, "attempting db connection");
        conn = DriverManager.getConnection(dbUrl);
        logger.log(Level.INFO, "db connection ok.");
    }
    
    private Connection getConnection()
    {
        return conn;
    }
    
    private Statement createStatement() throws SQLException
    {
        Connection conn = getConnection();
        if ((conn != null) && (!conn.isClosed()))
        {
            logger.log(Level.INFO, "attempting statement create");
            Statement st = conn.createStatement();
            logger.log(Level.INFO, "statement created");
            return st;
        }
        else
        {
            // Handle connection failure
        }
        return null;
    }
    
    private PreparedStatement createPreparedStatement(String sql) throws SQLException
    {
        Connection conn = getConnection();
        if ((conn != null) && (!conn.isClosed()))
        {
            logger.log(Level.INFO, "attempting statement create");
            PreparedStatement pst = conn.prepareStatement(sql);
            logger.log(Level.INFO, "prepared statement created");
            return pst;
        }
        else
        {
            // Handle connection failure
        }
        return null;
    }
    
    public int newShop(Shop shp) throws SQLException
    {
        String sqlInsert="insert into shops (name, address, hours, phone, food) values ('"
                + shp.getName() + "'" + "," + "'" + shp.getAddress() + "'" + "," 
                + "'" + shp.getHours() + "'" + "," + "'" + shp.getPhone()
                + "'" + "," + "'" + shp.getFood() + "'" + ");";
        Statement s = createStatement();
        logger.log(Level.INFO, "attempting statement execute");
        s.execute(sqlInsert,Statement.RETURN_GENERATED_KEYS);
        logger.log(Level.INFO, "statement executed.  atempting get generated keys");
        ResultSet rs = s.getGeneratedKeys();
        logger.log(Level.INFO, "retrieved keys from statement");
        int shopid = -1;
        while (rs.next())
            shopid = rs.getInt(6);   // assuming 6th column is shopid
        logger.log(Level.INFO, "The new shop id=" + shopid);
        return shopid;
    }
    
    public void deleteShop(int shopid) throws SQLException
    {
        String sqlDelete="delete from shops where shopid=?";
        PreparedStatement pst = createPreparedStatement(sqlDelete);
        pst.setInt(1, shopid);
        pst.execute();
    }
    
    public Shop[] getShops() throws SQLException
    {
        LinkedList<Shop> ll = new LinkedList<Shop>();
        String sqlQuery ="select * from shops;";
        Statement st = createStatement();
        ResultSet rows = st.executeQuery(sqlQuery);
        while (rows.next())
        {
            logger.log(Level.INFO, "Reading row...");
            Shop shp = new Shop();
            shp.setName(rows.getString("name"));
            shp.setShopId(rows.getInt("shopid"));
            shp.setAddress(rows.getString("address"));
            shp.setHours(rows.getString("hours"));
            shp.setPhone(rows.getInt("phone"));
            logger.log(Level.INFO, "Adding shop to list with id=" + shp.getShopId());
            ll.add(shp);
        }
        return ll.toArray(new Shop[ll.size()]);
    }
    
    public boolean updateShop(Shop shp) throws SQLException
    {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("update shops ");
        sqlQuery.append("set name='" + shp.getName() + "', ");
        sqlQuery.append("address=" + shp.getAddress() + " ");
        sqlQuery.append("hours=" + shp.getHours() + " ");
        sqlQuery.append("phone=" + shp.getHours() + " ");
        sqlQuery.append("where shopid=" + shp.getShopId() + ";");
        Statement st = createStatement();
        logger.log(Level.INFO, "UPDATE SQL=" + sqlQuery.toString());
        return st.execute(sqlQuery.toString());
    }
    
    
}