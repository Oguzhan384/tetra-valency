package com.td.game.combat;

public class LifeAttack implements AttackAction {
    private final float range;
    private final float attackSpeed;

    public LifeAttack(float range, float attackSpeed) {
        this.range = range;
        this.attackSpeed = attackSpeed;
    }

    @Override
    public void attack(AttackContext context) {
        if (context == null) {
            return;
        }
        // No direct damage here. Ally revival requires a dedicated ally system, which isn't present yet.
    }
}
