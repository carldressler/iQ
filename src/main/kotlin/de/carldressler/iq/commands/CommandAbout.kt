/*
 * Copyright © 2020 Carl Dressler. All rights reserved.
 */

package de.carldressler.iq.commands

import de.carldressler.iq.utils.Statics
import de.carldressler.iq.utils.translateColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.BookMeta

class CommandAbout : CommandExecutor {
    private val writtenBook = ItemStack(Material.WRITTEN_BOOK)

    init {
        val meta = writtenBook.itemMeta
        (meta as BookMeta).addPage(translateColor(
                "iQ Plugin is developed by Carl Dressler. Contact me for your personal plugins or other projects. :)\n\nGitHub:\n&9github.com/carldressler.de\n&0e-mail:\n&9ich@carldressler.de"
        ))
        meta.author = "Carl Dressler"
        meta.title = "About iQ Plugin"
        writtenBook.itemMeta = meta
    }

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): Boolean {
        if (sender !is Player) {
            println(Statics.playerOnlyCommand)
            return true
        }
        sender.openBook(writtenBook)
        return true
    }
}