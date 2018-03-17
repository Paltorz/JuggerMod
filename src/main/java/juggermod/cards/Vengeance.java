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
import juggermod.patches.AbstractCardEnum;

public class Vengeance extends CustomCard{
    public static final String ID = "Vengeance";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final int COST_UPDATE = -1;
    private static final int ENERGY_GAIN = 1;
    private static final int POOL = 1;

    public Vengeance() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.VENGEANCE), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.COPPER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
        this.magicNumber = this.baseMagicNumber = ENERGY_GAIN;
        this.exhaust = true;
    }

    @Override
    public void tookDamage() {
        this.updateCost(COST_UPDATE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        Vengeance tmp = new Vengeance();
        if (AbstractDungeon.player != null) {
            tmp.updateCost(- AbstractDungeon.player.damagedThisCombat);
        }
        return tmp;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
