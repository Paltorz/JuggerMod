package juggermod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import juggermod.JuggerMod;

import java.util.Objects;

public class PlateBalancePower extends AbstractPower{
    public static final String POWER_ID = "Foolhardy";
    public static final String NAME = "Foolhardy";
    public static final double LOSS = .2;
    public static final String[] DESCRIPTIONS = new String[]{
            "At the start of your turn, if your Plated Armor is more than ",
            ", lose 1/5 of your Plated Armor."
    };

    public PlateBalancePower() {
        this.name = NAME;
        this.ID = "Foolhardy";
        this.owner = AbstractDungeon.player;
        this.amount = -1;
        this.description = DESCRIPTIONS[0];
        this.updateDescription();
        this.img = JuggerMod.getFoolhardyPowerTexture();
    }

    private int GetPowerCount(AbstractCreature c, String powerId) {
        AbstractPower power =  c.getPower(powerId);
        return power != null ? power.amount : 0;
    }

    @Override
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(DESCRIPTIONS[0]);
        if (Objects.equals(AbstractDungeon.id, "Exordium")) {
            sb.append(5);
        }else if (Objects.equals(AbstractDungeon.id, "TheCity")){
             sb.append(10);
        }else {
            sb.append(15);
        }
        sb.append(DESCRIPTIONS[1]);
        this.description = sb.toString();
    }

    @Override
    public void atStartOfTurn() {
        int plateCount = GetPowerCount(AbstractDungeon.player, "Plated Armor");
        if (Objects.equals(AbstractDungeon.id, "Exordium")) {
            if (plateCount > 5) {
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "Plated Armor", (int) (plateCount * .2)));
            }
        }else if (Objects.equals(AbstractDungeon.id, "TheCity")) {
            if (plateCount > 10) {
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "Plated Armor", (int) (plateCount * .2)));
            }
        } else {
            if (plateCount > 15) {
                this.flash();
                AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "Plated Armor", (int) (plateCount * .2)));
            }
        }
    }
}
