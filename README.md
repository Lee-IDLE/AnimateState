상태 주도 애니메이션 공부를 위한 데모 프로젝트입니다.

1. animateFloatAsState를 사용해 targetValue가 변경되면 float 값이 변경 되도록 하고 Modifier.rotate를 사용해 회전 시킨다
2. animateColorAsState를 사용해 targeValue가 변경되면 Color 값이 변경 되도록 하고 Modifier.backgroun를 사용해 배경색을 바꾼다
3. animateDpAsState를 사용해 targetValue가 변경되면 dp 값이 변경 되도록 하고 Modifier.Offset을 사용해 위치를 변경한다
   - animateDpAsState의 animationSpec에 spring을 넣어서 스프링 효과를 추가한다
   - animateDpAsState의 animationSpec에 keyFrame을 넣어서 타임스탬프를 찍어 몇초에 어느 위치까지 이동하도록 한다
4. updateTransition을 사용해 targetState가 변경되면 색상, 위치 등이 변경되도록 한다
