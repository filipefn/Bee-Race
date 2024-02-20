package com.beerace.winner

import com.CoroutineExtension
import com.beerace.race.model.PositionEnum
import com.beerace.race.model.StatusRaceUiModel
import com.beerace.winner.factory.WinnerUiFactory
import com.beerace.winner.model.WinnerUiModel
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class, CoroutineExtension::class)
internal class WinnerUiFactoryTest {
    @InjectMockKs
    lateinit var uiFactory: WinnerUiFactory

    @Test
    fun `Winner is correctly mapped to the UI model`() = runTest {
        val result =
            uiFactory(
                StatusRaceUiModel("name1", PositionEnum.FIRST_PLACE, "color1")
            )

        assertEquals(WinnerUiModel("name1", "color1"), result)
    }
}