package com.example.a30days

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Spring.DampingRatioHighBouncy
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessHigh
import androidx.compose.animation.core.Spring.StiffnessMedium
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a30days.data.Mental
import com.example.a30days.data.MentalRepository
import com.example.a30days.ui.theme._30DaysTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            _30DaysTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    MentalApp()
                }
            }
        }
    }
}

@Composable
fun MentalApp() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MentalTopBar()
        }
    ) {
        val mental = MentalRepository.mentalInfo
        MentalListApp(mental = mental, contentPadding = it )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MentalListApp(
    mental: List<Mental>,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(0.dp)
) {
    val state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }
    AnimatedVisibility(
        visibleState = state,
        enter = fadeIn(
            animationSpec = spring(dampingRatio = DampingRatioHighBouncy)
        ),
        exit = fadeOut()
    ) {
        LazyColumn(
            contentPadding = contentPadding,
            modifier = Modifier
        ) {
            itemsIndexed(mental) { index, mental ->
                MentalListItem(
                    mental = mental,
                    modifier = modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .animateEnterExit(
                            enter = slideInVertically(
                                animationSpec = spring(
                                    stiffness = StiffnessMedium,
                                    dampingRatio = DampingRatioHighBouncy
                                ),
                                initialOffsetY = {it * (index + 1)}
                            )
                        )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MentalTopBar(
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
           Text(text = stringResource(R.string.app_name))
        },
        modifier = modifier
    )
}

@Composable
fun MentalListItem(
    mental: Mental,
    modifier: Modifier = Modifier
) {
    Card (
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(mental.dayNumber),
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = stringResource(mental.title),
                style = MaterialTheme.typography.headlineSmall
            )
            Image(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp)),
                painter = painterResource(mental.image),
                contentDescription = null
            )
            Text(
                text = stringResource(mental.description),
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MentalDarkPreview() {
    _30DaysTheme (darkTheme = true) {
        MentalApp()
    }
}

@Preview(showBackground = true)
@Composable
fun MentalLightPreview() {
    _30DaysTheme (darkTheme = false) {
        MentalApp()
    }
}


