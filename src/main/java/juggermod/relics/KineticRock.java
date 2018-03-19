package juggermod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import juggermod.JuggerMod;

public class KineticRock extends CustomRelic {
    private static final String ID = "Kinetic Rock";

    public KineticRock() {
        super(ID, JuggerMod.getKineticRockTexture(),
                AbstractRelic.RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
        --com.megacrit.cardcrawl.dungeons.AbstractDungeon.player.masterHandSize;
    }

    @Override
    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
        ++com.megacrit.cardcrawl.dungeons.AbstractDungeon.player.masterHandSize;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new KineticRock();
    }
}
