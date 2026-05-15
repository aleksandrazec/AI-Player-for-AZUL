import java.util.ArrayList;
import java.util.List;

public class BoardFloorLine {
    public final int[] floorLineValues=new int[]{-1,-1,-2,-2,-2,-3,-3};
    private final List<Tile> floorLine;
    private final Board board;
    private final TileBox tileBox;
    public BoardFloorLine(Board board, TileBox tileBox){
        floorLine=new ArrayList<>();
        this.board=board;
        this.tileBox=tileBox;
    }
    public void addMinusPoints(List<Tile> tiles){
        if(floorLine.size()+tiles.size()<=floorLineValues.length) {
            floorLine.addAll(tiles);
        }else {
            for (int i =0; i < floorLineValues.length-floorLine.size(); i++) {
                floorLine.add(tiles.remove(i));
            }
            tileBox.addTiles(tiles);
        }
    }
    public int calculateMinusPoints(){
        int minusPoints=0;
        int size = Math.min(floorLine.size(), 7);
        for (int i = 0; i < size; i++) {
            minusPoints+=floorLineValues[i];
        }
        moveTilesToBox();
        return minusPoints;
    }
    public void moveTilesToBox(){
        for (int i = 0; i < floorLine.size(); i++) {
            if(floorLine.get(i).type==TileType.startingPlayerMarker){
                board.setStartingPlayerMarker(true);
                floorLine.remove(i);
            }
            break;
        }
        tileBox.addTiles(floorLine);
        floorLine.clear();
    }
}
