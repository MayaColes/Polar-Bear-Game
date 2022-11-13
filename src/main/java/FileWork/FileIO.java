package FileWork;
import Utils.Globals;
import com.google.gson.Gson;
import GameObjects.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;

public class FileIO {
    private static RandomAccessFile f = null;
    private static Reader[] c = null;
    private static File config = null;
    
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
    public static void openConfigFiles(){
        try{
            config = new File(System.getProperty("user.dir"), "config");
            File[] configFiles = config.listFiles();
            c = new FileReader[configFiles.length];

            for(int i = 0; i < configFiles.length; i++){
                c[i] = new FileReader(configFiles[i]);
            }
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
    public static void readFromConfigFiles(){
        Gson gson = new Gson();
        for(int i = 0; i < c.length; i++){
            String fileName = config.listFiles()[i].getName();
            if(fileName.equals("buildings.json")){
                Globals.ALL_BUILDINGS = gson.fromJson(c[i], Building[].class);
            }
            else if(fileName.equals("civilizations.json")){
                Globals.ALL_CIVILIZATIONS = gson.fromJson(c[i], Civilization[].class);
            }
            else if(fileName.equals("craftableResources.json")){
                Globals.ALL_CRAFTABLE_RESOURCES = gson.fromJson(c[i], CraftableResource[].class);
            }
            else if(fileName.equals("jobs.json")){
                Globals.ALL_JOBS = gson.fromJson(c[i], Job[].class);
            }
            else if(fileName.equals("magicEffects.json")){
                Globals.ALL_MAGIC_EFFECTS = gson.fromJson(c[i], MagicEffect[].class);
            }
            else if(fileName.equals("magics.json")){
                Globals.ALL_MAGIC = gson.fromJson(c[i], Research[].class);
            }
            else if(fileName.equals("resources.json")){
                Globals.ALL_RESOURCES = gson.fromJson(c[i], Resource[].class);
            }
            else if(fileName.equals("sciences.json")){
                Globals.ALL_SCIENCES = gson.fromJson(c[i], Research[].class);
            }
            else if(fileName.equals("upgrades.json")){
                Globals.ALL_UPGRADES = gson.fromJson(c[i], Upgrade[].class);
            }
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
