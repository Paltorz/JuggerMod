package juggermod.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import juggermod.JuggerMod;

public class CombatTrainingPower extends AbstractPower{
    public static final String POWER_ID = "Combat Training";
    public static final String NAME = "Combat Training";
    public static final String[] DESCRIPTIONS = new String[]{
            "Every turn, the first ",
            " Upgradable cards played are Upgraded after they enter the discard pile."
    };

    private int upgradeCount;

    public CombatTrainingPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "Combat Training";
        this.owner = owner;
        this.amount = amount;
        this.upgradeCount = 0;
        this.updateDescription();
        this.img = JuggerMod.getCombatTrainingPowerTexture();
    }

    @Override
    public void onAfterCardPlayed(AbstractCard card) {
        if (this.upgradeCount < this.amount) {
            if (card.canUpgrade() == true  && card.type != AbstractCard.CardType.POWER) {
                this.flash();
                card.upgrade();
                ++this.upgradeCount;
            }
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.upgradeCount = 0;
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
