package FileWork;
import Utils.Globals;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;

public class FileIO {
    private static RandomAccessFile f = null;
    private static RandomAccessFile c = null;
    
    public static void openSaveFile(){
        File saveFile = new File(Globals.SAVE_FILE);
        try{
            f = new RandomAccessFile(saveFile, "rw");
        }
        catch(IOException e){
        }
    }
    public static void closeSaveFile(){
        try{
            f.close();
        }
        catch(IOException e){
        }
    }
    public static void openConfigFile(){
        try{
            c = new RandomAccessFile(Globals.CONFIG_FILE, "rw");
        }
        catch(IOException e){
        }
    }
    public static void closeConfigFile(){
        try{
            c.close();
        }
        catch(IOException e){
        }
    }
    public static double readFromSaveFile(int i){
        try{
            f.seek(i*Globals.RECORD_LEN);
            return f.readDouble();
        }
        catch(IOException e){
            return 0;
        }
    }
    public static void readFromConfigFile(){
        try{
            StringBuffer s = new StringBuffer();
            String d = c.readLine();
            while(d!= null){
                s.append(d);
                d = c.readLine();
            }
            Globals.CONFIG = s + "";
        }
        catch(IOException e){
        }
    }
    public static void writeToSaveFile(double i, int j){
        try{
            f.seek(j*Globals.RECORD_LEN);
            f.writeDouble(i);
        }
        catch(IOException e){
        }
    }
    public static BufferedImage readImage(String image, int width, int height){

        try{
            BufferedImage original = ImageIO.read(new File(Globals.IMAGES_FOLDER + image));
            BufferedImage resized = new BufferedImage(width, height, original.getType());
            
            Graphics2D g2d = resized.createGraphics();
            g2d.drawImage(original, 0, 0, width, height, null);
            g2d.dispose();
            
            return resized;
        }
        catch(Exception e){
            return null;
        }
    }
}
