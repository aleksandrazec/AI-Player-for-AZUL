import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class TilesBag {
    // 20 tiles of each type at start of game
    private List<Tile> tiles;
    private final SecureRandom random;
    private final TileBox tileBox;
    public TilesBag(TileBox tileBox){
        tiles=new ArrayList<>();
        tiles.addAll(initializeTileList(TileType.black));
        tiles.addAll(initializeTileList(TileType.cyan));
        tiles.addAll(initializeTileList(TileType.blue));
        tiles.addAll(initializeTileList(TileType.yellow));
        tiles.addAll(initializeTileList(TileType.red));
        random=new SecureRandom();
        this.tileBox=tileBox;
    }
    private void fillBag(){
        tiles=tileBox.emptyBox();
    }
    private List<Tile> initializeTileList(TileType type){
        List<Tile> tileList=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            tileList.add(new Tile(type));
        }
        return tileList;
    }

    public List<Tile> pullFourTiles(){
        List<Tile> fourTiles=new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            if(tiles.isEmpty()){
                fillBag();
            }
            int index=random.nextInt(0, tiles.size());
            fourTiles.add(tiles.remove(index));
        }
        return fourTiles;
    }
}
