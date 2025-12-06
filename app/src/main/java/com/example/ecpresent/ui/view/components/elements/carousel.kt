package com.example.ecpresent.ui.view.components.elements

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Carousel(
    imageList: List<Int>,
    modifier: Modifier = Modifier
) {
    val pagerState = rememberPagerState(pageCount = { imageList.size })

    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            val nextPage = (pagerState.currentPage + 1) % imageList.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Box(modifier = modifier) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            Image(
                painter = painterResource(id = imageList[page]),
                contentDescription = "Carousel Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
        }
    }
}