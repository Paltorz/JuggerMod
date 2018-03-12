package juggermod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class TauntAction extends AbstractGameAction{
    int STR_LOSS;

    public TauntAction(int STR) {
        STR_LOSS = STR;
        this.duration = 0.0f;
        this.actionType = AbstractGameAction.ActionType.WAIT;
    }

    @Override
    public void update() {
        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (mo != null && !(mo.intent == AbstractMonster.Intent.ATTACK || mo.intent == AbstractMonster.Intent.ATTACK_BUFF || mo.intent == AbstractMonster.Intent.ATTACK_DEBUFF || mo.intent == AbstractMonster.Intent.ATTACK_DEFEND)) {
                AbstractDungeon.actionManager.addToBottom(new LoseBlockAction(mo, AbstractDungeon.player, mo.currentBlock));
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(mo, AbstractDungeon.player, new StrengthPower(mo, STR_LOSS), STR_LOSS, true));
            }
        }
        this.isDone = true;
    }
}
