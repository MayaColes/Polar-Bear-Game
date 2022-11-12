package GameObjects;
import Utils.Globals;
import Utils.Utils;
import incrementalgame.Initialize;

public class MagicEffect {
    private transient boolean visible;
    private String name;
    private String toolTipText;
    private transient boolean enabled;
    private int dependancy;
    private boolean enablable;
    private Effect[] effects;
    
    public MagicEffect(){
        visible = false;
        name = "";
        toolTipText = "";
        enabled = false;
        dependancy = 0;
        enablable = false;
    }
    public MagicEffect(boolean v, String n, String t, boolean e, int d, boolean b){
            visible = v;
            name = n;
            toolTipText = t;
            enabled = e;
            dependancy = d;
            enablable = b;
    }
    public void initializeMagicEffect(){
        visible = Globals.ALL_MAGIC[dependancy].getIsResearched();
        enabled = false;

        for(int i = 0; i < effects.length; i++){
            effects[i].initializeEffect();
        }
    }
    public boolean getEnabled(){
        return enabled;
    }
    public boolean getVisible(){
        return visible;
    }
    public void switchEnabled(){
        if(enablable){
            enabled = !enabled;
        }
        else{
            boolean canCast = true;
            
            for(int i = 0; i < effects.length; i++){
                if(effects[i].getTypeOfEffect() == Globals.NEGATIVE_PRODUCTION_IDENTIFIER && Globals.ALL_RESOURCES[effects[i].getEffectsWhatObject()].getAmount() < effects[i].getEffectAmount()){
                    canCast = false;
                }
            }
            if(canCast){
                for(int i = 0; i < effects.length; i++){
                    if(effects[i].getTypeOfEffect() == Globals.NEGATIVE_PRODUCTION_IDENTIFIER){
                        Globals.ALL_RESOURCES[effects[i].getEffectsWhatObject()].changeAmount(-effects[i].getEffectAmount());
                    }
                    else if(effects[i].getTypeOfEffect() == Globals.POSITIVE_PRODUCTION_IDENTIFIER){
                        Globals.ALL_RESOURCES[effects[i].getEffectsWhatObject()].changeAmount(effects[i].getEffectAmount());
                    }
                }
            }
        }
    }
    public String getName(){
        return name;
    }
    public String getToolTipText(){
        return toolTipText;
    }
    public boolean getEnablable(){
        return enablable;
    }
    public void checkVisible(){
        visible = Globals.ALL_MAGIC[dependancy].getIsResearched();
    }
    public Effect[] getEffects(){
        return effects;
    }
    public Effect getOneEffect(int e){
        return effects[e];
    }
}
