package com.sample.appxyNavigationBug.node

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.bumble.appyx.navigation.composable.AppyxNavigationContainer
import com.bumble.appyx.navigation.modality.NodeContext
import com.bumble.appyx.navigation.node.Node

class BottomNavNode(
    nodeContext: NodeContext,
    spotlightModel: SpotlightModel<Routing> = SpotlightModel(
        items = Routing.entries,
        savedStateMap = nodeContext.savedStateMap,
    ),
    val spotlight: Spotlight<Routing> = Spotlight(
        model = spotlightModel,
        visualisation = { SpotlightSlider(it, spotlightModel.currentState) },
        gestureFactory = {
            SpotlightSlider.Gestures(
                transitionBounds = it,
                orientation = Orientation.Vertical,
                reverseOrientation = true,
            )
        }
    )
) : Node<BottomNavNode.Routing>(
    nodeContext = nodeContext,
    appyxComponent = spotlight
) {

    enum class Routing {
        Home,
        Profile,
    }

    override fun buildChildNode(navTarget: Routing, nodeContext: NodeContext): Node<*> {
        return when (navTarget) {
            Routing.Home -> HomeNode(nodeContext = nodeContext)
            Routing.Profile -> ProfileNode(nodeContext = nodeContext)
        }
    }

    @Composable
    override fun Content(modifier: Modifier) {
        Box(modifier = modifier) {
            AppyxNavigationContainer(appyxComponent = spotlight)
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