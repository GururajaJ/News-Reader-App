package com.example.newsapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kwabenaberko.newsapilib.NewsApiClient
import com.kwabenaberko.newsapilib.models.Article
import com.kwabenaberko.newsapilib.models.request.EverythingRequest
import com.kwabenaberko.newsapilib.models.request.TopHeadlinesRequest
import com.kwabenaberko.newsapilib.models.response.ArticleResponse

class NewsViewModel: ViewModel() {

    private val _article = MutableLiveData<List<Article>>()

    val article: LiveData<List<Article>> =_article

    init {
        fetchNews()
    }

    fun fetchNews( category: String="GENERAL"){

        val newsApiclient = NewsApiClient(api.apiKey)

        val request = TopHeadlinesRequest.Builder().language("en").category(category).build()


        newsApiclient.getTopHeadlines(request, object: NewsApiClient.ArticlesResponseCallback {
            override fun onSuccess(response: ArticleResponse?) {
                response?.articles?.let {
                    _article.postValue(it)


                }
            }

            override fun onFailure(throwable: Throwable?) {
                Log.i("NewsAPI Responce Failed",throwable?.localizedMessage.toString())
            }

        })
    }
    fun fetchNewswithsearch( query: String){

        val newsApiclient = NewsApiClient(api.apiKey)

        val request = EverythingRequest.Builder().language("en").q(query).build()


        newsApiclient.getEverything(request, object: NewsApiClient.ArticlesResponseCallback {
            override fun onSuccess(response: ArticleResponse?) {
                response?.articles?.let {
                    _article.postValue(it)


                }
            }

            override fun onFailure(throwable: Throwable?) {
                Log.i("NewsAPI Responce Failed",throwable?.localizedMessage.toString())
            }

        })
    }
}