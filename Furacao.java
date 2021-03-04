import java.util.List;
import java.util.Iterator;

public class Furacao extends ProblemasAmbientais {

    @Override
    public void act(Field currentField, Field updatedField, List newActors) {
        procurarOndeIr(currentField, updatedField);
    }

    @Override
    public boolean isAtive() {
        return true;
    }

    //Procura algum lugar para ir. Ocupa o local do animal que matou.
    private void procurarOndeIr(Field currentField, Field updatedField) {
        // Move em direcao ao local para destruir
        Location newLocation = destruir(currentField, location);
        if(newLocation == null) {  // nao encontrou nada para destruir
            newLocation = updatedField.freeAdjacentLocation(location);
        }
        if(newLocation != null) {
            setLocation(newLocation);
            updatedField.place(this, newLocation);
        }
    }

    // destroi o que esta em sua volta tomando o seu lugar 
    private Location destruir(Field field, Location location) {
        Iterator adjacentLocations = field.adjacentLocations(location);
        while(adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Object obj = field.getObjectAt(where);
            if(obj instanceof Animal) {
                Animal animal = (Animal) obj;
                if(animal.isAtive()) { 
                    animal.setDead();
                    return where;
                }
            }
        }
        return null;
    }
}
