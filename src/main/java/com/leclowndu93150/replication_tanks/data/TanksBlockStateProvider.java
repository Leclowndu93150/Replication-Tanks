package com.leclowndu93150.replication_tanks.data;

import com.hrznstudio.titanium.block.RotatableBlock;
import com.leclowndu93150.replication_tanks.ReplicationTanks;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.VariantBlockStateBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.List;

public class TanksBlockStateProvider extends BlockStateProvider {

    private List<Block> blocks;

    public TanksBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper, List<Block> blocks) {
        super(gen.getPackOutput(), modid, exFileHelper);
        this.blocks = blocks;
    }

    @Override
    protected void registerStatesAndModels() {
        for (Block block : blocks) {
            String blockName = BuiltInRegistries.BLOCK.getKey(block).getPath();

            ModelFile blockModel = models().withExistingParent(blockName, ResourceLocation.fromNamespaceAndPath(ReplicationTanks.MODID, "block/base_tank"))
                    .texture("1", ResourceLocation.fromNamespaceAndPath(ReplicationTanks.MODID, "block/" + blockName));

            itemModels().withExistingParent(blockName, ResourceLocation.fromNamespaceAndPath(ReplicationTanks.MODID, "block/" + blockName));

            if (block instanceof RotatableBlock<?> rotatableBlock) {
                VariantBlockStateBuilder builder = getVariantBuilder(rotatableBlock);
                if (rotatableBlock.getRotationType().getProperties().length > 0) {
                    for (DirectionProperty property : rotatableBlock.getRotationType().getProperties()) {
                        for (Direction allowedValue : property.getPossibleValues()) {
                            builder.partialState().with(property, allowedValue)
                                    .addModels(new ConfiguredModel(
                                            blockModel,
                                            allowedValue.get2DDataValue() == -1 ? allowedValue.getOpposite().getAxisDirection().getStep() * 90 : 0,
                                            (int) allowedValue.getOpposite().toYRot(),
                                            false));
                        }
                    }
                } else {
                    builder.partialState().addModels(new ConfiguredModel(blockModel));
                }
            }
        }
    }
}
