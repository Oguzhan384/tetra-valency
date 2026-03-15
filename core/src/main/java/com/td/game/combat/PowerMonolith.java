package com.td.game.combat;

import com.badlogic.gdx.math.Vector3;
import com.td.game.pillars.Pillar;
import com.td.game.pillars.PillarType;
import com.td.game.utils.ModelFactory;

public class PowerMonolith extends Pillar implements AttackAction {
    private AttackAction delegate;

    public PowerMonolith(Vector3 position, ModelFactory modelFactory) {
        this(position, modelFactory, null);
    }

    public PowerMonolith(Vector3 position, ModelFactory modelFactory, AttackAction delegate) {
        super(PillarType.POWER, position, modelFactory);
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
