/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import model.Reflectance;
import model.WallReflectance;

/**
 *
 * @author YANIXMRML
 */
public class EffectiveReflectanceCalculation {

    private ArrayList<Reflectance> refList;

    public EffectiveReflectanceCalculation() {
        this.refList = new ArrayList<>();
    }
    

    public EffectiveReflectanceCalculation(ArrayList<Reflectance> refList) {
        this.refList = refList;
    }
    
    public double calculateEffectiveReflectance(double cavityRatio, double percentReflectance, double wallReflectance){
        double wallRef = Math.round(wallReflectance);
        if(cavityRatio <= 0){
            return percentReflectance;    
        }else{
            int size = refList.size();
            
            for(int i =0; i< size;i++){
                Reflectance ref = refList.get(i);
                if(ref.getPercentReflectance() == percentReflectance){
                    //CASE 1: EXACT VALUES of PRIMARY REFLECTANCE
                    Reflectance primaryRef = refList.get(i);
                    ArrayList<WallReflectance> walls = primaryRef.getWallReflectances();
                    int wallSize = walls.size();
                    for(int j=0;j<wallSize;j++){
                        WallReflectance wall = walls.get(j);
                        if(wallRef == wall.getWallReflectance()){
                            //CASE 1: EXACT WALL
                            return MathService.findEffectiveReflectance(cavityRatio,wall.getSecondaryEffectiveReflectances());
                        }else if((j+1)<wallSize && ((wallRef > walls.get(j).getWallReflectance())
                                && (wallRef < walls.get(j+1).getWallReflectance()))){
                            //CASE 2: INTERPOLATE WALL
                            ArrayList<Double> extractInterpolatedSecondary = MathService.extractInterpolatedReflectance(wallRef, walls.get(j).getWallReflectance(),
                                    walls.get(j).getSecondaryEffectiveReflectances(), walls.get(j+1).getWallReflectance(), walls.get(j+1).getSecondaryEffectiveReflectances());
                            //MathService
                            return MathService.findEffectiveReflectance(cavityRatio, extractInterpolatedSecondary);
                        }
                    }
                    //CASE 3: EXTRAPOLATE WALL
                    ArrayList<Double> extractExtrapolatedSecondary = MathService.extractExtrapolatedReflectance(wallRef, walls);
                    //MathService
                    return MathService.findEffectiveReflectance(cavityRatio, extractExtrapolatedSecondary);
                }else if((i+1)<size && (refList.get(i).getPercentReflectance()<percentReflectance
                        && refList.get(i+1).getPercentReflectance()>percentReflectance)){
                    //CASE 2: INTERPOLATE: IN BETWEEN PRIMARY VALUES
                    ArrayList<Double> extractInterpolatedSecondary = MathService.extractInterpolatedPrimary(percentReflectance, wallRef, 
                            refList.get(i), refList.get(i+1));
                    //MathService
                    return MathService.findEffectiveReflectance(cavityRatio, extractInterpolatedSecondary);
                }
            }
            //CASE 3: EXTRAPOLATE
            ArrayList<Double> extractExtrapolatedSecondary = MathService.extractExtrapolatedReflectancePrimary(wallReflectance, refList);
            //MathService
            return MathService.findEffectiveReflectance(cavityRatio, extractExtrapolatedSecondary);
        }
    }

    public ArrayList<Reflectance> getList() {
        return refList;
    }

    public void setList(ArrayList<Reflectance> list) {
        this.refList = list;
    }
}
