package juggermod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PhantasmalPower;

public class PursuitAction extends AbstractGameAction {
    private AbstractMonster targetMonster;

    public PursuitAction(AbstractMonster m) {
        this.duration = 0.0f;
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.targetMonster = m;
    }

    @Override
    public void update() {
        if (this.targetMonster != null && !(this.targetMonster.intent == AbstractMonster.Intent.ATTACK || this.targetMonster.intent == AbstractMonster.Intent.ATTACK_BUFF || this.targetMonster.intent == AbstractMonster.Intent.ATTACK_DEBUFF || this.targetMonster.intent == AbstractMonster.Intent.ATTACK_DEFEND)) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new PhantasmalPower(AbstractDungeon.player, 1), 1));
        }
        this.isDone = true;
    }
}
