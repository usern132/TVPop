package com.silliconpowerinc.tvpop.ui.views.details

import com.silliconpowerinc.tvpop.common.LoadingState

private const val SAMPLE_SUCCESS_DATA = "This is a sample AI-generated overview."

sealed class AIOverviewState : LoadingState {
    object Loading : AIOverviewState()
    data class Success(val overview: String = SAMPLE_SUCCESS_DATA) : AIOverviewState()
    data class Error(
        val message: String? = null,
        val errorType: AIOverviewError = AIOverviewError.GenericError
    ) : AIOverviewState()
}

sealed interface AIOverviewError {
    object GenericError : AIOverviewError
    object TVShowNotFound : AIOverviewError
}

data class DetailsScreenState(
    val aiOverviewState: AIOverviewState
)