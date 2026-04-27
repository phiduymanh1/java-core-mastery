package org.example.java_core.p01_principlesO_of_oop.model;

public class AssaultRobot extends Robot {

    public AssaultRobot(int id, String name) {
        super(id,name, 100,50);
    }

    @Override
    public void attack(Robot target) {
        int damage = 30;
        target.takeDamage(damage);
    }

    @Override
    public void attackSkill(Robot target) {
        if (!consumeEnergy(20)) return;
        int damage = 60;
        target.takeDamage(damage);
    }
}
