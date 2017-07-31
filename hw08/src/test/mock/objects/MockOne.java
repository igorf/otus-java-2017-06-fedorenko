package mock.objects;

import lombok.Data;

@Data
public class MockOne {
    public int x = 0;
    protected float y = 1.5f;
    private String z = "hello, world";
    protected MockTwo[] inner = {};
    protected int[] numbers = {1, 2, 4, 6};
}
