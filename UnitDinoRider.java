import java.awt.*;

// create by 21099-vmode
// edit by vadhub
// edit by Qwen && Deepseek

public class UnitDinoRider extends GameObject {

    public UnitDinoRider() {
    }

    public UnitDinoRider(int id, float x, float y, int size, float speed) {
        super(id, x, y, size, speed, Color.BLACK);
    }

    public void draw(Graphics g) {
        int x = (int) this.x;
        int y = (int) this.y;
        // масштабный коэффициент: базовый размер принят за 100
        float k = this.size / 100.0f;
        if (k <= 0) k = 1.0f; // защита от нулевого размера

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // тень
        g2.setColor(new Color(0, 0, 0, 40));
        g2.fillOval(Math.round(x - 25 * k), Math.round(y + 50 * k),
                Math.round(150 * k), Math.round(20 * k));

        // задние ноги динозавра
        g2.setColor(new Color(100, 160, 100));
        g2.fillRoundRect(Math.round(x + 17 * k), Math.round(y + 30 * k),
                Math.round(18 * k), Math.round(30 * k),
                Math.round(10 * k), Math.round(10 * k));
        g2.fillRoundRect(Math.round(x + 80 * k), Math.round(y + 30 * k),
                Math.round(18 * k), Math.round(30 * k),
                Math.round(10 * k), Math.round(10 * k));

        // тело динозавра
        g2.setColor(new Color(100, 180, 100));
        g2.fillRoundRect(Math.round(x - 10 * k), Math.round(y - 10 * k),
                Math.round(110 * k), Math.round(55 * k),
                Math.round(30 * k), Math.round(50 * k));

        // шея и голова динозавра
        g2.fillRoundRect(Math.round(x + 70 * k), Math.round(y - 60 * k),
                Math.round(35 * k), Math.round(75 * k),
                Math.round(25 * k), Math.round(45 * k));
        g2.setColor(new Color(100, 160, 100));
        g2.fillRoundRect(Math.round(x + 65 * k), Math.round(y - 80 * k),
                Math.round(35 * k), Math.round(45 * k),
                Math.round(25 * k), Math.round(35 * k));
        g2.fillRoundRect(Math.round(x + 85 * k), Math.round(y - 70 * k),
                Math.round(35 * k), Math.round(35 * k),
                Math.round(25 * k), Math.round(70 * k));

        // передние ноги динозавра
        g2.fillRoundRect(Math.round(x - 5 * k), Math.round(y + 30 * k),
                Math.round(18 * k), Math.round(30 * k),
                Math.round(10 * k), Math.round(10 * k));
        g2.fillRoundRect(Math.round(x + 60 * k), Math.round(y + 30 * k),
                Math.round(18 * k), Math.round(30 * k),
                Math.round(10 * k), Math.round(10 * k));

        // глаза и нос динозавра
        g2.setColor(new Color(255, 255, 255));
        g2.fillOval(Math.round(x + 77 * k), Math.round(y - 72 * k),
                Math.round(10 * k), Math.round(15 * k));
        g2.fillOval(Math.round(x + 90 * k), Math.round(y - 77 * k),
                Math.round(10 * k), Math.round(15 * k));
        g2.setColor(new Color(0, 0, 0));
        g2.fillOval(Math.round(x + 83 * k), Math.round(y - 67 * k),
                Math.round(5 * k), Math.round(7 * k));
        g2.fillOval(Math.round(x + 96 * k), Math.round(y - 72 * k),
                Math.round(5 * k), Math.round(7 * k));
        g2.setColor(new Color(40, 40, 40));
        g2.fillOval(Math.round(x + 103 * k), Math.round(y - 62 * k),
                Math.round(5 * k), Math.round(7 * k));
        g2.fillOval(Math.round(x + 110 * k), Math.round(y - 65 * k),
                Math.round(5 * k), Math.round(7 * k));

        // всадник с копьём
        g2.setColor(new Color(108, 29, 13));
        g2.fillRoundRect(Math.round(x + 15 * k), Math.round(y - 40 * k),
                Math.round(40 * k), Math.round(50 * k),
                Math.round(35 * k), Math.round(75 * k));
        g2.setColor(new Color(217, 142, 73));
        g2.fillOval(Math.round(x + 17 * k), Math.round(y - 65 * k),
                Math.round(35 * k), Math.round(35 * k));
        g2.setColor(new Color(0, 0, 0));
        g2.fillOval(Math.round(x + 42 * k), Math.round(y - 57 * k),
                Math.round(5 * k), Math.round(10 * k));
        g2.fillOval(Math.round(x + 33 * k), Math.round(y - 55 * k),
                Math.round(5 * k), Math.round(10 * k));

        // копьё
        g2.setStroke(new BasicStroke(7.0f * k));
        g2.setColor(new Color(121, 67, 25));
        g2.drawLine(Math.round(x - 20 * k), Math.round(y - 15 * k),
                Math.round(x + 100 * k), Math.round(y - 15 * k));
        g2.setStroke(new BasicStroke(3.0f * k));
        g2.setColor(new Color(128, 121, 115));
        int[] xPoints = {
                Math.round(x + 100 * k),
                Math.round(x + 120 * k),
                Math.round(x + 100 * k)
        };
        int[] yPoints = {
                Math.round(y - 25 * k),
                Math.round(y - 15 * k),
                Math.round(y - 5 * k)
        };
        g2.fillPolygon(xPoints, yPoints, 3);
    }
    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        this.x = x;
        this.y = y + 40;
        draw(g);
    }
}
