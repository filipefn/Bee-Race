package com.beerace.winner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.beerace.commons.component.BeeBackgroundComponent
import com.beerace.commons.component.ButtonComponent
import com.beerace.commons.component.LoaderComponent
import com.beerace.commons.event.ClickEventOneArg
import com.beerace.commons.string.toColor
import com.beerace.commons.theme.BeeRaceTheme
import com.beerace.race.R
import com.beerace.winner.model.WinnerEvent
import com.beerace.winner.model.WinnerUiModel
import com.beerace.winner.model.WinnerUiState


@Composable
fun WinnerNavigation() {
    val viewModel: WinnerViewModel = hiltViewModel()

    val state by viewModel.state.collectAsStateWithLifecycle()

    WinnerScreen(
        state = state,
        onEvent = viewModel::handle
    )
}

@Composable
private fun WinnerScreen(
    state: WinnerUiState,
    onEvent: ClickEventOneArg<WinnerEvent>
) {
    when (state) {
        is WinnerUiState.Loading -> LoaderComponent()
        is WinnerUiState.Loaded -> {
            Column(
                Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = stringResource(R.string.winner),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(24.dp))

                BeeBackgroundComponent(modifier = Modifier.size(64.dp),
                    icon = R.drawable.bee,
                    contentDescription = stringResource(id = R.string.icon_bee),
                    backgroundColor = state.winner.color.toColor())
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(R.string.first_place),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(
                    text = state.winner.name,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.tertiary,
                )
                ButtonComponent(modifier = Modifier.padding(vertical = 24.dp, horizontal = 32.dp),
                    text = stringResource(R.string.re_start_bee_race)) {
                    onEvent(WinnerEvent.ReStartClick)
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffff)
@Composable
private fun WinnerScreenPreview() {
    BeeRaceTheme {
        WinnerScreen(
            WinnerUiState.Loaded(
                WinnerUiModel(name = "Bee 1", color = "#303F9F"),
            )
        ) {}
    }
}