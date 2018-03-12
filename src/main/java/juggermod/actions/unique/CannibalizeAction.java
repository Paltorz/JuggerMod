package juggermod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class CannibalizeAction extends AbstractGameAction{
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RetainCardsAction");
    public static final String[] TEXT = {
            "Cannibalize"
    };

    public CannibalizeAction(AbstractCreature source, int amount) {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        if (this.duration == 0.5f) {
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false, false, false, false, false);
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25f));
            this.tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(c.costForTurn));
                AbstractDungeon.player.hand.moveToExhaustPile(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        this.tickDuration();
    }
}
