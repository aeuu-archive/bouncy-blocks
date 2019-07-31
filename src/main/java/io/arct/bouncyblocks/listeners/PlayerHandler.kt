package io.arct.bouncyblocks.listeners

import io.arct.bouncyblocks.BouncyBlocks
import org.bukkit.block.BlockFace
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import org.bukkit.util.Vector

class PlayerHandler(private val plugin: BouncyBlocks) : Listener {
    @EventHandler
    fun onPlayerWalk(e: PlayerMoveEvent) {
        if (!e.player.hasPermission("bouncyblocks.use"))
            return

        val material = plugin.blocks[e.player.location.block.getRelative(BlockFace.DOWN).type] ?: return

        if (!material.worlds.contains(e.player.location.world?.name?.toLowerCase()) && !material.worlds.contains("*"))
            return

        val minDistance: Int = material.minDistance
        val maxDistance: Int = material.maxDistance
        val minHeight: Int = material.minHeight
        val maxHeight: Int = material.maxHeight

        val distance: Int = (minDistance..maxDistance).random()
        val height: Int = (minHeight..maxHeight).random()

        val direction = direction(e.player)

        // Check again right before
        plugin.blocks[e.player.location.block.getRelative(BlockFace.DOWN).type] ?: return

        e.player.velocity = Vector(
            e.player.velocity.x + (if (direction == Direction.EAST) distance else if (direction == Direction.WEST) -distance else 0),
            e.player.velocity.y + height,
            e.player.velocity.z + (if (direction == Direction.SOUTH) distance else if (direction == Direction.NORTH) -distance else 0)
        )
    }

    private fun direction(player: Player): Direction {
        var yaw = player.location.yaw

        if (yaw < 0)
            yaw += 360f

        return when {
            yaw >= 315 || yaw < 45 -> Direction.SOUTH
            yaw < 135 -> Direction.WEST
            yaw < 225 -> Direction.NORTH
            yaw < 315 -> Direction.EAST
            else -> Direction.NORTH
        }
    }

    private enum class Direction {
        NORTH,
        SOUTH,
        EAST,
        WEST
    }
}