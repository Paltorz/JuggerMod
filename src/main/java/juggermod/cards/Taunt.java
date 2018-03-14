package juggermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import juggermod.JuggerMod;
import juggermod.actions.unique.TauntAction;
import juggermod.patches.AbstractCardEnum;

public class Taunt extends CustomCard{
    public static final String ID = "Taunt";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int STR_LOSS = 3;
    private static final int STR_LOSS_UP = 1;
    private static final int COST = 1;
    private static final int POOL = 1;

    public Taunt() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.TAUNT), COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.COPPER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY, POOL);
        this.magicNumber = this.baseMagicNumber = STR_LOSS;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new TauntAction(this.magicNumber * -1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Taunt();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(STR_LOSS_UP);
        }
    }
}
