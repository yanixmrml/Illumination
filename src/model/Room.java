/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author YANIXMRML
 */
public class Room {
    
    public static final String A_Rectangular = "Rectangular"; //Rectangular
    public static final String A_Circular = "Circular"; //Circular
    public static final String A_Semicircular = "Semi-circular"; //Semicircular
    public static final String A_Trapezoidal = "Trapezoidal"; //Trapezoid
    
    private String type; // Type 1 - Rectangular, Type 2 - Circular, Type 3 - Semi-circular, Type 4 - Trapezoidal;
    private double desiredFlux;
    private double length;
    private double width;
    private double angle;
    private double radius;
    private double base1;
    private double base2;
    private double area;
    private double permiter;
    private double height;
    private double hc;
    private double hr;
    private double hf;
    private double ccr;
    private double rcr;
    private double fcr;
    private double approxCeilRef;
    private double approxWallRef;
    private double approxFloorRef;
    private String   llType;
    private double effectiveCeilRef;
    private double effectiveFloorRef;
    
    public Room() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public double getPermiter() {
        return permiter;
    }

    public void setPermiter(double permiter) {
        this.permiter = permiter;
    }

    public double getHc() {
        return hc;
    }

    public void setHc(double hc) {
        this.hc = hc;
    }

    public double getHr() {
        return hr<=0?(height-hc-hf):hr;
    }

    public void setHr(double hr) {
        this.hr = hr;
    }

    public double getHf() {
        return hf;
    }

    public void setHf(double hf) {
        this.hf = hf;
    }

    public double getCcr() {
        return ccr;
    }

    public void setCcr(double ccr) {
        this.ccr = ccr;
    }

    public double getRcr() {
        return rcr;
    }

    public void setRcr(double rcr) {
        this.rcr = rcr;
    }

    public double getFcr() {
        return fcr;
    }

    public void setFcr(double fcr) {
        this.fcr = fcr;
    }

    public double getApproxCeilRef() {
        return approxCeilRef;
    }

    public void setApproxCeilRef(double approxCeilRef) {
        this.approxCeilRef = approxCeilRef;
    }

    public double getApproxWallRef() {
        return approxWallRef;
    }

    public void setApproxWallRef(double approxWallRef) {
        this.approxWallRef = approxWallRef;
    }

    public double getApproxFloorRef() {
        return approxFloorRef;
    }

    public void setApproxFloorRef(double approxFloorRef) {
        this.approxFloorRef = approxFloorRef;
    }

    public String getLlno() {
        return llType;
    }

    public void setLlType(String llType) {
        this.llType = llType;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getDesiredFlux() {
        return desiredFlux;
    }

    public void setDesiredFlux(double desiredFlux) {
        this.desiredFlux = desiredFlux;
    }

    public double getEffectiveCeilRef() {
        return effectiveCeilRef;
    }

    public void setEffectiveCeilRef(double effectiveCeilRef) {
        this.effectiveCeilRef = effectiveCeilRef;
    }

    public double getEffectiveFloorRef() {
        return effectiveFloorRef;
    }

    public void setEffectiveFloorRef(double effectiveFloorRef) {
        this.effectiveFloorRef = effectiveFloorRef;
    }

    public double getBase1() {
        return base1;
    }

    public void setBase1(double base1) {
        this.base1 = base1;
    }

    public double getBase2() {
        return base2;
    }

    public void setBase2(double base2) {
        this.base2 = base2;
    }
    
    
    
}
