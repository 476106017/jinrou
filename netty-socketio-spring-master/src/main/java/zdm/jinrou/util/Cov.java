package zdm.jinrou.util;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Cov {
    public static int NI(Object o) {
        return NI(o, 0);
    }

    public static int NI(Object o, int df) {
        if (o == null) {
            return df;
        }
        if (o instanceof Integer) {
            return (Integer) o;
        }
        if (o instanceof Byte) {
            return (Byte) o;
        }
        if (o instanceof Short) {
            return (Short) o;
        }
        if (o instanceof BigInteger) {
            return ((BigInteger) o).intValue();
        }
        if(o instanceof Double){
            return ((Double) o).intValue();
        }
        if (o instanceof BigDecimal) {
            return ((BigDecimal) o).intValue();
        }
        try {
            return Integer.parseInt(o.toString());
        } catch (Exception e) {
            return df;
        }
    }
}
