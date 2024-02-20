package com.beerace.race

import com.beerace.domain.statusrace.StatusRace
import com.beerace.race.factory.RaceUiFactory
import com.beerace.race.model.PositionEnum
import com.beerace.race.model.StatusRaceUiModel
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals

@ExtendWith(MockKExtension::class)
internal class RaceUiFactoryTest {
    @InjectMockKs
    lateinit var uiFactory: RaceUiFactory

    @Test
    fun `StatusRace is correctly mapped to the UI model`() = runTest {
        val result =
            uiFactory(
                listOf(
                    StatusRace("name1", "color1"),
                    StatusRace("name2", "color2"),
                    StatusRace("name3", "color3"),
                    StatusRace("name4", "color4")
                )
            )

        assertEquals(listOf(
            StatusRaceUiModel("name1", PositionEnum.FIRST_PLACE, "color1"),
            StatusRaceUiModel("name2", PositionEnum.SECOND_PLACE,"color2"),
            StatusRaceUiModel("name3", PositionEnum.THIRD_PLACE,"color3"),
            StatusRaceUiModel("name4", PositionEnum.ANOTHER,"color4")
        ), result)
    }
}