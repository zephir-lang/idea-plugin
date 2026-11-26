namespace Test;

class PropertyShortcuts
{
    protected a { get };
    protected b { set };
    protected c { toString };
    protected d { __toString };
    protected e { get, set };
    protected f {};
    protected g = 1 { get, set };
    protected h {
        /** Reads the value */
        get,
        /** Writes the value */
        set
    };
    protected i { unknown };
    protected j { get, whatever, set };
}
