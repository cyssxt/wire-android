package com.waz.zclient.feature.auth.registration.personal.email.name

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waz.zclient.core.exception.Failure
import com.waz.zclient.core.usecase.DefaultUseCaseExecutor
import com.waz.zclient.core.usecase.UseCaseExecutor
import com.waz.zclient.shared.user.name.ValidateNameFailure
import com.waz.zclient.shared.user.name.ValidateNameParams
import com.waz.zclient.shared.user.name.ValidateNameUseCase
import kotlinx.coroutines.Dispatchers

class CreatePersonalAccountEmailNameViewModel(
    private val validateNameUseCase: ValidateNameUseCase
) : ViewModel(), UseCaseExecutor by DefaultUseCaseExecutor() {

    private val _isValidNameLiveData = MutableLiveData<Boolean>()
    val isValidNameLiveData: LiveData<Boolean> = _isValidNameLiveData

    fun validateName(name: String) {
        validateNameUseCase(viewModelScope, ValidateNameParams(name), Dispatchers.Default) {
            it.fold(::validateNameFailure) { validateNameSuccess() }
        }
    }

    private fun validateNameSuccess() {
        _isValidNameLiveData.value = true
    }

    private fun validateNameFailure(failure: Failure) {
        if (failure is ValidateNameFailure) {
            _isValidNameLiveData.value = false
        }
    }
}
