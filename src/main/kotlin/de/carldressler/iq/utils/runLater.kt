/*
 * Copyright © 2020 Carl Dressler. All rights reserved.
 */

package de.carldressler.iq.utils

import de.carldressler.iq.IQPlugin
import org.bukkit.Bukkit

fun runLater(task: () -> Unit, plugin: IQPlugin, delayInMs: Int) {
    Bukkit.getScheduler().runTaskLater(plugin, Runnable(task), (delayInMs / 100).toLong())
}
