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
import com.bumble.appyx.navigation.composable.AppyxComponent
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.navigation.node.node

class HomeNode(
    buildContext: BuildContext,
    private val backStack: BackStack<Routing> = BackStack(
        model = BackStackModel(
            initialTarget = Routing.Home,
            savedStateMap = buildContext.savedStateMap,
        ),
        visualisation = { BackStackSlider(it) }
    )
) : ParentNode<HomeNode.Routing>(
    appyxComponent = backStack,
    buildContext = buildContext,
) {

    enum class Routing {
        Home,
    }

    override fun resolve(navTarget: Routing, buildContext: BuildContext): Node {
        return when (navTarget) {
            Routing.Home -> {
                node(buildContext = buildContext) {
                    Log.i("HomeNode", "resolve: Routing.Home")
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
    override fun View(modifier: Modifier) {
        AppyxComponent(appyxComponent = backStack)
    }
}
