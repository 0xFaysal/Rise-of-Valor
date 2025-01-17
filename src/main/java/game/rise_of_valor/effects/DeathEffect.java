package game.rise_of_valor.effects;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class DeathEffect {
    private static final double DURATION = 1.0; // Duration in seconds
    private double x;
    private double y;
    private double timeElapsed;
    private List<Particle> particles;
    private int characterId;

    public DeathEffect(double x, double y, double enemyWidth, double enemyHeight, int characterId) {
        this.x = x;
        this.y = y;
        this.timeElapsed = 0;
        this.characterId = characterId;
        this.particles = new ArrayList<>();
        generateParticles(enemyWidth, enemyHeight);
    }

    private void generateParticles(double enemyWidth, double enemyHeight) {
        for (int i = 0; i < 30; i++) { // Reduced number of particles
            particles.add(new Particle(x, y, enemyWidth, enemyHeight, characterId));
        }
    }

    public void update(double deltaTime) {
        timeElapsed += deltaTime;
        for (Particle particle : particles) {
            particle.update(deltaTime);
        }
        particles.removeIf(Particle::isExpired);
    }

    public boolean isExpired() {
        return timeElapsed >= DURATION && particles.isEmpty();
    }

    public void draw(GraphicsContext gc) {
        for (Particle particle : particles) {
            particle.draw(gc);
        }
    }

    private static class Particle {
        private double x, y;
        private double vx, vy;
        private double life;
        private static final double MAX_LIFE = 1.0;
        private Color color;

        public Particle(double x, double y, double enemyWidth, double enemyHeight, int characterId) {
            this.x = x;
            this.y = y;
            this.vx = (Math.random() - 0.5) * enemyWidth * 2; // Velocity based on enemy width
            this.vy = (Math.random() - 0.5) * enemyHeight * 2; // Velocity based on enemy height
            this.life = MAX_LIFE;
            this.color = getColorByCharacterId(characterId);
        }

        private Color getColorByCharacterId(int characterId) {
            return switch (characterId) {
                case 1 -> Color.web("#BB60D8");
                case 2 -> Color.web("#D86460");
                case 3 -> Color.web("#BAD860");
                case 4 -> Color.web("#CBB05A");
                default -> Color.rgb(255, 255, (int)(Math.random() * 240)); // Default white-influenced color
            };
        }

        public void update(double deltaTime) {
            life -= deltaTime;
            x += vx * deltaTime;
            y += vy * deltaTime;
        }

        public boolean isExpired() {
            return life <= 0;
        }

        public void draw(GraphicsContext gc) {
            double alpha = life / MAX_LIFE;
            double size = 8 * alpha; // Start large and decrease size
            gc.setGlobalAlpha(alpha);
            gc.setFill(color);
            gc.fillOval(x - size / 2, y - size / 2, size, size);
            gc.setGlobalAlpha(1.0); // Reset alpha
        }
    }
}