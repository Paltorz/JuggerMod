package juggermod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import juggermod.JuggerMod;
import juggermod.patches.OverflowCard;

public class PlateBalancePower extends AbstractPower{
    public static final String POWER_ID = "Foolhardy";
    public static final String NAME = "Foolhardy";
    public static final double LOSS = .2;
    public static final String[] DESCRIPTIONS = new String[]{
            "At the start of your turn, if your Plated Armor is 5 or more, lose 1/5 of your Plated Armor."
    };

    public PlateBalancePower() {
        this.name = NAME;
        this.ID = "Foolhardy";
        this.owner = AbstractDungeon.player;
        this.amount = -1;
        this.description = DESCRIPTIONS[0];
        this.updateDescription();
        this.img = JuggerMod.getFoolhardyPowerTexture();
    }

    private int GetPowerCount(AbstractCreature c, String powerId) {
        AbstractPower power =  c.getPower(powerId);
        return power != null ? power.amount : 0;
    }

    @Override
    public void atStartOfTurn() {
        int plateCount = GetPowerCount(AbstractDungeon.player, "Plated Armor");
        if (plateCount > 4) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "Plated Armor", (int)(plateCount * .2)));
        }
    }
}
