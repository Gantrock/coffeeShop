/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author nick
 */
public class Review {
    private String myContent;
    private LocalDate myDate;
    private int reviewid;
    private int myWifi;
    private int myCoffee;
    private int myFood;
    private int myStudy;
    private int myReviewId;
    private int shopid;

    /**
     * @return the myContent
     */
    public String getMyContent() {
        return myContent;
    }

    /**
     * @param myContent the myContent to set
     */
    public void setMyContent(String myContent) {
        this.myContent = myContent;
    }

    /**
     * @return the myDate
     */
    public LocalDate getMyDate() {
        return myDate;
    }

    /**
     * @param myDate the myDate to set
     */
    public void setMyDate(LocalDate myDate) {
        this.myDate = myDate;
    }

 
    public int getShopid() {
        return reviewid;
    }

    
    public void setShopid(int reviewid) {
        this.reviewid = reviewid;
    }

    /**
     * @return the myWifi
     */
    public int getMyWifi() {
        return myWifi;
    }

    /**
     * @param myWifi the myWifi to set
     */
    public void setMyWifi(int myWifi) {
        this.myWifi = myWifi;
    }

    /**
     * @return the myCoffee
     */
    public int getMyCoffee() {
        return myCoffee;
    }

    /**
     * @param myCoffee the myCoffee to set
     */
    public void setMyCoffee(int myCoffee) {
        this.myCoffee = myCoffee;
    }

    /**
     * @return the myFood
     */
    public int getMyFood() {
        return myFood;
    }

    /**
     * @param myFood the myFood to set
     */
    public void setMyFood(int myFood) {
        this.myFood = myFood;
    }

    /**
     * @return the myStudy
     */
    public int getMyStudy() {
        return myStudy;
    }

    /**
     * @param myStudy the myStudy to set
     */
    public void setMyStudy(int myStudy) {
        this.myStudy = myStudy;
    }

    /**
     * @return the myReviewId
     */
    public int getMyReviewId() {
        return myReviewId;
    }

    /**
     * @param myReviewId the myReviewId to set
     */
    public void setMyReviewId(int myReviewId) {
        this.myReviewId = myReviewId;
    }







}
