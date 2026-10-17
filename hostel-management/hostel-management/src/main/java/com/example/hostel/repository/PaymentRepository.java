package com.example.hostel.repository;

import com.example.hostel.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByStudentIdOrderByPaidOnDesc(Long studentId);
    boolean existsByStudentIdAndForMonth(Long studentId, String forMonth);

    @Query("select coalesce(sum(p.amount), 0) from Payment p where p.forMonth = :month")
    BigDecimal totalForMonth(@Param("month") String month);
}
