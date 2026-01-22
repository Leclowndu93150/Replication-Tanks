package com.leclowndu93150.replication_tanks.data;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TanksLangProvider extends LanguageProvider {

    private final List<Block> blocks;

    public TanksLangProvider(DataGenerator gen, String modid, String locale, List<Block> blocks) {
        super(gen.getPackOutput(), modid, locale);
        this.blocks = blocks;
    }

    @Override
    protected void addTranslations() {
        this.add("itemGroup.replication_tanks", "Replication Tanks");
        this.add("tooltip.replication_tanks.capacity", "Capacity: %s mB");
        this.add("config.jade.plugin_replication_tanks.matter_tank", "Matter Tank");

        this.blocks.forEach(block -> {
            String path = BuiltInRegistries.BLOCK.getKey(block).getPath();
            String formatted = formatTankName(path);
            this.add(block, formatted);
        });
    }

    private String formatTankName(String path) {
        String name = path.replace("_tank_t", " Tank Tier ");
        return capitalize(name);
    }

    private String capitalize(String str) {
        return Arrays.stream(str.split(" "))
                .map(word -> word.isEmpty() ? word : Character.toUpperCase(word.charAt(0)) + word.substring(1))
                .collect(Collectors.joining(" "));
    }
}
