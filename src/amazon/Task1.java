package amazon;

/**
 * Created by jacek.maszota on 29.09.2015.
 */
public class Task1 {
    public static void main(String[] args) {
        int[][] A = new int[7][3];
        A[0][0] = 5 ;   A[0][1] = 4   ; A[0][2] = 4;
        A[1][0] = 4 ;   A[1][1] = 3  ;  A[1][2] = 4;
        A[2][0] = 3  ;  A[2][1] = 2   ; A[2][2] = 4;
        A[3][0] = 2  ;  A[3][1] = 2   ; A[3][2] = 2;
        A[4][0] = 3   ; A[4][1] = 3   ; A[4][2] = 4;
        A[5][0] = 1   ; A[5][1] = 4   ; A[5][2] = 4;
        A[6][0] = 4   ; A[6][1] = 1   ; A[6][2] = 1;

        System.out.println(solution(A));

    }

    public static int solution(int[][] A){

        int country = 0;

        for(int i = 0; i < A.length; i++){
            for (int j = 0; j < A[i].length; j++){
              // System.out.println(i + " " + j);
                if((i > 0 && A[i-1][j] == A[i][j]) || (j > 0 && A[i][j-1]== A[i][j])){
                    continue;
                }
                else if(j<A[i].length-1 && i > 0){
                    if((A[i][j+1] == A[i][j]) && (A[i-1][j+1] == A[i][j])){
                            continue;
                        }
                }
                country++;
            }
        }

        return country;
    }
}
