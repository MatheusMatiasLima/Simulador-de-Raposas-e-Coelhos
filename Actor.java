import java.util.List;

public interface Actor {
    abstract public void act (Field currentField, Field updatedField, List newFoxess);
    abstract public boolean isAtive();
}
