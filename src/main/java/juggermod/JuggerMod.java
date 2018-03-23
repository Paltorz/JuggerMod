package juggermod;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import juggermod.cards.*;
import juggermod.characters.TheJuggernaut;
import juggermod.patches.AbstractCardEnum;
import juggermod.patches.TheJuggernautEnum;
import juggermod.relics.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

@SpireInitializer
public class JuggerMod implements PostInitializeSubscriber,
	EditCardsSubscriber, EditRelicsSubscriber, EditCharactersSubscriber,
	EditStringsSubscriber, SetUnlocksSubscriber, OnCardUseSubscriber,
	EditKeywordsSubscriber, OnPowersModifiedSubscriber, PostExhaustSubscriber,
	PostBattleSubscriber, PostDungeonInitializeSubscriber, PostDrawSubscriber {
	public static final Logger logger = LogManager.getLogger(JuggerMod.class.getName());
	
    private static final String MODNAME = "JuggerMod";
    private static final String AUTHOR = "Paltorz, and the FruityMod team";
    private static final String DESCRIPTION = "v0.4.3\n Adds The Juggernaut as a playable third character";
    
    private static final Color COPPER = CardHelper.getColor(186.0f, 108.0f, 31.0f);
    private static final String FRUITY_MOD_ASSETS_FOLDER = "img";
    
    // card backgrounds
    private static final String ATTACK_COPPER = "512/bg_attack_brown.png";
    private static final String SKILL_COPPER = "512/bg_attack_brown.png";
    private static final String POWER_COPPER = "512/bg_attack_brown.png";
    private static final String ENERGY_ORB_COPPER = "512/card_brown_orb.png";
    
    private static final String ATTACK_COPPER_PORTRAIT = "1024/bg_attack_brown.png";
    private static final String SKILL_COPPER_PORTRAIT = "1024/bg_attack_brown.png";
    private static final String POWER_COPPER_PORTRAIT = "1024/bg_attack_brown.png";
    private static final String ENERGY_ORB_COPPER_PORTRAIT = "1024/card_brown_orb.png";
    
    // card images
    public static final String STRIKE_PURPLE = "cards/strike_purple.png";
    public static final String DEFEND_PURPLE = "cards/defend_purple.png";

    public static final String EARTHQUAKE = "cards/earth_quake.png";
    public static final String ON_GUARD = "cards/on_guard.png";
    public static final String FURY = "cards/fury.png";
    public static final String STRUGGLE = "cards/struggle.png";
    public static final String SKULL_BASH = "cards/skull_bash.png";
    public static final String HAMMER_ARM = "cards/hammer_arm.png";
    public static final String PULVERIZE = "cards/pulverize.png";
    public static final String SHOULDER_BLOW = "cards/shoulder_blow.png";
    public static final String FLYING_PRESS = "cards/flying_press.png";
    public static final String FRENZY = "cards/frenzy.png";
    public static final String RELENTLESS_BLOWS = "cards/relentless_blows.png";
    public static final String LUNGE = "cards/lunge.png";
    public static final String CHALLENGING_ROAR = "cards/challenging_roar.png";
    public static final String SIMPLE_MINDED = "cards/simple_minded.png";
    public static final String HYSTERIA = "cards/hysteria.png";
    public static final String SMOTHER = "cards/smother.png";
    public static final String CASCADING_STEEL = "cards/cascading_steel.png";
    public static final String FEINT = "cards/feint.png";
    public static final String BULLDOZE = "cards/bulldoze.png";
    public static final String PURSUIT = "cards/pursuit.png";
    public static final String ENDURE = "cards/endure.png";
    public static final String GALVANIZE = "cards/galvanize.png";
    public static final String ACCELERATE = "cards/accelerate.png";
    public static final String BULWARK = "cards/bulwark.png";
    public static final String FOCUSED_POWER = "cards/focused_power.png";
    public static final String UNSHAKABLE = "cards/unshakable.png";
    public static final String CONVERT_FLESH = "cards/convert_flesh.png";
    public static final String VENGEANCE = "cards/vengeance.png";
    public static final String OVERPOWER = "cards/overpower.png";
    public static final String HEAVY_CRASH = "cards/heavy_crash.png";
    public static final String STEEL_FORCE = "cards/steel_force.png";
    public static final String LASH_OUT = "cards/lash_out.png";
    public static final String UNSTOPPABLE_FORCE = "cards/unstoppable_force.png";
    public static final String HEAVY_ASSAULT = "cards/heavy_assault.png";
    public static final String SLOW_AND_STEADY = "cards/slow_and_steady.png";
    public static final String BREAKTHROUGH = "cards/breakthrough.png";
    public static final String HEAVY_ARMOR = "cards/heavy_armor.png";
    public static final String GRAPPLE = "cards/grapple.png";
    public static final String INHUMAN_RECOVERY = "cards/inhuman_recovery.png";
    public static final String HUNKER_DOWN = "cards/hunker_down.png";
    public static final String TAUNT = "cards/taunt.png";
    public static final String DESTRUCTIVE_FINISH = "cards/destructive_finish.png";
    public static final String THUNDER_STRUCK = "cards/thunder_struck.png";
    public static final String LIVING_ARMOR = "cards/living_armor.png";
    public static final String REVERSAL = "cards/reflection_ward.png";
    public static final String MERCURIAL = "cards/mercurial.png";
    public static final String BRUTE_FORCE = "cards/brute_force.png";
    public static final String INERTIA = "cards/inertia.png";
    public static final String COMBAT_TRAINING = "cards/combat_training.png";
    public static final String RELEASE_RESTRAINT = "cards/release_restraint.png";
    public static final String SHATTER = "cards/shatter.png";
    public static final String NATURAL_ENDURANCE = "cards/natural_endurance.png";
    public static final String BOLSTER = "cards/bolster.png";
    public static final String CANNIBALIZE = "cards/cannibalize.png";
    public static final String BIDE = "cards/bide.png";
    public static final String GIGA_IMPACT = "cards/giga_impact.png";
    public static final String FEAST = "cards/feast.png";
    public static final String APEX_PREDATOR = "cards/apex_predator.png";
    public static final String OVEREXERT = "cards/overexert.png";
    public static final String COLOSSUS = "cards/colossus.png";
    public static final String SPIKED_ARMOR = "cards/spiked_armor.png";
    public static final String INDOMITABLE_WILL = "cards/indomitable_will.png";
    public static final String MANGLE = "cards/mangle.png";
    public static final String CALL_TO_ARMS = "cards/call_to_arms.png";
    public static final String PERFECTED_BLOW = "cards/perfected_blow.png";
    public static final String OVERFLOWING_ARMOR = "cards/overflowing_armor.png";
    public static final String ATLAS = "cards/atlas.png";
    public static final String IMPENETRABLE = "cards/impenetrable.png";
    public static final String CHARGE = "cards/charge.png";
    public static final String BATTLE_SHOUT = "cards/battle_shout.png";
    public static final String BLITZ = "cards/blitz.png";
    public static final String IMPROVISATION = "cards/improvisation.png";
    public static final String SLUGFEST = "cards/slugfest.png";
    public static final String HEAVIER_BODY = "cards/heavier_body.png";

    // power images

    public static final String BRUTE_FORCE_POWER = "powers/brute_force.png";
    public static final String INERTIA_POWER = "powers/inertia.png";
    public static final String COMBAT_TRAINING_POWER = "powers/combat_training.png";
    public static final String RELEASE_RESTRAINT_POWER = "powers/release_restraint.png";
    public static final String SHATTER_POWER = "powers/shatter.png";
    public static final String NATURAL_ENDURANCE_POWER = "powers/natural_endurance.png";
    public static final String BIDE_POWER = "powers/bide.png";
    public static final String GIGA_IMPACT_POWER = "powers/giga_impact.png";
    public static final String COLOSSUS_POWER = "powers/colossus.png";
    public static final String SPIKED_ARMOR_POWER = "powers/spiked_armor.png";
    public static final String INDOMITABLE_WILL_POWER = "powers/indomitable_will.png";
    public static final String MANGLE_POWER = "powers/mangle.png";
    public static final String OVERFLOWING_BLOCK_POWER = "powers/overflowing_block.png";
    public static final String OVERFLOWING_PLATE_POWER = "powers/overflowing_plate.png";
    public static final String ATLAS_POWER = "powers/atlas.png";
    public static final String IMPENETRABLE_POWER = "powers/impenetrable.png";
    public static final String HEAVIER_BODY_POWER = "powers/heavier_body.png";
    public static final String FOOLHARDY_POWER = "powers/foolhardy.png";

    // relic images

    public static final String HEAVY_BODY_RELIC = "relics/mechanicalCore.png";
    public static final String SNECKO_HEART_RELIC = "relics/sneckoHeart.png";
    public static final String RIGID_ARMOR_RELIC = "relics/rigidArmor.png";
    public static final String BRITTLE_ROCK_RELIC = "relics/brittleRock.png";
    public static final String KINETIC_ROCK_RELIC = "relics/kineticRock.png";

    
    // seeker assets
    private static final String SEEKER_BUTTON = "charSelect/seekerButton.png";
    private static final String SEEKER_PORTRAIT = "charSelect/SeekerPortraitBG.jpg";
    public static final String SEEKER_SHOULDER_1 = "char/seeker/shoulder.png";
    public static final String SEEKER_SHOULDER_2 = "char/seeker/shoulder2.png";
    public static final String SEEKER_CORPSE = "char/seeker/corpse.png";
    public static final String SEEKER_SKELETON_ATLAS = "char/seeker/skeleton.atlas";
    public static final String SEEKER_SKELETON_JSON = "char/seeker/skeleton.json";
    
    // badge
    public static final String BADGE_IMG = "FRelicBadge.png";
    
    // texture loaders
    public static Texture getFoolhardyPowerTexture() {
        return new Texture(makePath(FOOLHARDY_POWER));
    }

    public static Texture getHeavierBodyPowerTexture() {
        return new Texture(makePath(HEAVIER_BODY_POWER));
    }

    public static Texture getImpenetrablePowerTexture() {
        return new Texture(makePath(IMPENETRABLE_POWER));
    }

    public static Texture getAtlasPowerTexture() {
        return new Texture(makePath(ATLAS_POWER));
    }

    public static Texture getOverflowingBlockPowerTexture() {
        return new Texture(makePath(OVERFLOWING_BLOCK_POWER));
    }

    public static Texture getOverflowingPlatePowerTexture() {
        return new Texture(makePath(OVERFLOWING_PLATE_POWER));
    }

    public static Texture getManglePowerTexture() {
        return new Texture(makePath(MANGLE_POWER));
    }

    public static Texture getIndomitableWillPowerTexture() {
        return new Texture(makePath(INDOMITABLE_WILL_POWER));
    }

    public static Texture getSpikedArmorPowerTexture() { return new Texture(makePath(SPIKED_ARMOR_POWER)); }

    public static Texture getColossusPowerTexture() {
        return new Texture(makePath(COLOSSUS_POWER));
    }

    public static Texture getGigaImpactPowerTexture() {
        return new Texture(makePath(GIGA_IMPACT_POWER));
    }

    public static Texture getBidePowerTexture() {
        return new Texture(makePath(BIDE_POWER));
    }

    public static Texture getNaturalEndurancePowerTexture() { return new Texture(makePath(NATURAL_ENDURANCE_POWER)); }

    public static Texture getShatterPowerTexture() { return new Texture(makePath(SHATTER_POWER)); }

    public static Texture getReleaseRestraintPowerTexture() {
        return new Texture(makePath(RELEASE_RESTRAINT_POWER));
    }

    public static Texture getCombatTrainingPowerTexture() {
        return new Texture(makePath(COMBAT_TRAINING_POWER));
    }

    public static Texture getInertiaPowerTexture() {
        return new Texture(makePath(INERTIA_POWER));
    }

    public static Texture getBruteForcePowerTexture() {
        return new Texture(makePath(BRUTE_FORCE_POWER));
    }

    public static Texture getHeavyBodyTexture() { return new Texture(makePath(HEAVY_BODY_RELIC)); }

    public static Texture getSneckoHeartTexture() { return new Texture(makePath(SNECKO_HEART_RELIC)); }

    public static Texture getRigidArmorTexture() { return new Texture(makePath(RIGID_ARMOR_RELIC)); }

    public static Texture getBrittleRockTexture() { return new Texture(makePath(BRITTLE_ROCK_RELIC)); }

    public static Texture getKineticRockTexture() { return new Texture(makePath(KINETIC_ROCK_RELIC)); }

    /**
     * Makes a full path for a resource path
     * @param resource the resource, must *NOT* have a leading "/"
     * @return the full path
     */
    public static final String makePath(String resource) {
    	return FRUITY_MOD_ASSETS_FOLDER + "/" + resource;
    }
    
    public JuggerMod() {
    	logger.info("subscribing to postInitialize event");
        BaseMod.subscribeToPostInitialize(this);
        
        logger.info("subscribing to editCharacters event");
        BaseMod.subscribeToEditCharacters(this);
        
        logger.info("subscribing to editRelics event");
        BaseMod.subscribeToEditRelics(this);
        
        logger.info("subscribing to editCards event");
        BaseMod.subscribeToEditCards(this);

        logger.info("subscribing to editStrings event");
        BaseMod.subscribeToEditStrings(this);
        
        /* Disable this during playtesting for being counterproductive */
        // logger.info("subscribing to setUnlocks event");
        // BaseMod.subscribeToSetUnlocks(this);
        
        logger.info("subscribing to onCardUse event");
        BaseMod.subscribeToOnCardUse(this);
        
        logger.info("subscribing to editKeywords event");
        BaseMod.subscribeToEditKeywords(this);
        
        BaseMod.subscribeToOnPowersModified(this);
        BaseMod.subscribeToPostExhaust(this);
        BaseMod.subscribeToPostBattle(this);
        BaseMod.subscribeToPostDraw(this);
        
        /*
         * Note that for now when installing JuggerMod, in the `mods/` folder another folder named
         * the value of FRUITY_MOD_ASSETS_FOLDER must be created into which all the contents of the
         * `images/` folder must be relocated
         */
        logger.info("creating the color " + AbstractCardEnum.COPPER.toString());
        BaseMod.addColor(AbstractCardEnum.COPPER.toString(),
        		COPPER, COPPER, COPPER, COPPER, COPPER, COPPER, COPPER,
        		makePath(ATTACK_COPPER), makePath(SKILL_COPPER),
        		makePath(POWER_COPPER), makePath(ENERGY_ORB_COPPER),
        		makePath(ATTACK_COPPER_PORTRAIT), makePath(SKILL_COPPER_PORTRAIT),
        		makePath(POWER_COPPER_PORTRAIT), makePath(ENERGY_ORB_COPPER_PORTRAIT));
    }

    public static void initialize() {
    	logger.info("========================= FRUITYMOD INIT =========================");
		
		@SuppressWarnings("unused")
		JuggerMod Jug = new JuggerMod();
		
		logger.info("================================================================");
    }

    @Override
    public void receivePostInitialize() {
        // Mod badge
        Texture badgeTexture = new Texture(makePath(BADGE_IMG));
        ModPanel settingsPanel = new ModPanel();
        settingsPanel.addLabel("JuggerMod does not have any settings (yet)!", 400.0f, 700.0f, (me) -> {});
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        
        Settings.isDailyRun = false;
        Settings.isTrial = false;
        Settings.isDemo = false;
    }

	@Override
	public void receiveEditCharacters() {
		logger.info("begin editting characters");
		
		logger.info("add " + TheJuggernautEnum.THE_JUGGERNAUT.toString());
		BaseMod.addCharacter(TheJuggernaut.class, "The Juggernaut", "Juggernaut class string",
				AbstractCardEnum.COPPER.toString(), "The Juggernaut",
				makePath(SEEKER_BUTTON), makePath(SEEKER_PORTRAIT),
				TheJuggernautEnum.THE_JUGGERNAUT.toString());
		
		logger.info("done editting characters");
	}

	
	@Override
	public void receiveEditRelics() {
		logger.info("begin editting relics");
        
        // Add relics
		BaseMod.addRelicToCustomPool(new HeavyBody(), AbstractCardEnum.COPPER.toString());
        BaseMod.addRelicToCustomPool(new SneckoHeart(), AbstractCardEnum.COPPER.toString());
        BaseMod.addRelicToCustomPool(new RigidArmor(), AbstractCardEnum.COPPER.toString());
        BaseMod.addRelicToCustomPool(new BrittleRock(), AbstractCardEnum.COPPER.toString());
        BaseMod.addRelicToCustomPool(new KineticRock(), AbstractCardEnum.COPPER.toString());
        
        logger.info("done editting relics");
	}

	@Override
	public void receiveEditCards() {
		logger.info("begin editting cards");
		
		logger.info("add cards for " + TheJuggernautEnum.THE_JUGGERNAUT.toString());
		
		BaseMod.addCard(new Strike_Purple());
		BaseMod.addCard(new Defend_Purple());

        BaseMod.addCard(new HeavierBody());
        BaseMod.addCard(new Slugfest());
        BaseMod.addCard(new Improvisation());
        BaseMod.addCard(new Blitz());
        BaseMod.addCard(new BattleShout());
        BaseMod.addCard(new Charge());
        BaseMod.addCard(new Impenetrable());
        BaseMod.addCard(new Atlas());
        BaseMod.addCard(new OverflowingArmor());
        BaseMod.addCard(new PerfectedBlow());
        BaseMod.addCard(new CallToArms());
        BaseMod.addCard(new Mangle());
        BaseMod.addCard(new IndomitableWill());
        BaseMod.addCard(new SpikedArmor());
        BaseMod.addCard(new Colossus());
        BaseMod.addCard(new Overexert());
        BaseMod.addCard(new ApexPredator());
        BaseMod.addCard(new Feast());
        BaseMod.addCard(new GigaImpact());
        BaseMod.addCard(new Bide());
        BaseMod.addCard(new Cannibalize());
        BaseMod.addCard(new NaturalEndurance());
        BaseMod.addCard(new Shatter());
        BaseMod.addCard(new ReleaseRestraint());
        BaseMod.addCard(new CombatTraining());
        BaseMod.addCard(new Inertia());
        BaseMod.addCard(new BruteForce());
        BaseMod.addCard(new Mercurial());
        //BaseMod.addCard(new Reversal());
        BaseMod.addCard(new LivingArmor());
        BaseMod.addCard(new ThunderStruck());
        BaseMod.addCard(new DestructiveFinish());
        BaseMod.addCard(new Taunt());
        BaseMod.addCard(new HunkerDown());
        BaseMod.addCard(new InhumanRecovery());
        BaseMod.addCard(new Grapple());
        BaseMod.addCard(new HeavyArmor());
        BaseMod.addCard(new Bolster());
        BaseMod.addCard(new Breakthrough());
        BaseMod.addCard(new SlowAndSteady());
        BaseMod.addCard(new HeavyAssault());
        BaseMod.addCard(new UnstoppableForce());
        BaseMod.addCard(new LashOut());
        BaseMod.addCard(new SteelForce());
        BaseMod.addCard(new HeavyCrash());
        BaseMod.addCard(new Overpower());
        BaseMod.addCard(new Vengeance());
        BaseMod.addCard(new ConvertFlesh());
        BaseMod.addCard(new Unshakable());
        BaseMod.addCard(new FocusedPower());
        BaseMod.addCard(new Bulwark());
        BaseMod.addCard(new Accelerate());
        BaseMod.addCard(new Galvanize());
        BaseMod.addCard(new Endure());
        BaseMod.addCard(new Pursuit());
        BaseMod.addCard(new Bulldoze());
        BaseMod.addCard(new Feint());
        BaseMod.addCard(new CascadingSteel());
        BaseMod.addCard(new Smother());
        BaseMod.addCard(new Hysteria());
        BaseMod.addCard(new ChallengingRoar());
        BaseMod.addCard(new SimpleMinded());
        BaseMod.addCard(new Lunge());
        BaseMod.addCard(new RelentlessBlows());
        BaseMod.addCard(new FlyingPress());
        BaseMod.addCard(new Frenzy());
        BaseMod.addCard(new ShoulderBlow());
        BaseMod.addCard(new Pulverize());
        BaseMod.addCard(new HammerArm());
        BaseMod.addCard(new SkullBash());
        BaseMod.addCard(new Struggle());
        BaseMod.addCard(new Fury());
        BaseMod.addCard(new OnGuard());
        BaseMod.addCard(new Earthquake());
		
		// make sure everything is always unlocked
		UnlockTracker.unlockCard("Strike_P");
		UnlockTracker.unlockCard("Defend_P");
		
		/*UnlockTracker.unlockCard("Starburst");
		UnlockTracker.unlockCard("Irradiate");
		UnlockTracker.unlockCard("AstralHaze");
		UnlockTracker.unlockCard("Brainstorm");
		UnlockTracker.unlockCard("DarkMatter");
		UnlockTracker.unlockCard("ArcaneArmor");
		UnlockTracker.unlockCard("Entropy");
		UnlockTracker.unlockCard("EssenceDart");		
		UnlockTracker.unlockCard("Flicker");
		UnlockTracker.unlockCard("PlasmaWave");		
		UnlockTracker.unlockCard("PulseBarrier");
		UnlockTracker.unlockCard("Nebula");
		UnlockTracker.unlockCard("EtherBlast");
		UnlockTracker.unlockCard("Flare");		
		UnlockTracker.unlockCard("NullStorm");
		UnlockTracker.unlockCard("VoidRay");
		UnlockTracker.unlockCard("DisruptionField");
		UnlockTracker.unlockCard("UnstableOrb");
		UnlockTracker.unlockCard("Hypothesis");
		UnlockTracker.unlockCard("Comet");
		UnlockTracker.unlockCard("ForceRipple");
		UnlockTracker.unlockCard("PhaseCoil");
		UnlockTracker.unlockCard("Overload");
		UnlockTracker.unlockCard("Syzygy");
		UnlockTracker.unlockCard("SiphonPower");
		UnlockTracker.unlockCard("Shimmer");
		UnlockTracker.unlockCard("ThoughtRaze");
		UnlockTracker.unlockCard("Retrograde");
		UnlockTracker.unlockCard("Singularity");
		UnlockTracker.unlockCard("Umbra");
		UnlockTracker.unlockCard("Genesis");
		UnlockTracker.unlockCard("PrismaticSphere");
		UnlockTracker.unlockCard("Flux");
		UnlockTracker.unlockCard("Channel");
		UnlockTracker.unlockCard("Implosion");
		UnlockTracker.unlockCard("ChaosForm");
		UnlockTracker.unlockCard("Vacuum");
		UnlockTracker.unlockCard("DimensionDoor");
		UnlockTracker.unlockCard("Wormhole");
		UnlockTracker.unlockCard("Eureka");
		UnlockTracker.unlockCard("Eclipse");
		UnlockTracker.unlockCard("Echo");
		UnlockTracker.unlockCard("EventHorizon");
		UnlockTracker.unlockCard("Zenith");
		UnlockTracker.unlockCard("ReflectionWard");
		UnlockTracker.unlockCard("Creativity");
		UnlockTracker.unlockCard("Transference");
		UnlockTracker.unlockCard("Surge");
		UnlockTracker.unlockCard("StrokeOfGenius");
		UnlockTracker.unlockCard("SiphonSpeed");
		UnlockTracker.unlockCard("Convergence");
		UnlockTracker.unlockCard("GravityWell");
		UnlockTracker.unlockCard("Coalescence");
		UnlockTracker.unlockCard("PeriaptOfCelerity");
		UnlockTracker.unlockCard("PeriaptOfPotency");
		UnlockTracker.unlockCard("MeteorShower");
		UnlockTracker.unlockCard("PowerOverwhelming");
		UnlockTracker.unlockCard("MindOverMatter");
		UnlockTracker.unlockCard("Disperse");
		UnlockTracker.unlockCard("Magnetize");
		UnlockTracker.unlockCard("Illuminate");
		UnlockTracker.unlockCard("Flow");
		UnlockTracker.unlockCard("Equinox");
		UnlockTracker.unlockCard("Corona");
		UnlockTracker.unlockCard("Archives");
		UnlockTracker.unlockCard("MagicMissile");
		UnlockTracker.unlockCard("Enigma");
		UnlockTracker.unlockCard("Feedback");
		UnlockTracker.unlockCard("Brilliance");
		UnlockTracker.unlockCard("Anomaly");
		UnlockTracker.unlockCard("Nova");
		UnlockTracker.unlockCard("Vortex");
		UnlockTracker.unlockCard("Nexus");
		*/
		
		logger.info("done editting cards");
	}

	@Override
	public void receiveEditStrings() {
		logger.info("begin editting strings");
		
        // RelicStrings
        String relicStrings = Gdx.files.internal("localization/JuggerMod-RelicStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        // CardStrings
        String cardStrings = Gdx.files.internal("localization/JuggerMod-CardStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
		
		logger.info("done editting strings");
	}

	@Override
	public void receiveSetUnlocks() {
//		UnlockTracker.addCard("Flicker");
//		UnlockTracker.addCard("Transference");
//		UnlockTracker.addCard("ForceRipple");
//		// seeker unlock 1
//		BaseMod.addUnlockBundle(new CustomUnlockBundle(
//				"Flicker", "Transference", "ForceRipple"
//				), TheJuggernautEnum.THE_JUGGERNAUT, 1);
//		
//		// seeker unlock 2
//		BaseMod.addUnlockBundle(new CustomUnlockBundle(
//				"Channel", "Shimmer", "ThoughtRaze"
//				), TheJuggernautEnum.THE_JUGGERNAUT, 2);
//		UnlockTracker.addCard("Channel");
//		UnlockTracker.addCard("Shimmer");
//		UnlockTracker.addCard("ThoughtRaze");
//		
//		// seeker unlock 3 (Vacuum tmp in place of Feedback)
//		BaseMod.addUnlockBundle(new CustomUnlockBundle(
//				"Convergence", "Hypothesis", "Nexus"
//				), TheJuggernautEnum.THE_JUGGERNAUT, 3);
//		UnlockTracker.addCard("Convergence");
//		UnlockTracker.addCard("Hypothesis");
//		UnlockTracker.addCard("Nexus");
	}
	

	@Override
	public void receiveEditKeywords() {
        logger.info("setting up custom keywords");
        BaseMod.addKeyword(new String[] {"plated-armor", "Plated-Armor"}, "Gain Block equal to your Plated-Armor at the end of your turn. Taking damage from an attack reduces Plated-Armor by 1.");
        BaseMod.addKeyword(new String[] {"overflow", "Overflow"}, "When a card with Overflow is in your hand at the end of the turn, activate an effect.");
        BaseMod.addKeyword(new String[] {"draw-reduction", "Draw-Reduction"}, "Draw 1 less card at the beginning of your turn.");
        BaseMod.addKeyword(new String[] {"confusion", "Confusion"}, "The costs of your cards are randomized when they are drawn.");
	}
	
	//
	// Enigma hooks and functionality 	
	//
	
	// used by juggermod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CanUsedDazed
	public static boolean hasRelicCustom(String relicID, AbstractCard card) {
		System.out.println("I was checked!");
		// if it's checking for relicID.equals("Medical Kit") then we know we're in the block where
		// we are saying if we can use a status card so also check if we have enigma and the card is Dazed
		if (relicID.equals("Medical Kit") && AbstractDungeon.player.hasPower("EnigmaPower") && card.cardID.equals("Dazed")) {
			return true;
		} else {
			// otherwise leave normal behavior intact
			return AbstractDungeon.player.hasRelic(relicID);
		}
	}

	/*
    public static boolean hasRelicCustomI(String relicID) {
        System.out.println("I was checked!");
        // if it's checking for relicID.equals("Runic Pyramid") then we know we're in the block where
        // we are saying if we can use a status card so also check if we have enigma and the card is Dazed
        if (AbstractDungeon.player.hasPower("Inertia") ) {
            return true;
        } else {
            // otherwise leave normal behavior intact
            return AbstractDungeon.player.hasRelic(relicID);
        }
    }
    */

    // used by fruitmod.patches.com.megacrit.cardcrawl.cards.status.Dazed.UseDazed
	public static void maybeUseDazed(Dazed dazed) {
		System.out.println("maybe use dazed");
		if (!AbstractDungeon.player.hasPower("EnigmaPower")) {
			System.out.println("do use dazed");
			AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.utility.UseCardAction(dazed));
		} else {
			System.out.println("don't use dazed");
		}
	}
	
	@Override
	public void receiveCardUsed(AbstractCard c) {
		AbstractPlayer p = AbstractDungeon.player;
		if (p.hasPower("EnigmaPower") && c.cardID.equals("Dazed")) {
			AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, c.block));
			AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, 
					c.multiDamage,
					DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE, true));
			c.exhaustOnUseOnce = false;
		}
	}

	//
	// Relic code
	// (yes we're doing the exact same things the devs did where relic code
	// isn't in the actual relics - oh well)
	//
	
	private boolean moreThanXStacks(AbstractPlayer player, String powerID, int stacksWanted) {
		if (player != null && player.hasPower(powerID) && player.getPower(powerID).amount >= stacksWanted) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void receivePostBattle(AbstractRoom arg0) {
	}

	@Override
	public void receivePostDungeonInitialize() {
	}

	@Override
	public void receivePostDraw(AbstractCard c) {
	}

    @Override
    public void receivePowersModified() {
    }

    @Override
    public void receivePostExhaust(AbstractCard card) {
    }
}
