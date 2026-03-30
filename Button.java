import javax.swing.*;
import java.awt.*;

public class Button extends JButton {
    private boolean isOnCooldown = false;
    private Timer cooldownTimer;
    private long cooldownDuration;
    private long cooldownStartTime;
    private Event originalEvent;
    private float fillHeight = 0;
    private long cooldownSeconds = 0;

    public Button(String text, Icon icon, Event event) {
        this(text, icon, event, 0); // 0 = без перезарядки
    }

    public Button(String text, Icon icon, Event event, long cooldownSeconds) {
        super(text, icon);
        this.originalEvent = event;
        this.cooldownSeconds = cooldownSeconds;
        this.cooldownDuration = cooldownSeconds * 1000;

        setVerticalTextPosition(SwingConstants.BOTTOM);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setVerticalAlignment(SwingConstants.TOP);
        setHorizontalAlignment(SwingConstants.CENTER);
        setMargin(new Insets(5, 5, 5, 5));

        addActionListener(e -> {
            if (cooldownSeconds <= 0) {
                // Без перезарядки
                if (originalEvent != null) {
                    originalEvent.action();
                }
            } else {
                // С перезарядкой
                if (!isOnCooldown && originalEvent != null) {
                    originalEvent.action();
                    startCooldown();
                }
            }
        });
    }

    private void startCooldown() {
        isOnCooldown = true;
        cooldownStartTime = System.currentTimeMillis();
        setEnabled(false);

        cooldownTimer = new Timer(16, e -> {
            long elapsed = System.currentTimeMillis() - cooldownStartTime;

            if (elapsed >= cooldownDuration) {
                cooldownTimer.stop();
                isOnCooldown = false;
                setEnabled(true);
                repaint();
            } else {
                fillHeight = (float) elapsed / cooldownDuration;
                repaint();
            }
        });
        cooldownTimer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Анимация только если есть перезарядка и она активна
        if (cooldownSeconds > 0 && isOnCooldown) {
            Graphics2D g2d = (Graphics2D) g.create();

            int w = getWidth();
            int h = getHeight();

            g2d.setColor(new Color(0, 0, 0, 100));
            g2d.fillRect(0, 0, w, h);

            int squareSize = Math.min(w, h);
            int squareX = (w - squareSize) / 2;
            int squareY = (h - squareSize) / 2;

            g2d.setColor(Color.WHITE);
            g2d.drawRect(squareX, squareY, squareSize, squareSize);

            int fillHeightPixels = (int)(squareSize * fillHeight);
            g2d.setColor(new Color(255, 255, 255, 139));
            g2d.fillRect(squareX, squareY, squareSize, fillHeightPixels);

            g2d.dispose();
        }
    }
}
