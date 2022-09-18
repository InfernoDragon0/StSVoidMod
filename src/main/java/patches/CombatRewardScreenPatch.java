package patches;

import cards.*;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

@SpirePatch(
        clz = CombatRewardScreen.class,
        method = "setupItemReward"
)
public class CombatRewardScreenPatch {
    public static final Logger logger = LogManager.getLogger(CombatRewardScreenPatch.class.getName());
    public static final ArrayList<String> relicExclusion = new ArrayList<>();
    public static final ArrayList<AbstractCard> devourCards = new ArrayList<>();

    public CombatRewardScreenPatch() {
        logger.info("patcher loaded for combatrewardscreen");

    }

    public static void Prefix(Object __obj_instance) {
        relicExclusion.clear();
        relicExclusion.add("Tiny House");
        relicExclusion.add("Cauldron");
        relicExclusion.add("Orrery");

        devourCards.clear();
        devourCards.add(new ArmorOfLight());

        devourCards.add(new EnergyOfLife());
        devourCards.add(new EnhancedDevour());
        devourCards.add(new EtherealArmor());

        devourCards.add(new HealEveryone());

        devourCards.add(new LifeDevour());

        devourCards.add(new Reversal());

        devourCards.add(new SiphoningBlade());

        devourCards.add(new UndyingFate());

        CombatRewardScreen crs = (CombatRewardScreen)__obj_instance;
        if ((AbstractDungeon.getCurrRoom().event == null || AbstractDungeon.getCurrRoom().event != null && !AbstractDungeon.getCurrRoom().event.noCardsInRewards) && !(AbstractDungeon.getCurrRoom() instanceof TreasureRoom) && !(AbstractDungeon.getCurrRoom() instanceof RestRoom)) {
            AbstractRoom abstractRoom = AbstractDungeon.getCurrRoom();
            Random rand = new Random();
            int totalRelics = Math.max(1, rand.nextInt(3)); //min 1 relic
            logger.info("infernomod: we have stolen " + totalRelics + " relics to give you");
            for (int i = 0; i < totalRelics; i++) {
                int rarity = Math.max(2, rand.nextInt(8));
                logger.info("infernomod: generating a relic at rarity " + rarity);
                AbstractRelic.RelicTier rt = AbstractRelic.RelicTier.values()[rarity];
                AbstractRelic rel = AbstractDungeon.returnRandomRelic(rt);
                if (relicExclusion.contains(rel.relicId)) {
                    logger.info("Illegal relic stolen, rewarding circlet instead");
                    rel = RelicLibrary.getRelic("Circlet");
                }
                abstractRoom.addRelicToRewards(rel);
            }

            //add custom cards reward
            RewardItem ri = new RewardItem();
            ri.cards.clear();
            ArrayList<Integer> shuffle = new ArrayList<>();
            for (int i = 0; i < devourCards.size(); i++) shuffle.add(i);
            Collections.shuffle(shuffle);

            for (int i = 0; i < 3; i++) {
                ri.cards.add(devourCards.get(shuffle.get(i)).makeCopy());
            }

            abstractRoom.addCardReward(ri);
        }
    }
}
