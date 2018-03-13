package juggermod.powers;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import juggermod.JuggerMod;

public class ImpenetrablePower extends AbstractPower{
    public static final String POWER_ID = "Impenetrable";
    public static final String NAME = "Impenetrable";
    public static final String[] DESCRIPTIONS = new String[]{
            "You do not lose Plated-Armor from attacks."
    };

    public ImpenetrablePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "Impenetrable";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = JuggerMod.getImpenetrablePowerTexture();
    }

    private int GetPowerCount(AbstractCreature c, String powerId) {
        AbstractPower power =  c.getPower(powerId);
        return power != null ? power.amount : 0;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "Impenetrable", 1));
        }
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS && damageAmount > 0) {
            this.flash();
            int plateCount = GetPowerCount(this.owner, "Plated Armor");
            if (plateCount > 0)
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner, this.owner, new PlatedArmorPower(this.owner,  1), 1));
        }
        return damageAmount;
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0f;
        this.amount += stackAmount;
    }
}
