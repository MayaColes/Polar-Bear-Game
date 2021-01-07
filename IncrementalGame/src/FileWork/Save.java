package FileWork;
import Utils.Globals;

public class Save {
    public static void saveGame(){
        int recordNumber = 0;
        for(int i = 0; i < Globals.NUMBER_OF_BUILDINGS; i++){
            FileIO.writeToSaveFile(Globals.ALL_BUILDINGS[i].getNumberBuilt(), recordNumber);
            recordNumber++;
        }
        FileIO.writeToSaveFile(Globals.END_OF_ARRAY_MARKER, recordNumber);
        recordNumber++;
        
        for(int i = 0; i < Globals.NUMBER_OF_BUILDINGS; i++){
            FileIO.writeToSaveFile(Globals.ALL_BUILDINGS[i].getNumberEnabled(), recordNumber);
            recordNumber++;
        }
        FileIO.writeToSaveFile(Globals.END_OF_ARRAY_MARKER, recordNumber);
        recordNumber++;
        
        for(int i = 0; i < Globals.NUMBER_OF_RESOURCES; i++){
            FileIO.writeToSaveFile(Globals.ALL_RESOURCES[i].getAmount(), recordNumber);
            recordNumber++;
        }
        FileIO.writeToSaveFile(Globals.END_OF_ARRAY_MARKER, recordNumber);
        recordNumber++;
        
        for(int i = 0; i < Globals.NUMBER_OF_RESOURCES; i++){
            if(Globals.ALL_RESOURCES[i].getVisible()){
                FileIO.writeToSaveFile(1, recordNumber);
            }
            else{
                FileIO.writeToSaveFile(0, recordNumber);
            }
            recordNumber++;
        }
        FileIO.writeToSaveFile(Globals.END_OF_ARRAY_MARKER, recordNumber);
        recordNumber++;
        
        for(int i = 0; i < Globals.NUMBER_OF_TECHNOLOGIES; i++){
            if(Globals.ALL_SCIENCES[i].getIsResearched()){
                FileIO.writeToSaveFile(1, recordNumber);
            }
            else{
                FileIO.writeToSaveFile(0, recordNumber);
            }
            recordNumber++;
        }
        FileIO.writeToSaveFile(Globals.END_OF_ARRAY_MARKER, recordNumber);
        recordNumber++;
        
        for (int i = 0; i < Globals.NUMBER_OF_MAGICS; i++){
            if(Globals.ALL_MAGIC[i].getIsResearched()){
                FileIO.writeToSaveFile(1, recordNumber);
            }
            else{
                FileIO.writeToSaveFile(0, recordNumber);
            }
            recordNumber++;
        }
        FileIO.writeToSaveFile(Globals.END_OF_ARRAY_MARKER, recordNumber);
        recordNumber++;
        
        for(int i = 0; i < Globals.NUMBER_OF_CRAFTABLE_RESOURCES; i++){
            FileIO.writeToSaveFile(Globals.ALL_CRAFTABLE_RESOURCES[i].getAmount(), recordNumber);
            recordNumber++;
        }
        FileIO.writeToSaveFile(Globals.END_OF_ARRAY_MARKER, recordNumber);
        recordNumber++;
        
        for(int i = 0; i < Globals.NUMBER_OF_CRAFTABLE_RESOURCES; i++){
            if(Globals.ALL_CRAFTABLE_RESOURCES[i].getVisible()){
                FileIO.writeToSaveFile(1, recordNumber);
            }
            else{
                FileIO.writeToSaveFile(0, recordNumber);
            }
            recordNumber++;
        }
        FileIO.writeToSaveFile(Globals.END_OF_ARRAY_MARKER, recordNumber);
        recordNumber++;
        
        for(int i = 0; i < Globals.NUMBER_OF_JOBS; i++){
            FileIO.writeToSaveFile(Globals.ALL_JOBS[i].getNumberWorking(), recordNumber);
            recordNumber++;
        }
        FileIO.writeToSaveFile(Globals.END_OF_ARRAY_MARKER, recordNumber);
        recordNumber++;
        
        for(int i = 0; i < Globals.NUMBER_OF_UPGRADES; i++){
            if(Globals.ALL_UPGRADES[i].getResearched()){
                FileIO.writeToSaveFile(1, recordNumber);
            }
            else{
                FileIO.writeToSaveFile(0, recordNumber);
            }
            
            recordNumber++;
        }
        FileIO.writeToSaveFile(Globals.END_OF_ARRAY_MARKER, recordNumber);
        recordNumber++;
        
        for(int i = 0; i < Globals.NUMBER_OF_CIVILIZATIONS; i++){
            if(Globals.ALL_CIVILIZATIONS[i].getRevealed()){
                FileIO.writeToSaveFile(1, recordNumber);
            }
            else{
                FileIO.writeToSaveFile(0, recordNumber);
            }
            recordNumber++;
        }
        FileIO.writeToSaveFile(Globals.END_OF_ARRAY_MARKER, recordNumber);
        recordNumber++;
        
        for(int i = 0; i < Globals.NUMBER_OF_CIVILIZATIONS; i++){
            FileIO.writeToSaveFile(Globals.ALL_CIVILIZATIONS[i].getRaceStanding(), recordNumber);
            recordNumber++;
        }
        FileIO.writeToSaveFile(Globals.END_OF_ARRAY_MARKER, recordNumber);
        recordNumber++;
        
        FileIO.writeToSaveFile(Globals.TIME_IN_DAYS, recordNumber);
    }
}
