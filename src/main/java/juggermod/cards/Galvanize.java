package juggermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawReductionPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import juggermod.JuggerMod;
import juggermod.patches.AbstractCardEnum;

public class Galvanize extends CustomCard{
    public static final String ID = "Galvanize";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int PLATE_AMT = 3;
    private static final int UPGRADE_PLUS_PLATE = 1;
    private static final int POOL = 1;

    public Galvanize() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.GALVANIZE), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.COPPER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
        this.magicNumber = this.baseMagicNumber = PLATE_AMT;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p,  this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DrawReductionPower(p, 2), 2));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Galvanize();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_PLATE);
        }
    }
}
