package juggermod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import juggermod.JuggerMod;
import juggermod.patches.OverflowCard;

public class OverflowingPlatePower extends AbstractPower{
    public static final String POWER_ID = "Overflowing Plate";
    public static final String NAME = "Overflowing Plate";
    public static final String[] DESCRIPTIONS = new String[]{
            "At the end of your turn, gain ",
            " Plated-Armor for each Overflow Card in your hand."
    };

    public OverflowingPlatePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "Overflowing Plate";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = JuggerMod.getOverflowingPlatePowerTexture();
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
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c == null) continue;
                if(c instanceof OverflowCard) {
                    if (((OverflowCard) c).isOverflow) {
                        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new PlatedArmorPower(this.owner, this.amount), this.amount));
                    }
                }
            }
        }
    }
}
