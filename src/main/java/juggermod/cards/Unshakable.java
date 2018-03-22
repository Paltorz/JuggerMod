package juggermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import juggermod.JuggerMod;
import juggermod.patches.AbstractCardEnum;

public class Unshakable extends CustomCard{
    public static final String ID = "Unshakable";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final int PLATE_AMT= 5;
    private static final int UPGRADED_PLATE = 6;
    private static final int WEAK_AMOUNT = 2;
    private static final int UPGRADE_PLUS_WEAK = 1;
    private static final int POOL = 1;

    public Unshakable() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.UNSHAKABLE), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.COPPER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY, POOL);
        this.magicNumber = this.baseMagicNumber = WEAK_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, this.magicNumber, false), this.magicNumber));
        if (!this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, PLATE_AMT), PLATE_AMT));
        }else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, UPGRADED_PLATE), UPGRADED_PLATE));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Unshakable();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_WEAK);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
