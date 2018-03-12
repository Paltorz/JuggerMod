package juggermod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import juggermod.JuggerMod;

public class ColossusPower extends AbstractPower{
    public static final String POWER_ID = "Colossus";
    public static final String NAME = "Colossus";
    public static final String[] DESCRIPTIONS = new String[]{
            "You can no longer gain Block from cards."
    };

    public ColossusPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "Colossus";
        this.owner = owner;
        this.amount = -1;
        this.updateDescription();
        this.img = JuggerMod.getColossusPowerTexture();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    @Override
    public int modifyBlock(int blockAmount) {
        return 0;
    }
}
