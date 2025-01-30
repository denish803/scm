package com.scm;

import com.scm.services.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ScmApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private EmailService emailService;

	@Test
	void emailSender() {
		emailService.sendEmail(
				"kotadiyadenish803@gmail.com",
				"verify the email",
				"This is testing to check the email is work ya not bh=ut you read this mail so this is work and congutulation"
		);
	}

}
