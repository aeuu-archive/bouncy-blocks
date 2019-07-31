package io.arct.bouncyblocks

import org.bukkit.World

data class BouncyBlock(
    val worlds: List<String>,
    val minDistance: Int,
    val maxDistance: Int,
    val minHeight: Int,
    val maxHeight: Int
)