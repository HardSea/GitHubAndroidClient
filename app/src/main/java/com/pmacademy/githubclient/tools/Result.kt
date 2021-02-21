package com.pmacademy.githubclient.tools

class Result<T, E> private constructor(
    private val success: ValueWrapper<T>? = null,
    private val error: ValueWrapper<E>? = null
) {
    private class ValueWrapper<T>(val value: T)

    var isError = error != null

    val successResult: T
        get() = requireNotNull(success) {
            "Result was without success"
        }.value

    val errorResult: E
        get() = requireNotNull(error) {
            "Result was without error"
        }.value

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
            return Result(ValueWrapper(entity), null)
        }

        fun <T, E> error(error: E): Result<T, E> {
            return Result(null, ValueWrapper(error))
        }
    }
}

//package com.pmacademy.githubclient.tools
//
//import androidx.annotation.Nullable
//
//
//class com.pmacademy.githubclient.tools.Result<T> private constructor(
//    val status: Status,
//    val data: T?,
//    val message: String?
//) {
//    enum class Status {
//        SUCCESS, ERROR, LOADING
//    }
//
//    companion object {
//        fun <T> success(data: T): com.pmacademy.githubclient.tools.Result<T> {
//            return com.pmacademy.githubclient.tools.Result(Status.SUCCESS, data, null)
//        }
//
//        fun <T> error(msg: String?, @Nullable data: T): com.pmacademy.githubclient.tools.Result<T> {
//            return com.pmacademy.githubclient.tools.Result(Status.ERROR, data, msg)
//        }
//
////fun <T> error(msg: String?, @Nullable data: T): com.pmacademy.githubclient.tools.Result<T> {
////            return com.pmacademy.githubclient.tools.Result(Status.ERROR, data, msg)
////        }
//
//        fun <T> loading(@Nullable data: T): com.pmacademy.githubclient.tools.Result<T> {
//            return com.pmacademy.githubclient.tools.Result(Status.LOADING, data, null)
//        }
//    }
//}
//
