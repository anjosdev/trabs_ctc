import java.util.Set;

public class Coordenada {
    //set e get guardar x e y, retornar x e y
    private char conteudo;

    private int x; // linha
    private int y; // coluna
    
    public Coordenada(char conteudo, int x, int y){
        // verificar se é diferente de E, S, #, " " -> Se diferente Throw Exception
        this.conteudo = conteudo;
        this.x = x;
        this.y = y;
    }

    public char getConteudo() { return this.conteudo; };

    public int getX(){ return this.x; }

    public void setX(int x){ this.x = x; }

    public int getY(){ return this.y; }

    public void setY(int y){ this.y = y; }

    @Override
    public String toString() {
        return "("+this.x+","+this.y+")";
    }
}

