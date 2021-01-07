package GameObjects;
import Utils.Globals;
import Utils.Utils;
import Utils.AmountCalculator;
import incrementalgame.Initialize;
import java.util.ArrayList;

public class Civilization {
    private String name;
    private String status;
    private String imageFile;
    private int buysResource;
    private double buyAmount;
    private int sellResource;
    private double defaultSellAmount;
    private double sellAmount;
    private int hostility;
    private double scienceFavour;
    private double magicFavour;
    private double warFavour;
    private double populationFavour;
    private double happinessFavour;
    private boolean revealed;
    private double raceStanding;
    private int tradeTime;
    private String description;
    private ArrayList<Integer> trades;
    private ArrayList<Integer> gifts;
    
    public Civilization(){
        name = "";
        status = "";
        buysResource = 0;
        sellResource = 0;
        hostility = Globals.NEUTRAL;
        revealed = false;
        raceStanding  = 0;
        tradeTime = 0;
        trades = new ArrayList<Integer>(0);
        gifts = new ArrayList<Integer>(0);
        description = "";
        imageFile = "placeholder.jpg";
    }
    public void initializeCivilization(double standing, boolean r){
        String s = Initialize.buildFromConfig();
        
        raceStanding = standing;
        revealed = r;
        
        hostility = Integer.parseInt("" + s.charAt(0));
        
        name = s.substring(1, s.indexOf(Globals.END_OF_NAME_MARKER));
        
        imageFile = s.substring(s.indexOf(Globals.END_OF_NAME_MARKER) + 1, s.indexOf(Globals.END_OF_FILENAME_MARKER));
        
        tradeTime = Integer.parseInt(s.substring(s.indexOf(Globals.END_OF_FILENAME_MARKER) + 1, s.indexOf(Globals.END_OF_TIME_MARKER)));
        
        buysResource = Integer.parseInt(s.substring(s.indexOf(Globals.END_OF_TIME_MARKER) + 1, s.indexOf(Globals.END_OF_RESOURCE_MARKER)));
        buyAmount = Integer.parseInt(s.substring(s.indexOf(Globals.END_OF_RESOURCE_MARKER) + 1, s.indexOf(Globals.END_OF_COST_MARKER)));
        s = s.substring(s.indexOf(Globals.END_OF_COST_MARKER) + 1);
        
        sellResource = Integer.parseInt(s.substring(s.indexOf(Globals.END_OF_NAME_MARKER) + 1, s.indexOf(Globals.END_OF_RESOURCE_MARKER)));
        defaultSellAmount = Integer.parseInt(s.substring(s.indexOf(Globals.END_OF_RESOURCE_MARKER) + 1, s.indexOf(Globals.END_OF_COST_MARKER)));
        
        s = s.substring(s.indexOf(Globals.END_OF_COST_MARKER) + 1);
        
        scienceFavour = Double.parseDouble(s.substring(0, s.indexOf(Globals.END_OF_FAVOUR_MARKER))) / 100;
        s = s.substring(s.indexOf(Globals.END_OF_FAVOUR_MARKER) + 1);
        
        magicFavour = Double.parseDouble(s.substring(0, s.indexOf(Globals.END_OF_FAVOUR_MARKER))) / 100;
        s = s.substring(s.indexOf(Globals.END_OF_FAVOUR_MARKER) + 1);
        
        warFavour = Double.parseDouble(s.substring(0, s.indexOf(Globals.END_OF_FAVOUR_MARKER))) / 100;
        s = s.substring(s.indexOf(Globals.END_OF_FAVOUR_MARKER) + 1);
        
        populationFavour = Double.parseDouble(s.substring(0, s.indexOf(Globals.END_OF_FAVOUR_MARKER))) / 100;
        s = s.substring(s.indexOf(Globals.END_OF_FAVOUR_MARKER) + 1);
        
        happinessFavour = Double.parseDouble(s.substring(0, s.indexOf(Globals.END_OF_FAVOUR_MARKER))) / 100;
        s = s.substring(s.indexOf(Globals.END_OF_FAVOUR_MARKER) + 1);
        
        description = s.substring(0, s.indexOf(Globals.END_OF_RECORD_MARKER));
        
        checkStatus();
    }
    public void startTrade(){
        if(Globals.ALL_RESOURCES[buysResource].getAmount() >= buyAmount && Globals.ALL_RESOURCES[Utils.findResourceFromIdentifier('g')].getAmount() >= 10 && AmountCalculator.calculateFreeTraders() > 0){
            Globals.ALL_RESOURCES[buysResource].changeAmount(-buyAmount);
            Globals.ALL_RESOURCES[Utils.findResourceFromIdentifier('g')].changeAmount(-10);
            trades.add((Integer)tradeTime);
            Globals.GAME.writeToEvent("You have sent out a trader! -10 gold. -" + Utils.round(buyAmount, 2) + " " + Globals.ALL_RESOURCES[buysResource].getName());
        }
    }
    public void finishTrade(){
        Globals.ALL_RESOURCES[sellResource].changeAmount(sellAmount);
        changeRaceStanding(5);
        Globals.GAME.writeToEvent("After visiting the " + name + ", your trader has returned with " + Utils.round(sellAmount, 2) + " " + Globals.ALL_RESOURCES[sellResource].getName() + ".\n\n");
    }
    public void updateTradeTimes(){
        for(int i = 0; i < trades.size(); i++){
            trades.set(i, trades.get(i) - 1);
            if(trades.get(i) == 0){
                finishTrade();
                trades.remove(i);
            }
        }
    }
    public void startGift(){
        if(Globals.ALL_RESOURCES[buysResource].getAmount() >= buyAmount && AmountCalculator.calculateFreeTraders() > 0){
            gifts.add((Integer)tradeTime);
        }
    }
    public void finishGift(){
        Globals.ALL_RESOURCES[buysResource].changeAmount(-buyAmount);
        raceStanding = raceStanding + 100;
    }
    public void updateGiftTimes(){
        for(int i = 0; i < gifts.size(); i++){
            gifts.set(i, gifts.get(i) - 1);
            if(gifts.get(i) == 0){
                finishGift();
                gifts.remove(i);
            }
        }
    }
    public void changeRaceStanding(double amount){
        if(amount > 0 && raceStanding != Globals.WAR){
            raceStanding = raceStanding + amount;
        }
        else if(amount < 0){
            if(raceStanding + amount < Globals.WAR){
                raceStanding = Globals.WAR;
            }
            else if(raceStanding > Globals.NO_STANDING && raceStanding + amount >= Globals.NO_STANDING){
                raceStanding = raceStanding + amount;
            }
            else if(raceStanding > Globals.NO_STANDING && raceStanding + amount < Globals.NO_STANDING){
                raceStanding = Globals.NO_STANDING;
            }
        }
        checkStatus();
    }
    public void checkStatus(){
        if(raceStanding > 500 && raceStanding < 1500){
            status = "Neutral";
            sellAmount = defaultSellAmount;
        }
        else if(raceStanding > 1500 && raceStanding < Globals.ALLIES){
            status = "Friendly";
            sellAmount = defaultSellAmount * 1.25;
        }
        else if(raceStanding >= Globals.ALLIES){
            status = "Allies";
            sellAmount = defaultSellAmount * 1.5;
        }
        else if(raceStanding < 500 && raceStanding > Globals.WAR){
            status = "Enemies";
            sellAmount = defaultSellAmount * 0.5;
        }
        else if (raceStanding == Globals.WAR){
            status = "At War!";
            sellAmount = 0;
        }
    }
    public double calculateFavourMultiplier(int index){
        if(index == 0){
            if(scienceFavour >= Globals.INDIFFERENT){
                return (scienceFavour - 0.5) * 2;
            }
            else if(scienceFavour < Globals.INDIFFERENT){
                return (scienceFavour) * 2;
            }
        }
        else if(index == 1){
            if(magicFavour >= Globals.INDIFFERENT){
                return (magicFavour - 0.5) * 2;
            }
            else if(magicFavour < Globals.INDIFFERENT){
                return (magicFavour) * 2;
            }
        }
        else if(index == 2){
            if(warFavour >= Globals.INDIFFERENT){
                return (warFavour - 0.5) * 2;
            }
            else if(warFavour < Globals.INDIFFERENT){
                return (warFavour) * 2;
            }
        }
        else if(index == 3){
            if(populationFavour >= Globals.INDIFFERENT){
                return (populationFavour - 0.5) * 2;
            }
            else if(populationFavour < Globals.INDIFFERENT){
                return (populationFavour) * 2;
            }
        }
        else{
            if(happinessFavour >= Globals.INDIFFERENT){
                return (happinessFavour - 0.5) * 2;
            }
            else if(happinessFavour < Globals.INDIFFERENT){
                return (happinessFavour) * 2;
            }
        }
        
        return 0;
    }
    public String getScienceFavourString(){
        return makeFavourString(scienceFavour) + "Science";
    }
    public String getMagicFavourString(){
        return makeFavourString(magicFavour) + "Magic";
    }
    public String getPopulationFavourString(){
        return makeFavourString(populationFavour) + "Large Populations";
    }
    public String getWarFavourString(){
        return makeFavourString(warFavour) + "Military Strength";
    }
    public String getHappinessFavourString(){
        return makeFavourString(happinessFavour) + "High Happiness";
    }
    private String makeFavourString(double favour){
        if(favour <= Globals.LIKES && favour > Globals.INDIFFERENT){
            return "Likes ";
        }
        else if(favour > Globals.LIKES && favour <= Globals.LOVES){
            return "Loves ";
        }
        else if(favour >= Globals.DISLIKES && favour < Globals.INDIFFERENT){
            return "Dislikes ";
        }
        else if (favour < Globals.DISLIKES && favour >= Globals.HATES){
            return "Hates ";
        }
        return "Indifferent to ";
    }
    public String getName(){
        return name;
    }
    public boolean getRevealed(){
        return revealed;
    }
    public int getBuysResource(){
        return buysResource;
    }
    public int getSellsResource(){
        return sellResource;
    }
    public double getBuyAmount(){
        return buyAmount;
    }
    public double getSellAmount(){
        return sellAmount;
    }
    public double getRaceStanding(){
        return raceStanding;
    }
    public int getHostility(){
        return hostility;
    }
    public int getNumberTrading(){
        return trades.size();
    }
    public String getStatus(){
        return status;
    }
    public double getScienceFavour(){
        return scienceFavour;
    }
    public double getMagicFavor(){
        return magicFavour;
    }
    public double getWarFavour(){
        return warFavour;
    }
    public double getPopulationFavour(){
        return populationFavour;
    }
    public double getHappinessFavour(){
        return happinessFavour;
    }
    public int getTradeTime(){
        return tradeTime;
    }
    public String getDescription(){
        return description;
    }
    public String getImageFIle(){
        return imageFile;
    }
    public void setRevealed(boolean r){
        revealed = r;
    }
}
