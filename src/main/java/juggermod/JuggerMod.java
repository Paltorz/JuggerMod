package juggermod;

import java.nio.charset.StandardCharsets;

import juggermod.cards.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.OnPowersModifiedSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDrawSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostExhaustSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.SetUnlocksSubscriber;
import juggermod.actions.unique.ConvergenceAction;
import juggermod.characters.Juggernaut;
import juggermod.patches.AbstractCardEnum;
import juggermod.patches.JuggernautEnum;
import juggermod.relics.HeavyBody;

@SpireInitializer
public class JuggerMod implements PostInitializeSubscriber,
	EditCardsSubscriber, EditRelicsSubscriber, EditCharactersSubscriber,
	EditStringsSubscriber, SetUnlocksSubscriber, OnCardUseSubscriber,
	EditKeywordsSubscriber, OnPowersModifiedSubscriber, PostExhaustSubscriber,
	PostBattleSubscriber, PostDungeonInitializeSubscriber, PostDrawSubscriber {
	public static final Logger logger = LogManager.getLogger(JuggerMod.class.getName());
	
    private static final String MODNAME = "JuggerMod";
    private static final String AUTHOR = "Paltorz, and the FruityMod team";
    private static final String DESCRIPTION = "v0.4.3\n Adds Juggernaut as a playable third character";
    
    private static final Color PURPLE = CardHelper.getColor(85.0f, 50.0f, 0.0f);
    private static final String FRUITY_MOD_ASSETS_FOLDER = "img";
    
    // card backgrounds
    private static final String ATTACK_PURPLE = "512/bg_attack_purple.png";
    private static final String SKILL_PURPLE = "512/bg_attack_purple.png";
    private static final String POWER_PURPLE = "512/bg_attack_purple.png";
    private static final String ENERGY_ORB_PURPLE = "512/card_purple_orb.png";
    
    private static final String ATTACK_PURPLE_PORTRAIT = "1024/bg_attack_purple.png";
    private static final String SKILL_PURPLE_PORTRAIT = "1024/bg_attack_purple.png";
    private static final String POWER_PURPLE_PORTRAIT = "1024/bg_attack_purple.png";
    private static final String ENERGY_ORB_PURPLE_PORTRAIT = "1024/card_purple_orb.png";
    
    // card images
    public static final String STRIKE_PURPLE = "cards/strike_purple.png";
    public static final String DEFEND_PURPLE = "cards/defend_purple.png";

    public static final String EARTHQUAKE = "cards/phase_coil.png";
    public static final String ON_GUARD = "cards/corona.png";
    public static final String FURY = "cards/phase_coil.png";
    public static final String STRUGGLE = "cards/prismatic_sphere.png";
    public static final String SKULL_BASH = "cards/flux.png";
    public static final String HAMMER_ARM = "cards/overload.png";
    public static final String PULVERIZE = "cards/retrograde.png";
    public static final String SHOULDER_BLOW = "cards/thought_raze.png";
    public static final String FLYING_PRESS = "cards/syzygy.png";
    public static final String FRENZY = "cards/convergence.png";
    public static final String RELENTLESS_BLOWS = "cards/singularity.png";
    public static final String LUNGE = "cards/siphon_power.png";
    public static final String CHALLENGING_ROAR = "cards/disperse.png";
    public static final String SIMPLE_MINDED = "cards/mind_over_matter.png";
    public static final String HYSTERIA = "cards/disperse.png";
    public static final String SMOTHER = "cards/power_overwhelming.png";
    public static final String CASCADING_STEEL = "cards/reflection_ward.png";
    public static final String FEINT = "cards/unstable_orb.png";
    public static final String BULLDOZE = "cards/feedback.png";
    public static final String PURSUIT = "cards/astral_haze.png";
    public static final String ENDURE = "cards/genesis.png";
    public static final String GALVANIZE = "cards/nova.png";
    public static final String ACCELERATE = "cards/mind_over_matter.png";
    public static final String BULWARK = "cards/genesis.png";
    public static final String FOCUSED_POWER = "cards/archives.png";
    public static final String UNSHAKABLE = "cards/gravity_well.png";
    public static final String CONVERT_FLESH = "cards/corona.png";
    public static final String VENGEANCE = "cards/zenith.png";
    public static final String OVERPOWER = "cards/phase_coil.png";
    public static final String HEAVY_CRASH = "cards/singularity.png";
    public static final String STEEL_FORCE = "cards/disperse.png";
    public static final String LASH_OUT = "cards/syzygy.png";
    public static final String UNSTOPPABLE_FORCE = "cards/vortex.png";
    public static final String HEAVY_ASSAULT = "cards/thought_raze.png";
    public static final String SLOW_AND_STEADY = "cards/convergence.png";
    public static final String BREAKTHROUGH = "cards/shimmer.png";
    public static final String HEAVY_ARMOR = "cards/flow.png";
    public static final String GRAPPLE = "cards/entropy.png";
    public static final String INHUMAN_RECOVERY = "cards/flux.png";
    public static final String HUNKER_DOWN = "cards/runic_binding.png";
    public static final String TAUNT = "cards/coalescence.png";
    public static final String DESTRUCTIVE_FINISH = "cards/archives.png";
    public static final String THUNDER_STRUCK = "cards/enigma.png";
    public static final String LIVING_ARMOR = "cards/chaos_form.png";
    public static final String REVERSAL = "cards/reflection_ward.png";
    public static final String MERCURIAL = "cards/implosion.png";
    public static final String BRUTE_FORCE = "cards/stroke_of_genius.png";
    public static final String INERTIA = "cards/disperse.png";
    public static final String COMBAT_TRAINING = "cards/retrograde.png";
    public static final String RELEASE_RESTRAINT = "cards/chaos_form.png";
    public static final String SHATTER = "cards/nova.png";
    public static final String NATURAL_ENDURANCE = "cards/flicker.png";
    public static final String BOLSTER = "cards/umbral_bolt.png";
    public static final String CANNIBALIZE = "cards/overload.png";
    public static final String BIDE = "cards/anomaly.png";
    public static final String GIGA_IMPACT = "cards/force_ripple.png";
    public static final String FEAST = "cards/thought_raze.png";
    public static final String APEX_PREDATOR = "cards/vacuum.png";
    public static final String OVEREXERT = "cards/feedback.png";
    public static final String COLOSSUS = "cards/echo.png";
    public static final String SPIKED_ARMOR = "cards/reflection_ward.png";
    public static final String INDOMITABLE_WILL = "cards/zenith.png";
    public static final String MANGLE = "cards/transference.png";
    public static final String CALL_TO_ARMS = "cards/brainstorm.png";
    public static final String PERFECTED_BLOW = "cards/unstable_orb.png";
    public static final String OVERFLOWING_ARMOR = "cards/mind_over_matter.png";
    public static final String ATLAS = "cards/retrograde.png";
    
    // power images
    public static final String ASTRAL_HAZE_POWER = "powers/astral_haze.png";
    public static final String ESSENCE_MIRROR_POWER = "powers/essence_mirror.png";
    public static final String ETHEREALIZE_POWER = "powers/essence_mirror.png";
    public static final String ASTRAL_FORM_POWER = "powers/chaos_form.png";
    public static final String VIGOR_POWER = "powers/vigor.png";
    public static final String ASTRAL_SHIFT_POWER = "powers/astral_shift.png";
    public static final String TENACITY_POWER = "powers/tenacity.png";
    public static final String CELERITY_POWER = "powers/celerity.png";
    public static final String POTENCY_POWER = "powers/potency.png";
    public static final String COALESCENCE_POWER = "powers/coalescence.png";
    public static final String CREATIVITY_POWER = "powers/creativity.png";
    public static final String POWER_OVERWHELMING_POWER = "powers/power_overwhelming.png";
    public static final String EVENT_HORIZON_POWER = "powers/event_horizon.png";
    public static final String ENIGMA_POWER = "powers/enigma.png";
    public static final String BRILLIANCE_POWER = "powers/brilliance.png";
    public static final String ANOMALY_POWER = "powers/anomaly.png";
    public static final String NEXUS_POWER = "powers/nexus.png";
    public static final String BRUTE_FORCE_POWER = "powers/power_overwhelming.png";
    public static final String INERTIA_POWER = "powers/astral_haze.png";
    public static final String COMBAT_TRAINING_POWER = "powers/chaos_form.png";
    public static final String RELEASE_RESTRAINT_POWER = "powers/astral_shift.png";
    public static final String SHATTER_POWER = "powers/brilliance.png";
    public static final String NATURAL_ENDURANCE_POWER = "powers/vigor.png";
    public static final String BIDE_POWER = "powers/essence_mirror.png";
    public static final String GIGA_IMPACT_POWER = "powers/event_horizon.png";
    public static final String COLOSSUS_POWER = "powers/potency.png";
    public static final String SPIKED_ARMOR_POWER = "powers/creativity.png";
    public static final String INDOMITABLE_WILL_POWER = "powers/tenacity.png";
    public static final String MANGLE_POWER = "powers/enigma.png";
    public static final String OVERFLOWING_BLOCK_POWER = "powers/coalescence.png";
    public static final String OVERFLOWING_PLATE_POWER = "powers/nexus.png";
    public static final String ATLAS_POWER = "powers/nexus.png";

    // relic images

    public static final String HEAVY_BODY_RELIC = "relics/mechanicalCore.png";

    
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

    public static Texture getAstralHazePowerTexture() {
        return new Texture(makePath(ASTRAL_HAZE_POWER));
    }

    public static Texture getEssenceMirrorPowerTexture() {
        return new Texture(makePath(ESSENCE_MIRROR_POWER));
    }

    public static Texture getEtherealizePowerTexture() {
        return new Texture(makePath(ETHEREALIZE_POWER));
    }

    public static Texture getAstralFormPowerTexture() {
        return new Texture(makePath(ASTRAL_FORM_POWER));
    }

    public static Texture getVigorPowerTexture() {
        return new Texture(makePath(VIGOR_POWER));
    }

    public static Texture getAstralShiftTexture() {
        return new Texture(makePath(ASTRAL_SHIFT_POWER));
    }

    public static Texture getTenacityPowerTexture() {
        return new Texture(makePath(TENACITY_POWER));
    }

    public static Texture getCelerityPowerTexture() {
        return new Texture(makePath(CELERITY_POWER));
    }

    public static Texture getPotencyPowerTexture() {
        return new Texture(makePath(POTENCY_POWER));
    }

    public static Texture getCoalescencePowerTexture() {
        return new Texture(makePath(COALESCENCE_POWER));
    }

    public static Texture getCreativityPowerTexture() {
        return new Texture(makePath(CREATIVITY_POWER));
    }

    public static Texture getPowerOverwhelmingPowerTexture() {
        return new Texture(makePath(POWER_OVERWHELMING_POWER));
    }

    public static Texture getEventHorizonPowerTexture() {
        return new Texture(makePath(EVENT_HORIZON_POWER));
    }

    public static Texture getEnigmaPowerTexture() {
        return new Texture(makePath(ENIGMA_POWER));
    }


    public static Texture getHeavyBodyTexture() { return new Texture(makePath(HEAVY_BODY_RELIC)); }

    public static Texture getBrillancePowerTexture() { return new Texture(makePath(BRILLIANCE_POWER)); }

    public static Texture getAnomalyPowerTexture() {
        return new Texture(makePath(BRILLIANCE_POWER));
    }

    public static Texture getNexusPowerTexture() {
        return new Texture(makePath(VIGOR_POWER));
    }

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
        logger.info("creating the color " + AbstractCardEnum.BROWN.toString());
        BaseMod.addColor(AbstractCardEnum.BROWN.toString(),
        		PURPLE, PURPLE, PURPLE, PURPLE, PURPLE, PURPLE, PURPLE,
        		makePath(ATTACK_PURPLE), makePath(SKILL_PURPLE),
        		makePath(POWER_PURPLE), makePath(ENERGY_ORB_PURPLE),
        		makePath(ATTACK_PURPLE_PORTRAIT), makePath(SKILL_PURPLE_PORTRAIT),
        		makePath(POWER_PURPLE_PORTRAIT), makePath(ENERGY_ORB_PURPLE_PORTRAIT));
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
		
		logger.info("add " + JuggernautEnum.JUGGERNAUT.toString());
		BaseMod.addCharacter(Juggernaut.class, "Juggernaut", "Juggernaut class string",
				AbstractCardEnum.BROWN.toString(), "Juggernaut",
				makePath(SEEKER_BUTTON), makePath(SEEKER_PORTRAIT),
				JuggernautEnum.JUGGERNAUT.toString());
		
		logger.info("done editting characters");
	}

	
	@Override
	public void receiveEditRelics() {
		logger.info("begin editting relics");
        
        // Add relics
		BaseMod.addRelicToCustomPool(new HeavyBody(), AbstractCardEnum.BROWN.toString());
        
        logger.info("done editting relics");
	}

	@Override
	public void receiveEditCards() {
		logger.info("begin editting cards");
		
		logger.info("add cards for " + JuggernautEnum.JUGGERNAUT.toString());
		
		BaseMod.addCard(new Strike_Purple());
		BaseMod.addCard(new Defend_Purple());

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
        //BaseMod.addCard(new Bide());
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
//				), JuggernautEnum.JUGGERNAUT, 1);
//		
//		// seeker unlock 2
//		BaseMod.addUnlockBundle(new CustomUnlockBundle(
//				"Channel", "Shimmer", "ThoughtRaze"
//				), JuggernautEnum.JUGGERNAUT, 2);
//		UnlockTracker.addCard("Channel");
//		UnlockTracker.addCard("Shimmer");
//		UnlockTracker.addCard("ThoughtRaze");
//		
//		// seeker unlock 3 (Vacuum tmp in place of Feedback)
//		BaseMod.addUnlockBundle(new CustomUnlockBundle(
//				"Convergence", "Hypothesis", "Nexus"
//				), JuggernautEnum.JUGGERNAUT, 3);
//		UnlockTracker.addCard("Convergence");
//		UnlockTracker.addCard("Hypothesis");
//		UnlockTracker.addCard("Nexus");
	}
	

	@Override
	public void receiveEditKeywords() {
        logger.info("setting up custom keywords");
        BaseMod.addKeyword(new String[] {"Plated-Armor", "Plated-Armor"}, "Gain Block equal to your Plated-Armor at the end of your turn. Taking damage from an attack reduces Plated-Armor by 1.");
        BaseMod.addKeyword(new String[] {"overflow", "Overflow"}, "When a card with Overflow is in your hand at the end of the turn, activate an effect.");
        BaseMod.addKeyword(new String[] {"draw-reduction", "Draw-Reduction"}, "Draw 1 less card at the beginning of your turn.");
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
