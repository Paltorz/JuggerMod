package juggermod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import juggermod.JuggerMod;
import juggermod.actions.common.ModifyMagicNumberAction;
import juggermod.patches.AbstractCardEnum;
import juggermod.patches.OverflowCard;

public class Smother extends OverflowCard{
    public static final String ID = "Smother";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int POOL = 1;
    private static final int WEAK_AMOUNT = 1;
    private static final int UPGRADE_PLUS_WEAK = 2;
    private static final int ATTACK_DMG = 6;
    private static final int UPGRADE_DMG_AMT = 2;
    private static final int OVERFLOW_AMT = 2;
    private static final int OVERFLOW_WEAK = 1;

    public Smother() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.SMOTHER), COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.COPPER, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY, POOL);
        this.damage=this.baseDamage = ATTACK_DMG;
        this.baseMagicNumber = this.magicNumber = OVERFLOW_AMT;
        this.isOverflow = true;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        if (this.magicNumber > 0) {
            for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new WeakPower(mo, OVERFLOW_WEAK, false), OVERFLOW_WEAK, true, AbstractGameAction.AttackEffect.NONE));
            }
            AbstractDungeon.actionManager.addToBottom(new ModifyMagicNumberAction(this, -1));
            if (this.magicNumber == 1) {
                this.isOverflow = false;
            }
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature) m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        if (this.upgraded) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, UPGRADE_PLUS_WEAK, false), UPGRADE_PLUS_WEAK));
        }else{
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, WEAK_AMOUNT, false), WEAK_AMOUNT));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Smother();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DMG_AMT);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
