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
    address varchar(40),
    hours varchar(30),
    phone char(10),
    food varchar(200),
    shopid serial primary key
);


