package game.rise_of_valor.game_engine;

public class Camera {
    private int x;
    private int y;
    private final int cameraWidth;
    private final int cameraHeight;
    private final int mapWidth;
    private final int mapHeight;

    public Camera(int cameraWidth, int cameraHeight, int mapWidth, int mapHeight) {
        this.cameraWidth = cameraWidth;
        this.cameraHeight = cameraHeight;
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.x = 0;
        this.y = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void update(int playerX, int playerY) {
        x = playerX - cameraWidth / 2 + 100;
        y = playerY - cameraHeight / 2 + 100;

        // Ensure the camera does not go out of bounds
        x = Math.max(0, Math.min(x, mapWidth - cameraWidth));
        y = Math.max(0, Math.min(y, mapHeight - cameraHeight));
    }
}