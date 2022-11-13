package GameObjects;

import Utils.Globals;
import Utils.Utils;

abstract public class Buildable{
    private String name;
    private char identifier;
    private char[] resourcesRequired;
    private double[] price;
    private boolean[] resourceCraftable;
    
    public Buildable(){
        name = "";
        identifier = Globals.DEFAULT_IDENTIFIER;
    }
    public boolean checkIfBuildable(){
        boolean canBuild = true;
        for(int i = 0; i < price.length; i++){
            if(!resourceCraftable[i]){
                if(Globals.ALL_RESOURCES[getOneRequired(i)].getAmount() < price[i]){
                    canBuild = false;
                }
            }
            else if(resourceCraftable[i]){
                if(Globals.ALL_CRAFTABLE_RESOURCES[getOneRequired(i)].getAmount() < price[i]){
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
        if(resourceCraftable[i]){
            return Utils.findCraftableResourceFromIdentifier(resourcesRequired[i]);
        }
        return Utils.findResourceFromIdentifier(resourcesRequired[i]);
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
}
