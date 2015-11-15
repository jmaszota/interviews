package amazon;

/**
 * Created by jacek.maszota on 29.09.2015.
 */
public class Task2 {
    public static void main(String[] args) {

        int[] data = new int[]{1,3,-3};
       // data = new int[]{-8,4,0,5,-3,6};
        System.out.println(solution(data));

    }

    public static int solution(int[] A){

        int maxSum = 0;

        for(int q = A.length -1; q >= 0; q--){

            for(int p = q; p >=0; p--){

                int sum = A[p] + A[q] + (q - p);

                maxSum = sum >= maxSum ? sum : maxSum;


                System.out.println("("+p+","+q+")" + sum);
            }


        }

        return maxSum;

    }
}
