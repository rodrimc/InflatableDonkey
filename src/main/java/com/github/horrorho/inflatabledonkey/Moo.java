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
package com.github.horrorho.inflatabledonkey;

import com.github.horrorho.inflatabledonkey.cloud.AssetDownloader;
import com.github.horrorho.inflatabledonkey.cloud.AssetWriter;
import com.github.horrorho.inflatabledonkey.cloud.AuthorizeAssets;
import com.github.horrorho.inflatabledonkey.cloud.AuthorizedAssets;
import com.github.horrorho.inflatabledonkey.data.backup.Asset;
import com.github.horrorho.inflatabledonkey.keybag.KeyBags;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import net.jcip.annotations.ThreadSafe;
import org.apache.http.client.HttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Moo.
 *
 * @author Ahseya
 */
@ThreadSafe
public final class Moo {

    private static final Logger logger = LoggerFactory.getLogger(Moo.class);

    private final AuthorizeAssets authorizeAssets;
    private final AssetDownloader assetDownloader;
    private final KeyBagManager keyBagManager;

    public Moo(AuthorizeAssets authorizeAssets, AssetDownloader assetDownloader, KeyBagManager keyBagManager) {
        this.authorizeAssets = authorizeAssets;
        this.assetDownloader = assetDownloader;
        this.keyBagManager = keyBagManager;
    }

    void download(HttpClient httpClient, List<Asset> assets, Path folder) throws IOException {
        keyBagManager.update(httpClient, assets);
        KeyBags keyBags = new KeyBags(keyBagManager.keyBags());

        AssetWriter cloudWriter = new AssetWriter(folder, keyBags);

        AuthorizedAssets authorizedAssets = authorizeAssets.authorize(httpClient, assets);

        assetDownloader.get(httpClient, authorizedAssets, cloudWriter);
    }
}
// TODO time expiration tokens
// TODO rename, Moo is not an appropriate class name.