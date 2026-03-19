# java基礎学習5

## 拡張for文

## 拡張forとは
配列やコレクションといった複数の要素を持っているものから全ての要素に含まれる
値を順番に取り出して処理をするために使用されます。

## 拡張for文の構文

`for (型　変数名：　配列もしくはコレクション) {`
`実行する処理`
`}`

## 通常のfor文の構文

`for(初期化式；　条件式；　変化式；) `
`実行する式`
`}`

## for文との使い分け
結論、拡張for文を使用した方が**「記述内容がシンプルでコーディングもしやすく、
可読性も優れいている」**ので記述ミスが軽減される点が優れます。

## 1. Java 21のswitch式（従来との違い2点）
 
### 違い①：アロー構文（`->`）でbreak不要に
 
従来はbreakを書き忘れると次のcaseに落ちていく（フォールスルー）問題があった。
アロー構文ではそもそもフォールスルーが起きない。
 
```java
// 従来のswitch文
switch (day) {
    case "MON":
        System.out.println("月曜");
        break; // 忘れると次のcaseに落ちる
    case "TUE":
        System.out.println("火曜");
        break;
    default:
        System.out.println("その他");
}
 
// switch式（アロー構文）
switch (day) {
    case "MON" -> System.out.println("月曜");
    case "TUE" -> System.out.println("火曜");
    default -> System.out.println("その他");
}
```
 
### 違い②：値を返せる（式として使える）
 
従来のswitchは「処理を分岐する」だけの**文**だった。
switch式は結果を変数に**直接代入できる**。
 
```java
// 従来: 変数を先に宣言して各caseで代入
String result;
switch (day) {
    case "MON":
        result = "月曜";
        break;
    case "TUE":
        result = "火曜";
        break;
    default:
        result = "その他";
}
 
// switch式: 値を直接返せる
String result = switch (day) {
    case "MON" -> "月曜";
    case "TUE" -> "火曜";
    default -> "その他";
};  // ← セミコロンが必要（代入文なので）
```
 
### まとめ
- break忘れのバグが**構文レベルで消えた**
- switchが「文」から「式」に昇格し、**値を返せるようになった**
 
---
 
## 2. 配列の初期値（`new` で生成したときの自動初期化）
 
### 基本ルール
`new` で配列を生成すると、各要素は**型に応じたデフォルト値で自動初期化**される。
自分で値を入れなくても、中身はゼロやnullで埋まっている。
 
### 型ごとのデフォルト値
 
| 型 | デフォルト値 |
|----|-------------|
| int, short, byte, long | `0` |
| float, double | `0.0` |
| char | `'\u0000'`（空文字） |
| boolean | `false` |
| 参照型（String, 配列など） | `null` |
 
### 確認例
 
```java
int[] numbers = new int[3];
System.out.println(numbers[0]); // 0
System.out.println(numbers[1]); // 0
System.out.println(numbers[2]); // 0
 
String[] names = new String[2];
System.out.println(names[0]); // null
System.out.println(names[1]); // null
 
boolean[] flags = new boolean[2];
System.out.println(flags[0]); // false
```
 
### なぜ自動初期化されるのか？
- 配列は `new` でヒープメモリ上に確保される
- Javaはヒープ上のメモリを**必ずゼロクリアしてから渡す**仕様になっている
- これにより「初期化し忘れてゴミデータが入っている」というバグを防いでいる
 
### 注意：ローカル変数は自動初期化されない
配列の要素は自動初期化されるが、**ローカル変数は自動初期化されない**。
 
```java
int x;
System.out.println(x); // コンパイルエラー！初期化されていない
 
int[] arr = new int[1];
System.out.println(arr[0]); // OK: 0が入っている
```
 
この違いは「配列の要素はヒープ上、ローカル変数はスタック上」というメモリの管理場所の違いからきている。
 
---
 
## 3. メソッドの戻り値あり（`return`）となし（`void`）の違い
 
### 根本的な違い
- **戻り値あり**：メソッドが処理した**結果を呼び出し元に返す**
- **void**：処理を実行するだけで、**何も返さない**
 
### 戻り値ありのメソッド
 
```java
public static int add(int a, int b) {
    return a + b; // 計算結果を呼び出し元に返す
}
 
// 呼び出し側
int result = add(3, 5); // resultに8が入る
System.out.println(result); // 8
```
 
- 戻り値の型（ここでは `int`）を宣言する
- `return` で値を返す（必須。書かないとコンパイルエラー）
- 呼び出し側で**返ってきた値を受け取れる**
 
### voidのメソッド
 
```java
public static void greet(String name) {
    System.out.println("Hello, " + name); // 画面に出力するだけ
}
 
// 呼び出し側
greet("Hiroki"); // "Hello, Hiroki" が出力される
// String result = greet("Hiroki"); ← コンパイルエラー！voidは値を返さない
```
 
- 戻り値の型の位置に `void` と書く
- `return` は不要（書いても値は返せない）
- 呼び出し側で**値を受け取ることはできない**
 
### どちらを使うべきか？判断基準
- 計算結果や加工したデータを**呼び出し元で使いたい** → 戻り値あり
- 画面表示やファイル書き込みなど**副作用だけが目的** → void
 
### よくある間違い
 
```java
// NG: voidメソッドの結果を変数に入れようとする
public static void add(int a, int b) {
    System.out.println(a + b); // 画面に出すだけで返していない
}
int result = add(3, 5); // コンパイルエラー！
 
// OK: 戻り値ありにする
public static int add(int a, int b) {
    return a + b;
}
int result = add(3, 5); // 8が入る
```
 
### なぜこの区別が重要なのか？
メソッドを「結果を返す部品」と「動作を実行する命令」のどちらとして設計するかで、コードの構造が大きく変わる。戻り値ありのメソッドは**組み合わせて使える**のが強みで、voidメソッドはシンプルだが**再利用しにくい**。
 
```java
// 戻り値ありは組み合わせられる
int total = add(add(1, 2), add(3, 4)); // 10
 
// voidは組み合わせられない
// greet(greet("Hiroki")); ← 不可能
```