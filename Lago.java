import java.util.List;
import java.util.Iterator;
public class Lago extends Cenario {
    @Override
    public void act(Field current, Field updatedField, List newActors){
        // Nao faz nada, apenas se mantem no mesmo lugar
        updatedField.place(this,location);
    }
    @Override
    public boolean isAtive(){
        return true;
    }
}