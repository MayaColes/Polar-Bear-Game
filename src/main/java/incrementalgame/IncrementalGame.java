package incrementalgame;
import Utils.Globals;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import GameObjects.*;

public class IncrementalGame {

    public static void main(String[] args) {
        Initialize.initializeSaveFileChooser();
        /*Initialize.initializeConfig();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            Writer f = new FileWriter("upgrades.json");
            gson.toJson(Globals.ALL_UPGRADES, f);

            f.flush();
            f.close();
        }
        catch(Exception e){}*/

    }
    public static void testing(){
        for(int i = 0; i < Globals.NUMBER_OF_RESOURCES; i++){
            Globals.ALL_RESOURCES[i].changeAmount(Globals.ALL_RESOURCES[i].getMaximum());
        }
        for(int i = 0; i < Globals.NUMBER_OF_CRAFTABLE_RESOURCES; i++){
            Globals.ALL_CRAFTABLE_RESOURCES[i].changeAmount(1);
        }
    }
}
