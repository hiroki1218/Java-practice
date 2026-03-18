public class Compare {

  public static void main(String[] args) {
    String a = "hello";
    String b = "hello";
    String c = new String("hello");
    int x = 10;
    int y = 10;    


    System.out.println(a == b);
    System.out.println(a == c);
    System.out.println(a.equals(c));
    System.out.println(x == y);
  }
}