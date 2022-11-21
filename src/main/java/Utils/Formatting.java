package Utils;
import GameObjects.*;

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
    private static String buildableToolTip(Buildable buildable){
        StringBuffer s = new StringBuffer();
        s.append("<table style='width: 210px'>");

        for(int l = 0; l < buildable.getNumberOfResources(); l++) {
            if (!buildable.getOneResourceCraftable(l)) {
                Resource resource = Globals.ALL_RESOURCES[buildable.getOneRequired(l)];

                if (resource.getAmount() < buildable.getOnePrice(l)) {
                    s.append("<tr style='padding: 0; color: red'>" + "<td style='padding: 0'>" + resource.getName()+ ": " + "</td>" +
                            "<td style='text-align: right; padding: 0'>" + formatAmount(resource.getAmount()) + "/" +
                            formatAmount(buildable.getOnePrice(l)) +
                            Utils.timeUntilCanBuildBuildable(buildable, l) + "</td>" + "</tr>");
                } else {
                    s.append("<tr style='padding: 0'>" + "<td style='padding: 0'>" + resource.getName() + ": " + "</td>" +
                            "<td style='text-align: right; padding: 0'>" + formatAmount(buildable.getOnePrice(l)) +
                            Utils.timeUntilCanBuildBuildable(buildable, l) + "</td>" + "</tr>");
                }
            } else {
                CraftableResource resource = Globals.ALL_CRAFTABLE_RESOURCES[buildable.getOneRequired(l)];

                if (resource.getAmount() >= buildable.getOnePrice(l)) {
                    s.append("<tr style='padding: 0'>" + "<td style='padding: 0'>" + resource.getName()+ ": " + "</td>" + 
                            "<td style='text-align: right; padding: 0'>"  + formatAmount(buildable.getOnePrice(l)) + "</td>" + "</tr>");
                } else if (resource.getAmount() < buildable.getOnePrice(l)) {
                    if(resource.checkIfBuildable((int)Math.ceil((buildable.getOnePrice(l) - resource.getAmount()) / AmountCalculator.calculateCraftEffectiveness()))){
                        s.append("<tr style='padding: 0'>" + "<td style='padding: 0'>" + resource.getName() + ": " + "</td>" +
                            "<td style='text-align: right; padding: 0'>" + formatAmount(resource.getAmount()) + " / " +
                            formatAmount(buildable.getOnePrice(l)) + "</td>" + "</tr>");
                    }
                    else{
                        s.append("<tr style='padding: 0; color: red'>" + "<td style='padding: 0'>" + resource.getName() + ": " + "</td>" +
                            "<td style='text-align: right; padding: 0'>" + formatAmount(resource.getAmount()) + " / " +
                            formatAmount(buildable.getOnePrice(l)) + "</td>" + "</tr>");
                    }

                    for (int i = 0; i < resource.getNumberOfResources(); i++) {

                        int resourceNumber = resource.getOneRequired(i);
                        double amountNeeded = (buildable.getOnePrice(l) - resource.getAmount());

                        amountNeeded = Math.ceil(amountNeeded / AmountCalculator.calculateCraftEffectiveness()) * resource.getOnePrice(i);

                        if (Globals.ALL_RESOURCES[resourceNumber].getAmount() >= amountNeeded) {
                            s.append("<tr style='padding: 0'>"  + "<td style='padding: 0'>" + indent(2) + "> " + Globals.ALL_RESOURCES[resourceNumber].getName() + ":" + "</td>" +
                                     "<td style='text-align: right; padding: 0'>" + formatAmount(amountNeeded) + "</td>" + "</tr>");
                        } else if (Globals.ALL_RESOURCES[resourceNumber].getAmount() < amountNeeded) {
                            s.append("<tr style='padding: 0; color: red'>" + "<td style='padding: 0'>" + indent(2) + "> " + Globals.ALL_RESOURCES[resourceNumber].getName() + ":" + "</td>" +
                                    "<td style='text-align: right; padding: 0'>" + Utils.timeUntilAmount(resourceNumber, amountNeeded) + " " +
                                    formatAmount(Globals.ALL_RESOURCES[resourceNumber].getAmount()) + "/" + 
                                    formatAmount(amountNeeded) + "</td>" + "</tr>");
                        }
                    }
                }
            }
        }

        s.append("</table>");

        return s.toString();
    }
    public static String buildingTooltip(Building building){
        String effects = "";
        String secondaryToolTip = "e";
        
        for(int i = 0; i < building.getNumberOfEffects(); i++){
            effects += building.getOneEffect(i).getStringEffect() + "<br>";
        }
        
        if(building.getSecondaryToolTip().length() != 0){
            secondaryToolTip = "<br>" + "<i>" + middlePadding(" ", building.getSecondaryToolTip(), 45) + "</i>";
        }            
        return FormatStrings.buildingToolTipTemplate.formatted(
            sizeToolTip(building.getToolTipText(), 50),
            buildableToolTip(building),
            effects,
            secondaryToolTip);
    }
    public static String resourceName(int resourceNumber){
        String amountPerSecond = "";
        
        if(Globals.AMOUNT_PER_SECOND[resourceNumber] > 0){
            amountPerSecond = " (+" + formatAmount(Globals.AMOUNT_PER_SECOND[resourceNumber]) + Utils.checkToSmall(Globals.AMOUNT_PER_SECOND[resourceNumber]) + "/sec)";
        }
        else if(Globals.AMOUNT_PER_SECOND[resourceNumber] < 0){
            amountPerSecond = " (";
            if(Utils.checkToSmall(Globals.AMOUNT_PER_SECOND[resourceNumber]) != ""){
                amountPerSecond += "-";
            }
            amountPerSecond += formatAmount(Globals.AMOUNT_PER_SECOND[resourceNumber]) + Utils.checkToSmall(Globals.AMOUNT_PER_SECOND[resourceNumber])+ "/sec)";
        }
        return FormatStrings.resourceNamesTemplate.formatted(
            Globals.ALL_RESOURCES[resourceNumber].getColor(),
            Globals.ALL_RESOURCES[resourceNumber].getName(),
            formatAmount(Globals.ALL_RESOURCES[resourceNumber].getAmount()),
            formatAmount(Globals.ALL_RESOURCES[resourceNumber].getMaximum()),
            amountPerSecond);
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
    public static String researchTooltip(Research research){
        StringBuffer s = new StringBuffer();

        s.append("<html>" + sizeToolTip(research.getToolTipText(), 50) + "<br>" + "_____________________________________________" + "<br>" + "<br>" + "Cost:");

        s.append(buildableToolTip(research));

        return s + "<html>";
    }
    public static String makeJobToolTip(int jobNumber){
        StringBuffer s = new StringBuffer();
        Job job = Globals.ALL_JOBS[jobNumber];

        s.append(sizeToolTip(job.getToolTipText(), 50));
        s.append("<br>" + "_____________________________________________" +
                "<br>" + "<br>" + "Effects:" + "<br>" + "<font color=gray>");
        
        for(int i = 0; i < job.getNumberOfEffects(); i++){
            s.append(job.getOneEffect(i).getStringEffect() + "<br>");
        }
        if(job.getIdentifier() == 'h'){
            s.append("+1 Hunting Point/sec");
        }
        
        return "<html>" + s + "<html>";
    }
    public static String craftableResourceTooltip(int resourceNumber){
        StringBuffer s = new StringBuffer();
        CraftableResource craftableResource = Globals.ALL_CRAFTABLE_RESOURCES[resourceNumber];
                
        s.append("<html>" + sizeToolTip(craftableResource.getToolTipText(), 50) + "<br>" + "<br>" + "Cost:");

        s.append(buildableToolTip(craftableResource));

        return s + "<html>";
    }
    public static String createCraftToolTip(int resourceNumber, int amount){
        StringBuffer s = new StringBuffer();
        CraftableResource craftableResource = Globals.ALL_CRAFTABLE_RESOURCES[resourceNumber];
        
        s.append("Cost: " + "<br>");
        
        for(int i = 0; i < craftableResource.getNumberOfResources(); i++){
            Resource resource = Globals.ALL_RESOURCES[craftableResource.getOneRequired(i)];

            s.append(resource.getName() + ": ");
            s.append(formatAmount(craftableResource.getOnePrice(i) * amount) + "<br>");
        }
        return "<html>" + s + "<html>";
    }
    public static String formatCraftableResourceName(int resourceNumber){
        StringBuffer s = new StringBuffer();
        CraftableResource craftableResource = Globals.ALL_CRAFTABLE_RESOURCES[resourceNumber];

        s.append(craftableResource.getName() + ": " + formatAmount(craftableResource.getAmount()));
        
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
        Upgrade upgrade = Globals.ALL_UPGRADES[upgradeNumber];
        
        s.append(sizeToolTip("<html>" + upgrade.getToolTipText(), 50) +
                "<br>" + "_____________________________________________" + "<br>" + "<br>" + "Cost:");

        s.append(buildableToolTip(upgrade));
        
        s.append("<br>" + "_____________________________________________" + "<br>" + "<br>" + "Effects:" + "<br>" + "<font color=light_gray>");
        
        for(int i = 0; i < upgrade.getNumberOfEffects(); i++){
            s.append(upgrade.getOneEffect(i).getStringEffect() + "<br>");
        }
        
        s.append("</font>");
        
        return s + "<html>";
    }
    public static String createMagicEffectToolTip(int effectNumber){
        StringBuffer s = new StringBuffer();
        MagicEffect magicEffect = Globals.ALL_MAGIC_EFFECTS[effectNumber];
        
        s.append(sizeToolTip(magicEffect.getToolTipText(), 50) + "<br>" );
        
        if(magicEffect.getEnablable() && magicEffect.getEnabled()){
            s.append("<br>" + "Click to disable" + "<br>");
        }
        else if(magicEffect.getEnablable() && !magicEffect.getEnabled()){
            s.append("<br>" + "Click to enable" + "<br>");
        }
        
        s.append("_____________________________________________" + "<br>" +  "<br>" + "Effects:" + "<br>");
        
        
        for(int i = 0; i < magicEffect.getEffects().length; i++){
            s.append(magicEffect.getOneEffect(i).getStringEffect() + "<br>");
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
