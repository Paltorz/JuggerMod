package juggermod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class BulldozeAction extends AbstractGameAction{
    private DamageInfo info;
    private static final float DURATION = 0.01f;
    private static final float POST_ATTACK_WAIT_DUR = 0.1f;
    private AbstractMonster m;

    public BulldozeAction(AbstractMonster target, DamageInfo info) {
        this.info = info;
        this.setValues((AbstractCreature)target, info);
        this.m = target;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.attackEffect = AbstractGameAction.AttackEffect.BLUNT_HEAVY;
        this.duration = 0.01f;
    }

    @Override
    public void update() {
        if (this.target == null) {
            this.isDone = true;
            return;
        }
        if (this.m.hasPower("Vulnerable") || this.m.hasPower("Weakened")){
            if (this.duration == 0.01f && this.target != null && this.target.currentHealth > 0) {
                if (this.info.type != DamageInfo.DamageType.THORNS && this.info.owner.isDying) {
                    this.isDone = true;
                    return;
                }
                this.target.damageFlash = true;
                this.target.damageFlashFrames = 4;
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
            }
            this.tickDuration();
            if (this.isDone && this.target != null && this.target.currentHealth > 0) {
                this.target.damage(this.info);
                if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                }
                AbstractDungeon.actionManager.addToTop(new WaitAction(0.1f));
            }
        } else {
            this.isDone = true;
        }
    }
}
