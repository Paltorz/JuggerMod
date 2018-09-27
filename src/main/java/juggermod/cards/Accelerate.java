package juggermod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import juggermod.JuggerMod;
import juggermod.actions.common.ModifyMagicNumberAction;
import juggermod.patches.AbstractCardEnum;
import juggermod.patches.OverflowCard;

public class Accelerate extends OverflowCard{
    private static final String ID = "Accelerate";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int DEX_GAIN_AMT = 1;
    private static final int OVERFLOW_DRAW = 1;
    private static final int OVERFLOW_AMT = 2;
    private static final int BLOCK_AMT = 1;
    private static final int DRAW = 2;

    public Accelerate() {
        super (ID, NAME, JuggerMod.makePath(JuggerMod.ACCELERATE), COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.COPPER,
                AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = OVERFLOW_AMT;
        this.baseBlock = BLOCK_AMT;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        if (this.upgraded) {
            if (this.magicNumber > 0) {
                AbstractDungeon.actionManager.addToBottom(new ModifyMagicNumberAction(this, -1));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, OVERFLOW_DRAW), OVERFLOW_DRAW));
                if (this.magicNumber == 1) {
                    this.isOverflow = false;
                }
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, DRAW));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, DEX_GAIN_AMT), DEX_GAIN_AMT));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Accelerate();
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
