package de.carldressler.iq.commands

import de.carldressler.iq.utils.Statics
import de.carldressler.iq.utils.translateColor
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.util.*

class CommandMute : CommandExecutor {
    private val mutedPlayers: MutableSet<UUID?> = mutableSetOf()

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        val playerName = if (args.isNotEmpty()) args[0] else return false
        val uuid = Bukkit.getPlayerExact(playerName)?.uniqueId

        // Note that the two if blocks are flipped so that the error messages occur only in the lower ones.

        if (label == "mute") {

            if (!mutedPlayers.contains(uuid)) {
                mutedPlayers.add(uuid)
                sender.sendMessage(translateColor(
                    "${Statics.infoPrefix} $playerName was muted successfully"
                ))
            } else {
                mutedPlayers.remove(uuid)
                sender.sendMessage(translateColor(
                    "${Statics.pleaseNotePrefix} $playerName was unmuted successfully. Note that the proper command to unmute players is &e/unmute <player>"
                ))
            }

        } else if (label == "unmute") {

            if (mutedPlayers.contains(uuid)) {
                mutedPlayers.remove(uuid)
                sender.sendMessage(translateColor(
                    "${Statics.infoPrefix} $playerName was unmuted successfully"
                ))
            } else {
                sender.sendMessage(translateColor(
                    "${Statics.errorPrefix} $playerName is not muted and therefore cannot be unmuted. You can mute $playerName using &e/mute $playerName"
                ))
            }

        }

        Bukkit.broadcastMessage(mutedPlayers.toString())
        return true
    }
}