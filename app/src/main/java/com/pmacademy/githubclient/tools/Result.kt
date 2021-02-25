package com.pmacademy.githubclient.tools

class Result<T, E> private constructor(
    private val success: ValueWrapper<T>? = null,
    private val error: ValueWrapper<E>? = null,
    loading: Boolean = true
) {
    private class ValueWrapper<T>(val value: T)

    var isError = error != null
    var isLoading = loading

    val successResult: T
        get() {
            return requireNotNull(success) {
                "Result was without success"
            }.value
        }

    val errorResult: E
        get() {
            return requireNotNull(error) {
                "Result was without error"
            }.value
        }

    fun <R> mapSuccess(transformation: (T) -> (R)): Result<R, E> {
        return Result(
            success = success?.let { ValueWrapper(transformation(it.value)) },
            error = error
        )
    }


    fun <R> mapError(transformation: (E) -> R): Result<T, R> {
        return Result(
            success = success,
            error = error?.let { ValueWrapper(transformation(it.value)) }
        )
    }

    companion object {
        fun <T, E> success(entity: T): Result<T, E> {
            return Result(ValueWrapper(entity), null, false)
        }

        fun <T, E> error(error: E): Result<T, E> {
            return Result(null, ValueWrapper(error), false)
        }
    }
}