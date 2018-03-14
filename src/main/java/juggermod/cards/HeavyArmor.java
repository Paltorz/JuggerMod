package juggermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
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

public class HeavyArmor extends CustomCard{
    public static final String ID = "Heavy Armor";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK_AMT = 5;
    private static final int STR_SCALING= 1;
    private static final int STR_SCALING_UP = 1;
    private static final int POOL = 1;

    public HeavyArmor() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.HEAVY_ARMOR), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.COPPER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
        this.baseBlock = BLOCK_AMT;
        this.magicNumber = this.baseMagicNumber = STR_SCALING;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int strengthCount = GetPowerCount(p, "Strength") * this.magicNumber;
        this.block = this.baseBlock + strengthCount;
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    private int GetPowerCount(AbstractCreature c, String powerId) {
        AbstractPower power =  c.getPower(powerId);
        return power != null ? power.amount : 0;
    }

    @Override
    public AbstractCard makeCopy() {
        return new HeavyArmor();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(STR_SCALING_UP);
        }
    }
}
