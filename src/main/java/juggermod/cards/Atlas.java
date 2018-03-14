package juggermod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import juggermod.JuggerMod;
import juggermod.patches.AbstractCardEnum;
import juggermod.patches.OverflowCard;
import juggermod.powers.AtlasPower;

public class Atlas extends OverflowCard{
    public static final String ID = "Atlas";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int OVERFLOW_AMT = 1;
    private static final int POOL = 1;

    public Atlas() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.ATLAS), COST, DESCRIPTION,
                AbstractCard.CardType.POWER, AbstractCardEnum.COPPER,
                AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF, POOL);
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, OVERFLOW_AMT), OVERFLOW_AMT));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AtlasPower(p, 1), 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Atlas();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isOverflow = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}