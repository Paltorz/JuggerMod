package juggermod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import juggermod.JuggerMod;
import juggermod.patches.AbstractCardEnum;

public class Struggle extends CustomCard {
	public static final String ID = "Struggle";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int ATTACK_DMG = 15;
	private static final int UPGRADE_PLUS_DMG = 5;
	private static final int POOL = 1;

	public Struggle() {
		super(ID, NAME, JuggerMod.makePath(JuggerMod.STRUGGLE), COST, DESCRIPTION,
				AbstractCard.CardType.ATTACK, AbstractCardEnum.COPPER,
				AbstractCard.CardRarity.COMMON,
				AbstractCard.CardTarget.ENEMY, POOL);

		this.baseDamage = ATTACK_DMG;
	}
	
    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();        
        this.setCostForTurn(getHandCount());
    }
    
    @Override
    public void applyPowers() {
    	super.applyPowers();        
    	this.setCostForTurn(getHandCount());
    }
    
    private int getHandCount() {
    	int count = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
			if (c==null) continue;
            ++count;
        }
        return count-1;
    }


	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
			AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
					new DamageInfo(p, this.damage, this.damageTypeForTurn),
					AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
	}

	public AbstractCard makeCopy() {
		return new Struggle();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(UPGRADE_PLUS_DMG);
		}
	}
}