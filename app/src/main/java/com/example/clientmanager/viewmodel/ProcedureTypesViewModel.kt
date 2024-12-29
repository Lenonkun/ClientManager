package com.example.clientmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.clientmanager.data.repository.ProcedureRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProcedureTypesViewModel @Inject constructor(
    private val repository: ProcedureRepository
) : ViewModel() {
    val procedureTypes: StateFlow<List<String>> = repository.getProcedureTypes()
        .stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun addProcedureType(name: String) {
        viewModelScope.launch {
            repository.addProcedureType(name)
        }
    }

    fun updateProcedureType(index: Int, name: String) {
        viewModelScope.launch {
            repository.updateProcedureType(index, name)
        }
    }

    fun deleteProcedureType(name: String) {
        viewModelScope.launch {
            repository.deleteProcedureType(name)
        }
    }
}
