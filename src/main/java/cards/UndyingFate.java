package cards;

import action.DamageCallbackV2Action;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import enums.Enums;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import power.Undying;

public class UndyingFate extends CustomCard {
    public static final String ID = "Undying Fate";
    public static final String NAME = "Undying Fate";
    public static final String DESCRIPTION = "Retain. Gain Undying. Gain !M! Temporary Health. If Retained, lose !D! health.";
    public static final String IMG_PATH = "img/heal.png";
    private static final int COST = 3;
    private static final int HEAL_POWER = 15;
    private static final int UPGRADE_HEAL_POWER = 10;
    private static final int RETAIN_COST = 3;
    public static final Logger logger = LogManager.getLogger(LifeDevour.class.getName());


    public UndyingFate() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.POWER, Enums.INFERNO_YELLOW, CardRarity.RARE, CardTarget.ALL);
        this.magicNumber = this.baseMagicNumber = HEAL_POWER;
        this.damage = this.baseDamage = RETAIN_COST;
        this.selfRetain = true;
    }

    @Override
    public void onRetained() {
        super.onRetained();
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, RETAIN_COST));
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_HEAL_POWER);
            this.upgradeBaseCost(2);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abstractPlayer, abstractPlayer, new Undying(abstractPlayer, 1)));
        AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(abstractPlayer, abstractPlayer, this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new UndyingFate();
    }
}
