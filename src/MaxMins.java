import java.util.Scanner;
public class MaxMins {
    public int Max(int first, int second) { // max 함수
        int end = 0;
        try {
            if (first > second) { // 조건문에서 max 값 추출
            end = first; 
            } else if (first < second) {
                end = second;
            } else {
                end = first;
            } 
        } catch (Exception e) {
            // TODO: handle exception
        }
        return end;
        
    }
    public int Min(int first, int second) { // Min 함수
        int end = 0;
        try {
            if (first < second) { // 조건문에서 min 값 추출
                end = first; 
              } else if (first > second) {
                  end = second;
              } else {
                  end = first;
              }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return end;
    }
    public static void main(String[] args) {
        try {
            Scanner in = new Scanner(System.in); // 입력 값 두개 받기
            System.out.print("숫자 2개 입력 : ");
            int first = in.nextInt(); // 숫자 1 지정
            int second = in.nextInt(); // 숫자 2 지정
            MaxMins maxMins = new MaxMins(); // max 함수 호출
            int end = maxMins.Max(first, second);
            System.out.println("Max 값 : " + end); // max 값 출력
            int end_min = maxMins.Min(first, second);
            System.out.println("Min 값 : " + end_min); // min 값 출력
        } catch (Exception e) {
            // TODO: handle exception
        }
        // return 0;
    }
}
