package com.sakurafuld.beginningwiththeend;

import moze_intel.projecte.gameObjs.registries.PEItems;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

import static com.sakurafuld.beginningwiththeend.Deets.*;


@Mod(WITH)
public class WithTheEnd {
    public static final String TAG = "ValineLogInWithTablet";

    public WithTheEnd() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void clone(PlayerEvent.Clone event) {
        required(LogicalSide.SERVER).run(() -> {
            if(!event.isWasDeath()) {
                event.getEntity().getPersistentData().putBoolean(TAG, true);
            }
        });
    }
    @SubscribeEvent
    public void tick(LivingEvent.LivingTickEvent event) {
        required(LogicalSide.SERVER).run(() -> {
            if(event.getEntity() instanceof Player player && !player.getPersistentData().contains(TAG)) {
                player.addItem(new ItemStack(PEItems.TRANSMUTATION_TABLET));
                player.getPersistentData().putBoolean(TAG, true);
            }
        });
    }
}
