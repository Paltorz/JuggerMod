package juggermod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class CallToArmsAction extends AbstractGameAction {
    public static final String[] TEXT = new String[]{
        "Select a card to add to hand."
    };
    private AbstractPlayer p = AbstractDungeon.player;

    public CallToArmsAction(int amount) {
        this.setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            CardGroup tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : this.p.drawPile.group) {
                tmp.addToRandomSpot(c);
            }
            if (tmp.size() == 0) {
                this.isDone = true;
                return;
            }
            if (tmp.size() == 1) {
                AbstractCard card = tmp.getTopCard();
                if (this.p.hand.size() == 10) {
                    this.p.drawPile.moveToDiscardPile(card);
                    this.p.createHandIsFullDialog();
                } else {
                    card.unhover();
                    card.lighten(true);
                    card.setAngle(0.0f);
                    card.drawScale = 0.12f;
                    card.targetDrawScale = 0.75f;
                    card.current_x = CardGroup.DRAW_PILE_X;
                    card.current_y = CardGroup.DRAW_PILE_Y;
                    this.p.drawPile.removeCard(card);
                    AbstractDungeon.player.hand.addToTop(card);
                    AbstractDungeon.player.hand.refreshHandLayout();
                    AbstractDungeon.player.hand.applyPowers();
                }
                this.isDone = true;
                return;
            }
            AbstractDungeon.gridSelectScreen.open(tmp, this.amount, TEXT[0], false);
            this.tickDuration();
            return;
        }
        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                c.unhover();
                if (this.p.hand.size() == 10) {
                    this.p.drawPile.moveToDiscardPile(c);
                    this.p.createHandIsFullDialog();
                } else {
                    this.p.drawPile.removeCard(c);
                    this.p.hand.addToTop(c);
                }
                this.p.hand.refreshHandLayout();
                this.p.hand.applyPowers();
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.p.hand.refreshHandLayout();
        }
        this.tickDuration();
    }
}
