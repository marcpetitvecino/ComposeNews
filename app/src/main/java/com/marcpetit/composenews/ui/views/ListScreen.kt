package com.marcpetit.composenews.ui.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.marcpetit.composenews.Destinations
import com.marcpetit.composenews.R
import com.marcpetit.composenews.domainmodel.News
import com.marcpetit.composenews.ui.theme.ComposeNewsTheme
import com.marcpetit.composenews.ui.viewmodel.ListScreenViewModel

@Composable
fun ListScreen(
    navController: NavController,
    viewModel: ListScreenViewModel = hiltViewModel()
) {
    val newsList by viewModel.getNewsList().observeAsState(initial = emptyList())
    ListScreen(navController = navController, newsList)
}

// There are two ListScreen composables since we need one without ViewModel for the preview

@Composable
fun ListScreen(
    navController: NavController,
    newsList: List<News>
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Top news") }
            )
        }
    ) {
        LazyColumn() {
            items(newsList) { news ->
                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .clickable {
                            navController.navigate("${Destinations.DETAIL_SCREEN}/${news.title}")
                        }
                ) {
                    Column {
                        Image(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(16f / 9f),
                            painter = rememberAsyncImagePainter(
                                model = news.urlToImage,
                                placeholder = painterResource(id = R.drawable.ic_loading),
                                error = painterResource(id = R.drawable.ic_error)
                            ),
                            contentDescription = news.title,
                            contentScale = ContentScale.Fit
                        )
                        Column(modifier = Modifier.padding(8.dp)) {
                            Text(text = news.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Text(text = news.content ?: "", maxLines = 3, overflow = TextOverflow.Ellipsis)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListScreenPreview() {
    ComposeNewsTheme {
        ListScreen(
            navController = rememberNavController(),
            newsList = arrayListOf(
                News(
                    "S&P 500 Falls Further After Entering Bear Market - The Wall Street Journal",
                    "The S&amp;P 500 continued its slide on Tuesday, a day after it closed in a bear market for the first time since 2020. The broad market index fell 14.15 points, or 0.4%, to 3735.48, ending the session… [+4874 chars]",
                    "Alexander Osipovich, Anna Hirtenstein, Dave Sebastian",
                    "https://www.wsj.com/articles/global-stocks-markets-dow-update-06-14-2022-11655179460",
                    "https://images.wsj.net/im-563340/social"
                ),
                News(
                    "S&P 500 Falls Further After Entering Bear Market - The Wall Street Journal",
                    "The S&amp;P 500 continued its slide on Tuesday, a day after it closed in a bear market for the first time since 2020. The broad market index fell 14.15 points, or 0.4%, to 3735.48, ending the session… [+4874 chars]",
                    "Alexander Osipovich, Anna Hirtenstein, Dave Sebastian",
                    "https://www.wsj.com/articles/global-stocks-markets-dow-update-06-14-2022-11655179460",
                    "https://images.wsj.net/im-563340/social"
                )
            )
        )
    }
}
