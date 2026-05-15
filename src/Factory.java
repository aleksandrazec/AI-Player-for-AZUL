import java.util.ArrayList;
import java.util.List;

public class Factory {
    // 4 tiles always
    private List<Tile> tiles;
    public Factory(List<Tile> tiles){
        this.tiles=tiles;
    }
    public List<Tile> takeTiles(TileType type, CenterOfTable centerOfTable){
        List<Tile> takenTiles=new ArrayList<>();
        List<Tile> tilesForCenter=new ArrayList<>();
        for (int i = tiles.size()-1; i >= 0; i--) {
            if(tiles.get(i).type==type){
                takenTiles.add(tiles.remove(i));
            }else {
                tilesForCenter.add(tiles.remove(i));
            }
        }
        centerOfTable.addTiles(tilesForCenter);
        return takenTiles;
    }
}
