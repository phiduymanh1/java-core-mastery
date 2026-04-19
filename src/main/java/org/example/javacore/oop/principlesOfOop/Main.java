package org.example.javacore.oop.principlesOfOop;

import org.example.javacore.oop.principlesOfOop.model.AssaultRobot;
import org.example.javacore.oop.principlesOfOop.model.DefenderRobot;
import org.example.javacore.oop.principlesOfOop.model.Robot;
import org.example.javacore.oop.principlesOfOop.service.BattleService;

public class Main {

    public static void main(String[] args) {
        Robot r1 = new AssaultRobot(1, "Assault");
        Robot r2 = new DefenderRobot(2, "Defender");

        BattleService battle = new BattleService();

        while (!battle.isBattleOver(r1, r2)) {
            battle.attack(r1, r2);
            battle.attack(r2, r1);

            System.out.println("HP: " + r1.getHp() + " | " + r2.getHp());
            System.out.println("------");
        }

        System.out.println("Battle ended!");
    }

}
