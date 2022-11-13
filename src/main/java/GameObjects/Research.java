package GameObjects;
import Utils.Globals;

public class Research extends Buildable{
    private transient boolean isResearched;
    private Research dependancy;
    private String toolTipText;
    private transient boolean visible;
    
    public Research(boolean i, Research d, boolean v, String t){
        isResearched = i;
        dependancy = d;
        visible = v;
        toolTipText = "";
    }
    public void initalizeResearch(boolean i){
        isResearched = i;
        checkVisible();
    }
    public void completeResearch(){
        if(checkIfBuildable()){
            visible = false;
            isResearched = true;
            for(int i = 0; i < super.getNumberOfResources(); i++){
                  Globals.ALL_RESOURCES[super.getOneRequired(i)].changeAmount(-super.getOnePrice(i));
            }
        }
    }
    public void checkVisible(){
        if(dependancy != null && !isResearched){
            if(dependancy.getIsResearched()){
                visible = true;
            }
            else{
                visible = false;
            }
        }
        else if(dependancy == null && !isResearched){
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
