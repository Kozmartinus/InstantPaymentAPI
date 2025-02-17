package com.paymentapi;

import com.paymentapi.model.dto.PaymentRequestDTO;
import com.paymentapi.model.entity.Account;
import com.paymentapi.repository.AccountRepository;
import com.paymentapi.service.payment.PaymentManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.testcontainers.shaded.org.hamcrest.Matchers;

import java.math.BigDecimal;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class PaymentApiApplicationTests {

	@Autowired
	PaymentManager paymentManager;
	@Autowired
	AccountRepository accountRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void processPayment_multipleConcurrentThreadsWithSameIK_processedOnce() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		CountDownLatch latch = new CountDownLatch(10);

		for (int i = 0; i < 10; i++) {
			executor.submit(() -> {
				try {
					PaymentRequestDTO request = new PaymentRequestDTO();
					request.setSenderId(1L);
					request.setReceiverId(2L);
					request.setAmount(BigDecimal.valueOf(100));
					request.setIdempotencyKey("1-2-date-uniqueId");
					paymentManager.processPayment(request);
				} finally {
					latch.countDown();
				}
			});
		}
		latch.await();
		executor.shutdown();

		Account sender = accountRepository.findById(1L).orElseThrow();
		assertThat(BigDecimal.valueOf(900), Matchers.comparesEqualTo(sender.getBalance()));
	}

	@Test
	public void processPayment_multipleConcurrentThreadsWithDifferentIK_processedAll() throws InterruptedException {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		CountDownLatch latch = new CountDownLatch(10);

		for (int i = 0; i < 10; i++) {
			int finalI = i;
			executor.submit(() -> {
				try {
					PaymentRequestDTO request = new PaymentRequestDTO();
					request.setSenderId(1L);
					request.setReceiverId(2L);
					request.setAmount(BigDecimal.valueOf(100));
					request.setIdempotencyKey("1-2-date-uniqueId" + finalI);
					paymentManager.processPayment(request);
				} finally {
					latch.countDown();
				}
			});
		}
		latch.await();
		executor.shutdown();

		Account sender = accountRepository.findById(1L).orElseThrow();
		assertThat(BigDecimal.valueOf(0), Matchers.comparesEqualTo(sender.getBalance()));
	}

}
