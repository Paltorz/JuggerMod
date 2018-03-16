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
import juggermod.JuggerMod;
import juggermod.patches.AbstractCardEnum;

public class FlyingPress extends CustomCard{
    public static final String ID = "Flying Press";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String DESCRIPTION_UP = "Deal damage equal to twice your current Block.";
    private static final int COST = 1;
    private static final int ATTACK_DMG = 0;
    private static final int BLOCK_SCALING = 2;
    private static final int POOL = 1;

    public FlyingPress() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.FLYING_PRESS), COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK, AbstractCardEnum.COPPER,
                AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY, POOL);
        this.baseDamage = ATTACK_DMG;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.baseDamage = BLOCK_SCALING * (p.currentBlock);
        this.calculateCardDamage(m);
        AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature) m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.rawDescription = DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void applyPowers() {
        this.baseDamage = BLOCK_SCALING * (AbstractDungeon.player.currentBlock);
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
    public AbstractCard makeCopy() {
        return new FlyingPress();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust = false;
            this.rawDescription = DESCRIPTION_UP;
            this.initializeDescription();
        }
    }
}
