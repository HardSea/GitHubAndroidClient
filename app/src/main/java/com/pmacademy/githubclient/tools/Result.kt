package com.pmacademy.githubclient.tools

class Result<T, E> private constructor(
    private val success: ValueWrapper<T>? = null,
    private val error: ValueWrapper<E>? = null
) {
    private class ValueWrapper<T>(val value: T)

    var isError = error != null

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

    companion object {
        fun <T, E> success(entity: T): Result<T, E> {
            return Result(ValueWrapper(entity), null)
        }

        fun <T, E> error(error: E): Result<T, E> {
            return Result(null, ValueWrapper(error))
        }
    }
}