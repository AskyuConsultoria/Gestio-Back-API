package consultoria.askyu.gestio.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer as WebMvcConfigurer1

@Configuration
class CorsConfig {
    @Value("\${cors.allowed-origins}")
    private lateinit var allowedOrigins: String

    @Bean
    fun corsConfigurer(): WebMvcConfigurer1 {
        return object : WebMvcConfigurer1 {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**") // Aplica CORS para todas as rotas
                    .allowedOrigins(*allowedOrigins.split(",").toTypedArray()) // Converte string em array
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowCredentials(true)
            }
        }
    }
}