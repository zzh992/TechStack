package com.techstack.pms.dao.facade.utils;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;



@ActiveProfiles("test")
@ContextConfiguration(locations={"classpath:/applicationContext-test-jpa.xml"})
@Transactional
//@ContextConfiguration(locations={"classpath:/applicationContext-test-mybatis.xml"})
public abstract class SpringTransactionalJunit4Test extends AbstractTransactionalJUnit4SpringContextTests{

	
}
