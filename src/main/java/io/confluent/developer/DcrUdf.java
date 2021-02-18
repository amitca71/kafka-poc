package io.confluent.developer;

import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;
import java.util.Base64;
import java.security.Key;



//    byte[] decodedBytes = Base64.getDecoder().decode(encodedString);


@UdfDescription(name = "dcr", description = "decrypt")
public class DcrUdf {

    @Udf(description = "decrypy decode 64")
    public String dcr(
            @UdfParameter(value = "encodedString")
            final String encodedString
            ) {
            	byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        	String decodedString = new String(decodedBytes);
        return (decodedString);
    }

}
