/**
 * Representa a localização em um grid retangular.
 * 
 * @author David J. Barnes and Michael Kolling
 * @version 2002-04-09
 */
public class Location
{
    // Posicoes de linhas e colunas.
    private int row;
    private int col;

    /**
     * Representa uma linha e uma coluna.
     * @param row A linha.
     * @param col A coluna.
     */
    public Location(int row, int col)
    {
        this.row = row;
        this.col = col;
    }
    
    /**
     * Implementa igualdade de conteudo.
     */
    public boolean equals(Object obj)
    {
        if(obj instanceof Location) {
            Location other = (Location) obj;
            return row == other.getRow() && col == other.getCol();
        }
        else {
            return false;
        }
    }
    
    /**
     * Retorna uma string da forma linha, coluna
     * @return Representacao da localizacao em forma de string.
     */
    public String toString()
    {
        return row + "," + col;
    }
    
    /**
     * Use os 16 bits do topo para valores de linha e os da base para
     * a coluna. Exceto para grids muito grandes, isto deve dar um
     * codigo hash unico para cada par de (linha, coluna)
     */
    public int hashCode()
    {
        return (row << 16) + col;
    }
    
    /**
     * @return A linha
     */
    public int getRow()
    {
        return row;
    }
    
    /**
     * @return A coluna.
     */
    public int getCol()
    {
        return col;
    }
}
