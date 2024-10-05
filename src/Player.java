public class Player {
    double xLocation = 1.5;
    double yLocation = 1.5;
    double direction = 0;

    final double MOVEMENT_SPEED = 0.05;
    final double ROTATION_SPEED = 0.03;

    public void move(double movementAmount, int[][] map) {
        double nextXLocation = xLocation + Math.cos(direction) * movementAmount;
        double nextYLocation = yLocation + Math.sin(direction) * movementAmount;

        // Make sure the player is moving towards an open space.
        if (map[(int) nextYLocation][(int) nextXLocation] == 0) {
            xLocation = nextXLocation;
            yLocation = nextYLocation;
        }
    }

    public void rotate(double rotationAmount) {
        direction += rotationAmount;
    }
}