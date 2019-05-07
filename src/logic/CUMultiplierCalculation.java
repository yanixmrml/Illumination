/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import model.CU;

/**
 *
 * @author YANIXMRML
 */
public class CUMultiplierCalculation {
    
    private ArrayList<CU> cuMultiplierList;

    public CUMultiplierCalculation() {
        this.cuMultiplierList = new ArrayList<>();
    }
    
    public double calculateCUMultiplier(double ceilingEffective, double wallEffective, double floorEffective, 
            double roomCavity){
        if(floorEffective==20){
            return 1;
        }
        int cuSize = cuMultiplierList.size();
        for(int i=0;i<cuSize;i++){
            CU cuMul = cuMultiplierList.get(i);
            if(cuMul.getCuMultiplierFloorRef()==floorEffective){
                return CU20Calculation.calculateInside(ceilingEffective, wallEffective, roomCavity, cuMul);
            }else if((i+1)<cuSize && cuMultiplierList.get(i).getCuMultiplierFloorRef()<floorEffective
                    && floorEffective<cuMultiplierList.get(i+1).getCuMultiplierFloorRef()){
                //In Between
                //Interpolate
                
                ArrayList<Double> extractedInterpolatedMultiplier = MathService.extractInterpolatedCUMultipler(ceilingEffective, 
                        wallEffective, floorEffective, cuMultiplierList.get(i), cuMultiplierList.get(i+1));
                return MathService.findCU(roomCavity, extractedInterpolatedMultiplier);
            }
        }
        //Extrapolate
        ArrayList<Double> extractExtrapolatedMultiplier = MathService.extractExtrapolatedCUMultipler(ceilingEffective, wallEffective, floorEffective, cuMultiplierList);
        return MathService.findCU(roomCavity, extractExtrapolatedMultiplier);
    }

    public ArrayList<CU> getCuMultiplierList() {
        return cuMultiplierList;
    }

    public void setCuMultiplierList(ArrayList<CU> cuMultiplierList) {
        this.cuMultiplierList = cuMultiplierList;
    }
    
}
