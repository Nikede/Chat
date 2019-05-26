package com.nikede.chat.Crypt;

public class Key {
    static private int key;

    public static int getKey() {
        return key;
    }

    public static void setKey(int key) {
        Key.key = key;
    }
}
