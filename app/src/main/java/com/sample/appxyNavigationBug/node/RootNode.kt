package com.sample.appxyNavigationBug.node

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.BackStackModel
import com.bumble.appyx.components.backstack.operation.newRoot
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.components.backstack.ui.slider.BackStackSlider
import com.bumble.appyx.navigation.composable.AppyxComponent
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode

class RootNode(
    buildContext: BuildContext,
    private val backStack: BackStack<Routing> = BackStack(
        model = BackStackModel(
            initialTarget = Routing.Login,
            savedStateMap = buildContext.savedStateMap,
        ),
        visualisation = { BackStackSlider(it) }
    )
) : ParentNode<RootNode.Routing>(
    appyxComponent = backStack,
    buildContext = buildContext,
) {
    enum class Routing {
        Login,
        Main
    }

    override fun resolve(navTarget: Routing, buildContext: BuildContext): Node {
        return when (navTarget) {
            Routing.Login -> LoginNode(buildContext = buildContext)
            Routing.Main -> MainNode(buildContext = buildContext)
        }
    }

    
    override fun onChildFinished(child: Node) {
        super.onChildFinished(child)
        when (child) {
            is LoginNode -> {
                backStack.push(Routing.Main)
            }
        }
    }
    

    @Composable
    override fun View(modifier: Modifier) {
        AppyxComponent(appyxComponent = backStack)
    }
    
    
}
