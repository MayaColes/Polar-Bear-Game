package GameObjects;
import Utils.Globals;
import Utils.AmountCalculator;
import incrementalgame.Initialize;

public class CraftableResource extends Buildable{
    private String toolTipText;
    private transient double amount;
    private boolean canCraft;
    private transient boolean craftable;
    private transient boolean visible;
    private int dependancy;
    private boolean scienceDependancy;
    
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
    public void initializeCraftableResource(double a, boolean v){
        amount = a;
        visible = v;
        checkCraftable();
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
