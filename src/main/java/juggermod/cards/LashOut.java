package juggermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.unique.SwordBoomerangAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import juggermod.JuggerMod;
import juggermod.patches.AbstractCardEnum;

public class LashOut extends CustomCard{
    public static final String ID = "Lash Out";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 2;
    private static final int ATTACK_UPGRADE = 0;
    private static final int NUM_ATTACKS = 3;
    private static final int NUM_ATTACKS_UPGRADE = 1;
    private static final int POOL = 1;

    public LashOut() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.LASH_OUT), COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK, AbstractCardEnum.COPPER,
                AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY, POOL);
        this.damage = this.baseDamage = ATTACK_DMG;
        this.magicNumber = this.baseMagicNumber = NUM_ATTACKS;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new SwordBoomerangAction(AbstractDungeon.getMonsters().getRandomMonster(true), new DamageInfo(p, ATTACK_DMG), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new LashOut();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(NUM_ATTACKS_UPGRADE);
        }
    }
}
