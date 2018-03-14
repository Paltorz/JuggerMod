package juggermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import juggermod.JuggerMod;
import juggermod.patches.AbstractCardEnum;
import juggermod.powers.ShatterPower;

public class Shatter extends CustomCard{
    public static final String ID = "Shatter";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int DMG_AMT = 4;
    private static final int UPGRADED_DMG_AMT = 2;
    private static final int PLATE_LOSS_AMT = 1;
    private static final int POOL = 1;

    public Shatter() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.SHATTER), COST, DESCRIPTION,
                AbstractCard.CardType.POWER, AbstractCardEnum.COPPER,
                AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
        this.magicNumber = this.baseMagicNumber = DMG_AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new ApplyPowerAction(p, p,
                        new ShatterPower(p, this.magicNumber, PLATE_LOSS_AMT), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Shatter();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADED_DMG_AMT);
        }
    }
}
