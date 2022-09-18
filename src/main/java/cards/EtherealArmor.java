package cards;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import enums.Enums;

public class EtherealArmor extends CustomCard {
    public static final String ID = "Ethereal Armor";
    public static final String NAME = "Ethereal Armor";
    public static final String DESCRIPTION = "Ethereal. Gain !M! * X Temporal. If Exhausted, gain 5 Temporal instead.";
    public static final String IMG_PATH = "img/heal.png";
    private static final int COST = -1;
    private static final int TEMP_LIFE = 6;
    private static final int UPGRADE_TEMP_LIFE = 4;

    public EtherealArmor() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, Enums.INFERNO_YELLOW, CardRarity.UNCOMMON, CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = TEMP_LIFE;
        this.isEthereal = true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_TEMP_LIFE);
        }
    }

    @Override
    public void triggerOnExhaust() {
        super.triggerOnExhaust();
        AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(AbstractDungeon.player,AbstractDungeon.player, 5));
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int x = this.energyOnUse;
        AbstractDungeon.actionManager.addToBottom(new AddTemporaryHPAction(abstractPlayer,abstractPlayer, x * this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new EtherealArmor();
    }
}

