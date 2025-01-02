package com.client.mqttclient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Scanner;

@Configuration
public class MqttConfig {
    private static final String filename = "your/path/here/mqttconfig.txt";

    private static  String registryURL;
    private static  String clientId;

    private static  String login;
    private static  String password;

    private static  String TRUSTED_ROOT;

    @Bean
    public MqttClient mqttClient() {

        readConfig();

        try {
            MqttClient client = new MqttClient(registryURL, clientId);
            MqttConnectOptions options = new MqttConnectOptions();

            options.setUserName(login);
            options.setPassword(password.toCharArray());
            options.setSocketFactory(getSocketFactory());

            options.setCleanSession(true);
            client.connect(options);
            return client;
        } catch (MqttException e) {
            throw new RuntimeException("Failed to create MQTT client", e);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void readConfig(){
        File file = new File(filename);
        try (Scanner scan = new Scanner(file)) {
            registryURL = scan.nextLine().trim();
            clientId = scan.nextLine().trim();
            login = scan.nextLine().trim();
            password = scan.nextLine().trim();
            TRUSTED_ROOT = scan.nextLine().trim();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File reading error", e);
        }
    }

    //Загружаем сертификат удостоверяющего центра из статической переменной `TRUSTED_ROOT`.
    // Copied from Yandex IoT Core Java
    private SSLSocketFactory getSocketFactory() throws Exception {

        InputStream is = new ByteArrayInputStream(TRUSTED_ROOT.getBytes(StandardCharsets.UTF_8));
        CertificateFactory cFactory = CertificateFactory.getInstance("X.509");
        X509Certificate caCert = (X509Certificate) cFactory.generateCertificate(is);

        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        KeyStore tks = KeyStore.getInstance(KeyStore.getDefaultType());
        tks.load(null); // Вам не нужно загружать из файла экземпляр класса `KeyStore`.
        tks.setCertificateEntry("caCert", caCert);
        tmf.init(tks);

        SSLContext ctx = SSLContext.getInstance("TLS");
        ctx.init(null, tmf.getTrustManagers(), null);
        return ctx.getSocketFactory();
    }

}
