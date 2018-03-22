package juggermod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import juggermod.JuggerMod;

public class RigidArmor extends CustomRelic{
    private static final String ID = "Rigid Armor";
    private static final int PLATE_GAIN = 2;
    private static final int CARD_CUTOFF = 2;

    public RigidArmor() {
        super(ID, JuggerMod.getRigidArmorTexture(),
                AbstractRelic.RelicTier.COMMON, LandingSound.CLINK);
    }

    @Override
    public void atTurnStart() {
        this.counter = 0;
    }

    @Override
    public void onPlayerEndTurn() {
        if (this.counter <= CARD_CUTOFF) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player,  PLATE_GAIN), PLATE_GAIN));
        }
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        ++this.counter;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void onVictory() {
        this.counter = -1;
    }

    @Override
    public AbstractRelic makeCopy() {
        return new RigidArmor();
    }
}
