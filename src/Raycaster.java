import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Raycaster extends JPanel {
    private final Player player = new Player();
    private final GameMap gameMap = new GameMap();
    private final int MINIMAP_SCALE = 10;

    public Raycaster() {
        setFocusable(true);
        addKeyListener(new KeyHandler());
    }

    private class KeyHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_W) {
                player.move(player.MOVEMENT_SPEED, gameMap.getMapData());
            }

            if (e.getKeyCode() == KeyEvent.VK_S) {
                player.move(-player.MOVEMENT_SPEED, gameMap.getMapData());
            }

            if (e.getKeyCode() == KeyEvent.VK_A) {
                player.rotate(player.ROTATION_SPEED);
            }

            if (e.getKeyCode() == KeyEvent.VK_D) {
                player.rotate(-player.ROTATION_SPEED);
            }

            repaint();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        renderScene(g);
        gameMap.renderMinimap(g, player, MINIMAP_SCALE);
    }

    private void renderScene(Graphics g) {
        // Paint the sky.
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, getWidth(), getHeight() / 2);

        // Paint the ground.
        g.setColor(Color.GREEN);
        g.fillRect(0, getHeight() / 2, getWidth(), getHeight() / 2);

        for (int x = 0; x < getWidth(); x++) {
            double cameraX = 2 * x / (double) getWidth() - 1; // Relative X position in FOV (-1 to 1).
            double rayDirectionX = Math.cos(player.direction) + Math.sin(player.direction) * cameraX;
            double rayDirectionY = Math.sin(player.direction) - Math.cos(player.direction) * cameraX;

            // Cast the ray.
            int mapX = (int) player.xLocation;
            int mapY = (int) player.yLocation;

            double sideDistanceX, sideDistanceY;

            // Calculate distance to next grid intersection.
            double deltaDistanceX = Math.abs(1 / rayDirectionX);
            double deltaDistanceY = Math.abs(1 / rayDirectionY);
            double perpWallDistance;

            int stepX, stepY;
            boolean hasHit = false;
            int side = 0;

            // Determine step direction and initial side distances.
            if (rayDirectionX < 0) {
                stepX = -1;
                sideDistanceX = (player.xLocation - mapX) * deltaDistanceX;
            } else {
                stepX = 1;
                sideDistanceX = (mapX + 1.0 - player.xLocation) * deltaDistanceX;
            }

            if (rayDirectionY < 0) {
                stepY = -1;
                sideDistanceY = (player.yLocation - mapY) * deltaDistanceY;
            } else {
                stepY = 1;
                sideDistanceY = (mapY + 1.0 - player.yLocation) * deltaDistanceY;
            }

            while (!hasHit) {
                if (sideDistanceX < sideDistanceY) {
                    sideDistanceX += deltaDistanceX;
                    mapX += stepX;
                    side = 0;
                } else {
                    sideDistanceY += deltaDistanceY;
                    mapY += stepY;
                    side = 1;
                }

                if (gameMap.getMapData()[mapY][mapX] > 0) {
                    hasHit = true;
                }
            }

            // Calculate distance to the wall.
            if (side == 0) {
                perpWallDistance = (mapX - player.xLocation + (1 - stepX) / 2) / rayDirectionX;
            } else {
                perpWallDistance = (mapY - player.yLocation + (1 - stepY) / 2) / rayDirectionY;
            }

            // Figure out how tall the wall we're drawing is.
            int lineHeight = (int)(getHeight() / perpWallDistance);
            int drawStart = -lineHeight / 2 + getHeight() / 2;
            int drawEnd = lineHeight / 2 + getHeight() / 2;

            if (side == 1) {
                g.setColor(Color.DARK_GRAY);
            } else {
                g.setColor(Color.LIGHT_GRAY);
            }

            g.drawLine(x, drawStart, x, drawEnd);
        }
    }
}