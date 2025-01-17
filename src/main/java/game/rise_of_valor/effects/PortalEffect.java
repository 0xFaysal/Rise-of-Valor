package game.rise_of_valor.effects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PortalEffect {
    private double x, y;
    private Image portalImage;
    private boolean isVisible;

    double scaleFactor =0.1;

    private double duration;
    private double timeElapsed;
    private double timeElapsed1;



    /**
     * Constructor for the PortalEffect class
     * @param x x-coordinate of the portal effect
     * @param y y-coordinate of the portal effect
     * @param portalImage Image of the portal effect
     */
    public PortalEffect(double x, double y, Image portalImage) {
        this.x = x;
        this.y = y;
        this.portalImage = portalImage;
        this.isVisible = true;
        this.timeElapsed = 0;
        this.duration = 0.8;
    }

    public void update(double deltaTime) {

        timeElapsed += deltaTime;
        timeElapsed1+=deltaTime;
    }

    public boolean isExpired() {
//        System.out.println(timeElapsed>=duration);
        return timeElapsed >= duration;
    }

    public void vanish() {

        if (timeElapsed1 >= 5.0) {
            isVisible = false;
        }
    }

    public void draw(GraphicsContext gc) {
        if (isVisible) {
            double width = portalImage.getWidth() * scaleFactor;
            double height = portalImage.getHeight() * scaleFactor;
            gc.drawImage(portalImage, x - width / 2, y - height / 2 +5, width, height);
        }
    }

    public boolean isVisible() {
        return isVisible;
    }
}