/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fhs.vision;

/**
 *
 * @author Developer
 */


import edu.wpi.first.wpilibj.image.*;
import edu.wpi.first.wpilibj.camera.*;
/**
 *
 * @author Developer
 */
public class getAndWriteImage {

    public AxisCamera camera;


    public getAndWriteImage(){
       camera = AxisCamera.getInstance();
    }

    public void getAndWriteImage(){
        writeImage(getImage());
    }

    public void writeImage(ColorImage clrImg){
        try{
            clrImg.write("C://Users/Developer/Desktop/CameraJPG.jpg");
        }

        catch(NIVisionException nie){
            nie.printStackTrace();
        }
    }


    public ColorImage getImage(){

        ColorImage clrImg =null;

       try {
           clrImg = camera.getImage();
       }

       catch (AxisCameraException ace){
           ace.printStackTrace();
       }

        catch (NIVisionException nie){
            nie.printStackTrace();
        }
        return clrImg;

    }


    }


