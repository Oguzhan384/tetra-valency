package com.td.game.combat;

import com.badlogic.gdx.utils.Array;
import com.td.game.entities.Enemy;
import com.td.game.pillars.Pillar;

public class AttackContext {
    private final Pillar source;
    private final Enemy target;
    private final Array<Enemy> enemiesInRange;
    private final float deltaTime;

    public AttackContext(Pillar source, Enemy target, Array<Enemy> enemiesInRange, float deltaTime) {
        this.source = source;
        this.target = target;
        this.enemiesInRange = enemiesInRange;
        this.deltaTime = deltaTime;
    }

    public Pillar getSource() {
        return source;
    }

    public Enemy getTarget() {
        return target;
    }

    public Array<Enemy> getEnemiesInRange() {
        return enemiesInRange;
    }

    public float getDeltaTime() {
        return deltaTime;
    }
}
