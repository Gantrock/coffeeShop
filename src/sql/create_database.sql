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
    ciy varchar(40),
    state varchar(40),
    zip long,
    phone long,
    openTime integer,
    closeTime integer,
    description varchar(200),
    shopid serial primary key
);


