/*
 IntentHttpMessageWriter.java
 Copyright (c) 2014 NTT DOCOMO,INC.
 Released under the MIT license
 http://opensource.org/licenses/mit-license.php
 */
package com.nttdocomo.dconnect.message.intent.impl.io;

import java.io.IOException;
import java.util.logging.Logger;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpMessage;
import org.apache.http.io.HttpMessageWriter;
import org.apache.http.params.HttpParams;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.nttdocomo.dconnect.message.DConnectMessage;
import com.nttdocomo.dconnect.message.HttpHeaders;
import com.nttdocomo.dconnect.message.http.impl.factory.HttpMessageFactory;
import com.nttdocomo.dconnect.message.intent.impl.factory.IntentMessageFactory;

/**
 * HTTPメッセージパーサー.
 * @author NTT DOCOMO, INC.
 */
public class IntentHttpMessageWriter implements HttpMessageWriter {

    /**
     * ロガー.
     */
    private Logger mLogger = Logger.getLogger("dconnect.device");

    /**
     * コンテキスト.
     */
    private Context mContext;

    /**
     * コンストラクタ.
     * @param context コンテキスト
     * @param params HTTPパラメータ
     */
    public IntentHttpMessageWriter(
            final Context context,
            final HttpParams params) {
        mLogger.entering(getClass().getName(), "IntentHttpMessageWriter");
        mContext = context;
        mLogger.exiting(getClass().getName(), "IntentHttpMessageWriter");
    }

    @Override
    public void write(final HttpMessage message) throws IOException, HttpException {
        mLogger.entering(getClass().getName(), "write");

        DConnectMessage dmessage =
                HttpMessageFactory.getMessageFactory().newDConnectMessage(message);
        Intent intent =
                IntentMessageFactory.getMessageFactory().newPackagedMessage(dmessage);

        // put intent optional extras
        Header host = message.getFirstHeader(HttpHeaders.HOST);
        if (host != null) {
            intent.setComponent(ComponentName.unflattenFromString(host.getValue()));
        }
        intent.putExtra(DConnectMessage.EXTRA_RECEIVER,
                new ComponentName(mContext, IntentResponseReceiver.class));

        // send broadcast
        mLogger.fine("send request broadcast: " + intent);
        mLogger.fine("send request extra: " + intent.getExtras());
        mContext.sendBroadcast(intent);

        mLogger.exiting(getClass().getName(), "write");
    }

}
