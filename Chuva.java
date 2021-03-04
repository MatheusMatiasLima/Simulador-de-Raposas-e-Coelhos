import java.util.List;
import java.util.Iterator;

public class Chuva extends ProblemasAmbientais {

    @Override
    public void act(Field currentField, Field updatedField, List newActors) {
        procurarOndeIr(currentField, updatedField);
    }

    @Override
    public boolean isAtive() {
        return true;
    }

    private void procurarOndeIr(Field currentField, Field updatedField) {
        // Move em direcao ao local para destruir
        destruir(currentField, location);
        Location newLocation = null;
        if(newLocation == null) {  // move para um lugar vazio
            newLocation = updatedField.freeAdjacentLocation(location);
        }
        if(newLocation != null) { // nao encontrou nenhum lugar aparece em qualquer lugar
            setLocation(newLocation);
            updatedField.place(this, newLocation);
        }
    }

    private void destruir(Field field, Location location) {
        Iterator adjacentLocations = field.adjacentLocations(location);
        while(adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.deixarDoente();
                }
            }
            else if (animal instanceof Fox){
                Fox fox = (Fox) animal;
                if(fox.alive){
                    fox.deixarDoente();
                }
            }
            else if (animal instanceof Jacare) {
                Jacare jacare = (Jacare) animal;
                if (jacare.isAtive()) {
                    jacare.deixarDoente();
                }
            }
        }
    }
}
