package com.td.game.combat;

import com.badlogic.gdx.utils.Array;
import com.td.game.elements.Element;
import com.td.game.entities.Enemy;

public class FireAttack implements AttackAction {
    private final float baseDamage;
    private final float range;
    private final float attackSpeed;
    private final float radius;
    private final float rampingUp;
    private final float maxRamping;
    private Enemy lastTarget;
    private float currentRamping = 1f;

    public FireAttack(float baseDamage, float range, float attackSpeed, float radius, float rampingUp,
            float maxRamping) {
        this.baseDamage = baseDamage;
        this.range = range;
        this.attackSpeed = attackSpeed;
        this.radius = radius;
        this.rampingUp = rampingUp;
        this.maxRamping = maxRamping;
    }

    @Override
    public void attack(AttackContext context) {
        if (context == null || context.getTarget() == null) {
            return;
        }

        Enemy target = context.getTarget();
        if (target == lastTarget && target.isAlive()) {
            currentRamping = Math.min(maxRamping, currentRamping + rampingUp);
        } else {
            currentRamping = 1f;
        }
        lastTarget = target;

        Element attackerElement = context.getSource() != null ? context.getSource().getCurrentElement() : null;
        float damage = baseDamage * currentRamping;
        target.takeDamage(damage, attackerElement);

        if (radius <= 0f) {
            return;
        }

        Array<Enemy> enemies = context.getEnemiesInRange();
        if (enemies == null) {
            return;
        }

        for (Enemy enemy : enemies) {
            if (enemy == null || enemy == target || !enemy.isAlive()) {
                continue;
            }
            if (enemy.getPosition().dst(target.getPosition()) <= radius) {
                enemy.takeDamage(damage, attackerElement);
            }
        }
    }
}
