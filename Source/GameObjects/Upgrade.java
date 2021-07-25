package GameObjects;
import Utils.Globals;
import incrementalgame.Initialize;
import java.util.ArrayList;

public class Upgrade extends Buildable{
    private transient boolean researched;
    private int dependancy;
    private transient boolean visible;
    private String toolTipText;
    private ArrayList<Effect> effects;
    private boolean scienceDependancy;
    
    public Upgrade(){
        researched = true;
        dependancy = 0;
        visible = false;
        toolTipText = "";
    }
    public void initializeUpgrade(boolean r){
        researched = r;
        checkVisible();
        checkEffects();

        for(int i = 0; i < effects.size(); i++){
            effects.get(i).initializeEffect();
        }
    }
    public void researchUpgrade(){
        if(checkIfBuildable()){
            researched = true;
            visible = false;
            for(int i = 0; i < super.getNumberOfResources(); i++){
                if(super.getOneResourceCraftable(i)){
                    Globals.ALL_CRAFTABLE_RESOURCES[super.getOneRequired(i)].changeAmount(-super.getOnePrice(i));
                }
                else{
                    Globals.ALL_RESOURCES[super.getOneRequired(i)].changeAmount(-super.getOnePrice(i));
                }
            }
            checkEffects();
        }
    }
    public void checkVisible(){
        if(Globals.ALL_SCIENCES[dependancy].getIsResearched() && !researched){
            visible = true;
        }
    }
    public void checkEffects(){
        for(int i = 0; i < effects.size(); i++){
            if(effects.get(i).getEffectsWhatObjectType() == Globals.BUILDING_IDENTIFIER && researched){
                if(effects.get(i).getTypeOfEffect() == Globals.POSITIVE_PRODUCTION_IDENTIFIER || effects.get(i).getTypeOfEffect() == Globals.NEGATIVE_PRODUCTION_IDENTIFIER){
                    Globals.ALL_BUILDINGS[effects.get(i).getEffectsWhatObject()].addEffect(effects.get(i).getTypeOfEffect(), effects.get(i).getEffectAmount(), Globals.RESOURCE_IDENTIFIER, effects.get(i).getEffectsWhatResource(), 0);                
                }
            }
            if(effects.get(i).getEffectsWhatObjectType() == Globals.JOB_IDENTIFIER && researched){
                if(effects.get(i).getTypeOfEffect() == Globals.PERCENT_BONUS_IDENTIFIER){
                   Globals.ALL_JOBS[effects.get(i).getEffectsWhatObject()].addEffect(Globals.RESOURCE_IDENTIFIER, effects.get(i).getEffectsWhatResource(), Globals.PERCENT_BONUS_IDENTIFIER, effects.get(i).getEffectAmount());
                }
            }
        }
    }
    public boolean getVisible(){
        return visible;
    }
    public String getToolTipText(){
        return toolTipText;
    }
    public boolean getResearched(){
        return researched;
    }
    public Effect getOneEffect(int e){
        return effects.get(e);
    }
    public int getNumberOfEffects(){
        return effects.size();
    }
}
