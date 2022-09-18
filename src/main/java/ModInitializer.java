import basemod.AutoAdd;
import basemod.BaseMod;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.OnStartBattleSubscriber;
import cards.HealEveryone;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import enums.Enums;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpireInitializer
public class ModInitializer implements OnStartBattleSubscriber, EditCardsSubscriber, EditKeywordsSubscriber {
    public static final Logger logger = LogManager.getLogger(ModInitializer.class.getName());

    public ModInitializer() {
        BaseMod.subscribe(this);
        BaseMod.addColor(Enums.INFERNO_YELLOW, CardHelper.getColor(254.0F, 223.0F, 0.0F), "hermitResources/images/512/bg_attack_default_gray.png", "hermitResources/images/512/bg_skill_default_gray.png", "hermitResources/images/512/bg_power_default_gray.png", "hermitResources/images/512/card_default_gray_orb.png", "hermitResources/images/1024/bg_attack_default_gray.png", "hermitResources/images/1024/bg_skill_default_gray.png", "hermitResources/images/1024/bg_power_default_gray.png", "hermitResources/images/1024/card_default_gray_orb.png", "hermitResources/images/512/card_small_orb.png");
    }

    //Used by @SpireInitializer
    public static void initialize(){

        //This creates an instance of our classes and gets our code going after BaseMod and ModTheSpire initialize.
        logger.info("infernomod is loaded!");
        ModInitializer modInitializer = new ModInitializer();
    }

    @Override
    public void receiveEditCards() {
        new AutoAdd("InfernoMod")
                .packageFilter(HealEveryone.class)
                .setDefaultSeen(true)
                .cards();
    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {

    }

    @Override
    public void receiveEditKeywords() {
        BaseMod.addKeyword(new String[]{"devour"}, "Gain Temporal based on unblocked damage done. Gain 10 HP and 4 Max HP if it is a final blow.");
        BaseMod.addKeyword(new String[]{"temporal"}, "Temporary Health (applies on top of current health)");
        BaseMod.addKeyword(new String[]{"drain"}, "Lose health or Temporal equal to amount stated");
        BaseMod.addKeyword(new String[]{"volatile"}, "The next attack on this target consumes all volatile and applies volatile as damage to all enemies.");
        BaseMod.addKeyword(new String[]{"fireteam"}, "Effects apply to all allies.");
        BaseMod.addKeyword(new String[]{"devouring rift"}, "Skills and attacks played that does not exhaust grant you Devouring or Volatility.");
        BaseMod.addKeyword(new String[]{"undying"}, "If you take fatal damage, lose that much Max HP instead and convert that amount to Temporal.");
    }
}
