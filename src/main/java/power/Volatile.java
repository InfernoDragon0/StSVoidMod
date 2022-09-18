package power;

import cards.Devouring;
import cards.Volatility;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class Volatile extends AbstractPower {
    public Volatile(AbstractCreature owner, int amount) {
        this.name = "Volatile";
        this.ID = "Volatile";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("accuracy");

        this.type = PowerType.DEBUFF;
        this.isTurnBased = false;
    }

    public void updateDescription() {
        this.description = "The next attack on this target applies " + this.amount + " damage to other enemies";
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(new LoseHPAction(AbstractDungeon.player, m, this.amount));
        }
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        return super.onAttacked(info, damageAmount);
    }
}
