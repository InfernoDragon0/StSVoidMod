package cards;



import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.common.DamageCallbackAction;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.RemoveAllTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import enums.Enums;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Reversal extends CustomCard {
    public static final String ID = "Devour Reversal";
    public static final String NAME = "Reversal";
    public static final String DESCRIPTION = "Drain 5. Deal !D! + Temporal as damage !M! times to all enemies.";
    public static final String IMG_PATH = "img/heal.png";
    private static final int COST = 1;
    private static final int LIFE_COST = 5;
    private static final int DEVOUR_POWER = 2;
    private static final int UPGRADE_DEVOUR_POWER = 1;
    public static final Logger logger = LogManager.getLogger(Reversal.class.getName());


    public Reversal() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, Enums.INFERNO_YELLOW, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        this.damage = this.baseDamage = LIFE_COST;
        this.magicNumber = this.baseMagicNumber = DEVOUR_POWER;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_DEVOUR_POWER);
            this.upgradeBaseCost(0);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int totalTemphp = TempHPField.tempHp.get(abstractPlayer);
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(abstractPlayer,abstractPlayer, 5));
        for (int i = 0; i < this.magicNumber; i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(abstractPlayer, this.damage + totalTemphp, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Reversal();
    }
}
