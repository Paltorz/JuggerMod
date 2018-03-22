package juggermod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import juggermod.JuggerMod;
import juggermod.actions.unique.HeavyBodyAction;

public class HeavyBody extends CustomRelic{
    private static final String ID = "Heavy Body";
    private static final int CARDS_TO_CHOOSE = 1;

    public HeavyBody() {
        super(ID, JuggerMod.getHeavyBodyTexture(),
                RelicTier.STARTER, LandingSound.HEAVY);
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(
                new HeavyBodyAction(AbstractDungeon.player,
                        CARDS_TO_CHOOSE));
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new HeavyBody();
    }
}
