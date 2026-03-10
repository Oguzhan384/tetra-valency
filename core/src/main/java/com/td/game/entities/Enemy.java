package com.td.game.entities;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.td.game.utils.Constants;

public class Enemy implements Disposable {

    protected Vector3 position;
    protected float health;
    protected float maxHealth;
    protected float speed;
    protected float baseSpeed;
    protected int reward;
    protected boolean alive;
    protected boolean reachedEnd;

    protected Array<Vector3> waypoints;
    protected int currentWaypointIndex;
    protected float rotation;

    public Enemy(float maxHealth, float speed, int reward) {
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        float scaleMultiplier = Constants.TILE_SIZE / 2.0f;
        this.baseSpeed = speed * scaleMultiplier;
        this.speed = speed * scaleMultiplier;
        this.reward = reward;
        this.alive = true;
        this.reachedEnd = false;
        this.currentWaypointIndex = 0;
        this.position = new Vector3();
    }

    public void setWaypoints(Array<Vector3> waypoints) {
        this.waypoints = waypoints;
        if (waypoints.size > 0) {
            this.position.set(waypoints.first());
            this.currentWaypointIndex = 0;
        }
    }

    public void update(float deltaTime) {
        if (!alive || waypoints == null || waypoints.size == 0)
            return;

        if (currentWaypointIndex < waypoints.size) {
            Vector3 target = waypoints.get(currentWaypointIndex);
            Vector3 direction = target.cpy().sub(position).nor();

            if (direction.len2() > 0.01f) {
                float targetRotation = (float) Math.toDegrees(Math.atan2(-direction.x, -direction.z));
                
                float diff = targetRotation - this.rotation;
                while (diff > 180)
                    diff -= 360;
                while (diff < -180)
                    diff += 360;
                this.rotation += diff * 12f * deltaTime;
            }

            position.add(direction.scl(speed * deltaTime));

            if (position.dst(target) < 0.2f) {
                currentWaypointIndex++;

                if (currentWaypointIndex >= waypoints.size) {
                    reachedEnd = true;
                    alive = false;
                }
            }
        }
    }

    public void takeDamage(float damage) {
        health -= damage;
        if (health <= 0) {
            health = 0;
            alive = false;
        }
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean hasReachedEnd() {
        return reachedEnd;
    }

    public Vector3 getPosition() {
        return position;
    }

    public int getReward() {
        return reward;
    }

    public float getHealth() {
        return health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public float getHealthPercent() {
        return health / maxHealth;
    }

    public String getName() {
        return "Enemy";
    }

    @Override
    public void dispose() {
    }
}
