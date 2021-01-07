package incrementalgame;
import Utils.Globals;

public class IncrementalGame {

    public static void main(String[] args) {
         Initialize.initializeSaveFileChooser();
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
