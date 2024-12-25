package game.rise_of_valor.game_engine;

import game.rise_of_valor.models.Tile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import static game.rise_of_valor.data.MapData.*;

public class TileManager {
    Tile[] tiles;

    int COL = MAP1_WIDTH / Tile.TileSize;
    int ROW = MAP1_HEIGHT / Tile.TileSize;

    public TileManager(int CanvasWidth, int CanvasHeight) {
        tiles = new Tile[10];
        getTileImage();
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

    public void draw(GraphicsContext gc, double cameraX, double cameraY, int canvasWidth, int canvasHeight) {
        int startCol = (int) cameraX / Tile.TileSize;
        int endCol = Math.min(startCol + (canvasWidth / Tile.TileSize) + 1, COL);

        int startRow = (int) cameraY / Tile.TileSize;
        int endRow = Math.min(startRow + (canvasHeight / Tile.TileSize) + 1, ROW);

        for (int col = startCol; col < endCol; col++) {
            for (int row = startRow; row < endRow; row++) {

                int tileIndex = getTileMapData(MAP1, col, row);
                gc.drawImage(tiles[tileIndex].image, col * Tile.TileSize, row * Tile.TileSize);
//                gc.drawImage(tiles[getTileMapData(MAP1, col, row)].image,
//                        col * Tile.TileSize, row * Tile.TileSize);
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
