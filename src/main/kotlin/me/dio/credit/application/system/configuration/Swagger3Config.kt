package me.dio.credit.application.system.configuration

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.OpenAPI
import me.dio.credit.application.system.entity.Customer
import org.springdoc.core.models.GroupedOpenApi
import org.springdoc.core.utils.SpringDocUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.bind.annotation.*

@Configuration
class Swagger3Config {

    init {
        SpringDocUtils.getConfig().replaceWithClass(OpenAPI::class.java, CustomOpenAPI::class.java)
    }

    @Bean
    fun publicApi(): GroupedOpenApi? {
        return GroupedOpenApi.builder()
            .group("springcreditapplicationsystem-public")
            .pathsToMatch("/api/customer/**", "/api/credits/**")
            .build()
    }
}

class CustomOpenAPI : OpenAPI() {
    init {
        this.info = Info()
            .title("Credit Application System")
            .description("API for managing customers and credits for a fictional company. This code is for educational purposes only.")
            .version("1.0")
    }
}

@RestController
@RequestMapping("/api/customers")
@Tag(name = "Customer", description = "Operations related to customers")
class CustomerController {

    @RequestMapping(value = ["/{customerId}"], method = [RequestMethod.GET])
    @Operation(summary = "Get customer by ID", description = "Get a customer based on their ID")
    fun getCustomer(@PathVariable customerId: Long) {
    }

    @RequestMapping(method = [RequestMethod.POST])
    @Operation(summary = "Create a new customer", description = "Create a new customer with the provided data")
    fun saveCustomer(@RequestBody @Schema(description = "Customer details") customer: Customer) {
    }
}