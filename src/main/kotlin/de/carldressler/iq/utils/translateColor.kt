package de.carldressler.iq.utils

import org.bukkit.Bukkit
import org.bukkit.ChatColor

fun translateColor(message: String): String {
    return ChatColor.translateAlternateColorCodes('&', message)
}