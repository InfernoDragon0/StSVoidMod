package cards;

import action.DamageCallbackV2Action;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import enums.Enums;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import power.Volatile;

public class Volatility extends CustomCard {
    public static final String ID = "Volatility";
    public static final String NAME = "Volatility";
    public static final String DESCRIPTION = "Volatile 3, Ethereal, Exhaust.";
    public static final String IMG_PATH = "img/heal.png";
    private static final int COST = 0;
    private static final int VOLATILES = 3;
    private static final int UPGRADE_VOLATILES = 2;

    public static final Logger logger = LogManager.getLogger(LifeDevour.class.getName());


    public Volatility() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, Enums.INFERNO_YELLOW, CardRarity.COMMON, CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = VOLATILES;
        this.exhaustOnUseOnce = true;
        this.isEthereal = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_VOLATILES);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abstractMonster, abstractPlayer, new Volatile(abstractMonster, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Volatility();
    }
}
