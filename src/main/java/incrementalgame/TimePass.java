package incrementalgame;
import Utils.Utils;
import Utils.Globals;
import Utils.AmountCalculator;
import java.util.*;

public class TimePass {
    private static TimerTask task = null;
    private static Timer timer = null;
    private static boolean dayOver = false;
    
    public static void gameTick(){
        
       timer = new Timer();
       task = new TimerTask(){
           @Override
            public void run() {
                
//IncrementalGame.testing();

                if(!Globals.PAUSED){
                    AmountCalculator.calculateProductionPerTurn();
                    AmountCalculator.calculateHuntingPoints();
                    Utils.checkBears();
                    Utils.checkCivilizationVisability();
                    Utils.updateJobEffects();
                    for(int i = 0; i < Globals.NUMBER_OF_RESOURCES; i++){
                        Globals.ALL_RESOURCES[i].changeAmount(Globals.AMOUNT_PER_SECOND[i]);
                    }
                    for(int i = 0; i < Globals.NUMBER_OF_CRAFTABLE_RESOURCES; i++){
                        Globals.ALL_CRAFTABLE_RESOURCES[i].changeAmount(Globals.AMOUNT_PER_SECOND_CRAFTABLE[i]);
                    }
                
                    Globals.GAME.refreshResources();
                
                    if(dayOver){
                        Globals.TIME_IN_DAYS += Globals.DAY_LENGTH;
                        if(Globals.GAME_RUNNING == false){
                            this.cancel();
                        }
                        Utils.checkEvents();
                        Utils.checkNegativeCivilizationEffects();
                        Utils.updateRaceStandings();
                        Globals.GAME.refreshTime();
                        Utils.updateTradeTimes();
                    }
                    dayOver = !dayOver;
                    Utils.checkTabVisibility();
                    if(Globals.GAME.getWhichScreen() == Globals.BUILDING_SCREEN){
                        Globals.GAME.refreshBuildingToolTips();
                    }
                    else if(Globals.GAME.getWhichScreen() == Globals.VILLAGE_SCREEN){
                        Globals.GAME.refreshUpgradeToolTips();
                    }
                    else if(Globals.GAME.getWhichScreen() == Globals.SCIENCE_SCREEN){
                        Globals.GAME.refreshScienceToolTips();
                    }
                    else if(Globals.GAME.getWhichScreen() == Globals.MAGIC_SCREEN){
                        Globals.GAME.refreshMagicToolTips();
                    }
                    else if(Globals.GAME.getWhichScreen() == Globals.CRAFTING_SCREEN){
                        Globals.GAME.refreshCraftingToolTips();
                    }
                    else if(Globals.GAME.getWhichScreen() == Globals.TRADE_SCREEN){
                        Globals.GAME.refreshTradeLabels();
                    }
                    Globals.GAME.refreshHappiness();
                }
            }
       };
       timer.schedule(task, 1000, 1000);
    }
}
