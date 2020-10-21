package de.carldressler.iq

import de.carldressler.iq.commands.*
import de.carldressler.iq.utils.Statics
import de.carldressler.iq.utils.runLater
import de.carldressler.iq.utils.translateColor
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class IQPlugin : JavaPlugin() {
    override fun onEnable() {
        validateConfig()
        val muteCommand = getCommand("mute")
        val timeCommand = getCommand("time")
        val rainCommand = getCommand("rain")
        val aboutCommand = getCommand("aboutiQ")
        val muteallCommand = getCommand("muteall")

        muteCommand?.setExecutor(CommandMute())
        muteCommand?.usage = translateColor("${Statics.invalidUsage} /mute &9<player>")
        timeCommand?.setExecutor(CommandTime(this))
        timeCommand?.usage = translateColor("${Statics.invalidUsage}${Statics.prefixIndent}&7/time &e[morning / day / noon / afternoon / evening / sunset / night / midnight / sunrise / &9<ticks>&e]")
        rainCommand?.setExecutor(CommandWeather())
        rainCommand?.usage = translateColor("${Statics.invalidUsage} /&e[rain / storm]")
        aboutCommand?.setExecutor(CommandAbout())
        aboutCommand?.usage = translateColor("${Statics.invalidUsage} /about")
        muteallCommand?.setExecutor(CommandMuteAll(this))
        muteallCommand?.usage = translateColor("${Statics.invalidUsage} /muteall &a(&9<reason>&a)")

        runLater({
            println("[iQ] Note that iQ does not support persistence yet (no database integration) and therefore mutes, temp bans and global mutes were not saved from last runtime.")
            Bukkit.broadcastMessage(translateColor(
                    "${Statics.infoPrefix} iQ successfully reloaded. Please restart the server if you are experiencing any issues"
            ))
        }, this, 5000)
    }

    private fun validateConfig() {
        val timeFormat = config.getString("time format")
        if (timeFormat != "12h" || timeFormat != "24h") {
            logger.warning("Config Error: Value of 'time format' needs to be '12h' or '24h'. Fallback value: 24h")
        }
    }
}