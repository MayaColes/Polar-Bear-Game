package Windows;
import Utils.*;
import incrementalgame.Initialize;
import FileWork.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class GameWindow implements ActionListener, MouseMotionListener{
    private JFrame frame = null;
    private JPanel resources = null;
    private JPanel buildingArea = null;
    private JPanel events = null;
    private JPanel buttonArea = null;
    private JPanel topOptions = null;
    private RoundedPanel[] civilizations = null;
    private RoundedPanel civInfo = null;
    private JButton[] buildings = null;
    private JButton[] sciences = null;
    private JButton[] increaseWorking = null;
    private JButton[] decreaseWorking = null;
    private JButton[] increaseBuilding = null;
    private JButton[] decreaseBuilding = null;
    private JButton[] jobs = null;
    private JButton[] magics = null;
    private JButton[] magicEffects = null;
    private JButton[] craftResource = null;
    private JButton[] craftOne = null;
    private JButton[] craftFive = null;
    private JButton[] craftTen = null;
    private JButton[] craftAll = null;
    private JButton[] upgrades = null;
    private JButton[] trade = null;
    private JButton[] gift = null;
    private JButton[] moreInformation = null;
    private JLabel[] resourceLabel = null;
    private JLabel[] craftableResourceLabel = null;
    private JLabel[] civNames = null;
    private JLabel[] civBuys = null;
    private JLabel[] civSells = null;
    private JLabel[] civTradeTime = null;
    private JLabel[] scienceFavour = null;
    private JLabel[] magicFavour = null;
    private JLabel[] populationFavour = null;
    private JLabel[] happinessFavour = null;
    private JLabel[] warFavour = null;
    private JTextArea[] civDescription = null;
    private BufferedImage[] civImage = null;
    private GridBagConstraints c = null;
    private GridBagConstraints gbc = null;
    private GridBagConstraints gbc2 = null;
    private GridBagConstraints civConstraints = null;
    private JButton scienceSwitch = null;
    private JButton buildingsSwitch = null;
    private JButton village = null;
    private JButton magicSwitch = null;
    private JButton craftingSwitch = null;
    private JButton upgradeSwitch = null;
    private JButton tradeSwitch = null;
    private JButton magicEffectSwitch = null;
    private JButton craftingEfficiency = null;
    private JButton quit = null;
    private JButton clear = null;
    private JButton scavenge = null;
    private JButton sendHunters = null;
    private JButton save = null;
    private JButton pause = null;
    private JButton freeBears = null;
    private JButton back = null;
    private JButton sellBuilding = null;
    private JLabel warning = null;
    private JTextArea eventText = null;
    private JTextArea time = null;
    private JTextArea happiness = null;
    private JScrollPane eventScroll = null;
    private JScrollPane buildingAreaScroll = null;
    private JOptionPane firstTime = null;
    private Font monospaced = null;
    private BufferedImage polarbear = null;
    private boolean upgradesVisible = false;
    private boolean magicEffectsVisible = false;
    private boolean sellingBuilding = false;
    private int whichScreen = Globals.BUILDING_SCREEN;
    private int textPos = 0;
    
    public GameWindow(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Globals.WIDTH_OF_SCREEN = (int)screenSize.getWidth();
        Globals.HEIGHT_OF_SCREEN = (int)screenSize.getHeight()-100;
        
        frame = new JFrame("Polar Bear Game");
        frame.setResizable(false);
        frame.setBackground(Color.WHITE);
        
        Container contentPane = frame.getContentPane();
        GridBagLayout contentPaneLayout = new GridBagLayout();
        contentPane.setLayout(contentPaneLayout);
        
        gbc = new GridBagConstraints();
        
        resources = new JPanel();
        buildingArea = new JPanel();
        events = new JPanel();
        buttonArea = new JPanel();
        topOptions = new JPanel();
        
        buildingAreaScroll = new JScrollPane(buildingArea);
        buildingAreaScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        resources.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        buildingArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        events.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        buttonArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        topOptions.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        resources.setBackground(Color.white);
        buildingArea.setBackground(Color.DARK_GRAY);
        events.setBackground(Color.white);
        buttonArea.setBackground(Color.white);
        topOptions.setBackground(Color.white);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        gbc.gridwidth = 3;
        frame.add(topOptions, gbc);
        
        gbc.gridwidth = 1;
        gbc.gridheight = 23;
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(resources, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        frame.add(buttonArea, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridheight = 22;
        frame.add(buildingArea, gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridheight = 23;
        frame.add(events, gbc);
        
        resources.setPreferredSize(new Dimension((int)(Globals.WIDTH_OF_SCREEN/7) * 2, (int)(Globals.HEIGHT_OF_SCREEN/24) * 23));
        buildingArea.setPreferredSize(new Dimension((int)(Globals.WIDTH_OF_SCREEN/7) * 3, (int)(Globals.HEIGHT_OF_SCREEN/24) * 22));
        events.setPreferredSize(new Dimension((int)(Globals.WIDTH_OF_SCREEN) - ((int)(Globals.WIDTH_OF_SCREEN/7) * 3) - ((Globals.WIDTH_OF_SCREEN/7) * 2), (int)(Globals.HEIGHT_OF_SCREEN/24) * 23));
        buttonArea.setPreferredSize(new Dimension((int)(Globals.WIDTH_OF_SCREEN/7) * 3,(int)(Globals.HEIGHT_OF_SCREEN/24)));
        topOptions.setPreferredSize(new Dimension((int)(Globals.WIDTH_OF_SCREEN), (int)(Globals.HEIGHT_OF_SCREEN/24)));
        
        monospaced = new Font("no", Font.PLAIN, 13);
        
        gbc = new GridBagConstraints();
        buttonArea.setLayout(new GridBagLayout());
        gbc.weighty = 1;
        
        scienceSwitch = new JButton("Science");
        scienceSwitch.setFont(monospaced);
        scienceSwitch.addActionListener(this);
        scienceSwitch.setVisible(false);
        scienceSwitch.setEnabled(false);
        
        buildingsSwitch = new JButton("Buildings");
        buildingsSwitch.setFont(monospaced);
        buildingsSwitch.addActionListener(this);
        
        village = new JButton("Village");
        village.setFont(monospaced);
        village.addActionListener(this);
        village.setVisible(false);
        village.setEnabled(false);
        
        magicSwitch = new JButton("Magic");
        magicSwitch.setFont(monospaced);
        magicSwitch.addActionListener(this);
        magicSwitch.setVisible(false);
        magicSwitch.setEnabled(false);
        
        craftingSwitch = new JButton("Craft");
        craftingSwitch.setFont(monospaced);
        craftingSwitch.addActionListener(this);
        craftingSwitch.setVisible(false);
        craftingSwitch.setEnabled(false);
        
        tradeSwitch = new JButton("Trade");
        tradeSwitch.addActionListener(this);
        tradeSwitch.setVisible(false);
        tradeSwitch.setEnabled(false);
        
        buttonArea.add(buildingsSwitch,gbc);
        buttonArea.add(village, gbc);
        buttonArea.add(scienceSwitch, gbc);
        buttonArea.add(magicSwitch, gbc);
        buttonArea.add(craftingSwitch, gbc);
        buttonArea.add(tradeSwitch,gbc);
        
        buildings = new JButton[Globals.NUMBER_OF_BUILDINGS];
        increaseBuilding = new JButton[Globals.NUMBER_OF_BUILDINGS];
        decreaseBuilding = new JButton[Globals.NUMBER_OF_BUILDINGS];
        sciences = new JButton[Globals.NUMBER_OF_TECHNOLOGIES];
        increaseWorking = new JButton[Globals.NUMBER_OF_JOBS];
        decreaseWorking = new JButton[Globals.NUMBER_OF_JOBS];
        jobs = new JButton[Globals.NUMBER_OF_JOBS];
        magics = new JButton[Globals.NUMBER_OF_MAGICS];
        magicEffects = new JButton[Globals.NUMBER_OF_MAGIC_EFFECTS];
        craftResource = new JButton[Globals.NUMBER_OF_CRAFTABLE_RESOURCES];
        craftOne = new JButton[Globals.NUMBER_OF_CRAFTABLE_RESOURCES];
        craftFive = new JButton[Globals.NUMBER_OF_CRAFTABLE_RESOURCES];
        craftTen = new JButton[Globals.NUMBER_OF_CRAFTABLE_RESOURCES];
        craftAll = new JButton[Globals.NUMBER_OF_CRAFTABLE_RESOURCES];
        upgrades = new JButton[Globals.NUMBER_OF_UPGRADES];
        resourceLabel = new JLabel[Globals.NUMBER_OF_RESOURCES];
        craftableResourceLabel = new JLabel[Globals.NUMBER_OF_CRAFTABLE_RESOURCES];
        civilizations = new RoundedPanel[Globals.NUMBER_OF_CIVILIZATIONS];
        gift = new JButton[Globals.NUMBER_OF_CIVILIZATIONS];
        trade = new JButton[Globals.NUMBER_OF_CIVILIZATIONS];
        civNames = new JLabel[Globals.NUMBER_OF_CIVILIZATIONS];
        civBuys = new JLabel[Globals.NUMBER_OF_CIVILIZATIONS];
        civSells = new JLabel[Globals.NUMBER_OF_CIVILIZATIONS];
        moreInformation = new JButton[Globals.NUMBER_OF_CIVILIZATIONS];
        civTradeTime = new JLabel[Globals.NUMBER_OF_CIVILIZATIONS];
        scienceFavour = new JLabel[Globals.NUMBER_OF_CIVILIZATIONS];
        magicFavour = new JLabel[Globals.NUMBER_OF_CIVILIZATIONS];
        populationFavour = new JLabel[Globals.NUMBER_OF_CIVILIZATIONS];
        happinessFavour = new JLabel[Globals.NUMBER_OF_CIVILIZATIONS];
        warFavour = new JLabel[Globals.NUMBER_OF_CIVILIZATIONS];
        civDescription = new JTextArea[Globals.NUMBER_OF_CIVILIZATIONS];
        civImage = new BufferedImage[Globals.NUMBER_OF_CIVILIZATIONS];
        
        firstTime = new JOptionPane();
        
        ToolTipManager.sharedInstance().setInitialDelay(0);
        ToolTipManager.sharedInstance().setDismissDelay(Integer.MAX_VALUE);
        
        UIManager.put("ToolTip.foreground", Color.black);
        UIManager.put("ToolTip.background", Color.white);
        UIManager.put("ToolTip.border", BorderFactory.createLineBorder(Color.black));
        //UIManager.put("ToolTip.font", );
        
        scavenge = new JButton("Scavenge"){
                    public Point getToolTipLocation(MouseEvent event) {
                        return new Point(getWidth(), getHeight() / 2);
                    }
                 };
        scavenge.setFont(monospaced);
        scavenge.setToolTipText("Pick up something off the ground");
        scavenge.addActionListener(this);
        
        sellBuilding = new JButton("Sell Building (Disabled)"){
                    public Point getToolTipLocation(MouseEvent event) {
                        return new Point(getWidth(), getHeight() / 2);
                    }
                 };
        sellBuilding.setToolTipText("<html>" + Formatting.sizeToolTip("Click here to enable selling buildings. Click on buildngs to sell them. To disable, click again.", 50) + "<html>");
        sellBuilding.addActionListener(this);
        
        buildingArea.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        
        c.ipady = 30;
        c.weighty = 0;
        c.weightx = 1;
        c.gridx = Globals.BUILDING_GRID_COLOMN;
        c.gridy = Globals.BUILDING_GRID_ROW;
        
        events.setLayout(new GridBagLayout());
        gbc2 = new GridBagConstraints();
        eventText = new JTextArea();
        time = new JTextArea(Utils.getDateFromDaysPassed());
        
        time.setBorder(BorderFactory.createEmptyBorder(0,10,0,0));
        time.setFont(monospaced);
        
        eventScroll = new JScrollPane(eventText);
        eventScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        eventText.setEditable(false);
        eventText.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        eventText.setLineWrap(true);
        eventText.setWrapStyleWord(true);
        eventText.setFont(monospaced);
 
        clear = new JButton("Clear");
        clear.setFont(monospaced);
        clear.addActionListener(this);
        time.setVisible(false);

        gbc2.gridy = 0;
        gbc2.gridheight = 1;
        events.add(time, gbc2);
        
        gbc2.gridwidth = 1;
        gbc2.gridx = 1;
        gbc2.gridy = 0;
        events.add(clear, gbc2);
        
        gbc2.gridx = 0;
        gbc2.gridy = 3;
        gbc2.gridwidth = 3;
        gbc2.gridheight = 21;
        gbc2.fill = GridBagConstraints.BOTH;
        events.add(eventScroll, gbc2);
        
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(4,0,4,0);
        gbc.fill = GridBagConstraints.VERTICAL;
        
        for(int i = 0; i < Globals.NUMBER_OF_JOBS; i++){
            decreaseWorking[i] = new JButton("(-)");
            decreaseWorking[i].setFont(monospaced);
            decreaseWorking[i].addActionListener(this);
            increaseWorking[i] = new JButton("(+)");
            increaseWorking[i].setFont(monospaced);
            increaseWorking[i].addActionListener(this);
            decreaseWorking[i].setPreferredSize(new Dimension(50, 50));
            increaseWorking[i].setPreferredSize(new Dimension(50, 50));
        }
        for(int i = 0; i < Globals.NUMBER_OF_BUILDINGS; i++){
            decreaseBuilding[i] = new JButton("(-)");
            decreaseBuilding[i].setFont(monospaced);
            decreaseBuilding[i].setPreferredSize(new Dimension(30,30));
            decreaseBuilding[i].addActionListener(this);
            increaseBuilding[i] = new JButton("(+)");
            increaseBuilding[i].setFont(monospaced);
            increaseBuilding[i].setPreferredSize(new Dimension(30,30));
            increaseBuilding[i].addActionListener(this);
        }
        for(int i = 0; i < Globals.NUMBER_OF_CRAFTABLE_RESOURCES; i++){
            craftOne[i] = new JButton("+1"){
                public Point getToolTipLocation(MouseEvent e) {
                    return new Point(getWidth(), getHeight() / 2);
                }
            };
            craftOne[i].setFont(monospaced);
            craftOne[i].addActionListener(this);
            craftOne[i].setPreferredSize(new Dimension(50, 50));
            craftFive[i] = new JButton("+5"){
                public Point getToolTipLocation(MouseEvent e) {
                    return new Point(getWidth(), getHeight() / 2);
                }
            };
            craftFive[i].setFont(monospaced);
            craftFive[i].addActionListener(this);
            craftFive[i].setPreferredSize(new Dimension(50, 50));
            craftTen[i] = new JButton("+10"){
                public Point getToolTipLocation(MouseEvent e) {
                    return new Point(getWidth(), getHeight() / 2);
                }
            };
            craftTen[i].setFont(monospaced);
            craftTen[i].addActionListener(this);
            craftTen[i].setPreferredSize(new Dimension(50, 50));
            craftAll[i] = new JButton("All"){
                public Point getToolTipLocation(MouseEvent e) {
                    return new Point(getWidth(), getHeight() / 2);
                }
            };
            craftAll[i].setFont(monospaced);
            craftAll[i].setToolTipText("All your resource are belong to me!");
            craftAll[i].addActionListener(this);
            craftAll[i].setPreferredSize(new Dimension(50, 50));
        }
        for(int i = 0; i < Globals.NUMBER_OF_CIVILIZATIONS; i++){
            trade[i] = new JButton("Trade"){
                public Point getToolTipLocation(MouseEvent e) {
                    return new Point(getWidth(), getHeight() / 2);
                }
            };
            trade[i].setToolTipText("<html>" + Formatting.sizeToolTip("Send one of your traders to this civilization. Requires 10 gold.", 30) + "<html>");
            trade[i].addActionListener(this);
            trade[i].setPreferredSize(new Dimension((int)(Globals.WIDTH_OF_SCREEN / 6) - 15, 50));
            gift[i] = new JButton("Gift"){
                public Point getToolTipLocation(MouseEvent e) {
                    return new Point(getWidth(), getHeight() / 2);
                }
            };
            gift[i].setToolTipText("<html>" + Formatting.sizeToolTip("Give this civiliztion a gift to improve your standing.", 30) + "<html>");
            gift[i].addActionListener(this);
            gift[i].setPreferredSize(new Dimension((int)(Globals.WIDTH_OF_SCREEN / 6) - 15, 50));
            
            moreInformation[i] = new JButton("More Information");
            moreInformation[i].addActionListener(this);
            
            scienceFavour[i] = new JLabel(Globals.ALL_CIVILIZATIONS[i].getScienceFavourString());
            magicFavour[i] = new JLabel(Globals.ALL_CIVILIZATIONS[i].getMagicFavourString());
            populationFavour[i] = new JLabel(Globals.ALL_CIVILIZATIONS[i].getPopulationFavourString());
            happinessFavour[i] = new JLabel(Globals.ALL_CIVILIZATIONS[i].getHappinessFavourString());
            warFavour[i] = new JLabel(Globals.ALL_CIVILIZATIONS[i].getWarFavourString());
            civDescription[i] = new JTextArea(Globals.ALL_CIVILIZATIONS[i].getDescription());
            civDescription[i].setEditable(false);
            civDescription[i].setLineWrap(true);
            civDescription[i].setWrapStyleWord(true);
        }
        
        civInfo = new RoundedPanel();
        civInfo.setBackground(Color.WHITE);
        civInfo.setLayout(new GridBagLayout());
        
        back = new JButton("Back");
        back.addActionListener(this);
        
        
        sendHunters = new JButton("Send Hunters");
        sendHunters.setFont(monospaced);
        sendHunters.addActionListener(this);
        
        warning = new JLabel("");
        warning.setBorder(BorderFactory.createEmptyBorder(50, 10, 5, 10));
        
        frame.pack();
        frame.setVisible(true);
        
        initializeImages();
        refreshBuildings();
        initializeTopOptions();
        initializeEventArea();
        refreshResources();
        checkFirstTime();
    }
    public void refreshBuildings(){
        buildingArea.removeAll();
        buildingArea.repaint();
        Utils.checkBuildingVisibility();
        boolean first = true;
        
        Globals.BUILDING_GRID_COLOMN = 0;
        Globals.BUILDING_GRID_ROW = 0;
        c.gridx = Globals.BUILDING_GRID_COLOMN;
        c.gridy = Globals.BUILDING_GRID_ROW;
        c.gridwidth = 6;
        scavenge.setPreferredSize(new Dimension((int)(buildingArea.getWidth() / 2) - 5,30));
        
        buildingArea.add(scavenge, c);
        
        sellBuilding.setPreferredSize(new Dimension((int)(buildingArea.getWidth() / 2) - 5,30));
        c.gridx = 6;
        buildingArea.add(sellBuilding, c);
        
        Globals.BUILDING_GRID_ROW = 1;
        
        for(int i = 0; i < Globals.NUMBER_OF_BUILDINGS; i++){
            if(Globals.ALL_BUILDINGS[i].getBuildable()){
                c.gridx = Globals.BUILDING_GRID_COLOMN;
                c.gridy = Globals.BUILDING_GRID_ROW;
                
                if(Globals.ALL_BUILDINGS[i].getEnablable()){
                    buildings[i] = new JButton(Globals.ALL_BUILDINGS[i].getName() + " (" +
                                          Globals.ALL_BUILDINGS[i].getNumberEnabled() + "/" +
                                          Globals.ALL_BUILDINGS[i].getNumberBuilt() + ")"){
                        public Point getToolTipLocation(MouseEvent event) {
                            return new Point(getWidth(), getHeight() / 2);
                        }
                    };
                    
                    c.insets = new Insets(0,2,0,0);
                    c.weightx = 0;
                    buildings[i].setFont(monospaced);
                    buildings[i].setPreferredSize(new Dimension((int)(buildingArea.getWidth() / 2) - 70,30));
                    c.gridwidth = 4;
                    buildingArea.add(buildings[i], c);
                    
                    c.insets = new Insets(0,0,0,0);
                    Globals.BUILDING_GRID_COLOMN += 4;
                    c.gridwidth = 1;
                    c.gridx = Globals.BUILDING_GRID_COLOMN;
                    buildingArea.add(decreaseBuilding[i], c);
                    
                    
                    Globals.BUILDING_GRID_COLOMN++;
                    c.gridx = Globals.BUILDING_GRID_COLOMN;
                    buildingArea.add(increaseBuilding[i], c);
                    
                    Globals.BUILDING_GRID_COLOMN++;
                    first = !first;
                }
                else{
                    c.gridwidth = 6;
                    buildings[i] = new JButton(Globals.ALL_BUILDINGS[i].getName() + " (" + 
                                          Globals.ALL_BUILDINGS[i].getNumberBuilt() + ")"){
                        public Point getToolTipLocation(MouseEvent event) {
                            return new Point(getWidth(), getHeight() / 2);
                        }
                    };
                    
                    buildings[i].setFont(monospaced);
                    buildings[i].addActionListener(this);
                    buildings[i].addMouseMotionListener(this);
                    
                    buildings[i].setPreferredSize(new Dimension((int)(buildingArea.getWidth() / 2) - 5,30));
                    
                    buildingArea.add(buildings[i],c);
                    
                    first = !first;
                }
                
                buildings[i].setToolTipText(Formatting.buildingTooltip(i));
                buildings[i].addActionListener(this);
                
                c.weightx = 1;
                if (!first){
                    Globals.BUILDING_GRID_COLOMN = 6;
                }
                else if (first){
                    Globals.BUILDING_GRID_COLOMN = 0;
                    Globals.BUILDING_GRID_ROW++;
                }
            }
        }   
        frame.pack();
    }
    public void refreshBuildingToolTips(){
        for(int i = 0; i < Globals.NUMBER_OF_BUILDINGS; i++){
            if(Globals.ALL_BUILDINGS[i].getBuildable()){
                buildings[i].setToolTipText(Formatting.buildingTooltip(i));
            }
        }
    }
    public void refreshVillage(){
        buildingArea.removeAll();
        buildingArea.repaint();
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridwidth = 3;
        freeBears = new JButton("Unemployed Bears: " + AmountCalculator.calculateFreeBears());
        freeBears.setPreferredSize(new Dimension((int)(buildingArea.getWidth() - 10),50));
        buildingArea.add(freeBears, gbc);
        
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridwidth = 1;
        
        for(int i = 0; i < Globals.NUMBER_OF_JOBS; i++){
            if(Globals.ALL_JOBS[i].getVisible()){
                gbc.gridy = i + 1;
                jobs[i] = new JButton(Globals.ALL_JOBS[i].getName() + " (" + Globals.ALL_JOBS[i].getNumberWorking() + ")"){
                    public Point getToolTipLocation(MouseEvent event) {
                        return new Point(getWidth(), getHeight() / 2);
                    }
                };
                
                jobs[i].setFont(monospaced);
                jobs[i].setToolTipText(Formatting.makeJobToolTip(i));
                jobs[i].setPreferredSize(new Dimension((int)(buildingArea.getWidth() - 120),50));
                buildingArea.add(jobs[i], gbc);
            }
        }
        gbc.gridx = 1;
        for(int i = 0; i < Globals.NUMBER_OF_JOBS; i++){
            if(Globals.ALL_JOBS[i].getVisible()){
                gbc.gridy = i + 1;
                buildingArea.add(decreaseWorking[i], gbc);
            }
        }
        gbc.gridx = 2;
        for(int i = 0; i < Globals.NUMBER_OF_JOBS; i++){
            if(Globals.ALL_JOBS[i].getVisible()){
                gbc.gridy = i + 1;
                buildingArea.add(increaseWorking[i], gbc);
            }
        }
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = Globals.NUMBER_OF_JOBS + 2;
        gbc.gridx = 0;
        gbc.gridwidth = 5;
        gbc.weightx = 1;
        
        if(upgradesVisible && Globals.ALL_SCIENCES[Utils.findScienceFromIdentifier('m')].getIsResearched()){
            upgradeSwitch = new JButton("Upgrades ▲");
            upgradeSwitch.setFont(monospaced);
            upgradeSwitch.addActionListener(this);
            buildingArea.add(upgradeSwitch, gbc);
            
            for(int i = 0; i < Globals.NUMBER_OF_UPGRADES; i++){ 
                if(Globals.ALL_UPGRADES[i].getVisible()){
                    gbc.gridy = i + 3 + Globals.NUMBER_OF_JOBS;
                    upgrades[i] = new JButton(Globals.ALL_UPGRADES[i].getName()){
                        public Point getToolTipLocation(MouseEvent e) {
                            return new Point(getWidth(), getHeight() / 2);
                        }
                    };
                    upgrades[i].setToolTipText(Formatting.upgradeToolTip(i));
                    upgrades[i].addActionListener(this);
                    upgrades[i].setPreferredSize(new Dimension((int)(buildingArea.getWidth() - 10),50));
                    buildingArea.add(upgrades[i], gbc);
                }
            }
        }
        else if(!upgradesVisible && Globals.ALL_SCIENCES[Utils.findScienceFromIdentifier('m')].getIsResearched()){
            upgradeSwitch = new JButton("Upgrades ▼");
            upgradeSwitch.addActionListener(this);
            upgradeSwitch.setFont(monospaced);
            buildingArea.add(upgradeSwitch, gbc);
        }
        
        gbc.weightx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.gridwidth = 1;
        
        frame.pack();
    }
    public void refreshUpgradeToolTips(){
        if(upgradesVisible && Globals.ALL_SCIENCES[Utils.findScienceFromIdentifier('m')].getIsResearched()){
            for(int i = 0; i < Globals.NUMBER_OF_UPGRADES; i++){ 
                if(Globals.ALL_UPGRADES[i].getVisible()){
                    upgrades[i].setToolTipText(Formatting.upgradeToolTip(i));
                }
            }
        }
    }
    public void refreshResources(){
        
        Utils.checkResourceVisibility();
        Utils.checkCraftableResourceVisibility();
        
        warning.setText(Utils.makeFoodWaterWarningMessage());
        
        if(Globals.NEW_RESOURCE){
            
            resources.removeAll();
            
            resources.setLayout(new BoxLayout(resources, BoxLayout.Y_AXIS));
        
            for(int i = 0; i < Globals.NUMBER_OF_RESOURCES; i++){
                if(Globals.ALL_RESOURCES[i].getVisible()){
                
                    resourceLabel[i] = new JLabel(Formatting.resourceName(i)){
                        public Point getToolTipLocation(MouseEvent event) {
                            return new Point(getWidth() / 2, getHeight());
                        }
                    };
                
                    resourceLabel[i].setFont(monospaced);
                    resourceLabel[i].setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                    if(Globals.AMOUNT_PER_SECOND[i] > 0 || Globals.AMOUNT_PER_SECOND[i] < 0){
                        resourceLabel[i].setToolTipText(Formatting.resourceToolTip(i));
                    }
                    resources.add(resourceLabel[i]);
                }
            }
            JLabel space = new JLabel(" ");
            resources.add(space);
            for(int i = 0; i < Globals.NUMBER_OF_CRAFTABLE_RESOURCES; i++){
                if(Globals.ALL_CRAFTABLE_RESOURCES[i].getVisible()){
                    craftableResourceLabel[i] = new JLabel(Formatting.formatCraftableResourceName(i)){
                        public Point getToolTipLocation(MouseEvent event) {
                            return new Point(getWidth() / 2, getHeight());
                        }
                    };
                
                    craftableResourceLabel[i].setFont(monospaced);
                    craftableResourceLabel[i].setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
                
                    if(Globals.AMOUNT_PER_SECOND_CRAFTABLE[i] > 0 || Globals.AMOUNT_PER_SECOND_CRAFTABLE[i] < 0){
                        craftableResourceLabel[i].setToolTipText(Utils.timeUntilMaxMin(i, true));
                    }
                    resources.add(craftableResourceLabel[i]);
                }
            }
            resources.add(warning);
            Globals.NEW_RESOURCE = false;
        }
        else{
            for(int i = 0; i < Globals.NUMBER_OF_RESOURCES; i++){
                if(Globals.ALL_RESOURCES[i].getVisible()){
                    resourceLabel[i].setText(Formatting.resourceName(i));
                    if(Globals.AMOUNT_PER_SECOND[i] > 0 || Globals.AMOUNT_PER_SECOND[i] < 0){
                        resourceLabel[i].setToolTipText(Formatting.resourceToolTip(i));
                    }
                    else{
                        resourceLabel[i].setToolTipText(null);
                    }
                }
            }
            for(int i = 0; i < Globals.NUMBER_OF_CRAFTABLE_RESOURCES; i++){
                if(Globals.ALL_CRAFTABLE_RESOURCES[i].getVisible()){
                    craftableResourceLabel[i].setText(Formatting.formatCraftableResourceName(i));
                    if(Globals.AMOUNT_PER_SECOND_CRAFTABLE[i] > 0 || Globals.AMOUNT_PER_SECOND_CRAFTABLE[i] < 0){
                        craftableResourceLabel[i].setToolTipText(Utils.timeUntilMaxMin(i, true));
                    }
                    else{
                        craftableResourceLabel[i].setToolTipText(null);
                    }
                }
            }
        }
        if(Globals.HUNTING_POINTS == 120){
            resources.add(sendHunters);
        }
        resources.repaint();
        frame.pack();
    }
    public void refreshSciences(){
        buildingArea.removeAll();
        buildingArea.repaint();
        Globals.BUILDING_GRID_ROW = 0;
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        
        for(int i = 0; i < Globals.NUMBER_OF_TECHNOLOGIES; i++){
            if(Globals.ALL_SCIENCES[i].getVisible()){
                c.gridy = Globals.BUILDING_GRID_ROW;
                sciences[i] = new JButton(Globals.ALL_SCIENCES[i].getName()){
                    public Point getToolTipLocation(MouseEvent e) {
                        return new Point(getWidth(), getHeight() / 2);
                    }
                };
                
                sciences[i].setFont(monospaced);
                sciences[i].setToolTipText(Formatting.researchTooltip(Globals.ALL_SCIENCES[i]));
                sciences[i].addActionListener(this);
                Globals.BUILDING_GRID_ROW++;
                buildingArea.add(sciences[i], c);
            }
        }
        
        c.fill = GridBagConstraints.NONE;
        frame.pack();
    }
    public void refreshScienceToolTips(){
        for(int i = 0; i < Globals.NUMBER_OF_TECHNOLOGIES; i++){
            if(Globals.ALL_SCIENCES[i].getVisible()){
                sciences[i].setToolTipText(Formatting.researchTooltip(Globals.ALL_SCIENCES[i]));
            }
        }
    }
    public void refreshMagic(){
        buildingArea.removeAll();
        buildingArea.repaint();
        c.gridx = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        
        for(int i = 0; i < Globals.NUMBER_OF_MAGICS; i++){
            if(Globals.ALL_MAGIC[i].getVisible()){
                c.gridy = Globals.BUILDING_GRID_ROW;
                magics[i] = new JButton(Globals.ALL_MAGIC[i].getName()){
                    public Point getToolTipLocation(MouseEvent e) {
                        return new Point(getWidth(), getHeight() / 2);
                    }
                };
                
                magics[i].setFont(monospaced);
                magics[i].setToolTipText(Formatting.researchTooltip(Globals.ALL_MAGIC[i]));
                magics[i].addActionListener(this);
                Globals.BUILDING_GRID_ROW++;
                buildingArea.add(magics[i], c);
            }
        }
        if(Globals.ALL_MAGIC[Utils.findMagicFromIdentifier('d')].getIsResearched() && magicEffectsVisible){
            c.gridy = Globals.BUILDING_GRID_ROW;
            magicEffectSwitch = new JButton("Magic ▲");
            magicEffectSwitch.addActionListener(this);
            magicEffectSwitch.setPreferredSize(new Dimension(50, 1));
            buildingArea.add(magicEffectSwitch, c);
            
            Globals.BUILDING_GRID_ROW++;
                    
            for(int i = 0; i < Globals.NUMBER_OF_MAGIC_EFFECTS; i++){
                if(Globals.ALL_MAGIC_EFFECTS[i].getVisible()){
                    c.gridy = Globals.BUILDING_GRID_ROW;
                    String str = "";
                
                    if(Globals.ALL_MAGIC_EFFECTS[i].getEnablable()){
                        if(!Globals.ALL_MAGIC_EFFECTS[i].getEnabled()){
                            str = Globals.ALL_MAGIC_EFFECTS[i].getName() + "(Disabled)";
                        }
                        else{
                            str = Globals.ALL_MAGIC_EFFECTS[i].getName() + "(Enabled)";
                        }
                    }
                    else{
                        str = Globals.ALL_MAGIC_EFFECTS[i].getName();
                    
                    }
                    magicEffects[i] = new JButton(str){
                            public Point getToolTipLocation(MouseEvent e) {
                                return new Point(getWidth(), getHeight() / 2);
                            }
                    };
                
                    magicEffects[i].setFont(monospaced);
                    magicEffects[i].setToolTipText(Formatting.createMagicEffectToolTip(i));
                    magicEffects[i].addActionListener(this);
                    buildingArea.add(magicEffects[i], c);
                    Globals.BUILDING_GRID_ROW++;
                }
            }
        }
        else if(Globals.ALL_MAGIC[Utils.findMagicFromIdentifier('d')].getIsResearched() && !magicEffectsVisible){
            c.gridy = Globals.BUILDING_GRID_ROW;
            magicEffectSwitch = new JButton("Magic ▼");
            magicEffectSwitch.setPreferredSize(new Dimension(10, 1));
            magicEffectSwitch.addActionListener(this);
            buildingArea.add(magicEffectSwitch, c);
        }
        c.fill = GridBagConstraints.NONE;
        frame.pack();
    }
    public void refreshMagicToolTips(){ 
        for(int i = 0; i < Globals.NUMBER_OF_MAGICS; i++){
            if(Globals.ALL_MAGIC[i].getVisible()){
                magics[i].setToolTipText(Formatting.researchTooltip(Globals.ALL_MAGIC[i]));
            }
        }
    }
    public void refreshCrafting(){
        buildingArea.removeAll();
        buildingArea.repaint();
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 11;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        craftingEfficiency = new JButton("Crafting Efficiency: " + (int)(AmountCalculator.calculateCraftEffectiveness() * 100) + "%");
        buildingArea.add(craftingEfficiency, gbc);
        
        gbc.gridwidth = 6;
        gbc.fill = GridBagConstraints.VERTICAL;
        
        for(int i = 0; i < Globals.NUMBER_OF_CRAFTABLE_RESOURCES; i++){
            if(Globals.ALL_CRAFTABLE_RESOURCES[i].getCraftable()){
                
                gbc.gridy = i + 1;
                craftResource[i] = new JButton(Globals.ALL_CRAFTABLE_RESOURCES[i].getName()){
                        public Point getToolTipLocation(MouseEvent e) {
                            return new Point(getWidth(), getHeight() / 2);
                        }
                };
                
                craftResource[i].setFont(monospaced);
                craftResource[i].setPreferredSize(new Dimension((int)(buildingArea.getWidth() - 210),30));
                craftResource[i].setToolTipText(Formatting.craftableResourceTooltip(i));
                buildingArea.add(craftResource[i], gbc);
            }
        }
        gbc.gridwidth = 1;
        for(int i = 0; i < Globals.NUMBER_OF_CRAFTABLE_RESOURCES; i++){
            if(Globals.ALL_CRAFTABLE_RESOURCES[i].getCraftable()){
                gbc.gridy = i + 1;
                gbc.gridx = 7;
                craftOne[i].setToolTipText(Formatting.createCraftToolTip(i, 1));
                buildingArea.add(craftOne[i], gbc);
                
                gbc.gridx = 8;
                craftFive[i].setToolTipText(Formatting.createCraftToolTip(i, 5));
                buildingArea.add(craftFive[i], gbc);
                
                gbc.gridx = 9;
                craftTen[i].setToolTipText(Formatting.createCraftToolTip(i, 10));
                buildingArea.add(craftTen[i], gbc);
                
                gbc.gridx = 10;
                buildingArea.add(craftAll[i], gbc);
            }
        }
        
        frame.pack();
    }
    public void refreshCraftingToolTips(){
        for(int i = 0; i < Globals.NUMBER_OF_CRAFTABLE_RESOURCES; i++){
            if(Globals.ALL_CRAFTABLE_RESOURCES[i].getCraftable()){
                craftResource[i].setToolTipText(Formatting.craftableResourceTooltip(i));
            }
        }
    }
    public void refreshTrade(){
        buildingArea.removeAll();
        buildingArea.repaint();
        
        c.gridx = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10,5,0,5);
        civConstraints = new GridBagConstraints();
        
        for(int i = 0; i < Globals.NUMBER_OF_CIVILIZATIONS; i++){
            if(Globals.ALL_CIVILIZATIONS[i].getRevealed()){
                c.gridy = i;
                civilizations[i] = new RoundedPanel();
                civilizations[i].setBackground(Color.WHITE);
                civilizations[i].setLayout(new GridBagLayout());
                buildingArea.add(civilizations[i], c);
                
                civNames[i] = new JLabel(Globals.ALL_CIVILIZATIONS[i].getName() + " (" + Globals.ALL_CIVILIZATIONS[i].getStatus() + ")");
                civBuys[i] = new JLabel("Buys: " + Globals.ALL_CIVILIZATIONS[i].getBuyAmount() + " " + Globals.ALL_RESOURCES[Globals.ALL_CIVILIZATIONS[i].getBuysResource()].getName());
                civBuys[i].setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
                civSells[i] = new JLabel("Sells: " + Globals.ALL_CIVILIZATIONS[i].getSellAmount() + " " + Globals.ALL_RESOURCES[Globals.ALL_CIVILIZATIONS[i].getSellsResource()].getName());
                civSells[i].setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
                
                trade[i].setPreferredSize(new Dimension((buildingArea.getWidth() / 2) - 30, 50));
                gift[i].setPreferredSize(new Dimension((buildingArea.getWidth() / 2) - 30, 50));
                
                civConstraints.gridx = 0;
                civConstraints.gridy = 0;
                civConstraints.fill = GridBagConstraints.NONE;
                civConstraints.gridwidth = 1;
                civilizations[i].add(civNames[i], civConstraints);
                
                civConstraints.gridx = 1;
                civilizations[i].add(new JLabel(new ImageIcon(civImage[i])), civConstraints);
                
                civConstraints.fill = GridBagConstraints.HORIZONTAL;
                civConstraints.gridwidth = 2;
                civConstraints.gridx = 0;
                civConstraints.gridy = 1;
                civilizations[i].add(civBuys[i], civConstraints);
                
                civConstraints.gridy = 2;
                civilizations[i].add(civSells[i], civConstraints);
                
                civConstraints.fill = GridBagConstraints.NONE;
                civConstraints.gridwidth = 1;
                civConstraints.gridx = 0;
                civConstraints.gridy = 3;
                civConstraints.weightx = 0;
                civilizations[i].add(trade[i], civConstraints);
                
                civConstraints.gridx = 1;
                civilizations[i].add(gift[i], civConstraints);
                
                civConstraints.gridx = 0;
                civConstraints.gridy = 4;
                civConstraints.gridwidth = 2;
                civConstraints.fill = GridBagConstraints.HORIZONTAL;
                civConstraints.weightx = 0;
                civilizations[i].add(moreInformation[i], civConstraints);
                
                trade[i].setEnabled(true);
                
                if(Globals.ALL_CIVILIZATIONS[i].getRaceStanding() == Globals.WAR){
                    civilizations[i].setBackground(Color.red);
                    civBuys[i].setBackground(Color.red);
                    civSells[i].setBackground(Color.red);
                    trade[i].setEnabled(false);
                }
            }
        }
        
        c.insets = new Insets(0,0,0,0);
        c.fill = GridBagConstraints.NONE;
        frame.pack();
    }
    public void refreshTradeLabels(){
        for(int i = 0; i < Globals.NUMBER_OF_CIVILIZATIONS; i++){
            if(Globals.ALL_CIVILIZATIONS[i].getRevealed()){
                civNames[i].setText(Globals.ALL_CIVILIZATIONS[i].getName() + " (" + Globals.ALL_CIVILIZATIONS[i].getStatus() + ")");
                civBuys[i].setText("Buys: " + Globals.ALL_CIVILIZATIONS[i].getBuyAmount() + " " + Globals.ALL_RESOURCES[Globals.ALL_CIVILIZATIONS[i].getBuysResource()].getName());
                civSells[i].setText("Sells: " + Globals.ALL_CIVILIZATIONS[i].getSellAmount() + " " + Globals.ALL_RESOURCES[Globals.ALL_CIVILIZATIONS[i].getSellsResource()].getName());
            }
        }
    }
    public void refreshInfo(int civ){
        buildingArea.removeAll();
        buildingArea.repaint();
        
        civInfo.removeAll();
        civInfo.repaint();
        
        civInfo.setPreferredSize(new Dimension(buildingArea.getWidth() - 100, buildingArea.getHeight() - 50));
        
        GridBagConstraints f = new GridBagConstraints();
        
        civNames[civ] = new JLabel(Globals.ALL_CIVILIZATIONS[civ].getName() + " (" + Globals.ALL_CIVILIZATIONS[civ].getStatus() + ")");
        civNames[civ].setFont(new Font("NotAFont", Font.BOLD, 30));
        civNames[civ].setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        civBuys[civ] = new JLabel("Buys: " + Globals.ALL_CIVILIZATIONS[civ].getBuyAmount() + " " + Globals.ALL_RESOURCES[Globals.ALL_CIVILIZATIONS[civ].getBuysResource()].getName());
        civBuys[civ].setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        civBuys[civ].setFont(new Font("NotAFont", Font.PLAIN, 14));
        civSells[civ] = new JLabel("Sells: " + Globals.ALL_CIVILIZATIONS[civ].getSellAmount() + " " + Globals.ALL_RESOURCES[Globals.ALL_CIVILIZATIONS[civ].getSellsResource()].getName());
        civSells[civ].setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        civSells[civ].setFont(new Font("NotAFont", Font.PLAIN, 14));
        civTradeTime[civ] = new JLabel("Trade Time: " + Utils.getTimeFromSeconds(Globals.ALL_CIVILIZATIONS[civ].getTradeTime()));
        civTradeTime[civ].setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        civTradeTime[civ].setFont(new Font("NotAFont", Font.PLAIN, 14));
        back.setPreferredSize(new Dimension(300, 50));
        civDescription[civ].setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        f.gridy = 0;
        civInfo.add(civNames[civ], f);
        
        f.gridy = 1;
        civInfo.add(civDescription[civ], f);
        
        f.gridy = 2;
        civInfo.add(civBuys[civ], f);
        
        f.gridy = 3;
        civInfo.add(civSells[civ], f);
        
        f.gridy = 4;
        civInfo.add(civTradeTime[civ], f);
        
        f.gridy = 5;
        civInfo.add(scienceFavour[civ], f);
        
        f.gridy = 6;
        civInfo.add(magicFavour[civ], f);
        
        f.gridy = 7;
        civInfo.add(populationFavour[civ], f);
        
        f.gridy = 8;
        civInfo.add(happinessFavour[civ], f);
        
        f.gridy = 9;
        civInfo.add(warFavour[civ], f);
        warFavour[civ].setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        
        f.gridy = 10;
        civInfo.add(back, f);
        
        buildingArea.add(civInfo);
        civDescription[civ].setPreferredSize(new Dimension(buildingArea.getWidth() - 180, 80));
        frame.pack();
    }
    public void refreshTime(){
        time.setText(Utils.getDateFromDaysPassed());
    }
    public void revealTime(){
        time.setVisible(true);
        events.remove(clear);
        events.repaint();
        gbc2.gridx = 2;
        gbc2.gridy = 0;
        gbc2.gridheight = 1;
        gbc2.gridwidth = 1;
        gbc2.fill = GridBagConstraints.NONE;
        events.add(clear, gbc2);
    }
    public void initializeTopOptions(){
        quit = new JButton("Quit");
        quit.setFont(monospaced);
        quit.addActionListener(this);
        save = new JButton("Save");
        save.setFont(monospaced);
        save.addActionListener(this);
        happiness = new JTextArea("(^-^): " + AmountCalculator.calculateHappiness() + "%");
        happiness.setFont(monospaced);
        happiness.setEditable(false);
        pause = new JButton("Pause");
        pause.setFont(monospaced);
        pause.addActionListener(this);
        
        topOptions.setLayout(new FlowLayout(FlowLayout.TRAILING, 4, 2));
        topOptions.add(new JLabel(new ImageIcon(polarbear)));
        topOptions.add(happiness);
        topOptions.add(pause);
        topOptions.add(save);
        topOptions.add(quit);
        happiness.setBorder(BorderFactory.createEmptyBorder(0,0,0,(int)(((Globals.WIDTH_OF_SCREEN)/5) - 205)));
    }
    public void refreshHappiness(){
        happiness.setText("(^-^): " + AmountCalculator.calculateHappiness() + "%");
        happiness.setToolTipText(Formatting.createHappinessToolTip());
    }
    public void initializeEventArea(){
        eventText.setPreferredSize(new Dimension(events.getWidth() - 50, events.getHeight() - 50));
    }
    public void writeToEvent(String e){
        try{
            Utils.checkNewSeason();
            
            if(Globals.NEW_SEASON && Globals.ALL_SCIENCES[Utils.findScienceFromIdentifier('c')].getIsResearched()){
                eventText.getDocument().insertString(0, Formatting.createEventHeader(), null);
                textPos = Formatting.createEventHeader().length();
                Globals.NEW_SEASON = false;
            }
            eventText.getDocument().insertString(textPos, e, null);
            
        }
        catch(Exception r){
        }
    }
    public void checkFirstTime(){
        if(Globals.FIRST_TIME){
            firstTime.setFont(monospaced);
            int input = firstTime.showOptionDialog(frame, "This is your first time opening The Polar Bear Game. \nNeed some help?","Polar Bear Game", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
            if(input ==JOptionPane.YES_OPTION){
                firstTime.showMessageDialog(frame, "Welcome to the North. You are a polar bear building a village.", "About", JOptionPane.PLAIN_MESSAGE);
                firstTime.showMessageDialog(resources, "To start, click scavenge -->\nto get some materials. Only some materials\ncan be obtained in this way", "About", JOptionPane.PLAIN_MESSAGE);
                firstTime.showMessageDialog(scavenge, "<-- Resources you have collected will show\n    up here. You have't collected anything\n    yet, but you will.", "About", JOptionPane.PLAIN_MESSAGE);
                firstTime.showMessageDialog(resources, "If you have enough resources, you can click -->\nto build a building. Check their tooltips for\nbuilding costs and effects.", "About", JOptionPane.PLAIN_MESSAGE);
                firstTime.showMessageDialog(resources, "Don't be afraid to buy new buildings,\nthey can unlock new opportunities for you.", "About", JOptionPane.PLAIN_MESSAGE);
                firstTime.showMessageDialog(buildingArea, "                                  ^\n                                  |\n                                  |\nThese are the different tabs.\nClick the tabs to mave between the different game\nscreens. Right now you just have the building tab, but you\nwill unlock more as you progress through the game.", "About", JOptionPane.PLAIN_MESSAGE);
                firstTime.showMessageDialog(resources, "You should first build some berry fields and\na bonfire. When you have enough berries and water, build\nan igloo for a bear to live in. Bears will die if\nyou don't have enough food and water for them, so\nkeep an eye on that.", "About", JOptionPane.PLAIN_MESSAGE);
                firstTime.showMessageDialog(resources, "Once you have a bear, you can assign it a job\nin the village tab. The number of available jobs will\nincrease as the game progresses", "About", JOptionPane.PLAIN_MESSAGE);
                firstTime.showMessageDialog(frame, "Well, thats it for the tutorial. Good luck and happy building.", "About", JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
    public void initializeImages(){
        polarbear = FileIO.readImage(Globals.POLAR_BEAR_IMAGE, 20, 20);
        
        for(int i = 0; i < Globals.NUMBER_OF_CIVILIZATIONS; i++){
            civImage[i] = FileIO.readImage(Globals.ALL_CIVILIZATIONS[i].getImageFIle(), 40, 30);
        }
    }
    public void enableVillage(){
        village.setVisible(true);
        village.setEnabled(true);
    }
    public void enableScience(){
        scienceSwitch.setVisible(true);
        scienceSwitch.setEnabled(true);
    }
    public void enableMagic(){
        magicSwitch.setVisible(true);
        magicSwitch.setEnabled(true);
    }
    public void enableCrafting(){
        craftingSwitch.setVisible(true);
        craftingSwitch.setEnabled(true);
    }
    public void enableTrade(){
        tradeSwitch.setVisible(true);
        tradeSwitch.setEnabled(true);
    }
    public int getWhichScreen(){
        return whichScreen;
    }
    public void actionPerformed(ActionEvent e){
        Object buttonPressed = e.getSource();
        if(buttonPressed == buildingsSwitch){
            refreshBuildings();
            whichScreen = Globals.BUILDING_SCREEN;
        }
        for(int i = 0; i < Globals.NUMBER_OF_BUILDINGS; i++){
            if(buttonPressed == buildings[i]){
                if(sellingBuilding){
                    Globals.ALL_BUILDINGS[i].sellBuilding();
                }
                else{
                    Globals.ALL_BUILDINGS[i].buildBuilding();
                    AmountCalculator.calculateMaximums();
                    Utils.checkTabVisibility();
                }
                AmountCalculator.calculateMaximums();
                refreshBuildings();
            }
            else if(buttonPressed == increaseBuilding[i]){
                Globals.ALL_BUILDINGS[i].increaseEnabled();
                refreshBuildings();
            }
            else if(buttonPressed == decreaseBuilding[i]){
                Globals.ALL_BUILDINGS[i].decreaseEnabled();
                refreshBuildings();
            }
        }
        for(int i = 0; i < Globals.NUMBER_OF_TECHNOLOGIES; i++){
            if(buttonPressed == sciences[i]){
                if(Globals.ALL_SCIENCES[i].checkIfBuildable()){
                    Globals.ALL_SCIENCES[i].completeResearch();
                    for(int h = 0; h < Globals.NUMBER_OF_TECHNOLOGIES; h++){
                        Globals.ALL_SCIENCES[h].checkVisible();
                    }
                    for(int h = 0; h < Globals.NUMBER_OF_UPGRADES; h++){
                        Globals.ALL_UPGRADES[h].checkVisible();
                    }
                    refreshSciences();
                    AmountCalculator.calculateScienceBonuses();
                    Utils.checkTabVisibility();
                }
            }
        }
        for(int i = 0; i < Globals.NUMBER_OF_MAGICS; i++){
            if(buttonPressed == magics[i]){
                if(Globals.ALL_MAGIC[i].checkIfBuildable()){
                    Globals.ALL_MAGIC[i].completeResearch();
                    for(int h = 0; h < Globals.NUMBER_OF_MAGICS; h++){
                        Globals.ALL_MAGIC[h].checkVisible();
                    }
                    for(int h = 0; h < Globals.NUMBER_OF_MAGIC_EFFECTS; h++){
                        Globals.ALL_MAGIC_EFFECTS[h].checkVisible();
                    }
                    for(int h = 0; h < Globals.NUMBER_OF_UPGRADES; h++){
                        Globals.ALL_UPGRADES[h].checkVisible();
                    }
                    refreshMagic();
                }
            }
        }
        for(int i = 0; i < Globals.NUMBER_OF_MAGIC_EFFECTS; i++){
            if(buttonPressed == magicEffects[i]){
                Globals.ALL_MAGIC_EFFECTS[i].switchEnabled();
                refreshMagic();
            }
        }
        for(int i = 0; i < Globals.NUMBER_OF_CRAFTABLE_RESOURCES; i++){
            if(buttonPressed == craftOne[i]){
                Globals.ALL_CRAFTABLE_RESOURCES[i].craftResource(1);
                refreshResources();
            }
            if(buttonPressed == craftFive[i]){
                Globals.ALL_CRAFTABLE_RESOURCES[i].craftResource(5);
                refreshResources();
            }
            if(buttonPressed == craftTen[i]){
                Globals.ALL_CRAFTABLE_RESOURCES[i].craftResource(10);
                refreshResources();
            }
            if(buttonPressed == craftAll[i]){
                Globals.ALL_CRAFTABLE_RESOURCES[i].craftAll();
                refreshResources();
            }
        }
        if(buttonPressed == upgradeSwitch){
            upgradesVisible = !upgradesVisible;
            refreshVillage();
        }
        for(int i = 0; i < Globals.NUMBER_OF_UPGRADES; i++){
            if(buttonPressed == upgrades[i]){
                Globals.ALL_UPGRADES[i].researchUpgrade();
                refreshVillage();
            }
        }
        if(buttonPressed == magicEffectSwitch){
            magicEffectsVisible = !magicEffectsVisible;
            refreshMagic();
        }
        if(buttonPressed == sellBuilding){
            sellingBuilding = !sellingBuilding;
            if(sellingBuilding){
                sellBuilding.setText("Sell Building (Enabled)");
            }
            else{
                sellBuilding.setText("Sell Building (Disabled)");
            }
        }
        for(int i = 0; i < Globals.NUMBER_OF_CIVILIZATIONS; i++){
            if(buttonPressed == trade[i]){
                Globals.ALL_CIVILIZATIONS[i].startTrade();
                refreshResources();
                refreshTrade();
            }
            else if(buttonPressed == gift[i]){
                Globals.ALL_CIVILIZATIONS[i].startGift();
                refreshResources();
                refreshTrade();
            }
        }
        if(buttonPressed == quit){
            frame.dispose();
            Initialize.endGame();
        }
        if(buttonPressed == save){
            Save.saveGame();
        }
        if(buttonPressed == pause){
            if(!Globals.PAUSED){
                pause.setText("Unpause");
                happiness.setBorder(BorderFactory.createEmptyBorder(0,0,0,(int)((((Globals.WIDTH_OF_SCREEN)/5))- 223)));
            }
            else{
                pause.setText("Pause");
                happiness.setBorder(BorderFactory.createEmptyBorder(0,0,0,(int)((((Globals.WIDTH_OF_SCREEN)/5))- 205)));
            }
            Globals.PAUSED = !Globals.PAUSED;
        }
        if(buttonPressed == scienceSwitch){
            refreshSciences();
            whichScreen = Globals.SCIENCE_SCREEN;
        }
        if(buttonPressed == village){
            Utils.checkJobVisibility();
            refreshVillage();
            whichScreen = Globals.VILLAGE_SCREEN;
        }
        if(buttonPressed == magicSwitch){
            refreshMagic();
            whichScreen = Globals.MAGIC_SCREEN;
        }
        if(buttonPressed == craftingSwitch){
            refreshCrafting();
            whichScreen = Globals.CRAFTING_SCREEN;
        }
        if(buttonPressed == tradeSwitch){
            refreshTrade();
            whichScreen = Globals.TRADE_SCREEN;
        }
        if(buttonPressed == scavenge){
            if(!Globals.PAUSED){
                int i = 1 + (int)(Math.random() * 100);
                if(i <= 70){
                    Globals.ALL_RESOURCES[Utils.findResourceFromIdentifier('b')].changeAmount(1);
                }
                else if(i <= 90 && i > 70){
                    Globals.ALL_RESOURCES[Utils.findResourceFromIdentifier('I')].changeAmount(1);
                }
                else if(i<=100 && i>90){
                    Globals.ALL_RESOURCES[Utils.findResourceFromIdentifier('w')].changeAmount(1);
                }
                refreshResources();
            }
        }
        for(int i = 0; i < Globals.NUMBER_OF_JOBS; i++){
            if(buttonPressed == increaseWorking[i]){
                Globals.ALL_JOBS[i].increaseWorking();
                refreshVillage();
            }
        }
        for(int i = 0; i < Globals.NUMBER_OF_JOBS; i++){
            if(buttonPressed == decreaseWorking[i]){
                Globals.ALL_JOBS[i].decreaseWorking();
                refreshVillage();
            }
        }
        for(int i = 0; i < Globals.NUMBER_OF_CIVILIZATIONS; i++){
            if(buttonPressed == moreInformation[i]){
                refreshInfo(i);
            }
        }
        if(buttonPressed == clear){
            eventText.setText(null);
        }
        if(buttonPressed == sendHunters){
            AmountCalculator.calculateHuntingRewards();
            Globals.HUNTING_POINTS = 0;
            resources.remove(sendHunters);
            refreshResources();
        }
        if(buttonPressed == back){
            refreshTrade();
            whichScreen = Globals.TRADE_SCREEN;
        }
    }
    public void mouseEntered(MouseEvent e){
    }
    public void mouseMoved(MouseEvent e){
    }
    public void mouseDragged(MouseEvent e){
    }
}
