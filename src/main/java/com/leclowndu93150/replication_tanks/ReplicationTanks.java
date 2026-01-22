package com.leclowndu93150.replication_tanks;

import com.buuz135.replication.api.MatterType;
import com.buuz135.replication.block.MatterPipeBlock;
import com.hrznstudio.titanium.module.ModuleController;
import com.hrznstudio.titanium.tab.TitaniumTab;
import com.leclowndu93150.replication_tanks.block.ReplicationTankBlock;
import com.leclowndu93150.replication_tanks.data.*;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.slf4j.Logger;

import java.util.List;

@Mod(ReplicationTanks.MODID)
public class ReplicationTanks extends ModuleController {

    public static final String MODID = "replication_tanks";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static TitaniumTab TAB = new TitaniumTab(ResourceLocation.fromNamespaceAndPath(MODID, "main"));

    public ReplicationTanks(IEventBus modEventBus, ModContainer modContainer, Dist dist) {
        super(modContainer);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        modEventBus.addListener(this::onCommonSetup);
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            MatterPipeBlock.ALLOWED_CONNECTION_BLOCKS.add(
                block -> BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(MODID)
            );
        });
    }

    @Override
    protected void initModules() {
        this.addCreativeTab("main", () -> new ItemStack(ReplicationTanksRegistry.EARTH_TANK_T1.getBlock()), "replication_tanks", TAB);

        for (MatterType type : ReplicationTanksRegistry.getMatterTypes()) {
            for (int tier = 1; tier <= 3; tier++) {
                String name = ReplicationTanksRegistry.getTankName(type, tier);
                int capacity = ReplicationTanksRegistry.getCapacityForTier(tier);

                final MatterType finalType = type;
                final int finalTier = tier;
                final int finalCapacity = capacity;

                var blockWithTile = this.getRegistries().registerBlockWithTile(name,
                    () -> new ReplicationTankBlock(name, finalType, finalTier, finalCapacity), TAB);

                ReplicationTanksRegistry.TANKS.put(name, blockWithTile);
                setStaticField(type, tier, blockWithTile);
            }
        }
    }

    @Override
    public void addDataProvider(GatherDataEvent event) {
        super.addDataProvider(event);
        List<Block> blocks = BuiltInRegistries.BLOCK.stream()
                .filter(block -> BuiltInRegistries.BLOCK.getKey(block).getNamespace().equals(MODID))
                .toList();

        event.getGenerator().addProvider(true, new TanksBlockStateProvider(event.getGenerator(), MODID, event.getExistingFileHelper(), blocks));
        event.getGenerator().addProvider(true, new TanksLootTableProvider(event.getGenerator(), () -> blocks, event.getLookupProvider()));
        event.getGenerator().addProvider(true, new TanksLangProvider(event.getGenerator(), MODID, "en_us", blocks));
        var blockTags = new TanksBlockTagsProvider(event.getGenerator().getPackOutput(), event.getLookupProvider(), MODID, event.getExistingFileHelper(), blocks);
        event.getGenerator().addProvider(true, blockTags);
        event.getGenerator().addProvider(true, new TanksItemTagsProvider(event.getGenerator(), event.getLookupProvider(), blockTags.contentsGetter(), MODID, event.getExistingFileHelper()));
        event.getGenerator().addProvider(true, new TanksRecipeProvider(event.getGenerator(), () -> blocks, event.getLookupProvider()));
    }

    private void setStaticField(MatterType type, int tier, com.hrznstudio.titanium.module.BlockWithTile blockWithTile) {
        switch (type) {
            case EARTH -> {
                switch (tier) {
                    case 1 -> ReplicationTanksRegistry.EARTH_TANK_T1 = blockWithTile;
                    case 2 -> ReplicationTanksRegistry.EARTH_TANK_T2 = blockWithTile;
                    case 3 -> ReplicationTanksRegistry.EARTH_TANK_T3 = blockWithTile;
                }
            }
            case NETHER -> {
                switch (tier) {
                    case 1 -> ReplicationTanksRegistry.NETHER_TANK_T1 = blockWithTile;
                    case 2 -> ReplicationTanksRegistry.NETHER_TANK_T2 = blockWithTile;
                    case 3 -> ReplicationTanksRegistry.NETHER_TANK_T3 = blockWithTile;
                }
            }
            case ORGANIC -> {
                switch (tier) {
                    case 1 -> ReplicationTanksRegistry.ORGANIC_TANK_T1 = blockWithTile;
                    case 2 -> ReplicationTanksRegistry.ORGANIC_TANK_T2 = blockWithTile;
                    case 3 -> ReplicationTanksRegistry.ORGANIC_TANK_T3 = blockWithTile;
                }
            }
            case ENDER -> {
                switch (tier) {
                    case 1 -> ReplicationTanksRegistry.ENDER_TANK_T1 = blockWithTile;
                    case 2 -> ReplicationTanksRegistry.ENDER_TANK_T2 = blockWithTile;
                    case 3 -> ReplicationTanksRegistry.ENDER_TANK_T3 = blockWithTile;
                }
            }
            case METALLIC -> {
                switch (tier) {
                    case 1 -> ReplicationTanksRegistry.METALLIC_TANK_T1 = blockWithTile;
                    case 2 -> ReplicationTanksRegistry.METALLIC_TANK_T2 = blockWithTile;
                    case 3 -> ReplicationTanksRegistry.METALLIC_TANK_T3 = blockWithTile;
                }
            }
            case PRECIOUS -> {
                switch (tier) {
                    case 1 -> ReplicationTanksRegistry.PRECIOUS_TANK_T1 = blockWithTile;
                    case 2 -> ReplicationTanksRegistry.PRECIOUS_TANK_T2 = blockWithTile;
                    case 3 -> ReplicationTanksRegistry.PRECIOUS_TANK_T3 = blockWithTile;
                }
            }
            case LIVING -> {
                switch (tier) {
                    case 1 -> ReplicationTanksRegistry.LIVING_TANK_T1 = blockWithTile;
                    case 2 -> ReplicationTanksRegistry.LIVING_TANK_T2 = blockWithTile;
                    case 3 -> ReplicationTanksRegistry.LIVING_TANK_T3 = blockWithTile;
                }
            }
            case QUANTUM -> {
                switch (tier) {
                    case 1 -> ReplicationTanksRegistry.QUANTUM_TANK_T1 = blockWithTile;
                    case 2 -> ReplicationTanksRegistry.QUANTUM_TANK_T2 = blockWithTile;
                    case 3 -> ReplicationTanksRegistry.QUANTUM_TANK_T3 = blockWithTile;
                }
            }
        }
    }
}
