package juggermod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class TemporarySlowPower extends AbstractPower{
    public static final String POWER_ID = "Slowed";
    public static final String NAME = "Slowed";
    public static final String[] DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings("Slow").DESCRIPTIONS;

    public TemporarySlowPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "Slowed";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = ImageMaster.loadImage("images/powers/32/slow.png");
        this.type = AbstractPower.PowerType.DEBUFF;
    }

    @Override
    public void atEndOfRound() {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "Slowed"));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + FontHelper.colorString(this.owner.name, "y") + DESCRIPTIONS[1];
        if (this.amount != 0) {
            this.description = this.description + DESCRIPTIONS[2] + this.amount * 10 + DESCRIPTIONS[3];
        }
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new TemporarySlowPower(this.owner, 1), 1));
    }

    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * (1.0f + (float)this.amount * 0.1f);
        }
        return damage;
    }
}
