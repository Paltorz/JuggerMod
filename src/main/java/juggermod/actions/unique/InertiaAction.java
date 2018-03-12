package juggermod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class InertiaAction extends AbstractGameAction{

    public InertiaAction() {
        this.setValues(AbstractDungeon.player, AbstractDungeon.player);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
            for (AbstractCard c : AbstractDungeon.player.hand.group) {
                if (c == null) continue;
                if (!c.isEthereal) {
                    c.retain = true;
                }
            }
            this.isDone = true;
    }
}
