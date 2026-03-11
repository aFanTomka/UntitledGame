import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        Engine engine = new Engine();
        GameView gameView = new GameView(engine);
        JFrame frame = new JFrame("Untitled Game");
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());
        frame.add(gameView);
        frame.add(new Controls(engine), BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}