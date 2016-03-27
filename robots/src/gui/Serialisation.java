package gui;

import java.io.*;


public class Serialisation
{
    String serialString;

    public Serialisation(String serialString)
    {
        this.serialString = serialString;
    }

    public void SaveToFile()
    {
        try(FileWriter writer = new FileWriter("C:\\Users\\анастасия\\Desktop\\robots\\src\\ser.txt", false))
        {
            String text = serialString;
            writer.write(text);
            writer.append('\n');


            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
}
