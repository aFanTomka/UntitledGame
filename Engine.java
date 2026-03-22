import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Engine {

    private float gameTime = 0;
    private float deltaTime = 0;
    private int screenWidth = 800;
    private int screenHeight = 600;
    private final List<GameObject> objects = new ArrayList<>();
    private final Random random = new Random();
    private static Engine engine = null;

    private Engine() {
    }

    public static Engine getInstance() {
        if (engine == null) {
            engine = new Engine();
        }
        return engine;
    }

    public void update(float deltaTime) {
        this.deltaTime = deltaTime;
        gameTime += deltaTime;
        for (GameObject object: objects) {
            object.update(deltaTime);
        }

        objects.removeIf(obj -> !obj.isAlive());
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
        float halfA = a.getSize() / 2f;
        float halfB = b.getSize() / 2f;

        float leftA = a.getX() - halfA;
        float rightA = a.getX() + halfA;
        float topA = a.getY() - halfA;
        float bottomA = a.getY() + halfA;

        float leftB = b.getX() - halfB;
        float rightB = b.getX() + halfB;
        float topB = b.getY() - halfB;
        float bottomB = b.getY() + halfB;

        return rightA > leftB && leftA < rightB && bottomA > topB && topA < bottomB;
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

    public List<GameObject> getEnemiesFor(int faction) {
        return List.of();
    }

    public GameObject findNearestEnemy(GameObject self, float range) {
        if (self == null) return null;
        GameObject nearest = null;
        float rangeSq = range * range;
        for (GameObject obj : objects) {
            if (obj != self && obj.isAlive() && obj.getFraction() != self.getFraction()) {
                float distSq = self.distanceSqTo(obj);
                System.out.println(obj + " " + distSq + " < " + rangeSq);
                if (distSq < rangeSq) {
                    rangeSq = distSq;
                    nearest = obj;
                }
            }
        }
        return nearest;
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

    public float getGameTime() { return gameTime; }
}
