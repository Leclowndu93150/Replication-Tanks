package com.leclowndu93150.replication_tanks.mixin;

import com.buuz135.replication.api.IMatterType;
import com.buuz135.replication.block.tile.BaseMatterTankBlockEntity;
import com.buuz135.replication.container.component.LockableMatterTankBundle;
import com.leclowndu93150.replication_tanks.duck.IConfigurableCapacityTank;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BaseMatterTankBlockEntity.class)
public abstract class BaseMatterTankBlockEntityMixin implements IConfigurableCapacityTank {

    @Shadow
    private LockableMatterTankBundle<?> lockableMatterTankBundle;

    @Unique
    private int replicationTanks$customCapacity = -1;

    @Unique
    private boolean replicationTanks$hasCustomCapacity = false;

    @Override
    public void replicationTanks$setCapacity(int capacity) {
        this.replicationTanks$customCapacity = capacity;
        this.replicationTanks$hasCustomCapacity = true;
        if (this.lockableMatterTankBundle != null && this.lockableMatterTankBundle.getTank() != null) {
            this.lockableMatterTankBundle.getTank().setCapacity(capacity);
        }
    }

    @Override
    public int replicationTanks$getCapacity() {
        return this.replicationTanks$customCapacity;
    }

    @Override
    public boolean replicationTanks$hasCustomCapacity() {
        return this.replicationTanks$hasCustomCapacity;
    }

    @Override
    public void replicationTanks$setMatterTypeFilter(IMatterType matterType) {
        if (this.lockableMatterTankBundle != null && this.lockableMatterTankBundle.getTank() != null) {
            this.lockableMatterTankBundle.getTank().setValidator(
                matterStack -> matterStack.getMatterType().equals(matterType)
            );
        }
    }
}
