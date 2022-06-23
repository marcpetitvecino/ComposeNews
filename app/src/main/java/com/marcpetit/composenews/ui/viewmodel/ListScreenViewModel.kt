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
class ListScreenViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _news = MutableLiveData<List<News>>()

    fun getNewsList(): MutableLiveData<List<News>> {
        viewModelScope.launch(Dispatchers.IO) {
            val news = repository.getNewsList("US")
            _news.postValue(news)
        }
        return _news
    }
}
