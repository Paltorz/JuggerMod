package juggermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RegenerationPower;
import juggermod.JuggerMod;
import juggermod.patches.AbstractCardEnum;

public class InhumanRecovery extends CustomCard{
    public static final String ID = "Inhuman Recovery";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int HEAL_AMT = 4;
    private static final int TURNS_HEALED = 4;
    private static final int TURNS_HEALED_UP = 1;
    private static final int POOL = 1;

    public InhumanRecovery() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.INHUMAN_RECOVERY), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.BROWN, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF, POOL);
        this.magicNumber = this.baseMagicNumber = TURNS_HEALED;
        this.exhaust = true;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, HEAL_AMT));
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new RegenerationPower(p, this.magicNumber, HEAL_AMT), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new InhumanRecovery();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(TURNS_HEALED_UP);
        }
    }
}
