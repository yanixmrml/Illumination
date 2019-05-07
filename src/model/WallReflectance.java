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
public class WallReflectance {
    
    private double wallReflectance;
    private ArrayList<Double> secondaryReflectances;

    public WallReflectance() {
        this.wallReflectance = 0;
        this.secondaryReflectances = new ArrayList<Double>();
    }

    public WallReflectance(double wallReflectance, ArrayList<Double> secondaryReflectances) {
        this.wallReflectance = wallReflectance;
        this.secondaryReflectances = secondaryReflectances;
    }

    public double getWallReflectance() {
        return wallReflectance;
    }

    public void setWallReflectance(double wallReflectance) {
        this.wallReflectance = wallReflectance;
    }

    public ArrayList<Double> getSecondaryEffectiveReflectances() {
        return secondaryReflectances;
    }

    public void setSecondaryEffectiveReflectances(ArrayList<Double> secondaryReflectances) {
        this.secondaryReflectances = secondaryReflectances;
    }
    
}
