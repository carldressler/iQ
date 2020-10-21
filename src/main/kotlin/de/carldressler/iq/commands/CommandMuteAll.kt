/*
 * Copyright © 2020 Carl Dressler. All rights reserved.
 */

package de.carldressler.iq.commands

import de.carldressler.iq.IQPlugin
import de.carldressler.iq.utils.Statics
import de.carldressler.iq.utils.runLater
import de.carldressler.iq.utils.translateColor
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent

class CommandMuteAll(private val plugin: IQPlugin) : CommandExecutor, Listener {
    private var globalMuteActive = false
    private val exceptionsSet: Set<String> = setOf()

    init {
        Bukkit.getServer().pluginManager.registerEvents(this, plugin)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            operationMuteAll(sender, args)
            return true
        } else when (args[0]) {
            "except" -> operationExcept(sender, args)
        }
        return true
    }

    private fun operationMuteAll(sender: CommandSender, args: Array<out String>) {
        globalMuteActive = !globalMuteActive
        val reason = if (args.isNotEmpty()) args[0] else null

        // Global mute is not enabled
        if (globalMuteActive) {
            Bukkit.broadcastMessage(translateColor(
                    "${Statics.infoPrefix} Global mute was enabled. Nobody except for people with sufficient permissions can chat in global chat anymore."
            ))
            runLater({
                sender.sendMessage(translateColor(
                        "${Statics.pleaseNotePrefix} You or other players with sufficient permissions can deactivate the global mute using &e/muteall &7. Note that players muted using &e/mute &7are not automatically unmuted by disabling the global mute."
                ))
            }, plugin, 5000)
            return

            // Global mute is enabled
        } else {
            Bukkit.broadcastMessage(translateColor(
                    "${Statics.infoPrefix} Global mute was disabled. Everybody may chat again."
            ))
        }
    }

    private fun operationExcept(sender: CommandSender, args: Array<out String>) {
        TODO("Not yet implemented")
    }

    @EventHandler
    fun onChatMessage(event: AsyncPlayerChatEvent) {
        event.isCancelled = globalMuteActive
        if (globalMuteActive)
            event.player.sendMessage(translateColor(
                    "${Statics.errorPrefix} Your message was not sent because the global chat is muted."
            ))
    }
}