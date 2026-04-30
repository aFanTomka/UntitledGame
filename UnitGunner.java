import java.awt.*;
import java.util.List;

public class UnitGunner extends GameObject {

    // Константы для значений по умолчанию (используются только если параметры не переданы)
    private static final int DEFAULT_ATTACK_RANGE = 200;
    private static final int DEFAULT_DAMAGE = 15;
    private static final int DEFAULT_ATTACK_COOLDOWN_MS = 1000;
    private static final float DEFAULT_MOVE_SPEED = 80f;
    private static final int DEFAULT_MAX_HEALTH = 50;
    private static final int Y_TOLERANCE = 50; // Допустимая разница по Y для атаки

    // Параметры юнита
    private int attackRange;
    private int damage;
    private int attackCooldownMs;
    private float moveSpeed;
    private int maxHealth;

    private GameObject target;
    private long lastAttackTime;
    private transient Engine engine;

    public UnitGunner() {
        super(0, 0, 0, DEFAULT_MAX_HEALTH, 0, null);
        this.id = -1;
        this.attackRange = DEFAULT_ATTACK_RANGE;
        this.damage = DEFAULT_DAMAGE;
        this.attackCooldownMs = DEFAULT_ATTACK_COOLDOWN_MS;
        this.moveSpeed = DEFAULT_MOVE_SPEED;
        this.maxHealth = DEFAULT_MAX_HEALTH;
        this.isAlive = true;
        this.fraction = 1;
        this.lastAttackTime = 0;
        // Спавн у правого края (x = 750, ширина окна 800)
        this.x = 750;
        this.y = 300;
        // Здоровье устанавливается только через super, currentHealth не используется
    }

    public UnitGunner(int id, float x, float y, int size, float speed,
                      int attackRange, int damage, int attackCooldownMs, 
                      float moveSpeed, int maxHealth) {
        super(id, x, y, size, maxHealth, null);
        this.attackRange = attackRange;
        this.damage = damage;
        this.attackCooldownMs = attackCooldownMs;
        this.moveSpeed = moveSpeed;
        this.maxHealth = maxHealth;
        this.isAlive = true;
        this.fraction = 1;
        this.lastAttackTime = 0;
        
        // Если x и y не заданы или равны 0 - спавним у правого края
        if (x <= 0) {
            this.x = 750;
        }
        if (y <= 0) {
            this.y = 300;
        }
    }

    @Override
    protected void update(float dt) {
        if (!isAlive) return;

        if (engine == null) {
            engine = Engine.getInstance();
            if (engine == null) return;
        }

        updateTargetAndMovement(dt);

        if (target != null && target.isAlive()) {
            float distanceToTarget = Math.abs(this.x - target.getX());
            float yDifference = Math.abs(this.y - target.getY());

            if (distanceToTarget <= attackRange && yDifference <= Y_TOLERANCE) {
                performAttack();
            }
        }

        if (getHealth() <= 0 || this.x > 850 || this.x < -100) {
            isAlive = false;
        }
    }

    // Обновляет цель и выполняет движение
     
    private void updateTargetAndMovement(float dt) {
        if (engine == null) return;

        List<GameObject> objects = engine.getObjects();
        if (objects == null) return;

        // Поиск ближайшей цели
        GameObject nearest = null;
        float minDistance = Float.MAX_VALUE;

        for (GameObject obj : objects) {
            if (obj == null) continue;
            if (!obj.isAlive()) continue;
            if (obj == this) continue;
            if (obj.getClass().getSimpleName().contains("Tower")) continue;
            if (obj.getClass().getSimpleName().equals("UnitGunner")) continue;

            float distance = Math.abs(obj.getX() - this.x);
            if (distance < minDistance) {
                minDistance = distance;
                nearest = obj;
            }
        }

        target = nearest;

        if (target != null && target.isAlive()) {
            // Движение к цели
            if (target.getX() > this.x) {
                this.x += moveSpeed * dt;
            } else {
                this.x -= moveSpeed * dt;
            }

            if (target.getY() > this.y) {
                this.y += moveSpeed * dt;
            } else if (target.getY() < this.y) {
                this.y -= moveSpeed * dt;
            }
        } else {
            // Если нет цели - идём влево
            this.x -= moveSpeed * dt;
        }
    }

     // Выполняет атаку по цели
    private void performAttack() {
        if (target == null || !target.isAlive()) return;

        long currentTime = System.currentTimeMillis();
        if (currentTime - lastAttackTime >= attackCooldownMs) {
            target.takeDamage(damage);
            lastAttackTime = currentTime;
            System.out.println("Ганнер АТАКУЕТ " + target.getClass().getSimpleName() +
                    "! Урон: " + damage);

            if (!target.isAlive()) {
                System.out.println("Убит " + target.getClass().getSimpleName());
                target = null;
            }
        }
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        System.out.println("Ганнер получил урон: " + damage + ", HP: " + getHealth());

        if (getHealth() <= 0) {
            this.isAlive = false;
            System.out.println("Ганнер уничтожен!");
        }
    }

    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    public void setEngine(Engine engine) { this.engine = engine; }

    @Override
    public float getX() { return x; }
    @Override
    public float getY() { return y; }

    // Спавн юнита у правого края экрана 
    // @param yPosition вертикальная позиция для спавна
    public void spawnAtRightEdge(float yPosition) {
        this.x = 750;
        this.y = yPosition;
        this.isAlive = true;
        setHealth(maxHealth);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int offsetX = (int) x;
        int offsetY = (int) y;

        // Тело
        g2.setColor(new Color(55, 120, 14));
        g2.fillOval(offsetX + 45, offsetY + 10, 30, 30);
        g2.setColor(Color.BLACK);
        g2.drawOval(offsetX + 45, offsetY + 10, 30, 30);

        // Лицо
        g2.setColor(new Color(255, 233, 214));
        g2.fillRoundRect(offsetX + 50, offsetY + 20, 16, 14, 4, 4);
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(offsetX + 50, offsetY + 20, 16, 14, 4, 4);

        // Глаза
        g2.fillOval(offsetX + 53, offsetY + 25, 2, 2);
        g2.fillOval(offsetX + 60, offsetY + 25, 2, 2);

        // Нижняя часть тела
        g2.setColor(new Color(55, 120, 14));
        g2.fillRoundRect(offsetX + 51, offsetY + 36, 22, 18, 5, 5);
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(offsetX + 51, offsetY + 36, 22, 18, 5, 5);

        // Оружие
        g2.setColor(new Color(60, 60, 70));
        g2.fillRoundRect(offsetX + 64, offsetY + 37, 16, 11, 2, 2);
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(offsetX + 64, offsetY + 37, 16, 11, 2, 2);

        // Ремень
        g2.setColor(new Color(85, 90, 100));
        g2.fillRoundRect(offsetX + 25, offsetY + 39, 40, 7, 2, 2);
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(offsetX + 25, offsetY + 39, 40, 7, 2, 2);

        // Детали
        g2.setColor(new Color(70, 70, 80));
        g2.fillRect(offsetX + 14, offsetY + 41, 11, 3);
        g2.setColor(Color.BLACK);
        g2.drawRect(offsetX + 14, offsetY + 41, 11, 3);

        g2.setColor(new Color(75, 75, 85));
        g2.fillRect(offsetX + 56, offsetY + 46, 5, 8);
        g2.setColor(Color.BLACK);
        g2.drawRect(offsetX + 56, offsetY + 46, 5, 8);

        g2.setColor(new Color(60, 60, 70));
        g2.fillRoundRect(offsetX + 41, offsetY + 46, 5, 7, 2, 2);
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(offsetX + 41, offsetY + 46, 5, 7, 2, 2);

        // Полоска здоровья
        g2.setColor(Color.RED);
        g2.fillRect(offsetX + 40, offsetY - 5, 50, 5);
        g2.setColor(Color.GREEN);
        int healthPercent = (int)((float)getHealth() / maxHealth * 50);
        g2.fillRect(offsetX + 40, offsetY - 5, healthPercent, 5);
        g2.setColor(Color.BLACK);
        g2.drawRect(offsetX + 40, offsetY - 5, 50, 5);

        // Линия прицеливания
        if (target != null && target.isAlive()) {
            g2.setColor(Color.RED);
            g2.drawLine(offsetX + 60, offsetY + 25, (int)target.getX(), (int)target.getY());
        }
    }
}