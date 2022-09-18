package cards;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.RemoveAllTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import enums.Enums;
import spireTogether.SpireTogetherMod;
import spireTogether.network.objets.PlayerGroup;
import spireTogether.other.PlayerRenderGroup;

import java.util.ArrayList;

public class HealEveryone extends CustomCard {
    public static final String ID = "HealEveryone";
    public static final String NAME = "Mass Heal";
    public static final String DESCRIPTION = "Fireteam, Self Heal for 5 + !M!% of your Temporary Health. Drain all Temporal. Allies gain Block instead.";
    public static final String IMG_PATH = "img/heal.png";
    private static final int COST = 2;
    private static final int HEAL_PERCENT_AMT = 50;
    private static final int UPGRADE_PLUS_HEAL = 25;

    public HealEveryone() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, Enums.INFERNO_YELLOW, CardRarity.RARE, CardTarget.ALL);
        this.magicNumber = this.baseMagicNumber = HEAL_PERCENT_AMT;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_HEAL);
            this.upgradeBaseCost(1);
        }
    }

    @Override
    public void use(AbstractPlayer abstractPlayer, AbstractMonster abstractMonster) {
        int totalTemphp = TempHPField.tempHp.get(abstractPlayer) * (this.magicNumber / 100);
        AbstractDungeon.actionManager.addToBottom(new RemoveAllTemporaryHPAction(abstractPlayer, abstractPlayer));
        AbstractDungeon.actionManager.addToBottom(new HealAction(abstractPlayer, abstractPlayer, 5 + totalTemphp));

        if (SpireTogetherMod.isConnected) {
            ArrayList<Integer> players = PlayerGroup.GetCurrPlayersInRoomExceptSelf(true);
            for (Integer i : players) {
                PlayerRenderGroup.playerEntities[i].addBlock(5 + totalTemphp);
            }
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new HealEveryone();
    }
}
