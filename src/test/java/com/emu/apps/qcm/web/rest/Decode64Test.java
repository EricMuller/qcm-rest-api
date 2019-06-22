package com.emu.apps.qcm.web.rest;

import org.junit.Assert;
import org.junit.Test;

import java.util.Base64;

public class Decode64Test {

    @Test
    public void test()  {

        String encoded = "W3sidmFsdWUiOiIxIiwidHlwZSI6InRhZyJ9LHsidmFsdWUiOiIyIiwidHlwZSI6InRhZyJ9XQ==";

        byte[] message = Base64.getDecoder().decode(encoded);

        Assert.assertNotNull(message);


    }
}
