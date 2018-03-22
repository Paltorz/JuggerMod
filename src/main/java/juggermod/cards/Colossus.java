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
import com.megacrit.cardcrawl.powers.StrengthPower;
import juggermod.JuggerMod;
import juggermod.patches.AbstractCardEnum;
import juggermod.powers.ColossusPower;

public class Colossus extends CustomCard{
    public static final String ID = "Colossus";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int PLATE_AMT = 6;
    private static final int STR_AMOUNT = 4;
    private static final int STR_AMOUNT_UP = 3;
    private static final int POOL = 1;

    public Colossus() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.COLOSSUS), COST, DESCRIPTION, AbstractCard.CardType.POWER,
                AbstractCardEnum.COPPER, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF, POOL);
        this.magicNumber = this.baseMagicNumber = STR_AMOUNT;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(AbstractDungeon.player, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p,  PLATE_AMT), PLATE_AMT));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ColossusPower(p)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Colossus();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(STR_AMOUNT_UP);
        }
    }
}
