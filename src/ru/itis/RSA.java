package ru.itis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;

public class RSA {
    private BigInteger d;
    private BigInteger e;
    private BigInteger n;


    public RSA() throws IOException {
     //Блок генерации пары ключей

        //генерация чисел p и q
        SecureRandom secureRandom = new SecureRandom();
        BigInteger p = BigInteger.probablePrime(128, secureRandom);
        BigInteger q = BigInteger.probablePrime(128, secureRandom);

        //вычисление модуля
        BigInteger n = p.multiply(q);

        //вычисление функции Эйлера
        BigInteger efun = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));

        //проверка: явялется ли e взаимно простым с eFun
        BigInteger e = BigInteger.valueOf(2);
        while (efun.gcd(e).intValue() > 1) {
            e = e.add(BigInteger.ONE);
        }

        //вычисляем d алгоритмом Евклида
        BigInteger d = e.modInverse(efun);

        writeInFile(d, e, n);

        this.d = d;
        this.e = e;
        this.n = n;

    }

    //запись ключей в файл
    private void writeInFile(BigInteger d, BigInteger e, BigInteger n) throws IOException {
        FileWriter fileWriterOpen = new FileWriter(new File("openKey.txt"));
        FileWriter fileWriterClose = new FileWriter(new File("closeKey.txt"));

        fileWriterOpen.write(e + " " + n);
        fileWriterClose.write(d + " " + n);

        fileWriterClose.flush();
        fileWriterClose.close();

        fileWriterOpen.flush();
        fileWriterOpen.close();
    }

    // шифрование
    public String rsaCoder(String text, boolean flag) {
        BigInteger coderText;
        if (flag) {
            coderText = new BigInteger(text, 16);
        } else {
            coderText = new BigInteger(text);
        }
        BigInteger encrypted = coderText.modPow(e, n);
        try (FileWriter writer = new FileWriter("rsaCoder.txt")) {
            String messageEncrypt;
            if (flag) {
                messageEncrypt = encrypted.toString(16);
            } else
                messageEncrypt = encrypted.toString();
            writer.write(messageEncrypt);
            writer.flush();
            System.out.println(messageEncrypt);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        if (flag) {
            return encrypted.toString(16);
        } else return encrypted.toString();
    }

    public void rsaDecoder(String text, boolean flag) {

        BigInteger decoderText;
        if (flag) {
            decoderText = new BigInteger(text, 16);
        } else {
            decoderText = new BigInteger(text);
        }
        BigInteger decrypted = decoderText.modPow(d, n);
        if (flag) {
            System.out.println(decrypted.toString(16));
        } else {
            System.out.println(decrypted);
        }
    }



}
