package juggermod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import juggermod.JuggerMod;
import juggermod.patches.OverflowCard;

public class OverflowingBlockPower extends AbstractPower{
    public static final String POWER_ID = "Overflowing Block";
    public static final String NAME = "Overflowing Block";
    public static final String[] DESCRIPTIONS = new String[]{
            "At the end of your turn, gain ",
            " Block for each Overflow Card in your hand."
    };

    public OverflowingBlockPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "Overflowing Block";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = JuggerMod.getOverflowingBlockPowerTexture();
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
                        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, this.amount));
                    }
                }
            }
        }
    }
}
