package com.td.game.combat;

import com.td.game.elements.Element;
import com.td.game.entities.Enemy;

public class IceAttack implements AttackAction {
    public static final float FREEZE_DURATION = 2.0f;
    public static final float ATTACK_SPEED_MULTIPLIER = 0.65f;

    private final float baseDamage;
    private final float range;
    private final float attackSpeed;
    private final float freezeDuration;

    public IceAttack(float baseDamage, float range, float attackSpeed, float freezeDuration) {
        this.baseDamage = baseDamage;
        this.range = range;
        this.attackSpeed = attackSpeed;
        this.freezeDuration = freezeDuration;
    }

    @Override
    public void attack(AttackContext context) {
        if (context == null || context.getTarget() == null) {
            return;
        }
        Enemy target = context.getTarget();
        Element attackerElement = context.getSource() != null ? context.getSource().getCurrentElement() : null;
        target.takeDamage(baseDamage, attackerElement);
        applyFreeze(target, freezeDuration);
    }

    public static void applyOnHit(Enemy target, float impactDamage) {
        if (target == null || !target.isAlive()) {
            return;
        }

        target.takeDamage(impactDamage, Element.ICE);
        applyFreeze(target, FREEZE_DURATION);
    }

    public static void applyFreeze(Enemy target, float duration) {
        if (target == null || !target.isAlive() || duration <= 0f) {
            return;
        }
        target.applyFreeze(duration);
    }
}
