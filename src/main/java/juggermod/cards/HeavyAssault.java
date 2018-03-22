package juggermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
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

public class HeavyAssault extends CustomCard{
    public static final String ID = "Heavy Assault";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 0;
    private static final int DEX_SCALING = 2;
    private static final int DEX_SCALING_UP = 1;
    private static final int POOL = 1;

    public HeavyAssault() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.HEAVY_ASSAULT), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
                AbstractCardEnum.COPPER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY, POOL);
        this.baseDamage = ATTACK_DMG;
        this.magicNumber = this.baseMagicNumber = DEX_SCALING;
        this.isEthereal = true;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        AbstractDungeon.player.hand.moveToExhaustPile(this);
    }

    @Override
    public void applyPowers() {
        int dexCount = GetPowerCount(AbstractDungeon.player, "Dexterity");
        if (dexCount < 0)
        this.baseDamage = dexCount * -1;
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
        int dexCount = GetPowerCount(p, "Dexterity");
        if (dexCount < 0)
        this.damage = dexCount * -1;
        this.calculateCardDamage(m);
        for (int i = 0; i < this.magicNumber; i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
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
        return new HeavyAssault();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(DEX_SCALING_UP);
        }
    }
}
