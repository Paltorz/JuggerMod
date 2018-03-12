package juggermod.powers;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import juggermod.JuggerMod;

public class ManglePower extends AbstractPower{
    public static final String POWER_ID = "Mangled";
    public static final String NAME = "Mangled";
    public static final String[] DESCRIPTIONS = new String[]{
            "Lose ",
            " HP at the end of the turn."
    };

    public ManglePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "Mangled";
        this.owner = owner;
        this.amount = amount;
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        this.img = JuggerMod.getManglePowerTexture();
        this.type = AbstractPower.PowerType.DEBUFF;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(this.owner, null, this.amount));
    }
}
