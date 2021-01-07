package GameObjects;
import Utils.Globals;
import Utils.Utils;
import incrementalgame.Initialize;

public class MagicEffect {
    private boolean visible;
    private String name;
    private String toolTipText;
    private boolean enabled;
    private int dependancy;
    private boolean enablable;
    private String[] effects;
    private char[] typeOfEffect;
    private double[] effectAmount;
    private char[] effectsWhatObjectType;
    private int[] effectsWhichObject;
    private int[] effectsWhichResource;
    
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
        String s = Initialize.buildFromConfig();
        
        if(s.charAt(0) == '1'){
            enablable = true;
        }
        else{
            enablable = false;
        }
        
        char n = s.charAt(1);
        for(int i = 0; i < Globals.NUMBER_OF_MAGICS; i++){
            if (n == Globals.ALL_MAGIC[i].getIdentifier()){
                dependancy = i;
            }
            else if (n == '-'){
                dependancy = -1;
            }
         }
        
        
        name = s.substring(2, s.indexOf(Globals.END_OF_NAME_MARKER));
        
        s = s.substring(s.indexOf(Globals.END_OF_NAME_MARKER) + 1);
        
        effects = new String[Integer.parseInt("" + s.charAt(0))];
        typeOfEffect = new char[effects.length];
        effectAmount = new double[effects.length];
        effectsWhatObjectType = new char[effects.length];
        effectsWhichObject = new int[effects.length];
        effectsWhichResource = new int[effects.length];
        
        s = s.substring(1);
        
        for(int i = 0; i < effects.length; i++){
            StringBuffer effect = new StringBuffer();
            
            effectsWhatObjectType[i] = s.charAt(0);
            
            boolean found = false;
            char identifier = s.charAt(1);
            
            if(effectsWhatObjectType[i] == Globals.RESOURCE_IDENTIFIER){
                for(int j = 0; j < Globals.NUMBER_OF_RESOURCES && !found; j++){
                    if(identifier == Globals.ALL_RESOURCES[j].getIdentifier()){
                        effectsWhichObject[i] = j;
                        effectsWhichResource[i] = j;
                        effect.append(Globals.ALL_RESOURCES[j].getName());
                        found = true;
                    }
                }
            }
            else if(effectsWhatObjectType[i] == Globals.CRAFTABLE_RESOURCE_IDENTIFIER){
                for(int j = 0; j < Globals.NUMBER_OF_CRAFTABLE_RESOURCES && !found; j++){
                    if(identifier == Globals.ALL_CRAFTABLE_RESOURCES[j].getIdentifier()){
                        effectsWhichObject[i] = j;
                        effectsWhichResource[i] = j;
                        effect.append(Globals.ALL_CRAFTABLE_RESOURCES[j].getName());
                        found = true;
                    }
                }
            }
            else if(effectsWhatObjectType[i] == Globals.BUILDING_IDENTIFIER){
                for(int j = 0; j < Globals.NUMBER_OF_BUILDINGS || !found; j++){
                    if(identifier == Globals.ALL_BUILDINGS[j].getIdentifier()){
                        effectsWhichObject[i] = j;
                        effect.append(Globals.ALL_BUILDINGS[j].getName() + " ");
                        found = true;
                    }
                }
                found = false;
                identifier = s.charAt(2);
                
                for(int j = 0; j < Globals.NUMBER_OF_RESOURCES || !found; j++){
                    if(identifier == Globals.ALL_RESOURCES[j].getIdentifier()){
                        effectsWhichResource[i] = j;
                        effect.append(Globals.ALL_RESOURCES[j].getName());
                        found = true;
                    }
                }
            }
            else if(effectsWhatObjectType[i] == Globals.JOB_IDENTIFIER){
                for(int j = 0; j < Globals.NUMBER_OF_JOBS && !found; j++){
                    if(identifier == Globals.ALL_JOBS[j].getIdentifier()){
                        effectsWhichObject[i] = j;
                        effect.append(Globals.ALL_JOBS[j].getName() + " ");
                        found = true;
                    }
                }
                found = false;
                identifier = s.charAt(2);
                for(int j = 0; j < Globals.NUMBER_OF_RESOURCES || !found; j++){
                    if(identifier == Globals.ALL_RESOURCES[j].getIdentifier()){
                        effectsWhichResource[i] = j;
                        effect.append(Globals.ALL_RESOURCES[j].getName());
                        found = true;
                    }
                }
            }
            else if(effectsWhatObjectType[i] == Globals.CRAFT_EFFECTIVENESS_IDENTIFIER){
                effectsWhichObject[i] = 0;
                effectsWhichResource[i]  = 0;
                effect.append("Craft Effectiveness: ");
            }
            
            typeOfEffect[i] = s.charAt(3);
            if(s.substring(4, s.indexOf(Globals.END_OF_EFFECT_MARKER)).length() == 0){
                effectAmount[i] = Double.parseDouble(s.charAt(4) + "");
            }
            else{
                effectAmount[i] = Double.parseDouble(s.substring(4, s.indexOf(Globals.END_OF_EFFECT_MARKER)));
            }
            
            if(typeOfEffect[i] == Globals.POSITIVE_PRODUCTION_IDENTIFIER){
                if(enablable){
                    effect.append(" Production: +" + Utils.round(effectAmount[i], 2));
                }
                else{
                    effect.append(": +" + Utils.round(effectAmount[i], 2));
                }
            }
            else if(typeOfEffect[i] == Globals.NEGATIVE_PRODUCTION_IDENTIFIER){
                if(enablable){
                    effect.append(" Production: -" + Utils.round(effectAmount[i], 2));
                }
                else{
                    effect.append(": -" + Utils.round(effectAmount[i], 2));
                }
            }
            else if(typeOfEffect[i] == Globals.PERCENT_BONUS_IDENTIFIER){
                effectAmount[i] = effectAmount[i] / 100.0;
                effect.append(" Bonus: +" + Utils.round(effectAmount[i] * 100, 2) + "%");
            }
            else if(typeOfEffect[i] == Globals.MAXIMUM_IDENTIFIER){
                effect.insert(0, " Maximum ");
                effect.append(": +" +  + Utils.round(effectAmount[i], 2));
            }
            
            effects[i] = effect + "";
            s = s.substring(s.indexOf(Globals.END_OF_EFFECT_MARKER) + 1);
            //System.out.println(s);
        }
        
        toolTipText = s.substring(s.indexOf(Globals.END_OF_NAME_MARKER) + 1, s.indexOf(Globals.END_OF_RECORD_MARKER));
        
        visible = Globals.ALL_MAGIC[dependancy].getIsResearched();
        
        enabled = false;
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
                if(typeOfEffect[i] == Globals.NEGATIVE_PRODUCTION_IDENTIFIER && Globals.ALL_RESOURCES[effectsWhichObject[i]].getAmount() < effectAmount[i]){
                    canCast = false;
                }
            }
            if(canCast){
                for(int i = 0; i < effects.length; i++){
                    if(typeOfEffect[i] == Globals.NEGATIVE_PRODUCTION_IDENTIFIER){
                        Globals.ALL_RESOURCES[effectsWhichObject[i]].changeAmount(-effectAmount[i]);
                    }
                    else if(typeOfEffect[i] == Globals.POSITIVE_PRODUCTION_IDENTIFIER){
                        Globals.ALL_RESOURCES[effectsWhichObject[i]].changeAmount(effectAmount[i]);
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
    public String[] getEffects(){
        return effects;
    }
    public String getOneEffect(int e){
        return effects[e];
    }
    public char getOneTypeOfEffect(int e){
        return typeOfEffect[e];
    }
    public char getOneEffectsWhatObjectType(int e){
        return effectsWhatObjectType[e];
    }
    public int getOneEffectsWhichObject(int e){
        return effectsWhichObject[e];
    }
    public double getOneEffectAmount(int e){
        return effectAmount[e];
    }
    public int getOneEffectsWhichResource(int e){
        return effectsWhichResource[e];
    }
}
