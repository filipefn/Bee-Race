package com.beerace.race

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.beerace.commons.component.BeeBackgroundComponent
import com.beerace.commons.component.LoaderComponent
import com.beerace.commons.event.ClickEventOneArg
import com.beerace.commons.string.toColor
import com.beerace.commons.theme.BeeRaceTheme
import com.beerace.race.model.PositionEnum
import com.beerace.race.model.RaceEvent
import com.beerace.race.model.RaceUIState
import com.beerace.race.model.StatusRaceUiModel


@Composable
fun RaceNavigation() {
    val viewModel: RaceViewModel = hiltViewModel()

    val state by viewModel.state.collectAsStateWithLifecycle()

    RaceScreen(
        state = state,
        onEvent = viewModel::handle
    )
}

@Composable
private fun RaceScreen(
    state: RaceUIState,
    onEvent: ClickEventOneArg<RaceEvent>
) {

    when (state) {
        is RaceUIState.Loading, RaceUIState.Error -> LoaderComponent()
        is RaceUIState.Loaded -> {
            Column(
                Modifier
                    .fillMaxSize()
            ) {
                TimeDisplay(time = state.time)
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    itemsIndexed(state.statusRaceList) { index, item ->
                        RaceItem(item = item, position = index)
                        HorizontalDivider(Modifier.padding(vertical = 8.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun TimeDisplay(time: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(MaterialTheme.colorScheme.primary)
            .padding(all = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.time_remaining),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary
        )

        Text(
            text = time,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
private fun RaceItem(item: StatusRaceUiModel, position: Int) {
    ConstraintLayout(Modifier.fillMaxWidth()) {
        val (iconRef, textRef, medalRef) = createRefs()

        BeeBackgroundComponent(
            icon = R.drawable.bee,
            contentDescription = stringResource(R.string.time_remaining),
            backgroundColor = item.color.toColor(),
            modifier = Modifier
                .size(48.dp)
                .constrainAs(iconRef) {
                    start.linkTo(parent.start)
                    linkTo(
                        top = parent.top,
                        bottom = parent.bottom
                    )
                }
        )

        Column(
            Modifier
                .padding(horizontal = 18.dp)
                .constrainAs(textRef) {
                    start.linkTo(iconRef.end)
                    linkTo(
                        top = parent.top,
                        bottom = parent.bottom,
                    )
                }) {
            Text(
                text = stringResource(id = item.position.sufix, position.inc()),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.tertiary
            )
            Text(
                text = item.name,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.tertiary
            )
        }

        item.position.medal?.let {
            Box(
                Modifier
                    .size(16.dp)
                    .constrainAs(medalRef) {
                        end.linkTo(parent.end)
                        linkTo(
                            top = parent.top,
                            bottom = parent.bottom,
                        )
                    }, contentAlignment = Alignment.CenterEnd
            ) {
                Image(
                    painter = painterResource(it),
                    contentDescription = stringResource(id = R.string.medal)
                )
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffff)
@Composable
private fun RacePreview() {
    BeeRaceTheme {
        RaceScreen(
            RaceUIState.Loaded(
                time = "00:20",
                listOf(
                    StatusRaceUiModel(name = "Bee 1", color = "#303F9F", position = PositionEnum.FIRST_PLACE),
                    StatusRaceUiModel(name = "Bee 2", color = "#583F9F", position = PositionEnum.SECOND_PLACE),
                    StatusRaceUiModel(name = "Bee 3", color = "#69429F", position = PositionEnum.THIRD_PLACE),
                    StatusRaceUiModel(name = "Bee 4", color = "#FD2D9F", position = PositionEnum.ANOTHER)
                )
            )
        ) {}
    }
}