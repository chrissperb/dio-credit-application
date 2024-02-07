package me.dio.credit.application.system.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import me.dio.credit.application.system.entity.Address
import me.dio.credit.application.system.entity.Credit
import me.dio.credit.application.system.entity.Customer
import me.dio.credit.application.system.enummeration.Status
import me.dio.credit.application.system.repository.CreditRepository
import me.dio.credit.application.system.repository.CustomerRepository
import me.dio.credit.application.system.service.impl.CreditService
import me.dio.credit.application.system.service.impl.CustomerService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CreditServiceTest {
    @MockK
    lateinit var customerRepository: CustomerRepository

    @MockK
    lateinit var customerService: CustomerService

    @InjectMockKs
    lateinit var creditService: CreditService

    @MockK
    lateinit var creditRepository: CreditRepository

    @Test
    fun `should save credit`() {
        // Given
        val fakeCustomer = buildCustomer()
        val fakeCredit = buildCredit(customer = fakeCustomer)
        every { customerService.findById(fakeCredit.customer?.id!!) } returns fakeCustomer
        every { creditRepository.save(fakeCredit) } returns fakeCredit
        // When
        val savedCredit = creditService.save(fakeCredit)

        // Then
        assertThat(savedCredit).isNotNull
        assertThat(savedCredit.customer).isEqualTo(fakeCustomer)
        verify(exactly = 1) { creditRepository.save(savedCredit) }
    }

    @Test
    fun `should find all credits by customer id`() {
        // Given
        val customerId = 1L
        val fakeCredits = listOf(buildCredit(), buildCredit(), buildCredit())
        every { creditRepository.findAllByCustomerId(customerId) } returns fakeCredits

        // When
        val creditsFound = creditService.findAllByCustomer(customerId)

        // Then
        assertThat(creditsFound).isNotNull
        assertThat(creditsFound).isEqualTo(fakeCredits)
        assertThat(creditsFound).hasSize(fakeCredits.size)
    }

    @Test
    fun `should find credit by credit code`() {
        // Given
        val customerId = 1L
        val creditCode = UUID.randomUUID()
        val fakeCredit = buildCredit()
        every { creditRepository.findByCreditCode(creditCode) } returns fakeCredit
        // When
        val creditFound = creditService.findByCreditCode(customerId, creditCode)
        // Then
        Assertions.assertEquals(fakeCredit, creditFound)
    }

    companion object {
        fun buildCredit(
            creditCode: UUID = UUID.randomUUID(),
            creditValue: BigDecimal = BigDecimal.valueOf(10000),
            dayFirstInstallment: LocalDate = LocalDate.of(2025, 2, 1),
            numberOfInstallments: Int = 24,
            status: Status = Status.IN_PROGRESS,
            customer: Customer? = buildCustomer(),
            id: Long? = 1L
        ) = Credit(
            creditCode = creditCode,
            creditValue = creditValue,
            dayFirstInstallment = dayFirstInstallment,
            numberOfInstallments = numberOfInstallments,
            status = status,
            customer = customer,
            id = id
        )

        fun buildCustomer(
            firstName: String = "Chris",
            lastName: String = "Sperb",
            cpf: String = "80764029053",
            email: String = "chrissperb@gmail.com",
            password: String = "1234",
            zipCode: String = "88495000",
            street: String = "Rua Manoel",
            income: BigDecimal = BigDecimal.valueOf(2700.0),
            id: Long = 1L
        ) = Customer(
            firstName = firstName,
            lastName = lastName,
            cpf = cpf,
            email = email,
            password = password,
            address = Address(
                zipCode = zipCode,
                street = street
            ),
            income = income,
            id = id
        )
    }
}
