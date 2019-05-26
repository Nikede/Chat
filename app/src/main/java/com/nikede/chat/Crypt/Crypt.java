package com.nikede.chat.Crypt;

import android.content.Context;

public class Crypt {

    public static String encode(Context context, String message, int b) {
        char[] crypted = message.toCharArray();
        String cryptedMessage = "";
        int key = Key.getKey();
        int combinedKey = (int) (Math.pow(b, key) % 23);
        for (int i = 0; i < crypted.length; i++) {
            crypted[i] += combinedKey;
            cryptedMessage += crypted[i] + "AK1999";
        }
        return cryptedMessage;
    }


    public static String decode(Context context, String message, int b) {
        String[] symbols = message.split("AK1999");
        char[] chars = new char[symbols.length];
        int key = Key.getKey();
        int combinedKey = (int) (Math.pow(b, key) % 23);
        for (int i = 0; i < symbols.length; i++) {
            chars[i] = (char) (symbols[i].charAt(0) - combinedKey);
        }
        String decryptedMessage = "";
        for (char B : chars) {
            decryptedMessage += B;
        }
        return decryptedMessage;
    }
}
