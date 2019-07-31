package io.arct.bouncyblocks

import io.arct.bouncyblocks.listeners.PlayerHandler
import org.bukkit.Material
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class BouncyBlocks : JavaPlugin() {
    val blocks: EnumMap<Material, BouncyBlock> = EnumMap(Material::class.java)

    override fun onEnable() {
        saveDefaultConfig()

        val section = config.getConfigurationSection("blocks")

        if (section != null)
            for (name in section.getKeys(false)) {
                val blocks = config.getList("blocks.$name.blocks") as List<String>? ?: continue
                val worlds = config.getList("blocks.$name.worlds") as List<String>? ?: continue

                val minDistance = config.getInt("blocks.$name.distance-min")
                val maxDistance = config.getInt("blocks.$name.distance-max")

                val minHeight = config.getInt("blocks.$name.height-min")
                val maxHeight = config.getInt("blocks.$name.height-max")

                if (blocks.isEmpty() || worlds.isEmpty())
                    continue

                for (block in blocks) {
                    if (Material.getMaterial(block)?.isSolid == false)
                        continue

                    this.blocks[Material.getMaterial(block)] = BouncyBlock(worlds, minDistance, maxDistance, minHeight, maxHeight)
                }
            }

        events()
    }

    private fun events () {
        server.pluginManager.registerEvents(PlayerHandler(this), this)
    }
}