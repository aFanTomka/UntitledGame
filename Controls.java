import javax.swing.*;
import java.awt.*;

public class Controls extends JPanel {

    public Controls(Engine engine) {
        setLayout(new FlowLayout());

        add(new Button("Spawn Base", new GameObject(), () -> {
            System.out.println("spawn Base");
            // under construction
        }));

        add(new Button("Spawn Archer", new UnitArcher(), () -> {
            UnitArcher archer = new UnitArcher(1, 50, 800, 50, 500);
            archer.setFraction(1);
            archer.setAttackRange(100);
            archer.setAttackDamage(0);
            engine.spawnObject(archer);
            System.out.println("Archer spawned - will stop and stand");
        }, 90));

        add(new Button("Spawn Tank", new UnitDinoRider(), () -> {
            // under construction
        }, 90));
    }
}
