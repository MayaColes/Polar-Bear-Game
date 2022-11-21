package Utils;

public class FormatStrings {
    // Format with:
    // 1 : sizeToolTip(building.getToolTipText(), 50)
    // 2 : buildableToolTip(building)
    // 3 : building.getOneEffect(i).getStringEffect() + "<br>" for all effects
    // 4 : "<br>" + "<i>" + middlePadding(" ", building.getSecondaryToolTip(), 45) + "</i>" if there is a secondary buliding tooltip
    public static String buildingToolTipTemplate =
     "<html>"                                                + 
     "   %s <br>"                                            +
     "   _____________________________________________ <br>" +
     "   <br>"                                               +
     "   Cost:"                                              +
     "   %s <br>"                                            +
     "   _____________________________________________ <br>" +
     "   <br>"                                               +
     "   Effects: <br>"                                      +
     "   <font color=light_gray>"                            +
     "       %s"                                             + 
     "   </font>"                                            +
     "   %s"                                                 +
     "</html>";

    // Format with:
    // 1 : Globals.ALL_RESOURCES[resourceNumber].getColor()
    // 2 : Globals.ALL_RESOURCES[resourceNumber].getName()
    // 3 : formatAmount(Globals.ALL_RESOURCES[resourceNumber].getAmount()
    // 4 : formatAmount(Globals.ALL_RESOURCES[resourceNumber].getMaximum())
    // 5 : (+/- amount per second)
     public static String resourceNamesTemplate = 
     "<html>                                               " +
     "  <font color=%s>                                    " +
     "    %s                                               " +
     "  </font>:                                           " +
     "  %s / %s                                            " +
     "  %s                                                 " +
     "</html>                                              " ;
}