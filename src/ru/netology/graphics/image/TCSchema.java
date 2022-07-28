package ru.netology.graphics.image;

public class TCSchema implements TextColorSchema {
    private int[] symbols = {
            (int) '#',
            (int) '$',
            (int) '@',
            (int) '%',
            (int) '*',
            (int) '+',
            (int) '-',
            (int) ' '
    };

    @Override
    public char convert(int color) {
        return (char) symbols[color / 32];
    }
}
