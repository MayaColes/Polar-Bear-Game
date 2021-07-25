package GameObjects;

import Utils.Globals;

abstract public class Buildable{
    private String name;
    private char identifier;
    private char[] resourcesRequired;
    private double[] price;
    private boolean[] resourceCraftable;
    private transient String config;
    private transient String dependancy;
    
    public Buildable(){
        name = "";
        identifier = Globals.DEFAULT_IDENTIFIER;
        config = "";
    }
    public void intitializeIdentifier(String s){
        identifier = s.charAt(0);
        config = s;
    }
    public void initialize(){
        dependancy = config.substring(1, config.indexOf(Globals.END_OF_DEPENDANCY_MARKER) + 1);
        name = config.substring(config.indexOf(Globals.END_OF_DEPENDANCY_MARKER) + 1, config.indexOf(Globals.END_OF_NAME_MARKER));
        
        price = new double[Integer.parseInt("" + config.charAt(config.indexOf(Globals.END_OF_NAME_MARKER) + 1))];
        resourcesRequired = new char[price.length];
        resourceCraftable = new boolean[price.length];
            
        config = config.substring(config.indexOf(Globals.END_OF_NAME_MARKER) + 2);

        for(int j = 0;j < price.length; j++){
            boolean found = false;
            char resourceIdentifier = config.charAt(1);
            
            if(config.charAt(0) == 't'){
                resourceCraftable[j] = true;
                resourcesRequired[j] = resourceIdentifier;
            }
            else{
                resourceCraftable[j] = false;
                resourcesRequired[j] = resourceIdentifier;
            }
            
            if(config.substring(2,config.indexOf(Globals.END_OF_RESOURCE_MARKER)-1).length() == 0){
                price[j] = (double)Integer.parseInt("" + config.charAt(config.indexOf(Globals.END_OF_RESOURCE_MARKER)-1));
            }
            else{
                price[j] = (double)Integer.parseInt(config.substring(2,config.indexOf(Globals.END_OF_RESOURCE_MARKER)-1)) * 10;
            }
            
            config = config.substring(config.indexOf(Globals.END_OF_RESOURCE_MARKER) + 1);
        }
    }
    public boolean checkIfBuildable(){
        boolean canBuild = true;
        for(int i = 0; i < price.length; i++){
            if(!resourceCraftable[i]){
                if(Globals.ALL_RESOURCES[resourcesRequired[i]].getAmount() < price[i]){
                    canBuild = false;
                }
            }
            else if(resourceCraftable[i]){
                if(Globals.ALL_CRAFTABLE_RESOURCES[resourcesRequired[i]].getAmount() < price[i]){
                    canBuild = false;
                }
            }
        }
        return canBuild;
    }
    public int getNumberOfResources(){
        return price.length;
    }
    public double getOnePrice(int i){
        return price[i];
    }
    public void setOnePrice(int i, double a){
        price[i] = a;
    }
    public int getOneRequired(int i){
        return resourcesRequired[i];
    }
    public boolean getOneResourceCraftable(int i){
        return resourceCraftable[i];
    }
    public String getName(){
        return name;
    }
    public char getIdentifier(){
        return identifier;
    }
    public String getDependancy(){
        return dependancy;
    }
    public String getConfig(){
        return config;
    }
}
