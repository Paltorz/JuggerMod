package juggermod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class FeastAction extends AbstractGameAction{
    private int healAmount;
    private DamageInfo info;
    private static final float DURATION = 0.1f;

    public FeastAction(AbstractCreature target, DamageInfo info, int healAmount) {
        this.info = info;
        this.setValues(target, info);
        this.healAmount = healAmount;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.duration = 0.1f;
    }

    @Override
    public void update() {
        if (this.duration == 0.1f && this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            this.target.damage(this.info);
            if (!(!((AbstractMonster)this.target).isDying && this.target.currentHealth > 0 || this.target.halfDead || this.target.hasPower("Minion"))) {
                AbstractDungeon.player.heal(this.healAmount);
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        this.tickDuration();
    }
}