package com.marcpetit.composenews.ui.views

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import com.marcpetit.composenews.R
import com.marcpetit.composenews.domainmodel.News
import com.marcpetit.composenews.ui.viewmodel.DetailScreenViewModel

@Composable
fun DetailScreen(
    newTitle: String,
    navController: NavController,
    viewModel: DetailScreenViewModel = hiltViewModel()
) {
    val news by viewModel.getNews(newTitle).observeAsState(initial = null)
    DetailScreen(newTitle = newTitle, navController = navController, news = news)
}

// There are two ListScreen composables since we need one without ViewModel for the preview

@Composable
fun DetailScreen(
    newTitle: String,
    navController: NavController,
    news: News?
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(newTitle, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) {
        news?.let {
            Card(
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Column {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f),
                        painter = rememberAsyncImagePainter(
                            model = it.urlToImage,
                            placeholder = painterResource(id = R.drawable.ic_loading),
                            error = painterResource(id = R.drawable.ic_error)
                        ),
                        contentDescription = it.title,
                        contentScale = ContentScale.Fit
                    )
                    Column(modifier = Modifier.padding(8.dp)) {
                        val context = LocalContext.current
                        Text(text = it.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text(text = it.content ?: "", maxLines = 3, overflow = TextOverflow.Ellipsis)
                        Box(modifier = Modifier.size(8.dp))
                        Button(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            onClick = {
                                Intent(Intent.ACTION_VIEW, Uri.parse(news.url)).apply {
                                    context.startActivity(this)
                                }
                            }
                        ) {
                            Text(text = "Read more")
                        }
                    }
                }
            }
        } ?: run {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    DetailScreen(
        newTitle = "Hello",
        navController = rememberNavController(),
        news = News(
            title = "Test detail",
            content = "Lorem ipsum dolor sit amet",
            author = "Johnny",
            url = "https://www.wsj.com/articles/global-stocks-markets-dow-update-06-14-2022-11655179460",
            urlToImage = "https://images.wsj.net/im-563340/social"
        )
    )
}
