package amazon;

import binary.search.tree.Tree;

/**
 * Created by jacek.maszota on 06.10.2015.
 */
public class Task3 {
    public static void main(String[] args) {

    }
    public static int solution(Tree tree){

        int min = tree.getValue();
        int max = tree.getValue();

        int amp = max - min;

        if(tree.getLeft()!=null){
            int tempAmp = 0;
            if(tree.getLeft().getValue()>max) {
                tempAmp = tree.getLeft().getValue()-min > tempAmp ? tree.getLeft().getValue()-min  : tempAmp;
            }
            if(tree.getLeft().getValue()<min) {
                tempAmp = max -tree.getLeft().getValue() > tempAmp ? max -tree.getLeft().getValue() : tempAmp;
            }

            if(tempAmp > amp) return tempAmp;
            else solution(tree.getLeft());
        }
        else if(tree.getLeft().getValue()< min){

        }else{
            //do nothing
        }

        if(tree.getRight().getValue()> max){

        }
        else if(tree.getRight().getValue()< min){

        }else{
            //do nothing
        }

        return amp;
    }
}
