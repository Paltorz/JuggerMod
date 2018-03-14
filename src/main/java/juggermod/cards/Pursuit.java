package juggermod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import juggermod.JuggerMod;
import juggermod.actions.unique.PursuitAction;
import juggermod.patches.AbstractCardEnum;
import juggermod.patches.OverflowCard;

public class Pursuit extends OverflowCard{
    public static final String ID = "Pursuit";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK = 3;
    private static final int POOL = 1;

    public Pursuit() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.PURSUIT), COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.COPPER, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF_AND_ENEMY, POOL);
        this.baseBlock = BLOCK;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.block));
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new PursuitAction(m));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Pursuit();
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
