package juggermod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import juggermod.JuggerMod;
import juggermod.patches.AbstractCardEnum;
import juggermod.patches.OverflowCard;

public class HunkerDown extends OverflowCard {
    public static final String ID = "Hunker Down";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int DEX_LOSS = -1;
    private static final int COST = 1;
    private static final int POOL = 1;

    public HunkerDown() {
        super(ID, NAME, JuggerMod.makePath(JuggerMod.HUNKER_DOWN), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.COPPER, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF, POOL);
        this.exhaust = true;
        this.isOverflow = true;
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DexterityPower(AbstractDungeon.player, DEX_LOSS), DEX_LOSS));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        int dexCount = GetPowerCount(p, "Dexterity");
        if (!canUse) {
            return false;
        }
        if (dexCount >= 0) {
            this.cantUseMessage = EXTENDED_DESCRIPTION[0];
            return false;
        } else {
            canUse = true;
        }
        return canUse;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int dexCount = GetPowerCount(p, "Dexterity");
        if (dexCount < 0) {
            int plateCount = (int)(dexCount * -.5);
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PlatedArmorPower(p, plateCount), plateCount));
        }
    }

    private int GetPowerCount(AbstractCreature c, String powerId) {
        AbstractPower power = c.getPower(powerId);
        return power != null ? power.amount : 0;
    }

    @Override
    public AbstractCard makeCopy() {
        return new HunkerDown();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.exhaust = false;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
