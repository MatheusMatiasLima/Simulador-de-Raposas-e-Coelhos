import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Representa um grid retangulas das posicoes do campo.
 * Cada posicao guarda somente um unico animal.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-09
 */
public class Field
{
    private static final Random rand = new Random();
    
    // A profundidade e largura do campo.
    private int depth, width;
    // Armazenamento para os animais em uma matriz.
    private Object[][] field;

    /**
     * Representa um campo dado suas dimensoes.
     * @param depth A profundidade do campo.
     * @param width A largura do campo.
     */
    public Field(int depth, int width)
    {
        this.depth = depth;
        this.width = width;
        field = new Object[depth][width];
    }
    
    /**
     * Esvazia o campo.
     */
    public void clear()
    {
        for(int row = 0; row < depth; row++) {
            for(int col = 0; col < width; col++) {
                field[row][col] = null;
            }
        }
    }
    
    /**
     * Coloca um animal em uma localizacao dada.
     * Se ja existe um animal na localizacao, sera perdida.
     * @param animal Animal a ser colocado.
     * @param row Cordenada de linha da localizacao.
     * @param col Cordenada da coluna da localizacao.
     */
    public void place(Object animal, int row, int col)
    {
        place(animal, new Location(row, col));
    }
    
    /**
     * Coloca um animal em uma posicao dada.
     * Se ja existe uma animal na localizacao, sera perdida.
     * @param animal O animal a ser colocado.
     * @param location Onde o animal sera colocado.
     */
    public void place(Object animal, Location location)
    {
        field[location.getRow()][location.getCol()] = animal;
    }
    
    /**
     * Retorna o animal na posicao dada, se algum.
     * @param location Onde no campo.
     * @return O animal na posicao dada, ou nenhum se nao tiver.
     */
    public Object getObjectAt(Location location)
    {
        return getObjectAt(location.getRow(), location.getCol());
    }
    
    /**
     * Retorna o animal para a posicao dada, se algum.
     * @param row A linha desejada.
     * @param col A coluna desejada.
     * @return O animal na posicao dada ou null, se nao tiver nenhum.
     */
    public Object getObjectAt(int row, int col)
    {
        return field[row][col];
    }
    
    /**
     * Gera uma localizacao aleatoria adjacente a localizacao dada, ou e a mesma localizcao.
     * A localizacao retornado vai ser com bordas validas do campo.
     * @param location A localizacao adjacente gerada.
     * @return Uma localizacao valida da area do grid. Isto
     *         pode ser o mesmo objeto como parametro de localizacao.
     */
    public Location randomAdjacentLocation(Location location)
    {
        int row = location.getRow();
        int col = location.getCol();
        // Gera um offset de -1, 0, ou +1 para ambas linhas e colunas
        int nextRow = row + rand.nextInt(3) - 1;
        int nextCol = col + rand.nextInt(3) - 1;
        // Checa caso a nova localizacao esteja fora da borda.
        if(nextRow < 0 || nextRow >= depth || nextCol < 0 || nextCol >= width) {
            return location;
        }
        else if(nextRow != row || nextCol != col) {
            return new Location(nextRow, nextCol);
        }
        else {
            return location;
        }
    }
    
    /**
     * Tenta procurar uma localizacao que e adjacente a localizacao dada.
     * Se nao tiver nenhuma, entao retorna a localizacao atual se estiver livre.
     * Se nao, retorna null.
     * A localizacao retornada sera com bordas validas do campo.
     * @param location Localizacao a qual gerou a adjacencia.
     * @param andaSobreAgua Se true, indica que um campo do tipo lago sera considerado como posicao livre (Ator anda sobre agua)
     * @return Uma localizacao valida da area do grid. Isto pode estar em mesmo objeto
     * como o parametro da localizacao, ou nulo se todas localizacoes ao redor estiverem cheia.
     */
    public Location freeAdjacentLocation(Location location, boolean andaSobreAgua)
    {
        Iterator adjacent = adjacentLocations(location);
        while(adjacent.hasNext()) {
            Location next = (Location) adjacent.next();
            if(field[next.getRow()][next.getCol()] == null) {
                return next;
            } else if(andaSobreAgua){ // Se a posicao não estiver nula, mas for lago e um ator que anda pelo lago
                if(field[next.getRow()][next.getCol()] instanceof Lago){
                    return next;
                }
            }
        }
        // checa se a localizacao atual esta livre
        if(field[location.getRow()][location.getCol()] == null) {
            return location;
        } 
        else {
            return null;
        }
    }

    /**
     * Gera uma interacao sobre a lista de localizacoes adjacente para uma dada.
     * A lista nao inclui sua propria localizacao.
     * @param location Localizaca ao qual gerou adjacencias.
     * @return Iterador sobre as localizacoes adjacentes dadas.
     */
    public Iterator adjacentLocations(Location location)
    {
        int row = location.getRow();
        int col = location.getCol();
        LinkedList locations = new LinkedList();
        for(int roffset = -1; roffset <= 1; roffset++) {
            int nextRow = row + roffset;
            if(nextRow >= 0 && nextRow < depth) {
                for(int coffset = -1; coffset <= 1; coffset++) {
                    int nextCol = col + coffset;
                    // Exclui localizacoes invalidas e localizacao original
                    if(nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                        locations.add(new Location(nextRow, nextCol));
                    }
                }
            }
        }
        Collections.shuffle(locations,rand);
        return locations.iterator();
    }

    /**
     * @return A profundidade do campo.
     */
    public int getDepth()
    {
        return depth;
    }
    
    /**
     * @return A largura do campo.
     */
    public int getWidth()
    {
        return width;
    }
}
