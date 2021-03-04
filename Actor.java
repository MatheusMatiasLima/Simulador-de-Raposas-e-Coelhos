import java.util.List;

public interface Actor {
    // ação do ator
    abstract public void act (Field currentField, Field updatedField, List newActors);
    // se ele exite
    abstract public boolean isAtive();
}
