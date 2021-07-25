package incrementalgame;
import Utils.Globals;
import Utils.Utils;
import Utils.AmountCalculator;
import Windows.GameWindow;
import Windows.SaveFileChooser;
import FileWork.FileIO;
import FileWork.Save;
import GameObjects.*;

public class Initialize {
    private static int[] numberOfBuildingsBuilt = new int[Globals.NUMBER_OF_BUILDINGS];
    private static int[] numberWorking = new int[Globals.NUMBER_OF_JOBS];
    private static boolean[] upgradeResearched = new boolean[Globals.NUMBER_OF_UPGRADES];
    private static int[] numberEnabled = new int[Globals.NUMBER_OF_BUILDINGS];
    private static boolean[] buildingVisible = new boolean[Globals.NUMBER_OF_BUILDINGS];
    private static double[] amountOfResource = new double[Globals.NUMBER_OF_RESOURCES];
    private static double[] amountOfCraftableResource = new double[Globals.NUMBER_OF_CRAFTABLE_RESOURCES];
    private static double[] civilizationStandings = new double[Globals.NUMBER_OF_CIVILIZATIONS];
    private static boolean[] resourceVisible = new boolean[Globals.NUMBER_OF_RESOURCES];
    private static boolean[] craftableResourceVisible = new boolean[Globals.NUMBER_OF_CRAFTABLE_RESOURCES];
    private static boolean[] scienceResearched = new boolean[Globals.NUMBER_OF_TECHNOLOGIES];
    private static boolean[] magicResearched = new boolean[Globals.NUMBER_OF_MAGICS];
    private static boolean[] civilizationRevealed = new boolean[Globals.NUMBER_OF_CIVILIZATIONS];
    
    public static void initializeSaveFileChooser(){
        Globals.GAME_RUNNING = true;
        SaveFileChooser s = new SaveFileChooser();
    }
    public static void initializeGame(){
        initializeConfig();
        initializeSaveFile();
        AmountCalculator.calculateMaximums();
        AmountCalculator.calculateProductionPerTurn();
        
        Globals.GAME = new GameWindow();
        Utils.checkTabVisibility();
        Utils.checkUpgradeEffects();
        AmountCalculator.calculateScienceBonuses();
        Globals.GAME.refreshBuildings();
        Globals.GAME.refreshResources();
        

        TimePass.gameTick();
    }
    public static void endGame(){
        Save.saveGame();
        FileIO.closeSaveFile();
        System.exit(0);
    }
    public static void initializeSaveFile(){
        FileIO.openSaveFile();
        if(FileIO.readFromSaveFile(0) != -1){
            boolean endOfArray = false;
            int recordNumber = 0;
            for (int j = 0; j < Globals.NUMBER_OF_BUILDINGS && !endOfArray; j++){
                int i = (int)FileIO.readFromSaveFile(recordNumber);
                if(i != Globals.END_OF_ARRAY_MARKER){
                    numberOfBuildingsBuilt[j] = i;
                    recordNumber++;
                }
                else{
                    for(int k = 0; k < Globals.NUMBER_OF_BUILDINGS - j; k++){
                        numberOfBuildingsBuilt[k + j] = 0;
                        endOfArray = true;
                    }
                }
            }
            recordNumber++;
            endOfArray = false;
            for (int j = 0; j < Globals.NUMBER_OF_BUILDINGS && !endOfArray; j++){
                int i = (int)FileIO.readFromSaveFile(recordNumber);
                if(i != Globals.END_OF_ARRAY_MARKER){
                    numberEnabled[j] = i;
                    recordNumber++;
                }
                else{
                    for(int k = 0; k < Globals.NUMBER_OF_BUILDINGS - j; k++){
                        numberEnabled[k + j] = 0;
                        endOfArray = true;
                    }
                }
            }
            recordNumber++;
            endOfArray = false;
            for (int j = 0; j < Globals.NUMBER_OF_RESOURCES && !endOfArray; j++){
                
                double i = (int)FileIO.readFromSaveFile(recordNumber);
                if(i != Globals.END_OF_ARRAY_MARKER){
                    amountOfResource[j] = i;
                    recordNumber++;
                }
                else{
                    for(int k = 0; k < Globals.NUMBER_OF_RESOURCES - j; k++){
                        amountOfResource[k + j] = 0;
                        endOfArray = true;
                    }
                }
            }
            recordNumber++;
            endOfArray = false;
            for (int j = 0; j < Globals.NUMBER_OF_RESOURCES && !endOfArray; j++){
                
                int i = (int)FileIO.readFromSaveFile(recordNumber);
                if(i != Globals.END_OF_ARRAY_MARKER){
                    if(i == 1){
                        resourceVisible[j] = true;
                    }
                    else{
                        resourceVisible[j] = false;
                    }
                    recordNumber++;
                }
                else{
                    for(int k = 0; k < Globals.NUMBER_OF_RESOURCES - j; k++){
                        resourceVisible[k + j] = false;
                        endOfArray = true;
                    }
                }
            }
            recordNumber++;
            endOfArray = false;
            for(int j = 0; j < Globals.NUMBER_OF_TECHNOLOGIES && !endOfArray; j++){
                int i = (int)FileIO.readFromSaveFile(recordNumber);
                if(i != Globals.END_OF_ARRAY_MARKER){
                    if(i == 1){
                        scienceResearched[j] = true;
                    }
                    else if(i == 0){
                        scienceResearched[j] = false;
                    }
                    recordNumber++;
                }
                else{
                    for(int k = 0; k < Globals.NUMBER_OF_TECHNOLOGIES - j; k++){
                        scienceResearched[k + j] = false;
                        endOfArray = true;
                    }
                }
            }
            recordNumber++;
            endOfArray = false;
            for(int j = 0; j < Globals.NUMBER_OF_MAGICS && !endOfArray; j++){
                int i = (int)FileIO.readFromSaveFile(recordNumber);
                if(i != Globals.END_OF_ARRAY_MARKER){
                    if(i == 1){
                        magicResearched[j] = true;
                    }
                    else if(i == 0){
                        magicResearched[j] = false;
                    }
                    recordNumber++;
                }
                else{
                    for(int k = 0; k < Globals.NUMBER_OF_MAGICS - j; k++){
                        magicResearched[k + j] = false;
                        endOfArray = true;
                    }
                }
            }
            recordNumber++;
            endOfArray = false;
            for(int j = 0; j < Globals.NUMBER_OF_CRAFTABLE_RESOURCES && !endOfArray; j++){
                double i = FileIO.readFromSaveFile(recordNumber);
                if(i != Globals.END_OF_ARRAY_MARKER){
                    amountOfCraftableResource[j] = i;
                    recordNumber++;
                }
                else{
                    for(int k = 0; k < Globals.NUMBER_OF_CRAFTABLE_RESOURCES - j; k++){
                        amountOfCraftableResource[k + j] = 0;
                        endOfArray = true;
                    }
                }
            }
            recordNumber++;
            endOfArray = false;
            for (int j = 0; j < Globals.NUMBER_OF_CRAFTABLE_RESOURCES && !endOfArray; j++){
                
                int i = (int)FileIO.readFromSaveFile(recordNumber);
                if(i != Globals.END_OF_ARRAY_MARKER){
                    if(i == 1){
                        craftableResourceVisible[j] = true;
                    }
                    else{
                        craftableResourceVisible[j] = false;
                    }
                    recordNumber++;
                }
                else{
                    for(int k = 0; k < Globals.NUMBER_OF_CRAFTABLE_RESOURCES - j; k++){
                        craftableResourceVisible[k + j] = false;
                        endOfArray = true;
                    }
                }
            }
            recordNumber++;
            endOfArray = false;
            for(int j = 0; j < Globals.NUMBER_OF_JOBS && !endOfArray; j++){
                int i = (int)FileIO.readFromSaveFile(recordNumber);
                if(i != Globals.END_OF_ARRAY_MARKER){
                    numberWorking[j] = i;
                    recordNumber++;
                }
                else{
                    for(int k = 0; k < Globals.NUMBER_OF_JOBS - j; k++){
                        numberWorking[k + j] = 0;
                        endOfArray = true;
                    }
                }
            }
            recordNumber++;
            endOfArray = false;
            for(int j = 0; j < Globals.NUMBER_OF_UPGRADES && !endOfArray; j++){
                int i = (int)FileIO.readFromSaveFile(recordNumber);
                if(i != Globals.END_OF_ARRAY_MARKER){
                    if(i == 1){
                        upgradeResearched[j] = true;
                    }
                    else{
                        upgradeResearched[j] = false;
                    }
                    recordNumber++;
                }
                else{
                    for(int k = 0; k < Globals.NUMBER_OF_UPGRADES - j; k++){
                        upgradeResearched[k + j] = false;
                        endOfArray = true;
                    }
                }
            }
            recordNumber++;
            endOfArray = false;
            for(int j = 0; j < Globals.NUMBER_OF_CIVILIZATIONS && !endOfArray; j++){
                int i = (int)FileIO.readFromSaveFile(recordNumber);
                if(i != Globals.END_OF_ARRAY_MARKER){
                    if(i == 1){
                        civilizationRevealed[j] = true;
                    }
                    else{
                        civilizationRevealed[j] = false;
                    }
                    recordNumber++;
                }
                else{
                    for(int k = 0; k < Globals.NUMBER_OF_CIVILIZATIONS - j; k++){
                        civilizationRevealed[k + j] = false;
                        endOfArray = true;
                    }
                }
            }
            recordNumber++;
            endOfArray = false;
            for(int j = 0; j < Globals.NUMBER_OF_CIVILIZATIONS && !endOfArray; j++){
                double i = FileIO.readFromSaveFile(recordNumber);
                if(i != Globals.END_OF_ARRAY_MARKER){
                    civilizationStandings[j] = i;
                    recordNumber++;
                }
                else{
                    for(int k = 0; k < Globals.NUMBER_OF_CIVILIZATIONS - j; k++){
                        civilizationStandings[k + j] = 0;
                        endOfArray = true;
                    }
                }
            }
            recordNumber++;
            
            Globals.TIME_IN_DAYS = (long)FileIO.readFromSaveFile(recordNumber);
            
            Globals.FIRST_TIME = false;
        }
        else{
            for (int j = 0; j < Globals.NUMBER_OF_BUILDINGS; j++){
                numberOfBuildingsBuilt[j] = 0;
                numberEnabled[j] = 0;
            }
            for (int j = 0; j < Globals.NUMBER_OF_RESOURCES; j++){
                amountOfResource[j] = 0;
            }
            for (int j = 0; j < Globals.NUMBER_OF_TECHNOLOGIES; j++){
                scienceResearched[j] = false;
            }
            for (int j = 0; j < Globals.NUMBER_OF_MAGICS; j++){
                magicResearched[j] = false;
            }
            for (int j = 0; j < Globals.NUMBER_OF_CRAFTABLE_RESOURCES; j++){
                amountOfCraftableResource[j] = 0;
            }
            for(int j = 0; j < Globals.NUMBER_OF_CIVILIZATIONS; j++){
                civilizationRevealed[j] = false;
                civilizationStandings[j] = Globals.NO_STANDING;
            }
            Globals.FIRST_TIME = true;
        }

        for(int i = 0; i < Globals.NUMBER_OF_BUILDINGS; i++){
            Globals.ALL_BUILDINGS[i].initializeBuilding(numberOfBuildingsBuilt[i], numberEnabled[i]);
        }
        for(int k = 0;  k < Globals.NUMBER_OF_RESOURCES; k++){
            Globals.ALL_RESOURCES[k].initializeResource(amountOfResource[k], resourceVisible[k]);
        }
        for(int j = 0; j < Globals.NUMBER_OF_CRAFTABLE_RESOURCES; j++){
            Globals.ALL_CRAFTABLE_RESOURCES[j].initializeCraftableResource(amountOfCraftableResource[j], craftableResourceVisible[j]);
        }
        for(int j = 0; j < Globals.NUMBER_OF_JOBS; j++){
            Globals.ALL_JOBS[j].initializeJob(numberWorking[j]);
        }
        for(int j = 0; j < Globals.NUMBER_OF_TECHNOLOGIES; j++){
            Globals.ALL_SCIENCES[j].initalizeScience(scienceResearched[j]);
        }
        for(int j = 0; j < Globals.NUMBER_OF_MAGICS; j++){
            Globals.ALL_MAGIC[j].initalizeMagic(magicResearched[j]);
        }
        for(int j = 0; j < Globals.NUMBER_OF_MAGIC_EFFECTS; j++){
            Globals.ALL_MAGIC_EFFECTS[j].initializeMagicEffect();
        }
        for(int j = 0; j < Globals.NUMBER_OF_UPGRADES; j++){
            Globals.ALL_UPGRADES[j].initializeUpgrade(upgradeResearched[j]);
        }
        for(int j = 0; j < Globals.NUMBER_OF_CIVILIZATIONS; j++){
            Globals.ALL_CIVILIZATIONS[j].initializeCivilization(civilizationStandings[j], civilizationRevealed[j]);
        }
    }
    public static void initializeConfig(){
        FileIO.openConfigFiles();
        FileIO.readFromConfigFiles();
    }
}
