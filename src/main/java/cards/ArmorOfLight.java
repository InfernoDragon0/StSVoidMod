package cards;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.RemoveAllTemporaryHPAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import enums.Enums;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import spireTogether.SpireTogetherMod;
import spireTogether.network.objets.PlayerGroup;
import spireTogether.other.PlayerRenderGroup;

import java.util.ArrayList;

public class ArmorOfLight extends CustomCard {
    public static final String ID = "Weapon of Light";
    public static final String NAME = "Weapon of Light";
    public static final String DESCRIPTION = "Fireteam, Grants !M! Block and Drain all Temporal. Grant X Weapon of Lights.";
    public static final String IMG_PATH = "img/heal.png";
    private static final int COST = -1;
    private static final int STR_POWER = 25;
    private static final int UPGRADE_STR_POWER = 5;
    public static final Logger logger = LogManager.getLogger(LifeDevour.class.getName());

    public ArmorOfLight() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, Enums.INFERNO_YELLOW, CardRarity.RARE, CardTarget.SELF);
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
        AbstractDungeon.actionManager.addToBottom(new RemoveAllTemporaryHPAction(abstractPlayer, abstractPlayer));
        int x = this.energyOnUse;
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new WeaponOfLight(), x));
            abstractPlayer.addBlock(this.magicNumber);
            if (SpireTogetherMod.isConnected) {
                ArrayList<Integer> players = PlayerGroup.GetCurrPlayersInRoomExceptSelf(true);
                for (Integer i : players) {
                    PlayerRenderGroup.playerEntities[i].addBlock(this.magicNumber);
                    for (int j = 0; j < x; j ++) {
                        PlayerRenderGroup.playerEntities[i].addCardToHand(new WeaponOfLight());
                    }
                }
            }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ArmorOfLight();
    }
}
