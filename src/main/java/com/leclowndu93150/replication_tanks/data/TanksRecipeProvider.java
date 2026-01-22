package com.leclowndu93150.replication_tanks.data;

import com.buuz135.replication.ReplicationRegistry;
import com.buuz135.replication.api.MatterType;
import com.hrznstudio.titanium.recipe.generator.TitaniumShapedRecipeBuilder;
import com.leclowndu93150.replication_tanks.ReplicationTanks;
import com.leclowndu93150.replication_tanks.ReplicationTanksRegistry;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.Tags;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class TanksRecipeProvider extends RecipeProvider {

    private final Supplier<List<Block>> blocksToProcess;

    public TanksRecipeProvider(DataGenerator generatorIn, Supplier<List<Block>> blocksToProcess, CompletableFuture<HolderLookup.Provider> registries) {
        super(generatorIn.getPackOutput(), registries);
        this.blocksToProcess = blocksToProcess;
    }

    @Override
    public void buildRecipes(RecipeOutput consumer) {
        for (MatterType type : ReplicationTanksRegistry.getMatterTypes()) {
            ItemLike coreItem = getCoreItem(type);
            TagKey<Item> coreTag = getCoreTag(type);

            for (int tier = 1; tier <= 3; tier++) {
                String name = ReplicationTanksRegistry.getTankName(type, tier);
                var tank = ReplicationTanksRegistry.TANKS.get(name);
                if (tank == null) continue;

                if (tier == 1) {
                    if (coreTag != null) {
                        TitaniumShapedRecipeBuilder.shapedRecipe(tank.getBlock())
                                .pattern("IGI")
                                .pattern("GCG")
                                .pattern("IGI")
                                .define('I', ReplicationRegistry.Items.REPLICA_INGOT.get())
                                .define('G', Tags.Items.GLASS_BLOCKS)
                                .define('C', coreTag)
                                .save(consumer, ResourceLocation.fromNamespaceAndPath(ReplicationTanks.MODID, name));
                    } else {
                        TitaniumShapedRecipeBuilder.shapedRecipe(tank.getBlock())
                                .pattern("IGI")
                                .pattern("GCG")
                                .pattern("IGI")
                                .define('I', ReplicationRegistry.Items.REPLICA_INGOT.get())
                                .define('G', Tags.Items.GLASS_BLOCKS)
                                .define('C', coreItem)
                                .save(consumer, ResourceLocation.fromNamespaceAndPath(ReplicationTanks.MODID, name));
                    }
                } else {
                    String prevName = ReplicationTanksRegistry.getTankName(type, tier - 1);
                    var prevTank = ReplicationTanksRegistry.TANKS.get(prevName);
                    ItemLike upgradeItem = getUpgradeItem(type, tier);
                    ItemLike replicaItem = tier == 3 ? ReplicationRegistry.Blocks.REPLICA_BLOCK.get() : ReplicationRegistry.Items.REPLICA_INGOT.get();

                    TitaniumShapedRecipeBuilder.shapedRecipe(tank.getBlock())
                            .pattern("UIU")
                            .pattern("IPI")
                            .pattern("UIU")
                            .define('P', prevTank.getBlock())
                            .define('I', replicaItem)
                            .define('U', upgradeItem)
                            .save(consumer, ResourceLocation.fromNamespaceAndPath(ReplicationTanks.MODID, name));
                }
            }
        }
    }

    private ItemLike getCoreItem(MatterType type) {
        return switch (type) {
            case EARTH -> Items.STONE;
            case NETHER -> Items.NETHERRACK;
            case ORGANIC -> Items.OAK_LOG;
            case ENDER -> Items.END_STONE;
            case METALLIC -> Items.IRON_BLOCK;
            case PRECIOUS -> Items.GOLD_BLOCK;
            case LIVING -> Items.SLIME_BLOCK;
            case QUANTUM -> Items.CRYING_OBSIDIAN;
            default -> Items.STONE;
        };
    }

    private TagKey<Item> getCoreTag(MatterType type) {
        return switch (type) {
            case EARTH -> Tags.Items.STONES;
            case ORGANIC -> ItemTags.LOGS;
            case METALLIC -> Tags.Items.STORAGE_BLOCKS_IRON;
            case PRECIOUS -> Tags.Items.STORAGE_BLOCKS_GOLD;
            default -> null;
        };
    }

    private ItemLike getUpgradeItem(MatterType type, int tier) {
        if (tier == 2) {
            return switch (type) {
                case EARTH -> Items.DEEPSLATE;
                case NETHER -> Items.SOUL_SAND;
                case ORGANIC -> Items.MOSS_BLOCK;
                case ENDER -> Items.CHORUS_FRUIT;
                case METALLIC -> Items.IRON_BLOCK;
                case PRECIOUS -> Items.DIAMOND;
                case LIVING -> Items.HONEYCOMB;
                case QUANTUM -> Items.ECHO_SHARD;
                default -> Items.IRON_INGOT;
            };
        } else {
            return switch (type) {
                case EARTH -> Items.CALCITE;
                case NETHER -> Items.ANCIENT_DEBRIS;
                case ORGANIC -> Items.SPORE_BLOSSOM;
                case ENDER -> Items.SHULKER_SHELL;
                case METALLIC -> Items.NETHERITE_INGOT;
                case PRECIOUS -> Items.EMERALD_BLOCK;
                case LIVING -> Items.NETHER_STAR;
                case QUANTUM -> Items.DRAGON_BREATH;
                default -> Items.DIAMOND;
            };
        }
    }
}
