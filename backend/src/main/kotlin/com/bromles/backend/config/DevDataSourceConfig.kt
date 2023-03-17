package com.bromles.backend.config

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import javax.sql.DataSource

@Profile("test")
@Configuration
class DevDataSourceConfig {

    companion object {
        private val log = LoggerFactory.getLogger(DevDataSourceConfig::class.java)
    }

    class KPostgreSQLContainer(image: DockerImageName?) : PostgreSQLContainer<KPostgreSQLContainer>(image)

    @Bean
    fun postgreSQLContainer(): KPostgreSQLContainer {
        val dockerImageName: DockerImageName =
            DockerImageName.parse("postgres:12.4").asCompatibleSubstituteFor("postgres")
        val container = KPostgreSQLContainer(dockerImageName)
        container.start()
        log.info("Database: {}", container.jdbcUrl)
        log.info("DB User: {}", container.username)
        log.info("DB Pass: {}", container.password)
        return container
    }

    @Bean
    @Primary
    fun dataSource(container: KPostgreSQLContainer): DataSource {
        val hikariConfig = HikariConfig()
        hikariConfig.password = container.password
        hikariConfig.username = container.username
        hikariConfig.jdbcUrl = container.jdbcUrl
        hikariConfig.driverClassName = container.driverClassName
        return HikariDataSource(hikariConfig)
    }
}