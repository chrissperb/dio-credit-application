package me.dio.credit.application.system.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import me.dio.credit.application.system.entity.Address
import me.dio.credit.application.system.entity.Customer
import org.hibernate.validator.constraints.br.CPF
import java.math.BigDecimal

data class CustomerDto(
    @field:NotEmpty(message = "The field first name cannot be empty.") val firstName: String,
    @field:NotEmpty(message = "The field last name cannot be empty.") val lastName: String,
    @field:NotEmpty(message = "The field CPF cannot be empty.")
    @field:CPF(message = "This CPF is invalid.") val cpf: String,
    @field:NotNull(message = "Income cannot be empty.") val income: BigDecimal,
    @field:NotEmpty(message = "The field e-mail cannot be empty.")
    @field:Email(message = "Email invalid.") val email: String,
    @field:NotEmpty(message = "The field password cannot be empty.") val password: String,
    @field:NotEmpty(message = "The field zipCode cannot be empty.") var zipCode: String,
    @field:NotEmpty(message = "The field street cannot be empty.") val street: String
) {

    fun toEntity(): Customer = Customer(
        firstName = this.firstName,
        lastName = this.lastName,
        cpf = this.cpf,
        email = this.email,
        income = this.income,
        password = this.password,
        address = Address(
            zipCode = this.zipCode,
            street = this.street
        )
    )
}
