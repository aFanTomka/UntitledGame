import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

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
        System.out.println("mouseClicked at: " + e.getX() + ", " + e.getY());

        // Получаем координаты клика
        float clickX = e.getX();
        float clickY = e.getY();

        // Ищем всех живых лучников на сцене
        List<GameObject> objects = engine.getObjects();
        UnitArcher nearestArcher = null;
        float minDist = Float.MAX_VALUE;

        for (GameObject obj : objects) {
            if (obj instanceof UnitArcher && obj.isAlive()) {
                UnitArcher archer = (UnitArcher) obj;
                float dist = archer.distanceTo(new GameObject(clickX, clickY, 0));
                if (dist < minDist) {
                    minDist = dist;
                    nearestArcher = archer;
                }
            }
        }

        // Если лучник найден — стреляем в точку клика
        if (nearestArcher != null) {
            // Проверяем кулдаун
            if (nearestArcher.canAttack(engine.getGameTime()) || true) { // force shoot
                float angle = Arrow.calculateArrowAngle(
                        nearestArcher.getX(),
                        nearestArcher.getY(),
                        clickX,
                        clickY,
                        600 // скорость стрелы
                );
                Arrow arrow = new Arrow(
                        nearestArcher.getX(),
                        nearestArcher.getY(),
                        angle,
                        600
                );
                arrow.setAttackDamage(nearestArcher.getAttackDamage() > 0 ? nearestArcher.getAttackDamage() : 10);
                arrow.setFraction(nearestArcher.getFraction());
                engine.spawnObject(arrow);
                nearestArcher.setLastAttackTime(engine.getGameTime()); // сброс кулдауна
                System.out.println("Archer shot at: " + clickX + ", " + clickY);
            }
        }

        // Проверка клика по башне
        for (GameObject obj : objects) {
            if (obj instanceof Tower && obj.isAlive()) {
                // Башня рисуется с центром в (x/2, y) и шириной ~120, высотой 300
                float towerCenterX = obj.getX() / 2;
                float towerBottomY = obj.getY();

                // Проверка прямоугольной области
                if (clickX >= towerCenterX - 60 && clickX <= towerCenterX + 60 &&
                        clickY >= towerBottomY - 300 && clickY <= towerBottomY) {

                    obj.setAlive(false);  // Уничтожаем башню
                    System.out.println("Tower destroyed at: " + obj.getX() + ", " + obj.getY());
                    break;
                }
            }
        }
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
