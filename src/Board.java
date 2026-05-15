public class Board {
    private int currentPoints;
    private final BoardWall boardWall;
    private final BoardFloorLine boardFloorLine;
    private final BoardPatternLine[] boardPatternLines;
    private boolean startingPlayerMarker;
    private final TileBox tileBox;

    public Board(TileBox tileBox){
        this.tileBox=tileBox;
        boardWall=new BoardWall();
        boardFloorLine=new BoardFloorLine(this,tileBox);
        boardPatternLines=new BoardPatternLine[]{
            new BoardPatternLine(1,boardWall),
            new BoardPatternLine(2,boardWall),
            new BoardPatternLine(3,boardWall),
            new BoardPatternLine(4,boardWall),
            new BoardPatternLine(5,boardWall)
        };
        currentPoints=0;
        startingPlayerMarker=false;
    }
    public void setStartingPlayerMarker(boolean value){
        startingPlayerMarker=value;
    }
}
