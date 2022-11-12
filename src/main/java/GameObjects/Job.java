package GameObjects;
import Utils.Globals;
import Utils.Utils;
import Utils.AmountCalculator;
import incrementalgame.Initialize;
import java.util.ArrayList;

public class Job {
    private String name;
    private transient int numberWorking;
    private String toolTipText;
    private transient boolean visible;
    private char identifier;
    private ArrayList<Effect> effects;
    
    public Job(){
        name = "";
        numberWorking = 0;
        toolTipText = "";
        visible = false;
        identifier = Globals.DEFAULT_IDENTIFIER;
    }
    public Job(String n, int w, String t, boolean v){
        name = n;
        numberWorking = 0;
        toolTipText = t;
        visible = v;
    }
    public void initializeJob(int w){
        numberWorking = w;
        visible = false;

        for(int i = 0; i < effects.size(); i++){
            effects.get(i).initializeEffect();
        }
    }
    public void addEffect(char effectsWhichType, char whichObject, char effectType, double amount){
        if(effectType == Globals.POSITIVE_PRODUCTION_IDENTIFIER){
            Effect effect = new Effect();
            effect.createEffect(effectType, amount, effectsWhichType, whichObject, whichObject);
        }
    }
    public void updateEffectWithBonus(double amount, char objectType, int whichObject){
        for(int i = 0; i < effects.size(); i++){
            if(effects.get(i).getEffectsWhatObjectType() == objectType && effects.get(i).getEffectsWhatObject() == whichObject){
                effects.get(i).setEffectAmountWithBonus(effects.get(i).getEffectAmount() * amount);
            }
        }
    }
    public String getName(){
        return name;
    }
    public int getNumberWorking(){
        return numberWorking;
    }
    public String getToolTipText(){
        return toolTipText;
    }
    public void increaseWorking(){
        if(AmountCalculator.calculateFreeBears() > 0 && Globals.ALL_RESOURCES[6].getAmount() != 0){
            numberWorking++;
        }
    }
    public void decreaseWorking(){
        if(AmountCalculator.calculateFreeBears() != Globals.ALL_RESOURCES[Utils.findResourceFromIdentifier('B')].getAmount() && Globals.ALL_RESOURCES[Utils.findResourceFromIdentifier('B')].getAmount() != 0 && numberWorking > 0 && identifier != 't'){
            numberWorking--;
        }
        else if (numberWorking > 0 && identifier == 't' && AmountCalculator.calculateFreeTraders() > 0){
            numberWorking--;
        }
    }
    public boolean getVisible(){
        return visible;
    }
    public char getIdentifier(){
        return identifier;
    }
    public void setVisible(boolean v){
        visible = v;
    }
    public int getNumberOfEffects(){
        return effects.size();
    }
    public Effect getOneEffect(int e){
        return effects.get(e);
    }
}
