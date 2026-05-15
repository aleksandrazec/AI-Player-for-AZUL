import java.util.ArrayList;
import java.util.List;

public class TileBox {
    private final List<Tile> tiles;
    public TileBox(){
        tiles=new ArrayList<>();
    }
    public List<Tile> emptyBox(){
        List<Tile> temp = tiles;
        tiles.clear();
        return temp;
    }
    /**
     *
     * @param tilesToAdd list of tiles to add to the box
     * @return the success of adding the tiles
     */
    public boolean addTiles(List<Tile> tilesToAdd){
        for (Tile tileToAdd : tilesToAdd) {
            if(tileToAdd.type==TileType.startingPlayerMarker){
                return false;
            }
        }
        tiles.addAll(tilesToAdd);
        return true;
    }
}
