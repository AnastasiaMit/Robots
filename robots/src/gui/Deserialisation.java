package gui;


import com.sun.org.apache.xpath.internal.operations.Bool;
import java.awt.*;
import java.io.*;
import java.io.IOException;
import java.util.Scanner;


public class Deserialisation {

    public String iconBig;
    public boolean iconLog;
    public boolean iconGame;
    public Rectangle bigWindow;
    public Rectangle logWindow;
    public Rectangle gameWindow;


    public boolean findFile(){
        File file = new File("C:\\Users\\анастасия\\Desktop\\robots\\src\\ser.txt");
        if (file.exists() && file.isFile()) {
            return true;
        }
        return false;
    }

    public void readfromFile()
    {
        String str = "";
        try(Scanner in = new Scanner(new File("C:\\Users\\анастасия\\Desktop\\robots\\src\\ser.txt")))
        {
        while(in.hasNext())

            str += in.nextLine() + "\n";
        in.close();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }

        String[] data = str.split("\n");
        String[] coords = data[3].split(" ");
        iconBig = data[0];
        iconGame = Boolean.valueOf(data[1]);
        iconLog = Boolean.valueOf(data[2]);
        bigWindow = new Rectangle(Integer.valueOf(coords[0]), Integer.valueOf(coords[1]), Integer.valueOf(coords[2]), Integer.valueOf(coords[3]));
        coords = data[4].split(" ");
        gameWindow = new Rectangle(Integer.valueOf(coords[0]), Integer.valueOf(coords[1]), Integer.valueOf(coords[2]), Integer.valueOf(coords[3]));
        coords = data[5].split(" ");
        logWindow = new Rectangle(Integer.valueOf(coords[0]), Integer.valueOf(coords[1]), Integer.valueOf(coords[2]), Integer.valueOf(coords[3]));
}
}
