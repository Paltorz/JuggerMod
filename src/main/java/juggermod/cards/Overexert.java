package juggermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import juggermod.JuggerMod;
import juggermod.actions.unique.ArcanosphereAction;
import juggermod.patches.AbstractCardEnum;

public class Overexert extends CustomCard{
    public static final String ID = "Overexert";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final int DEX_LOSS = -2;
    private static final int DRAW = 2;
    private static final int TOP_DECK = 1;
    private static final int POOL = 1;

    public Overexert() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.OVEREXERT), COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.COPPER,
                AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF, POOL);
        this.baseMagicNumber = DRAW;
        this.magicNumber = DRAW;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (AbstractDungeon.player.drawPile.isEmpty()) {
            AbstractDungeon.actionManager.addToBottom(new EmptyDeckShuffleAction());
        }
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DexterityPower(p, DEX_LOSS), DEX_LOSS));
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        if (this.upgraded)
        AbstractDungeon.actionManager.addToTop(new ArcanosphereAction(AbstractDungeon.player, TOP_DECK));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Overexert();
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
