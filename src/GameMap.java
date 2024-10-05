import java.awt.*;

public class GameMap {
    // 2D representation of the map.
    // 1 = walls, 0 = open space
    private final int[][] mapData = {
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 1, 1, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
    };

    public int[][] getMapData() {
        return mapData;
    }

    public void renderMinimap(Graphics g, Player player, int scale) {
        // Draw the map grid.
        for (int y = 0; y < mapData.length; y++) {
            for (int x = 0; x < mapData[0].length; x++) {
                g.setColor(mapData[y][x] == 1 ? Color.GRAY : Color.WHITE);
                g.fillRect(x * scale, y * scale, scale, scale);
            }
        }

        // Draw the player on the minimap.
        g.setColor(Color.RED);
        int playerXLocation = (int) (player.xLocation * scale);
        int playerYLocation = (int) (player.yLocation * scale);
        g.fillOval(playerXLocation - 2, playerYLocation - 2, 5, 5);

        int rayLength = 10;
        int rayEndXLocation = (int) (playerXLocation + Math.cos(player.direction) * rayLength);
        int rayEndYLocation = (int) (playerYLocation + Math.sin(player.direction) * rayLength);
        g.drawLine(playerXLocation, playerYLocation, rayEndXLocation, rayEndYLocation);
    }
}