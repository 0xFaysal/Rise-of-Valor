package game.rise_of_valor.game_engine;

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

    public void draw(GraphicsContext gc){
        for (int i = 0; i < COL; i++) {
            for (int j= 0 ; j < ROW; j++) {
                gc.drawImage(tiles[getTileMapData(MAP1,i,j)].image, i * 16, j * 16);
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
