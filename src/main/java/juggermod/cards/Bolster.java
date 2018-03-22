package juggermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import juggermod.JuggerMod;
import juggermod.patches.AbstractCardEnum;

public class Bolster extends CustomCard{
    public static final String ID = "Bolster";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK_AMT = 5;
    private static final int UPGRADE_PLUS_BLOCK = 2;
    private static final int PLATE_AMT= 4;
    private static final int UPGRADE_PLUS_PLATE = 1;
    private static final int POOL = 1;

    public Bolster() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.BOLSTER), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.COPPER, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
        this.baseBlock = BLOCK_AMT;
        this.magicNumber = this.baseMagicNumber = PLATE_AMT;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int dexCount = GetPowerCount(p, "Dexterity");
        if (dexCount < 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, this.magicNumber), this.magicNumber));
        }else {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }
    }

    private int GetPowerCount(AbstractCreature c, String powerId) {
        AbstractPower power =  c.getPower(powerId);
        return power != null ? power.amount : 0;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Bolster();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_PLUS_BLOCK);
            this.upgradeMagicNumber(UPGRADE_PLUS_PLATE);
        }
    }
}
