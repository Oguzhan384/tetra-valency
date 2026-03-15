package com.td.game.combat;

import com.badlogic.gdx.math.Vector3;
import com.td.game.pillars.Pillar;
import com.td.game.pillars.PillarType;
import com.td.game.utils.ModelFactory;

public class SniperMonolith extends Pillar implements AttackAction {
    private AttackAction delegate;

    public SniperMonolith(Vector3 position, ModelFactory modelFactory) {
        this(position, modelFactory, null);
    }

    public SniperMonolith(Vector3 position, ModelFactory modelFactory, AttackAction delegate) {
        super(PillarType.SNIPER, position, modelFactory);
        this.delegate = delegate;
    }

    public void setAttackAction(AttackAction delegate) {
        this.delegate = delegate;
    }

    @Override
    public void attack(AttackContext context) {
        if (delegate != null) {
            delegate.attack(context);
        }
    }
}
