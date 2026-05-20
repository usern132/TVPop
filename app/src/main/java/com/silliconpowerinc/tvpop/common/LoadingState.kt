package com.silliconpowerinc.tvpop.common

interface LoadingState {
    object Loading : LoadingState
    data class Success<T>(val data: T) : LoadingState
    data class Error(val message: String?) : LoadingState
}