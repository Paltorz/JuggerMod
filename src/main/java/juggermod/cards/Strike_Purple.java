package juggermod.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModTags;
import basemod.helpers.CardTags;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import juggermod.JuggerMod;
import juggermod.patches.AbstractCardEnum;

public class Strike_Purple extends CustomCard {
	public static final String ID = "Strike_P";
	public static final String NAME = "Strike";
	public static final String DESCRIPTION = "Deal !D! damage.";
	private static final int COST = 1;
	private static final int ATTACK_DMG = 6;
	private static final int UPGRADE_PLUS_DMG = 3;

	public Strike_Purple() {
		super(ID, NAME, JuggerMod.makePath(JuggerMod.STRIKE_PURPLE), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.COPPER, AbstractCard.CardRarity.BASIC,
				AbstractCard.CardTarget.ENEMY);

		this.baseDamage = ATTACK_DMG;
		CardTags.addTags(this, BaseModTags.BASIC_STRIKE, BaseModTags.STRIKE);
	}

	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
		if (com.megacrit.cardcrawl.core.Settings.isDebug) {
			if (com.megacrit.cardcrawl.core.Settings.isInfo) {
				this.multiDamage = new int[AbstractDungeon.getCurrRoom().monsters.monsters.size()];
				for (int i = 0; i < AbstractDungeon.getCurrRoom().monsters.monsters.size(); i++) {
					this.multiDamage[i] = 150;
				}
				AbstractDungeon.actionManager.addToBottom(
						new com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction(p, this.multiDamage,
								this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
			} else {
				AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
						new DamageInfo(p, 150, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
			}
		} else {
			AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
					new DamageInfo(p, this.damage, this.damageTypeForTurn),
					AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
		}
	}

	public AbstractCard makeCopy() {
		return new Strike_Purple();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_PLUS_DMG);
		}
	}
}