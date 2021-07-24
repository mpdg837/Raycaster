package Raycaster.Display.Raycaster;

import java.util.ArrayList;
import java.util.Collection;

public class SpriteQueue {


    private final ArrayList<Column> lista = new ArrayList<>();

    public void add(Column newItem){
        lista.add(newItem);
    }

    public Column get(int index){
        return lista.get( index);
    }

    public int getSize(){
        return lista.size();
    }
    public void clear(){ lista.clear(); }
}
