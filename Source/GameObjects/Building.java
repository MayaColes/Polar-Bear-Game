package GameObjects;
import Utils.Globals;
import incrementalgame.Initialize;
import java.util.ArrayList;

public class Building extends Buildable{
    private int numberBuilt;
    private double increaseRatio;
    private String toolTipText;
    private String secondaryToolTip;
    private ArrayList<Effect> effects;
    private boolean enablable;
    private int numberEnabled;
    private String config;
    private boolean scienceDependancy;
    private int dependancy;
    private boolean buildable;
    
    public Building(){
        numberBuilt = 0;
        increaseRatio = 1.0;
        toolTipText = "";
        secondaryToolTip = "";
        enablable = false;
        numberEnabled = 0;
        effects = new ArrayList(0);
    }
    public Building(int m, double i, String t, boolean e){
        numberBuilt = m;
        increaseRatio = i;
        toolTipText = t;
        enablable = e;
        numberEnabled = 0;
    }
    public void initializeIdentifier(){
        super.intitializeIdentifier(Initialize.buildFromConfig());
    }
    public void initializeBuilding(int b, int e){
        //System.out.println(s);
        
        super.initialize();
        config = super.getConfig();
        
        numberBuilt = b;
        
        if(super.getDependancy().charAt(0) == 's'){
            scienceDependancy = true;
            char n = super.getDependancy().charAt(1);
            for(int i = 0; i < Globals.NUMBER_OF_TECHNOLOGIES; i++){
                if (n == Globals.ALL_SCIENCES[i].getIdentifier()){
                    dependancy = i;
                }
                else if (n == '-'){
                    dependancy = -1;
                }
            }
            
        }
        else{
            scienceDependancy = false;
            char n = super.getDependancy().charAt(1);
            for(int i = 0; i < Globals.NUMBER_OF_MAGICS; i++){
                if (n == Globals.ALL_MAGIC[i].getIdentifier()){
                    dependancy = i;
                }
            }
        }
        
        int numberOfEffects = Integer.parseInt(config.charAt(0) + "");
        
        effects = new ArrayList(0);
        
        config = config.substring(1);
        
        for(int i = 0; i < numberOfEffects; i++){
            Effect effect = new Effect();
            effect.createEffect(config.substring(0, config.indexOf(Globals.END_OF_EFFECT_MARKER) + 1));
            effects.add(effect);
            config = config.substring(config.indexOf(Globals.END_OF_EFFECT_MARKER) + 1);
        }
        
        sortEffects();
        
        if(config.charAt(0) == '1'){
            enablable = true;
            numberEnabled = e;
        }
        else{
            enablable = false;
            numberEnabled = b;
        }
        
        increaseRatio = Double.parseDouble(config.substring(1, config.indexOf(Globals.END_OF_RATIO_MARKER)));
        
        for(int i = 0; i < b; i++){
            for(int j = 0; j < super.getNumberOfResources(); j++){
                super.setOnePrice(j, super.getOnePrice(j) * increaseRatio);
            }
        }
        
        config = config.substring(config.indexOf(Globals.END_OF_RATIO_MARKER) + 1);
        
        if(config.indexOf(Globals.END_OF_TOOLTIP_MARKER) == -1){
            toolTipText = config.substring(0, config.indexOf(Globals.END_OF_RECORD_MARKER));
            secondaryToolTip = "";
        }
        else{
            toolTipText = config.substring(0, config.indexOf(Globals.END_OF_TOOLTIP_MARKER));
            secondaryToolTip = config.substring(config.indexOf(Globals.END_OF_TOOLTIP_MARKER) + 1, config.indexOf(Globals.END_OF_RECORD_MARKER));
        }
    }
    public void buildBuilding(){
        if(checkIfBuildable()){
            numberBuilt++;
            for(int i = 0; i < super.getNumberOfResources(); i++){
                if(super.getOneResourceCraftable(i)){
                    Globals.ALL_CRAFTABLE_RESOURCES[super.getOneRequired(i)].changeAmount(-super.getOnePrice(i));
                }
                else{
                    Globals.ALL_RESOURCES[super.getOneRequired(i)].changeAmount(-super.getOnePrice(i));
                }
                super.setOnePrice(i, super.getOnePrice(i) * increaseRatio);
            }
            
            numberEnabled++;
            
        }
    }
    public void sellBuilding(){
        if(numberBuilt > 0){
            numberBuilt--;
            numberEnabled--;
            
            for(int i = 0; i < super.getNumberOfResources(); i++){
                if(super.getOneResourceCraftable(i)){
                    Globals.ALL_CRAFTABLE_RESOURCES[super.getOneRequired(i)].changeAmount(super.getOnePrice(i) * 0.5);
                }
                else{
                    Globals.ALL_RESOURCES[super.getOneRequired(i)].changeAmount(super.getOnePrice(i) * 0.5);
                }
                super.setOnePrice(i, super.getOnePrice(i) / increaseRatio);
            }
        }
    }
    public void checkBuildingVisibility(){
        if(dependancy == -1){
            buildable = true;
        }
        else if(scienceDependancy){
            if(Globals.ALL_SCIENCES[dependancy].getIsResearched()){
                buildable = true;
            }
        }
        else if(!scienceDependancy){
            if(Globals.ALL_MAGIC[dependancy].getIsResearched()){
                buildable = true;
            }
        }
    }
    public void addEffect(char i, double a, char ot, int o, int r){
        Effect effect = new Effect(i, a, ot, o, r);
        effects.add(effect);
        sortEffects();
    }
    public void sortEffects(){
        ArrayList<Integer> bonus = new ArrayList(); 
        ArrayList<Integer> maximum = new ArrayList();
        ArrayList<Integer> production = new ArrayList();
        ArrayList<Integer> consumption = new ArrayList();
        ArrayList<Integer> craftBonus = new ArrayList();
        
        ArrayList<Effect> sortedEffects = new ArrayList();
        
        for(int i = 0; i < effects.size(); i++){
            if(effects.get(i).getTypeOfEffect() == Globals.PERCENT_BONUS_IDENTIFIER){
                bonus.add(i);
            }
            else if(effects.get(i).getTypeOfEffect() == Globals.MAXIMUM_IDENTIFIER){
                maximum.add(i);
            }
            else if(effects.get(i).getTypeOfEffect() == Globals.POSITIVE_PRODUCTION_IDENTIFIER){
                production.add(i);
            }
            else if(effects.get(i).getTypeOfEffect() == Globals.NEGATIVE_PRODUCTION_IDENTIFIER){
                consumption.add(i);
            }
            else if(effects.get(i).getTypeOfEffect() == Globals.CRAFT_EFFECTIVENESS_IDENTIFIER){
                craftBonus.add(i);
            }
        }
        
        for(int i = 0; i < effects.size(); i++){
            if(maximum.size() != 0){
                sortedEffects.add(effects.get(maximum.get(0)));
                
                maximum.remove(0);
            }
            else if(bonus.size() != 0){
                sortedEffects.add(effects.get(bonus.get(0)));
                
                bonus.remove(0);
            }
            else if(craftBonus.size() != 0){
                sortedEffects.add(effects.get(craftBonus.get(0)));
                
                craftBonus.remove(0);
            }
            else if(production.size() != 0){
                sortedEffects.add(effects.get(production.get(0)));
                
                production.remove(0);
            }
            else if(consumption.size() != 0){
                sortedEffects.add(effects.get(consumption.get(0)));
                
                consumption.remove(0);
            }
        }
        effects = sortedEffects;
    }
    public int getNumberBuilt(){
        return numberBuilt;
    }
    public double getIncreaseRatio(){
        return increaseRatio;
    }
    public String getToolTipText(){
        return toolTipText;
    }
    public String getSecondaryToolTip(){
        return secondaryToolTip;
    }
    public boolean getEnablable(){
        return enablable;
    }
    public int getNumberEnabled(){
        return numberEnabled;
    }
    public int getNumberOfEffects(){
        return effects.size();
    }
    public Effect getOneEffect(int e){
        return effects.get(e);
    }
    public void increaseEnabled(){
        if(numberEnabled < numberBuilt){
            numberEnabled++;
        }
    }
    public void decreaseEnabled(){
        if(numberEnabled > 0){
            numberEnabled--;
        }
    }
    public void setNumberBuilt(int n){
        numberBuilt = n;
    }
    public void setIncreaseRatio(Double n){
        increaseRatio = n;
    }
    public boolean getBuildable(){
        return buildable;
    }
}
