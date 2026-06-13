import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameGUI extends JFrame {
    int fieldSize;
    JPanel panel;
    JPanel gameEnvironment;
    JPanel maxBoard;
    int maxTotalPoints;
    JLabel maxTotalPointsLabel;
    JPanel minBoard;
    int minTotalPoints;
    JLabel minTotalPointsLabel;
    BufferedImage oneTile = ImageIO.read(new File("assets/one_tile.png"));
    BufferedImage blackTile = ImageIO.read(new File("assets/black_tile.png"));
    BufferedImage blueTile = ImageIO.read(new File("assets/blue_tile.png"));
    BufferedImage cyanTile = ImageIO.read(new File("assets/cyan_tile.png"));
    BufferedImage redTile = ImageIO.read(new File("assets/red_tile.png"));
    BufferedImage yellowTile = ImageIO.read(new File("assets/yellow_tile.png"));
    public BufferedImage getTileImage(int type){
        return switch (type) {
            case 1 -> blueTile;
            case 2 -> yellowTile;
            case 3 -> redTile;
            case 4 -> blackTile;
            case 5 -> cyanTile;
            default -> oneTile;
        };
    }
    public GameGUI(int[] centerOfTable, int[][] factories) throws IOException{
        this.setTitle("Azul");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocation(300, 200);
        this.setSize(1000, 500);
        panel=createPanel(centerOfTable, factories);
        this.add(panel);
        this.setVisible(true);

    }
    public JPanel createPanel(int[] centerOfTable, int[][] factories){
        JPanel main = new JPanel();
        GridLayout gridLayout=new GridLayout(1,3);
        main.setLayout(gridLayout);
        maxBoard=createBoard(true);
        minBoard=createBoard(false);
        gameEnvironment=createGameEnvironment(centerOfTable,factories);
        main.add(maxBoard);
        main.add(gameEnvironment);
        main.add(minBoard);
        return main;
    }
    public JPanel createGameEnvironment(int[] centerOfTable, int[][] factories){
        JPanel gameEnvironment=new JPanel();
        GridLayout gameEnvironmentLayout=new GridLayout(3,1);
        gameEnvironment.setLayout(gameEnvironmentLayout);

        JPanel factoriesPanel =new JPanel();
        GridLayout factoriesPanelLayout=new GridLayout(1,5);
        factoriesPanel.setLayout(factoriesPanelLayout);
        for (int i = 0; i < 5; i++) {
            factoriesPanel.add(createFactory(factories[i]));
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

        return gameEnvironment;
    }
    public JPanel createFactory(int[] factory){
        JPanel factoryPanel=new JPanel();
        GridLayout gridLayout = new GridLayout(2,2);
        factoryPanel.setLayout(gridLayout);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < factory[i]; j++) {
                JLabel tile = new JLabel(new ImageIcon(getTileImage(i)));
                factoryPanel.add(tile);
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

        JPanel wall=new JPanel();
        GridLayout wallLayout = new GridLayout(5,5);
        wall.setLayout(wallLayout);

        JPanel minusPoints=new JPanel();
        GridLayout minusPointsLayout = new GridLayout(2,7);
        minusPoints.setLayout(minusPointsLayout);

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