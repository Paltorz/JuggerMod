package juggermod.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.BaseModTags;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import juggermod.JuggerMod;
import juggermod.patches.AbstractCardEnum;

public class Defend_Purple extends CustomCard {
	public static final String ID = "Defend_P";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int BLOCK_AMT = 5;
	private static final int UPGRADE_PLUS_BLOCK = 3;
	private static final int POOL = 0;

	public Defend_Purple() {
		super(ID, NAME, JuggerMod.makePath(JuggerMod.DEFEND_PURPLE), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.COPPER, AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.SELF, POOL);

		this.baseBlock = BLOCK_AMT;
        CardTags.addTags(this, BaseModTags.BASIC_DEFEND);
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		if (com.megacrit.cardcrawl.core.Settings.isDebug) {
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, 50));
		} else {
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		}
	}

	public AbstractCard makeCopy() {
		return new Defend_Purple();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBlock(UPGRADE_PLUS_BLOCK);
		}
	}
}