package com.sample.appxyNavigationBug.node

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
import kotlin.random.Random

class ProfileNode(
    nodeContext: NodeContext,
    private val backStack: BackStack<Routing> = BackStack(
        model = BackStackModel(
            initialTarget = Routing.Profile,
            savedStateMap = nodeContext.savedStateMap,
        ),
        visualisation = { BackStackSlider(it) },
    )
) : Node<ProfileNode.Routing>(
    appyxComponent = backStack,
    nodeContext = nodeContext,
) {

    enum class Routing {
        Profile,
    }

    override fun buildChildNode(navTarget: Routing, nodeContext: NodeContext): Node<*> {
        return when (navTarget) {
            Routing.Profile -> {
                node(nodeContext = nodeContext) {
                    Log.i("ProfileNode", "buildChildNode: Routing.Profile")
                    val (number, setNumber) = rememberSaveable { mutableIntStateOf(0) }
                    Column(
                        modifier = it
                            .fillMaxSize()
                            .background(Color.Green),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "Number $number")
                        Button(onClick = { setNumber(number + 1) }) {
                            Text(text = "Increment")
                        }
                    }
                }
            }
        }
    }

    @Composable
    override fun Content(modifier: Modifier) {
        val counter = rememberSaveable { Random.nextInt(32) }
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.DarkGray),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Local UI state $counter")
            AppyxNavigationContainer(appyxComponent = backStack)
        }
    }
}
