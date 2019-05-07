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
public class CU {
    
    private String luminaireType;
    private int lldCategory;
    private double cuMultiplierFloorRef; 
    private ArrayList<CeilingEffectiveReflectance> ceilRef;
    private ArrayList<Double> cuMultiplierList;

    public CU() {
        this.lldCategory = 0;
        this.ceilRef = new ArrayList<>();
    }

    public CU(String luminaireType, int lldCategory, ArrayList<CeilingEffectiveReflectance> ceilRef) {
        this.luminaireType = luminaireType;
        this.lldCategory = lldCategory;
        this.ceilRef = ceilRef;
    }

    public String getLuminaireNo() {
        return luminaireType;
    }

    public void setLuminaireNo(String luminaireType) {
        this.luminaireType = luminaireType;
    }

    public int getLldCategory() {
        return lldCategory;
    }

    public void setLldCategory(int lldCategory) {
        this.lldCategory = lldCategory;
    }

    public ArrayList<CeilingEffectiveReflectance> getCeilRef() {
        return ceilRef;
    }

    public void setCeilRef(ArrayList<CeilingEffectiveReflectance> ceilRef) {
        this.ceilRef = ceilRef;
    }

    public double getCuMultiplierFloorRef() {
        return cuMultiplierFloorRef;
    }

    public void setCuMultiplierWallRef(double cuMultiplierFloorRef) {
        this.cuMultiplierFloorRef = cuMultiplierFloorRef;
    }

    public ArrayList<Double> getCuMultiplierList() {
        return cuMultiplierList;
    }

    public void setCuMultiplierList(ArrayList<Double> cuMultiplierList) {
        this.cuMultiplierList = cuMultiplierList;
    }
    
}
