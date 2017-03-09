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
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import objects.Review;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * REST Web Service
 *
 * @author nick
 */
@Path("reviews")
public class ReviewService {

    static final Logger logger = Logger.getLogger(ReviewService.class.getName());
    
    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public ReviewService() {
    }

    /**
     * Retrieves representation of an instance of services.GenericResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getReviews() {
        //TODO return proper representation object
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body><style>table, th, td {font-family:Arial,Verdana,sans-serif;"
                + "font-size:16px;padding: 0px;border-spacing: 0px;}</style><b>REVIEWS LIST:"
                + "</b><br><br><table cellpadding=10 border=1><tr><td>Content</td><td>Date</td>"
                + "<td>Shop ID</td><td>Wifi</td><td>Coffee</td><td>Food</td><td>Study</td>"
                + "<td>Review ID</td></tr>");
        try
        {
            Model db = Model.singleton();
            Review[] reviews = db.getReviews();
            for (int i=0;i<reviews.length;i++)
                sb.append("<tr><td>" + reviews[i].getMyContent() + "</td><td>" + reviews[i].getMyDate() 
                		+ "</td><td>" + reviews[i].getShopid()  + "</td><td>" + reviews[i].getMyWifi()
                		+ "</td><td>" + reviews[i].getMyCoffee()  + "</td><td>" + reviews[i].getMyFood()
                		+ "</td><td>" + reviews[i].getMyStudy()  + "</td><td>" + reviews[i].getMyReviewId());
        }
        catch (Exception e)
        {
            sb.append("</table><br>Error getting reviews: " + e.toString() + "<br>");
        }
        sb.append("</table></body></html>");
        return sb.toString();
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     * @param content representation for the resource
     */
    @PUT
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateReview(String jobj) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        Review review = mapper.readValue(jobj.toString(), Review.class);
        StringBuilder text = new StringBuilder();
        try {
            Model db = Model.singleton();
            int reviewid = review.getMyReviewId();
            db.updateReview(review);
            logger.log(Level.INFO, "update review with reviewid=" + reviewid);
            text.append("Review id updated with review id=" + reviewid + "\n");
        }
        catch (SQLException sqle)
        {
            String errText = "Error updating review after db connection made:\n" + sqle.getMessage() + " --- " + sqle.getSQLState() + "\n";
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
    public String deleteReview(String jobj) throws IOException
    {
        ObjectMapper mapper = new ObjectMapper();
        Review review = mapper.readValue(jobj.toString(), Review.class);
        StringBuilder text = new StringBuilder();
        try {
            Model db = Model.singleton();
            int reviewid = review.getMyReviewId();
            db.deleteReview(reviewid);
            logger.log(Level.INFO, "review deleted from db=" + reviewid);
            text.append("Review id deleted with id=" + reviewid);
        }
        catch (SQLException sqle)
        {
            String errText = "Error deleteing review after db connection made:\n" + sqle.getMessage() + " --- " + sqle.getSQLState() + "\n";
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
    
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String createReview(String jobj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        Review review = mapper.readValue(jobj.toString(), Review.class);
        LocalDate localDate = LocalDate.now();
        StringBuilder text = new StringBuilder();
        text.append("The JSON obj:" + jobj.toString() + "\n");
        text.append("Review:" + review.getMyContent() + "\n");
        text.append("Date:" + localDate + "\n");
        text.append("Shop ID:" + review.getShopid() + "\n");
        text.append("Wifi:" + review.getMyWifi() + "\n");
        text.append("Coffee:" + review.getMyCoffee() + "\n");
        text.append("Food:" + review.getMyFood() + "\n");
        text.append("Study:" +review.getMyStudy() + "\n");
        text.append("Review ID:" +review.getMyReviewId() + "\n");
        
        try {
            Model db = Model.singleton();
            int reviewid = db.newReview(review);
            logger.log(Level.INFO, "review persisted to db as reviewid=" + reviewid);
            text.append("Review id persisted with id=" + reviewid);
        }
        catch (SQLException sqle)
        {
            String errText = "Error persisting review after db connection made:\n" + sqle.getMessage() + " --- " + sqle.getSQLState() + "\n";
            logger.log(Level.SEVERE, errText);
            text.append(errText);
        }
        catch (Exception e)
        {
            logger.log(Level.SEVERE, "Error connecting to db.");
        }
        
        
        return text.toString();
    }
}

