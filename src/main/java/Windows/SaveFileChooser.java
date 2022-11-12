package Windows;
import Utils.Globals;
import incrementalgame.Initialize;
import javax.swing.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.nio.file.*;

public class SaveFileChooser implements ActionListener{
    private JFrame frame = null;
    private JPanel buttonArea = null;
    private JButton[] save = null;
    private JButton[] newGames = null;
    private JButton confirm = null;
    private JButton start = null;
    private JButton back = null;
    private JButton delete = null;
    private JTextField gameName = null;
    private JLabel information = null;
    private GridBagConstraints c = null;
    private JOptionPane deleteCheck = null;
    String[] saveNames = null;
    File Saves = null;
    File[] allSaves = null;
    int saveChosen = 0;
    
    public SaveFileChooser(){
        Saves = new File(System.getProperty("user.dir"), "Saves");
        allSaves = Saves.listFiles();
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Globals.WIDTH_OF_SCREEN = (int)screenSize.getWidth();
        Globals.HEIGHT_OF_SCREEN = (int)screenSize.getHeight()-100;
        
        frame = new JFrame("Choose Your Save File");
        frame.setResizable(false);
        frame.setBackground(Color.WHITE);
        
        buttonArea = new JPanel();
        buttonArea.setBackground(Color.WHITE);
        buttonArea.setPreferredSize(new Dimension(300, 200));
        buttonArea.setLayout(new GridBagLayout());
        frame.add(buttonArea);
        frame.setLocation((Globals.WIDTH_OF_SCREEN - 300) / 2, (Globals.HEIGHT_OF_SCREEN - 200) / 2);
        
        c = new GridBagConstraints();
        
        Saves.mkdir();
        
        createSaveChooseScreen();
        
        frame.pack();
        frame.setVisible(true);
    }
    public void createSaveChooseScreen(){
        buttonArea.removeAll();
        buttonArea.repaint();
        
        allSaves = Saves.listFiles();
        String[] saveNames = Saves.list();
        save = new JButton[saveNames.length];
        newGames = new JButton[3 - saveNames.length];
        
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        
        if(saveNames.length > 0){
            int count = 0;
            for(int i = 0; i < save.length; i++){
                c.gridy = count;
                String s = allSaves[i].getName();
                save[i] = new JButton(s.substring(0, s.indexOf(".")));
                save[i].addActionListener(this);
                buttonArea.add(save[i], c);
                count++;
            }
            if(allSaves.length < 3){
                for(int i = 0; i < newGames.length; i++){
                    c.gridy = count;
                    newGames[i] = new JButton("New Game");
                    newGames[i].addActionListener(this);
                    buttonArea.add(newGames[i], c);
                    count++;
                }
            }
        }
        else{
            for(int i = 0; i < 3; i++){
                c.gridy = i;
                newGames[i] = new JButton("New Game");
                newGames[i].addActionListener(this);
                buttonArea.add(newGames[i], c);
            }
        }
        
        frame.pack();
    }
    public void createNameScreen(){
        buttonArea.removeAll();
        c.gridy = 0;
        c.fill = GridBagConstraints.NONE;
        c.weighty = 0;
        c.gridwidth = 3;
                
        information = new JLabel("Please enter your save file name:");
        gameName = new JTextField(15);
        confirm = new JButton("Confirm");
        confirm.addActionListener(this);
                
        buttonArea.add(information, c);
                
        c.weighty = 0;
        c.gridy = 1;
        buttonArea.add(gameName, c);
        
        c.gridwidth = 1;
        c.gridx = 2;
        c.gridy = 2;
        
        buttonArea.add(confirm, c);
                
        buttonArea.repaint();
        frame.pack();
    }
    public void createSaveFile(int i){
        Globals.SAVE_FILE = Saves.getPath() + "/" + allSaves[i].getName();
        frame.dispose();
        Initialize.initializeGame();
    }
    public void createMenuScreen(){
        buttonArea.removeAll();
        buttonArea.repaint();
        
        String saveName = allSaves[saveChosen].getName();
        information = new JLabel(saveName.substring(0, saveName.indexOf(".")));
        start = new JButton("Start");
        start.addActionListener(this);
        back = new JButton("Back");
        back.addActionListener(this);
        delete = new JButton("Delete");
        delete.addActionListener(this);
        
        c.fill = GridBagConstraints.VERTICAL;
        c.weightx = 0;
        c.weighty = 1;
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 0;
        buttonArea.add(information, c);
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 0;
        c.gridwidth = 4;
        c.gridx = 0;
        c.gridy = 1;
        buttonArea.add(start, c);
        
        c.weighty = 0;
        c.gridwidth = 2;
        c.gridx = 0;
        c.gridy = 2;
        buttonArea.add(back, c);
        
        c.gridwidth = 2;
        c.gridx = 2;
        c.gridy = 2;
        buttonArea.add(delete, c);
        
        frame.pack();
    }
    public void actionPerformed(ActionEvent e){
        Object buttonPressed = e.getSource();
        for(int i = 0; i < save.length; i++){
            if(buttonPressed == save[i]){
                saveChosen  = i;
                createMenuScreen();
            }
        }
        for(int i = 0; i < newGames.length; i++){
            if(buttonPressed == newGames[i]){
                createNameScreen();
            }
        }
        if(buttonPressed == confirm){
            if(gameName.getText().length() != 0){
                Globals.SAVE_FILE = Saves.getPath() + "/" + gameName.getText() + ".dat";
                Initialize.initializeGame();
                frame.dispose();
            }
            else{
                createNameScreen();
            }
        }
        if(buttonPressed == back){
            createSaveChooseScreen();
        }
        if(buttonPressed == start){
            createSaveFile(saveChosen);
        }
        if(buttonPressed == delete){
            int input = deleteCheck.showOptionDialog(frame, "This will delete your save file. Do you want to continue?","Delete Save", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
            if(input == JOptionPane.YES_OPTION){
                Path path = Paths.get(Saves.getPath() + "/" + allSaves[saveChosen].getName());
                try{
                    Files.delete(path);
                }
                catch(IOException o){
            
                }
                createSaveChooseScreen();
            }
        }
    }
}
