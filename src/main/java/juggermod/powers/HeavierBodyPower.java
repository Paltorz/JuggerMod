package juggermod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import juggermod.JuggerMod;

public class HeavierBodyPower extends AbstractPower{
    public static final String POWER_ID = "Heavier Body";
    public static final String NAME = "Heavier Body";
    public static final String[] DESCRIPTIONS = new String[]{
            "Improve the effects of the Heavy Body relic."
    };

    public HeavierBodyPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "Heavier Body";
        this.owner = owner;
        this.amount = -1;
        this.description = DESCRIPTIONS[0];
        this.updateDescription();
        this.img = JuggerMod.getHeavierBodyPowerTexture();
    }
}
