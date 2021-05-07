package com.company;

public class Son {
    private Tree name;
    private boolean adopted;

    public Son(Tree name,boolean adopted){
        this.name=name;
        this.adopted=adopted;
    }

    public Tree getSon(){
        return name;
    }

    public boolean isAdopted(){
        return adopted;
    }

    public void setName(Tree name) {
        this.name = name;
    }

    public void setAdopted(boolean adopted) {
        this.adopted = adopted;
    }
}
