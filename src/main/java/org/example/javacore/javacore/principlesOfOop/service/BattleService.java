package org.example.javacore.javacore.principlesOfOop.service;

import org.example.javacore.javacore.principlesOfOop.model.Robot;

public class BattleService {

    public void attack(Robot attacker, Robot defender) {
        if (!attacker.isAlive()) {
            System.out.println(attacker.getName() + " is dead!");
            return;
        }

        attacker.attack(defender);
    }

    public void attackSkill(Robot attacker, Robot defender) {
        attacker.attackSkill(defender);
    }

    public boolean isBattleOver(Robot r1, Robot r2) {
        return !r1.isAlive() || !r2.isAlive();
    }
}
