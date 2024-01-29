package me.dio.credit.application.system.dto

import jakarta.validation.constraints.Future
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import me.dio.credit.application.system.entity.Credit
import me.dio.credit.application.system.entity.Customer
import java.math.BigDecimal
import java.time.LocalDate

data class CreditDto(
    @field:NotNull(message = "The field creditValue cannot be empty.") val creditValue: BigDecimal,
    @field:Future
    @field:Min(value = 90, message = "First installment must wait at least 3 months.") val dayFirstOfInstallment: LocalDate,
    @field:Positive(message = "Number of installments must be equal to 1 or higher.")
    @field:Min(value = 1, message = "Minimum value is 1.")
    @field:Max(value = 48, message = "Installments can´t be higher than 48.") val numberOfInstallments: Int,
    @field:NotNull(message = "The field creditValue cannot be empty.") val customerId: Long
) {

    fun toEntity(): Credit = Credit(
        creditValue = this.creditValue,
        dayFirstInstallment = this.dayFirstOfInstallment,
        numberOfInstallments = this.numberOfInstallments,
        customer = Customer(id = this.customerId)
    )
}
