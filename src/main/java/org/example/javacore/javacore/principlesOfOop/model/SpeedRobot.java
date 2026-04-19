package org.example.javacore.javacore.principlesOfOop.model;

import java.util.Random;

public class SpeedRobot extends Robot {

    protected SpeedRobot(int id, String name) {
        super(id, name, 100, 70);
    }

    private final Random random = new Random();

    @Override
    public void takeDamage(int damage) {
        if (random.nextDouble() < 0.3) return;
        super.takeDamage(damage);
    }

    @Override
    public void attack(Robot target) {
        int damage = 10;
        target.takeDamage(damage);
    }

    @Override
    public void attackSkill(Robot target) {
        if (!consumeEnergy(10)) return;
        int damage = 30;
        target.takeDamage(damage);
    }
}
