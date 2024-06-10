package com.lee_idle.animatestate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lee_idle.animatestate.ui.theme.AnimateStateTheme
import androidx.compose.animation.core.Spring.DampingRatioHighBouncy
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.updateTransition

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimateStateTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    //RotationDemo()
    //ColorChangeDemo()
    //MotionDemo()
    //SpringDemo()
    //KeyframeDemo()
    TransitionDemo()
}

enum class BoxColor {
    Red, Magenta
}

@Composable
fun RotationDemo() {
    var rotated by remember { mutableStateOf(false) }

    // easing = LinearEasing 설정을 안 하면 회전하는 각도의 마지막에 가까울수록
    // 애니메이션 속도가 느려진다.
    val angle by animateFloatAsState(
        targetValue = if (rotated) 360f else 0f,
        animationSpec = tween(durationMillis = 2500, easing = LinearEasing)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ){
        Image(
            painter = painterResource(R.drawable.propeller),
            contentDescription = "fan",
            modifier = Modifier
                .rotate(angle)
                .padding(10.dp)
                .size(300.dp)
        )

        Button(
            onClick = { rotated = !rotated },
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "Rotate Propeller")
        }
    }
}

@Composable
fun ColorChangeDemo() {
    var colorState by remember{ mutableStateOf(BoxColor.Magenta) }

    val animatedColor: Color by animateColorAsState(
        targetValue = when (colorState) {
            BoxColor.Red -> Color.Magenta
            BoxColor.Magenta -> Color.Red
        },
        animationSpec = tween(4500))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ){
        Box(
            modifier = Modifier
                .padding(20.dp)
                .size(200.dp)
                .background(animatedColor)
        )

        Button(
            onClick = {
                colorState = when(colorState) {
                    BoxColor.Red -> BoxColor.Magenta
                    BoxColor.Magenta -> BoxColor.Red
                }
            },
            modifier = Modifier.padding(10.dp)
        ){
            Text(text = "Change Color")
        }
    }
}

enum class BoxPosition {
    Start, End
}

@Composable
fun MotionDemo() {
    var boxState by remember { mutableStateOf(BoxPosition.Start) }
    val boxSideLength = 70.dp

    // 앱이 실행되는 기기의 화면 폭, 높이, 밀도, 글꼴 스케일 정보, 야간 모드 활성화 여부 등
    // 정보를 제공하는 LocalConfiguration 인스턴스를 활용해 시작 위치, 끝 위치를 가져온다
    var screenWidth = (LocalConfiguration.current.screenWidthDp.dp)

    val animatedOffset: Dp by animateDpAsState(
        targetValue = when(boxState) {
            BoxPosition.Start -> 0.dp
            BoxPosition.End -> screenWidth - boxSideLength
        }, animationSpec = tween(500), label = ""
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .offset(x = animatedOffset, y = 20.dp) // offset에 적용한다
                .size(boxSideLength)
                .background(Color.Red)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                boxState = when(boxState) {
                    BoxPosition.Start -> BoxPosition.End
                    BoxPosition.End -> BoxPosition.Start
                }
            },
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Move Box")
        }
    }
}

@Composable
fun SpringDemo() {
    var boxState by remember { mutableStateOf(BoxPosition.Start) }
    val boxSideLength = 70.dp

    var screenWidth = (LocalConfiguration.current.screenWidthDp.dp)

    val animatedOffset: Dp by animateDpAsState(
        targetValue = when(boxState){
            BoxPosition.Start -> 0.dp
            BoxPosition.End -> screenWidth - boxSideLength
        },
        animationSpec = spring(
            dampingRatio = DampingRatioHighBouncy, stiffness = StiffnessVeryLow)
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .offset(x = animatedOffset, y = 20.dp) // offset에 적용한다
                .size(boxSideLength)
                .background(Color.Red)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                boxState = when(boxState) {
                    BoxPosition.Start -> BoxPosition.End
                    BoxPosition.End -> BoxPosition.Start
                }
            },
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Move Box")
        }
    }
}

@Composable
fun KeyframeDemo() {
    var boxState by remember { mutableStateOf(BoxPosition.Start) }
    val boxSideLength = 70.dp

    var screenWidth = (LocalConfiguration.current.screenWidthDp.dp)

    val animatedOffset: Dp by animateDpAsState(
        targetValue = when(boxState){
            BoxPosition.Start -> 0.dp
            BoxPosition.End -> screenWidth - boxSideLength
        },
        animationSpec = keyframes {
            durationMillis = 1000
            // with 함수를 이용해 타임스탬프에 이징을 설정할 수 있다
            100.dp.at(10).with(LinearEasing)
            110.dp.at(500).with(FastOutSlowInEasing)
            200.dp.at(700).with(LinearOutSlowInEasing)
        }
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .offset(x = animatedOffset, y = 20.dp) // offset에 적용한다
                .size(boxSideLength)
                .background(Color.Red)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                boxState = when(boxState) {
                    BoxPosition.Start -> BoxPosition.End
                    BoxPosition.End -> BoxPosition.Start
                }
            },
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Move Box")
        }
    }
}

@Composable
fun TransitionDemo() {
    var boxState by remember{ mutableStateOf(BoxPosition.Start) }
    var screenWidth = LocalConfiguration.current.screenWidthDp
    // boxState의 변경에 반응하도록 설정된 Transition 인스턴스를 얻는다
    val transition = updateTransition(targetState = boxState,
        label = "Color and Motion")

    // 색상과 동작 애니메이션 추가
    val animatedColor: Color by transition.animateColor(
        transitionSpec = { tween(4000) }, label = ""
    ){state ->
        when(state) {
            BoxPosition.Start -> Color.Red
            BoxPosition.End -> Color.Magenta
        }
    }

    val animatedOffset: Dp by transition.animateDp(
        transitionSpec = { tween(4000) }, label = ""
    ) {state ->
        when(state) {
            BoxPosition.Start -> 0.dp
            BoxPosition.End -> (screenWidth - 70).dp
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .offset(x = animatedOffset, y = 20.dp)
                .size(70.dp)
                .background(animatedColor)
        )
        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                boxState = when(boxState){
                    BoxPosition.Start -> BoxPosition.End
                    BoxPosition.End -> BoxPosition.Start
                }
            },
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally)
        ){
            Text(text = "Start Animation")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AnimateStateTheme {
        MainScreen()
    }
}