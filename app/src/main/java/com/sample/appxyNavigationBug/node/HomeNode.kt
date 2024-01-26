package com.sample.appxyNavigationBug.node

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.BackStackModel
import com.bumble.appyx.components.backstack.ui.slider.BackStackSlider
import com.bumble.appyx.navigation.composable.AppyxNavigationContainer
import com.bumble.appyx.navigation.modality.NodeContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.node

class HomeNode(
    nodeContext: NodeContext,
    private val backStack: BackStack<Routing> = BackStack(
        model = BackStackModel(
            initialTarget = Routing.Home,
            savedStateMap = nodeContext.savedStateMap,
        ),
        visualisation = { BackStackSlider(it) }
    )
) : Node<HomeNode.Routing>(
    appyxComponent = backStack,
    nodeContext = nodeContext,
) {

    enum class Routing {
        Home,
    }

    override fun buildChildNode(navTarget: Routing, nodeContext: NodeContext): Node<*> {
        return when (navTarget) {
            Routing.Home -> {
                node(nodeContext = nodeContext) {
                    Log.i("HomeNode", "buildChildNode: Routing.Home")
                    Box(
                        modifier = it
                            .fillMaxSize()
                            .background(Color.Blue),
                    )
                }
            }
        }
    }

    @Composable
    override fun Content(modifier: Modifier) {
        AppyxNavigationContainer(appyxComponent = backStack)
    }
}