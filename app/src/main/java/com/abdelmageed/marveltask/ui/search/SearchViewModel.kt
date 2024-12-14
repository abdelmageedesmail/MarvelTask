package com.abdelmageed.marveltask.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelmageed.marveltask.data.remote.response.dto.MarvelCharactersDto
import com.abdelmageed.marveltask.data.utils.BaseResult
import com.abdelmageed.marveltask.domain.InvokeSearchUseCase
import com.abdelmageed.marveltask.ui.details.DetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchUseCase: InvokeSearchUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<SearchState>(SearchState.Init)
    val state: StateFlow<SearchState> get() = _state

    fun search(query: String) {
        viewModelScope.launch {
            _state.value = SearchState.Loading(true)
            searchUseCase.invoke(query).catch {
                _state.value = SearchState.Loading(false)
                _state.value = SearchState.Error(it.message.toString())
            }.collect {
                _state.value = SearchState.Loading(false)
                when (it) {
                    is BaseResult.Success -> _state.value = SearchState.Success(it.data)
                    is BaseResult.Error -> _state.value = SearchState.Error(it.error)
                }
            }
        }
    }

}

sealed class SearchState {
    object Init : SearchState()
    data class Loading(val isLoading: Boolean) : SearchState()
    data class Success(val data: List<MarvelCharactersDto?>) : SearchState()
    data class Error(val message: String) : SearchState()
}