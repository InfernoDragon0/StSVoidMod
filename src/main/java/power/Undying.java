package power;

import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import slimebound.actions.PreventCurrentOverMaxHealthAction;

public class Undying extends AbstractPower {

    public Undying(AbstractCreature owner, int amount) {
        this.name = "Undying";
        this.ID = "Undying Fate";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("vulnerable");

        this.type = PowerType.BUFF;
        this.isTurnBased = false;
    }

    public void atEndOfRound() {

    }

    public void updateDescription() {
        this.description = "If you take fatal damage, lose that much Max HP instead and convert that amount to Temporal.";
    }


    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (damageAmount >= this.owner.currentHealth) {
            if (damageAmount < this.owner.maxHealth) {
                AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(this.owner, this.owner, damageAmount));
                this.owner.decreaseMaxHealth(damageAmount);
                return super.onAttacked(info, 0);
            }
            else {
                return super.onAttacked(info, damageAmount);
            }
        }
        return super.onAttacked(info, damageAmount);
    }
}
