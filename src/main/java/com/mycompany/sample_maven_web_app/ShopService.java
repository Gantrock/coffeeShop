/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.sample_maven_web_app;

import data.Model;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import objects.Shop;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONObject;

/**
 * REST Web Service
 *
 * @author nick
 */
@Path("shops")
public class ShopService {

    static final Logger logger = Logger.getLogger(ShopService.class.getName());
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public ShopService() {
    }

    /**
     * Retrieves representation of an instance of services.GenericResource
     * @return an instance of java.lang.String
     */
//    @GET
//    @Produces(MediaType.TEXT_HTML)
//    public String getShops() {
//        //TODO return proper representation object
//        StringBuilder sb = new StringBuilder();
//        sb.append("<html><body><style>table, th, td {font-family:Arial,Verdana,sans-serif;"
//                + "font-size:16px;padding: 0px;border-spacing: 0px;}</style><b>SHOPS LIST:"
//                + "</b><br><br><table cellpadding=10 border=1><tr><td>Name</td><td>City</td>"
//                + "<td>State</td><td>Zip</td><td>Phone</td><td>Open</td><td>Close</td>"
//                + "<td>Description</td><td>Shop ID</td></tr>");
//        try
//        {
//            Model db = Model.singleton();
//            Shop[] shops = db.getShops();
//            for (int i=0;i<shops.length;i++)
//                sb.append("<tr><td>" + shops[i].getName() + "</td><td>" + shops[i].getCity() 
//                		+ "</td><td>" + shops[i].getState()  + "</td><td>" + shops[i].getZip()
//                		+ "</td><td>" + shops[i].getPhone()  + "</td><td>" + shops[i].getOpen()
//                		+ "</td><td>" + shops[i].getClose()  + "</td><td>" + shops[i].getDescription()
//                		 + "</td><td>" + shops[i].getShopid());
//        }
//        catch (Exception e)
//        {
//            sb.append("</table><br>Error getting shops: " + e.toString() + "<br>");
//        }
//        sb.append("</table></body></html>");
//        return sb.toString();
//    }

    
    
    @GET
    @Path("{shopid}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Shop> getShopJson(@PathParam("shopid") String id) {
        LinkedList<Shop> lshops = new LinkedList<Shop>();
     
        try
        {
            int shopid = Integer.parseInt(id);
            Model db = Model.singleton();
            Shop[] shops = db.getShops(shopid);
            if (shopid ==0)
                for (int i=0;i<shops.length;i++)
                    lshops.add(shops[i]);
            else
                lshops.add(shops[0]);
            logger.log(Level.INFO, "Received request to fetch shop id=" + shopid);
            return lshops;
        }
        catch (Exception e)
        {
            JSONObject obj = new JSONObject();
                logger.log(Level.WARNING, "Error getting shops:" + e.toString());
                return null;
        }
    }    
    
    
    
    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     */
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateShop(String jobj) throws IOException
    {
        logger.log(Level.INFO, "RECEIVED UPDATE REQUEST FOR:\n");
        logger.log(Level.INFO, "OBJECT:" + jobj + "\n");
        
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = mapper.readValue(jobj.toString(), Shop.class);
        StringBuilder text = new StringBuilder();
        try {
            Model db = Model.singleton();
            int shopid = shop.getShopid();
            db.updateShop(shop);
            logger.log(Level.INFO, "update shop with shopid=" + shopid);
            text.append("Shop id updated with shop id=" + shopid + "\n");
        }
        catch (SQLException sqle)
        {
            String errText = "Error updating shop after db connection made:\n" + sqle.getMessage() + " --- " + sqle.getSQLState() + "\n";
            logger.log(Level.SEVERE, errText);
            text.append(errText);
        }
        catch (Exception e)
        {
            logger.log(Level.SEVERE, "Error connecting to db.");
            text.append("Error connecting to db.");
        }
        return text.toString();
    }
    
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String deleteShop(String jobj) throws IOException
    {
        logger.log(Level.INFO, "RECEIVED DELETE REQUEST FOR:\n");
        logger.log(Level.INFO, "OBJECT:" + jobj + "\n");
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = mapper.readValue(jobj.toString(), Shop.class);
        StringBuilder text = new StringBuilder();
        try {
            Model db = Model.singleton();
            int shopid = shop.getShopid();
            db.deleteShop(shopid);
            logger.log(Level.INFO, "shop deleted from db=" + shopid);
            text.append("Shop id deleted with id=" + shopid);
        }
        catch (SQLException sqle)
        {
            String errText = "Error deleting shop after db connection made:\n" + sqle.getMessage() + " --- " + sqle.getSQLState() + "\n";
            logger.log(Level.SEVERE, errText);
            text.append(errText);
        }
        catch (Exception e)
        {
            logger.log(Level.SEVERE, "Error connecting to db.");
            text.append("Error connecting to db.");
        }
        return text.toString();
    }
    
//    @POST
//    @Produces(MediaType.TEXT_PLAIN)
//    @Consumes(MediaType.APPLICATION_JSON)
//    public String createShop(String jobj) throws IOException {
//        ObjectMapper mapper = new ObjectMapper();
//        Shop shop = mapper.readValue(jobj.toString(), Shop.class);
//        
//        StringBuilder text = new StringBuilder();
//        text.append("The JSON obj:" + jobj.toString() + "\n");
//        text.append("Shop:" + shop.getName() + "\n");
//        text.append("City:" + shop.getCity() + "\n");
//        text.append("State:" + shop.getState() + "\n");
//        text.append("Zip:" + shop.getZip() + "\n");
//        text.append("Phone:" + shop.getPhone() + "\n");
//        text.append("Open:" +shop.getOpen() + "\n");
//        text.append("Close:" +shop.getClose() + "\n");
//        text.append("Description:" +shop.getDescription() + "\n");
//        
//        try {
//            Model db = Model.singleton();
//            int shopid = db.newShop(shop);
//            logger.log(Level.INFO, "shop persisted to db as shopid=" + shopid);
//            text.append("Shop id persisted with id=" + shopid);
//        }
//        catch (SQLException sqle)
//        {
//            String errText = "Error persisting shop after db connection made:\n" + sqle.getMessage() + " --- " + sqle.getSQLState() + "\n";
//            logger.log(Level.SEVERE, errText);
//            text.append(errText);
//        }
//        catch (Exception e)
//        {
//            logger.log(Level.SEVERE, "Error connecting to db.");
//        }
//        
//        
//        return text.toString();
//    }
    
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public List<Shop> createShop(String jobj) throws IOException {
        logger.log(Level.INFO, "RECEIVED CREATE REQUEST FOR:\n");
        logger.log(Level.INFO, "OBJECT:" + jobj + "\n");
        
        LinkedList<Shop> lshops = new LinkedList<Shop>();

        ObjectMapper mapper = new ObjectMapper();
        Shop shop = mapper.readValue(jobj.toString(), Shop.class);
        
        StringBuilder text = new StringBuilder();
        text.append("The JSON obj:" + jobj.toString() + "\n");
        text.append("Shop:" + shop.getName() + "\n");
        text.append("Address:" + shop.getAddress() + "\n");
        text.append("City:" + shop.getCity() + "\n");
        text.append("State:" + shop.getState() + "\n");
        text.append("Zip:" + shop.getZip() + "\n");
        text.append("Phone:" + shop.getPhone() + "\n");
        text.append("Open:" +shop.getOpentime() + "\n");
        text.append("Close:" +shop.getClosetime() + "\n");
        text.append("Description:" +shop.getDescription() + "\n");
        
        try {
            Model db = Model.singleton();
            Shop shp = db.newShop(shop);
            logger.log(Level.INFO, "shop persisted to db as shopid=" + shp.getShopid());
            text.append("Shop id persisted with id=" + shp.getShopid());
            lshops.add(shp);
        }
        catch (SQLException sqle)
        {
            String errText = "Error persisting shop after db connection made:\n" + sqle.getMessage() + " --- " + sqle.getSQLState() + "\n";
            logger.log(Level.SEVERE, errText);
            text.append(errText);
        }
        catch (Exception e)
        {
            logger.log(Level.SEVERE, "Error connecting to db.");
        }
        
        return lshops;
    }
}


