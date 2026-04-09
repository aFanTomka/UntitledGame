import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameView extends JPanel implements MouseListener {
    private long lastFrameTime;
    private Background background = new Background();
    private Engine engine;

    public GameView(Engine engine) {
        this.engine = engine;
        lastFrameTime = System.currentTimeMillis();

        new Timer(16, e -> {
            long now = System.currentTimeMillis();
            float deltaTime = (now - lastFrameTime) / 1000.0f;
            lastFrameTime = now;

            // Ограничиваем дельту, чтобы избежать скачков
            if (deltaTime > 0.05f) deltaTime = 0.05f;

            engine.update(deltaTime);
            repaint();
        }).start();

        addMouseListener(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        background.draw(g, getWidth(), getHeight());
        engine.draw(g);
    }

    // Обработчики мыши (можно будет реализовать позже)
    @Override
    public void mouseClicked(MouseEvent e) {
        // Здесь будет логика кликов
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public void draw() {
        repaint();
    }
}
