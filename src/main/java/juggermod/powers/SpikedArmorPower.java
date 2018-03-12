package juggermod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import juggermod.JuggerMod;

import java.util.ArrayList;

public class SpikedArmorPower extends AbstractPower{
    public static final String POWER_ID = "Spiked Armor";
    public static final String NAME = "Spiked Armor";
    public static final String[] DESCRIPTIONS = new String[] {
            "Deal damage equal to ",
            " times your Plated Armor to enemies who attack you, at the end of their turn."
    };

    private ArrayList<AbstractCreature> attackers;

    public SpikedArmorPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        this.type = AbstractPower.PowerType.BUFF;
        this.isTurnBased = true;
        this.priority = 90;
        this.img = JuggerMod.getSpikedArmorPowerTexture();
        this.attackers = new ArrayList<AbstractCreature>();
    }

    private int GetPowerCount(AbstractCreature c, String powerId) {
        AbstractPower power =  c.getPower(powerId);
        return power != null ? power.amount : 0;
    }

    @Override
    public void atEndOfRound() {
        this.attackers.clear();
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS
                && info.owner != null && info.owner != this.owner && !this.attackers.contains(info.owner)) {
            this.flash();
            int plateCount = GetPowerCount(AbstractDungeon.player, "Plated Armor");
            this.attackers.add(info.owner);
            AbstractDungeon.actionManager.addToBottom(new DamageAction(info.owner, new DamageInfo(info.owner, this.amount * plateCount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_VERTICAL, true));
        }
        return damageAmount;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
