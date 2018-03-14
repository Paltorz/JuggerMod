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
import juggermod.powers.OverflowingBlockPower;
import juggermod.powers.OverflowingPlatePower;

public class OverflowingArmor extends CustomCard{
    public static final String ID = "Overflowing Armor";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int POOL = 1;

    public OverflowingArmor() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.OVERFLOWING_ARMOR), COST, DESCRIPTION,
                AbstractCard.CardType.POWER, AbstractCardEnum.COPPER,
                AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF, POOL);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new OverflowingPlatePower(p, 1), 1));
        if (this.upgraded) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new OverflowingBlockPower(p, 1), 1));}
    }

    @Override
    public AbstractCard makeCopy() {
        return new OverflowingArmor();
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
