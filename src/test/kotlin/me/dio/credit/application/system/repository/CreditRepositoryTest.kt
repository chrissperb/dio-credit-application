package me.dio.credit.application.system.repository

import me.dio.credit.application.system.entity.Address
import me.dio.credit.application.system.entity.Credit
import me.dio.credit.application.system.entity.Customer
import me.dio.credit.application.system.enummeration.Status
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CreditRepositoryTest {
    @Autowired
    lateinit var creditRepository: CreditRepository
    @Autowired
    lateinit var testEntityManager: TestEntityManager

    private lateinit var customer: Customer
    private lateinit var credit1: Credit
    private lateinit var credit2: Credit

    @BeforeEach fun setup() {
        customer = testEntityManager.persist(buildCustomer())
        credit1 = testEntityManager.persist(buildCredit())
        credit2 = testEntityManager.persist(buildCredit())
    }

    @Test
    fun `should find credit by credit code`() {
        // Given
        val creditCode1 = UUID.fromString("aa547c0f-9a6a-451f-8c89-afddce916a29")
        val creditCode2 = UUID.fromString("49f740be-46a7-449b-84e7-ff5b7986d7ef")

        val persistedCustomer = testEntityManager.merge(buildCustomer())
        val persistedCredit1 = testEntityManager.merge(buildCredit(customer = persistedCustomer, creditCode = creditCode1))
        val persistedCredit2 = testEntityManager.merge(buildCredit(customer = persistedCustomer, creditCode = creditCode2))

        testEntityManager.flush()

        // When
        val fakeCredit1 = creditRepository.findByCreditCode(creditCode1)
        val fakeCredit2 = creditRepository.findByCreditCode(creditCode2)

        // Then
        Assertions.assertThat(fakeCredit1).isNotNull
        Assertions.assertThat(fakeCredit2).isNotNull
        Assertions.assertThat(fakeCredit1).isSameAs(persistedCredit1)
        Assertions.assertThat(fakeCredit2).isSameAs(persistedCredit2)
    }


    companion object {
        fun buildCredit(
            creditCode: UUID = UUID.randomUUID(),
            creditValue: BigDecimal = BigDecimal.valueOf(10000),
            dayFirstInstallment: LocalDate = LocalDate.of(2024, 3, 1),
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