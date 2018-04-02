package juggermod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import juggermod.actions.unique.InertiaAction;
import juggermod.JuggerMod;

public class InertiaPower extends AbstractPower{
    public static final String POWER_ID = "Inertia";
    public static final String NAME = "Inertia";
    public static final String[] DESCRIPTIONS = new String[]{
            "At the end of your turn, you no longer discard your hand."
    };

    public InertiaPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "Inertia";
        this.owner = owner;
        this.amount = 0;
        this.updateDescription();
        this.img = JuggerMod.getInertiaPowerTexture();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    /*
    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            AbstractDungeon.actionManager.addToBottom(new InertiaAction());
        }
    }
    */
}
