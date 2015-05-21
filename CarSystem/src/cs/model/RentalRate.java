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
public class RentalRate {
    private final StringProperty r_type;
    private final DoubleProperty w_rate;
    private final DoubleProperty d_rate;
    private final DoubleProperty h_rate;
    private final DoubleProperty k_rate;
    public RentalRate(String t,double w,double d, double h,double k){
        this.r_type=new SimpleStringProperty(t);
        this.w_rate=new SimpleDoubleProperty(w);
        this.d_rate=new SimpleDoubleProperty(d);
        this.h_rate=new SimpleDoubleProperty(h);
        this.k_rate=new SimpleDoubleProperty(k);     
        
    }
    public String getR_type(){
        return this.r_type.getValue();
    }
    public double getW_rate(){
        return this.w_rate.getValue();
    }
    public double getD_rate(){
        return this.d_rate.getValue();
    }
    public double getH_rate(){
        return this.h_rate.getValue();
    }
    public double getK_rate(){
        return this.k_rate.getValue();
    }
    public void setW_rate(double d){
        w_rate.set(d);
    }
    public void setD_rate(double d){
        w_rate.set(d);
    }
    public void setH_rate(double d){
        w_rate.set(d);
    }
    public void setK_rate(double d){
        w_rate.set(d);
    }
}
