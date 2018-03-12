package juggermod.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import juggermod.JuggerMod;

public class GigaImpactPower extends AbstractPower{
    public static final String POWER_ID = "Advanced Tired";
    public static final String NAME = "Advanced Tired";
    public static final String[] DESCRIPTIONS = new String[]{
            "Lose",
            "at the start of your next turn."
    };

    public GigaImpactPower(int Amt) {
        this.name = NAME;
        this.ID = "Advanced Tired";
        this.owner = AbstractDungeon.player;
        this.amount = Amt;
        this.updateDescription();
        this.img = JuggerMod.getGigaImpactPowerTexture();
        this.type = AbstractPower.PowerType.DEBUFF;
        this.isTurnBased = true;
    }

    @Override
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(DESCRIPTIONS[0]);
        for (int i = 0; i < this.amount; ++i) {
            sb.append(" [R] [R] ");
        }
        sb.append(DESCRIPTIONS[1]);
        this.description = sb.toString();
    }

    @Override
    public void onEnergyRecharge() {
        int energy = EnergyPanel.totalCount;
        for (int i = 0; i < (this.amount * 2); i++){
            if (energy == 0){ continue;}
            energy--;
            AbstractDungeon.actionManager.addToBottom(new LoseEnergyAction(1));
        }
    }

    @Override
    public void atStartOfTurnPostDraw() {
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, "Advanced Tired"));
    }
}
