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
package com.github.horrorho.inflatabledonkey.pcs.service;

import com.github.horrorho.inflatabledonkey.crypto.eckey.ECPrivate;
import com.github.horrorho.inflatabledonkey.crypto.key.Key;
import com.github.horrorho.inflatabledonkey.crypto.key.KeyID;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.jcip.annotations.Immutable;

/**
 * ServiceKeySet.
 *
 * @author Ahseya
 */
@Immutable
public final class ServiceKeySet {

    private final Map<Integer, Key<ECPrivate>> serviceKeys = new HashMap<>();
    private final String name;
    private final String ksID;
    private final boolean isCompact;

    ServiceKeySet(
            Map<Integer, Key<ECPrivate>> serviceKeys,
            String name,
            String ksID,
            boolean isCompact) {

        this.serviceKeys.putAll(serviceKeys);
        this.name = name;
        this.ksID = ksID;
        this.isCompact = isCompact;
    }

    public Optional<Key<ECPrivate>> key(Service service) {
        return key(service.number());
    }

    public Optional<Key<ECPrivate>> key(int service) {
        return Optional.ofNullable(serviceKeys.get(service));
    }

    public Optional<Key<ECPrivate>> key(KeyID keyID) {
        return serviceKeys.values()
                .stream()
                .filter(key -> key.keyID().equals(keyID))
                .findFirst();
    }

    public Map<Integer, Key<ECPrivate>> services() {
        return new HashMap<>(serviceKeys);
    }

    public Collection<Key<ECPrivate>> keys() {
        return serviceKeys.values();
    }

    public String name() {
        return name;
    }

    public String ksID() {
        return ksID;
    }

    public boolean isCompact() {
        return isCompact;
    }

    @Override
    public String toString() {
        return "ServiceKeys{"
                + "keys=" + serviceKeys
                + ", name=" + name
                + ", ksID=" + ksID
                + ", isCompact=" + isCompact
                + '}';
    }
}
