import javax.swing.*;
import java.awt.*;

public class Controls extends JPanel {
    public Controls(Engine game) {
        setLayout(new FlowLayout());

        add(new Button("Spawn Base", new GameObject() /* under construction */, () -> {
            System.out.println("spawn Base");
            // under construction
        }));

        add(new Button("Spawn Archer", new UnitArcher() , () -> {
            UnitArcher unitArcher = new UnitArcher();
            unitArcher.setFraction(0);
            unitArcher.setX(0);
            unitArcher.setAttackRange(250);
            unitArcher.setSize(50);
            unitArcher.setY(game.getScreenHeight() - 250);
            unitArcher.setSpeed(1);
            unitArcher.setDirection(1);
            game.spawnObject(unitArcher);
        }));

        add(new Button("Spawn Tank", new UnitDinoRider(), () -> {
            UnitDinoRider unitDinoRider = new UnitDinoRider();
            unitDinoRider.setFraction(0);
            unitDinoRider.setX(0);
            unitDinoRider.setY(game.getScreenHeight() - 250);
            unitDinoRider.setSize(100);
            unitDinoRider.setSpeed(1);
            unitDinoRider.setDirection(1);
            game.spawnObject(unitDinoRider);
        }));
    }
}
