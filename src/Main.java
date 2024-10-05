import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        JFrame jFrame = new JFrame("Raycasting");
        Raycaster raycaster = new Raycaster();

        jFrame.add(raycaster);
        jFrame.setSize(640, 480);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setVisible(true); // Note: This method call needs to be at the bottom.
    }
}