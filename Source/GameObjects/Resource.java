package GameObjects;
import Utils.Globals;
import incrementalgame.Initialize;

public class Resource {
    private transient double amount;
    private String name;
    private double defaultMaximum;
    private transient double maximum;
    private transient boolean visible;
    private String color;
    private char identifier;
    private transient String config;
    
    public Resource(){
        amount = 0;
        name = "";
        maximum = 0;
        defaultMaximum = 0;
        visible = false;
        color = "black";
        identifier = Globals.DEFAULT_IDENTIFIER;
    }
    public Resource(double a, String n, double m, boolean v, String c){
        amount = a;
        name = n;
        maximum = m;
        defaultMaximum = m;
        visible = v;
        color = c;
    }
    public void initializeIdentifier(){
        config = Initialize.buildFromConfig();
        
        identifier = config.charAt(0);
    }
    public void initializeResource(double a, boolean v){
        if(a >= 0){
            amount = a;
        }
        else{
            amount = 0;
        }
        visible = v;
        
        name = config.substring(1, config.indexOf(Globals.END_OF_NAME_MARKER));
        color = config.substring(config.indexOf(Globals.END_OF_NAME_MARKER) + 1, config.indexOf(Globals.END_OF_COLOR_MARKER));
        defaultMaximum = Integer.parseInt(config.substring(config.indexOf(Globals.END_OF_COLOR_MARKER) + 1,
                  config.indexOf(Globals.END_OF_RECORD_MARKER)));
        maximum = defaultMaximum;
    }
    public void changeAmount(double a){
        if(a != 0){
            if(!(amount + a > maximum || amount + a < 0)){
                amount = amount + a;
            }
            else if(amount + a > maximum){
                if(amount < maximum){
                    amount = maximum;
                }
            }
            else if(amount + a < 0){
                amount = 0;
            }
        }
    }
    public void checkVisible(){
        if(amount > 0 && !visible){
            visible = true;
            Globals.NEW_RESOURCE = true;
        }
    }
    public String getName(){
        return name;
    }
    public double getMaximum(){
        return maximum;
    }
    public double getDefaultMaximum(){
        return defaultMaximum;
    }
    public void setMaximum(double m){
        maximum = m;
    }
    public double getAmount(){
        return amount;
    }
    public boolean getVisible(){
        return visible;
    }
    public String getColor(){
        return color;
    }
    public char getIdentifier(){
        return identifier;
    }
    public void setVisible(boolean v){
        visible = v;
    }
}
