package com.leclowndu93150.replication_tanks.block;

import com.buuz135.replication.api.IMatterType;
import com.buuz135.replication.block.tile.BaseMatterTankBlockEntity;
import com.leclowndu93150.replication_tanks.duck.IConfigurableCapacityTank;
import com.hrznstudio.titanium.block.BasicTileBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ReplicationTankBlockEntity extends BaseMatterTankBlockEntity<ReplicationTankBlockEntity> {

    private final IMatterType matterType;
    private final int capacity;

    public ReplicationTankBlockEntity(BasicTileBlock<ReplicationTankBlockEntity> base, BlockEntityType<?> blockEntityType, BlockPos pos, BlockState state, IMatterType matterType, int capacity) {
        super(base, blockEntityType, pos, state, () -> false);
        this.matterType = matterType;
        this.capacity = capacity;
        ((IConfigurableCapacityTank) this).replicationTanks$setCapacity(capacity);
        ((IConfigurableCapacityTank) this).replicationTanks$setMatterTypeFilter(matterType);
    }

    @Override
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        super.saveAdditional(compound, provider);
        compound.putInt("CustomCapacity", capacity);
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        super.loadAdditional(compound, provider);
        if (compound.contains("CustomCapacity")) {
            int savedCapacity = compound.getInt("CustomCapacity");
            ((IConfigurableCapacityTank) this).replicationTanks$setCapacity(savedCapacity);
        }
        ((IConfigurableCapacityTank) this).replicationTanks$setMatterTypeFilter(matterType);
    }

    public IMatterType getMatterType() {
        return matterType;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public @NotNull ReplicationTankBlockEntity getSelf() {
        return this;
    }
}
