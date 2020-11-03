package com.step.toolkit.global

/*
* Reason:  When using companion object under the hood getter and setter instance methods are created for
* the fields to be accessible Calling instance methods is more expensive than calling static methods
*
* NOT Best practice
* class AppConstants {
*       companion object {
*              const val TOKEN_KEY = "user-token"
*       }
* }
*
* */
// Best  practice
object AppConstants {
    /*net*/
    const val BASE_URL = "https://gank.io/api/v2/"
    /*net*/

    /*user*/
    const val TOKEN_KEY = "user-token"
    /*user*/

}