import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameView extends JPanel implements MouseListener {
    private long startTime;
    private Background background = new Background(); // Добавил
    private Engine engine;

    public GameView(Engine engine) {
        this.engine = engine;
        new Timer(16, e -> {
            engine.update(0.016f);
            repaint();
        }).start();
        addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        background.draw(g, getWidth(), getHeight()); // Добавил
        engine.draw(g);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("mouseClicked");

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("mousePressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("mouseReleased");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("mouseEntered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("mouseExited");
    }
    public void draw() {
        repaint();
    }
}
