package juggermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import juggermod.JuggerMod;
import juggermod.patches.AbstractCardEnum;

public class HeavyCrash extends CustomCard{
    public static final String ID = "Heavy Crash";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 0;
    private static final int PLATE_SCALING = 3;
    private static final int PLATE_SCALING_UP = 1;
    private static final int POOL = 1;

    public HeavyCrash() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.HEAVY_CRASH), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
                AbstractCardEnum.COPPER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY, POOL);
        this.damage=this.baseDamage = ATTACK_DMG;
        this.magicNumber = this.baseMagicNumber = PLATE_SCALING;
    }

    @Override
    public void applyPowers() {
        int plateCount = GetPowerCount(AbstractDungeon.player, "Plated Armor");
        this.baseDamage = this.magicNumber * plateCount;
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
        int plateCount = GetPowerCount(p, "Plated Armor");
        this.baseDamage = this.magicNumber * plateCount;
        this.calculateCardDamage(m);
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        if(plateCount > 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Plated Armor"));
        }
        this.rawDescription = DESCRIPTION + UPGRADE_DESCRIPTION;
        this.initializeDescription();
    }

    private int GetPowerCount(AbstractCreature c, String powerId) {
        AbstractPower power =  c.getPower(powerId);
        return power != null ? power.amount : 0;
    }

    @Override
    public AbstractCard makeCopy() {
        return new HeavyCrash();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(PLATE_SCALING_UP);
        }
    }
}
