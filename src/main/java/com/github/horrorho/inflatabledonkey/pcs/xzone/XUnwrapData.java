/*
 * The MIT License
 *
 * Copyright 2016 Ahseya.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.github.horrorho.inflatabledonkey.pcs.xzone;

import com.github.horrorho.inflatabledonkey.crypto.rfc6637.RFC6637;
import com.github.horrorho.inflatabledonkey.crypto.rfc6637.RFC6637Factory;
import java.math.BigInteger;
import java.util.Objects;
import java.util.function.BiFunction;
import net.jcip.annotations.Immutable;
import org.bouncycastle.util.encoders.Hex;

/**
 * XUnwrapData.
 *
 * @author Ahseya
 */
@Immutable
public final class XUnwrapData implements BiFunction<byte[], BigInteger, byte[]> {

    public static final XUnwrapData instance() {
        return INSTANCE;
    }

    private static final RFC6637 RFC6637_SECP256R1 = RFC6637Factory.secp256r1();
    private static final byte[] FINGERPRINT = Hex.decode("66696E6765727072696E74000000000000000000");

    private static final XUnwrapData INSTANCE = new XUnwrapData(RFC6637_SECP256R1, FINGERPRINT);

    private final RFC6637 rfc6637;
    private final byte[] fingerprint;

    public XUnwrapData(RFC6637 rfc6637, byte[] fingerprint) {
        this.rfc6637 = Objects.requireNonNull(rfc6637, "rfc6637");
        this.fingerprint = Objects.requireNonNull(fingerprint, "fingerprint");
    }

    @Override
    public byte[] apply(byte[] wrappedData, BigInteger d) {
        return rfc6637.unwrap(wrappedData, fingerprint, d);
    }
}
