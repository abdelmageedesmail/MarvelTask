package com.abdelmageed.marveltask.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelmageed.marveltask.data.remote.response.MarvelCharacterResponse
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
    private val _state = MutableStateFlow<HomeState>(HomeState.Init)
    val state: StateFlow<HomeState> get() = _state

    fun getAllCharacters() {
        viewModelScope.launch {
            invokeCharactersUseCase.invoke().catch {
                _state.value = HomeState.ErrorResponse(it.message.toString())
            }.collect {
                when (it) {
                    is BaseResult.Success ->
                        _state.value = HomeState.SuccessGetCharacters(it.data)

                    is BaseResult.Error -> _state.value = HomeState.ErrorResponse(it.error)
                }

            }
        }
    }
}

sealed class HomeState {
    data object Init : HomeState()
    data class SuccessGetCharacters(val response: MarvelCharacterResponse) : HomeState()
    data class ErrorResponse(val message: String) : HomeState()
}