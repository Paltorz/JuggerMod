package juggermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import juggermod.JuggerMod;
import juggermod.actions.common.DuplicateSelectedCardAction;
import juggermod.characters.TheJuggernaut;
import juggermod.patches.AbstractCardEnum;

public class Improvisation extends CustomCard{
    public static final String ID = "Improvisation";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int COPY_AMT = 1;
    private static final int ENERGY_GAIN = 1;
    private static final int POOL = 1;

    public Improvisation() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.IMPROVISATION), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.COPPER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
        this.isEthereal = true;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        AbstractDungeon.player.hand.moveToExhaustPile(this);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new DuplicateSelectedCardAction(p, COPY_AMT));
        if (TheJuggernaut.turnTracker == 0) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(ENERGY_GAIN));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Improvisation();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.isInnate = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}