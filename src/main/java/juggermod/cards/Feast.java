package juggermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import juggermod.JuggerMod;
import juggermod.actions.unique.FeastAction;
import juggermod.patches.AbstractCardEnum;

public class Feast extends CustomCard{
    public static final String ID = "Feast";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 12;
    private static final int ATTACK_DMG_UP = 2;
    private static final int HEAL_AMOUNT = 6;
    private static final int HEAL_AMOUNT_UP = 2;
    private static final int POOL = 1;

    public Feast() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.FEAST), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
                AbstractCardEnum.COPPER, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY, POOL);
        this.baseDamage = this.damage =  ATTACK_DMG;
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = this.HEAL_AMOUNT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new FeastAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Feast();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(ATTACK_DMG_UP);
            this.upgradeMagicNumber(HEAL_AMOUNT_UP);
        }
    }
}
