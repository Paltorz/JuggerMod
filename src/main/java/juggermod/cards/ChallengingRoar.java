package juggermod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import juggermod.JuggerMod;
import juggermod.actions.common.ModifyMagicNumberAction;
import juggermod.patches.AbstractCardEnum;
import juggermod.patches.OverflowCard;

public class ChallengingRoar extends OverflowCard{
    public static final String ID = "Challenging Roar";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final int POOL = 1;
    private static final int WEAK_AMOUNT = 1;
    private static final int VULNERABLE_AMOUNT = 2;
    private static final int OVERFLOW_AMT = 2;

    public ChallengingRoar() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.CHALLENGING_ROAR), COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.COPPER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY, POOL);
        this.baseMagicNumber = this.magicNumber = OVERFLOW_AMT;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        if (this.upgraded) {
            if (this.magicNumber > 0) {
                AbstractDungeon.actionManager.addToBottom(new ModifyMagicNumberAction(this, -1));
                for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    AbstractDungeon.actionManager
                            .addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new VulnerablePower(mo, VULNERABLE_AMOUNT, false),
                                    VULNERABLE_AMOUNT, true, AbstractGameAction.AttackEffect.NONE));
                }
                if (this.magicNumber == 1) {
                    this.isOverflow = false;
                }
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, p, new WeakPower(mo, WEAK_AMOUNT, false), WEAK_AMOUNT, true, AbstractGameAction.AttackEffect.NONE));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ChallengingRoar();
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
