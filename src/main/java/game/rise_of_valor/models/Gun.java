package game.rise_of_valor.models;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Gun {
    private final String name;

    private final int power;
    private final int damage;
    private final int shootSpeed; // gun fire rate


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
    private double gunBoxAngleOffset = 0;
//    private double gunBoxAngleOffset = 0.23;

    private int gunLength;
    private double gunScale;

    boolean isFlipped = false;

    public Gun(String name, int damage, int attackSpeed) {
        power = switch (name) {
            case "gun1" -> 1;
            case "gun2" -> 2;
            case "gun3" -> 3;
            case "gun4" -> 4;
            case "gun5" -> 5;
            case "pistol" -> 1;
            case "shotgun" -> 2;
            case "rifle" -> 3;
            case "sniper" -> 4;
            default -> 0;
        };
        gunLength = switch (name) {
            case "gun1" -> 21;
            case "gun2" -> 20;
            case "gun3" -> 25;
            case "gun4" -> 48;
            case "gun5" -> 35;
            case "pistol" -> 20;
            case "shotgun" -> 28;
            case "rifle" -> 20;
            case "sniper" -> 28;
            default -> 0;
        };

        gunScale = switch (name) {
            case "gun1" -> 0.22;
            case "gun2" -> 0.2;
            case "gun3" -> 0.2;
            case "gun4" -> 0.29;
            case "gun5" -> 0.2;
            case "pistol" -> 0.9;
            case "shotgun" -> 0.8;
            case "rifle" -> 0.8;
            case "sniper" -> 0.8;
            default -> 0;
        };

        gunBoxAngleOffset = switch (name) {
            case "gun1" -> 0.23;
            case "gun2", "pistol", "gun5" -> 0.10;
            case "gun3" -> 0.12;
            case "gun4" -> 0.15;
            case "rifle" -> 0.15;
            case "sniper" -> 0.10;
            default -> 0;
        };

        this.name = name;
        this.damage = damage;
        this.shootSpeed = attackSpeed;
        loadGunSprite();
        gunBoxWidth = (int) (gunSprite.getWidth() * gunScale);
        gunBoxHeight = (int) (gunSprite.getHeight() * gunScale);

    }

    public void update() {



        updateGunBoxAngle();
    }

    public void updateGunBoxAngle() {
        double handX = handPositionX - cameraX;
        double handY = handPositionY - cameraY;
        double dx = mouseX - handX;
        double dy = mouseY - handY;
        gunBoxAngle = Math.atan2(dy, dx);

//        System.out.println(gunBoxAngleOffset);

        isFlipped = gunBoxAngle > Math.PI / 2 || gunBoxAngle < -Math.PI / 2;
//        System.out.println(isFlipped);
        if (isFlipped) {
            gunBoxAngleOffset = -Math.abs(gunBoxAngleOffset);
        }else{
            gunBoxAngleOffset = Math.abs(gunBoxAngleOffset);
        }

        gunPointX = handPositionX + Math.cos(gunBoxAngle - gunBoxAngleOffset) * gunLength;
        gunPointY = handPositionY + Math.sin(gunBoxAngle - gunBoxAngleOffset) * gunLength;



    }


    public void loadGunSprite() {
        gunSprite = new Image(getClass().getResourceAsStream("/game/rise_of_valor/assets/guns/" + name + ".png"));
    }


    public void draw(GraphicsContext gc) {


        // Draw gun box
//        gc.setFill(Color.YELLOW);
        gc.save();
        gc.translate(handPositionX, handPositionY); // Translate to the hand position

        gc.rotate(Math.toDegrees(gunBoxAngle)); // Rotate around the hand position

        // Flip the gun sprite if the gunBoxAngle is greater than 90 degrees
        if (isFlipped) {
            gc.scale(1, -1);
//
        }

        // draw the gun sprite
        gc.drawImage(gunSprite, -20, -gunBoxHeight * gunScale / 2.0, gunBoxWidth * gunScale, gunBoxHeight * gunScale);
//        gc.strokeRect(-20,-gunBoxHeight*gunScale / 2.0 , gunBoxWidth*gunScale, gunBoxHeight*gunScale); // Draw the gun box with the hand position as one side
        gc.restore();

        gc.fillOval(gunPointX - 2, gunPointY - 2, 4, 4);

    }



    public void setHandPosition(double x, double y) {
        handPositionX = x;
        handPositionY = y;
    }

    public void updateMousePosition(double mouseX, double mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public void updateCameraPosition(double cameraX, double cameraY) {
        this.cameraX = cameraX;
        this.cameraY = cameraY;
    }

    public int getDamage() {
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

    public int getPower() {
        return power;
    }
}
