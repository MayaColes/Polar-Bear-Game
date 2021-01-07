package GameObjects;
import Utils.Globals;
import Utils.AmountCalculator;
import incrementalgame.Initialize;

public class CraftableResource extends Buildable{
    private String toolTipText;
    private double amount;
    private boolean canCraft;
    private boolean craftable;
    private boolean visible;
    private int dependancy;
    private boolean scienceDependancy;
    private String config;
    
    public CraftableResource(){
        amount = 0;
        craftable = false;
        visible = false;
        canCraft = false;
        toolTipText = "";
        dependancy = 0;
    }
    public CraftableResource(double a, boolean c, boolean v, int d, boolean b){
        amount = a;
        craftable = c;
        visible = v;
        toolTipText = "";
        dependancy = d;
        canCraft = b;
    }
    public void initializeIdentifier(){
        super.intitializeIdentifier(Initialize.buildFromConfig());
    }
    public void initializeCraftableResource(double a, boolean v){
        amount = a;
        
        super.initialize();
        config = super.getConfig();
        
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
        
        if(config.charAt(0) == '1'){
            canCraft = true;
            
            config = config.substring(config.indexOf(Globals.END_OF_NAME_MARKER) + 2);
            
            toolTipText = config.substring(0, config.indexOf(Globals.END_OF_RECORD_MARKER));
        }
        else{
            canCraft = false;
        }
        
        visible = v;
    }
    public void craftResource(int numberToCraft){
        if(checkIfBuildable()){
            amount = amount + AmountCalculator.calculateCraftEffectiveness() * numberToCraft;
            for(int i = 0; i < super.getNumberOfResources(); i++){
                  Globals.ALL_RESOURCES[super.getOneRequired(i)].changeAmount(-super.getOnePrice(i) * numberToCraft);
            }
        }
    }
    public void craftAll(){
        boolean canCraft = true;
        int numberCrafted = 0;
        
        while(canCraft){
            for(int i = 0; i < super.getNumberOfResources(); i++){
                if(Globals.ALL_RESOURCES[super.getOneRequired(i)].getAmount() < super.getOnePrice(i) * (numberCrafted + 1)){
                    canCraft = false;
                }
            }
            if(canCraft){
                numberCrafted++;
            }
        }
        
        amount = amount + AmountCalculator.calculateCraftEffectiveness() * numberCrafted;                                   //Add more later
        for(int i = 0; i < super.getNumberOfResources(); i++){
              Globals.ALL_RESOURCES[super.getOneRequired(i)].changeAmount(-super.getOnePrice(i) * numberCrafted);
        }
    }
    public void checkVisible(){
        if(canCraft && scienceDependancy){
            if(Globals.ALL_SCIENCES[dependancy].getIsResearched()){
                craftable = true;
            }
        }
        else if(canCraft && !scienceDependancy){
            if(Globals.ALL_MAGIC[dependancy].getIsResearched()){
                craftable = true;
            }
        }
        if(amount > 0 && !visible){
            visible = true;
            Globals.NEW_RESOURCE = true;
        }
    }
    public void changeAmount(double a){
        if(amount + a >= 0){
            amount = amount + a;
        }
        else{
            amount = 0;
        }
    }
    public void checkCraftable(){
        if(Globals.ALL_SCIENCES[dependancy].getIsResearched() && canCraft){
            craftable = true;
        }
        else{
            craftable = false;
        }
    }
    public boolean getVisible(){
        return visible;
    }
    public boolean getCraftable(){
        return craftable;
    }
    public double getAmount(){
        return amount;
    }
    public String getToolTipText(){
        return toolTipText;
    }
}
