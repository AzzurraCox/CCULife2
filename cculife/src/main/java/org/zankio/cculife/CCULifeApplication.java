package org.zankio.cculife;

import android.app.Application;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import com.squareup.leakcanary.LeakCanary;

import org.zankio.ccudata.base.source.http.HTTPSource;
import org.zankio.cculife.override.Net;

import javax.net.ssl.X509TrustManager;

public class CCULifeApplication extends Application {

    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    public void onCreate() {
        super.onCreate();
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        X509TrustManager trustManager = Net.generateTrustManagers(this, "ecourse_ssl.crt");
        HTTPSource.trustManager.put("ecourse.ccu.edu.tw", trustManager);
        HTTPSource.sslSocketFactory.put("ecourse.ccu.edu.tw", Net.generateSSLSocketFactory(trustManager));

        X509TrustManager trustManager1 = Net.generateTrustManagers(this, "wwwtaiwanbustw.crt");
        HTTPSource.trustManager.put("www.taiwanbus.tw", trustManager1);
        HTTPSource.sslSocketFactory.put("www.taiwanbus.tw", Net.generateSSLSocketFactory(trustManager1));

        X509TrustManager trustManager2 = Net.generateTrustManagers(this, "ptxtransportdatatw.crt");
        HTTPSource.trustManager.put("ptx.transportdata.tw", trustManager2);
        HTTPSource.sslSocketFactory.put("ptx.transportdata.tw", Net.generateSSLSocketFactory(trustManager2));


    }
}
