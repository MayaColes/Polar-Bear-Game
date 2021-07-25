package GameObjects;
import Utils.Globals;
import Utils.Utils;

public class Effect {
    private transient String stringEffect;
    private char typeOfEffect;
    private double effectAmount;
    private transient double effectAmountWithBonus;
    private char effectsWhatObjectType;
    private int effectsWhatObject;
    private int effectsWhatResource;
    
    public Effect(){
        stringEffect = "";
        typeOfEffect = Globals.DEFAULT_IDENTIFIER;
        effectAmount = 0;
        effectsWhatObjectType = Globals.DEFAULT_IDENTIFIER;
        effectsWhatObject = -1;
        effectsWhatResource = -1;
    }
    public Effect(char i, double a, char ot, int o, int r){
        typeOfEffect = i;
        effectAmount = a;
        effectsWhatObjectType = ot;
        effectsWhatObject = o;
        effectsWhatResource = r;
        makeEffect();
    }
    public void createEffect(char i, double a, char ot, int o, int r){
        typeOfEffect = i;
        effectAmount = a;
        effectsWhatObjectType = ot;
        effectsWhatObject = o;
        effectsWhatResource = r;
        makeEffect();
    }
    public void createEffect(String s){
        effectsWhatObjectType = s.charAt(0);
            
        boolean found = false;
        char identifier = s.charAt(1);
            
        if(effectsWhatObjectType == Globals.RESOURCE_IDENTIFIER){
            effectsWhatObject = Utils.findResourceFromIdentifier(identifier);
            effectsWhatResource = -1;
        }
        else if(effectsWhatObjectType == Globals.CRAFTABLE_RESOURCE_IDENTIFIER){
            effectsWhatObject = Utils.findCraftableResourceFromIdentifier(identifier);
            effectsWhatResource = -1;
        }
        else if(effectsWhatObjectType == Globals.JOB_IDENTIFIER){
            effectsWhatObject = Utils.findJobFromIdentifier(identifier);
            effectsWhatResource = Utils.findResourceFromIdentifier(s.charAt(2));
        }
        else if(effectsWhatObjectType == Globals.BUILDING_IDENTIFIER){
            effectsWhatObject = Utils.findBuildingFromIdentifier(identifier);
            effectsWhatResource = Utils.findResourceFromIdentifier(s.charAt(2));
        }
        else if(effectsWhatObjectType == Globals.CRAFT_EFFECTIVENESS_IDENTIFIER){
            effectsWhatObject = -1;
            effectsWhatResource = -1;
        }
            
        typeOfEffect = s.charAt(3);
        if(s.substring(4, s.indexOf(Globals.END_OF_EFFECT_MARKER)).length() == 0){
            effectAmount = Double.parseDouble(s.charAt(4) + "");
        }
        else{
            effectAmount = Double.parseDouble(s.substring(4, s.indexOf(Globals.END_OF_EFFECT_MARKER)));
        }
        
        if(typeOfEffect == Globals.PERCENT_BONUS_IDENTIFIER){
            effectAmount = effectAmount / 100.0;
        }
        
        effectAmountWithBonus = effectAmount;
        makeEffect();
    }
    public void makeEffect(){
        StringBuffer effect = new StringBuffer();
        
        if(effectsWhatObjectType == Globals.RESOURCE_IDENTIFIER){
            effect.append(Globals.ALL_RESOURCES[effectsWhatObject].getName());
        }
        else if(effectsWhatObjectType == Globals.CRAFTABLE_RESOURCE_IDENTIFIER){
            effect.append(Globals.ALL_CRAFTABLE_RESOURCES[effectsWhatObject].getName());
        }
        else if(effectsWhatObjectType == Globals.JOB_IDENTIFIER){
            effect.append(Globals.ALL_JOBS[effectsWhatObject].getName() + " ");
            effect.append(Globals.ALL_RESOURCES[effectsWhatResource].getName());
        }
        else if(effectsWhatObjectType == Globals.BUILDING_IDENTIFIER){
            effect.append(Globals.ALL_BUILDINGS[effectsWhatObject].getName() + " ");
            effect.append(Globals.ALL_RESOURCES[effectsWhatResource].getName());
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
            effect.append(": +" +  + Utils.round(effectAmount, 2));
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
        return effectsWhatObject;
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
        return effectsWhatResource;
    }
}
