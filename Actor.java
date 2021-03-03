import java.util.List;

public abstract class Actor {
    abstract public void act (Field currentField, Field updatedField, List newFoxess);
    abstract public boolean isAtive();
}
