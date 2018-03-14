package juggermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import juggermod.JuggerMod;
import juggermod.patches.AbstractCardEnum;

public class Overpower extends CustomCard{
    public static final String ID = "Overpower";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 6;
    private static final int STR_SCALING = 2;
    private static final int STR_SCALING_UP = 1;
    private static final int POOL = 1;

    public Overpower() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.OVERPOWER), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
                AbstractCardEnum.COPPER, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.ENEMY, POOL);
        this.damage=this.baseDamage = ATTACK_DMG;
        this.magicNumber = this.baseMagicNumber = STR_SCALING;
    }

    @Override
    public void applyPowers() {
        int strengthCount = GetPowerCount(AbstractDungeon.player, "Strength");
        this.baseDamage = ATTACK_DMG + (this.magicNumber - 1) * strengthCount;
        super.applyPowers();
        this.rawDescription = DESCRIPTION;
        this.rawDescription = this.rawDescription + UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.rawDescription = DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.rawDescription = DESCRIPTION;
        this.rawDescription = this.rawDescription + UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int strengthCount = GetPowerCount(p, "Strength");
        this.baseDamage = ATTACK_DMG + (this.magicNumber - 1) * strengthCount;
        this.calculateCardDamage(m);
        AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature) m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        this.rawDescription = DESCRIPTION + UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    private int GetPowerCount(AbstractCreature c, String powerId) {
        AbstractPower power =  c.getPower(powerId);
        return power != null ? power.amount : 0;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Overpower();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(STR_SCALING_UP);
        }
    }
}
