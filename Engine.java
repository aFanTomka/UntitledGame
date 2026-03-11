import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Engine {

    private int screenWidth = 800;
    private int screenHeight = 600;
    private final List<GameObject> objects = new ArrayList<>();
    private final Random random = new Random();

    public Engine() {
       objects.add(new Tower(0, 600,350 , 50, 0));
       // objects.add(new GameObject(1, 0, 200, 30, 4, Color.BLUE));
       // objects.add(new GameObject(2, 0, 300, 60, 1, Color.GREEN));
    }

    public void update(float deltaTime) {
        for (GameObject object: objects) {
            object.update();
        }
    }

    public void draw(Graphics g) {
        for (GameObject object: objects) {
            object.draw(g);
        }
    }

    public void spawnObject() {
    }

    public void spawnObject(GameObject gameObject) {
        objects.add(gameObject);
    }

    public boolean collisionAABB(GameObject a, GameObject b) {
        return false;
    }

    public void spawnObjectPattern(List<GameObject> pattern, long delay) {
        // создаёт новый поток
        Thread spawnThread = new Thread(() -> {
            for (int i = 0; i < pattern.size(); i++) {
                GameObject elem = pattern.get(i);

                // копия объекта создаётся
                GameObject newObject = new GameObject(
                        -1,
                    elem.getX(),
                    elem.getY(),
                    100,
                    elem.getSpeed()
                );

                // она добавляется в список
                synchronized (objects) {
                    objects.add(newObject);
                    System.out.println("Объект " + newObject.getId() + " заспавнен");
                }

                // задержка (очередь)
                if (i < pattern.size() - 1) {
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        System.out.println("Спавн прерван!");
                        break;
                    }
                }
            }
            System.out.println("Общий спавн завершен");
        });
        
        spawnThread.start(); // запуск потока
    }

    // supplier of Pythagoras
    public void moveTowards(GameObject attaker, GameObject target) {
    }

    public List<GameObject> getObjects() {
        return new ArrayList<>(objects);
    }

    public void clearObjects() {
        objects.clear();
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }
}
