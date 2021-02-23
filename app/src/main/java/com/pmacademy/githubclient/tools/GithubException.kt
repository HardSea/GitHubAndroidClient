package com.pmacademy.githubclient.tools

import java.io.IOException

open class GithubException(message: String) : IOException(message)

class UnknownException(message: String): GithubException(message)

class UnauthorizedException(message: String): GithubException(message) // -> 401

class ApiRateLimitExceed(message: String): GithubException(message) // -> 403