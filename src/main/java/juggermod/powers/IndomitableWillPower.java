package juggermod.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import juggermod.JuggerMod;

public class IndomitableWillPower extends AbstractPower{
    public static final String POWER_ID = "Indomitable Will";
    public static final String NAME = "Indomitable Will";
    public static final String[] DESCRIPTIONS = new String[] {
            "Weak has the opposite effect on you." };

    public IndomitableWillPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.description = DESCRIPTIONS[0];
        this.type = AbstractPower.PowerType.BUFF;
        this.isTurnBased = true;
        this.priority = 8;
        this.img = JuggerMod.getIndomitableWillPowerTexture();
    }

    /*
     * @Override public void updateDescription() { this.description =
     * this.amount == 1 ? (this.owner != null && !this.owner.isPlayer &&
     * AbstractDungeon.player.hasRelic("Paper Crane") ? DESCRIPTIONS[0] + 50 +
     * DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2] : DESCRIPTIONS[0] + 25 +
     * DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]) : (this.owner != null &&
     * !this.owner.isPlayer && AbstractDungeon.player.hasRelic("Paper Crane") ?
     * DESCRIPTIONS[0] + 50 + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3] :
     * DESCRIPTIONS[0] + 25 + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3]);
     * }
     */

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL && this.owner.hasPower("Weakened")) {
            return damage * 1.67f;
        }
        return damage;
    }
}
