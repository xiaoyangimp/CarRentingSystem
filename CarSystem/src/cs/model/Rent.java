/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author whitney-pc
 */
public class Rent {
    private final StringProperty category;
    private final StringProperty branch;
    private final IntegerProperty count;
    private final DoubleProperty fee;
    public Rent(String c, int co,double f,String b){
        category=new SimpleStringProperty(c);
        branch=new SimpleStringProperty(b);
        count=new SimpleIntegerProperty(co);
        fee=new SimpleDoubleProperty(f);
    }
    public String getCategory(){
        return category.getValue();
    }
    public String getBranch(){
        return branch.getValue();
    }
    public int getCount(){
        return count.getValue();
    }
    public double getFee(){
        return fee.getValue();
    }
}
