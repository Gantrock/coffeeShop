/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  nick
 * Created: Feb 15, 2017
 */

create table shops (
    name varchar(40),
    address varchar(60),
    city varchar(40),
    state varchar(40),
    zip integer,
    phone varchar(15),
    opentime integer,
    closetime integer,
    description varchar(200),
    shopid serial primary key
);

create table reviews (
    myContent varchar(500),
    myDate date,
    shopid integer,
    myWifi integer,
    myCoffee integer,
    myFood integer,
    myStudy integer,
    reviewid serial primary key
);


