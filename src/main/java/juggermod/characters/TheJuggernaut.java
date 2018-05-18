package juggermod.characters;

import basemod.abstracts.CustomPlayer;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import juggermod.JuggerMod;
import juggermod.actions.unique.HeavyBodyAction;
import juggermod.patches.TheJuggernautEnum;
import juggermod.powers.PlateBalancePower;

import java.util.ArrayList;

public class TheJuggernaut extends CustomPlayer {
	public static final int ENERGY_PER_TURN = 2;

	public static int turnTracker = 0;
	public static final float[] orbRotations = {
			0.0F,
			0.0F,
			0.0F,
			0.0F,
			0.0F,
			0.0F,
			0.0F,
			0.0F,
			0.0F,
			0.0F,
	};

	public static final String[] orbTextures = {
			"img/char/juggernaut/orb/juggernaut_layer1.png",
			"img/char/juggernaut/orb/juggernaut_layer2.png",
			"img/char/juggernaut/orb/juggernaut_layer3.png",
			"img/char/juggernaut/orb/juggernaut_layer4.png",
			"img/char/juggernaut/orb/juggernaut_layer5.png",
			"img/char/juggernaut/orb/juggernaut_layer6.png",
			"img/char/juggernaut/orb/juggernaut_layer1d.png",
			"img/char/juggernaut/orb/juggernaut_layer2d.png",
			"img/char/juggernaut/orb/juggernaut_layer3d.png",
			"img/char/juggernaut/orb/juggernaut_layer4d.png",
			"img/char/juggernaut/orb/juggernaut_layer5d.png",
	};
	
	public TheJuggernaut(String name, PlayerClass setClass) {
		super(name, setClass, orbTextures, "img/char/juggernaut/orb/vfx_juggernaut.png", "img/char/juggernaut/juggernaut2.g3dj", "sls_md_juggernaut|idle");
		
		initializeClass(null, JuggerMod.makePath(JuggerMod.JUGGERNAUT_SHOULDER_2),
				JuggerMod.makePath(JuggerMod.JUGGERNAUT_SHOULDER_1),
				JuggerMod.makePath(JuggerMod.JUGGERNAUT_CORPSE),
				getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));
	}

	@Override
	public void applyEndOfTurnTriggers() {
		for (AbstractPower p : this.powers) {
			p.atEndOfTurn(true);
		}
		turnTracker++;
	}

	@Override
	public void onVictory() {
		if (!this.isDying) {
			for (AbstractRelic r : this.relics) {
				r.onVictory();
			}
		}
		turnTracker = 0;
		this.damagedThisCombat = 0;
	}

	@Override
	public void applyStartOfTurnPostDrawPowers() {
		for (AbstractPower p : this.powers) {
			p.atStartOfTurnPostDraw();
		}
		if (AbstractDungeon.player.hasRelic("Heavy Body"))
		AbstractDungeon.actionManager.addToBottom(new HeavyBodyAction(AbstractDungeon.player, 1));
		if (AbstractDungeon.player.hasPower("Foolhardy") != true){
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlateBalancePower()));
		}
	}

	public void applyStartOfCombatLogic() {
		for (AbstractRelic r : this.relics) {
			if (r == null) continue;
			r.atBattleStart();
		}
		if (AbstractDungeon.player.hasPower("Foolhardy") != true){
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlateBalancePower()));
		}
	}

	public static ArrayList<String> getStartingDeck() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add("Strike_P");
		retVal.add("Strike_P");
		retVal.add("Strike_P");
		retVal.add("Strike_P");
		retVal.add("Defend_P");
		retVal.add("Defend_P");
		retVal.add("Defend_P");
		retVal.add("Defend_P");
		retVal.add("On Guard");
		retVal.add("Overpower");
		return retVal;
	}
	
	public static ArrayList<String> getStartingRelics() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add("Heavy Body");
		UnlockTracker.markRelicAsSeen("Heavy Body");
		return retVal;
	}
	
	public static CharSelectInfo getLoadout() {
		return new CharSelectInfo("The Juggernaut", "A fearless behemoth. He has weathered countless blows, and dealt countless more.",
				90, 90, 0, 99, 5,
			TheJuggernautEnum.THE_JUGGERNAUT, getStartingRelics(), getStartingDeck(), false);
	}


}
