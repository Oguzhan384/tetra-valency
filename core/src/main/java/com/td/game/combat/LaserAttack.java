package com.td.game.combat;

import com.td.game.elements.Element;
import com.td.game.entities.Enemy;

public class LaserAttack implements AttackAction {
    private final float baseDamage;
    private final float range;
    private final float attackSpeed;

    public LaserAttack(float baseDamage, float range, float attackSpeed) {
        this.baseDamage = baseDamage;
        this.range = range;
        this.attackSpeed = attackSpeed;
    }

    @Override
    public void attack(AttackContext context) {
        if (context == null || context.getTarget() == null) {
            return;
        }
        Enemy target = context.getTarget();
        Element attackerElement = context.getSource() != null ? context.getSource().getCurrentElement() : null;
        target.takeDamage(baseDamage, attackerElement);
    }
}
