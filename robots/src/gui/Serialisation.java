package gui;

import java.io.*;


public class Serialisation
{
    public File serialFile;
    String serialString;

    public Serialisation(String serialString)
    {
        this.serialString = serialString;
    }

    public void SaveToFile()
    {
        this.serialFile = new File(System.getProperty("user.home"), "ser.txt");
        try(FileWriter writer = new FileWriter(serialFile))
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
