import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class GameGUI extends JFrame {
    JPanel panel;
    JPanel gameEnvironment;
    List<Tile>[] factories;
    List<Tile> centerOfTable;
    JPanel maxBoard;
    int maxTotalPoints;
    JLabel maxTotalPointsLabel;
    Tile[][] maxWall;
    Tile[][] maxPatternLines;
    Tile[] maxMinusPoints;

    JPanel minBoard;
    int minTotalPoints;
    JLabel minTotalPointsLabel;
    Tile[][] minWall;
    Tile[][] minPatternLines;
    Tile[] minMinusPoints;
    Color background = new Color(219,175,160);
    ImageIcon oneTile = new ImageIcon("assets/one_tile.png");
    ImageIcon blackTile = new ImageIcon("assets/black_tile.png");
    ImageIcon blueTile = new ImageIcon("assets/blue_tile.png");
    ImageIcon cyanTile = new ImageIcon("assets/cyan_tile.png");
    ImageIcon redTile = new ImageIcon("assets/red_tile.png");
    ImageIcon yellowTile =  new ImageIcon("assets/yellow_tile.png");
    ImageIcon placeholderTile = new ImageIcon("assets/placeholder_tile.png");

    public ImageIcon getTileImage(int type){
        return switch (type) {
            case 0 -> oneTile;
            case 1 -> blueTile;
            case 2 -> yellowTile;
            case 3 -> redTile;
            case 4 -> blackTile;
            case 5 -> cyanTile;
            default -> placeholderTile;
        };
    }
    public class Tile{
        protected JLabel label;
        protected JButton button;
        int type;
        boolean isButton;
        public Tile(int type, boolean inactive, boolean isButton){
            this.isButton=isButton;
            if(isButton){
                this.button=new JButton();
            }else {
                this.label = new JLabel();
            }
            this.type=type;
            if(inactive){
                makeInactive();
            }else{
                makeActive();
            }
        }
        public void setColor(Color color){
            if(isButton){
                button.setIcon(null);
                button.setBackground(color);
            }else{
                label.setIcon(null);
                label.setBackground(color);
            }
        }
        public void makeActive(){
            if(isButton){
                button.setIcon(getTileImage(type));
            }else {
                label.setIcon(getTileImage(type));
            }
        }
        public void makeInactive(){
            ImageIcon icon=getTileImage(type);
            final int w = icon.getIconWidth();
            final int h = icon.getIconHeight();
            GraphicsEnvironment ge =
            GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gd.getDefaultConfiguration();
            BufferedImage image = gc.createCompatibleImage(w, h);
            Graphics2D g2d = image.createGraphics();
            icon.paintIcon(null, g2d, 0, 0);
            Image gray = GrayFilter.createDisabledImage(image);
            if(isButton){
                button.setIcon(new ImageIcon(gray));
            }else {
                label.setIcon(new ImageIcon(gray));
            }
        }
        public void makeEmpty(){
            if(isButton){
                button.setIcon(getTileImage(-1));
            }else {
                label.setIcon(getTileImage(-1));
            }
        }
    }
    public GameGUI(int[][] factories) throws IOException{
        this.centerOfTable=new ArrayList<>();
        this.factories=new List[5];
        for (int i = 0; i < 5; i++) {
            this.factories[i]=new ArrayList<>();
        }
        this.maxWall=new Tile[5][5];
        this.minWall=new Tile[5][5];
        this.maxPatternLines=new Tile[5][5];
        this.minPatternLines=new Tile[5][5];
        this.maxMinusPoints=new Tile[7];
        this.minMinusPoints=new Tile[7];

        this.setTitle("Azul");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(300, 200);
        this.setSize(1200, 400);
        panel=createPanel(factories);
        this.add(panel);
        this.setVisible(true);
    }
    public JPanel createPanel(int[][] factories){
        JPanel main = new JPanel();
        GridLayout gridLayout=new GridLayout(1,3);
        main.setLayout(gridLayout);
        maxBoard=createBoard(true);
        minBoard=createBoard(false);
        gameEnvironment=createGameEnvironment(factories);
        main.setBackground(background);
        main.add(maxBoard);
        main.add(gameEnvironment);
        main.add(minBoard);
        return main;
    }
    public JPanel createGameEnvironment(int[][] factories){
        JPanel gameEnvironment=new JPanel();
        GridLayout gameEnvironmentLayout=new GridLayout(3,1);
        gameEnvironment.setLayout(gameEnvironmentLayout);

        JPanel factoriesPanel =new JPanel();
        GridLayout factoriesPanelLayout=new GridLayout(1,5);
        factoriesPanel.setLayout(factoriesPanelLayout);
        for (int i = 0; i < 5; i++) {
            factoriesPanel.add(createFactory(factories[i],i));
        }

//        JPanel upperFactories =new JPanel();
//        GridLayout upperFactoriesLayout=new GridLayout(1,3);
//        upperFactories.setLayout(upperFactoriesLayout);
//        for (int i = 0; i < 3; i++) {
//            upperFactories.add(createFactory(factories[i]));
//        }
//
//        JPanel lowerFactories =new JPanel();
//        GridLayout lowerFactoriesLayout=new GridLayout(1,2);
//        lowerFactories.setLayout(lowerFactoriesLayout);
//        for (int i = 3; i < 5; i++) {
//            lowerFactories.add(createFactory(factories[i]));
//        }

        JPanel center = new JPanel();
//        because center can have maximum 16 tiles
        GridLayout centerLayout=new GridLayout(2,8);
        center.setLayout(centerLayout);
        Tile oneTile=new Tile(0, false,true);
        center.add(oneTile.button);
        centerOfTable.add(oneTile);
//        for (int i = 1; i < 16; i++) {
//            Tile tile = new Tile(-1,false);
//            center.add(tile.label);
//        }
        gameEnvironment.add(factoriesPanel);
        gameEnvironment.add(center);
        return gameEnvironment;
    }

    public JPanel createFactory(int[] factory, int factoryIndex){
        JPanel factoryPanel=new JPanel();
        GridLayout gridLayout = new GridLayout(2,2);
        factoryPanel.setLayout(gridLayout);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < factory[i]; j++) {
                Tile tile = new Tile(i, false, true);
                factoryPanel.add(tile.button);
                factories[factoryIndex].add(tile);
            }
        }
        return factoryPanel;
    }
    public JPanel createBoard(boolean isMax){
        JPanel board=new JPanel();
        GridLayout gridLayout = new GridLayout(2,2);
        board.setLayout(gridLayout);

        JPanel patternLines=new JPanel();
        GridLayout patternLineLayout = new GridLayout(5,5);
        patternLines.setLayout(patternLineLayout);
        if(isMax){
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if(StaticGameData.patternLinePattern[i][j]==0){
                        Tile tile = new Tile(-1, false, false);
                        tile.setColor(background);
                        patternLines.add(tile.label);
                        maxPatternLines[i][j]=tile;
                    }else{
                        Tile tile = new Tile(-1, false, true);
                        patternLines.add(tile.button);
                        maxPatternLines[i][j]=tile;
                    }
                }
            }
        }else {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if(StaticGameData.patternLinePattern[i][j]==0){
                        Tile tile = new Tile(-1, false, false);
                        tile.setColor(background);
                        patternLines.add(tile.label);
                        minPatternLines[i][j]=tile;
                    }else{
                        Tile tile = new Tile(-1, false, true);
                        patternLines.add(tile.button);
                        minPatternLines[i][j]=tile;
                    }
                }
            }
        }


        JPanel wall=new JPanel();
        GridLayout wallLayout = new GridLayout(5,5);
        wall.setLayout(wallLayout);
        if(isMax) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    Tile tile = new Tile(StaticGameData.wallPattern[i][j], true, false);
                    wall.add(tile.label);
                    maxWall[i][j] = tile;
                }
            }
        }else{
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    Tile tile = new Tile(StaticGameData.wallPattern[i][j], true, false);
                    wall.add(tile.label);
                    minWall[i][j] = tile;
                }
            }
        }
        JPanel minusPoints=new JPanel();
        GridLayout minusPointsLayout = new GridLayout(2,7);
        minusPoints.setLayout(minusPointsLayout);
        for (int i = 0; i < 7; i++) {
            JLabel minusPointsLabel = new JLabel(Integer.toString(StaticGameData.floorLineValues[i]), SwingConstants.CENTER);
            minusPoints.add(minusPointsLabel);
        }
        if(isMax){
            for (int i = 0; i < 7; i++) {
                Tile tile=new Tile(-1, false, false);
                minusPoints.add(tile.label);
                maxMinusPoints[i]=tile;
            }
        }else{
            for (int i = 0; i < 7; i++) {
                Tile tile=new Tile(-1, false, false);
                minusPoints.add(tile.label);
                minMinusPoints[i]=tile;
            }
        }

        JPanel totalPoints=new JPanel();
        GridLayout totalPointsLayout=new GridLayout(1,2);
        totalPoints.setLayout(totalPointsLayout);
        JLabel totalPointsLabelText = new JLabel("Total Points: ", SwingConstants.CENTER);
        totalPoints.add(totalPointsLabelText);
        if(isMax){
            maxTotalPointsLabel = new JLabel(Integer.toString(this.maxTotalPoints), SwingConstants.CENTER);
            totalPoints.add(maxTotalPointsLabel);
        }else{
            minTotalPointsLabel = new JLabel(Integer.toString(this.minTotalPoints), SwingConstants.CENTER);
            totalPoints.add(minTotalPointsLabel);
        }

        board.add(patternLines);
        board.add(wall);
        board.add(minusPoints);
        board.add(totalPoints);
        return board;
    }
}