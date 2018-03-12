package juggermod.powers;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import juggermod.JuggerMod;

public class ReleaseRestraintPower extends AbstractPower{
    public static final String POWER_ID = "Release Restraint";
    public static final String NAME = "Release Restraint";
    public static final String[] DESCRIPTIONS = new String[]{
            "Gain ",
            "at the start of each turn."
    };

    public ReleaseRestraintPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "Release Restraint";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = JuggerMod.getReleaseRestraintPowerTexture();
    }

    @Override
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(DESCRIPTIONS[0]);
        for (int i = 0; i < this.amount; ++i) {
            sb.append("[R] ");
        }
        sb.append(DESCRIPTIONS[1]);
        this.description = sb.toString();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
    }

    @Override
    public void atStartOfTurn() {
        if (this.owner.isPlayer) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.amount));
            this.flash();
        }
    }
}
