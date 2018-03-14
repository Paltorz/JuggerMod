package juggermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import juggermod.JuggerMod;
import juggermod.patches.AbstractCardEnum;

public class Earthquake extends CustomCard {
	public static final String ID = "Earthquake";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int ATTACK_DMG = 2;
	private static final int UPGRADE_PLUS_DMG = 3;
	private static final int VULNERABLE_AMT = 2;
	private static final int UPGRADE_PLUS_VULNERABLE = 0;
	private static final int POOL = 1;

	public Earthquake() {
		super(ID, NAME, JuggerMod.makePath(JuggerMod.EARTHQUAKE), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.COPPER, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ALL_ENEMY, POOL);
		this.isMultiDamage = true;
		this.magicNumber = this.baseMagicNumber = VULNERABLE_AMT;
		this.damage = this.baseDamage = ATTACK_DMG;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new SFXAction("THUNDERCLAP", 0.05f));
		for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
			if (mo.isDeadOrEscaped())
				continue;
			AbstractDungeon.actionManager.addToBottom(new VFXAction(new LightningEffect(mo.drawX, mo.drawY), 0.05f));
		}
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage,
				this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
		for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
			AbstractDungeon.actionManager
					.addToBottom(new ApplyPowerAction(mo, p, new VulnerablePower(mo, this.magicNumber, false),
							this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
		}
	}

	@Override
	public AbstractCard makeCopy() {
		return new Earthquake();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_DMG);
			this.upgradeMagicNumber(UPGRADE_PLUS_VULNERABLE);
		}
	}
}
