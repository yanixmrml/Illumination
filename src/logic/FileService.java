/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import model.CU;
import model.CeilingEffectiveReflectance;
import model.Reflectance;
import model.WallEffectiveReflectance;
import model.WallReflectance;

/**
 *
 * @author YANIXMRML
 */
public class FileService {
    
 
    public static final ArrayList<Reflectance> uploadReflectanceTable(String filename) throws IOException{
        ArrayList<Reflectance> reflectances = new ArrayList<>();
        InputStream stream = FileService.class.getResourceAsStream(filename);
        BufferedReader buf = new BufferedReader(new InputStreamReader(stream));
        String line = null;
        while((line = buf.readLine())!=null){
            double primaryReflectance = Double.parseDouble(line.replace(",",""));
            int noWallRef = Integer.parseInt(buf.readLine().replace(",",""));
            Reflectance ref = new Reflectance();
            ArrayList<WallReflectance> wallRefs = new ArrayList<>();
            ref.setPercentReflectance(primaryReflectance);
            for(int i=0;i<noWallRef;i++){
                WallReflectance wall = new WallReflectance();
                ArrayList<Double> secondaryRefs = new ArrayList<>();
                double wallReflectance = Double.parseDouble(buf.readLine().replace(",",""));
                String strSecondary = buf.readLine();
                wall.setWallReflectance(wallReflectance);
                StringTokenizer str = new StringTokenizer(strSecondary," ,\t\n\r\f");
                while(str.hasMoreTokens()){
                    double secondaryRef = Double.parseDouble(str.nextToken());
                    secondaryRefs.add(secondaryRef);
                }
                wall.setSecondaryEffectiveReflectances(secondaryRefs);
                wallRefs.add(wall);
            }
            ref.setWallReflectances(wallRefs);
            reflectances.add(ref);
        }
        buf.close();
       return reflectances;
    }
    
    
    public static final ArrayList<CU> uploadCU20Table(String filepath) throws FileNotFoundException, IOException{
        ArrayList<CU> cuList = new ArrayList<>();
        File folder = new File(filepath);
        File[] listOfFiles = folder.listFiles();
        for(int j=0;j<listOfFiles.length; j++){
            String fname = listOfFiles[j].getName();
            //System.out.println(fname + " - " + fname.substring(fname.indexOf("."), fname.length()));
            if(listOfFiles[j].isFile() && (fname.substring(fname.indexOf("."), fname.length()).equalsIgnoreCase(".csv"))){
                BufferedReader buf = new BufferedReader(new FileReader(filepath + fname));
                String line = null;
                while((line = buf.readLine())!=null){
                    CU cu = new CU();
                    StringTokenizer str1 = new StringTokenizer(line," ,\t\n\r\f");
                    cu.setLuminaireNo(str1.nextToken());
                    cu.setLldCategory(Integer.parseInt(str1.nextToken()));
                    double floorEffectiveReflectance = Double.parseDouble(str1.nextToken());
                    int numCeil = Integer.parseInt(str1.nextToken());
                    ArrayList<CeilingEffectiveReflectance> ceils = new ArrayList<>();
                    for(int h=0;h<numCeil;h++){
                        CeilingEffectiveReflectance ceilEffective = new CeilingEffectiveReflectance();
                        ceilEffective.setCeilingEffectiveReflectance(Double.parseDouble(buf.readLine().replace(",","")));
                        int numWall = Integer.parseInt(buf.readLine().replace(",", ""));
                        ArrayList<WallEffectiveReflectance> walls = new ArrayList<>();
                        for(int i=0;i<numWall;i++){
                            WallEffectiveReflectance wall = new WallEffectiveReflectance();
                            wall.setRoomEffectiveReflectance(floorEffectiveReflectance);
                            String l = buf.readLine();
                            wall.setWallReflectance(Double.parseDouble(l.replace(",","")));
                            System.out.println(l);
                            StringTokenizer str2 = new StringTokenizer(buf.readLine(),", \t\n\r\f");

                            ArrayList<Double> cus = new ArrayList<>();
                            while(str2.hasMoreTokens()){
                                double c = Double.parseDouble(str2.nextToken());
                                cus.add(c>=10?(c/100):c);
                            }
                            wall.setCU(cus);
                            walls.add(wall);
                        }
                        ceilEffective.setWallEffectiveReflectances(walls);
                        ceils.add(ceilEffective);
                    }
                    cu.setCeilRef(ceils);
                    cuList.add(cu);
                }
                buf.close();
            }
        }
        return cuList;
    }
    
    
    public static final ArrayList<CU> uploadCUMultipliearTable(String filename) throws FileNotFoundException, IOException{
        ArrayList<CU> cuList = new ArrayList<>();
        InputStream stream = FileService.class.getResourceAsStream(filename);
        BufferedReader buf = new BufferedReader(new InputStreamReader(stream));
        String line = null;
        while((line = buf.readLine())!=null){
            CU cu = new CU();
            StringTokenizer str1 = new StringTokenizer(line," ,\t\n\r\f");
            double floorEffectiveReflectance = Integer.parseInt(str1.nextToken());
            cu.setCuMultiplierWallRef(floorEffectiveReflectance);
            int numCeil = Integer.parseInt(str1.nextToken());
            ArrayList<CeilingEffectiveReflectance> ceils = new ArrayList<>();
            for(int h=0;h<numCeil;h++){
                String l = buf.readLine();
                double r = Double.parseDouble(l.replace(",",""));
                CeilingEffectiveReflectance ceilEffective = new CeilingEffectiveReflectance();
                ceilEffective.setCeilingEffectiveReflectance(r);
                int numWall = Integer.parseInt(buf.readLine().replace(",", ""));
                ArrayList<WallEffectiveReflectance> walls = new ArrayList<>();
                for(int i=0;i<numWall;i++){
                    WallEffectiveReflectance wall = new WallEffectiveReflectance();
                    wall.setRoomEffectiveReflectance(floorEffectiveReflectance);
                    
                    wall.setWallReflectance(Double.parseDouble(buf.readLine().replace(",","")));
                    StringTokenizer str2 = new StringTokenizer(buf.readLine(),", \t\n\r\f");

                    ArrayList<Double> cus = new ArrayList<>();
                    while(str2.hasMoreTokens()){
                        double c = Double.parseDouble(str2.nextToken());
                        cus.add(c);
                    }
                    wall.setCU(cus);
                    walls.add(wall);
                }
                ceilEffective.setWallEffectiveReflectances(walls);
                ceils.add(ceilEffective);
            }
            cu.setCeilRef(ceils);
            cuList.add(cu);
        }
        buf.close();
        return cuList;
    }   
    
}
 