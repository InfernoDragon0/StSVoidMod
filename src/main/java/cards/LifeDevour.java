package cards;

import action.DamageCallbackV2Action;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import enums.Enums;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LifeDevour extends CustomCard {
    public static final String ID = "Life Devour";
    public static final String NAME = "Life Devour";
    public static final String DESCRIPTION = "Retain, Drain 1, Devour !M!. If Retained, Drain 1, increase Devour by 4.";
    public static final String IMG_PATH = "img/heal.png";
    private static final int COST = 2;
    private static final int LIFE_COST = 1;
    private static final int DEVOUR_POWER = 15;
    private static final int UPGRADE_DEVOUR_POWER = 4;
    private static final int RETAIN_COST = 1;
    public static final Logger logger = LogManager.getLogger(LifeDevour.class.getName());


    public LifeDevour() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, Enums.INFERNO_YELLOW, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = DEVOUR_POWER;
        this.damage = this.baseDamage = LIFE_COST;
        this.selfRetain = true;
    }

    @Override
    public void onRetained() {
        super.onRetained();
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, RETAIN_COST));
        this.upgradeMagicNumber(UPGRADE_DEVOUR_POWER);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_DEVOUR_POWER);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(abstractPlayer, abstractPlayer, RETAIN_COST));
        DamageInfo di = new DamageInfo(abstractPlayer, this.magicNumber, DamageInfo.DamageType.NORMAL);
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
        return new LifeDevour();
    }
}
