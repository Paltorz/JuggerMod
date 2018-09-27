package juggermod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.DiscardPileToTopOfDeckAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import juggermod.JuggerMod;
import juggermod.actions.common.ModifyMagicNumberAction;
import juggermod.patches.AbstractCardEnum;
import juggermod.patches.OverflowCard;

public class SlowAndSteady extends OverflowCard{
    public static final String ID = "Slow and Steady";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final int OVERFLOW_DEX = -1;
    private static final int OVERFLOW_AMT = 5;
    private static final int CARDS_TOP = 1;
    private static final int CARDS_TOP_UP = 2;

    public SlowAndSteady() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.SLOW_AND_STEADY), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.COPPER, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = OVERFLOW_AMT;
        this.isOverflow = true;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        int dexCount = GetPowerCount(p, "Dexterity");
        if (!super.canUse(p, m)) {
            return false;
        }
        if(dexCount < 0) {
            return true;
        }
        this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        if (this.magicNumber > 0) {
            AbstractDungeon.actionManager.addToBottom(new ModifyMagicNumberAction(this, -1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, OVERFLOW_DEX), OVERFLOW_DEX));
            if (this.magicNumber == 1) {
                this.isOverflow = false;
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int cardsTop;
        if (this.upgraded) {
            cardsTop = CARDS_TOP_UP;
        }else{
            cardsTop = CARDS_TOP;
        }
        int dexCount = GetPowerCount(p, "Dexterity");
        if (dexCount < 0) {
            for (int i = 0; i < cardsTop; i++) {
                AbstractDungeon.actionManager.addToBottom(new DiscardPileToTopOfDeckAction(p));
            }
        }
    }

    private int GetPowerCount(AbstractCreature c, String powerId) {
        AbstractPower power =  c.getPower(powerId);
        return power != null ? power.amount : 0;
    }

    @Override
    public AbstractCard makeCopy() {
        return new SlowAndSteady();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
