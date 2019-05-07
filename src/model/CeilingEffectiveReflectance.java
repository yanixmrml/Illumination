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
public class CeilingEffectiveReflectance {
    
    private double ceilingEffectiveReflectance;
    private ArrayList<WallEffectiveReflectance> wallEffectiveReflectances;

    public CeilingEffectiveReflectance() {
        this.ceilingEffectiveReflectance = 0;
        this.wallEffectiveReflectances = new ArrayList<>();
    }

    public CeilingEffectiveReflectance(double ceilingEffectiveReflectance, ArrayList<WallEffectiveReflectance> wallEffectiveReflectances) {
        this.ceilingEffectiveReflectance = ceilingEffectiveReflectance;
        this.wallEffectiveReflectances = wallEffectiveReflectances;
    }

       public double getCeilingEffectiveReflectance() {
        return ceilingEffectiveReflectance;
    }

    public void setCeilingEffectiveReflectance(double ceilingEffectiveReflectance) {
        this.ceilingEffectiveReflectance = ceilingEffectiveReflectance;
    }

    public ArrayList<WallEffectiveReflectance> getWallEffectiveReflectances() {
        return wallEffectiveReflectances;
    }

    public void setWallEffectiveReflectances(ArrayList<WallEffectiveReflectance> wallEffectiveReflectances) {
        this.wallEffectiveReflectances = wallEffectiveReflectances;
    }
    
    
    
}
