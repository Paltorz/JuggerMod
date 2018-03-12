package juggermod.powers;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import juggermod.JuggerMod;

public class BruteForcePower extends AbstractPower {
    public static final String POWER_ID = "Brute Force";
    public static final String NAME = "Brute Force";
    public static final String[] DESCRIPTIONS = new String[]{
            "Gain ",
            "at the start of each turn.",
            " Lose ",
            " HP at the end of your turn."
    };
    private int energyAmount;

    public BruteForcePower(AbstractCreature owner, int amount, int energy) {
        this.name = NAME;
        this.ID = "Brute Force";
        this.owner = owner;
        this.amount = amount;
        this.energyAmount = energy;
        this.updateDescription();
        this.img = JuggerMod.getBruteForcePowerTexture();
    }

    @Override
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(DESCRIPTIONS[0]);
        for (int i = 0; i < this.energyAmount; ++i) {
            sb.append("[R] ");
        }
        sb.append(DESCRIPTIONS[1]);
        sb.append(DESCRIPTIONS[2]);
        sb.append(this.amount);
        sb.append(DESCRIPTIONS[3]);
        this.description = sb.toString();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
        this.energyAmount += 1;
    }

    @Override
    public void atStartOfTurn() {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.energyAmount));
        this.flash();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, this.owner, this.amount));
        }
    }
}
