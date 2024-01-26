package com.sample.appxyNavigationBug.node

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.BackStackModel
import com.bumble.appyx.components.backstack.operation.newRoot
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.components.backstack.ui.slider.BackStackSlider
import com.bumble.appyx.navigation.composable.AppyxNavigationContainer
import com.bumble.appyx.navigation.modality.NodeContext
import com.bumble.appyx.navigation.node.Node

class RootNode(
    nodeContext: NodeContext,
    private val backStack: BackStack<Routing> = BackStack(
        model = BackStackModel(
            initialTarget = Routing.Login,
            savedStateMap = nodeContext.savedStateMap,
        ),
        visualisation = { BackStackSlider(it) }
    )
) : Node<RootNode.Routing>(
    appyxComponent = backStack,
    nodeContext = nodeContext,
) {
    enum class Routing {
        Login,
        Main
    }

    override fun buildChildNode(navTarget: Routing, nodeContext: NodeContext): Node<*> {
        return when (navTarget) {
            Routing.Login -> LoginNode(nodeContext = nodeContext)
            Routing.Main -> MainNode(nodeContext = nodeContext)
        }
    }

    override fun onChildFinished(child: Node<*>) {
        super.onChildFinished(child)
        when (child) {
            is LoginNode -> {
                backStack.push(Routing.Main)
            }
        }
    }

    @Composable
    override fun Content(modifier: Modifier) {
        AppyxNavigationContainer(appyxComponent = backStack)
    }
}
