package me.dio.credit.application.system.dto

import jakarta.validation.constraints.*
import me.dio.credit.application.system.entity.Credit
import me.dio.credit.application.system.entity.Customer
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

data class CreditDto(
    @field:NotNull(message = "The field creditValue cannot be empty.") val creditValue: BigDecimal,
    @field:Future val dayFirstOfInstallment: LocalDate,
    @field:Positive(message = "Number of installments must be equal to 1 or higher.")
    @field:Min(value = 1, message = "Minimum value is 1.")
    @field:Max(value = 48, message = "Installments can´t be higher than 48.") val numberOfInstallments: Int,
    @field:NotNull(message = "The field creditValue cannot be empty.") val customerId: Long,
    val creditCode: UUID
) {

    fun toEntity(): Credit = Credit(
        creditValue = this.creditValue,
        dayFirstInstallment = this.dayFirstOfInstallment,
        numberOfInstallments = this.numberOfInstallments,
        customer = Customer(id = this.customerId)
    )
}
