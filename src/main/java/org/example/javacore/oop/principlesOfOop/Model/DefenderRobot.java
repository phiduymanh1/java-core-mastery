package org.example.javacore.oop.principlesOfOop.model;

public class DefenderRobot extends Robot {


    public DefenderRobot(int id, String name) {
        super(id, name, 120, 50);
    }

    @Override
    public void takeDamage(int damage) {
        int amount = damage/2;
        super.takeDamage(amount);
    }

    @Override
    public void attack(Robot target) {
        int damage = 10;
        target.takeDamage(damage);
    }

    @Override
    public void attackSkill(Robot target) {
        if (!consumeEnergy(20)) return;
        int damage = 30;
        target.takeDamage(damage);
    }
}
