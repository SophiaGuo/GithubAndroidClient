package com.sophia.githubandroidclient.base.exception
/**
 *  @author: SophiaGuo
 *  Describe:
 */
class ApiException(var code: Int, override var message: String) : RuntimeException()