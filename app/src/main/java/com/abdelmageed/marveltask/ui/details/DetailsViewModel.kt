package com.abdelmageed.marveltask.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelmageed.marveltask.data.remote.response.dto.CharacterDetailsListDto
import com.abdelmageed.marveltask.data.remote.response.dto.MarvelCharactersDto
import com.abdelmageed.marveltask.data.remote.response.dto.SectionImageDetailsDto
import com.abdelmageed.marveltask.data.utils.BaseResult
import com.abdelmageed.marveltask.domain.InvokeDetailsUseCase
import com.abdelmageed.marveltask.domain.InvokeSectionDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsUseCase: InvokeDetailsUseCase,
    private val invokeSectionDetails: InvokeSectionDetails
) : ViewModel() {

    private val _state = MutableStateFlow<DetailsState>(DetailsState.Init)
    val state: StateFlow<DetailsState> get() = _state

    private val _sectionDetailsState = MutableStateFlow<DetailsState>(SectionDetailsState.Init)
    val sectionDetailsState: StateFlow<DetailsState> get() = _sectionDetailsState


    fun getVarientInnerList(id: Int) {
        viewModelScope.launch {
            _state.value = DetailsState.Loading(true)
            async { detailsUseCase.invokeCharacterDetails(id) }.await().catch {
                _state.value = DetailsState.ErrorResponse(it.message.toString())
            }.collect {
                _state.value = DetailsState.Loading(false)
                when (it) {
                    is BaseResult.Success -> _state.value = DetailsState.SuccessGetDetails(it.data)
                    is BaseResult.Error -> _state.value = DetailsState.ErrorResponse(it.error)
                }
            }
            async { detailsUseCase.invokeComics(id) }.await().catch {
                _state.value = DetailsState.ErrorResponse(it.message.toString())
            }.collect {
                when (it) {
                    is BaseResult.Success -> {
                        _state.value = DetailsState.SuccessGetComics(it.data)
                    }

                    is BaseResult.Error -> _state.value = DetailsState.ErrorResponse(it.error)
                }
            }
            async { detailsUseCase.invokeSeries(id) }.await().catch {
                _state.value = DetailsState.ErrorResponse(it.message.toString())
            }.collect {
                when (it) {
                    is BaseResult.Success -> {
                        _state.value = DetailsState.SuccessGetSeries(it.data)
                    }

                    is BaseResult.Error -> _state.value = DetailsState.ErrorResponse(it.error)
                }
            }
            async { detailsUseCase.invokeEvents(id) }.await().catch {
                _state.value = DetailsState.ErrorResponse(it.message.toString())
            }.collect {
                when (it) {
                    is BaseResult.Success -> {
                        _state.value = DetailsState.SuccessGetEvents(it.data)
                    }

                    is BaseResult.Error -> _state.value = DetailsState.ErrorResponse(it.error)
                }
            }
            async { detailsUseCase.invokeStories(id) }.await().catch {
                _state.value = DetailsState.ErrorResponse(it.message.toString())
            }.collect {
                when (it) {
                    is BaseResult.Success -> {
                        _state.value = DetailsState.SuccessGetStories(it.data)
                    }

                    is BaseResult.Error -> _state.value = DetailsState.ErrorResponse(it.error)
                }
            }

        }
    }

    fun getSectionImages(url: String) {
        viewModelScope.launch {
            _sectionDetailsState.value = SectionDetailsState.Loading(true)
            invokeSectionDetails.invokeSectionImages(url).catch {
                _sectionDetailsState.value = SectionDetailsState.Loading(false)
                emit(BaseResult.Error(it.message.toString()))
            }.collect {
                _sectionDetailsState.value = SectionDetailsState.Loading(false)
                when (it) {
                    is BaseResult.Success -> _sectionDetailsState.value =
                        SectionDetailsState.SuccessGetDetails(it.data)

                    is BaseResult.Error -> _sectionDetailsState.value =
                        SectionDetailsState.ErrorResponse(it.error)
                }
            }

        }
    }
}

sealed class DetailsState {
    object Init : DetailsState()
    data class Loading(val isLoading: Boolean) : DetailsState()
    data class SuccessGetDetails(val response: MarvelCharactersDto) : DetailsState()
    data class SuccessGetComics(val response: List<CharacterDetailsListDto?>) : DetailsState()
    data class SuccessGetSeries(val response: List<CharacterDetailsListDto?>) : DetailsState()
    data class SuccessGetStories(val response: List<CharacterDetailsListDto?>) : DetailsState()
    data class SuccessGetEvents(val response: List<CharacterDetailsListDto?>) : DetailsState()
    data class ErrorResponse(val message: String) : DetailsState()
}

sealed class SectionDetailsState {
    object Init : DetailsState()
    data class Loading(val isLoading: Boolean) : DetailsState()
    data class SuccessGetDetails(val response: List<SectionImageDetailsDto?>) : DetailsState()
    data class ErrorResponse(val message: String) : DetailsState()

}