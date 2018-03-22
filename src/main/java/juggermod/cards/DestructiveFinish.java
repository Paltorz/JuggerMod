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
import juggermod.actions.unique.DestructiveFinishAction;
import juggermod.patches.AbstractCardEnum;

public class DestructiveFinish extends CustomCard{
    public static final String ID = "Destructive Finish";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 8;
    private static final int DISCARD_AMT = 1;
    private static final int DISCARD_AMT_UP = 1;
    private static final int POOL = 1;

    public DestructiveFinish() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.DESTRUCTIVE_FINISH), COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK, AbstractCardEnum.COPPER,
                CardRarity.RARE, AbstractCard.CardTarget.ENEMY, POOL);
        this.baseDamage = ATTACK_DMG;
        this.magicNumber = this.baseMagicNumber = DISCARD_AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom((new DamageAction((AbstractCreature) m,
                new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY)));
        AbstractDungeon.actionManager.addToBottom(new DestructiveFinishAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new DestructiveFinish();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(DISCARD_AMT_UP);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
