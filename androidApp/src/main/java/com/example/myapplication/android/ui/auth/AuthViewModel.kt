package com.example.myapplication.android.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.android.data.ShoppingRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: ShoppingRepositoryImpl) : ViewModel() {
    fun getTestKey(onResult: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val key = repository.createTestKey()
                repository.authenticate()
                onResult(key)
            } catch (e: Exception) {
                onResult("Ошибка")
            }
        }
    }
}