package ru.arinae_va.lensa.presentation.feature.feed.compose

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.arinae_va.lensa.domain.model.SpecialistModel
import ru.arinae_va.lensa.domain.repository.IUserInfoRepository
import javax.inject.Inject

@HiltViewModel
class SpecialistDetailsViewModel @Inject constructor(
    private val userInfoRepository: IUserInfoRepository
): ViewModel() {
    private val _screenState = mutableStateOf(SpecialistModel.EMPTY)
    val screenState: State<SpecialistModel> = _screenState

    fun loadSpecialistProfile(userUid: String) {
        viewModelScope.launch {
            val result = userInfoRepository.getProfileById(userUid)

            _screenState.value = result
        }

    }
}