package me.dio.credit.application.system.service

import me.dio.credit.application.system.entity.Customer
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ICustomerService {
    fun save(customer: Customer): Customer
    fun findById(id: Long): Customer
    fun findAll(pageable: Pageable): Page<Customer>
    fun deleteById(id: Long)
}