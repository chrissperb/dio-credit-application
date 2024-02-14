package me.dio.credit.application.system.controller

import com.fasterxml.jackson.databind.ObjectMapper
import me.dio.credit.application.system.entity.Address
import me.dio.credit.application.system.entity.Credit
import me.dio.credit.application.system.entity.Customer
import me.dio.credit.application.system.enummeration.Status
import me.dio.credit.application.system.repository.CreditRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import me.dio.credit.application.system.dto.CreditDto
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.math.BigDecimal
import java.time.LocalDate
import java.util.*

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration
class CreditResourceTest {
    @Autowired
    private lateinit var creditRepository: CreditRepository

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    companion object {
        const val URL: String = "/api/credits"
    }

    @BeforeEach
    fun setup() = creditRepository.deleteAll()

    @BeforeEach
    fun customerSetup() {
        val customerDto: Customer = builderCustomerDto()
        val valueAsString: String = objectMapper.writeValueAsString(customerDto)
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
    }


    @AfterEach
    fun tearDown() = creditRepository.deleteAll()

    @Test
    fun `should save a credit and return 201 status`() {
        // Given
        val creditDto: CreditDto = builderCreditDto()
        val valueAsString: String = objectMapper.writeValueAsString(creditDto)
        // When
        // Then
        mockMvc.perform(
            MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString)
        )
            .andExpect(MockMvcResultMatchers.status().isCreated)
    }

    @Test
    fun `should find all credits by customer id`() {
        // Given
        val creditDto1: CreditDto = builderCreditDto()
        val creditDto2: CreditDto = builderCreditDto()
        val valueAsString1: String = objectMapper.writeValueAsString(creditDto1)
        val valueAsString2: String = objectMapper.writeValueAsString(creditDto2)
        // When
        // Then
        mockMvc.perform(
            MockMvcRequestBuilders.get("$URL/${customerId}")
                .contentType(MediaType.APPLICATION_JSON)
                .content(valueAsString1)
                .content(valueAsString2)
        )
            .andExpect(MockMvcResultMatchers.status().isOk)

    }

    @Test
    fun `should find a credit by credit code`() {
        // Given
        val creditDto: CreditDto = builderCreditDto()
        val valueAsString: String = objectMapper.writeValueAsString(creditDto)
        // When
        // Then
        MockMvcRequestBuilders.get("$URL/${creditCode}")
            .contentType(MediaType.APPLICATION_JSON)
            .content(valueAsString)
        )
        .andExpect(MockMvcResultMatchers.status().isOk)
    }

    private fun builderCreditDto(
        creditCode: UUID = UUID.randomUUID(),
        creditValue: BigDecimal = BigDecimal.valueOf(1000),
        dayFirstOfInstallment: LocalDate = LocalDate.of(2024, 4, 1),
        numberOfInstallments: Int = 24,
        status: Status = Status.IN_PROGRESS,
        customer: Customer? = builderCustomerDto(),
        id: Long? = 1L
    ) = CreditDto(
        creditCode = creditCode,
        creditValue = creditValue,
        dayFirstOfInstallment = dayFirstOfInstallment,
        numberOfInstallments = numberOfInstallments,
        customerId = 1
    )

    private fun builderCustomerDto(
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