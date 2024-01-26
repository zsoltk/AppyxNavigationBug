package com.sample.appxyNavigationBug.node

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.bumble.appyx.components.backstack.BackStack
import com.bumble.appyx.components.backstack.BackStackModel
import com.bumble.appyx.components.backstack.operation.push
import com.bumble.appyx.components.backstack.ui.slider.BackStackSlider
import com.bumble.appyx.navigation.composable.AppyxComponent
import com.bumble.appyx.navigation.modality.BuildContext
import com.bumble.appyx.navigation.node.Node
import com.bumble.appyx.navigation.node.ParentNode
import com.bumble.appyx.navigation.node.node
import kotlin.random.Random

class MainNode(
    buildContext: BuildContext,
    private val backStack: BackStack<Routing> = BackStack(
        model = BackStackModel(
            initialTarget = Routing.BottomNav,
            savedStateMap = buildContext.savedStateMap,
        ),
        visualisation = { BackStackSlider(it) }
    )
) : ParentNode<MainNode.Routing>(
    buildContext = buildContext,
    appyxComponent = backStack
) {

    enum class Routing {
        BottomNav,
        ModalNode,
    }

    override fun resolve(navTarget: Routing, buildContext: BuildContext): Node {
        return when (navTarget) {
            Routing.BottomNav -> BottomNavNode(buildContext = buildContext)
            Routing.ModalNode -> node(buildContext = buildContext) { }
        }
    }

    @Composable
    override fun View(modifier: Modifier) {
        val counter = rememberSaveable { Random.nextInt(32) }
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(onClick = { backStack.push(Routing.ModalNode) }) {
                Text(text = "Modal $counter")
            }
            AppyxComponent(appyxComponent = backStack)
        }
    }
}
