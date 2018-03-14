package juggermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import juggermod.JuggerMod;
import juggermod.actions.unique.ApexPredatorAction;
import juggermod.patches.AbstractCardEnum;

public class ApexPredator extends CustomCard{
    public static final String ID = "Apex Predator";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int ATTACK_DMG = 8;
    private static final int ATTACK_DMG_UP = 2;
    private static final int BLOCK_AMT = 8;
    private static final int BLOCK_AMT_UP = 2;
    private static final int STR_LOSS = 3;
    private static final int STR_LOSS_UP = 1;
    private static final int POOL = 1;

    public ApexPredator() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.APEX_PREDATOR), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
                AbstractCardEnum.COPPER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY, POOL);
        this.baseDamage = this.damage =  ATTACK_DMG;
        this.baseBlock = BLOCK_AMT;
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = STR_LOSS;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApexPredatorAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    @Override
    public AbstractCard makeCopy() {
        return new ApexPredator();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(ATTACK_DMG_UP);
            this.upgradeBlock(BLOCK_AMT_UP);
            this.upgradeMagicNumber(STR_LOSS_UP);
        }
    }
}