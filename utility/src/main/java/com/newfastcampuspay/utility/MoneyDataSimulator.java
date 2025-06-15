package com.newfastcampuspay.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.json.JSONObject;

public class MoneyDataSimulator {

    private static final String DECREASE_API_ENDPOINT = "http://localhost:8083/money/decrease-eda";

    private static final String INCREASE_API_ENDPOINT = "http://localhost:8083/money/increase-eda";

    private static final String CREATE_MONEY_API_ENDPOINT = "http://localhost:8083/money/create-member-money";

    private static final String REGISTER_ACCOUNT_API_ENDPOINT = "http://localhost:8082/banking/account/register-eda";

    private static final String[] BANK_NAME = {"KBB", "신한한", "우리리"};

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        List<Integer> readyMemberList = new ArrayList<>();

        while (true) {
            // 증액 머니, 감액 머니
            int amount = random.nextInt(20001); // 1 ~ 20,000
            int targetMembershipId = random.nextInt(10001) + 1; // 1 ~ 10,000

            registerAccountSimulator(REGISTER_ACCOUNT_API_ENDPOINT, targetMembershipId);
            createMemberMoneySimulator(CREATE_MONEY_API_ENDPOINT, targetMembershipId);

            // eda 방식 모두 동작 후 DB에 save까지 1초 sleep
            Thread.sleep(1000);

            // 준비된 고객에 대해서만 증액/감액 요청하기 위해
            readyMemberList.add(targetMembershipId);

            // 증액
            increaseMemberMoneySimulator(INCREASE_API_ENDPOINT, amount, targetMembershipId);
            amount = random.nextInt(20001) - 10000; // 1 ~ 20,000

            // 감액
            Integer decreaseTargetMembershipId = readyMemberList.get(random.nextInt(readyMemberList.size()));
            increaseMemberMoneySimulator(DECREASE_API_ENDPOINT, amount, decreaseTargetMembershipId);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void increaseMemberMoneySimulator(String apiEndpoint, int amount, int targetMembershipId) {
        try {
            URL url = new URL(apiEndpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject jsonRequestBody = new JSONObject();
            jsonRequestBody.put("amount", amount);
            jsonRequestBody.put("targetMembershipId", targetMembershipId);

            call(apiEndpoint, conn, jsonRequestBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void registerAccountSimulator(String apiEndpoint, int targetMembershipId) {
        try {
            URL url = new URL(apiEndpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            Random random = new Random();

            JSONObject jsonRequestBody = new JSONObject();
            jsonRequestBody.put("bankAccountNumber", generateRandomAccountNumber());
            jsonRequestBody.put("bankName", BANK_NAME[random.nextInt(BANK_NAME.length)]);
            jsonRequestBody.put("membershipId", targetMembershipId);
            jsonRequestBody.put("valid", true);

            call(apiEndpoint, conn, jsonRequestBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void createMemberMoneySimulator(String apiEndpoint, int targetMembershipId) {
        try {
            URL url = new URL(apiEndpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject jsonRequestBody = new JSONObject();
            jsonRequestBody.put("targetMembershipId", String.valueOf(targetMembershipId));
            call(apiEndpoint, conn, jsonRequestBody);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private static void call(String apiEndpoint, HttpURLConnection conn, JSONObject jsonRequestBody) throws IOException {
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(jsonRequestBody.toString().getBytes());
        outputStream.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        System.out.println("API Response from " + apiEndpoint + ": " + response.toString());
    }

    // 랜덤 계좌 번호 생성. 10자리
    private static String generateRandomAccountNumber() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int digit = random.nextInt(10); // 0 ~ 9
            sb.append(digit);
        }

        return sb.toString();
    }
}
