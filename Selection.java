import java.util.Random;

public class Selection{

    public static void swap(int[] a, int i, int j) { //배열의 원소를 서로 바꿔주는 기능
		int temp = a[i];
		a[i] = a[j]; a[j] = temp;}


    public static int Divide(int[] A, int left, int right){ 
        int piv = (int) (Random()*(right-left))+left; //피봇을 랜덤으로 설정
        swap(A,left,piv); //피봇을 맨 왼쪽으로 옮김 (A[left] 값: 피봇)
        int p = left + 1; //피봇보다 작은 원소들이 피봇(A[left]) 옆으로 와야하기 때문에 
        int q = right; //피봇보다 큰 원소들은 맨오른쪽으로만 넣으면 자동으로 그룹이 나뉘어짐

        while (true){ //break 전까지 수행한다
            while ( p < right && A[p] < A[left]){ //피봇이 A[p] 값보다 크면 p는 +1 이동하며 right 값이 되기 전까지 계속 비교한다
                p += 1;
            }
            while ( q >left && A[q] > A[left]){ //피봇이 A[q] 값보다 크면 q는 -1씩 이동하며 left 값이 되기 전까지 계속 비교한다
                q -= 1;
            }
            if(p >= q){ //p가 계속 커지며(또는 q가 계속 작아지며) p 값이 q 값보다 크거나 같아지면 중단한다(=비교할 대상 없음)
                break;
            }
            swap(A, p, q); //배열 A에서 피봇보다 큰 값 p를 q와 자리를 바꾼다
            p += 1; q -= 1; //자리를 바꾼 뒤에도 계속 비교하기 위해 이동한다
        }
        swap(A, left, p); //가장 왼쪽에 있었던 피봇을 A[p]로 옮긴다
        return p; //A[p]번째 자리에 있는 값이 곳 피봇이다
    }
    public static int Selection(int[] A, int left, int right, int k){
        int pivot = Divide(A, left, right);
        int S = pivot - left;
        if(k < S){ //Small Group S 안에 K 번째 작은 숫자가 있음 (재귀)
            Selection(A, left, pivot -1, k);
        }
        else if (k == S){ //피봇이 K번째 작은 숫자다 -> 마지막까지 계산되어야 할 부분
            return A[pivot];
        }
        else{ //Small Group S보다 큰 그룹에서 k번째 작은 숫자 찾아야함 (재귀)
            Selection(A,pivot +1, right, k-pivot-1);
        }
    }
}