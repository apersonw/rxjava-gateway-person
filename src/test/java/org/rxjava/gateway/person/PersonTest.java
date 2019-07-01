package org.rxjava.gateway.person;

import org.junit.runner.RunWith;
import org.rxjava.api.user.inner.InnerUserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author happy 2019-06-18 14:59
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {RxGatewayPersonApplication.class, GatewayTestContext.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PersonTest {
    @Autowired
    protected InnerUserApi innerUserApi;
}
