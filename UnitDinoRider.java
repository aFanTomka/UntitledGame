import java.awt.*;

// create by 21099-vmode
// edit by vadhub

public class UnitDinoRider extends GameObject {

    public UnitDinoRider() {
    }

    public UnitDinoRider(int id, float x, float y, int size, float speed) {
        super(id, x, y, size, speed, Color.BLACK);
    }

    public void draw(Graphics g) {
        int x = (int) this.x;
        int y = (int) this.y;
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(0, 0, 0, 40));
        g2.fillOval(x - 25, y + 50, 150, 20);
        // dino back legs
        g2.setColor(new Color(100, 160, 100));
        g2.fillRoundRect(x + 17, y + 30, 18, 30, 10, 10);
        g2.fillRoundRect(x + 80, y + 30, 18, 30, 10, 10);
        // dino body
        g2.setColor(new Color(100, 180, 100));
        g2.fillRoundRect(x - 10, y - 10, 110, 55, 30, 50);
        // dino neck and head
        g2.fillRoundRect(x + 70, y - 60, 35, 75, 25, 45);
        g2.setColor(new Color(100, 160, 100));
        g2.fillRoundRect(x + 65, y - 80, 35, 45, 25, 35);
        g2.fillRoundRect(x + 85, y - 70, 35, 35, 25, 70);
        // dino front legs
        g2.fillRoundRect(x - 5, y + 30, 18, 30, 10, 10);
        g2.fillRoundRect(x + 60, y + 30, 18, 30, 10, 10);
        // eyes and nose
        g2.setColor(new Color(255, 255, 255));
        g2.fillOval(x + 77, y - 72, 10, 15);
        g2.fillOval(x + 90, y - 77, 10, 15);
        g2.setColor(new Color(0, 0, 0));
        g2.fillOval(x + 83, y - 67, 5, 7);
        g2.fillOval(x +96, y - 72, 5, 7);
        g2.setColor(new Color(40, 40, 40));
        g2.fillOval(x + 103, y - 62, 5, 7);
        g2.fillOval(x + 110, y - 65, 5, 7);
        // spearman
        g2.setColor(new Color(108, 29, 13));
        g2.fillRoundRect(x + 15, y - 40, 40, 50, 35, 75);
        g2.setColor(new Color(217, 142, 73));
        g2.fillOval(x + 17, y - 65, 35, 35);
        g2.setColor(new Color(0, 0, 0));
        g2.fillOval(x + 42, y - 57, 5, 10);
        g2.fillOval(x + 33, y - 55, 5, 10);
        // spear
        g2.setStroke(new BasicStroke(7.0F));
        g2.setColor(new Color(121, 67, 25));
        g2.drawLine(x - 20, y - 15, x + 100, y - 15);
        g2.setStroke(new BasicStroke(3.0F));
        g2.setColor(new Color(128, 121, 115));
        int[] xPoints = {x + 100, x + 120, x + 100};
        int[] yPoints = {y - 25, y - 15, y - 5};
        g2.fillPolygon(xPoints, yPoints, 3);

    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        this.x = x;
        this.y = y;
        draw(g);
    }
}
