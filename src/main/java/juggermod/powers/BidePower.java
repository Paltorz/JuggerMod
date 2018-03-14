package juggermod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import juggermod.JuggerMod;

public class BidePower extends AbstractPower{
    public static final String POWER_ID = "Bide";
    public static final String NAME = "Bide";
    public static final String[] DESCRIPTIONS = new String[]{
            "At the start of your turn, deal damage to All enemies equal to the damage you receive this turn."
    };
    private int dmgTaken;

    public BidePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "Bide";
        this.owner = owner;
        this.amount = amount;
        this.dmgTaken = 0;
        this.updateDescription();
        this.img = JuggerMod.getBidePowerTexture();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        this.dmgTaken += info.output;
        return damageAmount;
    }

    @Override
    public void atStartOfTurn() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null,
                DamageInfo.createDamageMatrix(this.amount * dmgTaken, true),
                DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "Bide"));
    }
}
