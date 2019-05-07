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
public class ReflectancePerPrimary {
    
    private double percentReflectance;
    private ArrayList<Double> secondaryReflectances;

    public ReflectancePerPrimary() {
        this.percentReflectance = 0;
        this.secondaryReflectances = new ArrayList<>();
    }

    public ReflectancePerPrimary(double percentReflectance, ArrayList<Double> secondaryReflectances) {
        this.percentReflectance = percentReflectance;
        this.secondaryReflectances = secondaryReflectances;
    }

    public double getPercentReflectance() {
        return percentReflectance;
    }

    public void setPercentReflectance(double percentReflectance) {
        this.percentReflectance = percentReflectance;
    }

    public ArrayList<Double> getSecondaryReflectances() {
        return secondaryReflectances;
    }

    public void setSecondaryReflectances(ArrayList<Double> secondaryReflectances) {
        this.secondaryReflectances = secondaryReflectances;
    }
}
