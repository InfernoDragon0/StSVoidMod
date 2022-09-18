package cards;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.RemoveAllTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import enums.Enums;
import spireTogether.SpireTogetherMod;
import spireTogether.network.objets.PlayerGroup;
import spireTogether.other.PlayerRenderGroup;

import java.util.ArrayList;

public class EnergyOfLife extends CustomCard {
    public static final String ID = "LifeForce";
    public static final String NAME = "Life Force";
    public static final String DESCRIPTION = "Fireteam. Drain !M!. Grant [E] [E].";
    public static final String IMG_PATH = "img/heal.png";
    private static final int COST = 0;
    private static final int HEAL_AMT = 15;
    private static final int UPGRADE_PLUS_HEAL = 5;

    public EnergyOfLife() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, AbstractCard.CardType.SKILL, Enums.INFERNO_YELLOW, CardRarity.RARE, AbstractCard.CardTarget.ALL);
        this.magicNumber = this.baseMagicNumber = HEAL_AMT;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(-UPGRADE_PLUS_HEAL);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        AbstractDungeon.actionManager.addToBottom(new LoseHPAction(abstractPlayer, abstractPlayer, this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(2));

        if (SpireTogetherMod.isConnected) {
            ArrayList<Integer> players = PlayerGroup.GetCurrPlayersInRoomExceptSelf(true);
            for (Integer i : players) {
                PlayerRenderGroup.playerEntities[i].gainEnergy(2);
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new EnergyOfLife();
    }
}
