package com.leclowndu93150.replication_tanks.compat;

import com.buuz135.replication.api.matter_fluid.MatterStack;
import com.buuz135.replication.block.tile.BaseMatterTankBlockEntity;
import com.buuz135.replication.util.NumberUtils;
import com.leclowndu93150.replication_tanks.ReplicationTanks;
import com.leclowndu93150.replication_tanks.ReplicationTanksRegistry;
import com.leclowndu93150.replication_tanks.block.ReplicationTankBlock;
import com.leclowndu93150.replication_tanks.block.ReplicationTankBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.impl.ui.ProgressElement;

import java.awt.*;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {

    private static final ResourceLocation MATTER_TANK_ID = ResourceLocation.fromNamespaceAndPath(ReplicationTanks.MODID, "matter_tank");

    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(TankComponentProvider.INSTANCE, ReplicationTankBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(TankComponentProvider.INSTANCE, ReplicationTankBlock.class);
    }

    public enum TankComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
        INSTANCE;

        @Override
        public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
            if (accessor.getServerData().contains("MatterStack")) {
                var matterStack = MatterStack.loadMatterStackFromNBT(accessor.getServerData().getCompound("MatterStack"));
                var floatColor = matterStack.getMatterType().getColor().get();
                var color = new Color(floatColor[0], floatColor[1], floatColor[2], floatColor[3]);

                double capacity = accessor.getServerData().contains("Capacity")
                        ? accessor.getServerData().getInt("Capacity")
                        : ReplicationTanksRegistry.TIER_1_CAPACITY;

                tooltip.add(new ProgressElement((float) (matterStack.getAmount() / capacity),
                        matterStack.isEmpty() ? Component.translatable("tooltip.titanium.tank.empty") : Component.translatable(matterStack.getTranslationKey()).append(" ").append(NumberUtils.getFormatedBigNumber(matterStack.getAmount())),
                        IElementHelper.get().progressStyle().color(color.getRGB()).textColor(0xFFFFFF), BoxStyle.getNestedBox(), false));
            }
        }

        @Override
        public ResourceLocation getUid() {
            return MATTER_TANK_ID;
        }

        @Override
        public void appendServerData(CompoundTag data, BlockAccessor accessor) {
            if (accessor.getBlockEntity() instanceof ReplicationTankBlockEntity blockEntity) {
                data.put("MatterStack", blockEntity.getTanks().get(0).getMatter().writeToNBT(new CompoundTag()));
                data.putInt("Capacity", blockEntity.getCapacity());
            }
        }
    }
}
