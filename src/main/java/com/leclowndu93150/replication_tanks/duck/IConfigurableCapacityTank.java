package com.leclowndu93150.replication_tanks.duck;

import com.buuz135.replication.api.IMatterType;

public interface IConfigurableCapacityTank {
    void replicationTanks$setCapacity(int capacity);
    int replicationTanks$getCapacity();
    boolean replicationTanks$hasCustomCapacity();
    void replicationTanks$setMatterTypeFilter(IMatterType matterType);
}
