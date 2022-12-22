package com.softserveinc.game_of_life.view.styles

import java.awt.Color
import java.awt.Component
import java.awt.Graphics
import java.awt.Insets
import javax.swing.JButton
import javax.swing.border.Border


fun outlineButton(button: JButton) {
    button.setBounds(0, 0, 30, 25);
    button.background = Color.WHITE
    button.foreground = Color.BLACK
    button.border = RoundedBorder(10)
    button.isOpaque = false;
    button.isFocusPainted = false;
}

private class RoundedBorder(private val radius: Int) : Border {
    override fun getBorderInsets(c: Component): Insets {
        return Insets(radius + 1, radius + 1, radius + 2, radius)
    }

    override fun isBorderOpaque(): Boolean {
        return true
    }

    override fun paintBorder(c: Component, g: Graphics, x: Int, y: Int, width: Int, height: Int) {
        g.drawRoundRect(x, y, width - 1, height - 1, radius, radius)
    }
}