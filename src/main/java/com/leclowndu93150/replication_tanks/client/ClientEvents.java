package com.leclowndu93150.replication_tanks.client;

import com.leclowndu93150.replication_tanks.ReplicationTanks;
import com.leclowndu93150.replication_tanks.ReplicationTanksRegistry;
import com.leclowndu93150.replication_tanks.block.ReplicationTankBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = ReplicationTanks.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        ReplicationTanksRegistry.TANKS.values().forEach(blockWithTile -> {
            event.registerBlockEntityRenderer(
                    (BlockEntityType<ReplicationTankBlockEntity>) blockWithTile.type().get(),
                    ReplicationTankRenderer::new
            );
        });
    }
}
