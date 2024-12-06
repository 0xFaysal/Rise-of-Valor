package game.rise_of_valor.game_engine;

import game.rise_of_valor.models.Player;
import game.rise_of_valor.models.Tile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static game.rise_of_valor.data.MapData.*;

public class TileManager {

    Tile[] tiles;
    int CanvasWidth;
    int CanvasHeight;

    int COL = MAP1_WIDTH / Tile.TileSize;
    int ROW = MAP1_HEIGHT / Tile.TileSize;


    public TileManager(int CanvasWidth, int CanvasHeight) {
        tiles = new Tile[10];
        getTileImage();
        this.CanvasWidth = CanvasWidth;
        this.CanvasHeight = CanvasHeight;
        System.out.println("Canvas Width: " + CanvasWidth + " Canvas Height: " + CanvasHeight);
    }

    private void getTileImage() {

        tiles[0] = new Tile();
        tiles[0].image = new Image(getClass().getResourceAsStream("/game/rise_of_valor/assets/tiles/sand.png"));
        tiles[2] = new Tile();
        tiles[2].image = new Image(getClass().getResourceAsStream("/game/rise_of_valor/assets/tiles/grass.png"));
        tiles[3] = new Tile();
        tiles[3].image = new Image(getClass().getResourceAsStream("/game/rise_of_valor/assets/tiles/water.png"));
        tiles[1] = new Tile();
        tiles[1].image = new Image(getClass().getResourceAsStream("/game/rise_of_valor/assets/tiles/earth.png"));

    }


//    public void draw(GraphicsContext gc, Camera camera) {
//        int startCol = camera.getCameraX() / Tile.TileSize;
//        int startRow = camera.getCameraY() / Tile.TileSize;
//        int endCol = Math.min(startCol + CanvasWidth / Tile.TileSize + 1, COL);
//        int endRow = Math.min(startRow + CanvasHeight / Tile.TileSize + 1, ROW);
//
//        for (int i = startCol; i < endCol; i++) {
//            for (int j = startRow; j < endRow; j++) {
//                gc.drawImage(tiles[getTileMapData(MAP1, i, j)].image,
//                        (i * Tile.TileSize) - camera.getCameraX(),
//                        (j * Tile.TileSize) - camera.getCameraY());
//            }
//        }
//    }

    public void draw(GraphicsContext gc , Player player) {

        System.out.println("Player X: " + player.getWorldX() + " Player Y: " + player.getWorldY());

        int startCol = (player.getWorldX() - player.getCameraX()  )/ Tile.TileSize;
        int startRow = (player.getWorldY() - player.getCameraY()) / Tile.TileSize;
        int endCol = Math.min(startCol + player.getCameraWidth() / Tile.TileSize + 1, COL);
        int endRow = Math.min(startRow + player.getCameraHeight() / Tile.TileSize + 1, ROW);



        System.out.println("StartCol: " + startCol + " ,StartRow: " + startRow + " ,EndCol: " + endCol + " ,EndRow: " + endRow);

        for (int worldCol = startCol; worldCol < endCol; worldCol++) {
            for (int worldRow= startRow ; worldRow < endRow; worldRow++) {

                int worldX = worldCol * Tile.TileSize;
                int worldY = worldRow * Tile.TileSize;
                int screenX = worldX - player.getWorldX() + player.getCameraX();
                int screenY = worldY - player.getWorldY() + player.getCameraY();

                gc.drawImage(tiles[getTileMapData(MAP1,worldCol,worldRow)].image, screenX, screenY,Tile.TileSize,Tile.TileSize);
            }
        }
    }

    public int getTileMapData(int[] mapData, int x, int y) {
        if (x < 0 || x >= COL || y < 0 || y >= ROW) {
            return 0;
        }
        return mapData[y * COL + x];
    }
}
