package com.ium.easyreps.config

/**
 * Config singleton
 * read value from a json file
 */
class Config(
    var ip: String,
    var port: Int,
    var servletLogin: String,
    var servletCourses: String,
    var servletReservation: String
) {
    companion object {
        private var configSingleton: Config? = null

        fun getInstance(): Config {
            if (configSingleton == null) {
                configSingleton = Config("8.8.8.8", 8080, "login", "", "")
            }

            return configSingleton as Config
        }
    }
}