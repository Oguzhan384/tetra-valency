package com.td.game.combat;

import com.td.game.elements.Element;
import com.td.game.entities.Enemy;

public class EarthAttack implements AttackAction {
    private final float baseDamage;
    private final float range;
    private final float attackSpeed;
    private final float stunDuration;

    public EarthAttack(float baseDamage, float range, float attackSpeed, float stunDuration) {
        this.baseDamage = baseDamage;
        this.range = range;
        this.attackSpeed = attackSpeed;
        this.stunDuration = stunDuration;
    }

    @Override
    public void attack(AttackContext context) {
        if (context == null || context.getTarget() == null) {
            return;
        }
        Enemy target = context.getTarget();
        Element attackerElement = context.getSource() != null ? context.getSource().getCurrentElement() : null;
        target.takeDamage(baseDamage, attackerElement);
        if (stunDuration > 0f) {
            target.applyRoot(stunDuration, 0f, 0f);
        }
    }
}
