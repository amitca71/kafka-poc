package io.confluent.developer;

import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;
import com.google.crypto.tink.Aead;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.aead.AesGcmKeyManager;
import com.google.crypto.tink.KeyTemplate;
import com.google.crypto.tink.config.TinkConfig;	
import com.google.crypto.tink.aead.AeadConfig;
import com.google.crypto.tink.Registry;
import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.aead.AesGcmKeyManager;
import com.google.crypto.tink.JsonKeysetWriter;
import java.io.File;
import java.util.Base64;
import java.security.Key;



@UdfDescription(name = "vwap", description = "Volume weighted encrypt")
public class VwapUdf {

    @Udf(description = "vwap for market prices as integers, returns double")
    public double vwap(
            @UdfParameter(value = "name")
            final String name,
            @UdfParameter(value = "bidQty")
            final int bidQty,
            @UdfParameter(value = "ask")
            final int ask,
            @UdfParameter(value = "askQty")
            final int askQty) {
                  try {
    KeysetHandle keysetHandle = KeysetHandle.generateNew(
        AesGcmKeyManager.aes128GcmTemplate());
    String keysetFilename = "my_keyset.json";
    CleartextKeysetHandle.write(keysetHandle, JsonKeysetWriter.withFile( new File(keysetFilename)));

    // 2. Get the primitive.
    Aead aead = keysetHandle.getPrimitive(Aead.class);
    String plaintext="AAAAA";
    String encodedString = Base64.getEncoder().encodeToString(plaintext.getBytes());
    byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
    String decodedString = new String(decodedBytes);

    String aad="BBBBB";
    // 3. Use the primitive to encrypt a plaintext,
    byte[] ciphertext = aead.encrypt(plaintext.getBytes(), aad.getBytes());

    // ... or to decrypt a ciphertext.
    byte[] decrypted = aead.decrypt(ciphertext, aad.getBytes());
                  }
                  catch(Exception e) {
			  //  Block of code to handle errors
		}
            
        return ((ask * askQty) + (bid * bidQty)) / (bidQty + askQty);
    }

    @Udf(description = "vwap for market prices as doubles, returns double")
    public double vwap(
            @UdfParameter(value = "bid")
            final double bid,
            @UdfParameter(value = "bidQty")
            final int bidQty,
            @UdfParameter(value = "ask")
            final double ask,
            @UdfParameter(value = "askQty")
            final int askQty) {
        return ((ask * askQty) + (bid * bidQty)) / (bidQty + askQty);
    }
}
