package Utils;

public class AmountCalculator {
    
    public static double[] totalPercentBonusResources = new double[Globals.NUMBER_OF_RESOURCES];
    public static double[] totalPercentBonusCraftableResources = new double[Globals.NUMBER_OF_CRAFTABLE_RESOURCES];
    public static double[] totalPositiveResourceProduction = new double[Globals.NUMBER_OF_RESOURCES];
    public static double[] totalNegativeResourceProduction = new double[Globals.NUMBER_OF_RESOURCES];
    public static double[] totalPositiveCraftableResourceProduction = new double[Globals.NUMBER_OF_CRAFTABLE_RESOURCES];
    public static double[] totalNegativeCraftableResourceProduction = new double[Globals.NUMBER_OF_CRAFTABLE_RESOURCES];
    public static double[] totalJobResourceProduction = new double[Globals.NUMBER_OF_RESOURCES];
    public static double[] totalJobPercentBonus = new double[Globals.NUMBER_OF_RESOURCES];
    public static double[] buildingBonusProduction = new double[Globals.NUMBER_OF_RESOURCES];
    public static double[] bearResourceConsumption = new double[Globals.NUMBER_OF_RESOURCES];
    public static double[] bearCraftableResourceConsumption = new double[Globals.NUMBER_OF_RESOURCES];
    
    public static double[] buildingBonuses = new double[Globals.NUMBER_OF_BUILDINGS];
    public static int[] maxEnabledBuildings = new int[Globals.NUMBER_OF_BUILDINGS];
    
    public static double bonusResourceHappiness = 0;
    public static double overPopulationLoss = 0;
    public static double magicLoss = 0;
    
    
    public static void calculateMaximums(){
        double[] maximumResources = new double[Globals.NUMBER_OF_RESOURCES];
        for(int i = 0; i < Globals.NUMBER_OF_BUILDINGS; i++){
            for(int j = 0; j < Globals.ALL_BUILDINGS[i].getNumberOfEffects();j++){
                if(Globals.ALL_BUILDINGS[i].getOneEffect(j).getTypeOfEffect() == Globals.MAXIMUM_IDENTIFIER){
                    maximumResources[Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectsWhatObject()] += Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectAmount() * Globals.ALL_BUILDINGS[i].getNumberEnabled();
                }
            }
        }
        for(int i = 0; i < Globals.NUMBER_OF_RESOURCES; i++){
            Globals.ALL_RESOURCES[i].setMaximum(maximumResources[i] + Globals.ALL_RESOURCES[i].getDefaultMaximum());
        }
    }
    public static void calculateProductionPerTurn(){
        
        totalPercentBonusResources = new double[Globals.NUMBER_OF_RESOURCES];
        totalPercentBonusCraftableResources = new double[Globals.NUMBER_OF_CRAFTABLE_RESOURCES];
        totalPositiveResourceProduction = new double[Globals.NUMBER_OF_RESOURCES];
        totalNegativeResourceProduction = new double[Globals.NUMBER_OF_RESOURCES];
        totalPositiveCraftableResourceProduction = new double[Globals.NUMBER_OF_CRAFTABLE_RESOURCES];
        totalNegativeCraftableResourceProduction = new double[Globals.NUMBER_OF_CRAFTABLE_RESOURCES];
        totalJobResourceProduction = new double[Globals.NUMBER_OF_RESOURCES];
        totalJobPercentBonus = new double[Globals.NUMBER_OF_RESOURCES];
        buildingBonusProduction = new double[Globals.NUMBER_OF_RESOURCES];
        bearResourceConsumption = new double[Globals.NUMBER_OF_RESOURCES];
        bearCraftableResourceConsumption = new double[Globals.NUMBER_OF_RESOURCES];
        
        calculateBuildingProductionBonuses();
        
        for(int i = 0; i < Globals.NUMBER_OF_BUILDINGS; i++){
            maxEnabledBuildings[i] = calculateMaxEnabledBuildings(i);
            for(int j = 0; j < Globals.ALL_BUILDINGS[i].getNumberOfEffects(); j++){
                if(Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectsWhatObjectType() == Globals.RESOURCE_IDENTIFIER && 
                         Globals.ALL_BUILDINGS[i].getOneEffect(j).getTypeOfEffect() == Globals.POSITIVE_PRODUCTION_IDENTIFIER){
                    
                    totalPositiveResourceProduction[Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectsWhatObject()] += Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectAmount() * maxEnabledBuildings[i] * (1 + buildingBonuses[i]);
                    buildingBonusProduction[Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectsWhatObject()] += buildingBonuses[i];
                }
                else if(Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectsWhatObjectType() == Globals.RESOURCE_IDENTIFIER && 
                        Globals.ALL_BUILDINGS[i].getOneEffect(j).getTypeOfEffect() == Globals.NEGATIVE_PRODUCTION_IDENTIFIER){
                    
                    totalNegativeResourceProduction[Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectsWhatObject()] += Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectAmount() * maxEnabledBuildings[i];
                }
                else if(Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectsWhatObjectType() == Globals.CRAFTABLE_RESOURCE_IDENTIFIER &&  
                        Globals.ALL_BUILDINGS[i].getOneEffect(j).getTypeOfEffect() == Globals.POSITIVE_PRODUCTION_IDENTIFIER){
                    
                    totalPositiveCraftableResourceProduction[Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectsWhatObject()] += Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectAmount() * maxEnabledBuildings[i] * (1 + buildingBonuses[i]);
                    
                }
                else if(Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectsWhatObjectType() == Globals.CRAFTABLE_RESOURCE_IDENTIFIER && 
                        Globals.ALL_BUILDINGS[i].getOneEffect(j).getTypeOfEffect() == Globals.NEGATIVE_PRODUCTION_IDENTIFIER){
                        
                    totalNegativeCraftableResourceProduction[Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectsWhatObject()] += Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectAmount() * maxEnabledBuildings[i];
                }
                else if(Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectsWhatObjectType() == Globals.RESOURCE_IDENTIFIER && 
                        Globals.ALL_BUILDINGS[i].getOneEffect(j).getTypeOfEffect() == Globals.PERCENT_BONUS_IDENTIFIER){
                
                    totalPercentBonusResources[Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectsWhatObject()] += Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectAmount() * maxEnabledBuildings[i];
                }
                else if(Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectsWhatObjectType() == Globals.CRAFTABLE_RESOURCE_IDENTIFIER && 
                        Globals.ALL_BUILDINGS[i].getOneEffect(j).getTypeOfEffect() == Globals.PERCENT_BONUS_IDENTIFIER){
               
                    totalPercentBonusCraftableResources[Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectsWhatObject()] += Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectAmount() * maxEnabledBuildings[i];
                }
                else if(Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectsWhatObjectType() == Globals.JOB_IDENTIFIER && 
                        Globals.ALL_BUILDINGS[i].getOneEffect(j).getTypeOfEffect() == Globals.PERCENT_BONUS_IDENTIFIER){
                    
                    totalJobPercentBonus[Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectsWhatResource()] += Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectAmount() * maxEnabledBuildings[i];
                }
            }
        }
        for(int i = 0; i < Globals.NUMBER_OF_UPGRADES; i++){
            if(Globals.ALL_UPGRADES[i].getResearched()){
                for(int j = 0; j < Globals.ALL_UPGRADES[i].getNumberOfEffects(); j++){
                    if(Globals.ALL_UPGRADES[i].getOneEffect(j).getEffectsWhatObjectType() == Globals.JOB_IDENTIFIER &&
                            Globals.ALL_UPGRADES[i].getOneEffect(j).getTypeOfEffect() == Globals.POSITIVE_PRODUCTION_IDENTIFIER){
                    
                        totalJobResourceProduction[Globals.ALL_UPGRADES[i].getOneEffect(j).getEffectsWhatResource()] += Globals.ALL_UPGRADES[i].getOneEffect(j).getEffectsWhatResource() * Globals.ALL_JOBS[Globals.ALL_UPGRADES[i].getOneEffect(j).getEffectsWhatObject()].getNumberWorking();
                    }
                    else if(Globals.ALL_UPGRADES[i].getOneEffect(j).getEffectsWhatObjectType() == Globals.JOB_IDENTIFIER &&
                            Globals.ALL_UPGRADES[i].getOneEffect(j).getTypeOfEffect() == Globals.PERCENT_BONUS_IDENTIFIER){
                    
                        totalJobPercentBonus[Globals.ALL_UPGRADES[i].getOneEffect(j).getEffectsWhatResource()] += Globals.ALL_UPGRADES[i].getOneEffect(j).getEffectAmount();
                    }
                }
            }
        }
        for(int i = 0; i < Globals.NUMBER_OF_JOBS; i++){
            for(int j = 0; j < Globals.ALL_JOBS[i].getNumberOfEffects(); j++){
                totalJobResourceProduction[Globals.ALL_JOBS[i].getOneEffect(j).getEffectsWhatObject()] += Globals.ALL_JOBS[i].getOneEffect(j).getEffectAmount() * Globals.ALL_JOBS[i].getNumberWorking();
            }
        }
        for(int i = 0; i < Globals.NUMBER_OF_MAGIC_EFFECTS; i++){
            if(Globals.ALL_MAGIC_EFFECTS[i].getEnablable() && Globals.ALL_MAGIC_EFFECTS[i].getEnabled()){
                for(int j = 0; j < Globals.ALL_MAGIC_EFFECTS[i].getEffects().length; j++){
                    if(Globals.ALL_MAGIC_EFFECTS[i].getOneEffectsWhatObjectType(j) == Globals.RESOURCE_IDENTIFIER &&
                       Globals.ALL_MAGIC_EFFECTS[i].getOneTypeOfEffect(j) == Globals.POSITIVE_PRODUCTION_IDENTIFIER){
                    
                        totalPositiveResourceProduction[Globals.ALL_MAGIC_EFFECTS[i].getOneEffectsWhichResource(j)] += Globals.ALL_MAGIC_EFFECTS[i].getOneEffectAmount(j);
                    }
                    else if(Globals.ALL_MAGIC_EFFECTS[i].getOneEffectsWhatObjectType(j) == Globals.RESOURCE_IDENTIFIER &&
                            Globals.ALL_MAGIC_EFFECTS[i].getOneTypeOfEffect(j) == Globals.NEGATIVE_PRODUCTION_IDENTIFIER){
                    
                        totalNegativeResourceProduction[Globals.ALL_MAGIC_EFFECTS[i].getOneEffectsWhichResource(j)] += Globals.ALL_MAGIC_EFFECTS[i].getOneEffectAmount(j);
                    }
                }
            }
        }
        if(Globals.SNOWSTORM == 1){
            for(int i = 0; i < Globals.NUMBER_OF_RESOURCES; i++){
                if(Globals.ALL_RESOURCES[i].getIdentifier() == 'b'){
                    totalPositiveResourceProduction[i] = totalPositiveResourceProduction[i] * 0.70;
                }
            }
        }

        bearResourceConsumption[Utils.findResourceFromIdentifier('b')] += 3 * Globals.ALL_RESOURCES[Utils.findResourceFromIdentifier('B')].getAmount();
        bearResourceConsumption[Utils.findResourceFromIdentifier('W')] += 0.5 * Globals.ALL_RESOURCES[Utils.findResourceFromIdentifier('B')].getAmount();
        
        bearCraftableResourceConsumption[Utils.findCraftableResourceFromIdentifier('p')] += 0.09 * Globals.ALL_RESOURCES[Utils.findResourceFromIdentifier('B')].getAmount();
        bearCraftableResourceConsumption[Utils.findCraftableResourceFromIdentifier('t')] += 0.04 * Globals.ALL_RESOURCES[Utils.findResourceFromIdentifier('B')].getAmount();
            
        
        
        for(int i = 0; i < Globals.NUMBER_OF_RESOURCES; i++){
            Globals.AMOUNT_PER_SECOND[i] = totalPositiveResourceProduction[i] * (1 + totalPercentBonusResources[i]) - totalNegativeResourceProduction[i] - bearResourceConsumption[i] + totalJobResourceProduction[i] * (1 + totalJobPercentBonus[i]) * (calculateHappiness() / 100.0);
        }
        for(int i = 0; i < Globals.NUMBER_OF_CRAFTABLE_RESOURCES; i++){
            Globals.AMOUNT_PER_SECOND_CRAFTABLE[i] = totalPositiveCraftableResourceProduction[i] * (1 + totalPercentBonusCraftableResources[i]) - totalNegativeCraftableResourceProduction[i] - bearCraftableResourceConsumption[i];
        }
    }
    public static int calculateMaxEnabledBuildings(int index){
        int i = 0;
        boolean canProduce = false;
        if(Globals.ALL_BUILDINGS[index].getNumberEnabled() == 0){
            return 0;
        }
        for(i = Globals.ALL_BUILDINGS[index].getNumberEnabled(); i >= 0 && !canProduce; i--){
            if(Utils.checkBuildingProduction(index, i)){
                 canProduce = true;   
                 //System.out.println(canProduce + " " + i + " " + Globals.ALL_BUILDINGS[index].getName());
            }
        }
        i++;
        //System.out.println(Globals.ALL_BUILDINGS[index].getName() + " " + i);
        return i;
    }
    public static void calculateBuildingProductionBonuses(){
        buildingBonuses = new double[Globals.NUMBER_OF_BUILDINGS];
        
        for(int i = 0; i < Globals.NUMBER_OF_BUILDINGS; i++){
            for(int j = 0; j < Globals.ALL_BUILDINGS[i].getNumberOfEffects(); j++){
                if(Globals.ALL_BUILDINGS[i].getOneEffect(j).getTypeOfEffect() == Globals.PERCENT_BONUS_IDENTIFIER && Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectsWhatObjectType() == Globals.BUILDING_IDENTIFIER){
                    buildingBonuses[Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectsWhatObject()] += Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectAmount() * calculateMaxEnabledBuildings(i);
                }
            }
        }
    }
    public static double calculateCraftEffectiveness(){
        double craftEffectiveness = 1;
        for(int i = 0; i < Globals.NUMBER_OF_BUILDINGS; i++){
            for(int j = 0; j < Globals.ALL_BUILDINGS[i].getNumberOfEffects(); j++){
                if(Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectsWhatObjectType() == Globals.CRAFT_EFFECTIVENESS_IDENTIFIER){
                    craftEffectiveness += Globals.ALL_BUILDINGS[i].getOneEffect(j).getEffectAmount() * Globals.ALL_BUILDINGS[i].getNumberEnabled();
                }
            }
        }
        return craftEffectiveness;
    }
    public static int calculateSnowstormChance(){
        int a = 8;
        
        if(Globals.ALL_MAGIC_EFFECTS[1].getEnabled()){
            a -= 6;
        }
        
        return a;
    }
    public static int calculateFreeBears(){
        int j = (int)Globals.ALL_RESOURCES[Utils.findResourceFromIdentifier('B')].getAmount();
        for(int i = 0; i < Globals.NUMBER_OF_JOBS; i++){
            j = j - Globals.ALL_JOBS[i].getNumberWorking();
        }
        return j;
    }
    public static int calculateFreeTraders(){
        int t = Globals.ALL_JOBS[Utils.findJobFromIdentifier('t')].getNumberWorking();
        for(int i = 0; i < Globals.NUMBER_OF_CIVILIZATIONS; i++){
            t = t - Globals.ALL_CIVILIZATIONS[i].getNumberTrading();
        }
        return t;
    }
    public static void calculateScienceBonuses(){
        if(Globals.ALL_SCIENCES[Utils.findScienceFromIdentifier('c')].getIsResearched()){
            Globals.GAME.revealTime();
        }
    }
    public static void calculateHuntingPoints(){
        if(Globals.HUNTING_POINTS + Globals.ALL_JOBS[Utils.findJobFromIdentifier('h')].getNumberWorking() < 120){
            Globals.HUNTING_POINTS = Globals.HUNTING_POINTS + 1 * Globals.ALL_JOBS[5].getNumberWorking();
        }
        else{
            Globals.HUNTING_POINTS = 120;
        }
    }
    public static void calculateHuntingRewards(){
        String message = "";
        int i = (int)(Math.random() * 5);
        if(Globals.SNOWSTORM > 0 && i == 0){
            message = Utils.makeBearDeathMessage(3) + "\n\n";
        }
        else{
            double pelts = 75 + (int)(Math.random() * 100);
            double tusks = (int)(Math.random() * 80);
        
            Globals.ALL_CRAFTABLE_RESOURCES[Utils.findCraftableResourceFromIdentifier('p')].changeAmount(pelts);
            if(tusks >= 40){
                Globals.ALL_CRAFTABLE_RESOURCES[Utils.findCraftableResourceFromIdentifier('t')].changeAmount(tusks / 2);
                tusks = tusks / 2;
            }
            else{
                tusks = 0;
            }
        
            if(Utils.round(pelts, 2) > 0 || Utils.round(tusks, 2) > 0){
                message = "Your hunters have returned.";
                if(Utils.round(pelts, 2) > 0){
                    message = message + " +" + Utils.round(pelts, 2) + " pelts.";
                }
                if(Utils.round(tusks, 2) > 0){
                    message = message + " +" + Utils.round(tusks, 2) + " tusks.";
                }
                message = message + "\n\n";
            }
        }
        
        Globals.GAME.writeToEvent(message);
    }
    public static int calculateAuroraChance(){
        int a = 2; // add more later
        return a;
    }
    public static double calculateAuroraBonus(){
        double a = Utils.round(Globals.ALL_RESOURCES[Utils.findResourceFromIdentifier('s')].getMaximum() * 0.04, 2);
        return a;
    }
    public static int calculateStarsAligningChance(){
        int a = 1; //add more later
        return a;
    }
    public static double calculateStarsAligningBonus(){
        double a = Utils.round(Globals.ALL_RESOURCES[Utils.findResourceFromIdentifier('m')].getMaximum() * 0.01, 2);
        return a;
    }
    public static int calculateHappiness(){
        bonusResourceHappiness = 0;
        magicLoss = 0;
        overPopulationLoss = 0;
        
        if(Globals.ALL_RESOURCES[Utils.findResourceFromIdentifier('B')].getAmount() > 3){
            overPopulationLoss = (3 - Globals.ALL_RESOURCES[Utils.findResourceFromIdentifier('B')].getAmount()) * 1.4;
            
        }
        if(Globals.ALL_RESOURCES[Utils.findResourceFromIdentifier('B')].getAmount() > Globals.ALL_RESOURCES[Utils.findResourceFromIdentifier('B')].getMaximum()){
            overPopulationLoss += -10 * (Globals.ALL_RESOURCES[Utils.findResourceFromIdentifier('B')].getAmount() - Globals.ALL_RESOURCES[Utils.findResourceFromIdentifier('B')].getMaximum());
        }
        for(int i = 0; i < Globals.NUMBER_OF_MAGIC_EFFECTS; i++){
            if(Globals.ALL_MAGIC_EFFECTS[i].getEnabled() && Globals.ALL_MAGIC_EFFECTS[i].getEnablable()){
                magicLoss -= 3;
            }
        }
        if(Globals.ALL_CRAFTABLE_RESOURCES[Utils.findCraftableResourceFromIdentifier('p')].getAmount() > 0){
            bonusResourceHappiness += 10;
        }
        if(Globals.ALL_CRAFTABLE_RESOURCES[Utils.findCraftableResourceFromIdentifier('t')].getAmount() > 0){
            bonusResourceHappiness += 10;
        }
        
        double h = 100 + overPopulationLoss + magicLoss + bonusResourceHappiness;
        return (int)Utils.round(h, 0);
    }
}
