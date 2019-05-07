/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author YANIXMRML
 */
public class CUPerCeilingEffective {
    
    private double ceilingEffectiveReflectance;
    private ArrayList<Double> cuList;

    public CUPerCeilingEffective() {
        this.ceilingEffectiveReflectance = 0;
        this.cuList = new ArrayList<>();
    }

    public CUPerCeilingEffective(double ceilingEffectiveReflectance, ArrayList<Double> cuList) {
        this.ceilingEffectiveReflectance = ceilingEffectiveReflectance;
        this.cuList = cuList;
    }

    public double getCeilingEffectiveReflectance() {
        return ceilingEffectiveReflectance;
    }

    public void setCeilingEffectiveReflectance(double ceilingEffectiveReflectance) {
        this.ceilingEffectiveReflectance = ceilingEffectiveReflectance;
    }

    public ArrayList<Double> getCuList() {
        return cuList;
    }

    public void setCuList(ArrayList<Double> cuList) {
        this.cuList = cuList;
    }
    
}
