package com.td.game.combat;

import com.td.game.elements.Element;
import com.td.game.entities.Enemy;

public class AirAttack implements AttackAction {
    private final float baseDamage;
    private final float range;
    private final float attackSpeed;
    private final float knockback;

    public AirAttack(float baseDamage, float range, float attackSpeed, float knockback) {
        this.baseDamage = baseDamage;
        this.range = range;
        this.attackSpeed = attackSpeed;
        this.knockback = knockback;
    }

    @Override
    public void attack(AttackContext context) {
        if (context == null || context.getTarget() == null) {
            return;
        }
        Enemy target = context.getTarget();
        Element attackerElement = context.getSource() != null ? context.getSource().getCurrentElement() : null;
        target.takeDamage(baseDamage, attackerElement);
        if (knockback > 0f) {
            target.applyKnockback(knockback);
        }
    }
}
