package juggermod.powers;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDrawSubscriber;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import juggermod.JuggerMod;

public class ChaosFormPower extends AbstractPower implements PostDrawSubscriber, PostBattleSubscriber {
	public static final String POWER_ID = "ChaosFormPower";
	public static final String NAME = "Chaos Form";
	public static final String[] DESCRIPTIONS = new String[] {
			"Whenever you draw an Ethereal card deal ",
			" damage to a random enemy."
	};
	
	public ChaosFormPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.priority = 90;
		this.img = JuggerMod.getAstralFormPowerTexture();
	}
	
	@Override
	public void onInitialApplication() {
		BaseMod.subscribeToPostDraw(this);
		BaseMod.subscribeToPostBattle(this);
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}

	@Override
	public void receivePostDraw(AbstractCard c) {
		AbstractPlayer player = (AbstractPlayer) owner;
		if (c.isEthereal) {
			this.flash();
			
			AbstractDungeon.actionManager.addToBottom(new DamageAction(
					AbstractDungeon.getMonsters().getRandomMonster(true),
					new DamageInfo(player, this.amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
		}
		
	}

	@Override
	public void receivePostBattle(AbstractRoom arg0) {
		System.out.println("should be removed now!");
		BaseMod.unsubscribeFromPostDraw(this);
		/*
		 *  calling unsubscribeFromPostBattle inside the callback
		 *  for receivePostBattle means that when we're calling it
		 *  there is currently an iterator going over the list
		 *  of subscribers and calling receivePostBattle on each of
		 *  them therefore if we immediately try to remove the this
		 *  callback from the post battle subscriber list it will
		 *  throw a concurrent modification exception in the iterator
		 *  
		 *  for now we just add a delay - yes this is an atrocious solution
		 *  PLEASE someone with a better idea replace it
		 */
		Thread delayed = new Thread(() -> {
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				System.out.println("could not delay unsubscribe to avoid ConcurrentModificationException");
				e.printStackTrace();
			}
			BaseMod.unsubscribeFromPostBattle(this);
		});
		delayed.start();
	}
	
}
