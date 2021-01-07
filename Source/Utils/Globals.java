package Utils;
import GameObjects.*;
import Windows.GameWindow;

public class Globals {
    public static final int NUMBER_OF_BUILDINGS = 14;
    public static final int NUMBER_OF_RESOURCES = 12;
    public static final int NUMBER_OF_CRAFTABLE_RESOURCES = 7;
    public static final int NUMBER_OF_TECHNOLOGIES = 11;
    public static final int NUMBER_OF_JOBS = 8;
    public static final int NUMBER_OF_MAGICS = 3;
    public static final int NUMBER_OF_MAGIC_EFFECTS = 3;
    public static final int NUMBER_OF_UPGRADES = 5;
    public static final int NUMBER_OF_CIVILIZATIONS = 2;
    public static final int RECORD_LEN = 8;
    public static final int RATIO_LEN = 2;
    
    public static String SAVE_FILE = "_Save.dat";
    public static final String CONFIG_FILE = "_Config.dat";
    
    public static final String POLAR_BEAR_IMAGE = "Polar Bear.jpg";
    public static final String IMAGES_FOLDER = "Images/";
   
    public static final int DAY_LENGTH = 1;
    public static final int SPRING = 0;
    public static final int SUMMER = 1;
    public static final int FALL = 2;
    public static final int WINTER = 3;
    public static final int FRIENDLY = 2;
    public static final int NEUTRAL = 1;
    public static final int HOSTILE = 0;
    public static final int ALLIES = 2000;
    public static final int WAR = 0;
    public static final int NO_STANDING = 1000;
    
    public static final double HATES = 0.0;
    public static final double DISLIKES = 0.25;
    public static final double INDIFFERENT = 0.5;
    public static final double LIKES = 0.75;
    public static final double LOVES = 1.0;
    
    public static final int END_OF_ARRAY_MARKER = -1;
    
    public static final char END_OF_RECORD_MARKER = ';';
    public static final char END_OF_NAME_MARKER = '=';
    public static final char END_OF_RATIO_MARKER = '@';
    public static final char END_OF_COST_MARKER = '?';
    public static final char END_OF_RESOURCE_MARKER = '-';
    public static final char END_OF_TOOLTIP_MARKER = '+';
    public static final char END_OF_EFFECT_MARKER = '&';
    public static final char END_OF_COLOR_MARKER = '$';
    public static final char END_OF_TIME_MARKER = '#';
    public static final char END_OF_FAVOUR_MARKER = '-';
    public static final char END_OF_DEPENDANCY_MARKER = '~';
    public static final char END_OF_FILENAME_MARKER = '^';
    
    public static final char DEFAULT_IDENTIFIER = '!';
    public static final char NO_DEPENDANCY_MARKER = '-';
    public static final char RESOURCE_IDENTIFIER = 'r';
    public static final char CRAFTABLE_RESOURCE_IDENTIFIER = 'c';
    public static final char JOB_IDENTIFIER = 'j';
    public static final char CRAFT_EFFECTIVENESS_IDENTIFIER = 'e';
    public static final char BUILDING_IDENTIFIER = 'b';
    public static final char POSITIVE_PRODUCTION_IDENTIFIER = '+';
    public static final char NEGATIVE_PRODUCTION_IDENTIFIER = '-';
    public static final char PERCENT_BONUS_IDENTIFIER = '%';
    public static final char MAXIMUM_IDENTIFIER = 'm';
    
    public static final String[] FIRST_NAMES = new String[]{"Snowy", "Fluffy"};
    public static final String[] LAST_NAMES = new String[]{"Paws", "Hunter"};
    
    public static double[] AMOUNT_PER_SECOND = new double[NUMBER_OF_RESOURCES];
    public static double[] AMOUNT_PER_SECOND_CRAFTABLE = new double[NUMBER_OF_CRAFTABLE_RESOURCES];
    
    public static Building[] ALL_BUILDINGS = new Building[NUMBER_OF_BUILDINGS];
    public static Resource[] ALL_RESOURCES = new Resource[NUMBER_OF_RESOURCES];
    public static Science[] ALL_SCIENCES = new Science[NUMBER_OF_TECHNOLOGIES];
    public static Job[] ALL_JOBS = new Job[NUMBER_OF_JOBS];
    public static Magic[] ALL_MAGIC = new Magic[NUMBER_OF_MAGICS];
    public static MagicEffect[] ALL_MAGIC_EFFECTS = new MagicEffect[NUMBER_OF_MAGIC_EFFECTS];
    public static CraftableResource[] ALL_CRAFTABLE_RESOURCES = new CraftableResource[NUMBER_OF_CRAFTABLE_RESOURCES];
    public static Upgrade[] ALL_UPGRADES = new Upgrade[NUMBER_OF_UPGRADES];
    public static Civilization[] ALL_CIVILIZATIONS = new Civilization[NUMBER_OF_CIVILIZATIONS];
    
    public static int END_OF_LAST_CONFIG = -1;
    public static int WIDTH_OF_SCREEN = 0;
    public static int HEIGHT_OF_SCREEN = 0;
    public static int BUILDING_GRID_COLOMN = 0;
    public static int BUILDING_GRID_ROW = 0;
    
    public static boolean FIRST_TIME = true;
    public static boolean GAME_RUNNING = true;
    public static boolean PAUSED = false;
    public static boolean NEW_RESOURCE = true;
    public static boolean NEW_SEASON = true;
    
    public static int SNOWSTORM = 0;
    public static int HUNTING_POINTS = 0;
    public static long TIME_IN_DAYS = 0;
    
    public static final int BUILDING_SCREEN = 0;
    public static final int VILLAGE_SCREEN = 1;
    public static final int SCIENCE_SCREEN = 2;
    public static final int MAGIC_SCREEN = 3;
    public static final int CRAFTING_SCREEN = 4;
    public static final int TRADE_SCREEN = 5;
    
    public static String CONFIG = null;
    public static GameWindow GAME = null;
    
    public static final String ASCII_ART = "                                                 ------           ------                                                    \n" +
                                          "                                               /            \\_____/            \\                                                    \n" +
                                          "                                               |                                     |                                                   \n" +
                                          "                                               |         /\\          /\\           |                                                   \n" +
                                          "                                               |         |/|         |\\|          |                                                   \n" +
                                          "                                                \\        \\/          \\/         /                                                   \n" +
                                          "                                                 \\              __              /                                                    \n" +
                                          "                                                   \\            \\/             /                                                     \n" +
                                          "                                                   /\\______________/\\                                                     \n" +
                                          "                                                  /                              \\                                                   \n" +
                                          "                                                 /                                \\                                                   \n" +
                                          "   __               _____              ___          ______         ________    _____      ___       _____        \n" +
                                          "  |  |              /        \\          /      \\        |   __   \\      |___   ___|   |       \\     |   |     /   __   \\     \n" +
                                          "  |  |              |   /\\   |        /   /\\   \\      |   |   \\   \\         |  |         |   |\\   \\   |   |    /  /   \\__\\    \n" +
                                          "  |  |              |   | |   |       /   /_\\   \\      |   |    |   |        |  |         |   | \\   \\  |   |    |   |      ___   \n" +
                                          "  |  |              |   | |   |      /   ____   \\     |   |    |   |        |  |         |   |  \\   \\ |   |    |   |     \\   |   \n" +
                                          "  |  |_____     |   \\/   |     /   /      \\   \\    |   |__/  /   ___|  |___    |   |   \\   \\|   |    \\   \\__/   /   \n" +
                                          "  |________|  \\_____/   /__/        \\__\\  |______/   |_________|  |__|    \\_____|    \\______/";
    
}
