package com.abdelmageed.marveltask.ui.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.abdelmageed.marveltask.data.remote.response.MarvelCharacterResponse
import com.abdelmageed.marveltask.data.remote.response.ResultsItem
import com.abdelmageed.marveltask.data.remote.response.dto.MarvelCharactersDto
import com.abdelmageed.marveltask.data.utils.BaseResult
import com.abdelmageed.marveltask.domain.InvokeCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val invokeCharactersUseCase: InvokeCharactersUseCase) :
    ViewModel() {

    private val _state = MutableStateFlow<PagingData<ResultsItem>>(PagingData.empty())
    val state: StateFlow<PagingData<ResultsItem>> = _state

    private val loading: MutableState<Boolean> = mutableStateOf(true)

    fun setLoading(isLoading: Boolean) {
        loading.value = isLoading
    }

    fun showLoading(): Boolean {
        return loading.value
    }

    /**
     * Fetches all characters and updates the state with the result.
     *
     * This function launches a coroutine to execute the `invokeCharactersUseCase` and collect its results.
     * The results are then cached using `cachedIn(viewModelScope)` to avoid redundant network calls.
     * Finally, the collected results are used to update the state value.
     */
    fun getAllCharacters() {
        viewModelScope.launch {
            invokeCharactersUseCase.invoke().cachedIn(viewModelScope)
                .collect {
                    _state.value = it
                }
        }
    }
}