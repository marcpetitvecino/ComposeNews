package com.marcpetit.composenews.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marcpetit.composenews.domainmodel.News
import com.marcpetit.composenews.network.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _news = MutableLiveData<News>()

    fun getNews(title: String): MutableLiveData<News> {
        viewModelScope.launch(Dispatchers.IO) {
            val news = repository.getNews(title)
            _news.postValue(news)
        }
        return _news
    }
}
