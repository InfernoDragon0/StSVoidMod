package power;

import cards.Devouring;
import cards.Volatility;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class Devour extends AbstractPower {

    public Devour(AbstractCreature owner, int amount) {
        this.name = "Devouring Rift";
        this.ID = "Enhanced Devour";
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("accuracy");

        this.type = PowerType.BUFF;
        this.isTurnBased = false;
    }

    public void atEndOfRound() {

    }

    public void updateDescription() {
        this.description = "Skills and attacks played that does not exhaust grants you Devouring or Volatility.";
    }

    @Override
    public void onAfterCardPlayed(AbstractCard usedCard) {
        if (!usedCard.exhaustOnUseOnce) {
            switch (usedCard.type) {
                case ATTACK:
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Devouring()));
                    break;
                case SKILL:
                    AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new Volatility()));
                    break;
            }
        }

        super.onAfterCardPlayed(usedCard);
    }

    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        super.onAttack(info, damageAmount, target);
        int totalTemphp = TempHPField.tempHp.get(this.owner);
        if (!target.isPlayer && totalTemphp < 50) {
            if (totalTemphp + damageAmount > 50) {
                damageAmount = 50 - totalTemphp;
            }
            AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(this.owner, this.owner, damageAmount));

        }

    }



}
