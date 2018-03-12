package juggermod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.utility.QueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MercurialAction
        extends AbstractGameAction {
    private int mercurialTimes;

    public MercurialAction(AbstractCreature target, int times) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = target;
        this.mercurialTimes = times;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.drawPile.size() + AbstractDungeon.player.discardPile.size() == 0) {
                this.isDone = true;
                return;
            }
            if (AbstractDungeon.player.drawPile.isEmpty()) {
                AbstractDungeon.actionManager.addToTop(new MercurialAction(this.target, this.mercurialTimes));
                AbstractDungeon.actionManager.addToTop(new EmptyDeckShuffleAction());
                this.isDone = true;
                return;
            }
            for (int i = 0; i < this.mercurialTimes; ++i) {
                if (AbstractDungeon.player.drawPile.isEmpty()) continue;
                AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
                AbstractDungeon.player.drawPile.group.remove(card);
                AbstractDungeon.getCurrRoom().souls.remove(card);
                card.freeToPlayOnce = true;
                AbstractDungeon.player.limbo.group.add(card);
                card.current_y = -200.0f * Settings.scale;
                card.target_x = (float)Settings.WIDTH / 2.0f + (float)(i * 200);
                card.target_y = (float)Settings.HEIGHT / 2.0f;
                card.targetAngle = 0.0f;
                card.lighten(false);
                card.drawScale = 0.12f;
                card.targetDrawScale = 0.75f;
                if (!card.canUse(AbstractDungeon.player, (AbstractMonster)this.target)) {
                    AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(card, AbstractDungeon.player.limbo));
                    continue;
                }
                card.applyPowers();
                AbstractDungeon.actionManager.addToTop(new QueueCardAction(card, this.target));
                AbstractDungeon.actionManager.addToTop(new UnlimboAction(card));
            }
            this.isDone = true;
        }
    }
}