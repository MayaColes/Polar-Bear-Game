package Utils;
import java.math.*;
import GameObjects.Buildable;

public class Utils {
    private static int bearCounter = 0;
    private static int snowstormCount = -1;
    private static int season = getSeason();
    
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    public static String checkToSmall(double value){
        if(value < 0.005 && value > -0.005){
            return "(...)";
        }
        else{
            return "";
        }
    }
    public static String getDateFromDaysPassed(){
        int year = (int)(Globals.TIME_IN_DAYS / 360);
        int timeOfYear = (int)(Globals.TIME_IN_DAYS % 360);
        String season = "";
        if(timeOfYear <= 90){
            season = "Spring";
        }
        else if(timeOfYear > 90 && timeOfYear <= 180){
            season = "Summer";
        }
        else if(timeOfYear > 180 && timeOfYear <= 270){
            season = "Fall";
        }
        else if(timeOfYear > 270){
            season = "Winter";
        }
        timeOfYear = timeOfYear % 90;
        return "Year " + year + " - " + season + ", day " + timeOfYear;
    }
    public static long getYear(){
        return (Globals.TIME_IN_DAYS / 360);
    }
    public static int getSeason(){
        int timeOfYear = (int)(Globals.TIME_IN_DAYS % 360);
        if(timeOfYear <= 89){
            return Globals.SPRING;
        }
        else if(timeOfYear > 90 && timeOfYear <= 180){
            return Globals.SUMMER;
        }
        else if(timeOfYear > 180 && timeOfYear <= 270){
            return Globals.FALL;
        }
        else if(timeOfYear > 270){
            return Globals.WINTER;
        }
        else{
            return Globals.SPRING;
        }
    }
    public static void checkNewSeason(){
        if(season != getSeason() || Globals.NEW_SEASON){
            season = getSeason();
            Globals.NEW_SEASON = true;
        }
        else{
            Globals.NEW_SEASON = false;
        }
    }
    public static int getDay(){
        return (int)(Globals.TIME_IN_DAYS % 360);
    }
    public static void checkJobVisibility(){
        if(Globals.ALL_SCIENCES[findScienceFromIdentifier('a')].getIsResearched()){
            Globals.ALL_JOBS[1].setVisible(true);
        }
        if(Globals.ALL_RESOURCES[findResourceFromIdentifier('B')].getAmount() > 0){
            Globals.ALL_JOBS[0].setVisible(true);
        }
        if(Globals.ALL_BUILDINGS[findBuildingFromIdentifier('m')].getNumberBuilt() > 0){
            Globals.ALL_JOBS[4].setVisible(true);
        }
        if(Globals.ALL_BUILDINGS[findBuildingFromIdentifier('t')].getNumberBuilt() > 0){
            Globals.ALL_JOBS[3].setVisible(true);
        }
        if(Globals.ALL_BUILDINGS[findBuildingFromIdentifier('l')].getNumberBuilt() > 0){
            Globals.ALL_JOBS[2].setVisible(true);
        }
        if(Globals.ALL_SCIENCES[findScienceFromIdentifier('o')].getIsResearched()){
            Globals.ALL_JOBS[5].setVisible(true);
        }
        if(getYear() > 9){
            Globals.ALL_JOBS[7].setVisible(true);
        }
        if(getYear() > 9 && Globals.ALL_SCIENCES[findScienceFromIdentifier('U')].getIsResearched()){
            Globals.ALL_JOBS[6].setVisible(true);
        }
    }
    public static void updateJobEffects(){
        for(int i = 0; i < Globals.NUMBER_OF_JOBS; i++){
            for(int j = 0; j < Globals.NUMBER_OF_RESOURCES; j++){
                Globals.ALL_JOBS[i].updateEffectWithBonus(AmountCalculator.totalJobPercentBonus[j] + 1, Globals.RESOURCE_IDENTIFIER, j);
            }
        }
    }
    public static void checkBuildingVisibility(){
        for(int i = 0; i < Globals.NUMBER_OF_BUILDINGS; i++){
            Globals.ALL_BUILDINGS[i].checkBuildingVisibility();
        }
    }
    public static boolean checkBuildingProduction(int index, int numberEnabled){
        boolean canProduce = true;
        
        for(int i = 0; i < Globals.ALL_BUILDINGS[index].getNumberOfEffects(); i++){
            if(Globals.ALL_RESOURCES[Globals.ALL_BUILDINGS[index].getOneEffect(i).getEffectsWhatObject()].getAmount() - Globals.ALL_BUILDINGS[index].getOneEffect(i).getEffectAmount() * numberEnabled < 0 && Globals.ALL_BUILDINGS[index].getOneEffect(i).getTypeOfEffect() == Globals.NEGATIVE_PRODUCTION_IDENTIFIER){
                canProduce = false;
            }
        }
        return canProduce;
    }
    public static void checkResourceVisibility(){
        for(int i = 0; i < Globals.NUMBER_OF_RESOURCES; i++){
            Globals.ALL_RESOURCES[i].checkVisible();
        }
    }
    public static void checkCraftableResourceVisibility(){
        for(int i = 0; i < Globals.NUMBER_OF_CRAFTABLE_RESOURCES; i++){
            Globals.ALL_CRAFTABLE_RESOURCES[i].checkVisible();
        }
    }
    public static void checkTabVisibility(){
        if(Globals.ALL_RESOURCES[findResourceFromIdentifier('B')].getAmount() > 0){
            Globals.GAME.enableVillage();
        }
        if(Globals.ALL_BUILDINGS[findBuildingFromIdentifier('l')].getNumberBuilt() > 0){
            Globals.GAME.enableScience();
        }
        if(Globals.ALL_SCIENCES[findScienceFromIdentifier('s')].getIsResearched()){
            Globals.GAME.enableMagic();
        }
        if(Globals.ALL_BUILDINGS[findBuildingFromIdentifier('T')].getNumberBuilt() > 0){
            Globals.GAME.enableCrafting();
        }
        if(getYear() >= 10){
            Globals.GAME.enableTrade();
        }
    }
    public static String timeUntilMaxMin(int index, boolean craftable){
        StringBuffer s = new StringBuffer();
        long seconds = 0;
        if(craftable){
            if(Globals.AMOUNT_PER_SECOND_CRAFTABLE[index] < 0){
                seconds = - (int)(Globals.ALL_CRAFTABLE_RESOURCES[index].getAmount() / Globals.AMOUNT_PER_SECOND_CRAFTABLE[index]);
                s.append("To zero: ");
            }
            s.append(getTimeFromSeconds(seconds));
        }
        else{
            if(Globals.AMOUNT_PER_SECOND[index] > 0){
                seconds = (int)((Globals.ALL_RESOURCES[index].getMaximum() - Globals.ALL_RESOURCES[index].getAmount()) / 
                           Globals.AMOUNT_PER_SECOND[index]);
                s.append("To max: ");
            }
            else if(Globals.AMOUNT_PER_SECOND[index] < 0){
                seconds = - (int)(Globals.ALL_RESOURCES[index].getAmount() / Globals.AMOUNT_PER_SECOND[index]);
                s.append("To zero: ");
            }
            s.append(getTimeFromSeconds(seconds));
        }
        
        return(s + "");
    }
    public static String timeUntilAmount(int resourceNumber, double amount){
        StringBuffer s = new StringBuffer();
        if(Globals.AMOUNT_PER_SECOND[resourceNumber] > 0){
            s.append("(");
            long time = (long)((amount - Globals.ALL_RESOURCES[resourceNumber].getAmount()) / Globals.AMOUNT_PER_SECOND[resourceNumber]);
            s.append(getTimeFromSeconds(time));
            s.append(")");
        }
        return s + "";
    }
    public static String timeUntilCanBuildBuildable(Buildable buildable, int resourceIndex){
        long seconds = 0;
        int resourceNeeded = buildable.getOneRequired(resourceIndex);
        double amountNeeded = buildable.getOnePrice(resourceIndex) -
                Globals.ALL_RESOURCES[resourceNeeded].getAmount();


        if(Globals.AMOUNT_PER_SECOND[resourceNeeded] > 0){
            seconds = (int)(amountNeeded / Globals.AMOUNT_PER_SECOND[resourceNeeded]);
        }

        if(getTimeFromSeconds(seconds).equals("Now")){
            return "";
        }
        else{
            return "(" + getTimeFromSeconds(seconds) + ")";
        }
    }
    public static String getTimeFromSeconds(long seconds){
        StringBuffer s = new StringBuffer();
        int minutes = 0;
        int hours = 0;
        int days = 0;
        if(seconds > 0){
            minutes = (int)(seconds / 60);
            hours = (int)(minutes / 60);
            days = (int)(hours / 24);
            seconds = seconds % 60;
            minutes = minutes % 60;
            hours = hours % 24;
            
            if(days > 0){
                s.append(days + "d");
            }
            if(hours > 0){
                s.append(hours + "h ");
            }
            if(minutes > 0){
                s.append(minutes + "m ");
            }
            if(seconds > 0){
                s.append(seconds + "s ");
            }
            return(s + "");
        }
        else{
            return "Now";
        }
    }
    public static void checkUpgradeEffects(){
        for(int i = 0; i < Globals.NUMBER_OF_UPGRADES; i++){
            Globals.ALL_UPGRADES[i].checkEffects();
        }
    }
    public static void checkBears(){
        if(Globals.ALL_RESOURCES[findResourceFromIdentifier('B')].getAmount() < Globals.ALL_RESOURCES[findResourceFromIdentifier('B')].getMaximum()){
            bearCounter++;
            if(bearCounter == 15){
                bearCounter = 0;
                Globals.ALL_RESOURCES[6].changeAmount(1);
                Utils.checkTabVisibility();
                Globals.GAME.writeToEvent("A new bear has moved to the village.\n\n");
            }
        }
        if(((Globals.ALL_RESOURCES[findResourceFromIdentifier('b')].getAmount() == 0 || Globals.ALL_RESOURCES[findResourceFromIdentifier('W')].getAmount() == 0) && Globals.ALL_RESOURCES[findResourceFromIdentifier('B')].getAmount()!= 0)){
            bearCounter = bearCounter - 2;
            if(bearCounter <= -10){
                bearCounter = 0;
                int i  = (int)(Math.random() * 3);
                Globals.GAME.writeToEvent(makeBearDeathMessage(i) + "\n\n");
            }
        }
    }
    public static String makeBearDeathMessage(int l){
        boolean b = false;
        if(Globals.ALL_RESOURCES[findResourceFromIdentifier('B')].getAmount() - AmountCalculator.calculateFreeBears() == Globals.ALL_RESOURCES[findResourceFromIdentifier('B')].getAmount() && !b){
            for(int i = 0; i < Globals.NUMBER_OF_JOBS && !b; i++){
                if(Globals.ALL_JOBS[i].getNumberWorking() > 0){
                    Globals.ALL_JOBS[i].decreaseWorking();
                    b = true;
                }
            }
        }
        Globals.ALL_RESOURCES[findResourceFromIdentifier('B')].changeAmount(-1);
        if(l == 0){
            return "The village has lost a bear.\nYou mourn the loss of one of your closest friends.\nMay " + Globals.FIRST_NAMES[(int)(Math.random() * Globals.FIRST_NAMES.length)] + " " + Globals.LAST_NAMES[(int)(Math.random() * Globals.LAST_NAMES.length)] + " rest in peace.";
        }
        if(l == 1){
            return "The village has lost a bear.\nThe children of the village will never forget their friend.\nMay " + Globals.FIRST_NAMES[(int)(Math.random() * Globals.FIRST_NAMES.length)] + " " + Globals.LAST_NAMES[(int)(Math.random() * Globals.LAST_NAMES.length)] + " rest in peace.";
        }
        if(l == 2){
            return "The village has lost a bear.\nThe town elder and storyteller will always be remembered.\nMay " + Globals.FIRST_NAMES[(int)(Math.random() * Globals.FIRST_NAMES.length)] + " " + Globals.LAST_NAMES[(int)(Math.random() * Globals.LAST_NAMES.length)] + " rest in peace.";
        }
        if(l == 3){
            return "The village has lost a bear.\nOne of your hunters got lost in the storm.\nMay " + Globals.FIRST_NAMES[(int)(Math.random() * Globals.FIRST_NAMES.length)] + " " + Globals.LAST_NAMES[(int)(Math.random() * Globals.LAST_NAMES.length)] + " forgive you for your misjudgement.";
        }
        return "";
    }
    public static void checkEvents(){
        if(snowstormCount > 0){
            snowstormCount--;
        }
        else if(snowstormCount == 0){
            Globals.SNOWSTORM = 0;
            snowstormCount--;
            Globals.GAME.writeToEvent("The snowstorm has subsided.\n\n");
        }
        int a = 1 + (int)(1000 * Math.random());
        if(snowstormCount == -1){
            if(a <= AmountCalculator.calculateSnowstormChance()){
                Globals.SNOWSTORM = 1;
                snowstormCount = 20;
                Globals.GAME.writeToEvent("A snowstorm is blowing across your village. \nBerry production reduced.\n\n");
            }
        }
        if(Globals.ALL_RESOURCES[findResourceFromIdentifier('s')].getMaximum() > 0){
            a = 1 + (int)(1000 * Math.random());
            if(a <= AmountCalculator.calculateAuroraChance()){
                Globals.GAME.writeToEvent("Some mysterious lights appeared in the sky above your village. +" + 
                                          AmountCalculator.calculateAuroraBonus() + " science.\n\n");
                Globals.ALL_RESOURCES[findResourceFromIdentifier('s')].changeAmount(AmountCalculator.calculateAuroraBonus());
            }
        }
        if(Globals.ALL_RESOURCES[findResourceFromIdentifier('m')].getMaximum() > 0){
            a = 1 + (int)(1000 * Math.random());
            if(a <= AmountCalculator.calculateStarsAligningChance()){
                Globals.GAME.writeToEvent("The stars have alligned! " + 
                AmountCalculator.calculateStarsAligningBonus() + " magic.\n\n");
                Globals.ALL_RESOURCES[findResourceFromIdentifier('m')].changeAmount(AmountCalculator.calculateStarsAligningBonus());
            }
        }
    }
    public static void updateRaceStandings(){
        for(int i = 0; i < Globals.NUMBER_OF_CIVILIZATIONS; i++){
            if(Globals.ALL_CIVILIZATIONS[i].getHostility() == Globals.FRIENDLY){
                if(Globals.ALL_CIVILIZATIONS[i].getRaceStanding() > Globals.NO_STANDING){
                    Globals.ALL_CIVILIZATIONS[i].changeRaceStanding(-0.1);
                }
                else if(Globals.ALL_CIVILIZATIONS[i].getRaceStanding() < Globals.NO_STANDING){
                   Globals.ALL_CIVILIZATIONS[i].changeRaceStanding(0.1);
                }
            }
            else if(Globals.ALL_CIVILIZATIONS[i].getHostility() == Globals.NEUTRAL){
                if(Globals.ALL_CIVILIZATIONS[i].getRaceStanding() > Globals.NO_STANDING){
                    Globals.ALL_CIVILIZATIONS[i].changeRaceStanding(-0.15);
                }
                else if(Globals.ALL_CIVILIZATIONS[i].getRaceStanding() < Globals.NO_STANDING){
                    Globals.ALL_CIVILIZATIONS[i].changeRaceStanding(0.08);
                }
            }
            else if(Globals.ALL_CIVILIZATIONS[i].getHostility() == Globals.HOSTILE){
                if(Globals.ALL_CIVILIZATIONS[i].getRaceStanding() > Globals.NO_STANDING){
                    Globals.ALL_CIVILIZATIONS[i].changeRaceStanding(-0.3);
                }
                else if(Globals.ALL_CIVILIZATIONS[i].getRaceStanding() < Globals.NO_STANDING){
                    Globals.ALL_CIVILIZATIONS[i].changeRaceStanding(-0.05);
                }
            }
        }
    }
    public static void checkCivilizationVisability(){
        if(getYear() >= 10 && !Globals.ALL_CIVILIZATIONS[0].getRevealed()){
            Globals.ALL_CIVILIZATIONS[0].setRevealed(true);
            Globals.GAME.writeToEvent("Raccoon scouts from the south have found your village. You have discovered a new civilization!\n\n");
        }
        if(getYear() >= 10 && Globals.ALL_SCIENCES[findScienceFromIdentifier('E')].getIsResearched() && !Globals.ALL_CIVILIZATIONS[1].getRevealed()){
            Globals.ALL_CIVILIZATIONS[1].setRevealed(true);
            Globals.GAME.writeToEvent("Hearing of your scientific prowess, the salamanders sent an envoy to your village. You have discovered a new civilization!\n\n");
        }
    }
    public static void checkNegativeCivilizationEffects(){
        for(int i = 0; i < Globals.NUMBER_OF_CIVILIZATIONS; i++){
            if(Globals.ALL_CIVILIZATIONS[i].getRevealed()){
                int a = 1 + (int)(Math.random() * 1000); 
                if(Globals.ALL_CIVILIZATIONS[i].getHostility() == Globals.HOSTILE && a == 1 && Globals.ALL_JOBS[findJobFromIdentifier('w')].getNumberWorking() < (Globals.ALL_RESOURCES[findResourceFromIdentifier('B')].getAmount() / 20)){
                    Globals.GAME.writeToEvent("Unimpressed with your military strength, the " + Globals.ALL_CIVILIZATIONS[i].getName() + " scoff at your weak leadership.\n\n");
                    Globals.ALL_CIVILIZATIONS[i].changeRaceStanding(-25);
                }
                else if(Globals.ALL_CIVILIZATIONS[i].getHostility() == Globals.NEUTRAL && a == 1 && Globals.AMOUNT_PER_SECOND[findResourceFromIdentifier('s')] < getYear() / 15){
                    Globals.GAME.writeToEvent("Your scientific emipre crumbling, the " + Globals.ALL_CIVILIZATIONS[i].getName() + " turn their backs in disgust.\n\n");
                    Globals.ALL_CIVILIZATIONS[i].changeRaceStanding(-25);
                }
                else if(Globals.ALL_CIVILIZATIONS[i].getHostility() == Globals.FRIENDLY && a == 1 && Globals.ALL_RESOURCES[findResourceFromIdentifier('B')].getAmount() < getYear() / 5){
                    Globals.GAME.writeToEvent("Upset that you are not bringing many bears to your village, the " + Globals.ALL_CIVILIZATIONS[i].getName() + " have focused their attention on cuter civilizations.\n\n");
                    Globals.ALL_CIVILIZATIONS[i].changeRaceStanding(-25);
                }
            }
        }
    }
    public static String makeFoodWaterWarningMessage(){
        if(Globals.ALL_RESOURCES[findResourceFromIdentifier('W')].getAmount() < Globals.ALL_RESOURCES[findResourceFromIdentifier('W')].getMaximum() / 20 && Globals.ALL_RESOURCES[findResourceFromIdentifier('b')].getAmount() < Globals.ALL_RESOURCES[findResourceFromIdentifier('b')].getMaximum() / 10){
            return "<html><font color=red>Warning - Low Water and Berries!<html>";
        }
        else if(Globals.ALL_RESOURCES[findResourceFromIdentifier('W')].getAmount() < Globals.ALL_RESOURCES[findResourceFromIdentifier('W')].getMaximum() / 20){
            return "<html><font color=red>Warning - Low Water!<html>";
        }
        else if(Globals.ALL_RESOURCES[findResourceFromIdentifier('b')].getAmount() < Globals.ALL_RESOURCES[findResourceFromIdentifier('b')].getMaximum() / 10){
            return "<html><font color=red>Warning - Low Berries!<html>";
        }
        else{
            return "";
        }
    }
    public static void updateTradeTimes(){
        for(int i = 0; i < Globals.NUMBER_OF_CIVILIZATIONS; i++){
            Globals.ALL_CIVILIZATIONS[i].updateTradeTimes();
        }
    }
    public static int findResourceFromIdentifier(char identifier){
        for(int i = 0; i < Globals.NUMBER_OF_RESOURCES; i++){
            if(Globals.ALL_RESOURCES[i].getIdentifier() == identifier){
                return i;
            }
        }
        return 0;
    }
    public static int findBuildingFromIdentifier(char identifier){
        for(int i = 0; i < Globals.NUMBER_OF_BUILDINGS; i++){
            if(Globals.ALL_BUILDINGS[i].getIdentifier() == identifier){
                return i;
            }
        }
        return 0;
    }
    public static int findCraftableResourceFromIdentifier(char identifier){
        for(int i = 0; i < Globals.NUMBER_OF_CRAFTABLE_RESOURCES; i++){
            if(Globals.ALL_CRAFTABLE_RESOURCES[i].getIdentifier() == identifier){
                return i;
            }
        }
        return 0;
    }
    public static int findScienceFromIdentifier(char identifier){
        for(int i = 0; i < Globals.NUMBER_OF_TECHNOLOGIES; i++){
            if(Globals.ALL_SCIENCES[i].getIdentifier() == identifier){
                return i;
            }
        }
        return 0;
    }
    public static int findMagicFromIdentifier(char identifier){
        for(int i = 0; i < Globals.NUMBER_OF_MAGICS; i++){
            if(Globals.ALL_MAGIC[i].getIdentifier() == identifier){
                return i;
            }
        }
        return 0;
    }
    public static int findJobFromIdentifier(char identifier){
        for(int i = 0; i < Globals.NUMBER_OF_JOBS; i++){
            if(Globals.ALL_JOBS[i].getIdentifier() == identifier){
                return i;
            }
        }
        return 0;
    }
}
