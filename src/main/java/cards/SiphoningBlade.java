package cards;

import action.DamageCallbackV2Action;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import enums.Enums;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SiphoningBlade extends CustomCard {
    public static final String ID = "SiphoningBlade";
    public static final String NAME = "Siphoning Blade";
    public static final String DESCRIPTION = "Devour 5.";
    public static final String IMG_PATH = "img/heal.png";
    private static final int COST = 0;
    private static final int LIFE_COST = 5;
    private static final int UPGRADE_LIFE_COST = 3;

    public static final Logger logger = LogManager.getLogger(LifeDevour.class.getName());


    public SiphoningBlade() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, Enums.INFERNO_YELLOW, CardRarity.COMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = LIFE_COST;
        this.exhaustOnUseOnce = true;
        this.isEthereal = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_LIFE_COST);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        DamageInfo di = new DamageInfo(abstractPlayer, this.damage, DamageInfo.DamageType.NORMAL);
        AbstractDungeon.actionManager.addToBottom(new DamageCallbackV2Action(abstractMonster, di, AbstractGameAction.AttackEffect.POISON, target -> {
            AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(abstractPlayer, abstractPlayer, target.lastDamageTaken));
            if (target.isDying) {
                logger.info("monster died add max hp");
                abstractPlayer.increaseMaxHp(4, true);
                AbstractDungeon.actionManager.addToBottom(new HealAction(abstractPlayer, abstractPlayer, 10));
            }
            else {
                logger.info("monster has health left: " + target.currentHealth);
            }
        }));

    }

    @Override
    public AbstractCard makeCopy() {
        return new SiphoningBlade();
    }
}
