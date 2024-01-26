package com.sample.appxyNavigationBug.node

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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

class LoginNode(
    buildContext: BuildContext,
    private val backStack: BackStack<Routing> = BackStack(
        model = BackStackModel(
            initialTarget = Routing.Login,
            savedStateMap = buildContext.savedStateMap,
        ),
        visualisation = { BackStackSlider(it) }
    )
) : ParentNode<LoginNode.Routing>(
    appyxComponent = backStack,
    buildContext = buildContext,
) {

    enum class Routing {
        Login,
    }

    override fun resolve(navTarget: Routing, buildContext: BuildContext): Node {
        return when (navTarget) {
            Routing.Login -> node(buildContext = buildContext) {
                Box(
                    modifier = it
                        .fillMaxSize()
                        .background(Color.Magenta),
                    contentAlignment = Alignment.Center
                ) {
                    Button(onClick = { finish() }) {
                        Text(text = "Login", color = Color.Black)
                    }
                }
            }
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        AppyxComponent(appyxComponent = backStack)
    }

}
