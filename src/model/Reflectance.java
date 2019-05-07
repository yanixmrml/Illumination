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
public class Reflectance {
    
    private double percentReflectance;
    private ArrayList<WallReflectance> wallReflectances;

    public Reflectance() {
        this.percentReflectance = 0;
        this.wallReflectances = new ArrayList<>();
    }

    public Reflectance(double percentReflectance, ArrayList<WallReflectance> wallReflectances) {
        this.percentReflectance = percentReflectance;
        this.wallReflectances = wallReflectances;
    }

    public double getPercentReflectance() {
        return percentReflectance;
    }

    public void setPercentReflectance(double percentReflectance) {
        this.percentReflectance = percentReflectance;
    }

    public ArrayList<WallReflectance> getWallReflectances() {
        return wallReflectances;
    }

    public void setWallReflectances(ArrayList<WallReflectance> wallReflectances) {
        this.wallReflectances = wallReflectances;
    }
    
}
