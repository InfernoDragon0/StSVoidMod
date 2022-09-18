package cards;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import enums.Enums;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import power.Undying;

public class WeaponOfLight extends CustomCard {
    public static final String ID = "Weapon of Light";
    public static final String NAME = "Weapon of Light";
    public static final String DESCRIPTION = "Ethereal, Exhaust. Gain !M! Temporary Strength.";
    public static final String IMG_PATH = "img/heal.png";
    private static final int COST = 0;
    private static final int STR_POWER = 8;
    private static final int UPGRADE_STR_POWER = 2;
    public static final Logger logger = LogManager.getLogger(LifeDevour.class.getName());

    public WeaponOfLight() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, Enums.INFERNO_YELLOW, CardRarity.COMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = STR_POWER;
        this.exhaustOnUseOnce = true;
        this.isEthereal = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_STR_POWER);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abstractPlayer, abstractPlayer, new StrengthPower(abstractPlayer, this.magicNumber)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(abstractPlayer, abstractPlayer, new LoseStrengthPower(abstractPlayer, this.magicNumber)));
    }

    @Override
    public AbstractCard makeCopy() {
        return new WeaponOfLight();
    }
}
