package game.rise_of_valor.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Gun {
    private String name;
    private int damage;
    private int shootSpeed; // gun fire rate

    private double gunBoxX;
    private double gunBoxY;
    private int gunBoxWidth = 30;
    private int gunBoxHeight = 10;
    private double gunBoxAngle = 0;

    double mouseX;
    double mouseY;
    double cameraX;
    double cameraY;

    private double handPositionX;
    private double handPositionY;

    private Image gunSprite;

    private double gunPointX, gunPointY;

    private int gunLength;

    public Gun(String name, int damage, int attackSpeed) {
        gunLength = name.equals("gun1") ? 15 : name.equals("gun2") ? 19 : 20;
        this.name = name;
        this.damage = damage;
        this.shootSpeed = attackSpeed;
        loadGunSprite();

    }
    public void update(){
        updateGunBoxAngle();
    }

    public void updateGunBoxAngle() {
        double handX = handPositionX - cameraX;
        double handY = handPositionY - cameraY;
        double dx = mouseX - handX;
        double dy = mouseY - handY;
        gunBoxAngle = Math.atan2(dy, dx);

//        System.out.println("HandPositionX: " + handPositionX + " HandPositionY: " + handPositionY);

        gunPointX = handPositionX + Math.cos(gunBoxAngle-0.15) * gunLength;
        gunPointY = handPositionY + Math.sin(gunBoxAngle-0.15) * gunLength;


    }


    public void loadGunSprite() {
        gunSprite = new Image(getClass().getResourceAsStream("/game/rise_of_valor/assets/guns/" + name + ".png"));
    }



    public void draw(GraphicsContext gc) {


        // Draw gun box
        gc.setFill(Color.YELLOW);
        gc.save();
        gc.translate(handPositionX, handPositionY); // Translate to the hand position
        gc.rotate(Math.toDegrees(gunBoxAngle)); // Rotate around the hand position
        // draw the gun sprite
        gc.drawImage(gunSprite, -20, -gunBoxHeight / 2.0-5, gunBoxWidth*1.5, gunBoxHeight*1.5);
//        gc.strokeRect(-20, -gunBoxHeight / 2.0, gunBoxWidth*2, gunBoxHeight*2); // Draw the gun box with the hand position as one side
        gc.restore();

        gc.fillOval(gunPointX-2,gunPointY-2,4,4);

    }

    public void setGunPosition(double x, double y){
        gunBoxX = x;
        gunBoxY = y;
    }
    public void setHandPosition(double x, double y){
        handPositionX = x;
        handPositionY = y;
    }

    public void updateMousePosition(double mouseX, double mouseY) {
        this.mouseX =  mouseX;
        this.mouseY =  mouseY;
    }

    public void updateCameraPosition(double cameraX, double cameraY) {
        this.cameraX = cameraX;
        this.cameraY = cameraY;
    }

    public int getDamage(){
        return damage;
    }

    public double getGunPointX() {
        return gunPointX;
    }
    public double getGunPointY() {
        return gunPointY;
    }

    public double getShootSpeed() {
        return shootSpeed;
    }
}
