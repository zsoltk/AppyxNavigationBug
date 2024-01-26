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
import androidx.compose.runtime.saveable.rememberSaveable
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
import kotlin.random.Random

class ProfileNode(
    buildContext: BuildContext,
    private val backStack: BackStack<Routing> = BackStack(
        model = BackStackModel(
            initialTarget = Routing.Profile,
            savedStateMap = buildContext.savedStateMap,
        ),
        visualisation = { BackStackSlider(it) },
    )
) : ParentNode<ProfileNode.Routing>(
    appyxComponent = backStack,
    buildContext = buildContext,
) {

    enum class Routing {
        Profile,
    }

    override fun resolve(navTarget: Routing, buildContext: BuildContext): Node {
        return when (navTarget) {
            Routing.Profile -> {
                node(buildContext = buildContext) {
                    Log.i("ProfileNode", "resolve: Routing.Profile")
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
    override fun View(modifier: Modifier) {
        val counter = rememberSaveable { Random.nextInt(32) }
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color.DarkGray),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Local UI state $counter")
            AppyxComponent(appyxComponent = backStack)
        }
    }
}
