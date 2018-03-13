package juggermod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class DestructiveFinishAction extends AbstractGameAction{
    public static final String[] TEXT = new String[]{
            "discard"
    };
    private DamageInfo info;
    private static final float DURATION = 0.5f;
    private static final float POST_ATTACK_WAIT_DUR = 0.1f;
    private AbstractMonster m;

    public DestructiveFinishAction(AbstractMonster target, DamageInfo info, int amount) {
        this.amount = amount;
        this.info = info;
        //this.setValues((AbstractCreature)target, info);
        this.setValues(AbstractDungeon.player, AbstractDungeon.player, amount);
        this.m = target;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.attackEffect = AbstractGameAction.AttackEffect.BLUNT_HEAVY;
        this.duration = 0.5f;
    }

    @Override
    public void update() {
        if (this.target == null) {
            this.isDone = true;
            return;
        }
        if (this.duration == 0.5f) {
            AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, false, true, false, false, true);
            AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25f));
            this.tickDuration();
            return;
        }
        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                this.setValues(this.m, info);
                if (this.target != null && this.target.currentHealth > 0) {
                    if (this.info.type != DamageInfo.DamageType.THORNS && this.info.owner.isDying) {
                        this.isDone = true;
                        return;
                    }
                    this.target.damageFlash = true;
                    this.target.damageFlashFrames = 4;
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
                }
                this.tickDuration();
                if (this.target != null && this.target.currentHealth > 0) {
                    this.target.damage(this.info);
                    if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                        AbstractDungeon.actionManager.clearPostCombatActions();
                    }
                    AbstractDungeon.actionManager.addToTop(new WaitAction(0.1f));
                }
                AbstractDungeon.player.hand.moveToDiscardPile(c);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }
        this.tickDuration();
    }
}
