package GameObjects;
import Utils.Globals;

public class Research extends Buildable{
    private transient boolean isResearched;
    private transient Research dependancy;
    private int dependancyPos;
    private String toolTipText;
    private transient boolean visible;
    
    public Research(boolean i, int d, boolean v, String t){
        isResearched = i;
        dependancyPos = d;
        visible = v;
        toolTipText = "";
    }
    public void initalizeResearch(boolean i, Research dep){
        isResearched = i;
        dependancy = dep;
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
    public int getDependancyPos(){
        return dependancyPos;
    }
}
