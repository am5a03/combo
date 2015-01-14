package dnomyar.combo.models;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Raymond on 2015-01-04.
 */
public enum ComboColor {
    RED, BLUE, YELLOW, GREEN, ORANGE, INDIGO, PURPLE;
    private static final List<ComboColor> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static ComboColor randomColor()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }

    @Override
    public String toString() {
        String name = this.name();
        name = name.toLowerCase();
        char[] _name = name.toCharArray();
        _name[0] = Character.toUpperCase(_name[0]);
        return new String(_name);
    }
}
