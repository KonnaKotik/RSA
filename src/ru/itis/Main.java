package ru.itis;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        //flag нужен для вида теста/ключа true - шестнадцатеричном; false - символьном
        System.out.println("Введите true - если шестнадцатеричный вид, и false - если символьный");
        Scanner scanner = new Scanner(System.in);
        boolean flag = scanner.nextBoolean();

        BufferedReader reader = new BufferedReader(new FileReader("text.txt"));
        String text = reader.readLine();
        RSA rsa = new RSA();
        if(!flag) {
            String key = getRandomKey(50, false);
            System.out.println("Сгенерированный ключ: " + key );
            Lab02 lab02 = new Lab02(text, key);
            String rcaCoderText = rsa.rsaCoder(key, false);
            rsa.rsaDecoder(rcaCoderText, false);
        } else {
            String key = getRandomKey(50, true);
            System.out.println("Сгенерированный ключ: " + key );
            String rcaCoderText = rsa.rsaCoder(key, true);
            rsa.rsaDecoder(rcaCoderText, true);
        }
    }

    private static String getRandomKey(int length, boolean flag) {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {

            builder.append(random.nextInt(10));
        }
        if (flag) {
            return new BigInteger(builder.toString()).toString(16);
        }
        return builder.toString();
    }
}
