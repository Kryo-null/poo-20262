public class AppExcecao4 {

    public static void main (String[] args) {
        divisaoSegura(args);
    }

    private static void divisaoSegura(String array[]) {
        try {
          System.out.println(divideArray(array));
        }
        catch (ArrayIndexOutOfBoundsException e) {
          System.err.println("Uso: AppExcecao3 <num1> <num2>");
          e.printStackTrace();
          System.out.println("Mensagem: "+e.getMessage());
        } catch (NumberFormatException e) {
          System.err.println("Valores devem ser inteiros");
        } catch (ArithmeticException e) {
          System.err.println("Não posso dividir por zero!");
        }
        finally {
          System.out.println(">>> Executando");
        }
    }

    private static int divideArray(String[] array) {
        String s1 = array[0];
        String s2 = array[1];
        return divideStrings(s1, s2);
    }

    private static int divideStrings(String s1, String s2) {
        int i1 = Integer.parseInt(s1);
        int i2 = Integer.parseInt(s2);
        return divideInts(i1, i2);
    }

    private static int divideInts(int i1, int i2) {
        return i1 / i2;
    }
}
