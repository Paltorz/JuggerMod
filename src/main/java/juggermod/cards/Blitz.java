package juggermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import juggermod.JuggerMod;
import juggermod.characters.TheJuggernaut;
import juggermod.patches.AbstractCardEnum;

public class Blitz extends CustomCard{
    public static final String ID = "Blitz";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final int ENERGY_GAIN = 1;
    private static final int CARD_DRAW = 2;
    private static final int POOL = 1;

    public Blitz() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.BLITZ), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.COPPER, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF, POOL);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (TheJuggernaut.turnTracker == 0) {
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(ENERGY_GAIN * 2));
        }else{
            AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(ENERGY_GAIN));
        }
        if (AbstractDungeon.player.drawPile.isEmpty()) {
            AbstractDungeon.actionManager.addToBottom(new EmptyDeckShuffleAction());
        }
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, CARD_DRAW));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Blitz();
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