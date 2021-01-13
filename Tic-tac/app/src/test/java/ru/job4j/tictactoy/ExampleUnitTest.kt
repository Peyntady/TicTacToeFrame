package ru.job4j.tictactoy

import io.kotest.matchers.shouldBe
import io.kotest.runner.junit4.StringSpec
import io.kotest.matchers.shouldNotBe

class ExampleUnitTest : StringSpec({
    "filled matrix" {
        val logic = Logic(arrayOf(
                arrayOf("X", "X", "O"),
                arrayOf("O", "O", "X"),
                arrayOf("O", "O", "X")))
        logic.isFilled shouldBe true
    }

    "unfilled matrix" {
        val logic = Logic(arrayOf(
                arrayOf("X", "X", null),
                arrayOf("O", "O", "X"),
                arrayOf("O", "O", "X")))
        logic.isFilled shouldNotBe true
    }

    "O won diagonally" {
        val logic = Logic(arrayOf(
                arrayOf("O", "X", null),
                arrayOf("X", "O", "X"),
                arrayOf("X", "O", "O")))
        logic.checkWin("O") shouldBe true
    }

    "O won vertically" {
        val logic = Logic(arrayOf(
                arrayOf("X", "O", null),
                arrayOf("X", "O", null),
                arrayOf(null, "O", "X")))
        logic.checkWin("O") shouldBe true
    }

    "X won horizontally" {
        val logic = Logic(arrayOf(
                arrayOf("X", "X", "X"),
                arrayOf("O", "O", null),
                arrayOf("X", "O", "X")))
        logic.checkWin("X") shouldBe true
    }

    "X won vertically" {
        val logic = Logic(arrayOf(
                arrayOf("X", "X", null),
                arrayOf("O", "X", null),
                arrayOf("X", "X", "O")))
        logic.checkWin("X") shouldBe true
    }

    "click on button do nothing" {
        val logic = Logic(arrayOf(
                arrayOf("X", "X", null),
                arrayOf("O", "X", null),
                arrayOf("X", "X", "O")))
        logic.clickOnButton("00")
        val expect = arrayOf(
                arrayOf("X", "X", null),
                arrayOf("O", "X", null),
                arrayOf("X", "X", "O"))
        logic.matrix shouldBe expect
    }

    "click on button place X" {
        val logic = Logic()
        logic.clickOnButton("11")
        val expect = arrayOf(
                arrayOf<String?>(null, null, null),
                arrayOf<String?>(null, "X", null),
                arrayOf<String?>(null, null, null))
        logic.matrix shouldBe expect
    }

    "AI presses a button to prevent the player from winning" {
        val logic = Logic(arrayOf(
                arrayOf<String?>(null, null, null),
                arrayOf<String?>("O", "X", null),
                arrayOf<String?>(null, "X", null)))
        logic.turn = 3
        logic.clickOnButtonWithAI()
        val expect = arrayOf(
                arrayOf(null, "O", null),
                arrayOf("O", "X", null),
                arrayOf(null, "X", null))
        logic.matrix shouldBe expect
    }

    "AI presses a button to win" {
        val logic = Logic(arrayOf(
                arrayOf("O", "X", null),
                arrayOf("O", null, "X"),
                arrayOf(null, "X", null)))
        logic.turn = 5
        logic.clickOnButtonWithAI()
        val expect = arrayOf(
                arrayOf("O", "X", null),
                arrayOf("O", null, "X"),
                arrayOf("O", "X", null))
        logic.matrix shouldBe expect
    }

    "Restart game" {
        val logic = Logic(arrayOf(
                arrayOf("O", "X", null),
                arrayOf("O", null, "X"),
                arrayOf(null, "X", null)))
        logic.turn = 5
        logic.clearMatrix()
        val expect = arrayOf(
                arrayOf<String?>(null, null, null),
                arrayOf<String?>(null, null, null),
                arrayOf<String?>(null, null, null))
        logic.matrix shouldBe expect
        logic.turn shouldBe 0
    }

    "Switch side" {
        val logic = Logic()
        logic.changeSide()
        logic.value shouldBe "O"
    }
})