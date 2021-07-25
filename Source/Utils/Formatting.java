package Utils;

public class Formatting {
    public static String padWithSpaces(String str, int length){
        StringBuffer s = new StringBuffer();
        
        s.append(str);
        
        for(int i = 0; i < (length - str.length()) * 3; i++){
            s.append(" ");
        }
        return s + "";
    }
    public static String indent(int length){
        StringBuffer s = new StringBuffer();
        
        s.append("<font color=white>");
        for(int i = 0; i < length; i++){
            s.append("_");
        }
        s.append("</font>");
        return s + "";
    }
    public static String middlePadding(String start, String end, int length){
        StringBuffer s = new StringBuffer();
        
        s.append(start);
        s.append("<font color=white>");
        
        for(int i = 0; i < length - start.length() - end.length(); i++){
            s.append("_");
        }
        
        s.append("</font><font color=black>");
        s.append(end);
        
        return s + "</font>";
    }
    public static String coloredMiddlePadding(String start, String end, int length, String color){
        StringBuffer s = new StringBuffer();
        
        s.append(color + start);
        
        s.append("</font><font color=white>");
        
        for(int i = 0; i < length - start.length() - end.length(); i++){
            s.append("_");
        }
        s.append("</font>");
        s.append(/*"<div align=right>" +*/ color + end);
        s.append(/*"</div>*/"</font>");
        
        return s + "";
    }
    public static String sizeToolTip(String tooltip, int length){
        StringBuffer s = new StringBuffer();
        
        if(tooltip.length() > length){
            while(tooltip.length() > length){
                if(findLastSpace(tooltip.substring(0, length + 1)) == length || 
                        findLastSpace(tooltip.substring(0, length + 1)) == length + 1){
                    s.append(tooltip.substring(0, length));
                    s.append("<br>");
                    tooltip = tooltip.substring(length);
                    
                }
                else if(findLastSpace(tooltip.substring(0, length + 1)) < length){
                    s.append(tooltip.substring(0, findLastSpace(tooltip.substring(0, length))));
                    s.append("<br>");
                    tooltip = tooltip.substring(findLastSpace(tooltip.substring(0, length)));
                }
            }
        }
        
        if(tooltip.length() < length){
            s.append(padWithSpaces(tooltip, length));
        }
        return s + "";
    }
    public static int findLastSpace(String s){
        int position = 0;
        while(s.indexOf(' ', position) != -1){
            if (s.indexOf(' ', position + 1) == -1){
                break;
            }
            position = s.indexOf(' ', position + 1);
        }
        return position;
    }
    public static String buildingTooltip(int buildingNumber){
        StringBuffer s = new StringBuffer();
        
        s.append("<html>" + sizeToolTip(Globals.ALL_BUILDINGS[buildingNumber].getToolTipText(), 50) + "<br>" + "_____________________________________________" + "<br>" + "<br>" + "Cost:");
        for(int l = 0; l < Globals.ALL_BUILDINGS[buildingNumber].getNumberOfResources(); l++){
            if(!Globals.ALL_BUILDINGS[buildingNumber].getOneResourceCraftable(l)){
                if(Globals.ALL_RESOURCES[Globals.ALL_BUILDINGS[buildingNumber].getOneRequired(l)].getAmount() < Globals.ALL_BUILDINGS[buildingNumber].getOnePrice(l)){
                    s.append("<br>" + coloredMiddlePadding(Globals.ALL_RESOURCES[Globals.ALL_BUILDINGS[buildingNumber].getOneRequired(l)].getName() 
                                + ": ", formatAmount(Globals.ALL_RESOURCES[Globals.ALL_BUILDINGS[buildingNumber].getOneRequired(l)].getAmount()) + "/" + 
                                        formatAmount(Globals.ALL_BUILDINGS[buildingNumber].getOnePrice(l)) + Utils.timeUntilCanBuild(buildingNumber, l), 45, "<font color=red>" ));
                }
                else{
                    s.append("<br>" + middlePadding(Globals.ALL_RESOURCES[Globals.ALL_BUILDINGS[buildingNumber].getOneRequired(l)].getName() 
                                + ": ", formatAmount(Globals.ALL_BUILDINGS[buildingNumber].getOnePrice(l)) + Utils.timeUntilCanBuild(buildingNumber, l), 45));
                }
            }
            else{
                if(Globals.ALL_CRAFTABLE_RESOURCES[Globals.ALL_BUILDINGS[buildingNumber].getOneRequired(l)].getAmount() >= Globals.ALL_BUILDINGS[buildingNumber].getOnePrice(l)){
                    s.append("<br>" + (middlePadding(Globals.ALL_CRAFTABLE_RESOURCES[Globals.ALL_BUILDINGS[buildingNumber].getOneRequired(l)].getName() 
                            + ": ", formatAmount(Globals.ALL_BUILDINGS[buildingNumber].getOnePrice(l)), 45)));
                }
                else if(Globals.ALL_CRAFTABLE_RESOURCES[Globals.ALL_BUILDINGS[buildingNumber].getOneRequired(l)].getAmount() < Globals.ALL_BUILDINGS[buildingNumber].getOnePrice(l)){
                    
                    s.append("<br>" + (middlePadding(Globals.ALL_CRAFTABLE_RESOURCES[Globals.ALL_BUILDINGS[buildingNumber].getOneRequired(l)].getName() 
                            + ": ", formatAmount(Globals.ALL_CRAFTABLE_RESOURCES[Globals.ALL_BUILDINGS[buildingNumber].getOneRequired(l)].getAmount()) + " / " + formatAmount(Globals.ALL_BUILDINGS[buildingNumber].getOnePrice(l)), 45)));
                    
                    for(int i = 0; i < Globals.ALL_CRAFTABLE_RESOURCES[Globals.ALL_BUILDINGS[buildingNumber].getOneRequired(l)].getNumberOfResources(); i++){
                        
                        int resourceNumber = Globals.ALL_CRAFTABLE_RESOURCES[Globals.ALL_BUILDINGS[buildingNumber].getOneRequired(l)].getOneRequired(i);
                        
                        double amountNeeded = (Globals.ALL_BUILDINGS[buildingNumber].getOnePrice(l) - Globals.ALL_CRAFTABLE_RESOURCES[Globals.ALL_BUILDINGS[buildingNumber].getOneRequired(l)].getAmount());
                        
                        amountNeeded = Math.ceil(amountNeeded / AmountCalculator.calculateCraftEffectiveness()) * Globals.ALL_CRAFTABLE_RESOURCES[Globals.ALL_BUILDINGS[buildingNumber].getOneRequired(l)].getOnePrice(i);
                        
                        if(Globals.ALL_RESOURCES[resourceNumber].getAmount() >= amountNeeded){
                            s.append("<br>" + indent(2) + middlePadding("> " + Globals.ALL_RESOURCES[resourceNumber].getName() + ":", formatAmount(amountNeeded), 42));
                        }
                        else if(Globals.ALL_RESOURCES[resourceNumber].getAmount() < amountNeeded){
                            s.append("<br>" + indent(2) + coloredMiddlePadding("> " + Globals.ALL_RESOURCES[resourceNumber].getName() + ":",
                                    Utils.timeUntilAmount(resourceNumber, amountNeeded) + " " + formatAmount(Globals.ALL_RESOURCES[resourceNumber].getAmount()) + "/" + formatAmount(amountNeeded), 42, "<font color=red>"));
                        }
                    }
                }
            }
        }
        
        s.append("<br>" + "_____________________________________________" + "<br>" + "<br>" + "Effects:" + "<br>" + "<font color=light_gray>");
        
        for(int i = 0; i < Globals.ALL_BUILDINGS[buildingNumber].getNumberOfEffects(); i++){
            s.append(Globals.ALL_BUILDINGS[buildingNumber].getOneEffect(i).getStringEffect() + "<br>");
        }
        
        s.append("</font>");
        
        if(Globals.ALL_BUILDINGS[buildingNumber].getSecondaryToolTip().length() != 0){
            s.append("<br>" + "<i>" + middlePadding(" ", Globals.ALL_BUILDINGS[buildingNumber].getSecondaryToolTip(), 45) + "</i>");       
        }            
        
        return s + "<html>";
    }
    public static String resourceName(int resourceNumber){
        StringBuffer s = new StringBuffer();
        s.append("<html>" + "<font color=" + Globals.ALL_RESOURCES[resourceNumber].getColor() + ">" + Globals.ALL_RESOURCES[resourceNumber].getName() + "</font>: " + formatAmount(Globals.ALL_RESOURCES[resourceNumber].getAmount()) +
                 " / " + formatAmount(Globals.ALL_RESOURCES[resourceNumber].getMaximum()));
        
        if(Globals.AMOUNT_PER_SECOND[resourceNumber] > 0){
            s.append(" (+" + formatAmount(Globals.AMOUNT_PER_SECOND[resourceNumber]) + Utils.checkToSmall(Globals.AMOUNT_PER_SECOND[resourceNumber]) + "/sec)");
        }
        else if(Globals.AMOUNT_PER_SECOND[resourceNumber] < 0){
            s.append(" (");
            if(Globals.AMOUNT_PER_SECOND[resourceNumber] > -0.005){
                s.append("-");
            }
            s.append(formatAmount(Globals.AMOUNT_PER_SECOND[resourceNumber]) + Utils.checkToSmall(Globals.AMOUNT_PER_SECOND[resourceNumber])+ "/sec)");
        }
        return s + "<html>";
    }
    public static String resourceToolTip(int resourceNumber){
        StringBuffer s = new StringBuffer();
        s.append(Utils.timeUntilMaxMin(resourceNumber, false) + "<br>");
        if(AmountCalculator.totalPositiveResourceProduction[resourceNumber] > 0){
            s.append("> Building Production: +" + formatAmount(AmountCalculator.totalPositiveResourceProduction[resourceNumber]) + "/sec" + "<br>");
            if(AmountCalculator.buildingBonusProduction[resourceNumber] > 0){
                s.append(indent(4) + "> Building Bonus: +" + (int)(100 * AmountCalculator.buildingBonusProduction[resourceNumber]) + "%" + "<br>");
            }
        }
        if(AmountCalculator.totalNegativeResourceProduction[resourceNumber] > 0 && AmountCalculator.bearResourceConsumption[resourceNumber] > 0){
            s.append("> Consumption: -" + formatAmount(AmountCalculator.totalNegativeResourceProduction[resourceNumber] + AmountCalculator.bearResourceConsumption[resourceNumber]) + "/sec" + "<br>");
            if(AmountCalculator.totalNegativeResourceProduction[resourceNumber] > 0){
                s.append(indent(4) + "> Building Consumption: -" + formatAmount(AmountCalculator.totalNegativeResourceProduction[resourceNumber]) + "/sec" + "<br>");
            }
            if(AmountCalculator.bearResourceConsumption[resourceNumber] > 0){
                s.append(indent(4) + "> Bear Consumption: -" + formatAmount(AmountCalculator.bearResourceConsumption[resourceNumber]) + "/sec" + "<br>");
            }
        }
        else if(AmountCalculator.totalNegativeResourceProduction[resourceNumber] > 0){
            if(AmountCalculator.totalNegativeResourceProduction[resourceNumber] > 0){
                s.append("> Building Consumption: -" + formatAmount(AmountCalculator.totalNegativeResourceProduction[resourceNumber]) + "/sec" + "<br>");
            }
        }
        else if(AmountCalculator.bearResourceConsumption[resourceNumber] > 0){
            s.append("> Bear Consumption: -" + formatAmount(AmountCalculator.bearResourceConsumption[resourceNumber]) + "/sec" + "<br>");
        }
        if(AmountCalculator.totalJobResourceProduction[resourceNumber] > 0){
            s.append("> Job Production: +" + formatAmount(AmountCalculator.totalJobResourceProduction[resourceNumber]) + "/sec" + "<br>");
            if(AmountCalculator.totalJobPercentBonus[resourceNumber] > 0){
                s.append(indent(4) + "> Job Bonus: +" + (int)(100 * AmountCalculator.totalJobPercentBonus[resourceNumber]) + "%" + "<br>");
            }
            if(AmountCalculator.calculateHappiness() > 100){
                s.append(indent(4) + "> Happiness Bonus: +" + (AmountCalculator.calculateHappiness() - 100) + "%");
            }
            else if(AmountCalculator.calculateHappiness() < 100){
                s.append(indent(4) + "> Happiness Bonus: " + (AmountCalculator.calculateHappiness() - 100) + "%");
            }
        }
        return "<html>" + s + "<html>";
    }
    public static String scienceTooltip(int scienceNumber){
        StringBuffer s = new StringBuffer();
        s.append("<html>" + sizeToolTip(Globals.ALL_SCIENCES[scienceNumber].getToolTipText(), 50) + "<br>" + "_____________________________________________" + "<br>" + "<br>" + "Cost:");
        for(int l = 0; l < Globals.ALL_SCIENCES[scienceNumber].getNumberOfResources(); l++){
            if(Globals.ALL_RESOURCES[Globals.ALL_SCIENCES[scienceNumber].getOneRequired(l)].getAmount() < Globals.ALL_SCIENCES[scienceNumber].getOnePrice(l)){
                s.append("<br>" + coloredMiddlePadding(Globals.ALL_RESOURCES[Globals.ALL_SCIENCES[scienceNumber].getOneRequired(l)].getName() + 
                     ": " , formatAmount(Globals.ALL_RESOURCES[Globals.ALL_SCIENCES[scienceNumber].getOneRequired(l)].getAmount()) +  "/" + formatAmount(Globals.ALL_SCIENCES[scienceNumber].getOnePrice(l)) + Utils.timeUntilCanResearchScience(scienceNumber, l), 50, "<font color=red>"));
            }
            else{
                s.append("<br>" + middlePadding(Globals.ALL_RESOURCES[Globals.ALL_SCIENCES[scienceNumber].getOneRequired(l)].getName() + 
                     ": ", formatAmount(Globals.ALL_SCIENCES[scienceNumber].getOnePrice(l)) + Utils.timeUntilCanResearchScience(scienceNumber, l), 50));
            }
        }
        return s + "<html>";
    }
    public static String makeJobToolTip(int jobNumber){
        StringBuffer s = new StringBuffer();
        s.append(sizeToolTip(Globals.ALL_JOBS[jobNumber].getToolTipText(), 50));
        s.append("<br>" + "_____________________________________________" + "<br>" + "<br>" + "Effects:" + "<br>" + "<font color=gray>");
        
        for(int i = 0; i < Globals.ALL_JOBS[jobNumber].getNumberOfEffects(); i++){
            s.append(Globals.ALL_JOBS[jobNumber].getOneEffect(i).getStringEffect() + "<br>");
        }
        if(Globals.ALL_JOBS[jobNumber].getIdentifier() == 'h'){
            s.append("+1 Hunting Point/sec");
        }
        
        return "<html>" + s + "<html>";
    }
    public static String magicTooltip(int magicNumber){
        StringBuffer s = new StringBuffer();
        s.append("<html>" + Globals.ALL_MAGIC[magicNumber].getToolTipText() + "<br>" + "_____________________________________________" + "<br>" + "<br>" + "Cost:");
        
        for(int l = 0; l < Globals.ALL_MAGIC[magicNumber].getNumberOfResources(); l++){
            if(Globals.ALL_RESOURCES[Globals.ALL_MAGIC[magicNumber].getOneRequired(l)].getAmount() < Globals.ALL_MAGIC[magicNumber].getOnePrice(l)){
                s.append("<br>" + coloredMiddlePadding(Globals.ALL_RESOURCES[Globals.ALL_MAGIC[magicNumber].getOneRequired(l)].getName() + 
                     ": ", formatAmount(Globals.ALL_RESOURCES[Globals.ALL_MAGIC[magicNumber].getOneRequired(l)].getAmount()) + "/" + formatAmount(Globals.ALL_MAGIC[magicNumber].getOnePrice(l)) + Utils.timeUntilCanResearchMagic(magicNumber, l), 50, "<font color=red>"));   
            }
            else{
                s.append("<br>" + middlePadding(Globals.ALL_RESOURCES[Globals.ALL_MAGIC[magicNumber].getOneRequired(l)].getName() + 
                     ": " ,formatAmount(Globals.ALL_MAGIC[magicNumber].getOnePrice(l)) + Utils.timeUntilCanResearchMagic(magicNumber, l), 50)); 
            }    
        }
        return s + "<html>";
    }
    public static String craftableResourceTooltip(int resourceNumber){
        StringBuffer s = new StringBuffer();
                
        s.append("<html>" + sizeToolTip(Globals.ALL_CRAFTABLE_RESOURCES[resourceNumber].getToolTipText(), 50) + "<br>" + "<br>" + "Cost:");
        for(int l = 0; l < Globals.ALL_CRAFTABLE_RESOURCES[resourceNumber].getNumberOfResources(); l++){
            s.append("<br>" + middlePadding(padWithSpaces(Globals.ALL_RESOURCES[Globals.ALL_CRAFTABLE_RESOURCES[resourceNumber].getOneRequired(l)].getName() + 
                     ": " + formatAmount(Globals.ALL_CRAFTABLE_RESOURCES[resourceNumber].getOnePrice(l)), 30), Utils.timeUntilCanCraftResource(resourceNumber, l), 50));
        }
        return s + "<html>";
    }
    public static String createCraftToolTip(int resourceNumber, int amount){
        StringBuffer s = new StringBuffer();
        
        s.append("Cost: " + "<br>");
        
        for(int i = 0; i < Globals.ALL_CRAFTABLE_RESOURCES[resourceNumber].getNumberOfResources(); i++){
            s.append(Globals.ALL_RESOURCES[Globals.ALL_CRAFTABLE_RESOURCES[resourceNumber].getOneRequired(i)].getName() + ": ");
            s.append(formatAmount(Globals.ALL_CRAFTABLE_RESOURCES[resourceNumber].getOnePrice(i) * amount) + "<br>");
        }
        return "<html>" + s + "<html>";
    }
    public static String formatCraftableResourceName(int resourceNumber){
        StringBuffer s = new StringBuffer();
        s.append(Globals.ALL_CRAFTABLE_RESOURCES[resourceNumber].getName() + ": " + formatAmount(Globals.ALL_CRAFTABLE_RESOURCES[resourceNumber].getAmount()));
        
        if(Globals.AMOUNT_PER_SECOND_CRAFTABLE[resourceNumber] > 0){
            s.append(" (+" + formatAmount(Globals.AMOUNT_PER_SECOND_CRAFTABLE[resourceNumber]) + Utils.checkToSmall(Globals.AMOUNT_PER_SECOND_CRAFTABLE[resourceNumber]) + ")/sec");
        }
        else if(Globals.AMOUNT_PER_SECOND_CRAFTABLE[resourceNumber] < 0){
            s.append(" (");
            if(Globals.AMOUNT_PER_SECOND_CRAFTABLE[resourceNumber] > -0.005){
                s.append("-");
            }
            s.append(formatAmount(Globals.AMOUNT_PER_SECOND_CRAFTABLE[resourceNumber]) + Utils.checkToSmall(Globals.AMOUNT_PER_SECOND_CRAFTABLE[resourceNumber])+ "/sec)");
        }
        return s + "";
    }
    public static String upgradeToolTip(int upgradeNumber){
        StringBuffer s = new StringBuffer();
        
        s.append(sizeToolTip("<html>" + Globals.ALL_UPGRADES[upgradeNumber].getToolTipText(), 50) + "<br>" + "_____________________________________________" + "<br>" + "<br>" + "Cost:");
        for(int l = 0; l < Globals.ALL_UPGRADES[upgradeNumber].getNumberOfResources(); l++){
            if(!Globals.ALL_UPGRADES[upgradeNumber].getOneResourceCraftable(l)){
                if(Globals.ALL_RESOURCES[Globals.ALL_UPGRADES[upgradeNumber].getOneRequired(l)].getAmount() < Globals.ALL_UPGRADES[upgradeNumber].getOnePrice(l)){
                    s.append("<br>" + coloredMiddlePadding(Globals.ALL_RESOURCES[Globals.ALL_UPGRADES[upgradeNumber].getOneRequired(l)].getName() 
                                + ": ", formatAmount(Globals.ALL_RESOURCES[Globals.ALL_UPGRADES[upgradeNumber].getOneRequired(l)].getAmount()) + "/" + 
                                        formatAmount(Globals.ALL_UPGRADES[upgradeNumber].getOnePrice(l)) + Utils.timeUntilCanResearchUpgrade(upgradeNumber, l), 50, "<font color=red>" ));
                }
                else{
                    s.append("<br>" + middlePadding(Globals.ALL_RESOURCES[Globals.ALL_UPGRADES[upgradeNumber].getOneRequired(l)].getName() 
                                + ": ", formatAmount(Globals.ALL_UPGRADES[upgradeNumber].getOnePrice(l)) + Utils.timeUntilCanResearchUpgrade(upgradeNumber, l), 50));
                }
            }
            else{
                s.append("<br>" + (padWithSpaces(Globals.ALL_CRAFTABLE_RESOURCES[Globals.ALL_UPGRADES[upgradeNumber].getOneRequired(l)].getName() 
                            + ": " + formatAmount(Globals.ALL_UPGRADES[upgradeNumber].getOnePrice(l)), 50)));
            }
        }
        
        s.append("<br>" + "_____________________________________________" + "<br>" + "<br>" + "Effects:" + "<br>" + "<font color=light_gray>");
        
        for(int i = 0; i < Globals.ALL_UPGRADES[upgradeNumber].getNumberOfEffects(); i++){
            System.out.println(Globals.ALL_UPGRADES[upgradeNumber].getOneEffect(i).getStringEffect());
            s.append(Globals.ALL_UPGRADES[upgradeNumber].getOneEffect(i).getStringEffect() + "<br>");
        }
        
        s.append("</font>");
        
        return s + "<html>";
    }
    public static String createMagicEffectToolTip(int effectNumber){
        StringBuffer s = new StringBuffer();
        
        s.append(sizeToolTip(Globals.ALL_MAGIC_EFFECTS[effectNumber].getToolTipText(), 50) + "<br>" );
        
        if(Globals.ALL_MAGIC_EFFECTS[effectNumber].getEnablable() && Globals.ALL_MAGIC_EFFECTS[effectNumber].getEnabled()){
            s.append("<br>" + "Click to disable" + "<br>");
        }
        else if(Globals.ALL_MAGIC_EFFECTS[effectNumber].getEnablable() && !Globals.ALL_MAGIC_EFFECTS[effectNumber].getEnabled()){
            s.append("<br>" + "Click to enable" + "<br>");
        }
        
        s.append("_____________________________________________" + "<br>" +  "<br>" + "Effects:" + "<br>");
        
        
        for(int i = 0; i < Globals.ALL_MAGIC_EFFECTS[effectNumber].getEffects().length; i++){
            s.append(Globals.ALL_MAGIC_EFFECTS[effectNumber].getOneEffect(i).getStringEffect() + "<br>");
        }
        
        return "<html>" + s + "<html>";
    }
    public static String createHappinessToolTip(){
        StringBuffer s = new StringBuffer();
        
        s.append("Happiness: " + AmountCalculator.calculateHappiness() + "%" + "<br>");
        if(AmountCalculator.bonusResourceHappiness != 0){
            s.append("> Bonus Resources: +" + Utils.round(AmountCalculator.bonusResourceHappiness,1) + "%" + "<br>");
        }
        if(AmountCalculator.overPopulationLoss != 0){
            s.append("> Over Population: " + Utils.round(AmountCalculator.overPopulationLoss,1) + "%" + "<br>");
        }
        if(AmountCalculator.magicLoss != 0){
            s.append("> Magic: " + Utils.round(AmountCalculator.magicLoss,1) + "%");
        }
        
        return "<html>" + s + "<html>";
    }
    public static String createEventHeader(){
        StringBuffer s = new StringBuffer();
        
        s.append("________________________________________________\n\n");
        
        s.append("Year " + Utils.getYear() + " - ");
        if(Utils.getSeason() == Globals.SPRING){
            s.append("Spring\n");
        }
        else if(Utils.getSeason() == Globals.SUMMER){
            s.append("Summer\n");
        }
        else if(Utils.getSeason() == Globals.FALL){
            s.append("Fall\n");
        }
        else if(Utils.getSeason() == Globals.WINTER){
            s.append("Winter\n");
        }
        
        s.append("________________________________________________\n\n");
        
        return s + "";
    }
    public static String formatAmount(double amount){
        if(amount >= 1000000000){
             amount =  Utils.round(amount / 1000000, 2);
             return amount + "Bi";
        }
        else if(amount >= 1000000){
            amount = Utils.round(amount / 1000, 2);
            return amount + "Mi";
        }
        else if(amount >= 10000){
            amount = Utils.round(amount / 1000, 2);
            return amount + "K";
        }
        else{
            return Utils.round(amount, 2) + "";
        }
    }
}
