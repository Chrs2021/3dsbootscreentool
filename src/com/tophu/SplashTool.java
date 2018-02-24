
/*
 * Copyright (c) 23/2/2018 Topher Campbelll
 */

package com.tophu;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.util.ArrayList;

public class SplashTool {
    ArrayList<Byte> mProcessed;
    String mPath;
    String mOutputName;
    byte framerate;

    public SplashTool(String path, String outputname, int framerate){
        mPath = path;
        mProcessed = new ArrayList<>();
        mOutputName = outputname;
        mProcessed.add((byte) framerate);
    }

    public File buildFile()
    {
        FilenameFilter pngSupport = new FilenameFilter() {
            @Override
            public boolean accept(File file, String s) {
              if(s.toLowerCase().endsWith(".png"))
                  return true;
              else
                  return false;
            }
        };

        File path = new File(mPath);
        processFileList(path.listFiles(pngSupport));
        return null;
    }

    private void processFileList(File[] files) {

        int cur;

        for (File file : files) {
            if (file.isFile()) {
                 try {
                    System.out.println(String.format("Processing: %s", file.getName()));
                     BufferedImage image = ImageIO.read(file);

                     int h = image.getHeight(),w = image.getWidth();

                     BufferedImage imageBGR = new BufferedImage(h,w,BufferedImage.TYPE_3BYTE_BGR);
                     AffineTransform rotTransform = new AffineTransform();
                     rotTransform.translate((h-w)/2,(w-h)/2);
                     rotTransform.rotate(Math.toRadians(90), w/2,  h/2);

                     AffineTransformOp rotOp = new AffineTransformOp( rotTransform, AffineTransformOp.TYPE_BILINEAR);

                     image = rotOp.filter(image,null);
                     imageBGR.getGraphics().drawImage(image,0,0,null);

                     byte[] pixels = ((DataBufferByte) imageBGR.getRaster().getDataBuffer()).getData();
                     for (int i = 0; i <pixels.length; i++) {
                         mProcessed.add(pixels[i]);
                     }
                 } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        writeData();
    }

    private void  writeData(){

        File out = new File(mOutputName);
        try {
            out.createNewFile();
            FileOutputStream fos = new FileOutputStream(out);
            byte[] result = new byte[mProcessed.size()];
            for(int i = 0; i < mProcessed.size(); i++) {
                result[i] = mProcessed.get(i).byteValue();
            }
            fos.write(result);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

    }

}
