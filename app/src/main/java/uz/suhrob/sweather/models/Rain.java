package uz.suhrob.sweather.models;

/**
 * Created by User on 28.01.2020.
 */

public class Rain {

    @SuppressWarnings("3h")
    private int _3h;

    public Rain() {
    }

    public Rain(int h) {
        _3h = h;
    }

    public int get_3h() {
        return _3h;
    }

    public void set_3h(int _3h) {
        this._3h = _3h;
    }
}
