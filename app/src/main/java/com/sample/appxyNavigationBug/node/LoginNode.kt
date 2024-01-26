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
import com.bumble.appyx.navigation.composable.AppyxNavigationContainer
import com.bumble.appyx.navigation.modality.NodeContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.node

class LoginNode(
    nodeContext: NodeContext,
    private val backStack: BackStack<Routing> = BackStack(
        model = BackStackModel(
            initialTarget = Routing.Login,
            savedStateMap = nodeContext.savedStateMap,
        ),
        visualisation = { BackStackSlider(it) }
    )
) : Node<LoginNode.Routing>(
    appyxComponent = backStack,
    nodeContext = nodeContext,
) {

    enum class Routing {
        Login,
    }

    override fun buildChildNode(navTarget: Routing, nodeContext: NodeContext): Node<*> {
        return when (navTarget) {
            Routing.Login -> node(nodeContext = nodeContext) {
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
    override fun Content(modifier: Modifier) {
        AppyxNavigationContainer(appyxComponent = backStack)
    }

}