package juggermod.characters;

import basemod.abstracts.CustomPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import juggermod.JuggerMod;
import juggermod.actions.unique.HeavyBodyAction;
import juggermod.patches.TheJuggernautEnum;

import java.util.ArrayList;

public class TheJuggernaut extends CustomPlayer {
	public static final int ENERGY_PER_TURN = 2;

	public static int turnTracker = 0;
	public static final String[] orbTextures = {
			"img/char/seeker/orb/layer1.png",
			"img/char/seeker/orb/layer2.png",
			"img/char/seeker/orb/layer3.png",
			"img/char/seeker/orb/layer4.png",
			"img/char/seeker/orb/layer5.png",
			"img/char/seeker/orb/layer6.png",
			"img/char/seeker/orb/layer1d.png",
			"img/char/seeker/orb/layer2d.png",
			"img/char/seeker/orb/layer3d.png",
			"img/char/seeker/orb/layer4d.png",
			"img/char/seeker/orb/layer5d.png",
	};
	
	public TheJuggernaut(String name, PlayerClass setClass) {
		super(name, setClass, orbTextures, "img/char/seeker/orb/vfx.png", "img/char/seeker/seeker2.g3dj", "sls_md|idle");
		
		initializeClass(null, JuggerMod.makePath(JuggerMod.SEEKER_SHOULDER_2),
				JuggerMod.makePath(JuggerMod.SEEKER_SHOULDER_1),
				JuggerMod.makePath(JuggerMod.SEEKER_CORPSE),
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
				90, 90, 99, 5,
			TheJuggernautEnum.THE_JUGGERNAUT, getStartingRelics(), getStartingDeck(), false);
	}


}
