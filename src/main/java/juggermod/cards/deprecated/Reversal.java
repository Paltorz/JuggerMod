package juggermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import juggermod.JuggerMod;
import juggermod.patches.AbstractCardEnum;

public class Reversal extends CustomCard{
    public static final String ID = "Reversal";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;
    private static final int STR_GAIN = 2;
    private static final int STR_GAIN_DOWN = -1;
    private static final int POOL = 1;

    public Reversal() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.REVERSAL), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.COPPER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY, POOL);
        this.magicNumber = this.baseMagicNumber = STR_GAIN;
        this.exhaust = true;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.magicNumber), this.magicNumber));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int strengthCount = GetPowerCount(m, "Strength");
        if (strengthCount > 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, "Strength"));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(AbstractDungeon.player, strengthCount), strengthCount));
        }
    }

    private int GetPowerCount(AbstractCreature c, String powerId) {
        AbstractPower power =  c.getPower(powerId);
        return power != null ? power.amount : 0;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Reversal();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(STR_GAIN_DOWN);
        }
    }
}
