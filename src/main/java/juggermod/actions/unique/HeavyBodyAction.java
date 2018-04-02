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
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class HeavyBodyAction extends AbstractGameAction {
    private static final int TMP_STRENGTH = 3;
    private static final int TMP_STRENGTH_UP = 2;
    private static final int BLOCK_AMT = 5;
    private static final int BLOCK_AMT_UP = 3;
    private static final int PLATE_AMT = 2;
    private static final int PLATE_AMT_UP = 1;
    private static final int DRAW_AMT = 1;
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RetainCardsAction");
    public static final String[] TEXT = {
            "gain an effect based on its type"
    };

    public HeavyBodyAction(AbstractCreature source, int amount) {
        this.setValues(AbstractDungeon.player, source, amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
    }

    private int GetPowerCount(AbstractCreature c, String powerId) {
        AbstractPower power =  c.getPower(powerId);
        return power != null ? power.amount : 0;
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
            int heavierBodyCount = GetPowerCount(AbstractDungeon.player, "Heavier Body");
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                switch (c.type) {
                    case ATTACK:
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, TMP_STRENGTH + heavierBodyCount * TMP_STRENGTH_UP), TMP_STRENGTH + heavierBodyCount * TMP_STRENGTH_UP));
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseStrengthPower(AbstractDungeon.player, TMP_STRENGTH + heavierBodyCount * TMP_STRENGTH_UP), TMP_STRENGTH + heavierBodyCount * TMP_STRENGTH_UP));
                        break;
                    case SKILL:
                            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, BLOCK_AMT + heavierBodyCount * BLOCK_AMT_UP));
                        break;
                    case POWER:
                            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PlatedArmorPower(AbstractDungeon.player, PLATE_AMT + heavierBodyCount * PLATE_AMT_UP), PLATE_AMT + heavierBodyCount * PLATE_AMT_UP));
                        break;
                    default:
                        if (AbstractDungeon.player.hasPower("Heavier Body")) {
                            AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, heavierBodyCount * DRAW_AMT));
                        }
                            AbstractDungeon.actionManager.addToTop(new ExhaustAction(AbstractDungeon.player, AbstractDungeon.player, 1, false));
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
