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
public class WallEffectiveReflectance {
    
    private double wallReflectance;
    private double roomEffectiveReflectance;
    private ArrayList<Double> CU;

    public WallEffectiveReflectance() {
        this.wallReflectance = 0;
        this.roomEffectiveReflectance = 0;
        this.CU = new ArrayList<>();
    }

    public WallEffectiveReflectance(double wallReflectance, double roomEffectiveReflectance, ArrayList<Double> CU) {
        this.wallReflectance = wallReflectance;
        this.roomEffectiveReflectance = roomEffectiveReflectance;
        this.CU = CU;
    }

    public double getWallReflectance() {
        return wallReflectance;
    }

    public void setWallReflectance(double wallReflectance) {
        this.wallReflectance = wallReflectance;
    }

    public double getRoomEffectiveReflectance() {
        return roomEffectiveReflectance;
    }

    public void setRoomEffectiveReflectance(double roomEffectiveReflectance) {
        this.roomEffectiveReflectance = roomEffectiveReflectance;
    }

    public ArrayList<Double> getCU() {
        return CU;
    }

    public void setCU(ArrayList<Double> CU) {
        this.CU = CU;
    }
    
}
