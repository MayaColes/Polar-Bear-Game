package GameObjects;
import Utils.Globals;
import incrementalgame.Initialize;

public class Magic extends Buildable{
    private boolean isResearched;
    private int dependancy;
    private String toolTipText;
    private boolean visible;
    private String config;
    
    public Magic(){
        isResearched = false;
        dependancy = 0;
        visible = false;
        toolTipText = "";
    }
    public Magic(boolean i, int d, boolean v, String t){
        isResearched = i;
        dependancy = d;
        visible = v;
        toolTipText = "";
    }
    public void initializeIdentifier(){
        super.intitializeIdentifier(Initialize.buildFromConfig());
    }
    public void initalizeMagic(boolean i){
        isResearched = i;
        
        super.initialize();
        config = super.getConfig();
        
        char c = super.getDependancy().charAt(0);
        for(int j = 0; j < Globals.NUMBER_OF_MAGICS; j++){
            if(c == Globals.ALL_MAGIC[j].getIdentifier()){
                dependancy = j;
            }
            if(c == Globals.NO_DEPENDANCY_MARKER){
                dependancy = -1;
            }
        }
        
        checkVisible();
        
        toolTipText = config.substring(0, config.indexOf(Globals.END_OF_RECORD_MARKER));
    }
    public void researchMagic(){
        if(checkIfBuildable()){
            visible = false;
            isResearched = true;
            for(int i = 0; i < super.getNumberOfResources(); i++){
                  Globals.ALL_RESOURCES[super.getOneRequired(i)].changeAmount(-super.getOnePrice(i));
            }
        }
    }
    public void checkVisible(){
        if(dependancy != -1 && !isResearched){
            if(Globals.ALL_MAGIC[dependancy].getIsResearched() || Globals.ALL_MAGIC[dependancy].getIdentifier() == super.getIdentifier()){
                visible = true;
            }
            else{
                visible = false;
            }
        }
        else if(dependancy == -1 && !isResearched){
            visible = true;
        }
        else{
            visible = false;
        }
    }
    public String getToolTipText(){
        return toolTipText;
    }
    public boolean getIsResearched(){
        return isResearched;
    }
    public boolean getVisible(){
        return visible;
    }
    public void setVisible(boolean v){
        visible = v;
    }
}
