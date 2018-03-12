package juggermod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import juggermod.JuggerMod;

public class FlickerPower extends AbstractPower {
	public static final String POWER_ID = "Flicker";
	public static final String NAME = "Flicker";
	public static final String[] DESCRIPTIONS = new String[] {
			"Weak, Frail, and Vulnerable have the opposite effect on you." };
	private boolean justApplied = false;

	public FlickerPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.description = DESCRIPTIONS[0];
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = true;
		this.priority = 8;
		this.img = JuggerMod.getEssenceMirrorPowerTexture();
	}

	@Override
	public void atEndOfRound() {
		if (this.justApplied) {
			this.justApplied = false;
			return;
		}
		if (this.amount == 0) {
			AbstractDungeon.actionManager
					.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "Flicker"));
		} else {
			AbstractDungeon.actionManager
					.addToBottom(new ReducePowerAction(this.owner, this.owner, "Flicker", 1));
		}
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

	@Override
	public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
		if (damageType == DamageInfo.DamageType.NORMAL && this.owner.hasPower("Vulnerable")) {
			return damage * 0.34f;
		}
		return damage;
	}

	@Override
	public int modifyBlock(int blockAmount) {
		if (this.owner.hasPower("Frail")) {
			return (int) (blockAmount * 1.67f);
		}
		return blockAmount;
	}
}