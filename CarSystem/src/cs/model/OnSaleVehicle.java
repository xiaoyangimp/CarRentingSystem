/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author whitney-pc
 */
public class OnSaleVehicle {
    private final StringProperty vid;
    private final DoubleProperty price;
    public OnSaleVehicle(String id,double p){
        vid=new SimpleStringProperty(id);
        price=new SimpleDoubleProperty(p);
    }
    public String getVid(){
        return vid.getValue();
    }
    public double getPrice(){
        return price.getValue();
    }
}
