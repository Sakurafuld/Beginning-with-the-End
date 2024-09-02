package com.sakurafuld.beginningwiththeend;

import moze_intel.projecte.gameObjs.registries.PEItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.sakurafuld.beginningwiththeend.Deets.*;


@Mod(WITH)
public class WithTheEnd
{
    public static final String TAG = "ValineLogInWithTablet";

    public WithTheEnd() {

        MinecraftForge.EVENT_BUS.register(this);

    }
    @SubscribeEvent
    public void loggedIn(PlayerEvent.Clone event) {
        required(LogicalSide.SERVER).run(() -> {
            if(!event.isWasDeath()) {
                LOG.debug("{}-notDeath", side());
                event.getEntity().getPersistentData().putBoolean(TAG, true);
            }
        });

    }
    @SubscribeEvent
    public void death(LivingEvent.LivingTickEvent event) {
        required(LogicalSide.SERVER).run(() -> {
            if(event.getEntity() instanceof Player player) {
                if((event.getEntity().level().getGameTime() & 40) == 0)
                    LOG.debug("{}-tickNotContain={}", side(), !player.getPersistentData().contains(TAG));
                if(!player.getPersistentData().contains(TAG)) {
                    LOG.debug("{}-tickAdd", side());
                    player.addItem(new ItemStack(PEItems.TRANSMUTATION_TABLET));
                    player.getPersistentData().putBoolean(TAG, true);
                }
            }
        });
    }
}
