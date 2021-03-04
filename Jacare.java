import java.util.List;
import java.util.Iterator;
import java.util.Random;


//Um modelo simples de um jacaré.  Os jacarés envelhecem, se movem, comem coelhos, raposas e morrem.
public class Jacare extends Animal {
    // Características compartilhadas por todos os jacarés (campos estáticos).
    
    // A idade em que um jacaré pode começar a procriar.
    private static final int BREEDING_AGE = 20;
    // A idade até a qual um jacaré pode viver.
    private static final int MAX_AGE = 200;
    // A probabilidade de uma criação de raposas.
    private static final double BREEDING_PROBABILITY = 0.03;
    // O número máximo de nascimentos.
    private static final int MAX_LITTER_SIZE = 20;
    // O valor alimentar de um único coelho. Na verdade, este é o
    // número de passos que um jacaré pode dar antes de ter que comer novamente.
    private static final int RABBIT_FOOD_VALUE = 2;
    // O valor alimentar de uma única raposa. Na verdade, este é o
    // número de passos que um jacaré pode dar antes de ter que comer novamente.
    private static final int FOX_FOOD_VALUE = 80;
    // Um gerador de números aleatórios compartilhado para controlar a reprodução.
    private static final Random rand = new Random();
    
    // Características individuais (campos de instância).
    // O nível de comida do jacare, que é aumentado comendo coelhos e raposas.
    private int foodLevel;
  
    //Cria um jacaré. Um jacaré pode ser criada como um recém-nascido (idade zero e sem fome) ou com idade aleatória.
    //@param randomAge Se verdadeiro, o jacaré terá idade e nível de fome aleatórios.
     
    public Jacare(boolean randomAge) {
        super();
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            foodLevel = rand.nextInt(FOX_FOOD_VALUE);
        }
        else {
            // deixar a idade em 0
            foodLevel = FOX_FOOD_VALUE;
        }
    }

    @Override
    public void act (Field currentField, Field updatedField, List newJacares) {
        hunt(currentField, updatedField, newJacares);
    }

    @Override
    public int getBreedingAge() {
        return BREEDING_AGE;
    }

    @Override
    public int getMaxAge () {
        return MAX_AGE;
    }

    @Override
    public int getMaxLitterSize () {
        return MAX_LITTER_SIZE;
    }

    @Override
    public double getBreedingProbability () {
        return BREEDING_PROBABILITY;
    }
    
    /*
    * Isso é o que o jacaré faz na maioria das vezes: ela caça
    * coelhos e raposas. No processo, ele pode se reproduzir, morrer de fome,
    * ou morrer de velhice.
    */
    private void hunt(Field currentField, Field updatedField, List newJacares) {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            // Novos jacares nascem em locais adjacentes.
            int births = breed();
            for(int b = 0; b < births; b++) {
                Jacare newJacare = new Jacare(false);
                newJacares.add(newJacare);
                Location loc = updatedField.randomAdjacentLocation(location);
                newJacare.setLocation(loc);
                updatedField.place(newJacare, loc);
            }
            // Mova-se em direção à fonte de alimento se encontrada
            Location newLocation = findFood(currentField, location);
            if(newLocation == null) {  // nenhum alimento encontrado - mover aleatoriamente
                newLocation = updatedField.freeAdjacentLocation(location);
            }
            if(newLocation != null) {
                setLocation(newLocation);
                updatedField.place(this, newLocation);
            }
            else {
                // não pode se mover nem ficar - superlotação - todos os locais tomados
                alive = false;
            }
        }
    }
    
    // Deixe este jacaré com mais fome. Isso pode resultar na morte do jacaré.
    private void incrementHunger() {
        foodLevel--;
        if(foodLevel <= 0) {
            alive = false;
        }
    }
    
    /**
      * Fala pro Jacare procurar coelhos ou raposas adjacentes à sua localização atual.
      * @param field O campo no qual ele deve olhar.
      * @param location Onde no campo ele está localizado.
      * @return Onde a comida foi encontrada, ou null se não for.
      */

    private Location findFood(Field field, Location location) {
        Iterator adjacentLocations = field.adjacentLocations(location);
        while(adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAlive()) { 
                    rabbit.setEaten();
                    foodLevel = RABBIT_FOOD_VALUE;
                    return where;
                }
            } else if(animal instanceof Fox){
                Fox fox = (Fox) animal;
                if(fox.alive){
                    fox.alive = false;
                    foodLevel = FOX_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }
    
}
