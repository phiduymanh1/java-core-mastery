package org.example.java_core.p01_principlesO_of_oop.model;

import lombok.Getter;

public abstract class Robot {

    private final int id;
    @Getter
    private final String name;

    @Getter
    private int hp;
    private int energy;

    protected Robot(int id, String name, int hp, int energy) {
        this.id = id;
        this.name = name;
        this.hp = Math.max(hp,0);
        this.energy = Math.max(energy,0);
    }

    public boolean isAlive(){
        return hp > 0;
    }

    public void takeDamage(int damage){
        if (!isAlive()) return;
        if (damage <= 0) return;

        hp -= damage;
        if (hp < 0) hp = 0;
    }

    public boolean consumeEnergy(int amount){
        if (energy < amount) return false;
        energy -= amount;

        return true;
    }

    public abstract void attack(Robot target);
    public abstract void attackSkill(Robot target);

}
