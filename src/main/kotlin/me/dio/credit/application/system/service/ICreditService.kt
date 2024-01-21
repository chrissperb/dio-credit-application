package me.dio.credit.application.system.service

import me.dio.credit.application.system.entity.Credit
import org.aspectj.apache.bcel.classfile.Code
import java.util.UUID

interface ICreditService {
    fun save(credit: Credit): Credit
    fun findAllByCustomer(customerId: Long): List<Credit>
    fun findByCreditCode(customerId: Long, creditCode: UUID):Credit
}