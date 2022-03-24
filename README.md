# 분할정복 알고리즘 

## 선택 문제를 위한 분할 정복 알고리즘

#### ▶ 선택문제(Selection) 개념

선택(Selection) 문제는 n개의 숫자들 중에서 k번째로 작은 숫자를 찾는 문제다.
이 문제를 해결하기 위한 간단한 방법은 아래와 같다.

1. 최소 숫자를 k번 찾는다. 최소 숫자를 찾은 뒤에는 그 숫자를 제거한다.
   → 시간복잡도는 O(kn), k번째 숫자를 찾을 때까지 반복하는 과정마다 항상 n, n-1, n-2....(최소 숫자는 제거되므로)개의 숫자들을 탐색한다.
2. 숫자들을 오름차순 정렬한 후, k번째 숫자를 찾는다.
   → 시간복잡도는 O(nlogn), 즉 정렬하는 과정에서 걸리는 시간이 곧 시간복잡도가 된다. (k번째 숫자를 찾는 것은 상수시간)

#### ▶ 더 효율적인 해결을 위한 분할정복 아이디어

선택 문제는 입력이 정렬되어 있지 않으므로 입력 숫자들 중에서 피봇을 선택하여 
피봇보다 작은 숫자의 그룹 (S)와 피봇보다 큰 숫자의 그룹(L)로 나눈다. 
각 그룹의 크기를 알면 k번째 작은 숫자가 어느 그룹에 있는지 알 수 있고, 
그 다음 그룹에서 몇번째로 작은 숫자를 찾아야하는지도 알 수 있게 된다.

- 만약 k번째 작은 숫자가 그룹 S에 있는 경우 그룹 S에서 찾으면 된다
- 그룹 L에 작은 숫자가 있는 경우 k번째는 (k-|그룹S의 숫자 개수|-1)번째로 작은 숫자를 그룹 L에서 찾으면 된다.

> 선택 문제 알고리즘은 그룹 S와 L처럼 두 개의 부분문제로 분할되지만 k번째 숫자는 둘 중 한 그룹에만 있으므로 나머지 한개의 부분문제는 고려할 필요가 없다.

| 부분문제의 크기가 일정하지 않은 크기로 감소하는 형태의 알고리즘이다

#### ▶ 선택문제의 psudo 코드 (교재 참고)

```
Selection(A, left, right, k)
입력: A[left] ~ A[right] 와 k, (단,  1<= k <= |A|, |A| = right - left + 1)
출력: A[left] ~ A[right]에서 k번재 작은 원소

1. 피봇을 A[left] ~ A[right]에서 랜덤하게 선택하고, 피봇과 A[left]의 자리를 바꾼 후, (첫 번째가 아닌 값을 피봇으로 정하면, 그 피봇이 원래 있던 위치를 지나갈 수 있으므로 맨 앞으로 빼놓는 것.) 피봇과 배열의 각 원소를 비교하여 피봇보다 작은 숫자는 A[left] ~ A[p - 1]로 옮기고, 피봇보다 큰 숫자는 A[p+1] ~ A[right]로 옮기며, 피봇은 A[p]에 놓는다.
2. S = (p - 1) - left + 1 // S = Small group의 크기
3. if (k <= S) Selection(A, left, p-1, k)  // Small group에서 찾기
4. else if ( k = S + 1) return A[p]       //피봇 = k번째 숫자
5. else Selection(A, p + 1, right, k - S - 1)  // Large group에서 찾기
```



#### ▶ 선택문제의 Java 코드 구현

```java
import java.util.Random;
public class Selection{
  public static void swap(int[] a, int i, int j) { //배열의 원소를 서로 바꿔주는 기능
​    int temp = a[i];
​    a[i] = a[j]; a[j] = temp;}

  public static int Divide(int[] A, int left, int right){ 
​    int piv = (int) (Random()*(right-left))+left; //피봇을 랜덤으로 설정
​    swap(A,left,piv); //피봇을 맨 왼쪽으로 옮김 (A[left] 값: 피봇)
​    int p = left + 1; //피봇보다 작은 원소들이 피봇(A[left]) 옆으로 와야하기 때문에 
​    int q = right; //피봇보다 큰 원소들은 맨오른쪽으로만 넣으면 자동으로 그룹이 나뉘어짐



​    while (true){ //break 전까지 수행한다
​      while ( p < right && A[p] < A[left]){ //피봇이 A[p] 값보다 크면 p는 +1 이동하며 right 값이 되기 전까지 계속 비교한다
​        p += 1;
​      }
​      while ( q >left && A[q] > A[left]){ //피봇이 A[q] 값보다 크면 q는 -1씩 이동하며 left 값이 되기 전까지 계속 비교한다
​        q -= 1;
​      }
​      if(p >= q){ //p가 계속 커지며(또는 q가 계속 작아지며) p 값이 q 값보다 크거나 같아지면 중단한다(=비교할 대상 없음)
​        break; }
​      swap(A, p, q); //배열 A에서 피봇보다 큰 값 p를 q와 자리를 바꾼다
​      p += 1; q -= 1; //자리를 바꾼 뒤에도 계속 비교하기 위해 이동한다
​    }
​    swap(A, left, p); //가장 왼쪽에 있었던 피봇을 A[p]로 옮긴다
​    return p; //A[p]번째 자리에 있는 값이 곳 피봇이다
  }

  public static int Selection(int[] A, int left, int right, int k){
​    int pivot = Divide(A, left, right);
​    int S = pivot - left;
​    if(k < S){ //Small Group S 안에 K 번째 작은 숫자가 있음 (재귀)
​      Selection(A, left, pivot -1, k);
​    }
​    else if (k == S){ //피봇이 K번째 작은 숫자다 -> 마지막까지 계산되어야 할 부분
​      return A[pivot];
​    }

​    else{ //Small Group S보다 큰 그룹에서 k번째 작은 숫자 찾아야함 (재귀)
​      Selection(A,pivot +1, right, k-pivot-1);
​    }
  }
}
```





#### ▶ 선택문제의 시간 복잡도 이해하기

- 피봇을 선택하는 것이 랜덤으로 결정되기 때문에, 피봇의 L그룹과 S그룹 중 한 쪽이 치우치게 나뉘어질 수 있다. 그렇게 되면 알고리즘 수행 시간이 길어진다.
- 하지만 한 쪽으로 치우치게 분할 될 확률은 2분의 1과 같다 (경우의 수: 한쪽으로 치우침, 치우치지 않음).
  그리고 치우침의 정의는 원본 배열의 절반 이상인 3/4보다 같거나 크다고 가정한다.

따라서 피봇을 평균 2회 연속으로 랜덤하게 정하면 치우치지 않은 분할로 분석이 가능하다.

- 첫 입력의 크기가 n일 때 피봇을 정한 후 입력은 두 개의 그룹으로 분할 된다. (시간복잡도 O(n))
  그리고 치우치지 않은 분할만 일어난다고 가정했을 때 한 부분의 최대 크기는 3/4n이다.

- 그리고 분할을 반복하다보면 n에서부터 3/4배씩 감소하므로 입력 크기가 1일 때에는 분할이 불가능하다.
  따라서 선택문제 알고리즘의 평균 시간복잡도는 
  $$O[n+(3/4)n+(3/4)^2n+(3/4)^3n+\cdots+(3/4)^{i-1} n+(3/4)^in] = O(n)$$
  

> 앞서 가정한 평균 2회 시도시 치우치지 않은 분할이 나온다는 사실을 토대로 2를 곱해야한다.
> 그러므로 2x O(n) = O(n) 이다.