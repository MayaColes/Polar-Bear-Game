package GameObjects;
import Utils.Globals;
import incrementalgame.Initialize;

public class Science extends Buildable{
    private transient boolean isResearched;
    private int dependancy;
    private String toolTipText;
    private transient boolean visible;
    
    public Science(){
        isResearched = false;
        dependancy = 0;
        visible = false;
        toolTipText = "";
    }
    public Science(boolean i, int d, boolean v, String t){
        isResearched = i;
        dependancy = d;
        visible = v;
        toolTipText = "";
    }
    public void initalizeScience(boolean i){
        isResearched = i;
        checkVisible();
    }
    public void researchScience(){
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
            if(Globals.ALL_SCIENCES[dependancy].getIsResearched()){
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
