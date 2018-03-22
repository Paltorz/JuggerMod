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
import juggermod.JuggerMod;
import juggermod.patches.AbstractCardEnum;

public class LivingArmor extends CustomCard{
    public static final String ID = "Living Armor";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int PLATE_AMT = 5;
    private static final int UPGRADE_PLUS_PLATE = 1;
    private static final int POOL = 1;

    public LivingArmor() {
        this(0);
    }

    public LivingArmor(int upgrades) {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.LIVING_ARMOR), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.COPPER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
        this.magicNumber = this.baseMagicNumber = PLATE_AMT;
        this.timesUpgraded = upgrades;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p,  this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new LivingArmor(this.timesUpgraded);
    }

    @Override
    public void upgrade() {
            this.upgradeMagicNumber(UPGRADE_PLUS_PLATE);
            ++this.timesUpgraded;
            this.upgraded = true;
            this.name = NAME + "+" + this.timesUpgraded;
            this.initializeTitle();
        }
    @Override
    public boolean canUpgrade() {
        return true;
    }
}
