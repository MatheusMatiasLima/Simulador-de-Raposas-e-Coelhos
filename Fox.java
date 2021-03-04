import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * Modelo simples de uma raposa.
 * Raposas nascem, movem, comem coelhos, e morrem.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-11
 */
public class Fox extends Animal {
    // Caracteristicas compartilhadas por todas as raposas (campos estaticos).
    
    // A idade que a raposa comeca a procriar.
    private static final int BREEDING_AGE = 10;
    // A idade que a raposa pode viver.
    private static final int MAX_AGE = 150;
    // A probabilidade da raposa procriar.
    private static final double BREEDING_PROBABILITY = 0.1;
    // Numero maximo de nascimento em uma gestacao.
    private static final int MAX_LITTER_SIZE = 5;
    // Valor de comida de um coelho. Em efeito,
    //quantidade de passos que a raposa pode fazer sem se alimentar.
    private static final int RABBIT_FOOD_VALUE = 4;
    // Numero aleatorio compartilhado que controla os nascimentos.
    private static final Random rand = new Random();
    
    // Caracteristicas individuais (campos de instancia).

    // Nivel de fome da raposa que aumenta ao comer coelhos.
    private int foodLevel;

    /**
     * Cria uma raposa. A raposa pode ser criada como um novo nascimento (idade zero
     * e sem fome) ou com uma idade aleatoria.
     * 
     * @param randomAge se true, a raposa ira ter uma idade e nivel de fome aleatorio.
     */
    public Fox(boolean randomAge) {
        super();
        if(randomAge) {
            setAge(rand.nextInt(MAX_AGE));
            foodLevel = rand.nextInt(RABBIT_FOOD_VALUE);
        }
        else {
            // deixe idade 0
            foodLevel = RABBIT_FOOD_VALUE;
        }
    }

    @Override
    public void act (Field currentField, Field updatedField, List newFoxes) {
        hunt(currentField, updatedField, newFoxes);
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
    
    /**
     * Isto e o que a raposa faz na maior parte do tempo: caca coelhos.
     * Alem disso, pode gerar filhos, morrer de fome ou de velhice. 
     */
    private void hunt(Field currentField, Field updatedField, List newFoxes) {
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            // Novas raposas nascem em posicoes adjacentes.
            int births = breed();
            for(int b = 0; b < births; b++) {
                Fox newFox = new Fox(false);
                newFoxes.add(newFox);
                Location loc = updatedField.randomAdjacentLocation(location);
                newFox.setLocation(loc);
                updatedField.place(newFox, loc);
            }
            // Move em direcao a comida se encontrada.
            Location newLocation = findFood(currentField, location);
            if(newLocation == null) {  // nao encontrou comida - meve aleatoriamente
                newLocation = updatedField.freeAdjacentLocation(location);
            }
            if(newLocation != null) {
                setLocation(newLocation);
                updatedField.place(this, newLocation);
            }
            else {
                // pode mover ou ficar - superlotacao - todas localizacoes ocupadas
                alive = false;
            }
        }
    }
    
    /**
     * Faz a raposa ficar com fome, pode resultar em morte
     */
    private void incrementHunger() {
        foodLevel--;
        if(foodLevel <= 0) {
            alive = false;
        }
    }
    
    /**
     * Diz a raposa para procurar coelhos em posicoes adjacentes.
     * @param field O campo que deve ser olhado.
     * @param location Onde o campo esta localizado.
     * @return Onde a comida foi encontrada, ou null se nao foi.
     */
    private Location findFood(Field field, Location location) {
        Iterator adjacentLocations = field.adjacentLocations(location);
        while(adjacentLocations.hasNext()) {
            Location where = (Location) adjacentLocations.next();
            Object animal = field.getObjectAt(where);
            if(animal instanceof Rabbit) {
                Rabbit rabbit = (Rabbit) animal;
                if(rabbit.isAtive()) { 
                    rabbit.setDead();
                    foodLevel = RABBIT_FOOD_VALUE;
                    return where;
                }
            }
        }
        return null;
    }
    
}
