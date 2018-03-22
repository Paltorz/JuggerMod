package juggermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import juggermod.JuggerMod;
import juggermod.patches.AbstractCardEnum;
import juggermod.powers.TemporarySlowPower;

public class Slugfest extends CustomCard{
    public static final String ID = "Slugfest";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 3;
    private static final int COST_UPGRADE = 2;
    private static final int DRAW_AMT= 1;
    private static final int POOL = 1;

    public Slugfest() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.SLUGFEST), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.COPPER, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF, POOL);
        this.magicNumber = this.baseMagicNumber = DRAW_AMT;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!mo.hasPower("Slow") && !mo.hasPower("Slowed")) {
                AbstractDungeon.actionManager
                        .addToBottom(new ApplyPowerAction(mo, p, new TemporarySlowPower(mo, 0),
                                0, true, AbstractGameAction.AttackEffect.NONE));
            }
        }
        for (AbstractCard c : p.hand.group) {
            if (c.type.equals((Object)AbstractCard.CardType.ATTACK)){
                c.setCostForTurn(-9);
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Slugfest();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(COST_UPGRADE);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
