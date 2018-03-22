package juggermod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import juggermod.JuggerMod;

public class SneckoHeart extends CustomRelic{
    private static final String ID = "Snecko Heart";
    private static final int CARD_DRAW = 1;

    public SneckoHeart() {
        super(ID, JuggerMod.getSneckoHeartTexture(),
                AbstractRelic.RelicTier.UNCOMMON, LandingSound.SOLID);
    }

    @Override
    public void atTurnStart() {
        if (AbstractDungeon.player.hasPower("Confusion")) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, CARD_DRAW));
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SneckoHeart();
    }
}
