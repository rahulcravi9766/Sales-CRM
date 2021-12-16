package com.rahul.viewModel

import androidx.lifecycle.ViewModel
import com.rahul.repository.SalesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(
    repository: SalesRepository
) : ViewModel() {
}