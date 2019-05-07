/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import model.CU;
import model.CUPerCeilingEffective;
import model.CeilingEffectiveReflectance;
import model.Reflectance;
import model.ReflectancePerPrimary;
import model.Room;
import model.WallEffectiveReflectance;
import model.WallReflectance;

/**
 *
 * @author YANIXMRML
 */
public class MathService {
    
    // Primary Array is assumed to be linear from x = (x+1), where x = 0 (1,2,3,4,5,6)
    public final static double findEffectiveReflectance(double cavityRatio, ArrayList<Double> y){
        double ref = interpolate(cavityRatio,y,0.1);
        if(ref==0){
            ref = extrapolate(cavityRatio,y,0.1);
        }
        return ref;
    }
    
    public final static double findCU(double cavityRatio, ArrayList<Double> cuList){
        double cu = interpolate(cavityRatio,cuList,1);
        if(cu==0){
            cu = extrapolate(cavityRatio,cuList,1);
        }
        return cu;
    }
    
    // Primary Array is assumed to be linear from x = (x+1), where x = 0 (1,2,3,4,5,6)
    public final static double interpolate(double cavityRatio, ArrayList<Double> y, double multiplier){
        int size = y.size();
        for(int i=0;i<size;i++){
            double x1 =  (i+1) * multiplier;
            double x2 = (i+2) * multiplier;
                
            if(cavityRatio==x1){
                return y.get(i);
            }else if((i+2)<size
                    && cavityRatio>x1 && cavityRatio<x2){
                return y.get(i) + (((y.get(i+1) - y.get(i))/(x2 - x1)) * (cavityRatio - x1));
            }
        }
        return 0;
    } 
     
    
    public final static double extrapolate(double cavityRatio, ArrayList<Double> y, double multiplier){
        //Least-Square Regression Model
        double n = y.size();
        double sumX = 0;
        double sumY = 0;
        double sumX2 = 0;
        double sumXY = 0;
        double a0;
        double a1;
        double det;
        for(int i=0;i<n;i++){
            double x = ((i+1)*multiplier);
            sumX += x;
            sumY += y.get(i);
            sumX2 += (x * x);
            sumXY += (x * y.get(i));
        }
        
        
        det = (n*sumX2) - (sumX * sumX);
        a0 = ((sumY*sumX2) - (sumX * sumXY)) / det;
        a1 = ((n*sumXY) - (sumX * sumY)) / det;
        return (a0 + (a1 * cavityRatio));
    }
    
    public final static ArrayList<Double> extractInterpolatedCU(double wallRef, double wallRef1, ArrayList<Double> cuList1,
                    double wallRef2, ArrayList<Double> cuList2){
        ArrayList<Double> cuList = new ArrayList<>();
        int cuSize = cuList1.size(); //Assuming cuList1.size = cuList2.size
        for(int i=0;i<cuSize;i++){
            double cu1 = cuList1.get(i);
            double cu2 = cuList2.get(i);
            double cu =  cu1 + (((cu2 - cu1)/(wallRef2 - wallRef1)) * (wallRef - wallRef1));
            cuList.add(cu);
        }
        return cuList;
    }
    
    public final static ArrayList<Double> extractExtrapolatedCU(double wallEffectiveRef, ArrayList<WallEffectiveReflectance> walls){
        ArrayList<Double> cuList = new ArrayList<>();
        int wallSize = walls.size(); //Assuming cuList1.size = cuList2.size
        int cuSize = walls.get(0).getCU().size();
        for(int i=0;i<cuSize;i++){
            double sumX = 0;
            double sumY = 0;
            double sumX2 = 0;
            double sumXY = 0;
            double a0;
            double a1;
            double det;
            double cu;
            for(int j=0;j<wallSize;j++){
                WallEffectiveReflectance wall = walls.get(j);
                sumX += wall.getWallReflectance();
                sumY += wall.getCU().get(i);
                sumX2 += Math.pow(wall.getWallReflectance(), 2);
                sumXY += (wall.getWallReflectance() * wall.getCU().get(i));
            }
            det = (cuSize*sumX2) - Math.pow(sumX, 2);
            a0 = ((sumY*sumX2) - (sumX*sumXY)) / det;
            a1 = ((cuSize*sumXY) - (sumX*sumY)) / det;
            cu = (a0 + (a1 * wallEffectiveRef));
            cuList.add(cu);
        }
        return cuList;
    }
    
    public final static ArrayList<Double> extractInterpolatedCU(double ceilingEffectiveRef,double wallEffectiveRef,
            CeilingEffectiveReflectance c1, CeilingEffectiveReflectance c2){
        ArrayList<Double> cuList = new ArrayList<>();
        ArrayList<Double> cuList1 = extractCUPerCeiling(wallEffectiveRef,c1);
        ArrayList<Double> cuList2 = extractCUPerCeiling(wallEffectiveRef,c2);
        // NOTE: Let ceilingEffective as wallEffective since both c1 and c2 has extracted their own values for 
        // CU for the corresponding given wallEffective
        // Therefore the interpolatedCU will be dependent to ceilingEffective
        cuList = extractInterpolatedCU(ceilingEffectiveRef,c1.getCeilingEffectiveReflectance(),cuList1,
                c2.getCeilingEffectiveReflectance(),cuList2);
        return cuList;
    }
    
    public final static ArrayList<Double> extractCUPerCeiling(double wallEffectiveRef,CeilingEffectiveReflectance ceil){
        ArrayList<WallEffectiveReflectance> walls = ceil.getWallEffectiveReflectances();
        int wallSize = walls.size();
        for(int k=0;k<wallSize;k++){
            WallEffectiveReflectance wall = walls.get(k);
            if(wallEffectiveRef == wall.getWallReflectance()){
                //call calculate CU / (roomCavity, arrayList<double> CU)
                //return statement here
                return wall.getCU();
            }else if((k+1)<wallSize
                    && (wallEffectiveRef>walls.get(k).getWallReflectance()
                    &&(wallEffectiveRef<walls.get(k+1).getWallReflectance()))){
                //find the interpolate values of CU
                //call calculate CU / roomCavity, arrayList<double> CU
                ArrayList<Double> cuInterpolated = extractInterpolatedCU(wallEffectiveRef,walls.get(k).getWallReflectance() , 
                        walls.get(k).getCU(), walls.get(k+1).getWallReflectance(), walls.get(k+1).getCU());
                //return statement here
                return cuInterpolated;
            }
        }
        //extrapolate the values of CU
        ArrayList<Double> cuExtrapolated = extractExtrapolatedCU(wallEffectiveRef, walls);
        return cuExtrapolated;
    }
    
    /**
     *
     * @param wallEffectiveRef
     * @param ceilList
     * @return
     */
    public final static ArrayList<Double> extractExtrapolatedCUCeil(double wallEffectiveRef, ArrayList<CeilingEffectiveReflectance> ceilList){
        ArrayList<Double> cuList = new ArrayList<>();
        ArrayList<CUPerCeilingEffective> ceilNewList = new ArrayList<>();
        int ceilSize = ceilList.size();
        for(int i=0;i<ceilSize;i++){
            CeilingEffectiveReflectance oldCeil = ceilList.get(i);
            CUPerCeilingEffective cuCeil = new CUPerCeilingEffective();
            cuCeil.setCeilingEffectiveReflectance(oldCeil.getCeilingEffectiveReflectance());
            cuCeil.setCuList(MathService.extractCUPerCeiling(wallEffectiveRef, oldCeil));
            ceilNewList.add(cuCeil);
        }
        
        int cuCeilSize = ceilNewList.size();
        int cuSize = ceilNewList.get(0).getCuList().size();
        for(int i=0;i<cuSize;i++){
            double sumX = 0;
            double sumY = 0;
            double sumX2 = 0;
            double sumXY = 0;
            double a0;
            double a1;
            double det;
            double cu;
            for(int j=0;j<cuCeilSize;j++){
                CUPerCeilingEffective ceil = ceilNewList.get(j);
                sumX += ceil.getCeilingEffectiveReflectance();
                sumY += ceil.getCuList().get(i);
                sumX2 += Math.pow(ceil.getCeilingEffectiveReflectance(), 2);
                sumXY += (ceil.getCeilingEffectiveReflectance() * ceil.getCuList().get(i));
            }
            det = (cuSize*sumX2) - Math.pow(sumX, 2);
            a0 = ((sumY*sumX2) - (sumX*sumXY)) / det;
            a1 = ((cuSize*sumXY) - (sumX*sumY)) / det;
            cu = (a0 + (a1 * wallEffectiveRef));
            cuList.add(cu);
        }
        return cuList;
    }
    
    /**
     * @param wallRef*
     * @param w1
     * @param sList1
     * @param w2*
     * @param sList2
     * @return ************************************************/
    
    public static final ArrayList<Double> extractInterpolatedReflectance(double wallRef, double w1, ArrayList<Double> sList1, 
                double w2, ArrayList<Double> sList2){
        ArrayList<Double> secondRefList = new ArrayList<>();
        int secondSize = sList1.size(); //Assuming secondList1.size = secondList2.size
        for(int i=0;i<secondSize;i++){
            double s1 = sList1.get(i);
            double s2 = sList2.get(i);
            double second =  s1 + (((s2 - s1)/(w2 - w1)) * (wallRef - w1));
            secondRefList.add(second);
        }
        return secondRefList;
    }
    
    
    public final static ArrayList<Double> extractExtrapolatedReflectance(double wallRef, ArrayList<WallReflectance> walls){
        ArrayList<Double> secondRefList = new ArrayList<>();
        int wallSize = walls.size(); //Assuming cuList1.size = cuList2.size
        int secondSize = walls.get(0).getSecondaryEffectiveReflectances().size();
        for(int i=0;i<secondSize;i++){
            double sumX = 0;
            double sumY = 0;
            double sumX2 = 0;
            double sumXY = 0;
            double a0;
            double a1;
            double det;
            double second;
            for(int j=0;j<wallSize;j++){
                WallReflectance wall = walls.get(j);
                sumX += wall.getWallReflectance();
                sumY += wall.getSecondaryEffectiveReflectances().get(i);
                sumX2 += Math.pow(wall.getWallReflectance(), 2);
                sumXY += (wall.getWallReflectance() * wall.getSecondaryEffectiveReflectances().get(i));
            }
            det = (secondSize*sumX2) - Math.pow(sumX, 2);
            a0 = ((sumY*sumX2) - (sumX*sumXY)) / det;
            a1 = ((secondSize*sumXY) - (sumX*sumY)) / det;
            second = (a0 + (a1 * wallRef));
            secondRefList.add(second);
        }
        return secondRefList;
    }
    
    public final static ArrayList<Double> extractInterpolatedPrimary(double reflectance,double wallRef,
            Reflectance ref1, Reflectance ref2){
        ArrayList<Double> secondaryList = new ArrayList<>();
        ArrayList<Double> sList1 = extractReflectancePerPrimary(wallRef,ref1);
        ArrayList<Double> sList2 = extractReflectancePerPrimary(wallRef,ref2);
        // NOTE: Let ceilingEffective as wallEffective since both c1 and c2 has extracted their own values for 
        // CU for the corresponding given wallEffective
        // Therefore the interpolatedCU will be dependent to ceilingEffective
        secondaryList = extractInterpolatedReflectance(reflectance,ref1.getPercentReflectance(),sList1,
                ref2.getPercentReflectance(),sList2);
        return secondaryList;
    }
    
    public final static ArrayList<Double> extractReflectancePerPrimary(double wallRef,Reflectance reflectance){
        ArrayList<WallReflectance> walls = reflectance.getWallReflectances();
        int wallSize = walls.size();
        for(int k=0;k<wallSize;k++){
            WallReflectance wall = walls.get(k);
            if(wallRef == wall.getWallReflectance()){
                //call calculate CU / (roomCavity, arrayList<double> CU)
                //return statement here
                return wall.getSecondaryEffectiveReflectances();
            }else if((k+1)<wallSize
                    && (wallRef>walls.get(k).getWallReflectance()
                    &&(wallRef<walls.get(k+1).getWallReflectance()))){
                //find the interpolate values of CU
                //call calculate CU / roomCavity, arrayList<double> CU
                ArrayList<Double> secondaryInterpolated = extractInterpolatedCU(wallRef,walls.get(k).getWallReflectance() , 
                        walls.get(k).getSecondaryEffectiveReflectances(), 
                        walls.get(k+1).getWallReflectance(), walls.get(k+1).getSecondaryEffectiveReflectances());
                //return statement here
                return secondaryInterpolated;
            }
        }
        //extrapolate the values of CU
        ArrayList<Double> secondaryExtrapolated = extractExtrapolatedReflectance(wallRef, walls);
        return secondaryExtrapolated;
    }
    
    public final static ArrayList<Double> extractExtrapolatedReflectancePrimary(double wallEffectiveRef, ArrayList<Reflectance> refList){
        ArrayList<Double> secondList = new ArrayList<>();
        ArrayList<ReflectancePerPrimary> refNewList = new ArrayList<>();
        int refSize = refList.size();
        for(int i=0;i<refSize;i++){
            Reflectance oldRef = refList.get(i);
            ReflectancePerPrimary refPerPrimary = new ReflectancePerPrimary();
            refPerPrimary.setPercentReflectance(oldRef.getPercentReflectance());
            refPerPrimary.setSecondaryReflectances(MathService.extractReflectancePerPrimary(wallEffectiveRef, oldRef));
            refNewList.add(refPerPrimary);
        }
        
        int refPrimarySize = refNewList.size();
        int secondSize = refNewList.get(0).getSecondaryReflectances().size();
        for(int i=0;i<secondSize;i++){
            double sumX = 0;
            double sumY = 0;
            double sumX2 = 0;
            double sumXY = 0;
            double a0;
            double a1;
            double det;
            double secondary;
            for(int j=0;j<refPrimarySize;j++){
                ReflectancePerPrimary rf = refNewList.get(j);
                sumX += rf.getPercentReflectance();
                sumY += rf.getSecondaryReflectances().get(i);
                sumX2 += Math.pow(rf.getPercentReflectance(), 2);
                sumXY += (rf.getPercentReflectance() * rf.getSecondaryReflectances().get(i));
            }
            det = (secondSize*sumX2) - Math.pow(sumX, 2);
            a0 = ((sumY*sumX2) - (sumX*sumXY)) / det;
            a1 = ((secondSize*sumXY) - (sumX*sumY)) / det;
            secondary = (a0 + (a1 * wallEffectiveRef));
            secondList.add(secondary);
        }
        return secondList;
    }
    
    /****************************************************/
    
    
    
    private static ArrayList<Double> extractCUMultiplierPerCeil(double ceilingEffective, double wallEffective,
            CU cuEff){
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
                        System.out.println("EXTRAPOLATED WALL EFFECTIVE EQUAL ... " + wallEffective);
                        return  wall.getCU();
                    }else if((k+1)<wallSize && (k-1)>=0
                            && (wallEffective>walls.get(k-1).getWallReflectance()
                            &&(wallEffective<walls.get(k+1).getWallReflectance()))){
                        //find the interpolate values of CU
                        //call calculate CU / roomCavity, arrayList<double> CU
                        return MathService.extractInterpolatedCU(wallEffective,walls.get(k-1).getWallReflectance() , 
                                walls.get(k-1).getCU(), walls.get(k+1).getWallReflectance(), walls.get(k+1).getCU());
                        //return statement here
                    }
                }
                //extrapolate the values of CU
                return MathService.extractExtrapolatedCU(wallEffective, walls);
                //return statement here
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
                return MathService.extractInterpolatedCU(ceilingEffective,
                        wallEffective,ceilList.get(j-1),ceilList.get(j+1));
                //return statement here
            }
        }
        //CASE 3: OUTSIDE - Extrapolate
        // call extrapolate CU - > ArrayList<double> CU
        // -> inside method: for every ceil, find correponding wall if no(inter/extra) 
        // used that values to generate equation
        // 
        return MathService.extractExtrapolatedCUCeil(wallEffective, ceilList);
        //return statement here
    }
    
    
    
    public static ArrayList<Double> extractInterpolatedCUMultipler(double ceilingEffective, double wallEffective,
            double actualFloorEffective, CU c1, CU c2){
        ArrayList<Double> cuMultiplier = new ArrayList<>();
        //ceilingRef,wallRef,actualFloorEffective, cu1, cu2
        //Set floor1, floor2
        //Locate the appropriate CU for each ceil
        //Use floor1 and floor2 as X values, CU multipliers as Y values
        double floor1 = c1.getCuMultiplierFloorRef();
        double floor2 = c2.getCuMultiplierFloorRef();
        ArrayList<Double> cList1 = MathService.extractCUMultiplierPerCeil(ceilingEffective, wallEffective,c1);
        ArrayList<Double> cList2 = MathService.extractCUMultiplierPerCeil(ceilingEffective, wallEffective,c2);
        int cSize = cList1.size();
        for(int i=0;i<cSize;i++){
            cuMultiplier.add(cList1.get(i) + (( (cList2.get(i) - cList1.get(i)) / (floor2 - floor1)) * (actualFloorEffective - floor1)));
        }
        return cuMultiplier;
    }
    
    public static ArrayList<Double> extractExtrapolatedCUMultipler(double ceilingEffective, double wallEffective,
            double actualFloorEffective, ArrayList<CU> cuList){
        ArrayList<Double> cuMultiplier = new ArrayList<>();
        ArrayList<CU> cuNewList = new ArrayList<CU>();
        int cuSize = cuList.size();
        for(int i=0;i<cuSize;i++){
            CU cu = cuList.get(i);
            System.out.println("EXTRAPOLATE MULTIPLIER - CU " + actualFloorEffective);
            ArrayList<Double> extractedMultiplier = MathService.extractCUMultiplierPerCeil(ceilingEffective, wallEffective, cu);
            cu.setCuMultiplierList(extractedMultiplier);
            cuNewList.add(cu);
        }
            
        double sumX = 0;
        double sumY = 0;
        double sumX2 = 0;
        double sumXY = 0;
        double a0;
        double a1;
        double det;
        int multiplierSize = cuNewList.get(0).getCuMultiplierList().size();
        for(int j=0;j<multiplierSize;j++){
            for(int k=0;k<cuSize;k++){
                CU mul = cuNewList.get(k);
                sumX += mul.getCuMultiplierFloorRef();
                System.out.print("cu extracted: " + mul.getCuMultiplierList().get(j) + ", ");
                sumY += mul.getCuMultiplierList().get(j);
                sumX2 += Math.pow(mul.getCuMultiplierFloorRef(), 2);
                sumXY += (mul.getCuMultiplierFloorRef() * mul.getCuMultiplierList().get(j));
            }
            det = (cuSize*sumX2) - Math.pow(sumX, 2);
            a0 = ((sumY*sumX2) - (sumX*sumXY)) / det;
            a1 = ((cuSize*sumXY) - (sumX*sumY)) / det;
            double newCU = a0 + (a1 * actualFloorEffective);
            System.out.println("NEW CU - " + newCU);
            cuMultiplier.add(newCU);
        }
        System.out.println();
        return cuMultiplier;
    }
    
    
    /**
     * @param room*
     * @return *************************************************/
    
    public static final double calculateArea(Room room){
        double area = 0;
        switch(room.getType()){
            case Room.A_Rectangular:
                area = room.getLength() * room.getWidth();
                break;
            case Room.A_Circular:
                area = Math.PI * Math.pow(room.getRadius(), 2);
                break;
            case Room.A_Semicircular:
                area = (Math.PI * room.getAngle()/360) * Math.pow(room.getRadius(),2);
                double temp = 0;
                if(room.getLength()>0){
                    temp = (room.getLength() * room.getWidth());
                    area = temp - area;
                }
                break;
            case Room.A_Trapezoidal:
                area = (room.getLength() * (room.getBase1() + room.getBase2()) / 2);
        }
        return area<=0?-1:area;
    }
    
    public static final double calculatePerimeter(Room room){
        double perimeter = 0;
        switch(room.getType()){
            case Room.A_Rectangular:
                perimeter = 2 * (room.getLength() + room.getWidth());
                break;
            case Room.A_Circular:
                perimeter = 2 * Math.PI * room.getRadius();
                break;
            case Room.A_Semicircular:
                if(room.getLength()>0){
                    perimeter = (room.getLength() + room.getWidth() + (room.getLength() - room.getRadius()) + (room.getWidth() - room.getRadius())) + ((Math.PI * room.getAngle()/180) * room.getRadius());
                }else{
                    perimeter = ((Math.PI * room.getAngle()/180) * room.getRadius()) + 2 * (room.getRadius());
                }
                break;
            case Room.A_Trapezoidal:
                System.out.println("BASE1 " + room.getBase1() + " BASE2 " + room.getBase2());
                double smallBase  = (room.getBase1() - room.getBase2()) / 2;
                System.out.println("SMALLBASE " + smallBase);
                double side = Math.sqrt((room.getLength() * room.getLength()) + (smallBase * smallBase));
                perimeter = room.getBase1() + room.getBase2() + (2 * side);
                System.out.println("PERIMETER " + perimeter);
        }
        return perimeter<=0?-1:perimeter;
    }
    
    public static final double calculateCavityRatio(double height, Room room){
        return 2.5 * (height* room.getPermiter()) / room.getArea();
    }
    
   
}
