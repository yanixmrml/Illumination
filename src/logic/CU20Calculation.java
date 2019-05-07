/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import model.CU;
import model.CeilingEffectiveReflectance;
import model.WallEffectiveReflectance;

/**
 *
 * @author YANIXMRML
 * 
 */
public class CU20Calculation {
    
    private ArrayList<CU> cuList;

    public CU20Calculation() {
        this.cuList = new ArrayList<>();
    }
    
    public CU20Calculation(ArrayList<CU> cuList) {
        this.cuList = cuList;
    }
    
    public double calculateCU20(String llType, double ceilingEffective, double wallEffective,
            double roomCavity){
        int cuSize = cuList.size();
        for(int i=0;i<cuSize;i++){
            CU cuEff = cuList.get(i);
            if(llType.equalsIgnoreCase(cuEff.getLuminaireNo())){
                return calculateInside(ceilingEffective, wallEffective, roomCavity, cuEff);
            }
        }
        //check the lld main type
        //check the llno
        //check the ceiling
        // if equal got to wall => 
        // else in between - interpolate values for wall  => new wall values
        // else - extrapolate values for wall => new wall values
        // proceed to CU by locating RCR, used the obtained wall values
        return 0;
    }

    public static double calculateInside(double ceilingEffective, double wallEffective,
            double roomCavity, CU cuEff){
        ArrayList<CeilingEffectiveReflectance> ceilList = cuEff.getCeilRef();
        int ceilSize = ceilList.size();
        for(int j=0;j<ceilSize;j++){
            CeilingEffectiveReflectance ceil = ceilList.get(j);
            if(ceilingEffective==ceil.getCeilingEffectiveReflectance()){
                //CASE 1: EQUAL
                //locate Wall
                ArrayList<WallEffectiveReflectance> walls = ceil.getWallEffectiveReflectances();
                int wallSize = walls.size();
                for(int k=0;k<wallSize;k++){
                    WallEffectiveReflectance wall = walls.get(k);
                    if(wallEffective == wall.getWallReflectance()){
                        //call calculate CU / (roomCavity, arrayList<double> CU)
                        //return statement here
                        return MathService.findCU(roomCavity, wall.getCU());
                    }else if((k+1)<wallSize && (k-1)>=0
                            && (wallEffective>walls.get(k-1).getWallReflectance()
                            &&(wallEffective<walls.get(k+1).getWallReflectance()))){
                        //find the interpolate values of CU
                        //call calculate CU / roomCavity, arrayList<double> CU
                        ArrayList<Double> cuInterpolated = MathService.extractInterpolatedCU(wallEffective,walls.get(k-1).getWallReflectance() , 
                                walls.get(k-1).getCU(), walls.get(k+1).getWallReflectance(), walls.get(k+1).getCU());
                        //return statement here
                        return MathService.findCU(roomCavity, cuInterpolated);
                    }
                }
                //extrapolate the values of CU
                ArrayList<Double> cuExtrapolated = MathService.extractExtrapolatedCU(wallEffective, walls);
                //return statement here
                return MathService.findCU(roomCavity, cuExtrapolated);
            }else if((j+1)<ceilSize && (j-1)>=0
                    &&((ceilingEffective>ceilList.get(j-1).getCeilingEffectiveReflectance()
                    && (ceilingEffective<ceilList.get(j+1).getCeilingEffectiveReflectance())))){
                //CASE 2: IN BETWEEN - Interpolate
                //call extract interpolatedCUValues<double> CU
                //      -> param: ceilingEffective, wallEffective
                //            ceilEffective (j-1) , ceilEffective (j+1)
                //  In method: find the corresponding wall-> use Wall as X
                //          CU as Y
                // After extracting interpolatedCU
                //call calculate CU / roomCavity, arrayList<double> CU
                ArrayList<Double> cuInterpolated = MathService.extractInterpolatedCU(ceilingEffective,
                        wallEffective,ceilList.get(j-1),ceilList.get(j+1));
                //return statement here

                return MathService.findCU(roomCavity, cuInterpolated);
            }
        }
        //CASE 3: OUTSIDE - Extrapolate
        // call extrapolate CU - > ArrayList<double> CU
        // -> inside method: for every ceil, find correponding wall if no(inter/extra) 
        // used that values to generate equation
        // 
        ArrayList<Double> cuExtrapolated = MathService.extractExtrapolatedCUCeil(wallEffective, ceilList);
        //return statement here
        System.out.println("EXTRAPOLATED SIZE---" + cuExtrapolated.size());
        return MathService.findCU(roomCavity, cuExtrapolated);
    }
    
    public ArrayList<CU> getCuList() {
        return cuList;
    }

    public void setCuList(ArrayList<CU> cuList) {
        this.cuList = cuList;
    }
    
}
