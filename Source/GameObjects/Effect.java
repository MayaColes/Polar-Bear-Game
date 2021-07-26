package GameObjects;
import Utils.Globals;
import Utils.Utils;

public class Effect {
    private transient String stringEffect;
    private char typeOfEffect;
    private double effectAmount;
    private transient double effectAmountWithBonus;
    private char effectsWhatObjectType;
    private char effectsWhatObject;
    private char effectsWhatResource;
    
    public Effect(){
        stringEffect = "";
        typeOfEffect = Globals.DEFAULT_IDENTIFIER;
        effectAmount = 0;
        effectsWhatObjectType = Globals.DEFAULT_IDENTIFIER;
        effectsWhatObject = Globals.DEFAULT_IDENTIFIER;
        effectsWhatResource = Globals.DEFAULT_IDENTIFIER;
    }
    public Effect(char i, double a, char ot, char o, char r){
        typeOfEffect = i;
        effectAmount = a;
        effectsWhatObjectType = ot;
        effectsWhatObject = o;
        effectsWhatResource = r;
        makeEffect();
    }
    public void createEffect(char i, double a, char ot, char o, char r){
        typeOfEffect = i;
        effectAmount = a;
        effectsWhatObjectType = ot;
        effectsWhatObject = o;
        effectsWhatResource = r;
        makeEffect();
    }
    public void initializeEffect(){
        effectAmountWithBonus = effectAmount;
        makeEffect();
    }
    public void makeEffect(){
        StringBuffer effect = new StringBuffer();

        if(effectsWhatObjectType == Globals.RESOURCE_IDENTIFIER){
            effect.append(Globals.ALL_RESOURCES[Utils.findResourceFromIdentifier(effectsWhatObject)].getName());
        }
        else if(effectsWhatObjectType == Globals.CRAFTABLE_RESOURCE_IDENTIFIER){
            effect.append(Globals.ALL_CRAFTABLE_RESOURCES[Utils.findCraftableResourceFromIdentifier(effectsWhatObject)].getName());
        }
        else if(effectsWhatObjectType == Globals.JOB_IDENTIFIER){
            effect.append(Globals.ALL_JOBS[Utils.findJobFromIdentifier(effectsWhatObject)].getName() + " ");
            effect.append(Globals.ALL_RESOURCES[Utils.findResourceFromIdentifier(effectsWhatResource)].getName());
        }
        else if(effectsWhatObjectType == Globals.BUILDING_IDENTIFIER){
            effect.append(Globals.ALL_BUILDINGS[Utils.findResourceFromIdentifier(effectsWhatObject)].getName() + " ");
            effect.append(Globals.ALL_RESOURCES[Utils.findResourceFromIdentifier(effectsWhatResource)].getName());
        }
        else if(effectsWhatObjectType == Globals.CRAFT_EFFECTIVENESS_IDENTIFIER){
            effect.append("Craft Effectiveness");
        }
            
        if(typeOfEffect == Globals.POSITIVE_PRODUCTION_IDENTIFIER){
            effect.append(" Production: +" + Utils.round(effectAmountWithBonus, 2) + "/sec");
        }
        else if(typeOfEffect == Globals.NEGATIVE_PRODUCTION_IDENTIFIER){
            effect.append(" Production: -" + Utils.round(effectAmountWithBonus, 2) + "/sec");
        }
        else if(typeOfEffect == Globals.PERCENT_BONUS_IDENTIFIER){
            effect.append(" Bonus: +" + Utils.round(effectAmountWithBonus * 100, 2) + "%");
        }
        else if(typeOfEffect == Globals.MAXIMUM_IDENTIFIER){
            effect.insert(0, "Maximum ");
            effect.append(": +"  + Utils.round(effectAmount, 2));
        }
        stringEffect = (effect + "");
    }
    public String getStringEffect(){
        return stringEffect;
    }
    public char getTypeOfEffect(){
        return typeOfEffect;
    }
    public char getEffectsWhatObjectType(){
        return effectsWhatObjectType;
    }
    public int getEffectsWhatObject(){
        if(effectsWhatObjectType == 'b') {
            return Utils.findBuildingFromIdentifier(effectsWhatObject);
        }
        else if(effectsWhatObjectType == 'r'){
            return Utils.findResourceFromIdentifier(effectsWhatObject);
        }
        else if(effectsWhatObjectType == 'j'){
            return Utils.findJobFromIdentifier(effectsWhatObject);
        }
        else if(effectsWhatObjectType == 'c'){
            return Utils.findCraftableResourceFromIdentifier(effectsWhatObject);
        }
        return -1;
    }
    public double getEffectAmount(){
        return effectAmount;
    }
    public double getEffectAmountWithBonus(){
        return effectAmountWithBonus;
    }
    public void setEffectAmountWithBonus(double amount){
        effectAmountWithBonus = amount;
        makeEffect();
    }
    public int getEffectsWhatResource(){
        if(effectsWhatResource == Globals.DEFAULT_IDENTIFIER){
            return -1;
        }
        return Utils.findResourceFromIdentifier(effectsWhatResource);
    }
}
