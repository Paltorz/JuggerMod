package juggermod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import juggermod.JuggerMod;
import juggermod.patches.AbstractCardEnum;
import juggermod.patches.OverflowCard;
import juggermod.powers.ImpenetrablePower;

public class Impenetrable extends OverflowCard {
    public static final String ID = "Impenetrable";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int PLATE_AMT = 2;
    private static final int UPGRADED_PLATE = 4;
    private static final int POWER_AMT = 1;
    private static final int POWER_AMT_UP = 1;
    private static final int POOL = 1;

    public Impenetrable() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.IMPENETRABLE), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.COPPER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
        this.magicNumber = this.baseMagicNumber = POWER_AMT;
        this.isOverflow = true;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ImpenetrablePower(AbstractDungeon.player, this.magicNumber), this.magicNumber));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //if (this.upgraded) {
           // AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, UPGRADED_PLATE), UPGRADED_PLATE));
       // } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, PLATE_AMT), PLATE_AMT));
        //}
    }

    @Override
    public AbstractCard makeCopy() {
        return new Impenetrable();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(POWER_AMT_UP);
        }
    }
}