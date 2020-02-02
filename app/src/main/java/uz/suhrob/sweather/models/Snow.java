package uz.suhrob.sweather.models;

/**
 * Created by User on 28.01.2020.
 */

public class Snow {

    @SuppressWarnings("3h")
    private int _3h;

    public Snow(int h) {
        _3h = h;
    }

    public Snow() {
    }

    public int get_3h() {
        return _3h;
    }

    public void set_3h(int _3h) {
        this._3h = _3h;
    }
}
