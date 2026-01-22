package com.leclowndu93150.replication_tanks;

import com.buuz135.replication.api.MatterType;
import com.hrznstudio.titanium.module.BlockWithTile;

import java.util.HashMap;
import java.util.Map;

public class ReplicationTanksRegistry {

    public static final int TIER_1_CAPACITY = 256000;
    public static final int TIER_2_CAPACITY = 512000;
    public static final int TIER_3_CAPACITY = 1024000;

    public static final Map<String, BlockWithTile> TANKS = new HashMap<>();

    public static BlockWithTile EARTH_TANK_T1;
    public static BlockWithTile EARTH_TANK_T2;
    public static BlockWithTile EARTH_TANK_T3;

    public static BlockWithTile NETHER_TANK_T1;
    public static BlockWithTile NETHER_TANK_T2;
    public static BlockWithTile NETHER_TANK_T3;

    public static BlockWithTile ORGANIC_TANK_T1;
    public static BlockWithTile ORGANIC_TANK_T2;
    public static BlockWithTile ORGANIC_TANK_T3;

    public static BlockWithTile ENDER_TANK_T1;
    public static BlockWithTile ENDER_TANK_T2;
    public static BlockWithTile ENDER_TANK_T3;

    public static BlockWithTile METALLIC_TANK_T1;
    public static BlockWithTile METALLIC_TANK_T2;
    public static BlockWithTile METALLIC_TANK_T3;

    public static BlockWithTile PRECIOUS_TANK_T1;
    public static BlockWithTile PRECIOUS_TANK_T2;
    public static BlockWithTile PRECIOUS_TANK_T3;

    public static BlockWithTile LIVING_TANK_T1;
    public static BlockWithTile LIVING_TANK_T2;
    public static BlockWithTile LIVING_TANK_T3;

    public static BlockWithTile QUANTUM_TANK_T1;
    public static BlockWithTile QUANTUM_TANK_T2;
    public static BlockWithTile QUANTUM_TANK_T3;

    public static MatterType[] getMatterTypes() {
        return new MatterType[] {
                MatterType.EARTH,
                MatterType.NETHER,
                MatterType.ORGANIC,
                MatterType.ENDER,
                MatterType.METALLIC,
                MatterType.PRECIOUS,
                MatterType.LIVING,
                MatterType.QUANTUM
        };
    }

    public static int getCapacityForTier(int tier) {
        return switch (tier) {
            case 1 -> TIER_1_CAPACITY;
            case 2 -> TIER_2_CAPACITY;
            case 3 -> TIER_3_CAPACITY;
            default -> TIER_1_CAPACITY;
        };
    }

    public static String getTankName(MatterType type, int tier) {
        return type.getName() + "_tank_t" + tier;
    }
}
