package juggermod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import juggermod.JuggerMod;

public class NaturalEndurancePower extends AbstractPower{
    public static final String POWER_ID = "Natural Endurance";
    public static final String NAME = "Natural Endurance";
    public static final String[] DESCRIPTIONS = new String[]{
            "At the end of your turn gain Block equal to ",
                    " times your Strength."
    };

    public NaturalEndurancePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "Natural Endurance";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = JuggerMod.getNaturalEndurancePowerTexture();
    }

    private int GetPowerCount(AbstractCreature c, String powerId) {
        AbstractPower power =  c.getPower(powerId);
        return power != null ? power.amount : 0;
    }

    @Override
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(DESCRIPTIONS[0]);
        sb.append(this.amount);
        sb.append(DESCRIPTIONS[1]);
        this.description = sb.toString();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            int strengthCount = GetPowerCount(AbstractDungeon.player, "Strength");
            if (strengthCount > 0) {
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, strengthCount * this.amount));
            }
        }
    }
}
