package com.sample.appxyNavigationBug.node

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.BackStackModel
import com.bumble.appyx.components.backstack.ui.slider.BackStackSlider
import com.bumble.appyx.components.spotlight.Spotlight
import com.bumble.appyx.components.spotlight.SpotlightModel
import com.bumble.appyx.components.spotlight.operation.activate
import com.bumble.appyx.components.spotlight.ui.slider.SpotlightSlider
import com.bumble.appyx.navigation.composable.AppyxComponent
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import kotlin.random.Random

class BottomNavNode(
    buildContext: BuildContext,
    spotlightModel: SpotlightModel<Routing> = SpotlightModel(
        items = Routing.entries,
        savedStateMap = buildContext.savedStateMap,
    ),
    val spotlight: Spotlight<Routing> = Spotlight(
        model = spotlightModel,
        visualisation = { SpotlightSlider(it) },
        gestureFactory = {
            SpotlightSlider.Gestures(
                transitionBounds = it,
                orientation = Orientation.Vertical,
                reverseOrientation = true,
            )
        }
    )
) : ParentNode<BottomNavNode.Routing>(
    buildContext = buildContext,
    appyxComponent = spotlight
) {

    enum class Routing {
        Home,
        Profile,
    }

    override fun resolve(navTarget: Routing, buildContext: BuildContext): Node {
        return when (navTarget) {
            Routing.Home -> HomeNode(buildContext = buildContext)
            Routing.Profile -> ProfileNode(buildContext = buildContext)
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        Box(modifier = modifier) {
            val counter = rememberSaveable { Random.nextInt(32) }
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(Color.LightGray),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Local UI state $counter")
                AppyxComponent(appyxComponent = spotlight)
            }
            Row(modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Yellow)
                        .clickable { spotlight.activate(0f) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Home")
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.Red)
                        .clickable { spotlight.activate(1f) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Profile")
                }
            }
        }
    }
}
