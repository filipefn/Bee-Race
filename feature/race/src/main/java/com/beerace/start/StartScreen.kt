package com.beerace.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.beerace.commons.component.ButtonComponent
import com.beerace.commons.component.LoaderComponent
import com.beerace.commons.event.ClickEventOneArg
import com.beerace.commons.theme.BeeRaceTheme
import com.beerace.race.R
import com.beerace.start.model.StartEvent
import com.beerace.start.model.StartUiState


@Composable
fun StartNavigation() {
    val viewModel: StartViewModel = hiltViewModel()

    val state by viewModel.state.collectAsStateWithLifecycle()

    StartScreen(
        state = state,
        onEvent = viewModel::handle
    )
}

@Composable
private fun StartScreen(
    state: StartUiState,
    onEvent: ClickEventOneArg<StartEvent>
) {
    when (state) {
        is StartUiState.Loading -> LoaderComponent()

        is StartUiState.Initial -> {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ButtonComponent(modifier = Modifier.padding(horizontal = 32.dp),
                    text = stringResource(R.string.start_race)
                ) {
                    onEvent(StartEvent.StartClick)
                }
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xffffff)
@Composable
private fun StartPreview() {
    BeeRaceTheme {
        StartScreen(
            StartUiState.Initial
        ) {}
    }
}