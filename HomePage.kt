package com.example.newsapp

import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.kwabenaberko.newsapilib.models.Article
import org.w3c.dom.Text


@Composable
fun homePage(newsViewModel: NewsViewModel) {
    val article by newsViewModel.article.observeAsState(emptyList())

    Column(Modifier.fillMaxSize()) {
        CategoriesBar(newsViewModel)
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(article) { article ->
                articleItems(article)
            }
        }
    }
}

@Composable
fun articleItems(article: Article) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.surfaceVariant),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = article.urlToImage ?: "https://static.vecteezy.com/system/resources/thumbnails/004/141/669/small/no-photo-or-blank-image-icon-loading-images-or-missing-image-mark-image-not-available-or-image-coming-soon-sign-simple-nature-silhouette-in-frame-isolated-illustration-vector.jpg",
                contentDescription = "Article Image",
                modifier = Modifier
                    .size(100.dp)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = article.title,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.padding(top = 4.dp))
                Text(
                    text = article.source.name,
                    fontSize = 14.sp,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
@Composable
fun ArticleItem(article: Article){

}
@Composable
fun CategoriesBar(newsViewModel: NewsViewModel){

    var serach by remember {
        mutableStateOf("")
    }
    var isSearching by remember {
        mutableStateOf(false)
    }

    val clist = listOf(
        "GENERAL",
        "TECHNOLOGY",
        "BUSINESS",
        "ENTERTAINMENT",
        "HEALTH",
        "SCIENCE",
        "SPORTS"
    )

    Row(
        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ){

        if(isSearching){
            OutlinedTextField(
                value = serach,
                onValueChange = {
                    serach=it
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            isSearching = false
                            if(serach.isNotEmpty()){
                                newsViewModel.fetchNewswithsearch(serach)
                            }
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    }
                },
                modifier = Modifier.padding(4.dp).height(48.dp)
                    .border(1.dp, Color.Gray, CircleShape)
                    .clip(CircleShape),



            )
        }
        else{
            IconButton(onClick = {
                isSearching = true
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        }
        clist.forEach {
            category->
            Button(
                onClick = {
                    newsViewModel.fetchNews(category)
                },
                modifier = Modifier.padding(4.dp)
            ){
                Text(text = category)
            }
        }

    }
}