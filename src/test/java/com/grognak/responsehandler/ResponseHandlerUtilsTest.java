package com.grognak.responsehandler;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Chris on 2/23/2016.
 */
public class ResponseHandlerUtilsTest {
    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testIsRequestWithOneParam() {
        assertThat(ResponseHandlerUtils.isRequest("anyRequest", "anyOneParam")).isFalse();
    }

    @Test
    public void testIsRequestWrongRequest() {
        assertThat(ResponseHandlerUtils.isRequest("badRequest", "#someChannel :!doSomething")).isFalse();
    }

    @Test
    public void testIsRequestSameRequest() {
        assertThat(ResponseHandlerUtils.isRequest("validRequest", "#someChannel :!validRequest params")).isTrue();
    }

    @Test
    public void testGetRequestAudienceBad() {
        String prefix = "anyPrefix";
        String params = "#someChannel :?doSomething";

        exception.expect(RuntimeException.class);
        ResponseHandlerUtils.getRequestAudience(prefix, params);
    }

    @Test
    public void testGetRequestAudienceChannel() {
        String prefix = "anyPrefix";
        String params = "#someChannel :@doSomething";

        assertThat(ResponseHandlerUtils.getRequestAudience(prefix, params)).isEqualTo("#someChannel");
    }

    @Test
    public void testGetRequestAudienceUser() {
        String prefix = ":ircUser!~username@127.0.0.1";
        String paramsDot = "#someChannel :.doSomething";
        String paramsExc = "#someChannel :!doSomething";

        assertThat(ResponseHandlerUtils.getRequestAudience(prefix, paramsDot)).isEqualTo("ircUser");
        assertThat(ResponseHandlerUtils.getRequestAudience(prefix, paramsExc)).isEqualTo("ircUser");
    }
}