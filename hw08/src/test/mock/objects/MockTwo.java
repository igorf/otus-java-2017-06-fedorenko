package mock.objects;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class MockTwo {
    protected List<String> strings = new ArrayList<>();
    protected int a = 5;
    protected Object b = null;
}
