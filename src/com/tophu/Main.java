package com.tophu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static String header =
            "3ds Boot Splash Tool\n" +
            "\tBy Topher Campbell (C) 2018";
    static String usage = "this tool will take all pngs in a directory " +
            "and convert them in to an raw binary file.\n\n" +
            "usage: java -jar splashtool.jar /path/to/pngs {options}\n" +
            "options:\n" +
            "-f sets the framerate of the animation\n" +
            "if no options are set, default framerate is set to 15 fps";

    public static void main(String[] args) {
        int framerate = 15;
        String workingDirectory = System.getProperty("user.dir");
        System.out.println(workingDirectory);
        String path = workingDirectory;
        SplashTool tool;


        if(args.length > 0) {
            path = args[0];
            for (int i = 0; i < args.length; i++) {
                if(args[i].toLowerCase().equals("-f"))
                {
                  framerate = Integer.parseInt(args[i+1]);
                  if(framerate <= 0 ){
                      framerate = 15;
                      System.out.println("invalid framerate specified! defaulting to 15 fps.");

                  }
                }
            }

            //run the tool
            tool = new SplashTool(path, path.concat("/splash.bin"), framerate);
            tool.buildFile();
            System.out.println("Done...");
        }else {
            System.out.println(header);
            System.out.println(usage);
        }


    }




}
