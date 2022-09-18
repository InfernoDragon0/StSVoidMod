package cards;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.RemoveAllTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import enums.Enums;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import power.Devour;

public class EnhancedDevour extends CustomCard {
    public static final String ID = "Enhanced Devour";
    public static final String NAME = "Devouring Rift";
    public static final String DESCRIPTION = "Drain all Temporal. Grants Devouring Rift.";
    public static final String IMG_PATH = "img/heal.png";
    private static final int COST = 2;
    private static final int LIFE_COST = 20;
    private static final int DEVOUR_POWER = 1;
    private static final int UPGRADE_DEVOUR_POWER = 1;
    public static final Logger logger = LogManager.getLogger(LifeDevour.class.getName());


    public EnhancedDevour() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.POWER, Enums.INFERNO_YELLOW, CardRarity.RARE, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = DEVOUR_POWER;
        this.damage = this.baseDamage = LIFE_COST;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(COST - UPGRADE_DEVOUR_POWER);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractDungeon.actionManager.addToBottom(new RemoveAllTemporaryHPAction(abstractPlayer, abstractPlayer));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abstractPlayer, abstractPlayer, new Devour(abstractPlayer, 1)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnhancedDevour();
    }
}
