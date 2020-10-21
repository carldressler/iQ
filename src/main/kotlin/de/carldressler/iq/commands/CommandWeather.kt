/*
 * Copyright © 2020 Carl Dressler. All rights reserved.
 */

package de.carldressler.iq.commands

import de.carldressler.iq.utils.Statics
import de.carldressler.iq.utils.translateColor
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity
import kotlin.random.Random

class CommandWeather : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (sender !is Entity) {
            println(Statics.playerOnlyCommand)
            return true
        }

        val world = Bukkit.getWorld(sender.world.name)
        val statusIsRaining = world?.hasStorm() ?: false
        val targetIsStorm = label == "storm"

        if (args.isEmpty()) {
            // If no arguments are supplied this will toggle regular rain
            world?.setStorm(!statusIsRaining)
            world?.isThundering = targetIsStorm
            sendSuccessMessage(sender, !statusIsRaining, targetIsStorm)
            val isHotOrColdBiome = sendBiomeNote(sender, !statusIsRaining)
            sendOppositeCommandHint(sender, targetIsStorm, isHotOrColdBiome)
            return true
        } else if (args[0] == "on" || args[0] == "off") {
            // If a fitting argument is supplied it will be used to (de-)activate a storm
            val targetIsRaining = args[0] == "on"

            world?.setStorm(targetIsRaining)
            world?.isThundering = targetIsStorm
            sendSuccessMessage(sender, targetIsRaining, targetIsStorm)
            val isHotOrColdBiome = sendBiomeNote(sender, targetIsRaining)
            sendOppositeCommandHint(sender, targetIsStorm, isHotOrColdBiome)
        } else {
            // Incorrect syntax results in usage return
            return false
        }


        return true
    }

    private fun sendSuccessMessage(
            sender: CommandSender,
            statusIsRaining: Boolean,
            targetIsStorm: Boolean
    ) {
        /*
         * Note, that the results produced are not correct all the time.
         * That is because if downfall/storm is being deactivated it will always use
         * the label instead of figuring out if it is raining/storming right now.
         * That is such a minor issue though that I don't want to spend any further time fixing it.
         */

        val messageRainState = if (statusIsRaining) "activated" else "deactivated"
        val messageIsStorm = if (targetIsStorm) "Storm" else "Rain"
        sender.sendMessage(translateColor(
                "${Statics.infoPrefix} $messageIsStorm was $messageRainState successfully"
        ))
    }

    private fun sendBiomeNote(sender: CommandSender, targetIsRaining: Boolean): Boolean {
        // Cast
        if (sender !is Entity) {
            return false
        }
        // Do not send the notification if rain is being deactivated
        if (!targetIsRaining) {
            return false
        }
        val temperature = sender.location.block.temperature

        if (temperature < 0.15) {
            // Cold biome
            sender.sendMessage(translateColor(
                    "${Statics.pleaseNotePrefix} Note that instead of rain you will see snowfall as you are in a very cold biome."
            ))
            return false
        } else if (temperature > 0.95) {
            // Hot biome
            sender.sendMessage(translateColor(
                    "${Statics.pleaseNotePrefix} Note that it is too hot for rain in your biome. Leave the area and feel the refreshing rain on your skin."
            ))
            return false
        } else {
            return false
        }
    }

    private fun sendOppositeCommandHint(sender: CommandSender, targetIsStorm: Boolean, isHotOrColdBiome: Boolean) {
        if (Random.nextDouble() < 0.1 && !isHotOrColdBiome) {
             if (targetIsStorm) {
                 sender.sendMessage(translateColor(
                         "${Statics.pleaseNotePrefix} Did you know that &e/rain &7will summon unspectacular rainfall instead of a storm?"
                 ))
             } else {
                 sender.sendMessage(translateColor(
                         "${Statics.pleaseNotePrefix} Did you know that &e/storm &7will summon a storm instead of regular rainfall?"
                 ))
             }
        }
    }
}