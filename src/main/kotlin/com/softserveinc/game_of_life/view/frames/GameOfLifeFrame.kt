package com.softserveinc.game_of_life.view.frames

import com.softserveinc.game_of_life.controller.OceanController
import com.softserveinc.game_of_life.view.styles.outlineButton
import java.awt.BorderLayout
import java.awt.Color
import java.awt.Dimension
import java.awt.GridLayout
import java.util.*
import java.util.Timer
import javax.swing.*

class GameOfLifeFrame(private val controller: OceanController) : JFrame() {
    private val CELL_SIZE = 15
    private val CELL_COLORS = mapOf(
        0 to Color.BLACK,
        1 to Color(0xb02441),
        2 to Color(0x249cd8),
        3 to Color.DARK_GRAY
    )

    private val oceanPanel = JPanel()
    private val predatorCountLabel = JLabel()
    private val preyCountLabel = JLabel()
    private val nextButton = JButton("Next")
    private val resetButton = JButton("Reset")
    private val startButton = JButton("Start")
    private val pauseButton = JButton("Pause")
    private val numberInput = JTextField()
    private var timer: Timer? = null

    init {
        outlineButton(nextButton)
        outlineButton(resetButton)
        outlineButton(startButton)
        outlineButton(pauseButton)
        title = "Game of Life"
        defaultCloseOperation = EXIT_ON_CLOSE
        layout = BorderLayout()

        oceanPanel.border = BorderFactory.createLineBorder(Color.BLACK)
        add(oceanPanel, BorderLayout.CENTER)

        val controlPanel = JPanel()
        controlPanel.layout = GridLayout(6, 3)
        controlPanel.add(JLabel("Count predator:"))
        controlPanel.add(predatorCountLabel)
        controlPanel.add(JLabel("Count prey:"))
        controlPanel.add(preyCountLabel)
        controlPanel.add(nextButton)
        controlPanel.add(resetButton)
        controlPanel.add(JLabel("Number of iterations:"))
        controlPanel.add(numberInput)
        controlPanel.add(startButton)
        controlPanel.add(pauseButton)
        add(controlPanel, BorderLayout.SOUTH)

        // event listeners
        nextButton.addActionListener { updateOcean() }
        resetButton.addActionListener { resetOcean() }
        startButton.addActionListener { startIteration() }
        pauseButton.addActionListener {
            timer?.cancel()
            timer = null
        }
    }

    private fun updateOcean() {

        val data = controller.iterate()

        predatorCountLabel.text = data.predator.toString()
        preyCountLabel.text = data.prey.toString()

        oceanPanel.removeAll()
        oceanPanel.layout = GridLayout(data.ocean.size, data.ocean[0].size)
        Thread.sleep(200)
        data.ocean.forEach { row ->
            row.forEach { cell ->
                val cellPanel = JPanel()
                cellPanel.background = CELL_COLORS[cell]
                cellPanel.preferredSize = Dimension(CELL_SIZE, CELL_SIZE)
                oceanPanel.add(cellPanel)
            }
        }

        oceanPanel.revalidate()
        oceanPanel.repaint()

    }

    private fun resetOcean() {
        controller.reset()
        updateOcean()
    }

    private fun startIteration() {
        val iterations = numberInput.text.toInt()
        var i = 0
        if (timer == null) {
            timer = createTimer(300) {
                if (i >= iterations) {
                    timer?.cancel()
                    timer = null
                    return@createTimer
                }
                updateOcean()
                i++
            }
        } else {
            timer?.cancel()
            timer = null
            resetOcean()
            startIteration()
        }
    }

    private fun createTimer(delay: Int, function: () -> Unit): Timer {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                function()
            }
        }, delay.toLong(), delay.toLong())
        return timer
    }

}