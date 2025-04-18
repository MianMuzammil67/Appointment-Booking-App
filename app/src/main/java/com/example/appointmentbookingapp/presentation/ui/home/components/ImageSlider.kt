package com.example.appointmentbookingapp.presentation.ui.home.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.appointmentbookingapp.R
import kotlinx.coroutines.delay

@Composable
fun ImageSlider(modifier: Modifier = Modifier, imageUrls: List<String> ) {

    val pagerState = rememberPagerState(
        pageCount = { imageUrls.size }
    )
    LaunchedEffect(Unit) {
        while (true) {
            delay(4000)
            val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
            pagerState.scrollToPage(nextPage)
        }
    }
//    val scope = rememberCoroutineScope()

    Column(
        modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = modifier.wrapContentSize()) {
            HorizontalPager(
                state = pagerState, modifier.wrapContentSize()

            ) { currentPage ->

                Card(
                    modifier
                        .wrapContentSize()
//                        .padding(top = 16.dp, bottom = 16.dp),
                        .padding( 4.dp)
                    ,
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
//                    Image(
//                        painter = painterResource(id = images[currentPage]), contentDescription = ""
//                    )

//                    SubcomposeAsyncImage()


                   AsyncImage(
                        model = imageUrls[currentPage],
                        contentDescription = "Banner Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(400.dp)
                            .height(180.dp),
                    )

                }
            }
//            IconButton(
//                onClick = {
//                    val nextPage = pagerState.currentPage + 1
//                    if (nextPage < images.size) {
//                        scope.launch {
//                            pagerState.scrollToPage(nextPage)
//                        }
//                    }
//                },
//                modifier
//                    .padding(30.dp)
//                    .size(48.dp)
//                    .align(Alignment.CenterEnd)
//                    .clip(CircleShape),
//                colors = IconButtonDefaults.iconButtonColors(
//                    containerColor = Color(0x52373737)
//                )
//            ) {
//                Icon(
//                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
//                    contentDescription = "",
//                    modifier.fillMaxSize(),
//                    tint = Color.LightGray
//                )
//            }
//            IconButton(
//                onClick = {
//                    val prevPage = pagerState.currentPage - 1
//                    if (prevPage >= 0) {
//                        scope.launch {
//                            pagerState.scrollToPage(prevPage)
//                        }
//                    }
//                },
//                modifier
//                    .padding(30.dp)
//                    .size(48.dp)
//                    .align(Alignment.CenterStart)
//                    .clip(CircleShape),
//                colors = IconButtonDefaults.iconButtonColors(
//                    containerColor = Color(0x52373737)
//                )
//            ) {
//                Icon(
//                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
//                    contentDescription = "",
//                    modifier.fillMaxSize(),
//                    tint = Color.LightGray
//                )
//            }
        }

        PageIndicator(
            pageCount = imageUrls.size, currentPage = pagerState.currentPage, modifier = modifier
        )

    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        repeat(pageCount) {
            IndicatorDots(isSelected = it == currentPage, modifier = modifier)
        }
    }
}

@Composable
fun IndicatorDots(isSelected: Boolean, modifier: Modifier) {
    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp, label = "")
    Box(
        modifier = modifier
            .padding(2.dp)
            .size(size.value)
            .clip(CircleShape)
            .background(
                if (isSelected) colorResource(id = R.color.colorPrimary) else colorResource(
                    id = R.color.gray
                )
            )
    )

}
