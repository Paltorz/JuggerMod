package juggermod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import juggermod.JuggerMod;

public class ShatterPower extends AbstractPower {
    public static final String POWER_ID = "Shatter";
    public static final String NAME = "Shatter";
    public static final String[] DESCRIPTIONS = new String[]{
            "At the end of your turn, if you have at least ",
            " Plated Armor, lose ",
            " Plated Armor and deal ",
            " damage to All enemies."
    };
    private int plateLoss;

    public ShatterPower(AbstractCreature owner, int amount, int plateLoss) {
        this.name = NAME;
        this.ID = "Shatter";
        this.owner = owner;
        this.amount = amount;
        this.plateLoss = plateLoss;
        this.updateDescription();
        this.img = JuggerMod.getShatterPowerTexture();
    }

    private int GetPowerCount(AbstractCreature c, String powerId) {
        AbstractPower power =  c.getPower(powerId);
        return power != null ? power.amount : 0;
    }

    @Override
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(DESCRIPTIONS[0]);
        sb.append(plateLoss);
        sb.append(DESCRIPTIONS[1]);
        sb.append(plateLoss);
        sb.append(DESCRIPTIONS[2]);
        sb.append(this.amount);
        sb.append(DESCRIPTIONS[3]);
        this.description = sb.toString();
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
        this.plateLoss += 1;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            int plateCount = GetPowerCount(AbstractDungeon.player, "Plated Armor");
            if (plateCount >= plateLoss) {
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new PlatedArmorPower(this.owner, this.plateLoss * -1), this.plateLoss * -1));
                AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null,
                        DamageInfo.createDamageMatrix(this.amount, true),
                        DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
            }
        }
    }
}
