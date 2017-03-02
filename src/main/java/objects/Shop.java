/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;

import java.util.List;

/**
 *
 * @author nick
 */
public class Shop {

    private String name;
    private String city;
    private String state;
    private long zip;
    private long phone;
    private int openTime;
    private int closeTime;
    private String description;
    
    private int shopid;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;   
    }
    
    public void setState(String state) {
        this.state = state;   
    }

    public long getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }
    public long getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    
    public int getOpen() {
        return openTime;
    }

    public void setOpen(int openTime) {
        this.openTime = openTime;
    }
    
    public int getClose() {
        return closeTime;
    }

    public void setClose(int closeTime) {
        this.closeTime = closeTime;
    }
    

    public void setDescription(String decription) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }

    public int getShopId() {
        return shopid;
    }

    public void setShopId(int shopId) {
        this.shopid = shopId;
    }
    
    
    
    
}


