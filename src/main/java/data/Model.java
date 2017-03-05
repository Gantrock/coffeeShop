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
import objects.Review;

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
        String sqlInsert="insert into shops (name, city, state, zip, phone, openTime, closeTime, description) values ('"
                + shp.getName() + "','" + shp.getCity() + "','" + shp.getState() 
                + "','" + shp.getZip()  +"','" + shp.getPhone()
                +"','" + shp.getOpen() + "','" + shp.getClose()  
                +"','" + shp.getDescription()+ "');";
        Statement s = createStatement();
        logger.log(Level.INFO, "attempting statement execute");
        s.execute(sqlInsert,Statement.RETURN_GENERATED_KEYS);
        logger.log(Level.INFO, "statement executed.  atempting get generated keys");
        ResultSet rs = s.getGeneratedKeys();
        logger.log(Level.INFO, "retrieved keys from statement");
        int shopid = -1;
        while (rs.next())
            shopid = rs.getInt(9);   // assuming 9th column is shopid
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
        LinkedList<Shop> ll = new LinkedList<>();
        String sqlQuery ="select * from shops;";
        Statement st = createStatement();
        ResultSet rows = st.executeQuery(sqlQuery);
        while (rows.next())
        {
            logger.log(Level.INFO, "Reading row...");
            Shop shp = new Shop();
            shp.setName(rows.getString("name"));
            shp.setShopId(rows.getInt("shopid"));
            shp.setCity(rows.getString("city"));
            shp.setState(rows.getString("state"));
            shp.setZip(rows.getInt("zip"));
            shp.setPhone(rows.getInt("phone"));
            shp.setOpen(rows.getInt("openTime"));
            shp.setClose(rows.getInt("closeTime"));
            shp.setDescription(rows.getString("description"));
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
        sqlQuery.append("city='" + shp.getCity() + "',");
        sqlQuery.append("state='" + shp.getState() + "',");
        sqlQuery.append("zip=" + shp.getZip() + ",");
        sqlQuery.append("phone=" + shp.getPhone() + ",");
        sqlQuery.append("openTime=" + shp.getOpen() + ",");
        sqlQuery.append("closeTime=" + shp.getClose() + ",");
        sqlQuery.append("description='" + shp.getDescription() + "' ");
        sqlQuery.append("where shopid=" + shp.getShopId() + ";");
        Statement st = createStatement();
        logger.log(Level.INFO, "UPDATE SQL=" + sqlQuery.toString());
        return st.execute(sqlQuery.toString());
    }
    
    
   
    
    public int newReview(Review rvw) throws SQLException
    {
        String sqlInsert="insert into reviews (myContent, myDate, shopId, myWifi, myCoffee, myFood, myStudy) values ('"
                + rvw.getMyContent() + "','TO_DATE('" + rvw.getMyDate()+ "', 'yyyy-mm-dd'),'" + rvw.getShopId()
                + "','" + rvw.getMyWifi()  +"','" + rvw.getMyCoffee()
                +"','" + rvw.getMyFood() + "','" + rvw.getMyStudy() + "');";
        Statement s = createStatement();
        logger.log(Level.INFO, "attempting statement execute");
        s.execute(sqlInsert,Statement.RETURN_GENERATED_KEYS);
        logger.log(Level.INFO, "statement executed.  atempting get generated keys");
        ResultSet rs = s.getGeneratedKeys();
        logger.log(Level.INFO, "retrieved keys from statement");
        int reviewid = -1;
        while (rs.next())
            reviewid = rs.getInt(8);   // assuming 8th column is reviewid
        logger.log(Level.INFO, "The new review id=" + reviewid);
        return reviewid;
    }
    
    public void deleteReview(int reviewid) throws SQLException
    {
        String sqlDelete="delete from reviews where reviewid=?";
        PreparedStatement pst = createPreparedStatement(sqlDelete);
        pst.setInt(1, reviewid);
        pst.execute();
    }
    
    public Review[] getReviews() throws SQLException
    {
        LinkedList<Review> ll = new LinkedList<>();
        String sqlQuery ="select * from reviews;";
        Statement st = createStatement();
        ResultSet rows = st.executeQuery(sqlQuery);
        while (rows.next())
        {
            logger.log(Level.INFO, "Reading row...");
            Review rvw = new Review();
            rvw.setMyContent(rows.getString("content"));
            rvw.setMyDate(rows.getDate("date"));
            rvw.setShopId(rows.getInt("shopid"));
            rvw.setMyWifi(rows.getInt("wifi"));
            rvw.setMyCoffee(rows.getInt("coffee"));
            rvw.setMyFood(rows.getInt("food"));
            rvw.setMyStudy(rows.getInt("study"));
            rvw.setMyReviewId(rows.getInt("reviewid"));;
            logger.log(Level.INFO, "Adding review to list with id=" + rvw.getMyReviewId());
            ll.add(rvw);
        }
        return ll.toArray(new Review[ll.size()]);
    }
    
    public boolean updateReview(Review rvw) throws SQLException
    {
        StringBuilder sqlQuery = new StringBuilder();
        sqlQuery.append("update reviews ");
        sqlQuery.append("set content='" + rvw.getMyContent() + "', ");
        sqlQuery.append("date='" + rvw.getMyDate() + "',");
        sqlQuery.append("shopid='" + rvw.getShopId() + "',");
        sqlQuery.append("wifi=" + rvw.getMyWifi() + ",");
        sqlQuery.append("coffee=" + rvw.getMyCoffee() + ",");
        sqlQuery.append("food=" + rvw.getMyFood() + ",");
        sqlQuery.append("study=" + rvw.getMyStudy() + " ");
        sqlQuery.append("where reviewid=" + rvw.getMyReviewId() + ";");
        Statement st = createStatement();
        logger.log(Level.INFO, "UPDATE SQL=" + sqlQuery.toString());
        return st.execute(sqlQuery.toString());
    }
}
