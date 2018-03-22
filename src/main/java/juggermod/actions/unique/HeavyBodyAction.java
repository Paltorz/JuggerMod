package juggermod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class HeavyBodyAction extends AbstractGameAction {
    private static final int TMP_STRENGTH = 3;
    private static final int TMP_STRENGTH_UP = 5;
    private static final int BLOCK_AMT = 5;
    private static final int BLOCK_AMT_UP = 8;
    private static final int PLATE_AMT = 2;
    private static final int PLATE_AMT_UP = 3;
    private static final int DRAW_AMT = 1;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RetainCardsAction");
    public static final String[] TEXT = {
            "gain an effect based on its type"
    };

    public HeavyBodyAction(AbstractCreature source, int amount) {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    }

    @Override
    public void update() {
        if (this.duration == 0.5f) {
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, true, true, false, false, false);
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25f));
            this.tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                switch (c.type) {
                    case ATTACK:
                        if (AbstractDungeon.player.hasPower("Heavier Body")){
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, TMP_STRENGTH_UP), TMP_STRENGTH_UP));
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseStrengthPower(AbstractDungeon.player, TMP_STRENGTH_UP), TMP_STRENGTH_UP));
                        }else {
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, TMP_STRENGTH), TMP_STRENGTH));
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseStrengthPower(AbstractDungeon.player, TMP_STRENGTH), TMP_STRENGTH));
                        }
                        break;
                    case SKILL:
                        if (AbstractDungeon.player.hasPower("Heavier Body")) {
                            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, BLOCK_AMT_UP));
                        }else{
                            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, BLOCK_AMT));
                        }
                        break;
                    case POWER:
                        if (AbstractDungeon.player.hasPower("Heavier Body")) {
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player, PLATE_AMT_UP), PLATE_AMT_UP));
                        }else {
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player, PLATE_AMT), PLATE_AMT));
                        }
                        break;
                    default:
                        if (AbstractDungeon.player.hasPower("Heavier Body")) {
                            AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, DRAW_AMT));
                            AbstractDungeon.actionManager.addToTop(new ExhaustAction(AbstractDungeon.player, AbstractDungeon.player, 1, false));
                        }else {
                            AbstractDungeon.actionManager.addToTop(new ExhaustAction(AbstractDungeon.player, AbstractDungeon.player, 1, false));
                        }
                        break;
                }
                AbstractDungeon.player.hand.addToTop(c);
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
            }
            this.tickDuration();
        }
        AbstractDungeon.player.hand.refreshHandLayout();
        AbstractDungeon.player.hand.glowCheck();
        this.isDone = true;
    }
}
