package de.carldressler.iq.commands

import de.carldressler.iq.IQPlugin
import de.carldressler.iq.utils.Statics
import de.carldressler.iq.utils.getTime
import de.carldressler.iq.utils.translateColor
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Entity

class CommandTime(private val plugin: IQPlugin) : CommandExecutor {
    /*
     * This command sets the server time a specific value.
     * Only the time of the command senders world is manipulated.
     * There are three ways to invoke this command why there is an if check near the beginning.
     * The if check ensures that the proper "time word" is used (unless ticks were provided)
     * After that the when expression checks whether a valid time word was provided.
     * If not the else statement checks whether the time word actually are ticks.
     * If so, the time is set to the ticks. If not the method returns false.
     */

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (sender !is Entity) {
            return true
        }

        val world = Bukkit.getWorld(sender.world.name)
        val timeWord: String = if (label == "time") {
            if (args.isNotEmpty()) args[0] else return false
        } else if (label.startsWith("t")) {
            label.drop(1)
        } else {
            label
        }

        when (timeWord) {
            "morning" -> {
                world?.time = 0
                sender.sendMessage(translateColor(
                    "${Statics.infoPrefix} Time set to 0 (${getTime(plugin, "0600")})"
                ))
            }
            "day","d" -> {
                world?.time = 1000
                sender.sendMessage(translateColor(
                    "${Statics.infoPrefix} Time set to 1000 (${getTime(plugin, "0700")})"
                ))
            }
            "noon" -> {
                world?.time = 6000
                sender.sendMessage(translateColor(
                    "${Statics.infoPrefix} Time set to 6000 (${getTime(plugin, "1200")})"
                ))
            }
            "afternoon" -> {
                world?.time = 9000
                sender.sendMessage(translateColor(
                    "${Statics.infoPrefix} Time set to 9000 (${getTime(plugin, "1500")})"
                ))
            }
            "evening","sunset" -> {
                world?.time = 12000
                sender.sendMessage(translateColor(
                    "${Statics.infoPrefix} Time set to 12000 (${getTime(plugin, "1800")})"
                ))
            }
            "night","n" -> {
                world?.time = 13000
                sender.sendMessage(translateColor(
                    "${Statics.infoPrefix} Time set to 13000 (${getTime(plugin, "1900")})"
                ))
            }
            "midnight" -> {
                world?.time = 18000
                sender.sendMessage(translateColor(
                    "${Statics.infoPrefix} Time set to 18000 (${getTime(plugin, "0000")})"
                ))
            }
            "sunrise" -> {
                world?.time = 23000
                sender.sendMessage(translateColor(
                    "${Statics.infoPrefix} Time set to 23000 (${getTime(plugin, "0500")})"
                ))
            }
            else -> {
                val ticks = timeWord.toLong()
                if (ticks in 0..24000) {
                    world?.time = ticks
                    sender.sendMessage(translateColor(
                        "${Statics.infoPrefix} Time set to $ticks"
                    ))
                } else {
                    return false
                }
            }
        }
        return true
    }

}