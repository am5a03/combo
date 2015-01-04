package dnomyar.combo.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Raymond on 2015-01-04.
 */
public enum ComboColor {
    RED, BLUE, YELLOW, GREEN;
    private static final List<ComboColor> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static ComboColor randomColor()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
