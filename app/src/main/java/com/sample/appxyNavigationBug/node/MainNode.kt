package com.sample.appxyNavigationBug.node

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.BackStackModel
import com.bumble.appyx.components.backstack.ui.slider.BackStackSlider
import com.bumble.appyx.navigation.composable.AppyxNavigationContainer
import com.bumble.appyx.navigation.modality.NodeContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.node

class MainNode(
    nodeContext: NodeContext,
    private val backStack: BackStack<Routing> = BackStack(
        model = BackStackModel(
            initialTarget = Routing.BottomNav,
            savedStateMap = nodeContext.savedStateMap,
        ),
        visualisation = { BackStackSlider(it) }
    )
) : Node<MainNode.Routing>(
    nodeContext = nodeContext,
    appyxComponent = backStack
) {

    enum class Routing {
        BottomNav,
        ModalNode,
    }

    override fun buildChildNode(navTarget: Routing, nodeContext: NodeContext): Node<*> {
        return when (navTarget) {
            Routing.BottomNav -> BottomNavNode(nodeContext = nodeContext)
            Routing.ModalNode -> node(nodeContext = nodeContext) { }
        }
    }

    @Composable
    override fun Content(modifier: Modifier) {
        AppyxNavigationContainer(appyxComponent = backStack)
    }
}