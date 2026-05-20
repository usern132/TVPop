package com.silliconpowerinc.tvpop.ui.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.AsyncImagePainter.Companion.DefaultTransform
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageScope

/**
 * A customized version of Coil's [SubcomposeAsyncImage] with defaults for the loading and error [Composable]s.
 * All parameters match with Coil's own parameters (and their default values) and are passed down to it.
 *
 * @param model The data to load (e.g., URL, URI).
 * @param contentDescription The description of the image for accessibility.
 * @param modifier The modifier to be applied to the layout.
 * @param transform A function to transform the painter state.
 * @param loading Composable for the loading state. Defaults to [CustomLoading].
 * @param success Composable for the success state.
 * @param error Composable for the error state. Defaults to [CustomError].
 * @param onLoading Callback when the image starts loading.
 * @param onSuccess Callback when the image loads successfully.
 * @param onError Callback when the image fails to load.
 * @param alignment Alignment for the image within its bounds.
 * @param contentScale Content scale for the image.
 * @param alpha Opacity to be applied to the image.
 * @param colorFilter Color filter to be applied to the image.
 * @param filterQuality Filter quality for the image.
 * @param clipToBounds Whether to clip the image to its bounds.
 */
@Composable
fun MyAsyncImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    transform: (AsyncImagePainter.State) -> AsyncImagePainter.State = DefaultTransform,
    loading: @Composable (SubcomposeAsyncImageScope.(AsyncImagePainter.State.Loading) -> Unit)? = { CustomLoading() },
    success: @Composable (SubcomposeAsyncImageScope.(AsyncImagePainter.State.Success) -> Unit)? = null,
    error: @Composable (SubcomposeAsyncImageScope.(AsyncImagePainter.State.Error) -> Unit)? = { CustomError() },
    onLoading: ((AsyncImagePainter.State.Loading) -> Unit)? = null,
    onSuccess: ((AsyncImagePainter.State.Success) -> Unit)? = null,
    onError: ((AsyncImagePainter.State.Error) -> Unit)? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = 1.0f,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DefaultFilterQuality,
    clipToBounds: Boolean = true
) {
    SubcomposeAsyncImage(
        model = model,
        contentDescription = contentDescription,
        modifier = modifier,
        transform = transform,
        loading = loading,
        success = success,
        error = error,
        onLoading = onLoading,
        onSuccess = onSuccess,
        onError = onError,
        alignment = alignment,
        contentScale = contentScale,
        alpha = alpha,
        colorFilter = colorFilter,
        filterQuality = filterQuality,
        clipToBounds = clipToBounds
    )
}

/**
 * Default loading composable for [MyAsyncImage].
 */
@Composable
private fun CustomLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(48.dp)
        )
    }
}

/**
 * Default error composable for [MyAsyncImage].
 */
@Composable
private fun CustomError() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Icon(Icons.Default.Error, contentDescription = null)
    }
}
